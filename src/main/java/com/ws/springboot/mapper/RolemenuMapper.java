package com.ws.springboot.mapper;

import com.ws.springboot.entity.Rolemenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
public interface RolemenuMapper extends BaseMapper<Rolemenu> {

    @Select("select menu_id from rolemenu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);


}
