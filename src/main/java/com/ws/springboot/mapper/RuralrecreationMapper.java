package com.ws.springboot.mapper;

import com.ws.springboot.controller.dto.RecreationDto;
import com.ws.springboot.entity.Ruralrecreation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
public interface RuralrecreationMapper extends BaseMapper<Ruralrecreation> {

    @Select("select l.region , r.mydesc  from location l, ruralrecreation r ,recreationlocation rl where r.id = rl.recreation_id and rl.loaction_id = l.id")
    List<RecreationDto> getDetailData();
}
