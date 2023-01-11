package com.ws.springboot.mapper.PersonalReadingHouse;

import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
public interface CollecthistoryMapper extends BaseMapper<Collecthistory> {

    @Select("select * from collecthistory where bookid = #{bookid} AND userid = #{userid}")
    List<Collecthistory> selectCHByBookidAndUserid(@Param("bookid") Integer bookid, @Param("userid") String userid);

    @Delete("delete  from collecthistory where bookid = #{bookid} AND userid = #{userid}")
    boolean deleteByBookidAndUserid(@Param("bookid") Integer bookid, @Param("userid") String userid);


    @Select("select * from collecthistory where userid = #{userid}")
    List<Collecthistory>getAllByUserid(String userid);

    @Select("SELECT bookinfor.*  FROM collecthistory AS ch , bookinfor where ch.bookid = bookinfor.bookid AND ch.userid = #{userid} ")
    List<Bookinfor> getCollectHistoryInfor(Integer userId);
}
