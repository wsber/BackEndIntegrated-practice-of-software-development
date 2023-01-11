package com.ws.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.springboot.entity.SysMenu;
import com.ws.springboot.mapper.SysMenuMapper;
import com.ws.springboot.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> findMenus(String name) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("sort_num");
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        // 查询所有数据
        List<SysMenu> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<SysMenu> parentNodes = list.stream().filter(SysMenu -> SysMenu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (SysMenu sysMenu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            sysMenu.setChildren(list.stream().filter(m -> sysMenu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
