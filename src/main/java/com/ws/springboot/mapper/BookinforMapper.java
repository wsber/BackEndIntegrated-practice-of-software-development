package com.ws.springboot.mapper;

import com.ws.springboot.entity.Bookinfor;
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
 * @since 2022-05-29
 */
public interface BookinforMapper extends BaseMapper<Bookinfor> {

    @Select("SELECT * FROM `bookinfor` WHERE buy_time BETWEEN #{begintime} AND #{endtime}")
    List<Bookinfor> selectdate(@Param("begintime") String  begintime, @Param("endtime") String endtime);

    @Select("SELECT * FROM `bookinfor` WHERE theme = #{theme}")
    List<Bookinfor> selectListByTheme(@Param("theme") String theme);

    @Select("SELECT * FROM `bookinfor` WHERE area = #{area}")
    List<Bookinfor> selectListByArea(@Param("area") String area);

    @Select("SELECT * FROM `bookinfor` WHERE book_type = 0")
    List<Bookinfor> getVillageBooks();

    @Select("SELECT * FROM `bookinfor` WHERE book_type = 1 or book_type = 2")
    List<Bookinfor> getYouthBooks();

//    @Select("SELECT * FROM `bookinfo` WHERE ")
//    List<Bookinfor> getByBookids(List<Integer> bookids);
}
