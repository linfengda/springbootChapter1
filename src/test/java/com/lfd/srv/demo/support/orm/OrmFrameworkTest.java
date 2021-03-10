package com.lfd.srv.demo.support.orm;

import com.lfd.common.util.JsonUtil;
import com.lfd.srv.demo.Chapter1Application;
import com.lfd.srv.demo.bean.type.SysUserStatusType;
import com.lfd.srv.demo.support.gateway.entity.UserSessionBO;
import com.lfd.srv.demo.support.gateway.session.UserSession;
import com.lfd.srv.demo.support.orm.entity.ConditionParam;
import com.lfd.srv.demo.support.orm.entity.PageResult;
import com.lfd.srv.demo.support.orm.entity.SetValue;
import com.lfd.srv.demo.support.orm.entity.SysUserPO;
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
public class OrmFrameworkTest {
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
        log.info("测试根据主键查询用户信息={}", JsonUtil.toJson(sysUserPO));

        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("phone", "13632109840");
        SysUserPO sysUserPO2 = ormTemplate.get(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询用户信息={}", JsonUtil.toJson(sysUserPO2));
    }

    @Test
    public void testQuery() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", "1");
        List<SysUserPO> sysUserPOList = ormTemplate.query(conditionParam, SysUserPO.class);
        log.info("测试根据条件查询所有用户信息={}", JsonUtil.toJson(sysUserPOList));

        ConditionParam pageParam = new ConditionParam();
        pageParam.add("departmentId", "1");
        pageParam.setPageNo(1);
        pageParam.setPageSize(10);
        PageResult<SysUserPO> pageUserList = ormTemplate.page(pageParam, SysUserPO.class);
        log.info("测试根据条件分页查询用户信息={}", JsonUtil.toJson(pageUserList));
    }

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = Exception.class)
    public void testInsert() throws Exception {
        UserSession.put(UserSessionBO.builder().userId("123").userName("林丰达").build());
        SysUserPO sysUser = SysUserPO.builder().userName("林大大").status(SysUserStatusType.YES.toString()).phone("13632109840").password("123456").build();
        ormTemplate.save(sysUser);
        log.info("测试保存用户信息={}", JsonUtil.toJson(sysUser));
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
        log.info("测试更新用户信息={}", JsonUtil.toJson(sysUser));

        Integer id = 1;
        SetValue setValue = new SetValue();
        setValue.add("password", "123456a");
        ormTemplate.updateByPrimaryKey(SysUserPO.class, setValue, id);
        log.info("测试根据id更新用户信息，id={}", id);
        UserSession.remove();
    }
}
