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
 * @since 2023-01-15
 */
@Getter
@Setter
  @ApiModel(value = "Activityphotoes对象", description = "")
public class Activityphotoes implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String url;

    private String name;


}
