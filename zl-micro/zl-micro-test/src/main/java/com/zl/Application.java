package com.zl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @Author: CaoXiaoLong create on 2017/8/1 16:03.
 */
@SpringBootApplication
@EnableScheduling //启用定时
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
