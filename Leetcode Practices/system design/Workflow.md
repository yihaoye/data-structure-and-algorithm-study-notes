# Workflow
工作流或工作流编排系统是对工作流程及其操作步骤、业务规则的抽象与自动化描述。它将一连串的任务、数据和人按照特定的逻辑顺序组织起来，让复杂的业务或计算过程在计算机或 AI 的帮助下自动、顺畅地执行。  

AI Agent 的执行天然就是 Workflow。  

## Workflow 和普通 API 的区别
普通 API：Request -> Service -> DB -> Response  
通常生命周期只有几秒。

Workflow 引擎、框架：Activity A -> Activity B -> Wait 3 days -> Activity C -> Activity D  
它甚至可以睡 N 天，然后自动醒过来继续执行。而且不需要基于底层组件（Redis delayed queue + Kafka + DB state + cron + retry table + worker）自建开发实现上面这些复杂功能，引擎、框架（Temporal）已经帮开发者把这些可靠性机制抽象掉。  

Workflow 不是新的概念，已经在业界应用多年，随着微服务的新起而迎来了新的挑战 - 各类分布式系统问题，因此涌现了一些新的引擎、框架系统来解决这些问题：
```
Distributed Business Process
│
└── Workflow
    │
    ├── Orchestration
    │   ├── Temporal
    │   ├── Camunda
    │   └── AWS Step Functions
    │
    └── Choreography
        └── Event-driven / Saga
```

## Temporal
是一个分布式工作流编排 / Durable Execution（持久化执行）系统。把一个跨服务、跨时间、可能执行几天甚至几个月的业务流程，写成一段代码，然后 Temporal 保证它最终能够可靠地执行完。[Temporal 101](https://temporal.talentlms.com/plus/my/training/126/units/2101)  



// TBC...

