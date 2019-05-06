package com.gouge.template.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GouGe
 * @data 2019/5/6 17:14
 */
public class Sort {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<Student>();
        list.add(new Student("小马", 18, 10000L));
        list.add(new Student("小张", 19, 10000L));
        list.add(new Student("小张", 20, 1000L));
        list.add(new Student("小王", 21, 10000L));
        list.add(new Student("小王", 22, 20000L));
        Map<String, Student> map = new HashMap<String, Student>();
        for (Student s : list) {
            if (map.containsKey(s)) {
                map.put(s.getName(), Student.merge(s, map.get(s)));
            } else {
                map.put(s.getName(), s);
            }
        }
        System.out.println(map.values());
    }
}
