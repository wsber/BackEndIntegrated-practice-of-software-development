package com.ws.springboot.mapper;

import com.ws.springboot.entity.Ruralrecreation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
public interface RuralrecreationMapper extends BaseMapper<Ruralrecreation> {
    @Select("select max(id) from 'ruralrecreation'")
    public Integer maxID();
}
