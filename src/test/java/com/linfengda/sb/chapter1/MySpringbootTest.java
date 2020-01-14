package com.linfengda.sb.chapter1;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.common.cache.OrganizeCache;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import com.linfengda.sb.support.dao.OrmTemplate;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * 描述: springboot单元测试
 *
 * @author linfengda
 * @create 2020-01-10 14:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MySpringbootTest {
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    private OrmTemplate ormTemplate;

    @Autowired
    private SysOrganizeService sysOrganizeService;
    //使用mock包装的bean，对bean调用的方法进行模拟
    @MockBean
    private OrganizeCache organizeCache;
    //使用mock包装的bean，对bean调用的方法进行模拟
    @MockBean
    private SysUserService sysUserService;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void daoTest() throws Exception {
        SysUserPO sysUserPO = ormTemplate.findByPrimaryKey(1, SysUserPO.class);
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getUserId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setPassword(sysUserPO.getPassword());
        userVO.setStatus(sysUserPO.getStatus());
        log.info("查询用户信息={}", JSON.toJSONString(userVO));
    }

    @Test
    public void serviceTest() throws Exception {
        // 假设未连接redis，模拟缓存方法调用
        Mockito.doNothing().when(organizeCache).clearCache();
        sysOrganizeService.delDepartment(123);
        sysOrganizeService.delTeam(456);
    }

    @Test
    public void controllerTest() throws Exception {
        // 模拟方法返回数据
        UserVO userVO = new UserVO();
        userVO.setUserId(1);
        userVO.setUserName("模拟数据");
        userVO.setPhone("13632109840");
        userVO.setPassword("123456");
        userVO.setStatus(SysUserPO.Status.YES.getCode());
        // 模拟service方法调用
        Mockito.doReturn(userVO).when(sysUserService).getUserInfo(1);
        // 构建请求
        String content="{\"userId\":\"1\"}";
        // 执行请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/pc/sys/getUserInfo").contentType(MediaType.APPLICATION_JSON).content(content);
        MockHttpServletResponse response = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
        log.info("response={}", response.getContentAsString());
        assertThat(response.getContentAsString(), CoreMatchers.containsString("林丰达"));
    }
}
