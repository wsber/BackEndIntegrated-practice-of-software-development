package com.ws.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2022-06-07
 */
@Getter
@Setter
  @ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    @TableId
    private String userid;

    private String password;

    private String tel;

    private String gender;

//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private LocalDateTime regdate;

    @ApiModelProperty("0级是普通用户，1级是黄金会员，2是铂金会员，3级是钻石会员，享受的折扣和积分机制不一样")
    private String membership;

    private Double account;

    private Double totalcharge;

    private String avatarurl;

    private String role;


}
