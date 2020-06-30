package com.hzz.hzzjdbc.jdbcutil.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class LoopUtil {

    /**
     * 根据批量数据进行处理
     * @param sourceList
     * @param splitCnt
     * @param comsumer
     * @param <T>
     */
    public static <T> void splitLoop(Collection<T> sourceList, int splitCnt, Consumer<Collection<T>> comsumer) {
        try {
            if (sourceList != null && sourceList.size() > 0 && splitCnt > 0) {
                if (sourceList.size() <= splitCnt) {
                    comsumer.accept(sourceList);
                } else {
                    List<T> sourList = new ArrayList<>();
                    for (T t : sourceList) {
                        sourList.add(t);
                    }
                    for (int i = 0; i < sourceList.size(); i += splitCnt) {
                        if (i + splitCnt > sourceList.size()) {
                            splitCnt = sourceList.size() - i;
                        }
                        List<T> list = sourList.subList(i, i + splitCnt);
                        comsumer.accept(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
