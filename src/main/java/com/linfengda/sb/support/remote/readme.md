## 使用类似mybatis mapper接口的方式重构http服务调用
#### 1.对mapper接口进行扫描
###### (1).通过spring IOC bean初始化的后置处理BeanDefinitionRegistryPostProcessor注册ClassPathBeanDefinitionScanner，就可以对指定包路径进行扫描
```  ```
#### 2.为mapper接口建立代理
###### (1).扫描到mapper接口后，注册mapper接口的实例，并通过GenericBeanDefinition#setBeanClass方法为mapper接口实现代理
```  ```
###### (2).GenericBeanDefinition#setBeanClass方法中传入参数FactoryBean接口的实现类，该实现类的getObject方法用以返回mapper接口的代理
```  ```
###### (3).getObject方法返回代理类可以有2种实现，CGLIB或JDK动态代理
```  ```
#### 3.在代理中实现http服务调用逻辑
###### (1).首先解析方法注解和参数注解为元数据，并缓存元数据
```  ```
###### (2).将元数据交给对应的Handler执行，这里可以应用代理模式
```  ```
###### (3).在Handler中执行http服务调用，这里可以应用策略模式区分get/post方法的调用，或区分不同外部系统的调用
```  ```

