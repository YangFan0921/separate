package com.example.separate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("sys_file")
public class File {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("size")
    private Long size;

    @TableField("url")
    private String url;

    @TableField("md5")
    private String md5;

    @TableField("is_delete")
    private Boolean isDelete;

    @TableField("enable")
    private Boolean enable;


}
