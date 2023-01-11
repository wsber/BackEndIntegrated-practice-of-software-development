package com.ws.springboot.controller.dto;

import com.ws.springboot.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String tel;
    private String username;
    private String password;
    private double account;
    private double chargemoney;
    private  String token;
    private List<SysMenu>  menus;
    private String role;
    private String userid;

}
