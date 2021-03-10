## 基于注解实现Cache框架
[toc]

#### 一.缓存注解
* 注解在查询方法上，缓存查询结果：QueryCache
* 注解在删除方法上，同步删除对应缓存：DeleteCache，DeleteCaches
* 注解在更新方法上，同步更新对应缓存：UpdateCache

#### 二. 实现过程
* 分别实现查询、删除、更新注解切面，切面优先级：查询>删除>更新
QueryCacheInterceptor
DeleteCacheInterceptor
UpdateCacheInterceptor
* 缓存逻辑入口
CacheMethodHandlerAdapter
* 缓存方法元数据解析
CacheMethodMetaBuilder
* 缓存参数构造
CacheParamBuilder
* 缓存处理
CacheHandler
* 缓存数据类型处理
CacheDataTypeResolver

#### 三. 版本日志
###### 1.关于切面
* 缓存方法切面优先级：查询缓存>删除缓存>事务>更新缓存；
* 查询缓存切面优先于事务切面，以便缓存命中时不需要打开多余的事务。更新缓存切面优先级应该在事务切面之后，以保证数据一致性；
###### 2.缓存稳定策略
* 通过设置随机过期时间，防止缓存雪崩；
* 通过互斥锁限制只有一个线程更新缓存，防止缓存击穿；
* 通过默认缓存空值，防止缓存穿透；
###### 3.缓存最大数量和LRU淘汰算法
* 通过配置缓存最大数量（maxSize），限制缓存规模；
* 对于object类型缓存，maxSize为指定前缀的最大缓存数量，
* 对于hash类型缓存，maxSize为指定前缀哈希表的最大容量，
* 对于list类型缓存，maxSize为指定前缀列表的最大数量，如：myList:{*}大小，
* 对于set类型缓存，maxSize为指定前缀集合的最大数量，如：mySet:{*}大小
* LRU淘汰算法需要配置maxSize和timeOut，否则没有实际业务意义，应用启动后会自动开启一个后台守护线程，清除过期key的lru标识；
###### 4.灵活的使用方式
* 通过@DeleteCaches注解实现多个缓存同时删除；
* 可以为方法同时添加@QueryCache、@UpdateCache、@DeleteCache/@DeleteCaches，在更新一类缓存的同时删除其他类型缓存；