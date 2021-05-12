package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @author fanxuankai
 */
public abstract class AbstractTreeDao<T extends TreeNode, M extends BaseMapper<T>, C>
        extends ServiceImpl<M, T> implements TreeDao<T, C> {
    protected final Class<T> entityClass;

    public AbstractTreeDao() {
        this.entityClass = entityClass();
    }

    @Override
    public List<T> roots(LambdaQueryWrapper<T> wrapper) {
        if (wrapper == null) {
            return list(Wrappers.lambdaQuery(entityClass).eq(T::getLevel, 1));
        } else {
            return list(wrapper.eq(T::getLevel, 1));
        }
    }

    @Override
    public List<T> children(Long id) {
        return list(Wrappers.lambdaQuery(entityClass).eq(T::getPid, id));
    }
}
