package com.linfengda.sb.chapter1.demo;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.order.entity.po.OrderRecordPO;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.support.orm.OrmTemplate;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述: MES ORM框架测试
 *
 * @author linfengda
 * @create 2020-01-13 15:10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ORMFrameworkSpringBootTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Resource
    OrmTemplate ormTemplate;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉@EnableApplicationStartup注解！");
    }

    @Test
    @Rollback(false)
    public void testGet() throws Exception {
        SysUserPO sysUserPO = ormTemplate.getByPrimaryKey(1, SysUserPO.class);
        log.info("测试根据主键查询用户信息={}", JSON.toJSONString(sysUserPO));

        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        SysUserPO sysUserPO2 = ormTemplate.get(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询用户信息={}", JSON.toJSONString(sysUserPO2));
    }

    @Test
    @Rollback(false)
    public void testQuery() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", "1");
        List<SysUserPO> sysUserPOList = ormTemplate.query(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询所有用户信息={}", JSON.toJSONString(sysUserPOList));

        ConditionParam pageParam = new ConditionParam();
        pageParam.add("departmentId", "1");
        pageParam.setPageNo(1);
        pageParam.setPageSize(2);
        PageResult<SysUserPO> pageUserList = ormTemplate.page(pageParam, SysUserPO.class);
        log.info("测试根据条件分页查询用户信息={}", JSON.toJSONString(pageUserList));
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testInsert() throws Exception {
        OrderRecordPO orderRecordPO = new OrderRecordPO();
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        orderRecordPO.setState(1);
        ormTemplate.save(orderRecordPO);
        log.info("测试保存订单信息={}", JSON.toJSONString(orderRecordPO));
    }
}
