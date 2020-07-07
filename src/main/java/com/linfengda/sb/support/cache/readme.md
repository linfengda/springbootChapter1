## 使用AOP编程实现Cache框架
[toc]

#### 一. 切面实现
```
// 切点
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcut
// 增强器
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcutAdvisor
// 拦截器
com.linfengda.sb.support.cache.interceptor.QueryCacheInterceptor
```

#### 二. 缓存方法注解
```
// 缓存方法返回值
com.linfengda.sb.support.cache.annotation.QueryCache
// 删除查询结果/删除方法对应缓存
com.linfengda.sb.support.cache.annotation.DeleteCache
// 强制查询并缓存方法返回值
com.linfengda.sb.support.cache.annotation.UpdateCache
```

#### 三. 实现过程
```
// 缓存信息解析
com.linfengda.sb.support.cache.parser.CacheMethodParser
// 委派handler进行缓存
com.linfengda.sb.support.cache.handler.impl.QueryCacheHandler
```

#### 三. 需要优化的地方
1. 缓存方法切面优先级：查询缓存--->删除缓存--->事务--->更新缓存
2. 查询缓存，删除缓存切面优先级应该在事务切面优先级之前，以便缓存命中时不需要打开多余的事务。更新缓存切面优先级应该在事务切面之后，以保证数据一致性
3. 首先解析方法注解和参数注解为元数据，并缓存元数据
4. 将元数据委派给对应的Handler执行
5. 在Handler中实现缓存，使用策略模式实现对不同redis api操作的支持
6. 过期时间可分级设置，可叠加随机时间
7. 对缓存穿透、缓存击穿、缓存雪崩有控制手段
8. 可限制缓存规模
9. 缓存粒度可拆分
10. 关联加载和关联清除
