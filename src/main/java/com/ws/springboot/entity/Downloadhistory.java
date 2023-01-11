package com.ws.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-06-05
 */
@Getter
@Setter
  @ApiModel(value = "Downloadhistory对象", description = "")
public class Downloadhistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bookName;

    private LocalDateTime downloadTime;

    private Integer userId;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


}
