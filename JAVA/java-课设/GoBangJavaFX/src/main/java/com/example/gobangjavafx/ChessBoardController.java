package com.example.gobangjavafx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChessBoardController implements Initializable {

    public Button exit; // 退出按钮
    public Button repentance; // 悔棋按钮

    public ImageView chessBoard; // 棋盘图片
    public Pane chessPane; // 用于棋盘图片区域的点击事件触发和显示棋子的布局

    public Text text; // 两个按钮左侧的文字

    public int[] time = {0}; // 用于记录时间的数组

    public Timeline timeline = new Timeline(new KeyFrame(
            // 计时逻辑
            Duration.seconds(1), actionEvent -> {
                time[0] ++;
                text.setText("时间：" + time[0] + " 秒");
    }
    ));
    public Text gameModeText; // 显示游戏模式的文字 单人模式还是双人模式

    GoBangGame goBangGame = new GoBangGame(); // 游戏逻辑层

    int gameMode; // 游戏模式 1就是单人 2就是双人

    int userIsHeadStart; // 在单人模式中指定先手 0就是计算机先手 1就是用户先手

    public void getGameModeData() {
        /**
         * @Description: 从HelloController中获取游戏模式 具体是单人还是双人 如果是单人并且计算机先手的话 在正中心添加一颗黑棋
         * @Param: []
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/12 0:46
         */
        Scene scene = exit.getScene(); // 获取scene实例 因为要传递的信息是绑定在scene中的
        String gameModeInfo = ((String[]) scene.getUserData())[0];
        String gameFirstStart = ((String[]) scene.getUserData())[1];

        if (gameModeInfo.equals("单人模式")) {
            this.gameMode = 1;
            if (gameFirstStart.equals("计算机先手")) {
                // 添加一颗黑棋
                double[] firstChessInfo = new double[]{250, 244, 0};
                int[] firstChess = goBangGame.addNewChess(firstChessInfo);
                Circle circle = new Circle();
                circle.setCenterX(firstChess[0]);
                circle.setCenterY(firstChess[1]);
                circle.setRadius(10);
                circle.setFill((firstChess[2] == 1) ? Color.BLACK : Color.WHITE);
                circle.setVisible(true);
                chessPane.getChildren().add(circle);
            }
        } else if (gameModeInfo.equals("双人模式")) {
            this.gameMode = 2;
        }
        gameModeText.setText(gameModeInfo); // 在界面中显示模式
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //虽然Controller类并没有准确的生命周期 但是该函数仍然会在Controller类初始化时被调用 可以视作init或者start
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); // 开始计时

        Platform.runLater(new Runnable() {
            // 这是一个特殊的方法 它是JavaFX独有的一个静态方法
            // 它的作用是将一个Runnable对象添加到JavaFX应用程序的事件队列中 并在适当的时候运行(来源：bing)
            // 由于initialize方法运行时 该窗口并没有加载scene和stage 而getGameModeData方法需要通过scene获取信息
            // 所以getGameModeData方法并不能直接运行 否则会报NPE
            // 因此需要将这个方法放在runLater中使用
            public void run() {
                getGameModeData();
            }
        });
    }

    @FXML
    protected void onExitButtonClicked() {
        /**
         * @Description: 退出按钮的点击事件 用户点击退出按钮后 执行该方法
         * @Param: []
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 20:23
         */
        Stage nowStage = (Stage) exit.getScene().getWindow();
        nowStage.close();
    }

    public void onRepentanceButtonClicked(ActionEvent actionEvent) {
        /**
         * @Description: 悔棋按钮的点击事件 用户点击悔棋之后 执行该方法
         * @Param: [actionEvent]
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 20:24
         */
        goBangGame.repentance();
        chessPane.getChildren().remove(chessPane.getChildren().size() - 1);
    }

    public void showResultAndExit(int color) {
        /**
         * @Description: 判断获胜后 显示对话框并且调用退出按钮的点击事件 也就是关闭该窗口
         * @Param: [color] 获胜方 1为黑色 2为白色
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/10 23:44
         */
        // 该方法也负责调用GoBangGame的save方法以保存棋局为文件
        timeline.stop();
        goBangGame.saveWholeGame(); // 保存棋局
        Alert endAlert = new Alert(Alert.AlertType.CONFIRMATION); // 新建一个提示对话框
        if (color == 1) {
            endAlert.setTitle("黑棋胜利！"); // 窗口标题文字
            endAlert.setHeaderText("黑棋胜利！"); // 标题文字
        } else if (color == 2) {
            endAlert.setTitle("白棋胜利！");
            endAlert.setHeaderText("白棋胜利！");
        }
        endAlert.setContentText("请点击退出游戏按钮，结束对局。对局记录将自动保存。"); // 内容文字
        ButtonType exit = new ButtonType("退出游戏"); // 按钮类型 可以看作一个返回固定值的按钮
        endAlert.getButtonTypes().setAll(exit); // 将该按钮加入对话框

        Optional<ButtonType> result = endAlert.showAndWait(); // 使提示框显示 并且接收该对话框用户点击按钮的结果
        if (result.isPresent()) { // important 这是判断用户有没有直接关闭对话框 如果直接关闭对话框并且这里不判断的话 下一行会报NoSuchElementException
            if (result.get() == exit) { // 判断是exit那个按钮类型被点击
                onExitButtonClicked(); // 调用退出按钮的点击事件
            }
        } else {
            onExitButtonClicked(); // 即使直接关闭对话框 仍然执行退出的逻辑
        }
    }

    public void onChessBoardClicked(MouseEvent mouseEvent) {
        /**
         * @Description: 棋盘的点击事件 用户点击棋盘后 执行该方法
         * @Param: [mouseEvent]
         * @return: void
         * @Author: Misaka19327
         * @DateTime: 2023/6/9 15:30
         */
        double x = mouseEvent.getSceneX();
        double y = mouseEvent.getSceneY() - 50; // 50是按钮那一行的高
        int[] chessPosition = goBangGame.addNewChess(new double[] {x, y, (double) time[0]}); // 返回棋子绘制坐标 棋子颜色
        if (chessPosition[2] != 2) {
            Circle circle = new Circle(); // 棋子图形实例
            circle.setCenterX(chessPosition[0]); // 棋子位置
            circle.setCenterY(chessPosition[1]);
            circle.setRadius(10); // 棋子半径
            circle.setFill((chessPosition[2] == 1) ? Color.BLACK : Color.WHITE); // 棋子颜色
            circle.setVisible(true); // 棋子可见性
            chessPane.getChildren().add(circle);
        }
        if (goBangGame.checkIsWin() == 1) {
            showResultAndExit(1);
        } else if (goBangGame.checkIsWin() == 2) {
            showResultAndExit(2);
        }
        if (gameMode == 1) { // 只有单人模式才考虑算法来下棋
            int[] calcChessPosition = goBangGame.calcTheBestChessPosition();
            Circle circle2 = new Circle();
            circle2.setCenterX(calcChessPosition[0]);
            circle2.setCenterY(calcChessPosition[1]);
            circle2.setRadius(10);
            circle2.setFill((calcChessPosition[2] == 1) ? Color.BLACK : Color.WHITE);
            circle2.setVisible(true);
            chessPane.getChildren().add(circle2);
        }
        if (goBangGame.checkIsWin() == 1) {
            showResultAndExit(1);
        } else if (goBangGame.checkIsWin() == 2) {
            showResultAndExit(2);
        }
    }
}
