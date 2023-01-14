package com.ws.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Employee;
import com.ws.springboot.entity.Files;
import com.ws.springboot.mapper.BookinforMapper;
import com.ws.springboot.utils.DateUtil;
import com.ws.springboot.utils.TimeNumberUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.ws.springboot.service.IBookinforService;
import com.ws.springboot.entity.Bookinfor;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-29
 */
        @RestController
        @CrossOrigin
        @RequestMapping("/bookinfor")
    public class BookinforController {

    @Resource
    private IBookinforService bookinforService;
    @Resource
    private BookinforMapper bookinforMapper;


    @GetMapping("/saleinfo")
    public Result loadBookToUser()
    {
        QueryWrapper<Bookinfor> wrapper = new QueryWrapper<>();
        wrapper.eq("oldbook",'0');
        System.out.println("request info ...");
        List<Bookinfor> booklist = bookinforMapper.selectList(wrapper);
        System.out.println(booklist);

        System.out.print("wser");

        return Result.success(booklist);
    }

    @GetMapping("/saleinfo2")
    public Result loadOldBook()
    {
        QueryWrapper<Bookinfor> wrapper = new QueryWrapper<>();
        wrapper.eq("oldbook",'1');
        System.out.println("request info ...");
        List<Bookinfor> booklist = bookinforMapper.selectList(wrapper);
        System.out.println(booklist);
        return Result.success(booklist);
    }

    @GetMapping("/memberBook")
    public Result getMemberBook(){
        QueryWrapper<Bookinfor> wrapper = new QueryWrapper<>();
        wrapper.ge("reading_privilege",1);
        System.out.println("我正在request reading_privilege ...");
        List<Bookinfor> booklist = bookinforMapper.selectList(wrapper);
        System.out.println(booklist);
     return  Result.success(booklist);
    }

    @GetMapping("/searchBookTheme/{themeList}")
    public List<Bookinfor> searchBookTheme(@PathVariable List<String > themeList ){
        List<Bookinfor> bookinfors = new ArrayList<>();
        for(String theme : themeList){
            List<Bookinfor> tmp = bookinforMapper.selectListByTheme(theme);
            bookinfors.addAll(tmp);
        }

       /* int i = 0;

        if( areaList.size() != 0){
            for( i = 0  ; i < bookinfors.size() ; i ++){
                int countMarry = 0 ;
                int j = 0 ;
                Bookinfor bookinfor = bookinfors.get(i);
                for( j = 0 ; j < areaList.size() ; j++){
                    if(bookinfor.getArea() == areaList.get(j)){
                        countMarry ++;
                    }
                }
                if(countMarry == 0){
                    bookinfors.remove(bookinfor);
                }
            }
        }*/


        return  bookinfors;
    }



    @GetMapping("/searchBookArea/{areaList}")
    public List<Bookinfor> searchBookArea(@PathVariable List<String > areaList){
        List<Bookinfor> bookinfors = new ArrayList<>();

        for(String area : areaList){
            List<Bookinfor> tmp = bookinforMapper.selectListByArea(area);
            bookinfors.addAll(tmp);
        }

        return  bookinfors;
    }

    @PostMapping
    public Boolean save(@RequestBody Bookinfor bookinfor) {
        return bookinforService.saveOrUpdate(bookinfor);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Bookinfor bookinfor) {
        return Result.success(bookinforMapper.updateById(bookinfor));
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return bookinforService.removeById(id);
    }


    @GetMapping
    public List<Bookinfor> findAll() {
        QueryWrapper<Bookinfor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("click_num");
        return bookinforService.list(queryWrapper);
    }


    @PostMapping("/intersectionBook/{bookList}/{areaList}")
    public List<Bookinfor> deleteHistory(@PathVariable List<Bookinfor> bookList, @PathVariable List<String> areaList) {

        int i = 0;
        for( i = 0  ; i < bookList.size() ; i ++){
            int countMarry = 0 ;
            int j = 0 ;
            Bookinfor bookinfor = bookList.get(i);
            for( j = 0 ; j < areaList.size() ; j++){
                if(bookinfor.getArea() == areaList.get(j)){
                    countMarry ++;
                }
            }
            if(countMarry == 0){
                bookList.remove(bookinfor);
            }
        }

        return bookList;

    }



    @GetMapping("/{id}")
    public Bookinfor findOne(@PathVariable Integer id) {
        return bookinforService.getById(id);
    }

    @DeleteMapping("del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return bookinforService.removeBatchByIds(ids);
    }

    @GetMapping("/page")
    public Page<Bookinfor> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String bookname,
                                    @RequestParam(defaultValue = "") String readingPrivilege,
                                    @RequestParam(defaultValue = "") String authorName) {

        QueryWrapper<Bookinfor> queryWrapper = new QueryWrapper<>();
        if (!"".equals(bookname)) {
            queryWrapper.like("bookname", bookname);
        }
        if (!"".equals(readingPrivilege)) {
            queryWrapper.like("reading_privilege", readingPrivilege);
        }
        if (!"".equals(authorName)) {
            queryWrapper.like("author_name", authorName);
        }
        queryWrapper.orderByDesc("click_num");
        return bookinforService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/page2")
    public IPage<Bookinfor> findPage2(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String bookname,
                                    @RequestParam(defaultValue = "") String authorName,
                                    @RequestParam(defaultValue = "") String bookid){
        IPage<Bookinfor> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Bookinfor> queryWrapper = new QueryWrapper<>();
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(authorName)){
            queryWrapper.like("author_name",authorName);
        }
        if(!"".equals(bookid)){
            queryWrapper.like("bookid",bookid);
        }
        return bookinforService.page(page,queryWrapper);
    }

    @GetMapping("/likeSearchBooks")
    public List<Bookinfor> likeSearchBooks(
                                    @RequestParam(defaultValue = "") String bookname,
                                    @RequestParam(defaultValue = "") String memberName,
                                    @RequestParam(defaultValue = "") String authorName) {

        QueryWrapper<Bookinfor> queryWrapper = new QueryWrapper<>();
        int readingPrivilege =  0;

        if (!"".equals(bookname)) {
            queryWrapper.like("bookname", bookname);
        }
        if (!"".equals(memberName)) {
            switch (memberName) {
                case "普通会员":
                    readingPrivilege = 0;
                    break;
                case "白银会员":
                    readingPrivilege = 1;
                    break;
                case "黄金会员":
                    readingPrivilege = 2;
                    break;
                case "铂金会员":
                    readingPrivilege = 3;
                    break;
                case "钻石会员":
                    readingPrivilege = 4;
                    break;
                case "超级大会员":
                    readingPrivilege = 5;
                    break;
                default:
                    System.out.println("此用户为游客！");
            }
            queryWrapper.le("reading_privilege", readingPrivilege);
        }
        if (!"".equals(authorName)) {
            queryWrapper.like("author_name", authorName);
        }

        List<Bookinfor> bookinfors = new ArrayList<>();
        bookinfors.addAll(bookinforService.list(queryWrapper));
        return bookinfors;

    }


    private Page getPages(Integer currentPage, Integer pageSize, List list) {
        Page page = new Page();
        if (list == null) {
            return null;
        }
        int size = list.size();
        if (pageSize > size) {
            pageSize = size;
        }
        if (pageSize != 0) {
            // 求出最大页数，防止currentPage越界
            int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
            if (currentPage > maxPage) {
                currentPage = maxPage;
            }
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;
        List pageList = new ArrayList();
        // 将当前页的数据放进pageList
        for (int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }
        page.setCurrent(currentPage).setSize(pageSize).setTotal(list.size()).setRecords(pageList);
        return page;
    }

    @GetMapping("/page/date")
    public IPage<Bookinfor> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String begintime,
                                     @RequestParam(defaultValue = "") String endtime) {
        IPage<Bookinfor> page = new Page<>(pageNum, pageSize);
        return getPages(pageNum, pageSize, bookinforMapper.selectdate(begintime, endtime));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Bookinfor> list = bookinforService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("图书信息", "UTF-8");
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
        List<Bookinfor> list = reader.readAll(Bookinfor.class);
        System.out.println(list);
        bookinforService.saveBatch(list);

        bookinforService.saveBatch(list);
        return true;
    }






}