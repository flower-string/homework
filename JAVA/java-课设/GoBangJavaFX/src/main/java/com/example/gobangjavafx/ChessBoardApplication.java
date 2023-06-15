package com.example.gobangjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessBoardApplication extends Application {
    private Scene scene;
    private Stage stage = new Stage();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chessBoard.fxml"));
        scene = new Scene(fxmlLoader.load(), 500, 550);
        stage.setTitle("五子棋");
        stage.setScene(scene);
        stage.show();
    }

    public void showChessBoard() throws IOException {
        start(stage);
    }
}
