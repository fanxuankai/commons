package com.fanxuankai.commons.extra.mybatis.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.domain.Status;
import com.fanxuankai.commons.exception.DataBaseException;
import com.fanxuankai.commons.extra.mybatis.util.MybatisPlusPageUtils;
import com.fanxuankai.commons.extra.mybatis.util.QueryHelper;
import com.fanxuankai.commons.extra.spring.util.GenericTypeUtils;

import java.util.List;
import java.util.Optional;

/**
 * DAO 接口
 *
 * @param <T> 实体类
 * @param <C> 条件类
 * @author fanxuankai
 */
public interface BaseDao<T, C> extends IService<T> {
    /**
     * 获取实体类类型
     *
     * @return /
     */
    default Class<T> entityClass() {
        return GenericTypeUtils.getGenericType(getClass(), BaseDao.class, "T");
    }

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return T, 查询不到会抛出异常
     */
    default T getOne(Long id) {
        return Optional.ofNullable(getById(id)).orElseThrow(() -> new DataBaseException(new Status() {
            @Override
            public int getCode() {
                return DefaultStatus.FAILED.getCode();
            }

            @Override
            public String getMessage() {
                return "找不到数据";
            }
        }));
    }

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return PageResult
     */
    default PageResult<T> page(C criteria, Page page) {
        return MybatisPlusPageUtils.convert(page(MybatisPlusPageUtils.convert(page),
                QueryHelper.getQueryWrapper(entityClass(), criteria)));
    }

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    default List<T> list(C criteria) {
        return list(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }

    /**
     * 查询数量
     *
     * @param criteria 条件参数
     * @return int
     */
    default int count(C criteria) {
        return count(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }

    /**
     * 是否存在
     *
     * @param criteria 条件参数
     * @return boolean
     */
    default boolean exists(C criteria) {
        return count(criteria) > 0;
    }

    /**
     * 更新
     *
     * @param t        实体类
     * @param criteria 条件参数
     * @return 是否更新成功
     */
    default boolean update(T t, C criteria) {
        return update(t, QueryHelper.getUpdateWrapper(entityClass(), criteria));
    }

    /**
     * 删除
     *
     * @param criteria 条件参数
     * @return 是否删除成功
     */
    default boolean remove(C criteria) {
        return remove(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }
}
