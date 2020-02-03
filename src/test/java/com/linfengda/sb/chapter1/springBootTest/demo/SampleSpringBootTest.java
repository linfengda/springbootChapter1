package com.linfengda.sb.chapter1.springBootTest.demo;

import com.linfengda.sb.chapter1.common.entity.StringConstant;
import com.linfengda.sb.chapter1.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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

        List<String> strList = StringUtil.string2List("a,b,c", StringConstant.COMMA);
        assertThat(strList, CoreMatchers.equalTo(Arrays.asList("a","b","c")));

        String str = StringUtil.join(StringConstant.COMMA, "a", "b", "c");
        assertThat(str, CoreMatchers.equalTo("a,b,c"));
    }
}
