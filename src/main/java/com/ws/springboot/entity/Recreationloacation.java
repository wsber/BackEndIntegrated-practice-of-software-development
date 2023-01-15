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
  @ApiModel(value = "Recreationloacation对象", description = "")
public class Recreationloacation implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("娱乐活动id")
      private Integer recreationId;

      @ApiModelProperty("所在地址id")
      private Integer loactionId;


}
