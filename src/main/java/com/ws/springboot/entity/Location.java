package com.ws.springboot.entity;

import java.io.Serializable;
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
  @ApiModel(value = "Location对象", description = "")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("所在区域县级")
      private String region;

      @ApiModelProperty("详细地址镇")
      private String town;

      @ApiModelProperty("村")
      private String village;

    private Integer id;

    private String province;


}
