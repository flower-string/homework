#include <iostream>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;
 
struct term {
    float xishu;   //һԪ����ʽϵ��
    int zhishu;    //һԪ����ʽָ��
};

struct LNode {
    //������洢term����ʽֵ
    term data;
    struct LNode *next;
};
typedef LNode *polynomail;
/*��������*/
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

/*��������*/
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

/*�ϲ�ͬ����*/
polynomail hebing(polynomail Head) {
    arrangeup(Head); // �Ƚ�����������
    polynomail r, q, p, Q;
    for (q = Head->next; q != NULL; q = q->next) {
        for (p = q->next, r = q; p != NULL;) {
            if (q->data.zhishu == p->data.zhishu) {
                q->data.xishu = q->data.xishu + p->data.xishu;
                if (p == r->next) // ���Ҫɾ���Ľڵ�����һ���ڵ�
                {
                    r->next = p->next;
                    delete p;
                    p = r->next;
                } else // ���Ҫɾ���Ľڵ㲻����һ���ڵ�
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

/*����һԪ����ʽ*/
polynomail creatpolyn(int m) {
    if (m == 0) // �������Ϊ 0�����ؿ�ָ��
    {
        return NULL;
    }
    polynomail Head, r, s;
    int i;
    Head = new LNode;
    r = Head;
    for (i = 0; i < m; i++) {
        s = new LNode;
        cout << "�������" << i + 1 << "���ϵ����ָ����";
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

/*һԪ����ʽת�ַ���*/
string polyntoString(polynomail P) {
    stringstream ss; // ����ƴ���������� stringstream ����
    int i = 0; // ��¼����������
    polynomail q;
    if (P == NULL) {
        ss << "����";
    } else if (P->next == NULL) {
        ss << "Y=0";
    } else {
        q = P->next;
        if (q->data.xishu != 0) {
            if (q->data.zhishu == 0) // �����һ���ָ��Ϊ 0
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
                if (q->data.zhishu == 0) // �����ǰ���ָ��Ϊ 0
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
        if (i == 0) // ���û�������
        {
            ss << "0";
        }
    }
    return ss.str();
}

/*�ж϶���ʽ�Ƿ�ϡ��*/
bool judge(polynomail Head) {
    polynomail p;
    p = Head->next;
    bool xi = false;
    int prev_zhishu = -1; // ���ڼ�¼��һ�����ָ��
    while (p != NULL && !xi) {
        if (p->data.zhishu - prev_zhishu > 1) {
            xi = true;
        }
        prev_zhishu = p->data.zhishu;
        p = p->next;
    }
    return xi;
}

/*������ʽ���(����)*/
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
        if (s->data.xishu != 0) // ��������ϵ����Ϊ 0���������������
        {
            r->next = s;
            r = s;
        }
    }
    // ��ʣ����������������
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

/*������ʽ��ӣ����ݣ�*/
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
        if (s->data.xishu != 0) // ��������ϵ����Ϊ 0���������������
        {
            r->next = s;
            r = s;
        }
    }
    // ��ʣ����������������
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

/* ������ʽ��������ݣ�*/
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

/*������ʽ��������ݣ�*/
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

/*4������ʽ��ˣ����ݣ�*/
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

/*4������ʽ��ˣ����ݣ�*/
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
    if(newHead->next!=NULL&&newHead->next->next!=NULL)//�ϲ�ͬ����
        newHead=hebing(newHead);
    return newHead;
}

void printp(string d, polynomail p) {
    cout << d << polyntoString(p) << endl
         << (judge(p) ? "�ö���ʽϡ��\n\n" : "�ö���ʽ����\n\n");
}

void outputTable(ostream& out, polynomail pa, polynomail pb, polynomail addpup, polynomail addpdown, polynomail subpup, polynomail subpdown, polynomail mulpup, polynomail mulpdown) {
    out  << "�ӷ����ݽ��Ϊ��" << polyntoString(addpup) << endl
         << "�ӷ����ݽ��Ϊ��" << polyntoString(addpdown) << endl
         << "�������ݽ��Ϊ��" << polyntoString(subpup) << endl
         << "�������ݽ��Ϊ��" << polyntoString(subpdown) << endl
         << "�˷����ݽ��Ϊ��" << polyntoString(mulpup) << endl
         << "�˷����ݽ��Ϊ��" << polyntoString(mulpdown) << endl;
}

int main() {
//    system("chcp 65001");
    polynomail pa = NULL, pb = NULL;
    polynomail addpup = NULL, addpdown = NULL, subpup = NULL, subpdown = NULL, mulpup = NULL, mulpdown = NULL;
    int m;
    cout << "                             **һԪ����ʽ��ʵ��**                    \n";
    cout << "�������һ������ʽ��\n";
    cout << "Ҫ���뼸�";
    cin >> m;
    while (m == 0) {
        cout << "m����Ϊ0������������m:";
        cin >> m;
    }
    pa = creatpolyn(m);
    printp("��һ������ʽΪ: ", pa);
    cout << "������ڶ�������ʽ��\n";
    cout << "Ҫ���뼸�";
    cin >> m;
    pb = creatpolyn(m);
    printp("�ڶ�������ʽΪ: ", pb);

    // ����
    addpup = addpolynup(pa, pb);
    addpdown = addpolyndown(pa, pb);
    subpup = subpolynup(pa, pb);
    subpdown = subpolyndown(pa, pb);
    mulpup = mulpolynup(pa, pb);
    mulpdown = mulpolyndown(pa, pb);

    // ����������ı��
    outputTable(cout, pa, pb, addpup, addpdown, subpup, subpdown, mulpup, mulpdown);

    // ������ļ�
    ofstream outfile("result.txt");
    if (!outfile) {
        cerr << "���ļ�ʧ��" << endl;
        return 1;
    }

    outputTable(outfile, pa, pb, addpup, addpdown, subpup, subpdown, mulpup, mulpdown);
    return 0;
}


