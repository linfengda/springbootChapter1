package com.linfengda.sb.chapter1.demo;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.bean.entity.SysUserPO;
import com.linfengda.sb.chapter1.bean.type.SysUserStatusType;
import com.linfengda.sb.support.gateway.entity.UserSessionBO;
import com.linfengda.sb.support.gateway.session.UserSession;
import com.linfengda.sb.support.orm.OrmTemplate;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.PageResult;
import com.linfengda.sb.support.orm.entity.SetValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ORMFrameworkSpringBootTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Resource
    OrmTemplate ormTemplate;


    @Test
    public void testExist() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        boolean exist = ormTemplate.isExist(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询是否存在记录，exist={}", exist);
    }

    @Test
    public void testCount() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        long count = ormTemplate.countByParam(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询符合条件的总记录数，count={}", count);
    }

    @Test
    public void testGet() throws Exception {
        SysUserPO sysUserPO = ormTemplate.getByPrimaryKey(1, SysUserPO.class);
        log.info("测试根据主键查询用户信息={}", JSON.toJSONString(sysUserPO));

        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        SysUserPO sysUserPO2 = ormTemplate.get(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询用户信息={}", JSON.toJSONString(sysUserPO2));
    }

    @Test
    public void testQuery() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", "1");
        List<SysUserPO> sysUserPOList = ormTemplate.query(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询所有用户信息={}", JSON.toJSONString(sysUserPOList));

        ConditionParam pageParam = new ConditionParam();
        pageParam.add("departmentId", "1");
        pageParam.setPageNo(1);
        pageParam.setPageSize(10);
        PageResult<SysUserPO> pageUserList = ormTemplate.page(pageParam, SysUserPO.class);
        log.info("测试根据条件分页查询用户信息={}", JSON.toJSONString(pageUserList));
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testInsert() throws Exception {
        UserSession.put(UserSessionBO.builder().userId("123").userName("林丰达").build());
        SysUserPO sysUser = SysUserPO.builder().userName("林大大").status(SysUserStatusType.YES.toString()).phone("13632109840").password("123456").build();
        ormTemplate.save(sysUser);
        log.info("测试保存用户信息={}", JSON.toJSONString(sysUser));
        UserSession.remove();
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testUpdate() throws Exception {
        UserSession.put(UserSessionBO.builder().userId("123").userName("林丰达").build());
        SysUserPO sysUser = new SysUserPO();
        sysUser.setId(1);
        sysUser.setPassword("6789");
        ormTemplate.save(sysUser);
        log.info("测试更新用户信息={}", JSON.toJSONString(sysUser));

        Integer id = 1;
        SetValue setValue = new SetValue();
        setValue.add("password", "123456a");
        ormTemplate.updateByPrimaryKey(SysUserPO.class, setValue, id);
        log.info("测试根据id更新用户信息，id={}", id);
        UserSession.remove();
    }
}
