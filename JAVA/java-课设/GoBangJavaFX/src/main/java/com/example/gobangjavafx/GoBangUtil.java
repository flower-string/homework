package com.example.gobangjavafx;

import java.util.Arrays;
import java.util.LinkedList;

public final class GoBangUtil {
    /**
     * @Package: com.example.gobangjavafx
     * @Params:
     * @Description: 该类为该课设的工具类
     * @Author: Misaka19327
     * @DateTime: 2023/6/10 23:52
     */
    private GoBangUtil(){
        // 私有化构造函数
    }

    public static int[] findNearestChessPoint(double[] clickedPoint) {
        /**
         * @Description: 该方法将传入的用户点击坐标 转化为准确的棋盘线的交点的坐标(绘制坐标)和该坐标对应的棋子在19x19的棋盘中的坐标(存储坐标)
         * @Param: [clickedPoint]
         * @return: int[] 长度为4 前两个数为绘制坐标 后两个数为存储坐标
         * @Author: Misaka19327
         * @DateTime: 2023/6/9 15:34
         */
        // important 一个棋子的坐标 分为用户点击坐标 绘制坐标(即用户点击的那个位置 如果出现棋子它应该在哪) 存储坐标(第一行第一列即为1, 1)
        int[] result = new int[4];
        // 求x和求y的过程一致
        // 每个小正方形边长为26 可得到一个一段区间对应一个数的关系(3 ~ 29 -> 16)
        int x = (int) clickedPoint[0];
        int y = (int) clickedPoint[1];

        // 以x为例
        // 对于传入的x 先减3 再整除26 得到的结果乘26 最后再加16
        // 这个计算过程中 3对应的是棋盘的边框(准确说是棋盘边和棋盘上最边上的点的对应点击正方形范围的距离)
        // 26对应的是棋盘小格子的长 也可以理解为每个点对应的点击正方形区域的长
        // 16对应的是上面那个3加了13之后的结果 意义上就是棋盘边框离那个棋子点的距离
        x -= 3;
        x = x / 26;
        result[2] = x;
        x = x * 26 + 16;
        y -= 3;
        y = y / 26;
        result[3] = y;
        y = y * 26 + 16;
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static int[] calcPaintPosition(int[] storagePosition) {
        /**
         * @Description: 将棋子的存储坐标转化为绘制坐标
         * @Param: [storagePosition] 存储坐标
         * @return: int[] 绘制坐标
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 5:33
         */
        int[] result = new int[2];
        result[0] = storagePosition[0] * 26 + 16;
        result[1] = storagePosition[1] * 26 + 16;
        return result;
    }

    public static boolean linkedListIsContainArray(LinkedList<int[]> linkedList, int[] array) {
        /**
         * @Description: 对于泛型指定为int数组的链表的contain方法的特化 从调用Object类的equals改为调用Arrays的equals
         * @Param: [linkedList, array] 查找主体 被查找的对象
         * @return: boolean 比较结果
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 15:16
         */
        for (int[] singleArray : linkedList) {
            if (Arrays.equals(singleArray, array)) {
                return true;
            }
        }
        return false;
    }

    public static int linkedListLocateArray(LinkedList<int[]> linkedList, int[] array) {
        /**
         * @Description: 该方法用于在一个链表中寻找并返回一个数组元素的位置
         * @Param: [linkedList, array] 被查询的链表 要查询的数组
         * @return: int 位置 从0开始 如果返回-1则表示该链表中无此元素
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 21:46
         */
        int position = 0;
        for (int[] singleArray : linkedList) {
            if (singleArray[0] == array[0] && singleArray[1] == array[1]) {
                return position;
            }
            position ++;
        }
        return -1;
    }

    public static int[][] convertLinkedListIntoArray(LinkedList<ChessStorage> linkedList) {
        /**
         * @Description: 该方法用于将传入的存着所有棋子的链表转换为二维数组 数组元素 0为无子 1为黑子 2为白子
         * @Param: [linkedList] 存着所有棋子的链表
         * @return: int[][] 二维数组
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 1:18
         */
        int[][] result = new int[19][19];
        for (ChessStorage singleChess : linkedList) {
            result[singleChess.x][singleChess.y] = (singleChess.chessType == 1) ? 1 : 2;
        }
        return result;
    }

    public static int evaluation(int[][] chessBoard, int player) {
        /**
         * @Description: 评估函数 对传入的二维数组代表的棋盘整体进行评估并且得出一个分数
         * @Param: [chessBoard, player] 一个是二维数组代表的棋盘 一个是评估的对象 即是为白棋评估还是为黑棋评估
         * @return: int
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 1:30
         */
        // player 1为黑棋 2为白棋

        // 分解整个棋盘 以下为评估思路
        // 首先 评估整体是一个遍历求和的过程
        // 需要对所有竖线上的元素进行分析 再对所有横线上的元素进行分析 最后再对两个斜线上的元素进行分析
        // 单次分析只分析一行
        // 最后将所有的结果求和并且返回

        int score = 0;

        // 先竖线
        for (int[] array : chessBoard) {
            score += evaluationPerLine(array, player);
        }

        // 再横线
        for (int i = 0; i < chessBoard[0].length; i++) {
            int[] array = new int[chessBoard.length];
            for (int j = 0; j < chessBoard.length; j++) {
                array[j] = chessBoard[j][i];
            }
            score += evaluationPerLine(array, player);
        }

        // 斜线方向
        for (int k = 0; k < chessBoard.length; k++) {
            int[] diagonal1 = new int[k + 1];
            int[] diagonal2 = new int[k + 1];
            for (int i = 0; i <= k; i++) {
                diagonal1[i] = chessBoard[i][k - i]; // 左上到右下
                diagonal2[i] = chessBoard[chessBoard.length - 1 - i][chessBoard.length - 1 - (k - i)]; // 右上到左下
            }
            if (diagonal1.length > 6) {
                score += evaluationPerLine(diagonal1, player);
            }
            if (diagonal2.length > 6) {
                score += evaluationPerLine(diagonal2, player);
            }
        }

        return score;
    }

    public static int evaluationPerLine(int[] line, int player) {
        /**
         * @Description: 评估函数 对传入的棋盘的单行进行评估并且得出一个分数
         * @Param: [line, player] 棋盘的一行 评估对象 即是为白棋评估还是为黑棋评估
         * @return: int
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 1:52
         */
        // player 1为黑棋 2为白棋

        // 单行评估思路如下 如果存在以下情况 即刻返回分数
        // 我方存在活四 得9990分
        // 我方存在冲四 得9980分
        // 对方存在活四 得-9970分
        // 对方存在冲四且对方存在活三 得-9960分
        // 我方存在活三并且对方存在冲四 得9950分

        // 如果不存在上述情况 按如下计分
        // 对于我方而言
        // 存在多个活三 2000 分
        // 存在 1 个活三 200 分
        // 存在冲四 冲四个数 * 10 分
        // 存在活二 活二个数*4
        // 存在冲二 冲二个数*1
        // 最终得分为这些分数相加

        // 对于对方而言
        // 存在多个活三 500 分
        // 存在 1 个活三 100 分
        // 存在冲四 冲四个数 * 10 分
        // 存在活二 活二个数*4
        // 存在冲二 冲二个数*1
        // 最终得分为这些分数相加

        // 最终得分为我方分数 - 对方分数
        // 该评估权值及思路来源为知乎 实现为个人实现

        // 以下为术语所代表得棋形 说明以黑棋为例

        // 活四：四个黑棋连成一线 两端无白棋
        // 冲四：下一步就能胜利的棋形 活四除外 中间可隔断
        // 活三：下一步就能成为活四的棋形 中间可隔断
        // 活二：下一步就能成为活三的棋形 中间可隔断
        // 冲三：三连的左侧和右侧其中一侧有子另一侧无子，或者其中一侧为边界另一侧无子，则当前棋子与线上周围棋子构成棋形冲三
        // 冲二：如果二连左侧和右侧其中一侧有子另一侧无子，或者其中一侧为边界另一侧无子，则当前棋子与线上周围棋子构成棋形冲二

        int oppositePlayer = (player == 1) ? 2 : 1; // 对方

        // 开始判断即刻返回分数的情况
        for (int i = 1; i < line.length - 4; i++) {
            // 我方活四即刻返回9990
            if (line[i - 1] == 0 && line[i] == player && line[i + 1] == player && line[i + 2] == player && line[i + 3] == player && line[i + 4] == 0) {
                return 9990;
            }
        }

        for (int i = 0; i < line.length - 3; i++) {
            // 我方冲四即刻返回9980
            if (line[i] == player && line[i + 1] == player && line[i + 2] == player && line[i + 3] == player) {
                if ((i == 0 || line[i - 1] == 0) || (i == line.length -4 || line[i + 4] ==0)) {
                    return 9980;
                }
            }
        }

        for (int i = 1; i < line.length - 4; i++) {
            // 对方活四即刻返回-9970
            if (line[i - 1] == 0 && line[i] == oppositePlayer && line[i + 1] == oppositePlayer && line[i + 2] == oppositePlayer && line[i + 3] == oppositePlayer && line[i + 4] == 0) {
                return -9970;
            }
        }

        for (int i = 0; i < line.length - 3; i++) {
            // 对方冲四且存在活三 即刻返回-9960
            if (line[i] == oppositePlayer && line[i + 1] == oppositePlayer && line[i + 2] == oppositePlayer && line[i + 3] == oppositePlayer) {
                if ((i == 0 || line[i - 1] == 0) || (i == line.length -4 || line[i + 4] ==0)) {
                    for (int j = 1; j < line.length - 3; j++) {
                        // 对方活三
                        if (line[j - 1] == 0 && line[j] == oppositePlayer && line[j + 1] == oppositePlayer && line[j + 2] == oppositePlayer && line[j + 3] == 0) {
                            return -9960;
                        }
                    }
                }
            }
        }

        for (int i = 1; i < line.length - 3; i++) {
            // 我方存在活三并且对方存在冲四 即刻返回9950分
            if (line[i - 1] == 0 && line[i] == player && line[i + 1] == player && line[i + 2] == player && line[i + 3] == 0) {
                for (int j = 0; j < line.length - 3; j++) {
                    // 对方冲四
                    if (line[j] == oppositePlayer && line[j + 1] == oppositePlayer && line[j + 2] == oppositePlayer && line[j + 3] == oppositePlayer) {
                        if ((j == 0 || line[j - 1] == 0) || (j == line.length -4 || line[j + 4] ==0)) {
                            return 9950;
                        }
                    }
                }
            }
        }

        // 排除掉即刻返回分数的情况 开始计分
        int playerScore = 0; // 我方分数
        int oppositePlayerScore = 0; // 对方分数
        int count = 0; // 这个是计数用的 用于那种”存在多个“的情况 用完需要清空以保证安全

        // 我方存在大于1个活三 记2000分 如果只有一个的话 记200分
        for (int i = 1; i < line.length - 3; i++) {
            if (line[i - 1] == 0 && line[i] == player && line[i + 1] == player && line[i + 2] == player && line[i + 3] == 0) {
                count++;
            }
        }
        if (count > 1) {
            playerScore += 2000;
        } else if (count == 1) {
            playerScore += 200;
        }
        count = 0;

        // 对方存在大于1个活三 记500分 如果只有一个的话 记100分
        for (int i = 1; i < line.length - 3; i++) {
            if (line[i - 1] == 0 && line[i] == oppositePlayer && line[i + 1] == oppositePlayer && line[i + 2] == oppositePlayer && line[i + 3] == 0) {
                count++;
            }
        }
        if (count > 1) {
            oppositePlayerScore += 500;
        } else if (count == 1) {
            playerScore += 100;
        }
        count = 0;

        // 对方存在冲四 每有一个冲四计10分
        for (int i = 0; i < line.length - 3; i++) {
            if (line[i] == oppositePlayer && line[i + 1] == oppositePlayer && line[i + 2] == oppositePlayer && line[i + 3] == oppositePlayer) {
                if ((i == 0 || line[i - 1] == 0) || (i == line.length -4 || line[i + 4] ==0)) {
                    count++;
                }
            }
        }
        oppositePlayerScore += count * 10;
        count = 0;

        // 我方存在活二 每有一个活二计4分
        for (int i = 1; i < line.length - 2; i++) {
            if (line[i - 1] == 0 && line[i] == player && line[i + 1] == player && line[i + 2] == 0) {
                count++;
            }
        }
        playerScore += count * 4;
        count = 0;

        // 对方存在活二 每有一个活二计4分
        for (int i = 1; i < line.length - 2; i++) {
            if (line[i - 1] == 0 && line[i] == oppositePlayer && line[i + 1] == oppositePlayer && line[i + 2] == 0) {
                count++;
            }
        }
        oppositePlayerScore += count * 4;
        count = 0;

        // 我方存在冲二 每有一个冲二计1分
        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] == player && line[i + 1] == player) {
                count++;
            }
        }
        playerScore += count;
        count = 0;

        // 对方存在冲二 每有一个冲二计1分
        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] == oppositePlayer && line[i + 1] == oppositePlayer) {
                count++;
            }
        }
        oppositePlayerScore += count;

        return playerScore - oppositePlayerScore;
    }
}
