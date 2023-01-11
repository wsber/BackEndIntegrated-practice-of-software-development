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
 * @since 2022-06-11
 */
@Getter
@Setter
  @ApiModel(value = "Rolemenu对象", description = "")
public class Rolemenu implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer roleId;

    private Integer menuId;


}
