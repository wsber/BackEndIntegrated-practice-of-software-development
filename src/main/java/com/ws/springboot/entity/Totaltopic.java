package com.ws.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-06-14
 */
@Getter
@Setter
  @ApiModel(value = "Totaltopic对象", description = "")
public class Totaltopic implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId
      private Integer topicid;

    private String userid;

    private String title;

      @ApiModelProperty("回帖数量")
      private Integer remarknum;

    private String context;

      @ApiModelProperty("这里的用户是话题的发起者")
      private String author;

    private LocalDateTime createTime;

    private String description;

    private Integer status;

    private String lables;


}
