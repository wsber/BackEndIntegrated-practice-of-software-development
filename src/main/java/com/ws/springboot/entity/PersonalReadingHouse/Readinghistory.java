package com.ws.springboot.entity.PersonalReadingHouse;

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
 * @since 2022-06-13
 */
@Getter
@Setter
  @ApiModel(value = "Readinghistory对象", description = "")
public class Readinghistory implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer bookid;

      private String userid;

    private LocalDateTime readingTime;


}
