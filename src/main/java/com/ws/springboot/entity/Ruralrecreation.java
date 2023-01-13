package com.ws.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
@Getter
@Setter
  @ApiModel(value = "Ruralrecreation对象", description = "")
public class Ruralrecreation implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("活动名称")
      private String activityName;

      @ApiModelProperty("活动开始时间")
      private LocalDateTime activityStarttime;

      @ApiModelProperty("观众数目")
      private Integer audienceNumber;

      @ApiModelProperty("活动简述")
      private String mydesc;

      @ApiModelProperty("演出团队")
      private String performanceTeam;

      @ApiModelProperty("活动终止时间")
      private LocalDateTime activityEndtime;

      @ApiModelProperty("封面图片（M）")
      private String cover;

      @ApiModelProperty("视频文件（G）")
      private String videoFile;

      @ApiModelProperty("点击量")
      private Integer clickNum;

      @ApiModelProperty("送戏下乡，公共电影放映，文化演出活动")
      private String type;


}
