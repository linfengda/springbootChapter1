## 使用AOP编程实现Cache框架
[toc]

#### 一.缓存注解
```
// 缓存方法返回值
com.linfengda.sb.support.cache.annotation.QueryCache
// 删除查询结果/删除方法对应缓存
com.linfengda.sb.support.cache.annotation.DeleteCache
// 强制查询并缓存方法返回值
com.linfengda.sb.support.cache.annotation.UpdateCache
```
#### 二. 缓存切面
```
// 切点
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcut
// 增强器
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcutAdvisor
// 拦截器
com.linfengda.sb.support.cache.interceptor.QueryCacheInterceptor
```
#### 三. 实现过程
```
// 缓存入口
com.linfengda.sb.support.cache.handler.CacheRouter
// 缓存信息解析
com.linfengda.sb.support.cache.builder.CacheMethodMetaBuilder
// 委派handler进行缓存
com.linfengda.sb.support.cache.handler.CacheHandler
// 根据数据类型使用策略
com.linfengda.sb.support.cache.handler.strategy.CacheStrategy
```

#### 四. 需要优化的地方
```
1.缓存方法切面优先级：查询缓存>删除缓存>事务>更新缓存
2.查询缓存切面优先于事务切面，以便缓存命中时不需要打开多余的事务。更新缓存切面优先级应该在事务切面之后，以保证数据一致性
3.通过设置随机过期时间，防止缓存雪崩
4.通过互斥锁限制只有一个线程更新缓存，防止缓存击穿
5.默认缓存空值，且内置布隆过滤器，防止缓存穿透
6.通过指定缓存数量（maxSize），限制缓存规模：
对于object类型缓存，maxSize为指定前缀的最大缓存数量，
对于hash类型缓存，maxSize为哈希表的最大缓存数量，
对于list类型缓存，maxSize为列表的最大缓存数量，
对于set类型缓存，maxSize为集合的最大缓存数量，
7. 关联加载和关联清除
```