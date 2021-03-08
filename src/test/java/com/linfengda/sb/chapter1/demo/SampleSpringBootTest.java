package com.linfengda.sb.chapter1.demo;

import com.linfengda.sb.chapter1.common.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

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
        String[] stringArray = StringUtils.delimitedListToStringArray("a,b,c", ",");
        assertThat(stringArray, CoreMatchers.equalTo(new String[] {"a","b","c"}));

        String str = StringUtils.arrayToCommaDelimitedString(new Object[] {",", "a", "b", "c"});
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
