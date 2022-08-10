package com.kaibutsusama.stock.system.demo.order.catutils;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/9 22:39
 */

public class CatContext implements Cat.Context {
    /**
     * 存储链路相关信息
     */
    private Map<String,String> properties = new HashMap<>();

    /**
     * s = key,s1 = value
     * @param s
     * @param s1
     */
    @Override
    public void addProperty(String s, String s1) {
        properties.put(s,s1);
    }

    @Override
    public String getProperty(String s) {
        return properties.get(s);
    }
}
