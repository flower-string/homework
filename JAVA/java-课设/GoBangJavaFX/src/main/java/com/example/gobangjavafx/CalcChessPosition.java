package com.example.gobangjavafx;

public class CalcChessPosition {
    /**
     * @Package: com.example.gobangjavafx
     * @Params: 无
     * @Description: 该类是专门用于计算下棋位置的工具类
     * @Author: Misaka19327
     * @DateTime: 2023/6/14 4:33
     */

    private CalcChessPosition() {
        // 私有化构造函数
    }

    public static int[][] getMoves(int[][] chessBoard) {
        /**
         * @Description: 从当前棋盘中 获取所有可以新增棋子的位置
         * @Param: [chessBoard]
         * @return: int[][]
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 6:49
         */
        int count = 0;
        for (int[] array : chessBoard) { // 可被放置棋子的地方数量统计
            for (int i : array) {
                if (i == 0) {
                    count += 1;
                }
            }
        }
        int[][] result = new int[count][2];
        int pointer = 0;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] == 0) {
                    result[pointer] = new int[]{i, j};
                    pointer += 1;
                }
            }
        }
        return result;
    }

    public static void makeMove(int[][] chessBoard, int[] move, int player) {
        /**
         * @Description: 根据传入的move 对棋盘进行新增棋子的操作
         * @Param: [chessBoard, move, player] 棋盘 新增棋子的位置 颜色
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 6:50
         */
        chessBoard[move[0]][move[1]] = player;
    }

    public static void undoMove(int[][] chessBoard, int[] move) {
        /**
         * @Description: 根据传入的move 对棋盘进行删除棋子的操作(撤销)
         * @Param: [chessBoard, move]
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 6:51
         */
        chessBoard[move[0]][move[1]] = 0;
    }

    public static int minimax(int[][] board, int player, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        /**
         * @Description: 极大极小树 + α-β 剪枝算法本体
         * @Param: [board, player, depth, alpha, beta, isMaximizingPlayer] 要评估的棋盘 评估对象 递归深度 剪枝算法中α和β的值 当前是否为极大层
         * @return: int 计算得出的要下棋的存储坐标
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 6:53
         */

        // 极大极小树并不是一个正经的树的数据结构 而是一种递归算法
        // 它的每一次递归都相当于树的一个节点 在同一次函数中执行的所有递归都可被视作树的一层 根节点即为递归的开始
        // 它的每一层都被划分为极大/极小层 用以模拟博弈中双方的意图
        // 以计算五子棋下棋位置为例 如果想要求出的是五子棋黑方最佳的下棋位置 则树除根节点外第一层即为极大树
        // 在这一层 权值要尽可能大 以利于黑方
        // 而下一层模拟的是白方 权值要尽可能小 以利于黑方
        // 再下一层 模拟的是黑方 权值又变成了尽可能大

        // α-β 剪枝算法是对极大极小树的优化算法
        // 因为极大极小树这种搜索算法 需要遍历所有可能
        // 还是以五子棋为例 项目中使用的棋盘共有361个可以下棋的位置
        // 假设递归三层 则需评估的棋盘理论上就有47045881个
        // 必须使用剪枝算法来减少分支 以加快速度
        // 该算法会在极大/极小层将节点的权值与α/β的值进行比较 以确定该分支不可能产生比α更大/比β更小的值

        if (depth == 0) { // 递归到头了
            return GoBangUtil.evaluation(board, player);
        }

        // 极大层和极小层操作几乎相同
        if (isMaximizingPlayer) { // 如果这一层是极大层
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : getMoves(board)) { // 遍历棋盘的所有可能分支
                makeMove(board, move, player);
                int eval = minimax(board, (player == 1) ? 2 : 1,depth - 1, alpha, beta, false); // 递归
                undoMove(board, move);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) { // 这里是重点 以极大层为例 beta是从上一层传过来的 alpha是在这一层更新的
                    break; // 剪枝
                }
            }
            return maxEval; // 求最大权值
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : getMoves(board)) {
                makeMove(board, move, player);
                int eval = minimax(board, (player == 1) ? 2 : 1, depth - 1, alpha, beta, true);
                undoMove(board, move);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    public static int[] findBestMove(int[][] board, int player) {
        /**
         * @Description: 开始调用递归算法
         * @Param: [board, player] 棋盘 棋子颜色
         * @return: int[] 结果的存储坐标
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 7:10
         */
        int[] bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        for (int[] move : getMoves(board)) {
            makeMove(board, move, player);
            int value = minimax(board, player, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            undoMove(board, move);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

}
