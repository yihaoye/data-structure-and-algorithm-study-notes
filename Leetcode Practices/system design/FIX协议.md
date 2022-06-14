# FIX 协议
https://www.onixs.biz/fix-protocol.html  

金融信息交换 (FIX) 协议是一种开放式电子通信协议，旨在标准化和简化金融服务行业中的电子通信，支持金融实体之间的多种格式和类型的通信，包括交易分配、订单提交、订单更改、执行报告和广告。  

核心 FIX 协议和消息传递规范标准系列由 FIX Trading Community 维护 -- 一个独立的非营利性、行业驱动的标准机构。  

FIX 协议和消息传递标准包括大量首字母缩略词，这些首字母缩略词通常分为以下几类：  
* **[FIX 会话层](https://www.onixs.biz/fix-dictionary/fixt1.1/section_session_protocol.html)**，在FIX 交易对手之间提供可靠、有序、可恢复的通信。从 FIX 5.0 版本开始，会话层与应用层分离。[FIX 传输会话协议 (FIXT)](https://www.onixs.biz/fix-dictionary/fixt1.1/index.html) 是与 FIX 5.0SP2 和更高版本的 FIX 结合使用的应用程序版本独立会话层。[FIX 性能会话层 (FIXP)](https://www.onixs.biz/fixp.html) 标准是一种高性能、高效率、可靠的会话级协议。[FIX 引擎](https://www.onixs.biz/fix-engine.html)实现支持发起者和接受者角色，在[连续序列号系列中的两方之间具有有序消息的双向流](https://ref.onixs.biz/net-core-fix-engine-guide/articles/message-sequence-numbers.html)具有心跳和序列间隙以及重放恢复支持。
* **FIX 应用程序级别的消息传递**，它指定在应用程序登录级别使用的字段和消息。大多数开发人员将使用 [FIX 应用程序级别的消息](https://www.onixs.biz/fix-dictionary/5.0.SP2/msgs_by_category.html)，通过 FIX 引擎的 API 创建和使用与交易对手的基于消息的通信使用中的实现。还值得注意的是，FIX 消息传递标准被设计为可扩展的——这意味着添加 FIX 标记以扩展基于双边或多边使用约定的基本消息标准是常见的做法。这种用法通常在 “FIX 参与规则”（FIX RoE）规范中定义，该规范由相关交易所、投资公司或公用事业公司发布以供交易对手实施。这样的扩展通常被称为 “FIX 字典” FIX 方言变体，它扩展或专门化了基本 FIX 消息传递标准。
* **FIX 编码标准**，例如 “标准” FIX 标签/值对的最常用实现、FIXML XML 编码、[简单二进制编码 (SBE)](https://www.onixs.biz/sbe-decoder-encoder.html) 和 [FIX Adapted for Streaming (FAST)](https://www.onixs.biz/fix-fast-decoder-encoder.html)。FIX 引擎在 API 功能上包括用于处理标签/值对以及 [FIX 到/来自 FIXML 转换器](https://ref.onixs.biz/net-core-fix-engine-guide/articles/fixml-converter.html)、SBE 编码器/解码器和 FAST 编码器/解码器。

还需要注意的是，FIX 标准是技术规范，而不是具体的实现。实际的技术实现被称为 “FIX 引擎”，它支持 FIX 协议会话、应用程序和编码规范。FIX 协议标准的一个主要好处是它们强制连接的对手方的行为，以便来自不同提供商的 FIX 引擎实现是可互操作的。  
In this specific context, reference the [OnixS FIX Engine C++, .NET Framework, .NET Core/.NET 5 and Java implementations](https://www.onixs.biz/fix-engine.html) and the free online [OnixS FIX Dictionary reference](https://www.onixs.biz/fix-dictionary.html) for a quick and easy to use contemporary dictionary of the FIX Protocol standards.  

