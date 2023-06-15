package com.example.gobangjavafx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

class ChessStorage {
    /**
     * @Package: com.example.gobangjavafx
     * @Params: 棋子的棋盘坐标, 棋子的种类
     * @Description: 该类用于存储单个棋子的信息 仅用于保存对局记录
     * @Author: Misaka19327
     * @DateTime: 2023/6/9 15:49
     */
    int x, y;
    int chessType; // 棋子的颜色 1为黑色 0为白色
    int time; // 下棋的时间 单位为秒

    public ChessStorage(int[] chessInfo) {
        this.x = chessInfo[0];
        this.y = chessInfo[1];
        this.chessType = chessInfo[2];
        this.time = chessInfo[3];
    }

    @Override
    public boolean equals(Object chessStorage) {
        if (chessStorage instanceof ChessStorage) { // 先判断类型
            // 只比较x和y就够用
            return this.x == ((ChessStorage) chessStorage).x && this.y == ((ChessStorage) chessStorage).y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        // 该类toString的重写
        String type = (chessType == 0) ? "白棋" : "黑棋";
        String position = "(" + x + "," + y + ")";
        String chessTime = "第 " + time + " 秒";
        return type + "方在坐标 " + position + " 处下了一子，时间为" + chessTime + "。";
    }
}

public class GoBangGame {
    /**
     * @Package: com.example.gobangjavafx
     * @Params: 无
     * @Description: 该类是一局五子棋游戏的实例 它负责游戏的所有逻辑
     * @Author: Misaka19327
     * @DateTime: 2023/6/9 15:44
     */
    private LinkedList<int[]> blackChessPosition; // 记录所有黑子的存储坐标 格式为[x, y]
    private LinkedList<int[]> whiteChessPosition; // 记录所有白子的存储坐标 格式为[x, y]
    private LinkedList<ChessStorage> allChessHistory; // 记录所有棋子 一个棋子的信息分别包括它的存储坐标 它的颜色 它的被下的时间

    int color; // 下一个棋子的颜色 1为黑色 0为白色

    public GoBangGame() {
        this.blackChessPosition = new LinkedList<>();
        this.whiteChessPosition = new LinkedList<>();
        this.allChessHistory = new LinkedList<>();
        this.color = 1; // 黑棋先行
    }

    public int[] addNewChess(double[] clickPosition) {
        /**
         * @Description: 该方法用于向棋盘中添加棋子这一过程的逻辑层 它根据用户点击的坐标 添加对应的棋子 并返回该棋子的绘制坐标和颜色种类
         * @Param: [clickPosition] 点击坐标
         * @return: int[] 长度为3的数组 前两位为绘制坐标 后一位为颜色种类 1为黑色 0为白色 如果最后一位为2 则该棋子已存在 不再绘制
         * @Author: Misaka19327
         * @DateTime: 2023/6/9 16:12
         */

        // 返回要绘制棋子的部分
        int[] chessPosition = GoBangUtil.findNearestChessPoint(clickPosition);
        int[] result = new int[3];
        result[0] = chessPosition[0];
        result[1] = chessPosition[1];
        result[2] = color;

        // 判断该棋子是否存在
        ChessStorage chessStorage = new ChessStorage(new int[]{chessPosition[2], chessPosition[3], color, (int) clickPosition[2]});
        if (allChessHistory.contains(chessStorage)) {
            // 在Java的链表中 contains调用的是equals方法来判断是否存在某对象 因此ChessStorage类的equals方法需要重写
            result[2] = 2;
            return result;
        }

        // 记录棋子信息并翻转color
        allChessHistory.add(chessStorage);
        if (color == 1) {
            blackChessPosition.add(new int[] {chessPosition[2], chessPosition[3]});
            color = 0;
        } else if (color == 0) {
            whiteChessPosition.add(new int[] {chessPosition[2], chessPosition[3]});
            color = 1;
        }
        return result;
    }

    public int checkIsWin() {
        /**
         * @Description: 检查当下所存储的所有棋子 判断有无胜负 只检查最新的一步棋子有无胜负即可
         * @Param: [] 无
         * @return: int 返回0时 无人获胜 返回1时 黑棋获胜 返回2时 白棋获胜
         * @Author: Misaka19327
         * @DateTime: 2023/6/9 21:10
         */

        // 好吧我自认是绕了很多远路 这代码写的质量属实垃圾
        ChessStorage newestChess = allChessHistory.get(allChessHistory.size() - 1); // 获取最新一步
        LinkedList<int[]> possiblyChess = new LinkedList<>();
        int judge = 0; // 这次判断对象是黑棋还是白棋 前者为1 后者为2

        // 开始从x, y维度上寻找所有可能构成胜利条件的棋子 并加入possiblyChess中
        if (newestChess.chessType == 1) { // 1为黑 0为白 根据类型不同遍历不同的链表
            for (int[] singleChess : blackChessPosition) {
                if (    // 找出以newestChess为中心的 10x10的正方形区域的棋子
                        newestChess.x - 5 < singleChess[0]
                        && singleChess[0] < newestChess.x + 5
                        && newestChess.y - 5 < singleChess[1]
                        && singleChess[1] < newestChess.y + 5
                ) {
                    possiblyChess.add(singleChess);
                    judge = 1;
                }
            }
        } else if (newestChess.chessType == 0) {
            for (int[] singleChess : whiteChessPosition) {
                if (    // 找出以newestChess为中心的 10x10的正方形区域的棋子
                        newestChess.x - 5 < singleChess[0]
                        && singleChess[0] < newestChess.x + 5
                        && newestChess.y - 5 < singleChess[1]
                        && singleChess[1] < newestChess.y + 5
                ) {
                    possiblyChess.add(singleChess);
                    judge = 2;
                }
            }
        }

        // 开始根据这些缩小范围过后的棋子进行判断 暴力判断
        for (int[] a : possiblyChess) { // 判断同一竖行下有无胜利
            int status = 0;
            for (int i = a[0]; i < a[0] + 5; i++) {
                if (GoBangUtil.linkedListIsContainArray(possiblyChess, new int[]{i, a[1]})) {
                    status++;
                } else {
                    break;
                }
            }
            if (status == 5) {
                return judge;
            }
        }
        for (int[] a : possiblyChess) { // 判断同一横行下有无胜利
            int status = 0;
            for (int i = a[1]; i < a[1] + 5; i++) {
                if (GoBangUtil.linkedListIsContainArray(possiblyChess, new int[]{a[0], i})) {
                    status++;
                } else {
                    break;
                }
            }
            if (status == 5) {
                return judge;
            }
        }
        for (int[] a : possiblyChess) { // 判断同一斜线下有无胜利
            int status = 0;
            for (int i = 0; i <= 5; i++) {
                if (GoBangUtil.linkedListIsContainArray(possiblyChess, new int[]{a[0] + i, a[1] + i})) {
                    status++;
                } else {
                    break;
                }
            }
            if (status == 5) {
                return judge;
            }
        }
        return 0;
    }

    public void saveWholeGame() {
        /**
         * @Description: 该方法用于将整局游戏过程保存在一个txt文件中 作为记录
         * @Param: []
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/14 0:47
         */

        // 获取当前系统时间 并将其作为文件名
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String nowTime = now.format(formatter) + ".txt";
        String fileName = "src/main/java/com/example/gobangjavafx/" + nowTime;

        // 开始写入
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("开始棋局");
            writer.newLine();
            for (ChessStorage singleChess : allChessHistory) {
                writer.write(singleChess.toString());
                writer.newLine();
            }
            writer.write("最终胜利一方为" + ((color == 0) ? "黑棋" : "白棋"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void repentance() {
        /**
         * @Description: 悔棋操作的逻辑层 查找最后一个棋子 并且从那三个存储结构中删除
         * @Param: [] 无
         * @return: void 无
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 21:16
         */
        ChessStorage newestChess = allChessHistory.get(allChessHistory.size() - 1); // 获取最新一步
        if (newestChess.chessType == 1) { // 上一个棋子为黑色
            blackChessPosition.remove(GoBangUtil.linkedListLocateArray(blackChessPosition, new int[]{newestChess.x, newestChess.y}));
        } else if (newestChess.chessType == 0) {
            whiteChessPosition.remove(GoBangUtil.linkedListLocateArray(whiteChessPosition, new int[]{newestChess.x, newestChess.y}));
        }
        allChessHistory.remove(allChessHistory.size() - 1);
        if (this.color == 1) { // 翻转棋子颜色
            this.color = 0;
        } else if (this.color == 0) {
            this.color = 1;
        }
    }

    public int[] calcTheBestChessPosition() {
        /**
         * @Description: 该方法用于计算当前棋局下 如何下棋是可能的优解 并且在最优解位置下棋 在记录中添加棋子 翻转棋子颜色
         * @Param: []
         * @return: int[] 返回给Controller的数组 包括计算机下的棋的绘制坐标 颜色
         * @Author: Misaka19327
         * @DateTime: 2023/6/12 1:23
         */

        int[] newChess = CalcChessPosition.findBestMove(GoBangUtil.convertLinkedListIntoArray(allChessHistory), (color == 1) ? 1 : 2);
        int[] paintPosition = GoBangUtil.calcPaintPosition(newChess);
        int[] returnArray = new int[3];
        returnArray[0] = paintPosition[0];
        returnArray[1] = paintPosition[1];
        returnArray[2] = color;
        allChessHistory.add(new ChessStorage(new int[]{newChess[0], newChess[1], color, 0}));
        if (color == 1) { // 黑棋
            blackChessPosition.add(newChess);
            color = 0;
        } else if (color == 0) {
            whiteChessPosition.add(newChess);
            color = 1;
        }
        return returnArray;
    }
}
