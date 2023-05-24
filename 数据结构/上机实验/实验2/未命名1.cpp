#include<iostream>
using namespace std;
/**
 * 姓名： 刘敬超
 * 学号： 2107090411 
 * 班级： 软件212 
 **/ 
struct TreeNode {
    char val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(char x) : val(x), left(NULL), right(NULL) {}
};

// 创建二叉树 
TreeNode* createTree() {
    char ch;
    cin >> ch;
    if (ch == '#') return NULL;
    TreeNode *node = new TreeNode(ch);
    node->left = createTree();
    node->right = createTree();
    return node;
}

//统计叶子节点个数 
int countLeaves(TreeNode *root) {
    if (!root) return 0;
    if (!root->left && !root->right) return 1;
    return countLeaves(root->left) + countLeaves(root->right);
}

//统计节点总数 
int countNodes(TreeNode *root) {
    if (!root) return 0;
    return 1 + countNodes(root->left) + countNodes(root->right);
}

//先序遍历 
void preOrderTraversal(TreeNode *root) {
    if (!root) return;
    cout << root->val;
    preOrderTraversal(root->left);
    preOrderTraversal(root->right);
}

//中序遍历 
void inOrderTraversal(TreeNode *root) {
    if (!root) return;
    inOrderTraversal(root->left);
    cout << root->val;
    inOrderTraversal(root->right);
}

//后序遍历 
void postOrderTraversal(TreeNode *root) {
    if (!root) return;
    postOrderTraversal(root->left);
    postOrderTraversal(root->right);
    cout << root->val;
}

// 获取二叉树高度 
int getHeight(TreeNode *root) {
    if (!root) return 0;
    int leftHeight = getHeight(root->left);
    int rightHeight = getHeight(root->right);
    return max(leftHeight, rightHeight) + 1;
}

// 层序遍历的工具函数 
void printLevel(TreeNode *root, int level) {
    if (!root) return;
    if (level == 1) {
        cout << root->val;
    } else {
        printLevel(root->left, level - 1);
        printLevel(root->right, level - 1);
    }
}

// 层序遍历 
void levelOrderTraversal(TreeNode *root) {
    int height = getHeight(root);
    for (int i = 1; i <= height; i++) {
        printLevel(root, i);
    }
}
// 统计非叶子节点的个数 
int countNonLeaves(TreeNode *root) {
    if (!root) return 0;
    if (!root->left && !root->right) return 0;
    return 1 + countNonLeaves(root->left) + countNonLeaves(root->right);
}

// 获取二叉树宽度的辅助函数 
void getWidthHelper(TreeNode *root, int level, int *width) {
    if (!root) return;
    width[level]++;
    getWidthHelper(root->left, level + 1, width);
    getWidthHelper(root->right, level + 1, width);
}
// 返回二叉树的宽度
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
    cout << "叶子节点数：" << leaf << endl
		 << "节点总数：" << all << endl
		 << "其他节点个数：" << other << endl
		 << "树的高度：" << height << endl
		 << "树的宽度：" << width << endl; 
    
    cout << "先序遍历的结果为："; 
    preOrderTraversal(root);
    cout << endl;
    
    cout << "中序遍历的结果为：";
    inOrderTraversal(root);
    cout << endl;
    
    cout << "后序遍历的结果为：";
    postOrderTraversal(root);
    cout << endl;
    
    cout << "层次遍历的结果为：";
    levelOrderTraversal(root);
    cout << endl;
	return 0; 
    // ...
}
