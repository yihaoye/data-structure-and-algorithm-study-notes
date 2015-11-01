//数据结构与算法



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
	#Status GetElem(SqList L, int i, ElemType *e)
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
	
	
	
	