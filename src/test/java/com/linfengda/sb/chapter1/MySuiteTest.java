package com.linfengda.sb.chapter1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: 组合测试，逐个执行整合进来的单元测试
 *
 * @author linfengda
 * @create 2020-01-13 13:51
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {MySpringbootTest.class})
public class MySuiteTest {
}
