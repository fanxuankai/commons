//package com.fanxuankai.commons.extra.mybatis.tree;
//
//import cn.hutool.core.collection.CollectionUtil;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author fanxuankai
// */
//public class ClosureTableUtils {
//    /**
//     * 构建子孙对象
//     *
//     * @param <T>         节点类型
//     * @param id          节点
//     * @param descendants 子孙
//     * @return /
//     */
//    public static <T extends ClosureTable.Node> List<Descendant<T>> buildDescendants(Long id, List<T> descendants) {
//        if (CollectionUtil.isEmpty(descendants)) {
//            return Collections.emptyList();
//        }
//        Map<String, List<T>> groupedByPid =
//                descendants.stream().collect(Collectors.groupingBy(PathEnumerationsUtils::getParentPath));
//        return buildDescendants(id, descendants, groupedByPid);
//    }
//
//    /**
//     * 构建子孙对象
//     *
//     * @param pid          父节点 id
//     * @param descendants  子孙
//     * @param groupedByPid key: 父节点 value: 子节点列表
//     * @param <T>          节点类型
//     * @return /
//     */
//    private static <T extends ClosureTable.Node> List<Descendant<T>> buildDescendants(Long pid,
//                                                                                      List<T> descendants,
//                                                                                      Map<String, List<T>> groupedByPid) {
//        if (CollectionUtil.isEmpty(descendants)) {
//            return Collections.emptyList();
//        }
//        List<T> children = groupedByPid.get(pid);
//        if (CollectionUtil.isEmpty(children)) {
//            return Collections.emptyList();
//        }
//        return children.stream()
//                .map(t -> new Descendant<T>(t, buildDescendants(t.getPath(), groupedByPid.get(getParentPath(t)),
//                        groupedByPid)))
//                .collect(Collectors.toList());
//    }
//}
