package com.ws.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.Storeorder;
import com.ws.springboot.mapper.StoreorderMapper;
import com.ws.springboot.service.impl.StoreorderServiceImpl;
import com.ws.springboot.utils.TimeNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sirius
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/storeorder")
public class StoreorderController {

    @Autowired
    private StoreorderServiceImpl storeorderService;

    @Resource
    private StoreorderMapper storeorderMapper;

    @PostMapping("updatestorage")
    public boolean updatestorage(@RequestBody Storeorder storeorder){
        if("".equals(storeorder.getBookname())||"".equals(storeorder.getAuthor())||
                null==storeorder.getBooknum()){
            return false;
        }
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        storeorder.setTotalprice(storeorder.getBooknum()*storeorder.getSingleprice());
        return storeorderService.updatestorage(storeorder);
    }

    @PostMapping
    public boolean save(@RequestBody Storeorder storeorder){
        if("".equals(storeorder.getBookname())||"".equals(storeorder.getAuthor())||
                null==storeorder.getBooknum()){
            return false;
        }
        storeorder.setOrdernumber(TimeNumberUtils.getLocalTrmSeqNum());
        storeorder.setTotalprice(storeorder.getBooknum()*storeorder.getSingleprice());
        return storeorderService.saveOrUpdate(storeorder);
    }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable String id) {
        return storeorderService.removeById(id);
    }

    @PostMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<String> userids){
        return storeorderService.removeBatchByIds(userids);
    }

    @GetMapping
    public List<Storeorder> findAll() {
        return storeorderService.list();
    }

    @GetMapping("/{id}")
    public Storeorder findOne(@PathVariable String id) {
        return storeorderService.getById(id);
    }


    @GetMapping("/page")
    public IPage<Storeorder> findPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "")String bookname,
                                      @RequestParam(defaultValue = "")String ordernumber){
        IPage<Storeorder> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Storeorder> queryWrapper = new QueryWrapper<>();
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(ordernumber)){
            queryWrapper.like("ordernumber",ordernumber);
        }
        return storeorderService.page(page,queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Storeorder> list = storeorderService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("ordernumber", "订单号");
        writer.addHeaderAlias("bookname", "书名");
        writer.addHeaderAlias("booknum", "购进量");
        writer.addHeaderAlias("singleprice", "单价");
        writer.addHeaderAlias("totalprice", "总价");
        writer.addHeaderAlias("ordertime", "订单时间");
        writer.addHeaderAlias("author", "作者");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("书籍进货信息", "UTF-8");
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
        List<Storeorder> orders = CollUtil.newArrayList();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (List<Object> row : list) {
            Storeorder storeorder = new Storeorder();
            storeorder.setOrdernumber(row.get(0).toString());
            storeorder.setBookname(row.get(1).toString());
            storeorder.setBooknum(Integer.valueOf(row.get(2).toString()));
            storeorder.setSingleprice(Double.valueOf(row.get(3).toString()));
            storeorder.setTotalprice(Double.valueOf(row.get(4).toString()));
            LocalDateTime localDateTime = LocalDateTime.parse(row.get(5).toString(),df);
            storeorder.setOrdertime(localDateTime);
            storeorder.setAuthor(row.get(6).toString());
            orders.add(storeorder);
        }
        storeorderService.saveBatch(orders);
        return true;
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
    public IPage<Storeorder> findPage2(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String begintime,
                                     @RequestParam(defaultValue = "") String endtime) {
        IPage<Bookinfor> page = new Page<>(pageNum, pageSize);
        return getPages(pageNum, pageSize, storeorderMapper.selectdate(begintime, endtime));
    }
}
