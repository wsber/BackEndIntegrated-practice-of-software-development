package com.ws.springboot.service;

import com.ws.springboot.entity.Rolemenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
public interface IRolemenuService extends IService<Rolemenu> {

    void setRoleMenu(Integer roleId, List<Integer> menuids);

    List<Integer> getRoleMenu(Integer roleId);
}
