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

    @Select("select max(id) from 'ruralrecreation'")
    public Integer maxID();

    @Select("SELECT\n" +
            "\tr.activity_name,\n" +
            "\tr.activity_startTime,\n" +
            "\tr.audience_number,\n" +
            "\tr.mydesc,\n" +
            "\tr.performance_team,\n" +
            "\tr.activity_endTime,\n" +
            "\tr.cover,\n" +
            "\tr.video_file,\n" +
            "\tr.click_num,\n" +
            "\tr.type,\n" +
            "\tr.ENABLE,\n" +
            "\tl.region,\n" +
            "\tl.town,\n" +
            "\tl.village,\n" +
            "\tl.province \n" +
            "FROM\n" +
            "  ruralrecreation r\n" +
            "\tLeft OUTER JOIN recreationlocation rl ON r.id = rl.recreation_id\n" +
            "\tLeft OUTER  Join location l ON rl.loaction_id = l.id "
           )
    List<RecreationDto> getDetailData();

}
