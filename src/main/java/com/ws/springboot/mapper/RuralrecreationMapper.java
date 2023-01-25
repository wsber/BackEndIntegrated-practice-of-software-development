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
            "\t r.activity_name,\n" +
            "\t r.activity_startTime,\n" +
            "\t r.audience_number,\n" +
            "\t r.mydesc,\n" +
            "\t r.performance_team,\n" +
            "\t r.activity_endTime,\n" +
            "\t r.cover,\n" +
            "\t r.video_file,\n" +
            "\t r.click_num,\n" +
            "\t r.type,\n" +
            "\t r.ENABLE,\n" +
            "\t l.region,\n" +
            "\t l.town,\n" +
            "\t l.village,\n" +
            "\t l.province \n" +
            "FROM\n" +
            "  ruralrecreation r\n" +
            "\tLeft OUTER JOIN recreationlocation rl ON r.id = rl.recreation_id\n" +
            "\tLeft OUTER  Join location l ON rl.loaction_id = l.id "
           )
    List<RecreationDto> getDetailData();

}
