package com.ws.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.springboot.entity.Rolemenu;
import com.ws.springboot.entity.SysMenu;
import com.ws.springboot.mapper.RolemenuMapper;
import com.ws.springboot.service.IRolemenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.springboot.service.ISysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
@Service
public class RolemenuServiceImpl extends ServiceImpl<RolemenuMapper, Rolemenu> implements IRolemenuService {

    @Resource
    private RolemenuMapper rolemenuMapper;

    @Resource
    private ISysMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuids){
        QueryWrapper<Rolemenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        //先删除当前角色id所有的绑定关系
        rolemenuMapper.delete(queryWrapper);
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuids);
        for (Integer menuId : menuids) {
            SysMenu menu = menuService.getById(menuId);
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) { // 二级菜单 并且传过来的menuId数组里面没有它的父级id
                // 那么我们就得补上这个父级id
                Rolemenu roleMenu = new Rolemenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                rolemenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            Rolemenu roleMenu = new Rolemenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            rolemenuMapper.insert(roleMenu);
        }

    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return rolemenuMapper.selectByRoleId(roleId);
    }

}
