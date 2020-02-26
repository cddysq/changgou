package com.changgou.user.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 11:29
 * @Description: 城市接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/cities")
public class CitiesController {
    @Autowired
    private CitiesService citiesService;

    /**
     * 查询全部城市数据
     *
     * @return 所有城市信息
     */
    @GetMapping
    public Result<List<Cities>> findAll() {
        return Result.<List<Cities>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( citiesService.findAll() ).build();
    }

    /**
     * 根据ID查询城市数据
     *
     * @param id 城市 id
     * @return 城市信息
     */
    @GetMapping("/{id}")
    public Result<Cities> findById(@PathVariable("id") String id) {
        return Result.<Cities>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( citiesService.findById( id ) ).build();
    }

    /**
     * 新增城市数据
     *
     * @param cities 新城市数据
     * @return 新增结果
     */
    @PostMapping
    public Result<Object> addCities(@RequestBody Cities cities) {
        citiesService.addCities( cities );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加成功" ).build();
    }

    /**
     * 修改城市数据
     *
     * @param id     城市 id
     * @param cities 新城市数据
     * @return 修改结果
     */
    @PutMapping("/{id}")
    public Result<Object> updateCities(@PathVariable("id") String id, @RequestBody Cities cities) {
        cities.setCityid( id );
        citiesService.updateCities( cities );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "修改成功" ).build();
    }

    /**
     * 根据ID删除城市数据
     *
     * @param id 城市 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id) {
        citiesService.deleteById( id );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "删除成功" ).build();
    }

    /**
     * 多条件搜索城市数据
     *
     * @param searchMap 搜索条件
     * @return 城市数据
     */
    @GetMapping("/search")
    public Result<List<Cities>> findList(@RequestParam Map<String, Object> searchMap) {
        return Result.<List<Cities>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( citiesService.findList( searchMap ) ).build();
    }

    /**
     * 分页搜索实现
     *
     * @param searchMap 搜索条件
     * @param page      当前页码
     * @param size      每页显示条数
     * @return 分页结果
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageResult<Cities>> findPage(@RequestParam Map<String, Object> searchMap, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Cities> pageList = citiesService.findPage( searchMap, page, size );
        return Result.<PageResult<Cities>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询成功" )
                .data( PageResult.<Cities>builder()
                        .total( pageList.getTotal() )
                        .rows( pageList.getResult() ).build() ).build();
    }
}