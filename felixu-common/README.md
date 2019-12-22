# 公共包
尽量避免引入第三方包，用以被后续一些项目引入

## bean 包
- BeanUtils：用于 Bean 拷贝，依赖 Dozer
## date 包
- DateFormatUtils：用于格式化时间，内置多种格式化方式
- DateTimeUtils：提供一些通用时间处理方法
## enums 包
- Describable：被前端和后端所使用的枚举的公用父类
## func 包
- ConsumerWrapper：Consumer 函数式接口包装，处理异常
- FunctionWrapper：Function 函数式接口包装，处理异常
- PredicateWrapper：Predicate 函数式接口包装，处理异常
## json 包
- JsonUtils：用于 Json 的序列化与反序列化
## parameter 包
- Joiners：字符串拼接
- Splitters：字符串切割
- valueUtils：对值做一些特定处理