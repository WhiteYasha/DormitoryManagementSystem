package helloworld;

import java.sql.*;
import javafx.stage.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import com.jfoenix.controls.*;

public class LoginStage {
	private Stage currentStage;
	private JFXTextField tf1 = new JFXTextField();
	private JFXPasswordField tf2 = new JFXPasswordField();
	
	public LoginStage(Stage stage) {				//构造函数
		currentStage = stage;
	}
	
	public HBox getControls() {				//获取按钮控件布局
		JFXButton login = new JFXButton("登陆");
		//设置登陆按钮的样式
		login.setTextFill(Color.WHITE);
		login.setStyle("-fx-background-color: #46456d");
		//设置登陆按钮的单击响应事件
		login.setOnAction(e -> {
			if (tf1.getText().isEmpty()) {
				Alert msg = new Alert(AlertType.ERROR, "请输入用户名!");
				msg.show();
			}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String userName = tf1.getText(),
						userPassword = tf2.getText();
				ResultSet rs = sta.executeQuery("SELECT * FROM admininfo WHERE name='" + userName + "'");
				while (rs.next()) {
					if (rs.getString("password").equals(userPassword)) {
						MainStage mainStage = new MainStage(new BorderPane());
						currentStage.close();
						mainStage.getStage().show();
					}
					else {
						Alert msg = new Alert(AlertType.ERROR, "密码错误！");
						msg.show();
					}
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		JFXButton exit = new JFXButton("退出");
		//设置退出按钮的样式
		exit.setTextFill(Color.WHITE);
		exit.setStyle("-fx-background-color: #46456d");
		//设置退出按钮的单击响应事件
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(login, exit);
		
		return hb;
	}
	public VBox getMainPane() {				//获取主布局
		HBox hb1 = new HBox(10), hb2 = new HBox(10);
		Label l1 = new Label("用户名"), l2 = new Label("密   码"), title = new Label("寝室管理系统");
		
		title.setFont(Font.font("Kai", 30));
		title.setStyle("-fx-padding:20");
		
		hb1.getChildren().addAll(l1, tf1);
		hb1.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(l2, tf2);
		hb2.setAlignment(Pos.CENTER);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(title, hb1, hb2, getControls());
		
		return vb;
	}
	public Stage getStage() {				//获取主界面
		currentStage.setTitle("寝室管理系统");
		Scene scene = new Scene(getMainPane(), 400, 300);
		currentStage.setScene(scene);
		
		return currentStage;
	}
}
