https://www.jianshu.com/p/5089318b2e71  
  
## Topcoder 算法题解
  
  
  
## Topcoder 程序设计/工程项目
  
  
  
### Topcoder 使用
<details>
<summary>转自：http://blog.csdn.net/skai_csdn/article/details/6167334</summary>

```
最近找我问TC的朋友越来越多，于是就想写一个入门教程放在这里。对我来说，TC目前是总收入的一个重要组成部分，虽然不如工资多，但是性价比极高。由于我基本只干Design/Development，因此本文只介绍这两种，对于有志于Architecture/Assembly乃至Studio的，我给予精神上支持。另外，我在软件工程方面的知识体系极其山寨，这篇文章应该会有不少错误，请海涵。最后，转载请注明。  
一，准备  
　　虽然说TC的门槛并不高，但是还是有一些基础要求的，基本可以概括为：  
　　基础条件：  
1. 语言：Java/C#其中的一种；  
2. 英语水平：这个我比较没有具体化感受，至少我本人没有碰到过交流障碍，可以算四级以上吧；  
　　以下条件则不那么重要，如果有则事半功倍，毕竟有些学起来也是很快的：  
1. 常用framework：对于Java来说就是Hibernate/Struts/Spring等，对于.net来说就是WCF等；  
2. 企业级应用开发经验：TC的开发模式是借鉴了Agile Programming的，如果对这个有所了解应该能更快入门，当然，我不了解；  
3. UML相关知识：这个应该是必须，但是考虑到学得很快，就放这里了；  
4. 忽悠能力：这点在appeal时尤其重要，后面另说。  
5. 网络情况：出国速度越快越好！（抢review很重要！）  
二，TC的开发流程  
　　TopCoder作为一个老牌的软件外包（Outsourcing，参考http://en.wikipedia.org/wiki/Outsourcing ）公司，在六年多的发展中自己完善了一套开发模式，从一开始只有Design/Development作为比赛开放给member，到现在的一条龙服务，按照开发时间顺序排列如下，括号里是我自己的土鳖翻译：  
　　Conceptualization（概念化）: competitor直接与客户公司代表沟通，确定需求，将需求形成文字；  
　　Specification（规格化）: 根据conceptualization的结果，进一步完善成需求文档（一般称为ARS，　　Application Requirement Specification）；  
　　Architecture（架构）: 根据ARS确定整个系统的架构，并将整个系统打散成多个component，给出每个component的逻辑功能以及对外接口；  
　　Design（设计）: Design的基本单位是component，这一点与上面都不同；Designer需要根据Arch给出的需求文档（RS，Requirement Specification）确定模块的内部逻辑，给出UML图及模块说明文档（CS，Component Specification）；  
　　Development（开发）: Dev的单位同样是component，Developer根据Designer给出的UML图和文档进行实际的代码开发，并且需要自己给出Unit test suite；  
　　Assembly（装配）: 将完成的组件按照archi的要求装配到一起，成为一个（或者一组）可部署的程序；  
　　Test（测试）: 测试，不多说了。  
　　关于Design和Development的细节在后面详述。  
很抱歉这个系列拖了那么久，我会在十一长假期间结束掉。  
三、TC Contest周期  
　　TC的每个Contest都已经形成了标准化流程，以design为例，如下：  
　　Registration：组件在网站上开放注册，一般持续3天至一周，这时可以再Active Contest一栏中看到组件的描述以及需求文档(Requirement Specification)。注册后将可以访问组件专有论坛，上面有进一步的开发资料，以及PM答疑解惑。  
　　Submission：这个阶段起始时间和Registration重叠，比Registration多2-3天。在这个阶段注册的Member可以提交自己的submission，对于design来说就是包含UML图和组件说明文档(Component Specification)的压缩包；  
　　Screening：从这个阶段开始组件在Active Contest中就看不到了，必须进入Contest Status中才能看到。这个阶段primary reviewer会对每份submission进行一个粗略的review，过滤掉特别弱智的，其他submission放入review。（我觉得这个阶段的存在完全是因为reviewer是按照submission份数拿钱的...）  
　　Review：这个阶段3个reviewer对每份submission进行评分，评分的方式是基于一个scorecard，每一项有一定的权重，从最好到最差分为4个等级，最后按100分制算一个总分，3个reviewer给的平均分就是你的初始分，至于为啥不是最终得分，请继续看；  
　　Appeal：这个阶段competitor可以看到每个reviewer给自己的分数，对于认为扣分不妥的可以进行Appeal(貌似应该翻译成"申诉"？反正也别扭)；  
　　Appeal Response：reviewer再次复查被appeal的部分，酌情给分，这是的分数就是最终分数，至此分数最高的就被定为winner了；  
　　Aggregation：primary reviewer对每个reviewer给的评价进行评估，决定是否需要修正；  
　　Aggregation Review：每个reviewer对winner的submission进行复查，查漏补缺；  
　　Final Fix：winner将reviewer指出的纰漏逐一修复，重新提交；  
　　Final Review：primary reviewer对final fix进行review，如果不通过，则退回到Final Fix阶段，如此反复直至通过为止。  
　　其他几个需要注意的：  
　　报酬方面，一般来说一个组件的前2名有钱，第二名是第一名的50%，所以，如果Submission结束后你发现除了你只有一个人提交，那么恭喜你，一般来说你有钱拿了(有一个例外就是你的submission 75分以下，这是不能拿钱的。当然，根据我的经验，想拿75分以下是挺难的…………)  
　　关于Reliability：这是一个衡量一个competitor信用的指标，同时影响到收入；它被定义为过去15次注册的组件中，最后提交并超过75分的比例。如果是100%，则收入有一个20%的bonus，95%-100%的，15%，以此类推，80%以下就没有了。由于有Reliability的存在，小号就显得很必要了…………下面详述；  
　　关于rating：一个表示水平的数字，和钱基本无关，忽略即可，如果有兴趣的可以研究http://www.topcoder.com/wiki/display/tc/Component+Design+Ratings 。  
四、上手  
　　好，说了这么多，该上手试试了。我的建议是先使用一个小号，海量注册（注册即可下载别人的submission），等积累了一定经验有感性认识了以后再开始干活，避免影响Reliability。以design为例：  
　　一、进入http://www.topcoder.com/tc ，点击右上角的Register Now；  
　　二、填写个人信息，由于是小号可以填一些假信息但是注意别太过分…………毕竟这个在道理上是不被允许的，虽然很多人这么干；记得勾上I want to Compete - on TopCoder；  
　　三、好了，你有自己的TC账号了，重新进入http://www.topcoder.com/tc 点击左边Software Design/Active Contests，这里列出了所有处于Submission期间的组建，列出了他们的注册deadline，提交deadline以及报酬；点你感兴趣的之后点击Register即可完成注册；  
　　四、你现在已经注册了一个组件，进入这个组件的页面点击forum可以进入组件论坛，其中Design Documents给出了一些开发文档，Design Questions供开发者提问，会有专职人员(一般是架构师或者PM)进行答疑；  
　　五，当这个组件进入Aggregation后，进入Software Design/Submis & Review界面便可以看到别人提交的压缩包，下载下来学习吧！  
五、Review之路  
　　之所以把Review单独拿出来说，是因为这是我目前赚钱的主要手段。Review相比做组件的特点是收入相对稳定，不需要大块空闲时间，比较适合已经工作的。但是reviewer需要一定的经验，以Design为例，需要做过10个85分以上的组件才有review资格。  
　　一个组件会有3个reviewer，其中一个primary。primary review除了和常规reviewer一样负责review和appeal response之外，还需要负责screening和final review，对应地，primary的收入会多50-60$。  
　　每个组件会在Open之后12小时后开放reviewer的注册，一般来说这3个名额会在几分钟之内被秒杀，因为穷人实在是多。  
六、怎么拿钱  
　　TC的付款方式有三种：支票，电汇和Paypal，手续费从低到高，延时从高到低，一般来说支票需要三个月到半年左右时间到账，但是基本不需要手续费；Paypal可以在一周内到账，但是Paypal转账到国内银行需要收取35$/笔的手续费。考虑到Paypal转账单次最大金额是2500$，并且在银行还要收取一定费用，因此可以看做2%左右的手续费。  
　　第一次拿钱时，需要打印一份Assignment Document，签名扫描后email或者传真给TC，此后就不需要了。以我选择的Paypal支付为例，TC会把每个月的收入在下个月的15日打到Paypal上，然后积攒到2500$后一次汇到国内银行账户上，总的来说延时不超过一周。  
　　总算赶在十一之内草草结束掉，其中各种技巧只能靠大家自己挖掘了。最后说一句，如果大家觉得这篇文章还算有点用，注册TC账号的时候Referer填linwe（我的handle），算是对我的一点肯定吧。  
```
</details>