package com.linfengda.sb.support.cache.handler;

import com.lhdf.eserve.support.converter.entity.dto.MapperDataDTO;
import com.lhdf.eserve.support.converter.manager.MapperHandlerManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 描述: 缓存操作路由（使用委派模式设计）
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public enum MapperRouter {
    /**
     * 路由
     */
    INSTANCE;

    public List<?> doMapper(MapperDataDTO mapperDataDTO) {
        try {
            DataMapperHandler handler = MapperHandlerManager.provide(mapperDataDTO);
            if (null == handler) {
                log.error("上数据没有找到对应的映射处理器，uploadType={}，mappingId={}", mapperDataDTO.getUploadType(), mapperDataDTO.getMappingId());
                return null;
            }
            return handler.doMapper();
        }catch (Exception e) {
            log.error("上数据映射失败", e);
        }
        return null;
    }
}
