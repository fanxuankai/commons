package com.fanxuankai.commons.extra.mybatis.tree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class AdjacencyListUtils {
    /**
     * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
     *
     * @param <T> 节点类型
     * @param dao dao
     * @param id  节点 id
     * @return /
     */
    public static <T extends AdjacencyList.Entity> List<Descendant<T>> descendants(AdjacencyList.Dao<T> dao, Long id) {
        return descendants(dao, id, 2);
    }

    /**
     * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
     *
     * @param <T>   节点类型
     * @param dao   dao
     * @param id    节点 id
     * @param level 相对阶度
     * @return /
     */
    private static <T extends AdjacencyList.Entity> List<Descendant<T>> descendants(AdjacencyList.Dao<T> dao,
                                                                                    Long id, int level) {
        return dao.children(id)
                .stream()
                .map(o -> new Descendant<>(o, descendants(dao, o.getId(), level + 1), level))
                .collect(Collectors.toList());
    }
}
