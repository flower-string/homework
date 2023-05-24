#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// �������ڵ� 
typedef struct node {
    int data;
    struct node *left;
    struct node *right;
} Node;

// �򵥲��� 
void simpleSelectionSort(int arr[], int len) {
    int i, j, minIndex, temp;
    for (i = 0; i < len - 1; i++) {
        minIndex = i;
        for (j = i + 1; j < len; j++)
            if (arr[j] < arr[minIndex])
                minIndex = j;
        temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
}

// �����ڵ� 
Node *createNode(int data) {
    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->data = data;
    newNode->left = NULL;
    newNode->right = NULL;
    return newNode;
}

// ����ڵ� 
void insert(Node **root, int data) {
    if (*root == NULL) {
        *root = createNode(data);
        return;
    }
    if (data < (*root)->data)
        insert(&(*root)->left, data);
    else
        insert(&(*root)->right, data);
}

// ������� 
void inorder(Node *root) {
    if (root == NULL)
        return;
    inorder(root->left);
    printf("%d ", root->data);
    inorder(root->right);
}

// ���������� 
int search(Node *root, int data) {
    if (root == NULL)
        return 0;
    if (data == root->data)
        return 1;
    else if (data < root->data)
        return search(root->left, data);
    else
        return search(root->right, data);
}


// ˳����� 
int seqSearch(int arr[], int len, int data) {
    int i;
    for (i = 0; i < len; i++)
        if (arr[i] == data)
            return 1;
    return 0;
}

// �۰���� 
int binSearch(int arr[], int len, int data) {
    int left = 0, right = len - 1, mid;
    while (left <= right) {
        mid = left + (right - left) / 2;
        if (arr[mid] == data)
            return 1;
        else if (arr[mid] < data)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return 0;
}

int main() {
    srand(time(NULL));
    clock_t start, end;
    double seqTime, binTime, bstTime;
    int i, j;

    // �������ұ� 
    int n = 10000;
    int lookupTable[n];
    for (i = 0; i < n; i++)
        lookupTable[i] = rand() % 100000;

    simpleSelectionSort(lookupTable, n);

    // �������������� 
    Node *root = NULL;
    for (i = 0; i < n; i++)
        insert(&root, lookupTable[i]);

    // ���Բ�ͬ�Ĵ��������� 
    int mValues[] = {1000, 2000, 5000};
    for (j = 0; j < 3; j++) {
        int m = mValues[j];
        int searchData[m];
        for (i = 0; i < m; i++)
            searchData[i] = rand();

        // ˳����� 
        start = clock();
        for (i = 0; i < m; i++)
            seqSearch(lookupTable, n, searchData[i]);
        end = clock();
        seqTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;

        // �۰���� 
        start = clock();
        for (i = 0; i < m; i++)
            binSearch(lookupTable, n, searchData[i]);
        end = clock();
        binTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;

        // �������������� 
        start = clock();
        for (i = 0; i < m; i++)
            search(root, searchData[i]);
        end = clock();
        bstTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;
		printf("-----------------------------------------------------\n"); 
        printf("������������Ϊm=%d��n=%d�����ұ�ʱ\n", m, n);
        printf("˳����ҵ�ʱ��Ϊ�� %.2f ����\n", seqTime);
        printf("�۰���ҵ�ʱ��Ϊ�� %.2f ����\n", binTime);
        printf("�������������ҵ�ʱ��Ϊ�� %.2f ����\n\n", bstTime);
    }

    return 0;
}



