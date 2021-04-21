package com.fanxuankai.commons.core.util;

import com.github.jsonzou.jmockdata.JMockData;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fanxuankai
 */
public class MockUtils extends JMockData {

    /**
     * Mock Data
     *
     * @param startInclusive 含头
     * @param endExclusive   不含尾
     * @param clazz          指定要生成的类型
     * @param <T>            the item type
     * @return List
     */
    public static <T> List<T> mock(int startInclusive, int endExclusive, Class<T> clazz) {
        return IntStream.range(startInclusive, endExclusive)
                .mapToObj(o -> JMockData.mock(clazz))
                .collect(Collectors.toList());
    }
}
