#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// 定义树节点 
typedef struct node {
    int data;
    struct node *left;
    struct node *right;
} Node;

// 简单查找 
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

// 创建节点 
Node *createNode(int data) {
    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->data = data;
    newNode->left = NULL;
    newNode->right = NULL;
    return newNode;
}

// 插入节点 
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

// 中序遍历 
void inorder(Node *root) {
    if (root == NULL)
        return;
    inorder(root->left);
    printf("%d ", root->data);
    inorder(root->right);
}

// 二叉树查找 
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


// 顺序查找 
int seqSearch(int arr[], int len, int data) {
    int i;
    for (i = 0; i < len; i++)
        if (arr[i] == data)
            return 1;
    return 0;
}

// 折半查找 
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

    // 创建查找表 
    int n = 10000;
    int lookupTable[n];
    for (i = 0; i < n; i++)
        lookupTable[i] = rand() % 100000;

    simpleSelectionSort(lookupTable, n);

    // 创建二叉搜索树 
    Node *root = NULL;
    for (i = 0; i < n; i++)
        insert(&root, lookupTable[i]);

    // 测试不同的待查找数据 
    int mValues[] = {1000, 2000, 5000};
    for (j = 0; j < 3; j++) {
        int m = mValues[j];
        int searchData[m];
        for (i = 0; i < m; i++)
            searchData[i] = rand();

        // 顺序查找 
        start = clock();
        for (i = 0; i < m; i++)
            seqSearch(lookupTable, n, searchData[i]);
        end = clock();
        seqTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;

        // 折半查找 
        start = clock();
        for (i = 0; i < m; i++)
            binSearch(lookupTable, n, searchData[i]);
        end = clock();
        binTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;

        // 二叉排序树查找 
        start = clock();
        for (i = 0; i < m; i++)
            search(root, searchData[i]);
        end = clock();
        bstTime = ((double)(end - start)) / CLOCKS_PER_SEC * 1000;
		printf("-----------------------------------------------------\n"); 
        printf("当待查找数据为m=%d和n=%d（查找表）时\n", m, n);
        printf("顺序查找的时间为： %.2f 毫秒\n", seqTime);
        printf("折半查找的时间为： %.2f 毫秒\n", binTime);
        printf("二叉排序树查找的时间为： %.2f 毫秒\n\n", bstTime);
    }

    return 0;
}



