package helloworld;

import javafx.stage.*;
import javafx.scene.layout.*;
import com.jfoenix.controls.*;

public class MenuPane {
	private BorderPane mainPage;
	private Stage currentStage;
	
	public MenuPane(BorderPane mp, Stage stage) {
		mainPage = mp;
		currentStage = stage;
	}
	public JFXListView<JFXButton> getMenu() {
		JFXButton mainMenuItem = new JFXButton("主页"), 
				searchMenuItem = new JFXButton("搜索用户"),
				modifyPassword = new JFXButton("修改密码"),
				exit = new JFXButton("退出登陆");
		//主界面的单击响应事件
		mainMenuItem.setOnAction(e -> {
			MainStage mainStage = new MainStage(mainPage);
			mainPage.setCenter(mainStage.getMainPane());
		});
		//搜索的单击响应事件
		searchMenuItem.setOnAction(e -> {
			SearchPane searchPane = new SearchPane(mainPage);
			mainPage.setCenter(searchPane.getMainPane());
		});
		//修改密码的单击响应事件
		modifyPassword.setOnAction(e -> {
			mainPage.setCenter(new ModifyPasswordPane().getMainPane());
		});
		//退出登陆的单击响应事件
		exit.setOnAction(e -> {
			currentStage.close();
			//返回登陆界面
			LoginStage login = new LoginStage(new Stage());
			login.getStage().show();
		});
		
		JFXListView<JFXButton> menu = new JFXListView<JFXButton>();
		menu.getStyleClass().add("mylistview");
		menu.setStyle("-fx-pref-width:100;-fx-border-color:#a14f64");
		menu.getItems().addAll(mainMenuItem, searchMenuItem, modifyPassword, exit);
		return menu;
	}
}
