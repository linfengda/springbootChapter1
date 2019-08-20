package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter1ApplicationTests {

    @Test
    public void runTest() {
        try {
            //System.out.println(new BigDecimal("0.57").setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());

            Integer i1 = new Integer(0);
            Integer i2 = new Integer(0);
            System.out.println(100.00f*i1.floatValue()/i2.floatValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
