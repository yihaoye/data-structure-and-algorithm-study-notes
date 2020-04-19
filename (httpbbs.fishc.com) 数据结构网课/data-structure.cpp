//数据结构与算法(http://bbs.fishc.com)


//lecture 01-05 
//general introduction，理论介绍，时间复杂度、空间复杂度的概念与解析


//lecture 06
/*
	ADT 抽象数据类型名
	Data 
		数据元素之间逻辑关系
	Operation
		操作
			InitList(*L)：初始化操作，建立一个空的线性表L。
			ListEmpty(L)：判断线性表是否为空表，若线性表为空，返回true，否则返回false。
			ClearList(*L)：将线性表清空。
			GetElem(L,i,*e)：将线性表L中的第i个位置元素值返回给e。
			LocateElem(L,e)：在线性表中查找与给定e值相等的元素，如果查找成功，
							返回该元素在表中序号表示成功；否则，返回0表示失败。
			ListInsert(*L,i,e):在线性表L中第i个位置插入新元素e。
			ListDelete(*L,i,*e)：删除线性表L中第i个位置元素，并用e返回其值。
			ListLength(L)：返回线性表L的元素个数。
			还有其他操作等等...
	endADT
*/


//lecture 07

//7.1 合并两个List(没有重复元素)
	void unionL(List *La, List Lb)
	{
		int La_len, Lb_len, i;
		
		ElemType e;
		La_len = ListLength(*La);
		Lb_len = ListLength(Lb);
		
		for(i=1; i <= Lb_len; i++)
		{
			GetElem(Lb, i, *e);
			if(!LocateElem(*La, e))
			{
				ListInsert(La, ++La_len, e);
			}
		}
	}


//lecture 08

//8.1 线性表顺序存储的结构代码：
	#define MAXSIZE 20
	typedef int ElemType;
	typedef struct
	{
		ElemType data[MAXSIZE];
		int length;  //线性表当前长度
	}SqList;

//8.2 getElem.c 实现GetElem的代码
	#define OK 1
	#define ERROR 0
	#define TRUE 1
	#define FALSE 0

	typedef int Status;
	// Status是函数的类型，其值是函数结果状态代码，如OK等。
	// 初始条件，顺序线性表L已存在，1<=i<=ListLength(L)
	//操作结果，用e返回L中第i个数据元素的值。
	Status GetElem(SqList L, int i, ElemType *e)
	{
		if(L.length==0 || i<1 || i>L.length)
		{
			return ERROR;
		}
		*e = L.data[i-1];
		
		return OK;
	}
	
//8.3 ListInsert.c 实现ListInsert的代码
	//初始条件，顺序线性表L已存在，1<=i<=ListLength(L)。
	//操作结果，在L中第i个位置之前插入新的数据元素e，L长度+1。
	
	Status ListInsert(SqList *L, int i, ElemType e)
	{
		int k;
		
		if(L->length == MAXSIZE)	//顺序线性表已经满了
		{
			return ERROR;
		}
		if(i<1 || i>L->length+1)	//当i不在范围内时
		{
			return ERROR;
		}
		if(i <= L->length)	//若插入数据位置不在表尾
		{
			//将要插入位置后的数据元素全部向后移动一位
			for(k=L->length-1; k>=i-1; k--)
			{
				L->data[k+1] = L->data[k];
			}
		}
		
		L->data[i-1] = e;	//
		L->length++;
		
		return OK;
	}
	

//lecture 09

//9.1 ListDelete.c 实现ListDelete的代码
	//初始条件：顺序线性表L已存在，1<=i<=ListLength(L)。
	//操作结果：删除L的第i个数据元素，并用e返回其值，L的长度-1。
	
	Status ListDelete(SqList *L, int i, ElemType *e)
	{
		int k;
		
		if(L->length == 0)
		{
			return ERROR;
		}
		if(i<1 || i>L->length)
		{
			return ERROR;
		}
		
		*e = L->data[i-1]; //这里是用e返回其值，"i-1"因为数组从0开始
		
		if(i < L->length)
		{
			for(k=i; k<L->length; k++)
			{
				L->data[k-1] = L->data[k]; //被删元素后面元素全部向前移动一位
			}
		}
		
		L->length--;
		
		return OK;
		//时间复杂度：最好为O(1)删除最后一位，最差为O(n)删除第一位，平均为O((n-1)/2)，所以时间复杂度还是O(n)。
	}
	
	//线性表链式存储结构中，一个数据元素通常有一个数据域和1-2个指针域（后继或前后继），该元素数据域和指针域组成为一个结点。
	//链表中，第一个结点的指针为头指针，指向第一个有数据的数据元素，而最后一个结点的指针/后继为NULL。
	
	
	
//lecture 10
//头指针是链表的必要元素，空链表也有头指针。头结点则不是必要的，但头结点能统一操作，比如在第一个有效结点前插入元素。头结点的数据域一般为空，但也可以用来存放链表长度。
	
//10.1 C语言中用结构指针来描述单链表
	typedef struct Node
	{
		ElemType data;		// 数据域
		struct Node *Next;	// 指针域
	} Node;
	typedef struct Node *LinkList;
	//若p是指向线性表第i个结点的指针，则该结点的数据域可用p->data表示，该结点的指针域可用p->next表示
	//若p->data = ai，则p->next->data = ai+1
	
	
//10.2 getElem.c 实现GetElem的代码 (单链表)
	//初始条件，顺序线性表L已存在，1<=i<=ListLength(L)
	//操作结果，用e返回L中第i个数据元素的值。
	
Status GetElem(LinkList *L, int i, ElemType *e)
{
	int j;
	LinkList p;
	
	p = *L->next;
	j = 1;
	
	while(p && j<i)
	{
		p = p->next;
		++j;
	}
	
	if(!p || j>i)
	{
		return ERROR;
	}
	
	*e = p->data;
	
	return OK;
}
	
	
	
//lecture 11

//11.1 ListInsert.c 实现ListInsert的代码 (单链表)
	//初始条件：顺序线性表L已存在，1<=i<=ListLength(L)。
	//操作结果：在L中第i个位置之前插入新的数据元素e，L的长度加1。
	
Status ListInsert(LinkList *L, int i, ElemType e)
{
	int j;
	LinkList p, s;
	
	p = *L;
	j = 1;
	
	while(p && j<i)  // 用于寻找第i个结点
	{
		p = p->next;
		j++;
	}
	
	if(!p || j>i)
	{
		return ERROR;
	}
	
	s = (LinkList)malloc(sizeof(Node)); // malloc进行内存分配（返回所分配内存块的首地址，以指针返回），sizeof判断其参数（数据类型）所占字节数
	s->data = e;
	
	s->next = p->next;
	p->next = s;
	
	return OK;
}
	


//11.2 ListDelete.c 实现ListDelete的代码 （单链表）
	//初始条件：顺序线性表L已存在，1<=i<=ListLength(L)。
	//操作结果：删除L的第i个数据元素，并用e返回其值，L的长度-1。
	
Status ListDelete(LinkList *L, int i, ElemType e)
{
	int j;
	LinkList p, q;
	
	p = *L;
	j = 1;
	
	while(p->next && j<i)  // 用于寻找第i个结点
	{
		p = p->next;
		++j;
	}
	
	if(!(p->next) || j>i)
	{
		return ERROR;
	}
	
	q = p->next;
	p->next = q->next;
	
	*e = q->data;
	free(q);
	
	return OK;
}
	
	

//lecture 12 单链表整表创建

/* 头插法建立单链表示例 */

void CreateListHead(LinkList *L, int n)
{
	LinkList p;
	int i;
	
	srand(time(0));	// 初始化随机种子
	
	*L = (LinkList)malloc(sizeof(Node));
	(*L)->next = NULL;
	
	for(i=0; i<n; i++)
	{
		p = (LinkList)malloc(sizeof(Node));		// 生成新结点
		p->data = rand()%100+1;
		p->next = (*L)->next;
		(*L)->next = p;
	}
}
	
	
/* 尾插法建立单链表 */

void CreateListTail(LinkList *L, int n)
{
	LinkList p, r;
	int i;
	
	srand(time(0));	// 初始化随机种子
	*L = (LinkList)malloc(sizeof(Node));
	r = *L;
	
	for(i=0; i<n; i++)
	{
		p = (Node *)malloc(sizeof(Node));	// 这里和上面不同，我也不知道原因。
		p->data = rand()%100+1;
		r->next = p;	// 上一个代指的结点的next指向新结点p。
		r = p;		// r只是一个暂存变量，一开始赋值为*L，是代指*L头结点，现在赋值为p，是代指新结点p。
	}
	
	r->next = NULL;
}
/*
关于srand：
电脑的随机数都是伪随机，也就是通过一定的算法得出一个数列，然后每 rand()一次就取一个数。
而srand()的功能就是设置产生随机数的公式的参数（随机数种子），如果使用相同的种子，那么得到的随机数也就是相同的。自然，如果使用不同的种子，得出的随机数序列也是不同的。
不同的种子会得到固定的不同的随机数序列。

通常可以利用系统时间来改变系统的种子值，即srand(time(NULL))，
可以为rand函数提供不同的种子值，进而产生不同的随机数序列。
*/





//lecture 13 单链表整表删除

/* ClearList */
Status ClearList(LinkList *L)
{
	LinkList p, q;
	
	p = (*L)->next;
	
	while(p) // 只要p在有数据的情况下即可继续执行while
	{
		q = p->next;
		free(p);
		p = q;
	}
	
	(*L)->next = NULL;
	
	return OK;
}

/* review */
/*
单链表结构与顺序存储结构优缺点

1.存储分配方式：顺序存储结构用一段连续的存储单元依次存储线性表的数据元素。
				单链表采用链式存储结构，用一组任意的存储单元存放线性表的元素。

2.时间性能：（1）查找：
				顺序存储结构时间复杂度为O(1)，因为有下标。
				单链表O(n)，因为无下标，要一个一个往下找（前驱、后继）。
			（2）插入和删除：
				顺序存储结构需要平均移动表长一半的元素，时间为O(n)。
				单链表在计算出某位置的指针后，插入和删除时间仅为O(1)。

3.空间性能：顺序存储结构需要预分配存储空间，分大了，容易造成空间浪费，分小了，容易发生溢出。
			单链表不需要预分配存储空间，只要有就可以动态分配，元素个数也不受限制。

结论：若线性表需要频繁查找，很少进行插入和删除操作时，宜采用顺序存储结构。
	  若需要频繁插入和删除时，或预先不知道存储元素有多少时，宜采用单链表结构。
*/




//lecture 14 静态链表

// C语言的魅力在于指针的灵活性，静态链表诞生于编程语言还没有指针功能的时代（比如basic）
// 用数组描述的链表叫做静态链表，空间需要预分配

/* 线性表的静态链表存储结构 */
#define MAXSIZE 1000
typedef struct
{
	ElemType data; //数据
	int cur; //游标(cursor)
} Component, StaticLinkList[MAXSIZE];
//数组最后一位的游标指向第一个元素不为空的数组下标（数组的第几位），数组第一位（A[0]）的游标指向第一个元素为空的数组下标。初始化时，连续空闲位的游标通常指向下一位的下标。

/* 对静态链表进行初始化相当于初始化数组 */
Status InitList(StaticLinkList space)
{
	int i;
	for(i=0; i<MAXSIZE-1; i++)
		space[i].cur = i + 1;
	
	space[MAXSIZE-1].cur = 0;
	return OK;
}

/* 备忘

1.数组的第一个和最后一个元素的data不存放数据。
2.通常把未使用的数组元素称为备用链表（data里数值为空，未存放数据）。
3.数组第一个元素，即下标为0的那个元素的cur就存放备用链表的第一个结点的下标。
4.数组最后一个元素的cur存放第一个有数值的元素的下标，相当于单链表的头结点的作用。
5.链表的最后一位的游标指向下标0（即数组第一个元素，该元素的游标指向备用链表的第一个结点）。

静态链表操作的是数组，没有现成malloc(), free()函数，需要自己实现。
*/




//lecture 15 静态链表插入操作

/* 获取空闲分量的下标 */
int Malloc_SSL(StaticLinkList space)
{
	int i = space[0].cur;
	if(space[0].cur)
		space[0].cur = space[i].cur; // 把它的下一个分量用来作为备用
	return i;
}

/* 在静态链表L中第i个元素之前插入新的数据元素 */
Status ListInsert(StaticLinkList L, int i, ElemType e)
{
	int j, k, l;
	
	k = MAX_SIZE - 1;	// 数组的最后一个元素
	if(i<1 || i>ListLength(L)+1)
	{
		return ERROR;
	}
	
	j = Malloc_SSL(L);	// 取得现在空闲（备用链表）的第一位
	if(j)
	{
		L(j).data = e;	// e是插入的值
		for(l=1; l<=i-1; l++)
		{
			k = L(k).cur;	// 找到链表中第i-1位,即顺着游标找到第i-1位
		}
		L(j).cur = L(k).cur;	// 新插入元素所在结点（数组空闲、备用链表第一位）的next指向链表原第i位（即原链表第i-1位的后继位）
		L(k).cur = j;	// 链表第i-1位后继next指向新元素所在位
		
		return OK;
	}
	
	return ERROR;
}
// 静态链表一开始为空，每一个新插入的元素都按照数组的下标依次放入，
// 而其实际上在链表中排第几位是由游标决定的。备用链表可看成是内存中准备动态分配数据元素的可用空间。




