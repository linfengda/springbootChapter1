package com.linfengda.sb.chapter1.demo;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.bean.type.SysUserStatusType;
import com.linfengda.sb.chapter1.bean.vo.UserVO;
import com.linfengda.sb.chapter1.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 描述: 完整的API接口测试
 *
 * @author linfengda
 * @create 2020-01-14 10:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompleteApiSpringBootTest extends BaseMockMvcSpringBootTest {
    //使用mock包装的bean，对bean调用的方法进行模拟
    @MockBean
    private SysUserService sysUserService;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉@EnableApplicationStartup注解！");
    }

    /**
     * 测试一个API方法
     * @throws Exception
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void test1() throws Exception {
        // 模拟方法返回数据
        UserVO userVO = new UserVO();
        userVO.setUserId(1);
        userVO.setUserName("模拟数据");
        userVO.setPhone("13632109840");
        userVO.setStatus(SysUserStatusType.YES.getCode());
        // 模拟service方法调用
        Mockito.doReturn(userVO).when(sysUserService).getUserInfo(1);
        // 执行请求
        MockHttpServletResponse response = super.getMockHttpResponse("{\"userId\":\"1\"}", "/pc/sys/getUserInfo");
        log.info("response={}", response.getContentAsString());
        assertThat(response.getContentAsString(), CoreMatchers.containsString("林丰达"));
    }

    /**
     * 测试模块中一系列关联的API方法
     * @throws Exception
     *//*
    @Test
    @Rollback(true)
    @Transactional(rollbackFor = Exception.class)
    public void test2() throws Exception {
        log.info("查询物料卡列表有没有数据返回--------------------------------------------------------------------------");
        JSONObject requestJson = new JSONObject();
        requestJson.put("factoryIds", Arrays.asList(6480723609421615183L));
        requestJson.put("type", 2);
        requestJson.put("status", SampleRecordPO.Status.TO_BE_BORROW.getValue());
        requestJson.put("pageNo", 1);
        requestJson.put("pageSize", 30);
        requestJson.put("token", token);
        MockHttpServletResponse response = super.getMockHttpResponse(requestJson.toJSONString(), "/sample/dashboard/list");
        PageInfo<SampleDashboardVO> pageInfo = JSONObject.parseObject(response.getContentAsString()).getObject(Constant.INTO_KEY, PageInfo.class);
        assertThat(pageInfo, CoreMatchers.anything("sampleRecordId"));

        log.info("ok，列表有数据返回，现在来模拟外部系统用户（PIMS）来推送物料卡清单--------------------------------------------------------------------------");
        // 此时PIMS环境不可用，于是让jumpKey鉴权方法返回模拟数据
        ExternalUserInfoDTO externalUserInfo = initMockitoExternalUserDTO();
        Mockito.doReturn(externalUserInfo).when(pimsData).getExternalUserInfo(Mockito.anyString());
        response = super.getMockHttpResponse( "{\"systemName\": \""+ ExternalSystemEnum.PIMS.getName() +"\", \"jumpKey\": \"xxx\"}", "/system/externalSystemLogin");
        LoginInfo loginInfo = JSONObject.parseObject(response.getContentAsString()).getObject(Constant.INTO_KEY, LoginInfo.class);

        response = super.getMockHttpResponse("{\"token\":\""+ loginInfo.getToken() +"\"}", "/sample/dashboard/pushMaterialCard");
        String result = JSONObject.parseObject(response.getContentAsString()).getObject(Constant.INTO_KEY, String.class);
        assertThat(result, CoreMatchers.equalTo("推送成功！请在企业微信中查看"));

        log.info("ok，推送成功，现在调用接口获取推送详情--------------------------------------------------------------------------");
        MaterialCardRobotMsgDetailsVO detailsVO = sampleDashboardService.getRobotMsgDetails(SessionContext.getCurrentUserId().intValue(), SessionContext.getPimsProduceMerchandiserJobNumber(), "2020-01-14");
        log.info("detailsVO={}", JSON.toJSONString(detailsVO));
    }

    *//**
     * 获取模拟数据
     * @return
     *//*
    private ExternalUserInfoDTO initMockitoExternalUserDTO() {
        ExternalUserInfoDTO externalUserInfo = new ExternalUserInfoDTO();
        externalUserInfo.setUserName("章建华");
        externalUserInfo.setProduceMerchandiserJobNumber("10006603");
        List<Integer> supplierIdList = new ArrayList<>();
        supplierIdList.add(5987);
        externalUserInfo.setSupplierIdList(supplierIdList);
        return externalUserInfo;
    }*/
}
