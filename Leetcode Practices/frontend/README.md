Leetcode 目前没考前端，只是在本路径下一致存放一些通用面试相关知识点  
  
前端如 React 等框架也有使用算法和计算机基础知识（Computer System Layer 及设计模式）的地方，比如 Virtual DOM  
> React creates a tree of custom objects representing a part of the DOM. For example, instead of creating an actual DIV element containing a UL element, it creates a React.div object that contains a React.ul object. It can manipulate these objects very quickly without actually touching the real DOM or going through the DOM API. Then, when it renders a component, it uses this virtual DOM to figure out what it needs to do with the real DOM to get the two trees to match.  
> You can think of the virtual DOM like a blueprint. It contains all the details needed to construct the DOM, but because it doesn't require all the heavyweight parts that go into a real DOM, it can be created and changed much more easily.

参考自：https://stackoverflow.com/a/21965987/6481829  
  
其中在对 Virtual DOM 的树与真实 DOM 的树的对比过程中包含了算法的技巧。而 Virtual DOM 的树数据结构则存放在内存中（与 Computer System Layer 相关）。  
