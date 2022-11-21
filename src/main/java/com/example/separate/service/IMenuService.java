package com.example.separate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.separate.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 阿帆
 * @since 2022-11-13
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findAll(String name);

    Integer saveMenu(Menu menu);

    IPage<Menu> selectMenuPage(Integer pageNum, Integer pageSize, String name);

    Menu me(Integer id);

    Integer deleteMenuById(Integer id);

    Integer deleteBatch(List<Integer> ids);

    void updateMenu(Menu menu);

    void exportMenu(HttpServletResponse response) throws IOException;

    Boolean importMenu(MultipartFile file) throws IOException;
}
