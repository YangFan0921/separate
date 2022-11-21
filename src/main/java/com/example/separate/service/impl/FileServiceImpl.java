package com.example.separate.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.separate.common.Constants;
import com.example.separate.common.Result;
import com.example.separate.entity.User;
import com.example.separate.mapper.FileMapper;
import com.example.separate.service.IFileService;
import com.example.separate.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    @Value("${files.upload.path}")
    private String filePath;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename----------->"+originalFilename);
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        File uploadParentFile = new File(filePath);
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();

        String fileName = uuid + StrUtil.DOT + type;
        System.out.println("fileName ----------> "+fileName);

        File uploadFile = new File(filePath + fileName);
        System.out.println("uploadFile -------------->" +uploadFile);

        String md5;
        //对应接口地址
        String url;
        //存入磁盘
        file.transferTo(uploadFile);
        //获取文件的MD5
        md5 = SecureUtil.md5(uploadFile);
        //从数据库根据MD5查询文件
        com.example.separate.entity.File fileByMD5 = getFileByMD5(md5);
        if (fileByMD5 != null){
            url = fileByMD5.getUrl();
            uploadFile.delete();
        }else {
            url = "http://localhost:9090/file/"+fileName;
        }
        com.example.separate.entity.File saveFile = new com.example.separate.entity.File()
                .setName(originalFilename)
                .setType(type)
                .setSize(size/1024) //单位KB
                .setUrl(url)
                .setMd5(md5);
        System.out.println(saveFile);
        fileMapper.insert(saveFile);
        flushRedis(Constants.FILES_KEY);
        return url;
    }

    @Override
    public void download(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(filePath + fileName);
        System.out.println("fileName------------------->"+fileName);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();
    }

    public com.example.separate.entity.File getFileByMD5(String md5){
        QueryWrapper<com.example.separate.entity.File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<com.example.separate.entity.File> fileList = fileMapper.selectList(queryWrapper);
        return fileList.size()==0 ? null : fileList.get(0);
    }

    @Override
    public IPage<com.example.separate.entity.File> selectFilePage(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<com.example.separate.entity.File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",false);
//        queryWrapper.eq("enable",true);
        queryWrapper.orderByDesc("id");
        if ( !"".equals(name) ){
            queryWrapper.like("name",name);
        }
        Page<com.example.separate.entity.File> page = new Page<>(pageNum,pageSize);
        return fileMapper.selectPage(page, queryWrapper);

    }

    @Override
    public Integer deleteFileById(Integer id) {
        com.example.separate.entity.File selectByIdFile = fileMapper.selectById(id);
        selectByIdFile.setIsDelete(true);
        int num = fileMapper.updateById(selectByIdFile);
        flushRedis(Constants.FILES_KEY);
        return num;
    }

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        QueryWrapper<com.example.separate.entity.File> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<com.example.separate.entity.File> fileList = fileMapper.selectList(queryWrapper);
        int num = 0;
        for (com.example.separate.entity.File file: fileList){
            file.setIsDelete(true);
            num = fileMapper.updateById(file);
        }
        flushRedis(Constants.FILES_KEY);
        return num;
    }

    @Override
    public Integer updateFile(com.example.separate.entity.File file) {
        int num = fileMapper.updateById(file);
        flushRedis(Constants.FILES_KEY);
        return num;
    }

    // 删除缓存
    private void flushRedis(String key) {
        stringRedisTemplate.delete(key);
    }


}
