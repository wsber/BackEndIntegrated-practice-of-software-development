package com.ws.springboot.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.springboot.common.Constants;
import com.ws.springboot.controller.dto.UserDTO;
import com.ws.springboot.entity.Rolemenu;
import com.ws.springboot.entity.SysMenu;
import com.ws.springboot.entity.User;
import com.ws.springboot.exception.ServiceException;
import com.ws.springboot.mapper.EmployeeMapper;
import com.ws.springboot.mapper.RolemenuMapper;
import com.ws.springboot.mapper.UserMapper;
import com.ws.springboot.service.ISysMenuService;
import com.ws.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.springboot.utils.TimeNumberUtils;
import com.ws.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-07
 */
    @Service
    public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private static final Log LOG = Log.get();

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RolemenuMapper rolemenuMapper;

    @Resource
    private ISysMenuService sysMenuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtils.genToken(one.getUserid(),one.getPassword());
            userDTO.setToken(token);

            String role  =  one.getRole();
            //设置用户的菜单列表
            System.out.println("这里是该用户的角色" + role);
            userDTO.setRole(role);
            Integer roleId =  employeeMapper.selectByAuthority(role);
            System.out.println("这里是"+roleId);

            List<SysMenu> roleMenus = getRoleMenus(roleId);

            System.out.println("这里是查询到的菜单表");
            for(SysMenu sysMenu:roleMenus){
                System.out.println(sysMenu.getId());
            }
            userDTO.setMenus(roleMenus);
            userDTO.setUserid(one.getUserid());
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            one.setUserid(TimeNumberUtils.getLocalTrmSeqNum());
            one.setRole("USER");
            save(one);      //存到数据库
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名已存在");
        }
        return one;
    }



    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);

        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }


        public boolean saveUser(User user) {
            return saveOrUpdate(user);
        }

        public boolean charge(UserDTO user) {
            String tel = user.getTel();
            String password = user.getPassword();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",user.getUsername());
            queryWrapper.eq("tel",user.getTel());
            queryWrapper.eq("password",user.getPassword());
            User one = getOne(queryWrapper);
            if(one!=null){
                double account = one.getAccount()+user.getChargemoney();
                double totalcharge = one.getTotalcharge() + user.getChargemoney();
                one.setAccount(account);
                one.setTotalcharge(totalcharge);
                if(totalcharge==0){
                    one.setMembership("普通会员");
                }else if(totalcharge>0&&totalcharge<100){
                    one.setMembership("白银会员");
                }else if(totalcharge>=100&&totalcharge<300){

                    one.setMembership("黄金会员");

                }else if(totalcharge>=300&&totalcharge<500){

                    one.setMembership("铂金会员");

                }else if(500<=totalcharge&&totalcharge<700){

                    one.setMembership("钻石会员");

                } else if(totalcharge>=700){

                    one.setMembership("超级大会员");

                }
                saveOrUpdate(one);
                return true;
            }
            else return false;
        }

        //获取当前角色的菜单列表

        private  List<SysMenu> getRoleMenus( Integer roleId){

            System.out.println("这里是"+roleId);
            //当前角色所有菜单id的集合
            List<Integer> menuIds = rolemenuMapper.selectByRoleId(roleId);

            //查出系统所有菜单
            List<SysMenu>  menus = sysMenuService.findMenus("");

            List<SysMenu> roleMenus = new ArrayList<>();
            //筛选当前用户角色的菜单
            for(SysMenu menu :menus){
                if(menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                List<SysMenu> children = menu.getChildren();
                //removeIf() 移除children里不在menuIds集合中的元素
                children.removeIf(child -> !menuIds.contains(child.getPid()));

            }

            return  roleMenus;
        }

    }



