## 简单实现ORM框架
[toc]

#### 一.实现方式
* 基于反射映射数据库字段
* 基于@Table，@Id等注解标注PO和主键
* 基于java类型和数据库类型的映射封装PO

#### 二. 支持
1. 新增
* 单个插入
* 批量插入
* 支持返回数据库自增主键
2. 更新
* 支持根据主键更新
* 支持根据条件更新
* 支持对指定字段的更新
3. 删除
* 支持根据主键删除
* 支持根据条件删除
* 提供逻辑删除方法
4. 查询
* 查询单条记录，get开头方法
* 查询所有记录，query开头方法
* 支持分页查询，page开头方法
* 支持返回指定字段

#### 三. 优化日志
1. 支持PO反射缓存
2. 支持对不同时间字段的封装
3. 支持新增、修改PO时自动使用默认值
4. 对BasePO的扩展支持

#### 四. 对null值的处理策略
1. 保存po方法：不保存null的字段，理由是如果数据库字段not null，保存null会抛错，如果数据库字段可以为null，但是有默认值，请让默认值生效，如果数据库可以为null，且没有默认值，不插入null值也是null，没必要。
2. 更新po方法：不更新null的字段，理由是如果数据库字段not null，更新null会抛错，如果数据库字段可以为null，但是有默认值，但是使用者想把它更新为null，请使用setValue的方式更新。