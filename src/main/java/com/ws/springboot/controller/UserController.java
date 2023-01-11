package com.ws.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Constants;
import com.ws.springboot.common.Result;
import com.ws.springboot.controller.dto.UserDTO;
import com.ws.springboot.service.impl.UserServiceImpl;
import com.ws.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import com.ws.springboot.service.IUserService;
import com.ws.springboot.entity.User;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    // 登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);

        return Result.success(dto);
    }

    //注册接口
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }



    @GetMapping("/username/{username}")
    public Result findOneByUserName(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }


    @PostMapping("charge")
    public boolean charge(@RequestBody UserDTO user){
        if(user.getPassword().equals("")){
            return false;
        }
        else{
            return (userService.charge(user));
        }
    }

    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    @PostMapping("promote")
    public boolean promote(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable String id) {
        return userService.removeById(id);
    }

    @PostMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<String> userids){
        return userService.removeBatchByIds(userids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable String id) {
        return userService.getById(id);
    }

    //分页查询mybatis-plus
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String tel,
                                @RequestParam(defaultValue = "") String membership){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(tel)){
            queryWrapper.like("tel",tel);
        }
        if(!"".equals(membership)){
            queryWrapper.like("membership",membership);
        }

         User currentUser =  TokenUtils.getCurrentUser();
        System.out.println("这里是当前用户的信息");
        System.out.println(currentUser.getUsername());
        return userService.page(page,queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("userid", "用户ID");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("tel", "电话");
        writer.addHeaderAlias("account", "账户余额");
        writer.addHeaderAlias("regdate", "注册时间");
        writer.addHeaderAlias("membership", "会员等级");
        writer.addHeaderAlias("totalcharge", "总共充值");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setUserid(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setGender(row.get(3).toString());
            user.setTel(row.get(4).toString());
            user.setAccount(Double.valueOf(row.get(5).toString()));
            LocalDateTime localDateTime = LocalDateTime.parse(row.get(6).toString(),df);
            user.setRegdate(localDateTime);
            user.setMembership(row.get(7).toString());
            user.setTotalcharge(Double.valueOf(row.get(8).toString()));
            users.add(user);
        }
        userService.saveBatch(users);
        return true;
    }
}
