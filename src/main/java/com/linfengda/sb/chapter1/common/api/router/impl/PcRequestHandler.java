package com.linfengda.sb.chapter1.common.api.router.impl;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.common.api.annotation.RequiresAccessCode;
import com.linfengda.sb.chapter1.common.api.entity.bo.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.entity.enums.ModuleType;
import com.linfengda.sb.chapter1.common.api.router.AbstractRequestHandler;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: PC端Handler
 *
 * @author linfengda
 * @create 2019-12-19 17:55
 */
@Slf4j
public class PcRequestHandler extends AbstractRequestHandler {

    public PcRequestHandler(RequestInfoBO requestInfoBO, HandlerMethod handlerMethod) {
        super(requestInfoBO, handlerMethod);
    }

    @Override
    protected void init() {
        this.moduleType = ModuleType.PC;
    }

    @Override
    public void doHandler() throws Exception {
        RequiresAccessCode requiresAccessCode = getMethodOrClassAnnotation(RequiresAccessCode.class);
        if(null == requiresAccessCode){
            return;
        }
        long startTime = System.currentTimeMillis();
        log.info("PC端接口鉴权开始，ip：{}，url：{}，开始时间: {}", requestInfoBO.getIp(), requestInfoBO.getUrl(), startTime);
        checkSupplier();
        log.info("PC端接口鉴权结束，ip：{}，url：{}，结束时间: {}，总耗时: {}ms", requestInfoBO.getIp(), requestInfoBO.getUrl(), System.currentTimeMillis(), System.currentTimeMillis()-startTime);
    }

    /**
     * 校验权限信息
     * @return {@code true} has permission {@code false} not permission
     */
    private void checkSupplier() {
        // 从缓存中获取权限信息
        List<String> accessCodeList = new ArrayList<>();
        log.info("权限信息：{}", JSON.toJSONString(accessCodeList));
        boolean hasPermission = checkSupplierPermission(accessCodeList);
        if(!hasPermission){
            throw new BusinessException("没有[" + requestInfoBO.getUrl() + "]接口权限！");
        }
    }

    /**
     * 检查供应商角色权限
     * @param accessCodeList
     * @return {@code true} has permission {@code false} not permission
     */
    private boolean checkSupplierPermission(List<String> accessCodeList) {
        RequiresAccessCode requiresAccessCode = getMethodOrClassAnnotation(RequiresAccessCode.class);
        if(null != requiresAccessCode){
            String[] value = requiresAccessCode.value();
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
