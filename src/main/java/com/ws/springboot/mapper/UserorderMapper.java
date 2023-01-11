package com.ws.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.Userorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sirius
 * @since 2022-06-05
 */
@Mapper
public interface UserorderMapper extends BaseMapper<Userorder> {

    @Select("SELECT * FROM `userorder` WHERE ordertime BETWEEN #{begintime} AND #{endtime}")
    List<Userorder> selectdate(@Param("begintime") String  begintime, @Param("endtime") String endtime);


}
