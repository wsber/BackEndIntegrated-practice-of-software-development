package com.ws.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private Integer bookType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookinfor bookinfor = (Bookinfor) o;
        return oldbook == bookinfor.oldbook &&
                Objects.equals(bookid, bookinfor.bookid) &&
                Objects.equals(cover, bookinfor.cover) &&
                Objects.equals(stockNum, bookinfor.stockNum) &&
                Objects.equals(clickNum, bookinfor.clickNum) &&
                Objects.equals(content, bookinfor.content) &&
                Objects.equals(readingPrivilege, bookinfor.readingPrivilege) &&
                Objects.equals(pageLimit, bookinfor.pageLimit) &&
                Objects.equals(downloadTime, bookinfor.downloadTime) &&
                Objects.equals(bookname, bookinfor.bookname) &&
                Objects.equals(originalPrice, bookinfor.originalPrice) &&
                Objects.equals(authorName, bookinfor.authorName) &&
                Objects.equals(synopsis, bookinfor.synopsis) &&
                Objects.equals(buyTime, bookinfor.buyTime) &&
                Objects.equals(sales, bookinfor.sales) &&
                Objects.equals(bookUrl, bookinfor.bookUrl) &&
                Objects.equals(discount, bookinfor.discount) &&
                Objects.equals(theme, bookinfor.theme) &&
                Objects.equals(enable, bookinfor.enable) &&
                Objects.equals(area, bookinfor.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookid, cover, stockNum, clickNum, content, readingPrivilege, pageLimit, downloadTime, bookname, originalPrice, authorName, synopsis, buyTime, sales, bookUrl, oldbook, discount, theme, enable, area);
    }
}
