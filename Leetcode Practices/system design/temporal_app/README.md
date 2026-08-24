# Temporal App

一个最小可运行的 Temporal Go 示例，用来学习 Worker、Workflow 和 Workflow Starter 的基本关系。

## 文件结构

```text
temporal_app/
├── cmd/
│   ├── starter/
│   │   └── main.go
│   └── worker/
│       └── main.go
├── internal/
│   ├── activity.go
│   ├── config.go
│   └── workflow.go
├── go.mod
└── go.sum
```

## 运行方式

启动本地 Temporal Server：

```bash
temporal server start-dev
```

启动 Worker：

```bash
go run ./cmd/worker
```

另开一个终端，启动 Workflow：

```bash
go run ./cmd/starter
```

预期输出：

```text
Hello, Temporal!
```

## 目录职责

- `cmd/worker`: Worker 进程入口，负责注册 Workflow 并监听 Task Queue。
- `cmd/starter`: Workflow 启动入口，负责发起一次 Workflow Execution。
- `internal/config.go`: 共享配置，例如 Task Queue 名称。
- `internal/workflow.go`: Workflow Definition，描述可靠执行的业务流程。
- `internal/activity.go`: Activity Definition，负责执行具体业务动作。

`internal` 中的代码属于应用内部实现，只由 `cmd/worker` 和 `cmd/starter` 使用，不作为公共 Go 包对外暴露。

## Workflow 和 Activity

这个示例参考 Temporal training exercise Go 仓库的 `solution1`：

- `GreetingWorkflow.Greet`: Workflow 方法，负责编排流程。
- `CreateGreeting`: Activity 方法，负责真正创建问候语。
- `workflow.ExecuteActivity`: 在 Workflow 中调度 Activity。
- `worker.RegisterWorkflow`: 让 Worker 知道有哪些 Workflow 可以执行。
- `worker.RegisterActivity`: 让 Worker 知道有哪些 Activity 可以执行。

Workflow 里会先配置 Activity 的超时时间：

```go
ao := workflow.ActivityOptions{StartToCloseTimeout: 5 * time.Second}
ctx = workflow.WithActivityOptions(ctx, ao)
```

然后调用 Activity：

```go
err := workflow.ExecuteActivity(ctx, CreateGreeting, name).Get(ctx, &result)
```

## Starter 和 Client

`starter` 本质上是一个 Temporal Client 程序。

更准确地说：

- `worker`: 长时间运行，监听 Task Queue，执行 Workflow 或 Activity。
- `starter`: 短生命周期程序，连接 Temporal Server，发起一次 Workflow Execution。
- `client`: Temporal SDK 里的客户端对象，用来和 Temporal Server 通信。

在 `cmd/starter/main.go` 中：

```go
c, err := client.Dial(client.Options{})
```

这段代码创建 Temporal Client。

```go
run, err := c.ExecuteWorkflow(
	context.Background(),
	options,
	(&temporalapp.GreetingWorkflow{}).Greet,
	"Temporal",
)
```

这段代码通过 client 启动 Workflow。

真实项目里，Temporal Client 不一定叫 `starter`，也不一定是独立命令行程序。它可能存在于 HTTP API Server、Cron Job、CLI Tool、Message Consumer 或 Admin Script 中。

关于 Worker 部署方式、Temporal Client 如何集成到业务应用、Temporal Server 和 Worker 的职责边界，见 [Workflow.md](../Workflow.md)。
