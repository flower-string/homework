package com.example.gobangjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    public ChoiceBox<String> gameModeChoiceBox; // 选择游戏模式的选择框
    public CheckBox gameFirstStart; // 选择先手后手的复选框
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize() {
        // 同ChessBoardController类中的initialize方法
        // 设置该选择框的默认值 默认为单人模式
        gameModeChoiceBox.setValue("单人模式");
    }

    @FXML
    protected void onStartButtonClick() throws Exception {
        // 启动棋盘窗口
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chessBoard.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);

        // 跨Stage传递信息
        String[] gameMode = new String[] {gameModeChoiceBox.getValue(), gameFirstStart.isSelected()?"计算机先手":"玩家先手"};
        scene.setUserData(gameMode);

        stage.setScene(scene);
        stage.setTitle("五子棋");
        stage.show();
    }
}