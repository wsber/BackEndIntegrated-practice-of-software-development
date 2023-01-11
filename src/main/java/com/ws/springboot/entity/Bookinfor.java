package com.ws.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2022-05-29
 */
@Getter
@Setter
  @ApiModel(value = "Bookinfor对象", description = "")
public class Bookinfor implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "bookid", type = IdType.AUTO)
      private Integer bookid;

    private String cover;


    private Integer stockNum;


    private Integer clickNum;


    private String content;


    private Integer readingPrivilege;


    private Integer pageLimit;


    private Integer downloadTime;


    private String bookname;


    private Double originalPrice;


    private String authorName;


    private String synopsis;

//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private LocalDateTime buyTime;

    private Integer sales;

    private String bookUrl;

    private char oldbook;

    private Double discount;

    private String theme;

    private Boolean enable;

    private String area;

}
