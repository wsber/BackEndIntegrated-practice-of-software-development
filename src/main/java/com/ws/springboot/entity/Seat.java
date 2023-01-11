package com.ws.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-06-15
 */
@Getter
@Setter
  @ApiModel(value = "Seat对象", description = "")
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer seatid;

    private Integer isempty;

    private LocalDateTime ordertime;

    private Integer lastingtime;


}
