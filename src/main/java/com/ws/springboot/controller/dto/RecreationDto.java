package com.ws.springboot.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecreationDto implements Serializable {
//    private Location loaction;
//    private Ruralrecreation ruralrecreation;
//    public RecreationDto(Location location, Ruralrecreation ruralrecreation) {
//        this.loaction = location;
//        this.ruralrecreation = ruralrecreation;
//    }
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

    private int enable ;



    @ApiModelProperty("所在区域县级")
    private String region;
    @ApiModelProperty("详细地址镇")
    private String town;
    @ApiModelProperty("村")
    private String village;
    private Integer id;
    private String province;




}
