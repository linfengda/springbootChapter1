## 使用AOP编程实现Cache框架
[toc]

#### 一. 切面实现
1. 切点
```
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcut
com.linfengda.sb.support.cache.interceptor.UpdateCacheInterceptor
```
2. 增强器
```
com.linfengda.sb.support.cache.interceptor.QueryCacheMethodPointcutAdvisor
com.linfengda.sb.support.cache.interceptor.UpdateCacheMethodPointcutAdvisor
```
3. 拦截器
```
com.linfengda.sb.support.cache.interceptor.QueryCacheInterceptor
com.linfengda.sb.support.cache.interceptor.UpdateCacheInterceptor
```

#### 二. 注解实现
1. sds类型注解
```
com.linfengda.sb.support.cache.annotation.ObjCache
```
2. map类型注解
```
com.linfengda.sb.support.cache.annotation.QueryCache
```
3. 注解元数据
```
com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta
```

#### 三. 需要优化的地方
1. 设计缓存注解，查询缓存和更新缓存分开
2. 查询缓存切面优先级应该在事务切面优先级之前，以便缓存命中时不需要打开多余的事务。更新缓存切面优先级应该在事务切面之后，以保证数据一致性
3. 首先解析方法注解和参数注解为元数据，并缓存元数据
4. 将元数据交给对应的Handler执行，这里可以应用代理模式
5. 在Handler中实现缓存，这里可以应用策略模式实现对不同redis api操作的支持，或实现二级缓存
6. getObject方法返回代理类可以有2种实现，CGLIB或JDK动态代理
7. 过期时间可分级设置，可叠加随机时间
8. 对缓存穿透、缓存击穿、缓存雪崩有控制手段
9. 可限制缓存规模
10. 缓存粒度可拆分
11. 关联加载和关联清除
