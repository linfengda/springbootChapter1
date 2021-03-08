package com.linfengda.sb.chapter1.demo;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.bean.type.OrderState;
import com.linfengda.sb.support.orm.OrmTemplate;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.PageResult;
import com.linfengda.sb.support.orm.entity.SetValue;
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
import java.math.BigDecimal;
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
    public void testExist() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        boolean exist = ormTemplate.isExist(conditionParam, SysUserIncrementEntity.class);
        log.info("测试根据条件查询是否存在记录，exist={}", exist);
    }

    @Test
    public void testCount() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        long count = ormTemplate.countByParam(conditionParam, SysUserIncrementEntity.class);
        log.info("测试根据条件查询符合条件的总记录数，count={}", count);
    }

    @Test
    public void testGet() throws Exception {
        SysUserIncrementEntity sysUserPO = ormTemplate.getByPrimaryKey(1, SysUserIncrementEntity.class);
        log.info("测试根据主键查询用户信息={}", JSON.toJSONString(sysUserPO));

        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        SysUserIncrementEntity sysUserPO2 = ormTemplate.get(conditionParam, SysUserIncrementEntity.class);
        log.info("测试根据条件查询用户信息={}", JSON.toJSONString(sysUserPO2));
    }

    @Test
    public void testQuery() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", "1");
        List<SysUserIncrementEntity> sysUserPOList = ormTemplate.query(conditionParam, SysUserIncrementEntity.class);
        log.info("测试根据条件查询所有用户信息={}", JSON.toJSONString(sysUserPOList));

        ConditionParam pageParam = new ConditionParam();
        pageParam.add("departmentId", "1");
        pageParam.setPageNo(1);
        pageParam.setPageSize(2);
        PageResult<SysUserIncrementEntity> pageUserList = ormTemplate.page(pageParam, SysUserIncrementEntity.class);
        log.info("测试根据条件分页查询用户信息={}", JSON.toJSONString(pageUserList));
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testInsert() throws Exception {
        ProduceOrderIncrementEntity produceOrderPO = new ProduceOrderIncrementEntity();
        produceOrderPO.setOrderNumber("000001");
        produceOrderPO.setState(OrderState.WAITING_ACCEPT.getCode());
        produceOrderPO.setSku("xxx");
        produceOrderPO.setReferenceSku("xxx");
        produceOrderPO.setPurchasePrice(new BigDecimal(9.99));
        produceOrderPO.setMaterialTypeEnum(1);
        produceOrderPO.setThreeCategoryId("1001");
        produceOrderPO.setSpecialTechnologyTag(1);
        produceOrderPO.setSpecialTechnologyText("印花");
        produceOrderPO.setFirstOrder(1);
        produceOrderPO.setUrgent(1);
        produceOrderPO.setMerchandiser("林大大");
        produceOrderPO.setGroupName("中东站");
        produceOrderPO.setGroupId(1);
        ormTemplate.save(produceOrderPO);
        log.info("测试保存订单信息={}", JSON.toJSONString(produceOrderPO));
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testUpdate() throws Exception {
        ProduceOrderIncrementEntity produceOrderPO = new ProduceOrderIncrementEntity();
        produceOrderPO.setId(1);
        produceOrderPO.setState(OrderState.PRODUCING.getCode());
        ormTemplate.save(produceOrderPO);
        log.info("测试更新订单信息={}", JSON.toJSONString(produceOrderPO));

        Integer id = 1;
        SetValue setValue = new SetValue();
        setValue.add("referenceImage", "www.sldflasflsajkl");
        ormTemplate.updateByPrimaryKey(ProduceOrderIncrementEntity.class, setValue, id);
        log.info("测试根据id更新订单信息，id={}", id);
    }
}
