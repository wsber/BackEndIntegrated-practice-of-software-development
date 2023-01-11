package com.ws.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.*;
import com.ws.springboot.mapper.BookinforMapper;
import com.ws.springboot.mapper.FileMapper;
import com.ws.springboot.service.IBookinforService;
import com.ws.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private   DownloadhistoryController downloadhistoryController;
    @Resource
    private IBookinforService bookinforService;

    @Resource
    private BookinforMapper bookinforMapper;

//    @GetMapping("/{id}")
//    public String findOne(@PathVariable Integer id) {
//        return fileMapper.getById(id);
//    }

    @PostMapping("/upload")
        public String upload(@RequestParam MultipartFile file) throws IOException {

            String originalFilename =  file.getOriginalFilename();
            String type =  FileUtil.extName(originalFilename);
            long size = file.getSize();

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) { // 文件已存在
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9099/file/" + fileUUID;
        }

        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(FileUtil.getPrefix(originalFilename));
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        return url;
        }

    /**
     * 文件下载接口   http://localhost:9099/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件

        //System.out.println("这里是文件唯一标识码" + fileUUID);
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

//        String url = "http://localhost:9099/file/" + fileUUID;
//
//        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("url", url);
//        List<Files> filesList = fileMapper.selectList(queryWrapper);
//        Files dhFile = filesList.size() == 0 ? null : filesList.get(0);
//
//        Downloadhistory downloadhistory = new Downloadhistory();
//        downloadhistory.setBookName(dhFile.getName());
//        downloadhistory.setUserId(2);   //这个地方暂时写死
//
//        downloadhistoryController.save(downloadhistory);


    }

    @GetMapping("/judgeAuthority/{bookid}")
    public Boolean  judgeAty( @PathVariable String bookid){

        Integer id =  Integer.parseInt(bookid);
        System.out.println(id);
        Bookinfor bookinfor = bookinforService.getById(id);

        int userAuthority = judge();
//        int userAuthority = 100;
        if(bookinfor.getReadingPrivilege().intValue() <= userAuthority){
            return  true;
        }
        else
        {
            return  false;
        }
    }
    @GetMapping("/judgeDownloadAuthority/{bookid}")
    public Boolean  judgeDownloadAty( @PathVariable String bookid){
        Integer id =  Integer.parseInt(bookid);
        System.out.println(id);
        Bookinfor bookinfor = bookinforService.getById(id);
        int userAuthority = judge();
        if(bookinfor.getReadingPrivilege().intValue() <= userAuthority-1){
            return  true;
        }
        else
        {
            return  false;
        }
    }

    @GetMapping("/judgeCollectTimeAuthority/{bookids}")
    public List<Integer> judgeCollectTimeAty(@PathVariable List<Integer> bookids) {
        List<Bookinfor> bookinfors = new ArrayList<>();

        for (Integer bookid : bookids) {
            bookinfors.add(bookinforService.getById(bookid));
        }
        List<Integer> collectDays = new ArrayList<>();
        int userAuthority = judge();
        int differenceValue = 0;
        for (Bookinfor bookinfor : bookinfors) {

            differenceValue = userAuthority - bookinfor.getReadingPrivilege().intValue();
            if (differenceValue < 0) {
                collectDays.add(new Integer(0));
            } else {
                switch (differenceValue) {
                    case 0:
                        collectDays.add(new Integer(128));
                        break;
                    case 1:
                        collectDays.add(new Integer(256));
                        break;
                    case 2:
                        collectDays.add(new Integer(512));
                        break;
                    case 3:
                        collectDays.add(new Integer(99991));
                        break;
                    case 4:
                        collectDays.add(new Integer(99992));
                        break;
                    case 5:
                        collectDays.add(new Integer(99991));
                        break;
                    default:
                        System.out.println("此用户为游客！");
                }
            }

        }
        return  collectDays;
    }

//        int day = 0;
//        int differenceValue = judge() - bookinfor.getReadingPrivilege().intValue() ;
//        if(differenceValue < 0 ){
//            return  0;
//        }
//        else {
//            switch (differenceValue){
//                case 0:
//                    day = 128;
//                    break;
//                case 1: day = 256; break;
//                case 2: day = 512;break;
//                case 3: day = 99991;break;
//                case 4: day = 99992;break;
//                case 5: day = 99993;break;
//                default:
//                    System.out.println("此用户为游客！");
//            }
//        }
//        return day;


    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    @GetMapping("/front/all")
    public Result frontAll(){
        return Result.success(fileMapper.selectList(null));
    }


    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        System.out.println(ids + "这里是ids");
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String type) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        //queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);

        }
        if (!"".equals(type)) {
            queryWrapper.like("type", type);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    public int judge(){

        int authority = -1;
        User user = TokenUtils.getCurrentUser();
        System.out.println("这里是用户级别判断" +user.getMembership() );
        switch (user.getMembership()){
            case"普通会员":
                authority = 0;
                break;
            case "白银会员": authority = 1;System.out.println("此用户为白银会员！" + authority); break;
            case "黄金会员": authority = 2;System.out.println("此用户为黄金会员！"+authority);break;
            case "铂金会员": authority = 3;System.out.println("此用户为铂金会员！"+authority);break;
            case "钻石会员": authority = 4;System.out.println("此用户为钻石会员！"+authority);break;
            case "超级大会员": authority = 5;System.out.println("此用户为超级大会员！"+authority);break;
            default:
                System.out.println("此用户为游客！");
        }
            return authority;
    }




}
