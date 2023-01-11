package com.ws.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.EmployeeSalary;
import com.ws.springboot.entity.User;
import com.ws.springboot.mapper.EmployeeMapper;
import com.ws.springboot.service.IRolemenuService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


import com.ws.springboot.service.IEmployeeService;
import com.ws.springboot.entity.Employee;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-01
 */
  @RestController
  @RequestMapping("//employee")
  public class EmployeeController {
    
    @Resource
    private IEmployeeService employeeService;
    @Resource
    private IRolemenuService rolemenuService;
    @Resource
    private EmployeeMapper employeeMapper;

    @PostMapping
    public Boolean save(@RequestBody Employee employee) {
            return employeeService.saveOrUpdate(employee);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return employeeService.removeById(id);
            }


    @GetMapping
    public Result findAll() {
            return Result.success(employeeService.list());
            }

    @GetMapping("/{id}")
    public Employee findOne(@PathVariable Integer id) {
            return employeeService.getById(id);
            }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  employeeService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Employee> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
//                                    @RequestParam(defaultValue = "") String realName,
                                    @RequestParam(defaultValue = "") String salary,
                                    @RequestParam(defaultValue = "") String position){
            QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
//            if(!"".equals(realName)){
//            queryWrapper.like("real_name" ,realName);
//            }
            if(!"".equals(salary)){
            queryWrapper.like("salary",salary);
            }
            if(!"".equals(position)){
            queryWrapper.like("position",position);
            }

            //    queryWrapper.orderByDesc("id");
        return employeeService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }



    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception{

        // 从数据库查询出所有的数据
//        List<User> list = employeeMapper.printSalaryForm();
        List<EmployeeSalary> list = employeeMapper.printSalaryForm();


        /*List<Employee> list = employeeService.list();*/

        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
//        writer.addHeaderAlias("id", "用户id");
//        writer.addHeaderAlias("userName", "用户名");
//        writer.addHeaderAlias("passWord", "密码");
//        writer.addHeaderAlias("position", "职位");
//        writer.addHeaderAlias("salary", "薪水");
//        writer.addHeaderAlias("realName", "真实姓名");
//        writer.addHeaderAlias("createTime", "创建时间");
//        writer.addHeaderAlias("avatarUrl", "头像");

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

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Employee> list = reader.readAll(Employee.class);
        System.out.println(list);
        employeeService.saveBatch(list);
        return  true;
    }

    @PostMapping("/rolemenu/{roleId}")
    public Boolean saveRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuids) {
        rolemenuService.setRoleMenu(roleId,menuids);
        return true;
    }

    @GetMapping("/rolemenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success(rolemenuService.getRoleMenu(roleId));
    }

    }



