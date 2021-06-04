package com.fanxuankai.commons.extra.spring.base;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller 基类
 *
 * @param <D> DTO
 * @param <V> VO
 * @param <S> Service
 * @author fanxuankai
 */
public class BaseController<D, V, S extends BaseService<D, V>> {
    @Autowired
    private S baseService;

    /**
     * 导出数据
     *
     * @param criteria 条件
     * @param response /
     * @throws IOException /
     */
    @GetMapping("download")
    public void download(Object criteria, HttpServletResponse response) throws IOException {
        baseService.download(baseService.list(criteria), response);
    }

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return Result
     */
    @GetMapping("page")
    public Result<PageResult<V>> page(Object criteria, Page page) {
        return ResultUtils.newResult(baseService.page(criteria, page));
    }

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return Result
     */
    @GetMapping("list")
    public Result<List<V>> list(Object criteria) {
        return ResultUtils.newResult(baseService.list(criteria));
    }

    /**
     * 根据 ID 查询
     *
     * @param id ID
     * @return Result
     */
    @GetMapping("get/{id}")
    public Result<V> get(@PathVariable Long id) {
        return ResultUtils.newResult(baseService.get(id));
    }

    /**
     * 创建
     *
     * @param dto 数据
     * @return Result
     */
    @PostMapping("create")
    public Result<Void> create(@Validated @RequestBody D dto) {
        baseService.create(dto);
        return ResultUtils.newResult();
    }

    /**
     * 修改
     *
     * @param id  ID
     * @param dto 数据
     * @return Result
     */
    @PutMapping("update/{id}")
    public Result<Void> update(@PathVariable Long id, @Validated @RequestBody D dto) {
        baseService.update(id, dto);
        return ResultUtils.newResult();
    }

    /**
     * 删除
     *
     * @param ids ID 列表
     * @return Result
     */
    @DeleteMapping("delete")
    public Result<Void> delete(@RequestBody Long[] ids) {
        baseService.deleteAll(ids);
        return ResultUtils.newResult();
    }
}