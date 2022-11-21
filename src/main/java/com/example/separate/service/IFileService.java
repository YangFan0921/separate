package com.example.separate.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.entity.File;
import com.example.separate.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IFileService {

    String upload(MultipartFile file) throws IOException;

    void download(String fileName, HttpServletResponse response) throws IOException;

    IPage<File> selectFilePage(Integer pageNum, Integer pageSize, String name);

    Integer deleteFileById(Integer id);

    Integer deleteBatch(List<Integer> ids);

    Integer updateFile(File file);

}
