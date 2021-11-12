package com.mapscloud.dtt.loadermanagerdemo.bean;

/**
 * @author TomCan
 * @description:
 * @date :2021/10/14 14:40
 */
public class UserBean {
    public String name;
    public int    age;

    public UserBean() {
    }

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
