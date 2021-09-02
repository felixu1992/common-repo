package top.felixu.common;

import lombok.Data;
import top.felixu.common.bean.BeanUtils;

/**
 * @author felixu
 * @date 2019.09.24
 */
public class Test {

    public static void main(String[] args) {
        User source = new User();
        source.setName("felixu");
        User target = new User();
        target.setAddress("火星");
        System.out.println(BeanUtils.mapNotNull(User.class, target, User.class));
        BeanUtils.copyNotNull(User.class, source, User.class, target);
        System.out.println(target);
    }

    @Data
    public static class User {
        private String name = "wocao";
        private String address;
    }
}