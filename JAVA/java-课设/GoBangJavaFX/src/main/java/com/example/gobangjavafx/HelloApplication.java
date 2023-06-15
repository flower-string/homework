package com.example.gobangjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startMenu.fxml"));
        scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("五子棋");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

/**
 * 这里是todo列表
 * completed 在两个窗口之间传递信息 使得游戏可选单人或双人
 * completed 单人模式下指定先手后手
 * important 下次打开要做的:
 * completed 棋局退出时保存棋局为文件 具体为ChessBoardController的onExitButtonClicked方法
 * todo 从文件中读取棋局(可选)
 * completed 基于极大极小树 α-β剪枝算法的自动下棋 具体为GoBangGame的calcTheBestChessPosition方法
 *
 * note:
 * 关于自动下棋 思路如下:
 * 首先 需要一个评估棋局的评估函数 它通过分析当前棋盘再下一子的棋局 得出当前我方的一个具体分数 分数与取胜可能相关联
 * 之后 需要一个α-β剪枝算法 来削减下棋中出现的所有可能性
 * 最后 需要一个博弈树 它存储了计算机方和人类方的可能的下棋的位置和对应的分数 层数约等于难度
 * 树完成之后 在叶子中寻找分数最高的 并且回溯到根节点(即当前棋盘)的最高那个分支的节点 那就是自动下棋的结果
 *
 * 关于树 根节点为当前棋局 第一层为所有计算机方的可能下棋的棋盘和分数 第二层为玩家方的可能下棋的棋盘和分数 以此类推
 * 因此 第一层在选择时分数要尽可能高 第二层在选择时分数要尽可能低
 *
 * 难度与博弈树的高度 树的节点的出度 评估函数等都有关联 一般情况下 高度为4 出度为3 具体还要等完成之后观察
 *
 *
 * 极大极小树并不是一个树 而是一个递归算法 它对一个节点的每个子节点重复应用该算法
 *
 * 在函数中，我们首先检查当前节点是否是终止节点或者搜索深度是否为 0。如果满足这些条件，那么就返回当前节点的启发式值。
 * 接下来，根据 isMaximizingPlayer 的值判断当前是极大层还是极小层。
 * 如果是极大层，那么就枚举当前节点的所有子节点，并对每个子节点递归调用 minimax 函数，取最大值作为当前节点的值。
 * 同时，我们还会更新 alpha 的值，并在每次循环中检查是否满足剪枝条件。
 * 如果满足剪枝条件，那么就直接跳出循环。
 * 如果是极小层，那么就枚举当前节点的所有子节点，并对每个子节点递归调用 minimax 函数，取最小值作为当前节点的值。
 * 同时，我们还会更新 beta 的值，并在每次循环中检查是否满足剪枝条件。
 * 如果满足剪枝条件，那么就直接跳出循环。
 * 最后，返回当前节点的值。
 *
 * 具体思路：
 * 对第一层　采用一个数组存储　每个元素为如果在当前位置下棋的最终权值　对每个元素调用递归函数
 *
 * 递归函数首先计算自身的权值并暂时存储 之后对每个位置递归调用函数 最终自身的权值为每个位置的最高权值
 * 暂时存储的自身权值和α或者β比较 进行剪枝
 *
 * 递归深度设定为3
 *
 */