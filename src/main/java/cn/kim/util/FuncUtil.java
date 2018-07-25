package cn.kim.util;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by 余庚鑫 on 2018/3/26
 * Iterable 的工具类
 */
public class FuncUtil {
    public static <E> void forEach(
            Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }

    public static Map<String, List<Integer>> getElementPositions(List<String> list) {
        Map<String, List<Integer>> positionsMap = Maps.newHashMapWithExpectedSize(16);

        FuncUtil.forEach(list, (index, str) -> {
            positionsMap.computeIfAbsent(str, k -> new ArrayList<>(1)).add(index);
        });

        return positionsMap;
    }

    public static void main(String[] args) throws Exception {
        List<String> list = Arrays.asList("a", "b", "b", "c", "c", "c", "d", "d", "d", "f", "f", "g");

        System.out.println("使用 computeIfAbsent 和 Iterable.forEach：");
        Map<String, List<Integer>> elementPositions = getElementPositions(list);
        System.out.println(elementPositions);
    }
}
