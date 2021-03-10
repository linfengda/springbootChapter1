package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.annotation.JobController;
import com.lfd.soa.srv.demo.support.schedule.annotation.JobMapping;
import com.lfd.soa.srv.demo.support.schedule.task.bean.JobCell;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 定时任务注解扫描{@link JobMapping}
 *
 * @author linfengda
 * @date 2021-02-03 16:33
 */
@Slf4j
public class JobMappingScanner {
    //private BaseScheduleService baseScheduleService;

    public JobMappingScanner() {
        //this.baseScheduleService = SpringUtil.getBean(BaseScheduleService.class);
    }

    /**
     * 扫描定时任务
     *
     * @return 定时任务Map
     */
    public Map<String, JobCell> scanJob() {
        /*// 系统扫描到的定时任务
        Map<String, JobCell> jobMap = loadJobCell(JobMappingClassMeta.getTaskMapperClazz());
        // 已经持久化的定时任务
        Map<String, BaseSchedule> baseScheduleMap = querySchedule();
        for (Map.Entry<String, JobCell> entry : jobMap.entrySet()) {
            String jobName = entry.getKey();
            JobCell jobCell = entry.getValue();
            if (baseScheduleMap.containsKey(jobName)) {
                BaseSchedule baseSchedule = baseScheduleMap.get(jobName);
                syncJob(jobCell, baseSchedule);
                baseSchedule.setUpdateUid("system");
                baseSchedule.setUpdateUname("system");
                baseSchedule.setUpdateTime(new Date(System.currentTimeMillis()));
                baseScheduleService.updateById(baseSchedule);
            } else {
                BaseSchedule baseSchedule = new BaseSchedule();
                syncJob(jobCell, baseSchedule);
                baseSchedule.setCreateUid("system");
                baseSchedule.setCreateUname("system");
                baseSchedule.setCreateTime(new Date(System.currentTimeMillis()));
                baseScheduleService.save(baseSchedule);
            }
        }
        for (Map.Entry<String, BaseSchedule> baseScheduleEntry : baseScheduleMap.entrySet()) {
            if (!jobMap.containsKey(baseScheduleEntry.getKey())) {
                baseScheduleService.removeById(baseScheduleEntry.getValue().getId());
            }
        }
        return jobMap;*/
        return null;
    }

    /**
     * 扫描{@link JobMapping}
     *
     * @param classes {@link JobController}
     * @return JobMapping列表
     */
/*    private Map<String, JobCell> loadJobCell(Set<Class<?>> classes) {
        Map<String, JobCell> jobMap = new HashMap<>(32);
        for (Class<?> clazz : classes) {
            Object jobTarget = SpringUtil.getBean(clazz);
            Method[] methods = clazz.getMethods();
            for (Method jobMethod : methods) {
                JobCell jobCell = new JobCell();
                JobMapping jobMapping = jobMethod.getAnnotation(JobMapping.class);
                if (jobMapping != null) {
                    log.info("JobMapped：[name=" + jobMapping.name() + ",desc=" + jobMapping.desc() + ",cron=" + jobMapping.cron() + ",class=" + clazz.getName() + ",method=" + jobMethod.getName() + ",moduleType=" + jobMapping.moduleType() + ",distribute=" + jobMapping.distribute() + "]");
                    jobCell.setTarget(jobTarget);
                    jobCell.setMethod(jobMethod);
                    jobCell.setTargetClassName(clazz.getName());
                    jobCell.setTargetMethodName(jobMethod.getName());
                    jobCell.setName(jobMapping.name());
                    jobCell.setDesc(jobMapping.desc());
                    jobCell.setCron(jobMapping.cron());
                    jobCell.setModuleType(jobMapping.moduleType());
                    jobCell.setDistribute(jobMapping.distribute());
                    jobMap.put(jobMapping.name(), jobCell);
                }
            }
        }
        return jobMap;
    }

    *//**
     * 查询定时任务列表
     *
     * @return 定时任务列表
     *//*
    private Map<String, BaseSchedule> querySchedule() {
        LambdaQueryWrapper<BaseSchedule> queryWrapper = new LambdaQueryWrapper<>();
        List<BaseSchedule> baseScheduleList = baseScheduleService.list(queryWrapper);
        return baseScheduleList.stream().collect(Collectors.toMap(BaseSchedule::getJobName, s -> s, (k1, k2) -> k1));
    }

    *//**
     * 同步job信息
     *
     * @param jobCell      job注解信息
     * @param baseSchedule job持久化对象
     *//*
    private void syncJob(JobCell jobCell, BaseSchedule baseSchedule) {
        baseSchedule.setJobName(jobCell.getName());
        baseSchedule.setJobDesc(jobCell.getDesc());
        baseSchedule.setCron(jobCell.getCron());
        baseSchedule.setClassName(jobCell.getTargetClassName());
        baseSchedule.setMethodName(jobCell.getTargetMethodName());
        baseSchedule.setModuleType(jobCell.getModuleType());
        baseSchedule.setState(JobState.active);
    }*/
}
