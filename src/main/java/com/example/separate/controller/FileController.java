package com.example.separate.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.common.Result;
import com.example.separate.entity.File;
import com.example.separate.entity.User;
import com.example.separate.service.IFileService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String filePath;
    @Resource
    private IFileService fileService;


    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        return fileService.upload(file);
    }

    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        fileService.download(fileName,response);
    }

    @GetMapping("/page")
    public IPage<File> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        return fileService.selectFilePage(pageNum,pageSize,name);
    }

    @PostMapping("/update")
    public Integer updateFile(@RequestBody File file){
        return fileService.updateFile(file);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteFileById(@PathVariable Integer id){
        return fileService.deleteFileById(id);
    }

    @PostMapping("/delete/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids){
        return fileService.deleteBatch(ids);
    }


}
