package com.ws.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Constants;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Dict;
import com.ws.springboot.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.ISysMenuService;
import com.ws.springboot.entity.SysMenu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
        @RestController
        @RequestMapping("//menu")
            public class SysMenuController {
    
    @Resource
    private ISysMenuService sysMenuService;
    @Resource
    private DictMapper dictMapper;

    @PostMapping
    public Boolean save(@RequestBody SysMenu sysMenu) {
            return sysMenuService.saveOrUpdate(sysMenu);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return sysMenuService.removeById(id);
            }

    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(sysMenuService.list().stream().map(SysMenu::getId));
    }

    @GetMapping
    public List<SysMenu>  findAll(@RequestParam(defaultValue = "") String name) {
        return sysMenuService.findMenus(name);
    }

    @GetMapping("/{id}")
    public SysMenu findOne(@PathVariable Integer id) {
            return sysMenuService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  sysMenuService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<SysMenu> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String name)
                                   {
            QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
            if(!"".equals(name)){
            queryWrapper.like("name" ,name);
            }
                queryWrapper.orderByDesc("id");
            return sysMenuService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }

     @GetMapping("/icons")
            public Result getIcons() {
                QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
                return Result.success(dictMapper.selectList(queryWrapper));
            }
 }

