package com.ws.springboot.mapper.PersonalReadingHouse;

import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;
import com.ws.springboot.entity.PersonalReadingHouse.Readinghistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
public interface ReadinghistoryMapper extends BaseMapper<Readinghistory> {
    @Select("select * from readinghistory where bookid = #{bookid} AND userid = #{userid}")
    List<Readinghistory> selectRHByBookidAndUserid(Integer bookid, String userid);

//    @Select("SELECT bookinfor.*  FROM downloadhistory AS dh,user,bookinfor WHERE dh.book_name = bookinfor.book_name AND dh.user_id = user.id AND dh.user_id = #{id}" )
//    List<Bookinfor> getDownloadHistoryInfor(@Param("id") Integer id);
    @Select("SELECT bookinfor.*  FROM collecthistory AS ch , bookinfor where ch.bookid = bookinfor.bookid AND ch.userid = #{userid} ")
    List<Bookinfor> getReadingHistoryInfor(@Param("userid")Integer userid);
}
