package com.linfengda.sb.chapter1.demo;

import com.linfengda.sb.support.util.StringUtil;
import com.linfengda.sb.support.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 描述: 简单的junit单元测试，适用于测试单个方法
 *
 * @author linfengda
 * @create 2020-01-13 15:08
 */
@Slf4j
@RunWith(JUnit4.class)
public class SampleSpringBootTest {

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        log.info("测试工具方法com.linfengda.sb.chapter1.common.util.StringUtil#getRandomStr(), result={}", StringUtil.getRandomStr(10));
        log.info("测试工具方法com.linfengda.sb.chapter1.common.util.StringUtil#getRandomNum(), result={}", StringUtil.getRandomNum(10));

        List<String> strList = StringUtil.string2List("a,b,c", ",");
        assertThat(strList, CoreMatchers.equalTo(Arrays.asList("a","b","c")));

        String str = StringUtil.join(",", "a", "b", "c");
        assertThat(str, CoreMatchers.equalTo("a,b,c"));
    }

    @Test
    public void test2() throws Exception {
        assertThat(TimeUtil.isToday(System.currentTimeMillis()), CoreMatchers.equalTo(Boolean.TRUE));
        assertThat(TimeUtil.isToday(new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-08 12:00:00").getTime()), CoreMatchers.equalTo(Boolean.TRUE));
    }

    @Test
    public void test3() throws Exception {
        assertThat(TimeUtil.format(TimeUtil.parseToDate("2020-06-05 12:00:00", "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"), CoreMatchers.equalTo("2020-06-05 12:00:00"));
    }
}
