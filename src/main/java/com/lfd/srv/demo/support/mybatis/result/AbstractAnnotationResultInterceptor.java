package com.lfd.srv.demo.support.mybatis.result;

import com.lfd.srv.demo.support.mybatis.entity.InterceptorClass;
import com.lfd.srv.demo.support.mybatis.entity.InterceptorField;
import com.lfd.srv.demo.support.type.BaseType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自动加载与缓存类信息解析
 *
 * @author linfengda
 * @date 2021-03-05 16:20
 */
public abstract class AbstractAnnotationResultInterceptor<T extends Annotation> implements ResultInterceptor {

    /**
     * 系统属性拦截注解
     */
    private static final List<Class<? extends Annotation>> FIELD_INTERCEPTOR_ANNOTATION_LIST = new ArrayList<>(16);

    /**
     * 保存 class name -> 拦截类类信息
     */
    private static final Map<String, InterceptorClass> NAME_CLASS_MAP = new ConcurrentHashMap<>(128);

    /**
     * @param clazz 需要拦截属性注解
     */
    public AbstractAnnotationResultInterceptor(Class<? extends Annotation> clazz) {
        FIELD_INTERCEPTOR_ANNOTATION_LIST.add(clazz);
    }

    @Override
    public void intercept(Object result) {
        if (result == null) {
            return;
        }
        resolveResult(result);
    }

    /**
     * 处理结果集
     *
     * @param result 结果集对象
     */
    private void resolveResult(Object result) {
        String className = result.getClass().getName();
        // 基础数据类型不可能携带拦截属性注解，直接跳过
        if (BaseType.isBaseDataType(className)) {
            return;
        }
        if (result instanceof List) {
            List<?> resultList = (List<?>) result;
            if (CollectionUtils.isEmpty(resultList)) {
                return;
            }
            if (1 == resultList.size() && null == resultList.get(0)) {
                return;
            }
            for (Object value : resultList) {
                resolveResult(value);
            }
        } else {
            // 没有拦截属性注解的类，直接跳过
            InterceptorClass interceptorClass = getInterceptorClass(result.getClass());
            if (CollectionUtils.isEmpty(interceptorClass.getInterceptorFieldList())) {
                return;
            }
            Class<? extends Annotation> interceptorAnnotation = getInterceptorAnnotation();
            List<InterceptorField> interceptorFields = interceptorClass.getInterceptorFieldList();
            for (InterceptorField interceptorField : interceptorFields) {
                if (!interceptorField.contains(interceptorAnnotation.getName())) {
                    continue;
                }
                Field field = interceptorField.getField();
                Object value = getFieldValue(field, result);
                T annotation = (T) interceptorField.get(interceptorAnnotation.getName());
                interceptorField(result, value, field, annotation);
            }
        }
    }

    /**
     * 获取类型元数据
     * @param clazz 类型
     * @return      类型元数据
     */
    private InterceptorClass getInterceptorClass(Class<?> clazz) {
        InterceptorClass interceptorClass = NAME_CLASS_MAP.get(clazz.getName());
        if (null == interceptorClass) {
            interceptorClass = initInterceptorClass(clazz);
        }
        return interceptorClass;
    }

    /**
     * 加载并缓存拦截类信息
     * @param clazz interceptor class info
     * @return      拦截类类信息
     */
    private static InterceptorClass initInterceptorClass(Class<?> clazz) {
        InterceptorClass interceptorClass = new InterceptorClass();
        interceptorClass.setClazz(clazz);
        initInterceptorFiled(clazz, interceptorClass);
        NAME_CLASS_MAP.put(clazz.getName(), interceptorClass);
        return interceptorClass;
    }

    /**
     * 根据拦截器注册的拦截属性注解
     * 加载需要拦截的属性
     *
     * @param clazz            interceptor class info
     * @param interceptorClass 拦截类类信息
     */
    private static void initInterceptorFiled(Class<?> clazz, InterceptorClass interceptorClass) {
        List<InterceptorField> interceptorFieldList = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, field -> {
            Map<String, Annotation> annotationMap = new HashMap<>(16);
            for (Class<? extends Annotation> fieldAnnotation : FIELD_INTERCEPTOR_ANNOTATION_LIST) {
                Annotation annotation = field.getAnnotation(fieldAnnotation);
                if (null != annotation) {
                    annotationMap.put(fieldAnnotation.getName(), annotation);
                }
            }
            if (!CollectionUtils.isEmpty(annotationMap)) {
                InterceptorField interceptorField = new InterceptorField();
                interceptorField.setField(field);
                interceptorField.setAnnotationMap(annotationMap);
                interceptorFieldList.add(interceptorField);
            }
        });
        interceptorClass.setInterceptorFieldList(Collections.unmodifiableList(interceptorFieldList));
    }

    /**
     * 获取Field value
     *
     * @param field  field
     * @param object 对象
     * @return field value
     */
    protected Object getFieldValue(Field field, Object object) {
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, object);
    }

    /**
     * 设置Field value
     *
     * @param field  field
     * @param target target object
     * @param value  new value
     */
    protected void setFieldValue(Field field, Object target, Object value) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }

    /**
     * 获取需要拦截的属性注解
     * 模版方法，交由子类实现
     *
     * @return 拦截注解
     */
    public abstract Class<? extends Annotation> getInterceptorAnnotation();

    /**
     * 拦截属性处理
     * 模版方法，交由子类实现
     *
     * @param target           拦截对象
     * @param value            拦截属性值
     * @param field            拦截属性
     * @param t                拦截注解
     */
    public abstract void interceptorField(Object target, Object value, Field field, T t);
}
