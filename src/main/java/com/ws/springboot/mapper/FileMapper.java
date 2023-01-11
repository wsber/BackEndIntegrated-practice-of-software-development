package com.ws.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.springboot.entity.Files;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends BaseMapper<Files> {

    @Select("select book_url from sys_file where id = #{id}")
    String getById(@Param("id")  Integer id);


}