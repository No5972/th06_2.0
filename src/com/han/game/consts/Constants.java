package com.han.game.consts;

import java.util.*;

/**
 * 本作地图数据
 */
public class Constants {
    public Map<Integer, Map<String, List<int[]>>> enemies = new HashMap<>(); // 关卡 - 关卡的敌人

    public Constants() {
        Map<String, List<int[]>> stg1 = new HashMap<>();
        stg1.put("stg1AL", Arrays.asList(new int[]{0,360}, new int[]{1300,1500})); // 敌人种类（方法中为循环若干次出现） - 出现范围
        stg1.put("stg1AR", Arrays.asList(new int[]{360,660}, new int[]{1500,1800}));
        stg1.put("stg1ALX", Arrays.asList(new int[]{700,1000}, new int[]{1800,2500}));
        stg1.put("stg1ARX", Arrays.asList(new int[]{700,1000}, new int[]{1800,2500}));
        stg1.put("stg1B", Arrays.asList(new int[]{1000,1300}, new int[]{2100,2500}));
        stg1.put("stg1Boss", Collections.singletonList(new int[]{3300, 3301}));
        enemies.put(1, stg1);
    }
}
