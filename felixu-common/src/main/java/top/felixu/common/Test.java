package top.felixu.common;

import top.felixu.common.utils.DateFormatUtils;

import java.time.LocalDateTime;

/**
 * @author felixu
 * @date 2019.09.24
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(DateFormatUtils.DEFAULT.format(LocalDateTime.now()));
    }
}
