## 使用AOP编程实现Cache框架
[toc]

#### 一.缓存注解
* 注解在查询方法上，缓存查询结果
com.linfengda.sb.support.redis.cache.annotation.QueryCache
* 注解在删除方法上，同步删除对应缓存
com.linfengda.sb.support.redis.cache.annotation.DeleteCache
* 注解在更新方法上，同步更新对应缓存
com.linfengda.sb.support.redis.cache.annotation.UpdateCache

#### 二. 缓存切面
* 切点
com.linfengda.sb.support.redis.cache.interceptor.QueryCacheMethodPointcut
* 增强器
com.linfengda.sb.support.redis.cache.interceptor.QueryCacheMethodPointcutAdvisor
* 拦截器
com.linfengda.sb.support.redis.cache.interceptor.QueryCacheInterceptor

#### 三. 实现过程
* 缓存入口
com.linfengda.sb.support.redis.cache.handler.CacheMethodHandlerAdapter
* 缓存方法解析
com.linfengda.sb.support.redis.cache.builder.CacheMethodMetaBuilder
* 缓存信息解析
com.linfengda.sb.support.redis.cache.builder.CacheParamBuilder
* 具体缓存处理
com.linfengda.sb.support.redis.cache.handler.CacheHandler
* 具体缓存类型处理
com.linfengda.sb.support.redis.cache.handler.resolver.CacheDataTypeResolver

#### 四. 需要优化的地方
1. 缓存方法切面优先级：查询缓存>删除缓存>事务>更新缓存
2. 查询缓存切面优先于事务切面，以便缓存命中时不需要打开多余的事务。更新缓存切面优先级应该在事务切面之后，以保证数据一致性
3. 通过设置随机过期时间，防止缓存雪崩
4. 通过互斥锁限制只有一个线程更新缓存，防止缓存击穿
5. 默认不缓存空值，如果需要缓存空值，请手动缓存/查询key是否存在
6. 通过指定缓存数量（maxSize），限制缓存规模：
    * 对于object类型缓存，maxSize为指定前缀的最大缓存数量，
    * 对于hash类型缓存，maxSize为哈希表的最大缓存数量，
    * 对于list类型缓存，maxSize为指定类型列表的最大缓存数量，
    * 对于set类型缓存，maxSize为指定类型集合的最大缓存数量，
7. 关联加载和关联清除