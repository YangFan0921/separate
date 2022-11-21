package com.example.separate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.separate.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
