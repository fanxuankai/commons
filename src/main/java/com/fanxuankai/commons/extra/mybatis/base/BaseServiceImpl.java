package com.fanxuankai.commons.extra.mybatis.base;

import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Service 接口实现类
 *
 * @param <T>   实体类
 * @param <D>   DTO
 * @param <V>   VO
 * @param <C>   对象转换器
 * @param <DAO> DAO
 * @author fanxuankai
 */
public class BaseServiceImpl<T extends BaseModel, D, V, Criteria,
        C extends Converter<T, D, V>,
        DAO extends BaseDao<T, Criteria>>
        implements BaseService<D, V, Criteria> {
    @Autowired
    protected C converter;
    @Autowired
    protected DAO baseDao;

    @Override
    public PageResult<V> page(Criteria criteria, PageRequest pageRequest) {
        return baseDao.page(criteria, pageRequest).map(converter::toVo);
    }

    @Override
    public List<V> list(Criteria criteria) {
        return converter.toVo(baseDao.list(criteria));
    }

    @Override
    public V get(Long id) {
        return converter.toVo(baseDao.getById(id));
    }

    @Override
    public void create(D dto) {
        baseDao.save(converter.toEntity(dto));
    }

    @Override
    public void update(Long id, D dto) {
        T existsUser = baseDao.getById(id);
        T t = converter.toEntity(dto);
        t.setId(existsUser.getId());
        baseDao.updateById(t);
    }

    @Override
    public void deleteAll(Long[] ids) {
        baseDao.removeByIds(Arrays.asList(ids));
    }
}