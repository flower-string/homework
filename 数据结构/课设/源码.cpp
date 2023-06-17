#include<iostream>
#include<string>
#include <iomanip> //ʹ��setw������Ҫ�����ͷ�ļ�
#include <sstream> 
using namespace std;

struct term{
    float xishu;   //һԪ����ʽϵ��
    int zhishu;    //һԪ����ʽָ��
};

struct LNode{ 
    //������洢term����ʽֵ
    term data;            
    struct LNode *next;
};
typedef LNode* polynomail;

/*��������*/
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

/*��������*/
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

/*�ϲ�ͬ����*/
polynomail hebing(polynomail Head)
{
    arrangeup(Head); // �Ƚ�����������
    polynomail r, q, p, Q;
    for (q = Head->next; q != NULL; q = q->next)
    {
        for (p = q->next, r = q; p != NULL;)
        {
            if (q->data.zhishu == p->data.zhishu)
            {
                q->data.xishu = q->data.xishu + p->data.xishu;
                if (p == r->next) // ���Ҫɾ���Ľڵ�����һ���ڵ�
                {
                    r->next = p->next;
                    delete p;
                    p = r->next;
                }
                else // ���Ҫɾ���Ľڵ㲻����һ���ڵ�
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

/*����һԪ����ʽ*/
polynomail creatpolyn(int m)
{
    if (m == 0) // �������Ϊ 0�����ؿ�ָ��
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
        cout << "�������" << i + 1 << "���ϵ����ָ����";
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

/*���һԪ����ʽ*/
void printpolyn(polynomail P)
{
    stringstream ss; // ����ƴ���������� stringstream ����
    int i = 0; // ��¼����������
    polynomail q;
    if (P == NULL)
    {
        ss << "����";
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
            if (q->data.zhishu == 0) // �����һ���ָ��Ϊ 0
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
                if (q->data.zhishu == 0) // �����ǰ���ָ��Ϊ 0
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
        if (i == 0) // ���û�������
        {
            ss << "0";
        }
    }
    cout << ss.str(); // ���ƴ�Ӻ�Ľ��
}

/*�ж϶���ʽ�Ƿ�ϡ��*/
bool judge(polynomail Head)
{
    polynomail p;
    p = Head->next;
    bool xi = false;
    int prev_zhishu = -1; // ���ڼ�¼��һ�����ָ��
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

/*������ʽ���(����)*/
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
        if (s->data.xishu != 0) // ��������ϵ����Ϊ 0���������������
        {
            r->next = s;
            r = s;
        }
    }
    // ��ʣ����������������
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
//    cout << "������ʽ��ӣ������ݣ�\n";
    arrangeup(newHead);
    return newHead; 
}

/*������ʽ��ӣ����ݣ�*/
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
        if (s->data.xishu != 0) // ��������ϵ����Ϊ 0���������������
        {
            r->next = s;
            r = s;
        }
    }
    // ��ʣ����������������
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
//    cout << "������ʽ��ӣ������ݣ�\n";
    arrangedown(newHead);
    return newHead; 
}

/* ������ʽ��������ݣ�*/
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

/* ������ʽ��������ݣ�*/
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

/* ������ʽ��ˣ����ݣ�*/
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

/* ������ʽ��ˣ����ݣ�*/
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
    cout << "                             **һԪ����ʽ��ʵ��**                    \n";
    cout << "�������һ������ʽ��\n";
    cout << "Ҫ���뼸�";
    cin >> m;
    while (m == 0) {
        cout << "m����Ϊ0������������m:";
        cin >> m;
    }
    pa = creatpolyn(m);
    cout << "��һ������ʽΪ��";
    printpolyn(pa);
    if (judge(pa))
        cout << "�ö���ʽϡ��\n\n";
    else
        cout << "�ö���ʽ����\n\n";
    cout << "������ڶ�������ʽ��\n";
    cout << "Ҫ���뼸�";
    cin >> m;
    pb = creatpolyn(m);
    cout << "�ڶ�������ʽΪ��";
    printpolyn(pb);
    if (judge(pb))
        cout << "�ö���ʽϡ��\n\n";
    else
        cout << "�ö���ʽ����\n\n";

	int WIDTH = 15;
	// ����������ı��
	cout << setw(50) << setfill('=') << "" << endl;
	cout << setw(WIDTH) << setfill(' ') << "" << "�������" << setw(WIDTH) << setfill(' ') << "" << endl;
	cout << setw(50) << setfill('=') << "" << endl;
	cout << left << setw(WIDTH) << setfill(' ') << "����" << setw(WIDTH) << setfill(' ') << "���ݽ��" << setw(WIDTH) << setfill(' ') << "���ݽ��" << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	// ʵ������һԪ����ʽ�ļӷ�
	cout << left << setw(WIDTH) << setfill(' ') << "�ӷ�" << setw(WIDTH) << setfill(' ');
	addp = addpolynup(pa, pb);
	printpolyn(addp);
	cout << setw(WIDTH) << setfill(' ');
	addp = addpolyndown(pa, pb);
	printpolyn(addp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	
	// ʵ������һԪ����ʽ�ļ���
	cout << left << setw(15) << setfill(' ') << "����" << setw(15) << setfill(' ');
	subp = subpolynup(pa, pb);
	printpolyn(subp);
	cout << setw(15) << setfill(' ');
	subp = subpolyndown(pa, pb);
	printpolyn(subp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
	
	// ʵ������һԪ����ʽ�ĳ˷�
	cout << left << setw(15) << setfill(' ') << "�˷�" << setw(15) << setfill(' ');
	mulp = mulpolynup(pa, pb);
	printpolyn(mulp);
	cout << setw(15) << setfill(' ');
	mulp = mulpolyndown(pa, pb);
	printpolyn(mulp);
	cout << endl;
	cout << setw(50) << setfill('-') << "" << endl;
    return 0;
}
