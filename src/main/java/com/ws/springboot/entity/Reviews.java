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
  @ApiModel(value = "Reviews对象", description = "")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;

    private String message;

    private String username;

    private Integer topicid;

    private LocalDateTime reviewtime;

      @TableId
      private Integer reviewid;


}
