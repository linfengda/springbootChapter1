package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Chapter1ApplicationTests {
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Test
    public void runTest() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
