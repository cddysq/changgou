package com.changgou.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.user.dao.ProvincesMapper;
import com.changgou.user.pojo.Provinces;
import com.changgou.user.service.ProvincesService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:55
 * @Description: 省份服务实现
 **/
@Service
public class ProvincesServiceImpl implements ProvincesService {
    @Autowired
    private ProvincesMapper provincesMapper;

    @Override
    public List<Provinces> findAll() {
        return provincesMapper.selectAll();
    }

    @Override
    public Provinces findById(String id) {
        return provincesMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProvinces(Provinces provinces) {
        provincesMapper.insertSelective( provinces );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProvinces(Provinces provinces) {
        provincesMapper.updateByPrimaryKeySelective( provinces );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        provincesMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Provinces> findList(@NotNull Map<String, Object> searchMap) {
        return provincesMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Provinces> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> provincesMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Provinces.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 省份ID
            String provinceId = Convert.toStr( searchMap.get( "provinceid" ) );
            if (StrUtil.isNotEmpty( provinceId )) {
                criteria.andLike( "provinceid", "%" + provinceId + "%" );
            }
            // 省份名称
            String province = Convert.toStr( searchMap.get( "province" ) );
            if (StrUtil.isNotEmpty( province )) {
                criteria.andLike( "province", "%" + province + "%" );
            }
        }
        return example;
    }
}