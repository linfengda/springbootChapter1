package com.lfd.soa.srv.demo.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 描述: MockMvc测试基类
 *
 * @author linfengda
 * @create 2020-01-15 09:19
 */
@Slf4j
public class BaseMockMvcSpringBootTest {
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    /**
     * 初始化MockMvc
     * @throws Exception
     */
    protected void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 获取Mock Http返回数据
     * @param content       请求头
     * @param url           url
     * @return
     * @throws Exception
     */
     protected MockHttpServletResponse getMockHttpResponse(String content, String url) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(content);
        return mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
    }
}
