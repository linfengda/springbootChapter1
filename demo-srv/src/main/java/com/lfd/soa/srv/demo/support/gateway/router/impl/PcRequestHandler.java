package com.lfd.soa.srv.demo.support.gateway.router.impl;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.srv.demo.support.gateway.annotation.Permission;
import com.lfd.soa.srv.demo.support.gateway.entity.RequestSessionBO;
import com.lfd.soa.srv.demo.support.gateway.enums.ModuleType;
import com.lfd.soa.srv.demo.support.gateway.router.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 描述: PC端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:55
 */
@Slf4j
public class PcRequestHandler extends AbstractRequestHandler {

    public PcRequestHandler(RequestSessionBO requestSessionBO, HandlerMethod handlerMethod) {
        super(requestSessionBO, handlerMethod);
    }

    @Override
    protected void init() {
        this.moduleType = ModuleType.PC;
    }

    @Override
    public void doHandler() throws Exception {
        Permission permission = getMethodOrClassAnnotation(Permission.class);
        if(null == permission){
            return;
        }
        long startTime = System.currentTimeMillis();
        log.info("PC端接口鉴权开始，url：{}，开始时间: {}", requestSessionBO.getUrl(), startTime);
        checkSupplier();
        log.info("PC端接口鉴权结束，url：{}，结束时间: {}，总耗时: {}ms", requestSessionBO.getUrl(), System.currentTimeMillis(), System.currentTimeMillis()-startTime);
    }

    /**
     * 校验权限信息
     * @return {@code true} has permission {@code false} not permission
     */
    private void checkSupplier() {
        // 从缓存中获取权限信息
        List<String> accessCodeList = getPermission();
        log.info("权限信息：{}", JsonUtil.toJson(accessCodeList));
        boolean hasPermission = checkSupplierPermission(accessCodeList);
        if(!hasPermission){
            throw new BusinessException("没有[" + requestSessionBO.getUrl() + "]接口权限！");
        }
    }

    /**
     * 获取用户权限列表
     * @return  用户权限列表
     */
    private List<String> getPermission() {
        return null;
    }

    /**
     * 检查供应商角色权限
     * @param accessCodeList
     * @return {@code true} has permission {@code false} not permission
     */
    private boolean checkSupplierPermission(List<String> accessCodeList) {
        Permission permission = getMethodOrClassAnnotation(Permission.class);
        if(null != permission){
            String[] value = permission.value();
            //当前登陆供应商拥有的权限列表
            for (String accessCode : value) {
                if(accessCodeList.contains(accessCode)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取方法或者类注解
     * 优先获取方法头部注解，获取不到则尝试获取类头部注解
     * @param clazz
     * @param <T>
     * @return
     */
    private <T extends Annotation> T getMethodOrClassAnnotation(Class<T> clazz){
        T annotation = handlerMethod.getMethod().getDeclaredAnnotation(clazz);
        if(null == annotation){
            //方法不存在  尝试获取类头部权限注解
            annotation = handlerMethod.getBeanType().getAnnotation(clazz);
        }
        return annotation;
    }
}
