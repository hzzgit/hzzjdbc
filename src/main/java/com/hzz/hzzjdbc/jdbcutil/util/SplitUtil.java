package com.hzz.hzzjdbc.jdbcutil.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class SplitUtil {
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

    public  static String gettableschme(String url){
        try {
            String[] split = url.split("/");
            int index= split[split.length-1].indexOf("?");
            String substring="";
            if(index==-1){
                substring = split[split.length-1];
            }else{
                substring = split[split.length-1].substring(0, split[split.length-1].indexOf("?"));
            }
            return  substring;
        } catch (Exception e) {
           return "";
        }
    }

}
