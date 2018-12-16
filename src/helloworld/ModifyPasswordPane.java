package helloworld;

import java.sql.*;
import com.jfoenix.controls.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ModifyPasswordPane {
	private JFXPasswordField originPassword = new JFXPasswordField(),
							newPassword = new JFXPasswordField(),
							confirmPassword = new JFXPasswordField();
	
	public Label getTitle() {				//获取标题布局
		Label title = new Label("修改密码");
		title.setAlignment(Pos.CENTER);
		title.setFont(Font.font("Kai", 30));
		
		return title;
	}
	public HBox getButtons() {				//获取按钮布局
		JFXButton confirm = new JFXButton("确认修改"),
				reset = new JFXButton("重  置");
		//添加确认修改按钮的样式
		confirm.setStyle("-fx-background-color:#46456d");
		confirm.setTextFill(Color.WHITE);
		//设置确认修改的单击响应事件
		confirm.setOnAction(e -> {
			Alert err_msg = new Alert(AlertType.ERROR);
			if (originPassword.getText().isEmpty()) {
				err_msg.setContentText("请输入原密码!");
				err_msg.show();
				return ;
			}
			else if (!newPassword.getText().equals(confirmPassword.getText())) {
				err_msg.setContentText("与输入的新密码不一致！");
				err_msg.show();
				return ;
			}
			//获取原密码
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement();
				ResultSet rs = sta.executeQuery("SELECT * FROM admininfo WHERE name='admin'");
				String password = "";
				while (rs.next()) password = rs.getString("password");
				if (!originPassword.getText().equals(password)) {
					err_msg.setContentText("原密码错误!");
					err_msg.show();
				}
				else if (password.equals(newPassword.getText())) {
					err_msg.setContentText("新密码不能与原密码一样!");
					err_msg.show();
				}
				else {
					sta.executeUpdate("UPDATE admininfo SET password='" + newPassword.getText() + "' WHERE name='admin'");
					Alert info_msg = new Alert(AlertType.INFORMATION, "修改成功!");
					info_msg.show();
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		//添加重置按钮的样式
		reset.setStyle("-fx-background-color:#46456d");
		reset.setTextFill(Color.WHITE);
		//设置重置的单击响应事件
		reset.setOnAction(e -> {
			originPassword.setText("");
			newPassword.setText("");
			confirmPassword.setText("");
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER_RIGHT);
		hb.getChildren().addAll(confirm, reset);
		
		return hb;
	}
	public GridPane getControls() {				//获取主控制布局
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(20);
		gp.setHgap(10);
		
		Label lbs[] = new Label[3];
		lbs[0] = new Label("原 密 码");
		lbs[1] = new Label("新 密 码");
		lbs[2] = new Label("确认密码 ");
		for (int i = 0; i < 3; i++) gp.add(lbs[i], 0, i);
		gp.add(originPassword, 1, 0);
		gp.add(newPassword, 1, 1);
		gp.add(confirmPassword, 1, 2);
		gp.add(getButtons(), 1, 3);
		
		return gp;
	}
	public VBox getMainPane() {				//获取主布局
		VBox vb = new VBox(40);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(getTitle(), getControls());
		
		return vb;
	}
}
