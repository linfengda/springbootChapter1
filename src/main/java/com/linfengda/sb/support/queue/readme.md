### rabbitmq消费逻辑扩展

一：目前代码存在的问题
1. 每个业务系统需要配置一个SimpleRabbitListenerContainerFactory实例，配置类中存在冗长的注入代码
2. 每次上线都要运维手动添加队列，容易出错
3. rabbitmq本身没有一套重试方案

二：通过设计规范改善代码
1. 使用@QueueService注解表示外部系统
2. 使用@QueueConsume注解表示消费的队列
3. 框架通过消息落表和异步消费实现了消息的最终一致性