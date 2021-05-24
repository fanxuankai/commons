package com.fanxuankai.commons.extra.mybatis.base;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Service 接口
 *
 * @param <D> DTO
 * @param <V> VO
 * @author fanxuankai
 */
public interface BaseService<D, V> {
    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return PageResult
     */
    PageResult<V> page(Object criteria, Page page);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    List<V> list(Object criteria);

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return V
     */
    V get(Long id);

    /**
     * 创建
     *
     * @param dto 数据
     */
    void create(D dto);

    /**
     * 修改
     *
     * @param id  ID
     * @param dto 数据
     */
    void update(Long id, D dto);

    /**
     * 多选删除
     *
     * @param ids ID 列表
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    default void download(List<V> all, HttpServletResponse response) throws IOException {

    }
}