package helloworld;

//2018.12.15 13:04

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		//初始显示登陆界面
		LoginStage login = new LoginStage(primaryStage);
		login.getStage().show();
	}
	public static void main(String args[]) {
		launch(args);
	}
}
