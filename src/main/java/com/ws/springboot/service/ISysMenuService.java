package com.ws.springboot.service;

import com.ws.springboot.entity.SysMenu;
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
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenus(String name);
}
