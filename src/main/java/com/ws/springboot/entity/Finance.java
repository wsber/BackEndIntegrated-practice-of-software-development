package com.ws.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2022-06-03
 */
@Getter
@Setter
@Data
  @ApiModel(value = "Finance对象", description = "")
public class Finance implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      private String time;

      @ApiModelProperty("盈利")
      private Double income;

      @ApiModelProperty("支出")
      private Double expenditure;

      @ApiModelProperty("员工数")
      private Integer employeeNum;

      @ApiModelProperty("营业额")
      private Double turnover;


}
