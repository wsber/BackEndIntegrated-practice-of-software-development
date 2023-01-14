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

    @Select("select r.activity_name,r.activity_startTime, r.audience_number, r.mydesc , r.performance_team,r.activity_endTime,r.cover,r.video_file,r.click_num,r.type, l.region , l.town,l.village,l.province  from location l, ruralrecreation r ,recreationlocation rl  where r.id = rl.recreation_id and rl.loaction_id = l.id")
    List<RecreationDto> getDetailData();
}
