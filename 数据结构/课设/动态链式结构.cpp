#include <iostream>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;
 
struct term {
    float xishu;   //一元多项式系数
    int zhishu;    //一元多项式指数
};

struct LNode {
    //单链表存储term多项式值
    term data;
    struct LNode *next;
};
typedef LNode *polynomail;
/*升幂排列*/
void arrangeup(polynomail pa) {
    polynomail h = pa, p, q, r;
    for (p = pa; p->next != NULL; p = p->next);
    r = p;
    while (h->next != r) {
        for (p = h; p->next != r && p != r; p = p->next) {
            if (p->next->data.zhishu > p->next->next->data.zhishu && p->next->next != NULL) {
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
void arrangedown(polynomail pa) {
    polynomail h = pa, p, q, r;
    for (p = pa; p->next != NULL; p = p->next);
    r = p;
    while (h->next != r) {
        for (p = h; p->next != r && p != r; p = p->next) {
            if (p->next->data.zhishu < p->next->next->data.zhishu && p->next->next != NULL) {
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
polynomail hebing(polynomail Head) {
    arrangeup(Head); // 先进行升幂排列
    polynomail r, q, p, Q;
    for (q = Head->next; q != NULL; q = q->next) {
        for (p = q->next, r = q; p != NULL;) {
            if (q->data.zhishu == p->data.zhishu) {
                q->data.xishu = q->data.xishu + p->data.xishu;
                if (p == r->next) // 如果要删除的节点是下一个节点
                {
                    r->next = p->next;
                    delete p;
                    p = r->next;
                } else // 如果要删除的节点不是下一个节点
                {
                    Q = p;
                    p = p->next;
                    delete Q;
                }
            } else {
                r = r->next;
                p = p->next;
            }
        }
    }
    return Head;
}

/*创建一元多项式*/
polynomail creatpolyn(int m) {
    if (m == 0) // 如果项数为 0，返回空指针
    {
        return NULL;
    }
    polynomail Head, r, s;
    int i;
    Head = new LNode;
    r = Head;
    for (i = 0; i < m; i++) {
        s = new LNode;
        cout << "请输入第" << i + 1 << "项的系数和指数：";
        cin >> s->data.xishu >> s->data.zhishu;
        r->next = s;
        r = s;
    }
    r->next = NULL;
    if (m > 1) {
        Head = hebing(Head);
    }
    return Head;
}

/*一元多项式转字符串*/
string polyntoString(polynomail P) {
    stringstream ss; // 用于拼接输出结果的 stringstream 对象
    int i = 0; // 记录输出项的数量
    polynomail q;
    if (P == NULL) {
        ss << "无项";
    } else if (P->next == NULL) {
        ss << "Y=0";
    } else {
        q = P->next;
        if (q->data.xishu != 0) {
            if (q->data.zhishu == 0) // 如果第一项的指数为 0
            {
                ss << q->data.xishu;
            } else {
                ss << q->data.xishu << "X^" << q->data.zhishu;
                i++;
            }
        }
        q = q->next;
        while (q != NULL) {
            if (q->data.xishu != 0) {
                if (q->data.zhishu == 0) // 如果当前项的指数为 0
                {
                    if (q->data.xishu > 0) {
                        ss << "+";
                    }
                    ss << q->data.xishu;
                } else {
                    if (q->data.xishu > 0) {
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
    return ss.str();
}

/*判断多项式是否稀疏*/
bool judge(polynomail Head) {
    polynomail p;
    p = Head->next;
    bool xi = false;
    int prev_zhishu = -1; // 用于记录上一个项的指数
    while (p != NULL && !xi) {
        if (p->data.zhishu - prev_zhishu > 1) {
            xi = true;
        }
        prev_zhishu = p->data.zhishu;
        p = p->next;
    }
    return xi;
}

/*两多项式相加(升幂)*/
polynomail addpolynup(polynomail pa, polynomail pb) {
    polynomail s, newHead, q, p, r;
    p = pa->next;
    q = pb->next;
    newHead = new LNode;
    r = newHead;
    while (p && q) {
        s = new LNode;
        if (p->data.zhishu == q->data.zhishu) {
            s->data.xishu = p->data.xishu + q->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
            q = q->next;
        } else if (p->data.zhishu > q->data.zhishu) {
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
        } else {
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
    while (p) {
        s = new LNode;
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        if (s->data.xishu != 0) {
            r->next = s;
            r = s;
        }
        p = p->next;
    }
    while (q) {
        s = new LNode;
        s->data.xishu = q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        if (s->data.xishu != 0) {
            r->next = s;
            r = s;
        }
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    arrangeup(newHead);
    return newHead;
}

/*两多项式相加（降幂）*/
polynomail addpolyndown(polynomail pa, polynomail pb) {
    polynomail s, newHead, q, p, r;
    p = pa->next;
    q = pb->next;
    newHead = new LNode;
    r = newHead;
    while (p && q) {
        s = new LNode;
        if (p->data.zhishu == q->data.zhishu) {
            s->data.xishu = p->data.xishu + q->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
            q = q->next;
        } else if (p->data.zhishu < q->data.zhishu) {
            s->data.xishu = p->data.xishu;
            s->data.zhishu = p->data.zhishu;
            p = p->next;
        } else {
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
    while (p) {
        s = new LNode;
        s->data.xishu = p->data.xishu;
        s->data.zhishu = p->data.zhishu;
        if (s->data.xishu != 0) {
            r->next = s;
            r = s;
        }
        p = p->next;
    }
    while (q) {
        s = new LNode;
        s->data.xishu = q->data.xishu;
        s->data.zhishu = q->data.zhishu;
        if (s->data.xishu != 0) {
            r->next = s;
            r = s;
        }
        q = q->next;
    }
    r->next = NULL;
    if (newHead->next != NULL && newHead->next->next != NULL) {
        newHead = hebing(newHead);
    }
    arrangedown(newHead);
    return newHead;
}

/* 两多项式相减（升幂）*/
polynomail subpolynup(polynomail pa, polynomail pb) {
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
            float diff = p->data.xishu - q->data.xishu;
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

/*两多项式相减（降幂）*/
polynomail subpolyndown(polynomail pa,polynomail pb)
{
    polynomail s,newHead,q,p,r;
    p=pa->next;q=pb->next;
    newHead=new LNode;
    r=newHead;
    while(p)
    {
        s=new LNode;
        s->data.xishu=p->data.xishu;
        s->data.zhishu=p->data.zhishu;
        r->next=s;  r=s;
        p=p->next;
    }
    while(q)
    {
        s=new LNode;
        s->data.xishu=-q->data.xishu;
        s->data.zhishu=q->data.zhishu;
        r->next=s;  r=s;
        q=q->next;
    }
    r->next=NULL;
    if(newHead->next!=NULL&&newHead->next->next!=NULL)
        newHead=hebing(newHead);
    arrangedown(newHead);
    return newHead;
}

/*4两多项式相乘（升幂）*/
polynomail mulpolynup(polynomail pa,polynomail pb)
{
    polynomail s,newHead,q,p,r;

    newHead=new LNode;
    r=newHead;
    for(p=pa->next;p!=NULL;p=p->next)
        for(q=pb->next;q!=NULL;q=q->next)
        {
            s=new LNode;
            s->data.xishu=p->data.xishu*q->data.xishu;
            s->data.zhishu=p->data.zhishu+q->data.zhishu;
            r->next=s;
            r=s;
        }
    r->next=NULL;
    arrangeup(newHead);
    if(newHead->next!=NULL&&newHead->next->next!=NULL)
        newHead=hebing(newHead);
    return newHead;
}

/*4两多项式相乘（降幂）*/
polynomail mulpolyndown(polynomail pa,polynomail pb)
{
    polynomail s,newHead,q,p,r;

    newHead=new LNode;
    r=newHead;
    for(p=pa->next;p!=NULL;p=p->next)
        for(q=pb->next;q!=NULL;q=q->next)
        {
            s=new LNode;
            s->data.xishu=p->data.xishu*q->data.xishu;
            s->data.zhishu=p->data.zhishu+q->data.zhishu;
            r->next=s;
            r=s;
        }
    r->next=NULL;
    arrangedown(newHead);
    if(newHead->next!=NULL&&newHead->next->next!=NULL)//合并同类项
        newHead=hebing(newHead);
    return newHead;
}

void printp(string d, polynomail p) {
    cout << d << polyntoString(p) << endl
         << (judge(p) ? "该多项式稀疏\n\n" : "该多项式稠密\n\n");
}

void outputTable(ostream& out, polynomail pa, polynomail pb, polynomail addpup, polynomail addpdown, polynomail subpup, polynomail subpdown, polynomail mulpup, polynomail mulpdown) {
    out  << "加法升幂结果为：" << polyntoString(addpup) << endl
         << "加法降幂结果为：" << polyntoString(addpdown) << endl
         << "减法升幂结果为：" << polyntoString(subpup) << endl
         << "减法降幂结果为：" << polyntoString(subpdown) << endl
         << "乘法升幂结果为：" << polyntoString(mulpup) << endl
         << "乘法降幂结果为：" << polyntoString(mulpdown) << endl;
}

int main() {
//    system("chcp 65001");
    polynomail pa = NULL, pb = NULL;
    polynomail addpup = NULL, addpdown = NULL, subpup = NULL, subpdown = NULL, mulpup = NULL, mulpdown = NULL;
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
    printp("第一个多项式为: ", pa);
    cout << "请输入第二个多项式：\n";
    cout << "要输入几项：";
    cin >> m;
    pb = creatpolyn(m);
    printp("第二个多项式为: ", pb);

    // 计算
    addpup = addpolynup(pa, pb);
    addpdown = addpolyndown(pa, pb);
    subpup = subpolynup(pa, pb);
    subpdown = subpolyndown(pa, pb);
    mulpup = mulpolynup(pa, pb);
    mulpdown = mulpolyndown(pa, pb);

    // 输出运算结果的表格
    outputTable(cout, pa, pb, addpup, addpdown, subpup, subpdown, mulpup, mulpdown);

    // 打开输出文件
    ofstream outfile("result.txt");
    if (!outfile) {
        cerr << "打开文件失败" << endl;
        return 1;
    }

    outputTable(outfile, pa, pb, addpup, addpdown, subpup, subpdown, mulpup, mulpdown);
    return 0;
}


