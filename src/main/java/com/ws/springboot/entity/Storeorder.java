package com.ws.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sirius
 * @since 2022-06-04
 */
@Getter
@Setter
  @ApiModel(value = "Storeorder对象", description = "")
public class Storeorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String ordernumber;

    private String bookname;

    private Integer booknum;

    private double singleprice;

    private double totalprice;

//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private LocalDateTime ordertime;

    private String author;

}
