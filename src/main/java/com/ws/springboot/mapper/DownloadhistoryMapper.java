package com.ws.springboot.mapper;

import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.Downloadhistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ws.springboot.common.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-05
 */
@Mapper
public interface DownloadhistoryMapper extends BaseMapper<Downloadhistory> {

    @Select("SELECT bookinfor.*  FROM downloadhistory AS dh,user,bookinfor WHERE dh.book_name = bookinfor.book_name AND dh.user_id = user.id AND dh.user_id = #{id}" )
    List<Bookinfor> getDownloadHistoryInfor(@Param("id") Integer id);

}
