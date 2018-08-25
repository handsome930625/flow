## Flow

`taskflow` `workflow`
## 1.taskflow 
 taskflow 作用是把一个操作分解成多个步骤执行，比如创建订单需要 
 * 1.获取商品信息
 * 2.校验购买的商品，信息是否合法
 * 3.使用库存
 * 4.使用优惠手段 
 * 5.创建订单 （普通订单，VIP订单等等）
 * 6.创建支付渠道（微信，支付宝等等）。
 
 如果这些步骤都杂糅在一起会使代码变得非常臃肿，每个层级的步骤又会产生不通的分支，代码维护其他就比较麻烦。
 
 所以taskflow的诞生就是解决这个问题
 
 ### 快速开始
 `定义xml` `构造任务方法类`
 
 #### 定义xml


```xml
<?xml version="1.0"?>
<taskflows xmlns="http://www.charse.com.cn"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.charse.com.cn taskflows.xsd">
    <taskflow id="productReserve" start-task="first-task" use-spring="false" validate="true">
        <filters>
            <filter class-name="com.charse.test.local.FirstFilter"/>
        </filters>
        <task task-id="first-task">
            <invoke class-name="com.charse.test.local.FirstTask"/>
            <results>
                <result road="R1" next-stop="second-task"/>
            </results>
        </task>
        <task task-id="second-task">
            <invoke class-name="com.charse.test.local.SecondTask"/>
            <results>
                <result road="R1" next-stop="third-task"/>
            </results>
        </task>
        <task task-id="third-task">
            <invoke class-name="com.charse.test.local.ThirdTask"/>
        </task>
    </taskflow>
</taskflows>
```

上图是不基于 spring ioc 容器配置方式，所有的filter 和 task由用户自行管理

* 1.taskflow 属性 use-spring 决定是否使用spring 容器，如果标记为使用，则以下节点所涉及的class 必须填写bean-id属性。
将会根据bean-id从spring容器取对象。并且 com.charse.taskflow.utils.SpringBeanHelper 这个class必须交给spring ioc 容器管理。validate 
属性建议在开发的时候打开，这边启动的时候会验证xml配置是否正确，跳转分支是否可达。
* 2.filter 节点填写的对象必须是实现 com.charse.taskflow.filter.Filter 接口,可以配置多个filter节点，没有需求可以不用定义，这个节点可以用过扩展分布式事务。
* 3.task 是方法任务节点，多少个任务配置多少个节点。task-id属性值必须是当前taskflow中的唯一值，否则会发生覆盖。  
* 4.invoke 是用来配置任务所在的全类名（class-name）或者在spring ioc 中的beanId(bean-id)，配置的类必须实现 com.charse.taskflow.flow.ITaskHandler 
泛型接口。
* 5.result 假设这个任务不是最后一个，那么必须配置 results 节点，底下的 result 节点可以配置多个。road 
属性对应着本task执行handle方法的返回值，如果匹配到了则会选择next-stop配置的task，跳到对应的task执行handle方法，如此反复，直到运行到没有result节点的task算任务结束

 #### 定义测试类
 将项目clone下来，查看taskflow项目下test目录下 com.charse.test.local.XmlBuilderTest 测试类
 
 #### 测试代码流程图
 ![local-taskflow](http://thyrsi.com/t6/362/1535186197x-1404817850.png)
 
 #### 内置方法
 
 TaskFlowContext 对象
 * 1.addStepReturnValue 方法接受 key ： value，其中key 为最终任务返回值的字段名，value 为字段的值
 * 2.writeNextTaskParameter 方法是作为为下一个task写入方法参数，如果在 
 taskHandler中的handle方法没用调用此方法，那么会选用上一个taskHandler写入的值。这点需要注意参数的数据类型是否匹配下一个task的入参类型。
 
 ITaskHandler 接口
 * 1.该接口是作为每一个任务方法必须要实现的接口，其中handle方法的返回值决定下一个task的走向。
 * 2.返回值匹配支持spel表达式(不懂的小伙伴可以百度)。
    * 2.1. result节点以\#W 开头代表从 return 的对象中取值
    * 2.2. result节点以\#P 开头代表从 TaskFlowContext.writeNextTaskParameter 写入的对象中取值
    * 2.3. result节点以\#C 开头代表从 TaskFlowContext 对像中取值 
 
 