#include<iostream>
using namespace std;
/**
 * ������ ������
 * ѧ�ţ� 2107090411 
 * �༶�� ���212 
 **/ 
struct TreeNode {
    char val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(char x) : val(x), left(NULL), right(NULL) {}
};

// ���������� 
TreeNode* createTree() {
    char ch;
    cin >> ch;
    if (ch == '#') return NULL;
    TreeNode *node = new TreeNode(ch);
    node->left = createTree();
    node->right = createTree();
    return node;
}

//ͳ��Ҷ�ӽڵ���� 
int countLeaves(TreeNode *root) {
    if (!root) return 0;
    if (!root->left && !root->right) return 1;
    return countLeaves(root->left) + countLeaves(root->right);
}

//ͳ�ƽڵ����� 
int countNodes(TreeNode *root) {
    if (!root) return 0;
    return 1 + countNodes(root->left) + countNodes(root->right);
}

//������� 
void preOrderTraversal(TreeNode *root) {
    if (!root) return;
    cout << root->val;
    preOrderTraversal(root->left);
    preOrderTraversal(root->right);
}

//������� 
void inOrderTraversal(TreeNode *root) {
    if (!root) return;
    inOrderTraversal(root->left);
    cout << root->val;
    inOrderTraversal(root->right);
}

//������� 
void postOrderTraversal(TreeNode *root) {
    if (!root) return;
    postOrderTraversal(root->left);
    postOrderTraversal(root->right);
    cout << root->val;
}

// ��ȡ�������߶� 
int getHeight(TreeNode *root) {
    if (!root) return 0;
    int leftHeight = getHeight(root->left);
    int rightHeight = getHeight(root->right);
    return max(leftHeight, rightHeight) + 1;
}

// ��������Ĺ��ߺ��� 
void printLevel(TreeNode *root, int level) {
    if (!root) return;
    if (level == 1) {
        cout << root->val;
    } else {
        printLevel(root->left, level - 1);
        printLevel(root->right, level - 1);
    }
}

// ������� 
void levelOrderTraversal(TreeNode *root) {
    int height = getHeight(root);
    for (int i = 1; i <= height; i++) {
        printLevel(root, i);
    }
}
// ͳ�Ʒ�Ҷ�ӽڵ�ĸ��� 
int countNonLeaves(TreeNode *root) {
    if (!root) return 0;
    if (!root->left && !root->right) return 0;
    return 1 + countNonLeaves(root->left) + countNonLeaves(root->right);
}

// ��ȡ��������ȵĸ������� 
void getWidthHelper(TreeNode *root, int level, int *width) {
    if (!root) return;
    width[level]++;
    getWidthHelper(root->left, level + 1, width);
    getWidthHelper(root->right, level + 1, width);
}
// ���ض������Ŀ��
int getWidth(TreeNode *root) {
    if (!root) return 0;
    int height = getHeight(root);
    int *width = new int[height]();
    getWidthHelper(root, 0, width);
    int maxWidth = 0;
    for (int i = 0; i < height; i++) {
        maxWidth = max(maxWidth, width[i]);
    }
    delete[] width;
    return maxWidth;
}

int main() {
    TreeNode *root = createTree();
    int leaf = countLeaves(root);
    int all = countNodes(root);
    int height = getHeight(root);
	int width = getWidth(root);
	int other =  countNonLeaves(root);
    cout << "Ҷ�ӽڵ�����" << leaf << endl
		 << "�ڵ�������" << all << endl
		 << "�����ڵ������" << other << endl
		 << "���ĸ߶ȣ�" << height << endl
		 << "���Ŀ�ȣ�" << width << endl; 
    
    cout << "��������Ľ��Ϊ��"; 
    preOrderTraversal(root);
    cout << endl;
    
    cout << "��������Ľ��Ϊ��";
    inOrderTraversal(root);
    cout << endl;
    
    cout << "��������Ľ��Ϊ��";
    postOrderTraversal(root);
    cout << endl;
    
    cout << "��α����Ľ��Ϊ��";
    levelOrderTraversal(root);
    cout << endl;
	return 0; 
    // ...
}
