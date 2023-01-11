package com.ws.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
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
 * @since 2022-06-05
 */
@Getter
@Setter
  @ApiModel(value = "Userorder对象", description = "")
@Data
public class Userorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String ordernumber;

    private String username;

    private String userid;

    private Integer bookid;

    private String bookname;

    private String author;

    private Integer booknum;

    private Double singleprice;

    private Double totalprice;

//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private LocalDateTime ordertime;

}
