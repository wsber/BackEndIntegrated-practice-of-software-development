package com.ws.springboot.mapper;

import com.ws.springboot.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.springboot.entity.EmployeeSalary;
import com.ws.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-01
 */
public interface EmployeeMapper extends BaseMapper<Employee> {


    @Select("select id from employee where authority_key = #{role}")
    Integer selectByAuthority(@Param("role") String role);

    @Select("SELECT user.username, user.tel , user.gender,  user.role , employee.salary FROM employee ,user WHERE employee.authority_key = user.role AND employee.authority_key != 'USER' ")
    List<EmployeeSalary> printSalaryForm();


}
