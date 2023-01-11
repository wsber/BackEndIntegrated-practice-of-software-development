package com.ws.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.springboot.entity.Userorder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookOrderMapper extends BaseMapper<Userorder> {

}
