package com.ws.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Userorder;
import com.ws.springboot.service.impl.UserorderServiceImpl;
import com.ws.springboot.utils.TimeNumberUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sirius
 * @since 2022-06-05
 */
@RestController
@RequestMapping("/userorder")
public class UserorderController {

    @Resource
    private UserorderServiceImpl userorderService;

    @PostMapping
    public boolean save(@RequestBody Userorder userorder){
        if("".equals(userorder.getBookname())||userorder.getBookid()==null){
            return false;
        }
        userorder.setTotalprice(userorder.getSingleprice()*userorder.getBooknum());
        userorder.setOrdernumber(TimeNumberUtils.getLocalTrmSeqNum());
        if(userorder.getUsername()==null){
            userorder.setUsername("游客");
            //userorder.setUserid(UUID.randomUUID().toString());
            System.out.println("当前是游客！");
        }
//        System.out.println("userid:"+userorder.getUserid());
        System.out.println("username:"+userorder.getUsername());

        return userorderService.saveOrUpdate(userorder);
    }

    @PostMapping("updatesales")
    public Integer updatesales(@RequestBody Userorder userorder){
        if("".equals(userorder.getBookname())){
            return -3;
        }
        System.out.println("----------------执行到这里------------------");
        userorder.setTotalprice(userorder.getSingleprice()*userorder.getBooknum());
//        userorder.setOrdernumber(TimeNumberUtils.getLocalTrmSeqNum());
//        if(userorder.getUsername()==""){
//            userorder.setUsername("游客");
//            //userorder.setUserid(UUID.randomUUID().toString());
//            System.out.println("当前是游客！");
//        }
        return userorderService.updatesales(userorder);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable String id) {
        return userorderService.removeById(id);
    }

    @PostMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<String> userids){
        return userorderService.removeBatchByIds(userids);
    }

    @GetMapping
    public List<Userorder> findAll() {
        return userorderService.list();
    }

    @GetMapping("/{id}")
    public Userorder findOne(@PathVariable String id) {
        return userorderService.getById(id);
    }


    @GetMapping("/page")
    public IPage<Userorder> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String username,
                                     @RequestParam(defaultValue = "") String ordernumber,
                                     @RequestParam(defaultValue = "") String bookname){
        IPage<Userorder> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Userorder> queryWrapper = new QueryWrapper<>();
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(ordernumber)){
            queryWrapper.like("ordernumber",ordernumber);
        }
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        return userorderService.page(page,queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Userorder> list = userorderService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("ordernumber", "订单号");
        writer.addHeaderAlias("userid", "用户ID");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("bookid", "图书ID");
        writer.addHeaderAlias("bookname", "书名");
        writer.addHeaderAlias("author", "作者");
        writer.addHeaderAlias("booknum", "购买量");
        writer.addHeaderAlias("singleprice", "单价");
        writer.addHeaderAlias("totalprice", "总价");
        writer.addHeaderAlias("ordertime", "订单时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户购买信息", "UTF-8");
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
        List<Userorder> orders = CollUtil.newArrayList();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (List<Object> row : list) {
            Userorder userorder = new Userorder();
            userorder.setOrdernumber(row.get(0).toString());
            userorder.setUserid(row.get(1).toString());
            userorder.setUsername(row.get(2).toString());
            userorder.setBookid(Integer.valueOf(row.get(3).toString()));
            userorder.setBookname(row.get(4).toString());
            userorder.setAuthor(row.get(5).toString());
            userorder.setBooknum(Integer.valueOf(row.get(6).toString()));
            userorder.setSingleprice(Double.valueOf(row.get(7).toString()));
            userorder.setTotalprice(Double.valueOf(row.get(8).toString()));
            LocalDateTime localDateTime = LocalDateTime.parse(row.get(9).toString(),df);
            userorder.setOrdertime(localDateTime);
            orders.add(userorder);
        }
        userorderService.saveBatch(orders);
        return true;
    }

    @GetMapping("/number")
    public Result getOrderNumber()
    {
        System.out.println("111");
        return Result.success(userorderService.getOrderNumber());
    }

    @PostMapping("/payorder")
    public Result saveOrder(@RequestBody Userorder userorder) throws AuthenticationException
    {
        System.out.println("55555555555555555555555555");
        System.out.println(userorder.getUsername());
        System.out.println(userorder.getUserid());
        return userorderService.saveOrder(userorder);
    }

}
