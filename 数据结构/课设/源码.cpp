#include<iostream>
#include<string>
#include <iomanip> //使用setw函数需要引入该头文件
#include <sstream> 
using namespace std;

struct term{
    float xishu;   //一元多项式系数
    int zhishu;    //一元多项式指数
};

struct LNode{ 
    //单链表存储term多项式值
    term data;            
    struct LNode *next;
};
typedef LNode* polynomail;

/*升幂排列*/
void arrangeup(polynomail pa)
{
    polynomail h = pa, p, q, r;
    for (p = pa; p->next != NULL; p = p->next);
    r = p;
    while (h->next != r)
    {
        for (p = h; p->next != r && p != r; p = p->next)
        {
            if (p->next->data.zhishu > p->next->next->data.zhishu && p->next->next != NULL)
            {
                q = p->next->next;
                p->next->next = q->next;
                q->next = p->next;
                p->next = q;
            }
        }
        r = p;
    }
}

/*降幂排序*/
void arrangedown(polynomail pa)
{ 
    polynomail h = pa, p, q, r;
    for (p = pa; p->next != NULL; p = p->next);
    r = p;
    while (h->next != r)
    {
        for (p = h; p->next != r && p != r; p = p->next)
        {
            if (p->next->data.zhishu < p->next->next->data.zhishu && p->next->next != NULL)
            { 
                q = p->next->next;
                p->next->next = q->next;
                q->next = p->next;
                p->next = q;
            }
        }
        r = p;
    } 
}

/*合并同类项*/
polynomail hebing(polynomail Head)
{
    arrangeup(Head); // 先进行升幂排列
    polynomail r, q, p, Q;
    for (q = Head->next; q != NULL; q = q->next)
    {
        for (p = q->next, r = q; p != NULL;)
        {
            if (q->data.zhishu == p->data.zhishu)
            {
                q->data.xishu = q->data.xishu + p->data.xishu;
                if (p == r->next) // 如果要删除的节点是下一个节点
                {
                    r->next = p->next;
                    delete p;
                    p = r->next;
                }
                else // 如果要删除的节点不是下一个节点
                {
                    Q = p;
                    p = p->next;
                    delete Q;
                }
            }
            else
            {
                r = r->next;
                p = p->next;
            }
        }
    }
    return Head;
}

/*创建一元多项式*/
polynomail creatpolyn(int m)
{
    if (m == 0) // 如果项数为 0，返回空指针
    {
        return NULL;
    }
    polynomail Head, r, s;
    int i;
    Head = new LNode;
    r = Head;
    for (i = 0; i < m; i++)
    { 
        s = new LNode;
        cout << "请输入第" << i + 1 << "项的系数和指数：";
        cin >> s->data.xishu >> s->data.zhishu;
        r->next = s;
        r = s;
    }
    r->next = NULL;
    if (m > 1)
    {
        Head = hebing(Head);
    }
    return Head;
}

/*输出一元多项式*/
void printpolyn(polynomail P)
{
    stringstream ss; // 用于拼接输出结果的 stringstream 对象
    int i = 0; // 记录输出项的数量
    polynomail q;
    if (P == NULL)
    {
        ss << "无项";
    }
    else if (P->next == NULL)
    {
        ss << "Y=0";
    }
    else
    {
        q = P->next;
        if (q->data.xishu != 0)
        {
            if (q->data.zhishu == 0) // 如果第一项的指数为 0
            {
                ss << q->data.xishu;
            }
            else
            {
                ss << q->data.xishu << "X^" << q->data.zhishu;
                i++;
            }
        }
        q = q->next;
        while (q != NULL)
        {
            if (q->data.xishu != 0)
            {
                if (q->data.zhishu == 0) // 如果当前项的指数为 0
                {
                    if (q->data.xishu > 0)
                    {
                        ss << "+";
                    }
                    ss << q->data.xishu;
                }
                else
                {
                    if (q->data.xishu > 0)
                    {
                        ss << "+";
                    }
                    ss << q->data.xishu << "X^" << q->data.zhishu;
                    i++;
                }
            }
            q = q->next;
        }
        if (i == 0) // 如果没有输出项
        {
            ss << "0";
        }
    }
    cout << ss.str(); // 输出拼接后的结果
}

/*判断多项式是否稀疏*/
bool judge(polynomail Head)
{
    polynomail p;
    p = Head->next;
    bool xi = false;
    int prev_zhishu = -1; // 用于记录上一个项的指数
    while (p != NULL && !xi)
    {
        if (p->data.zhishu - prev_zhishu > 1)
        {
            xi = true;
        }
        prev_zhishu = p->data.zhishu;
        p = p->next;
    }
    return xi;
}

/*两多项式相加(升幂)*/
polynomail addpolynup(polynomail pa, polynomail pb)
{
    polynomail s, newHead, q, p, r;
    p = pa->next;
    q = pb->next;
    newHead = new LNode;
    r = newHead;
    while (p && q)
    { 
        s = new LNode;
        if (p->data.zhishu == q->data.zhishu)
        {
            s->data.xishu = p->data.xishu + q->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
            q = q->next;
        }
        else if (p->data.zhishu > q->data.zhishu)
        {
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
        }
        else
        {
            s->data.xishu = q->data.xishu;
            s->data.zhishu = q->data.zhishu;
            q = q->next;
        }
        if (s->data.xishu != 0) // 如果新项的系数不为 0，则加入新链表中
        {
            r->next = s;
            r = s;
        }
    }
    // 将剩余的项加入新链表中
    while (p)
    { 
        s = new LNode;
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        if (s->data.xishu != 0)
        {
            r->next = s;
            r = s;
        }
        p = p->next;
    }
    while (q)
    { 
        s = new LNode;
        s->data.xishu = q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        if (s->data.xishu != 0)
        {
            r->next = s;
            r = s;
        }
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL)
    {
        newHead = hebing(newHead);
    }
//    cout << "两多项式相加：（升幂）\n";
    arrangeup(newHead);
    return newHead; 
}

/*两多项式相加（降幂）*/
polynomail addpolyndown(polynomail pa, polynomail pb)
{
    polynomail s, newHead, q, p, r;
    p = pa->next;
    q = pb->next;
    newHead = new LNode;
    r = newHead;
    while (p && q)
    { 
        s = new LNode;
        if (p->data.zhishu == q->data.zhishu)
        {
            s->data.xishu = p->data.xishu + q->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
            q = q->next;
        }
        else if (p->data.zhishu < q->data.zhishu)
        {
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
        }
        else
        {
            s->data.xishu = q->data.xishu;
            s->data.zhishu = q->data.zhishu;
            q = q->next;
        }
        if (s->data.xishu != 0) // 如果新项的系数不为 0，则加入新链表中
        {
            r->next = s;
            r = s;
        }
    }
    // 将剩余的项加入新链表中
    while (p)
    { 
        s = new LNode;
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        if (s->data.xishu != 0)
        {
            r->next = s;
            r = s;
        }
        p = p->next;
    }
    while (q)
    { 
        s = new LNode;
        s->data.xishu = q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        if (s->data.xishu != 0)
        {
            r->next = s;
            r = s;
        }
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL)
    {
        newHead = hebing(newHead);
    }
//    cout << "两多项式相加：（降幂）\n";
    arrangedown(newHead);
    return newHead; 
}

/* 两多项式相减（升幂）*/
polynomail subpolynup(polynomail pa,polynomail pb)
{
    polynomail s, newHead = new LNode(), r = newHead;
    LNode *p = pa->next, *q = pb->next;
    while (p && q) {
        if (p->data.zhishu < q->data.zhishu) {
            s = new LNode();
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            r->next = s;
            r = s;
            p = p->next;
        } else if (p->data.zhishu > q->data.zhishu) {
            s = new LNode();
            s->data.xishu = -q->data.xishu;
            s->data.zhishu = q->data.zhishu;
            r->next = s;
            r = s;
            q = q->next;
        } else {
            int diff = p->data.xishu - q->data.xishu;
            if (diff != 0) {
                s = new LNode();
                s->data.xishu = diff;
                s->data.zhishu = p->data.zhishu;
                r->next = s;
                r = s;
            }
            p = p->next;
            q = q->next;
        }
    }
    while (p) {
        s = new LNode();
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        r->next = s;
        r = s;
        p = p->next;
    }
    while (q) {
        s = new LNode();
        s->data.xishu = -q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        r->next = s;
        r = s;
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    return newHead;
}

/* 两多项式相减（降幂）*/
polynomail subpolyndown(polynomail pa,polynomail pb)
{
    polynomail s, newHead = new LNode(), r = newHead;
    LNode *p = pa->next, *q = pb->next;
    while (p && q) {
        if (p->data.zhishu > q->data.zhishu) {
            s = new LNode();
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            r->next = s;
            r = s;
            p = p->next;
        } else if (p->data.zhishu < q->data.zhishu) {
            s = new LNode();
            s->data.xishu = -q->data.xishu;
            s->data.zhishu = q->data.zhishu;
            r->next = s;
            r = s;
            q = q->next;
        } else {
            int diff = p->data.xishu - q->data.xishu;
            if (diff != 0) {
                s = new LNode();
                s->data.xishu = diff;
                s->data.zhishu = p->data.zhishu;
                r->next = s;
                r = s;
            }
            p = p->next;
            q = q->next;
        }
    }
    while (p) {
        s = new LNode();
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        r->next = s;
        r = s;
        p = p->next;
    }
    while (q) {
        s = new LNode();
        s->data.xishu = -q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        r->next = s;
        r = s;
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    return newHead;
}

/* 两多项式相乘（升幂）*/
polynomail mulpolynup(polynomail pa,polynomail pb)
{
    polynomail s, newHead = new LNode(), r = newHead;
    LNode *p, *q;
    for (p = pa->next; p != NULL; p = p->next) {
        for (q = pb->next; q != NULL; q = q->next) {
            s = new LNode();
            s->data.xishu = p->data.xishu * q->data.xishu;
            s->data.zhishu = p->data.zhishu + q->data.zhishu;
            r = newHead;
            while (r->next != NULL && r->next->data.zhishu < s->data.zhishu) {
                r = r->next;
            }
            if (r->next != NULL && r->next->data.zhishu == s->data.zhishu) {
                r->next->data.xishu += s->data.xishu;
                if (r->next->data.xishu == 0) {
                    LNode *temp = r->next;
                    r->next = temp->next;
                    delete temp;
                }
                delete s;
            } else {
                s->next = r->next;
                r->next = s;
            }
        }
    }
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    return newHead;
}

/* 两多项式相乘（降幂）*/
polynomail mulpolyndown(polynomail pa,polynomail pb)
{
    polynomail s, newHead = new LNode(), r = newHead;
    LNode *p, *q;
    for (p = pa->next; p != NULL; p = p->next) {
        for (q = pb->next; q != NULL; q = q->next) {
            s = new LNode();
            s->data.xishu = p->data.xishu * q->data.xishu;
            s->data.zhishu = p->data.zhishu + q->data.zhishu;
            r = newHead;
            while (r->next != NULL && r->next->data.zhishu > s->data.zhishu) {
                r = r->next;
            }
            if (r->next != NULL && r->next->data.zhishu == s->data.zhishu) {
                r->next->data.xishu += s->data.xishu;
                if (r->next->data.xishu == 0) {
                    LNode *temp = r->next;
                    r->next = temp->next;
                    delete temp;
                }
                delete s;
            } else {
                s->next = r->next;
                r->next = s;
            }
        }
    }
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    return newHead;
}


int main() {
    polynomail pa = NULL, pb = NULL;
    polynomail addp = NULL, subp = NULL, mulp = NULL;
    int m;
    cout << "                             **一元多项式的实现**                    \n";
    cout << "请输入第一个多项式：\n";
    cout << "要输入几项：";
    cin >> m;
    while (m == 0) {
        cout << "m不能为0，请重新输入m:";
        cin >> m;
    }
    pa = creatpolyn(m);
    cout << "第一个多项式为：";
    printpolyn(pa);
    if (judge(pa))
        cout << "该多项式稀疏\n\n";
    else
        cout << "该多项式稠密\n\n";
    cout << "请输入第二个多项式：\n";
    cout << "要输入几项：";
    cin >> m;
    pb = creatpolyn(m);
    cout << "第二个多项式为：";
    printpolyn(pb);
    if (judge(pb))
        cout << "该多项式稀疏\n\n";
    else
        cout << "该多项式稠密\n\n";

	int WIDTH = 15;
	// 输出运算结果的表格
	cout << setw(50) << setfill('=') << "" << endl;
	cout << setw(WIDTH) << setfill(' ') << "" << "操作结果" << setw(WIDTH) << setfill(' ') << "" << endl;
	cout << setw(50) << setfill('=') << "" << endl;
	cout << left << setw(WIDTH) << setfill(' ') << "操作" << setw(WIDTH) << setfill(' ') << "升幂结果" << setw(WIDTH) << setfill(' ') << "降幂结果" << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	// 实现两个一元多项式的加法
	cout << left << setw(WIDTH) << setfill(' ') << "加法" << setw(WIDTH) << setfill(' ');
	addp = addpolynup(pa, pb);
	printpolyn(addp);
	cout << setw(WIDTH) << setfill(' ');
	addp = addpolyndown(pa, pb);
	printpolyn(addp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	
	// 实现两个一元多项式的减法
	cout << left << setw(15) << setfill(' ') << "减法" << setw(15) << setfill(' ');
	subp = subpolynup(pa, pb);
	printpolyn(subp);
	cout << setw(15) << setfill(' ');
	subp = subpolyndown(pa, pb);
	printpolyn(subp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	
	// 实现两个一元多项式的乘法
	cout << left << setw(15) << setfill(' ') << "乘法" << setw(15) << setfill(' ');
	mulp = mulpolynup(pa, pb);
	printpolyn(mulp);
	cout << setw(15) << setfill(' ');
	mulp = mulpolyndown(pa, pb);
	printpolyn(mulp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
    return 0;
}
