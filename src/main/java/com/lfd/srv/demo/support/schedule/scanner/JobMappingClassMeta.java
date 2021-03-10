package com.lfd.srv.demo.support.schedule.scanner;

import java.util.HashSet;
import java.util.Set;

/**
 * 定时任务类元数据缓存
 *
 * @author linfengda
 * @date 2021-02-03 16:39
 */
public class JobMappingClassMeta {
    private static final Set<Class<?>> TASK_MAPPER_CLAZZ = new HashSet<>(16);

    public static void addClazz(Class<?> clazz){
        TASK_MAPPER_CLAZZ.add(clazz);
    }

    public static Set<Class<?>> getTaskMapperClazz(){
        return TASK_MAPPER_CLAZZ;
    }
}
