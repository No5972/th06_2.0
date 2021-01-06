package com.han.game.consts;

import java.util.*;

/**
 * ������ͼ����
 */
public class Constants {
    public Map<Integer, Map<String, List<int[]>>> enemies = new HashMap<>(); // �ؿ� - �ؿ��ĵ���

    public Constants() {
        Map<String, List<int[]>> stg1 = new HashMap<>();
        stg1.put("stg1AL", Arrays.asList(new int[]{0,360}, new int[]{1300,1500})); // �������ࣨ������Ϊѭ�����ɴγ��֣� - ���ַ�Χ
        stg1.put("stg1AR", Arrays.asList(new int[]{360,660}, new int[]{1500,1800}));
        stg1.put("stg1ALX", Arrays.asList(new int[]{700,1000}, new int[]{1800,2500}));
        stg1.put("stg1ARX", Arrays.asList(new int[]{700,1000}, new int[]{1800,2500}));
        stg1.put("stg1B", Arrays.asList(new int[]{1000,1300}, new int[]{2100,2500}));
        stg1.put("stg1Boss", Collections.singletonList(new int[]{3300, 3301}));
        enemies.put(1, stg1);
    }
}
