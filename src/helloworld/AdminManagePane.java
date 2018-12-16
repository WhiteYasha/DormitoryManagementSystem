package helloworld;

import java.sql.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.control.cell.*;
import com.jfoenix.controls.*;

public class AdminManagePane {
	private JFXTextField idField = new JFXTextField();
	private JFXPasswordField passwordField = new JFXPasswordField();
	private TableView adminTable;
	private ObservableList<Admin> tableModel;
	
	public HBox getControls() {				//获取输入及控制布局
		JFXButton register = new JFXButton("注册");
		Label idLabel = new Label("用户名："),
				passwordLabel = new Label("密码：");
		//添加注册按钮的样式
		register.setStyle("-fx-background-color:#46456d");
		register.setTextFill(Color.WHITE);
		//设置注册按钮的单击响应事件
		register.setOnAction(e -> {
			Alert msg;
			if (idField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				//有输入框为空
				msg = new Alert(AlertType.ERROR, "信息未填写完整!");
				msg.show();
				return ;
			}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = sta.executeQuery("SELECT * FROM admininfo WHERE name='" + idField.getText() + "'");
				rs.last();
				int rowCount = rs.getRow();
				if (rowCount != 0) {				//用户名重复
					msg = new Alert(AlertType.ERROR, "用户名已存在！");
					msg.show();
				}
				else {
					//添加新用户
					sta.executeUpdate("INSERT INTO admininfo (name,password) VALUES ('" + idField.getText() + 
							"','" + passwordField.getText() + "')");
					//更新表格
					tableModel = FXCollections.observableArrayList();
					rs = sta.executeQuery("SELECT * FROM admininfo");
					while (rs.next()) {
						Admin admin = new Admin(rs.getString("name"), rs.getString("password"));
						admin.setButton(getButton(admin));
						tableModel.add(admin);
					}
					adminTable.setItems(tableModel);
					msg = new Alert(AlertType.CONFIRMATION, "注册成功！");
					msg.show();
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(idLabel, idField, passwordLabel, passwordField, register);
		return hb;
	}
	public JFXButton getButton(Admin admin) {				//设置删除按钮
		JFXButton button = new JFXButton("删除");
		//添加样式
		button.setTextFill(Color.WHITE);
		button.setPrefWidth(100);
		button.setStyle("-fx-background-color:#46456d");
		//设置单击响应事件
		button.setOnAction(e -> {
			Alert msg;
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = sta.executeQuery("SELECT * FROM admininfo");
				rs.last();
				int sumRow = rs.getRow();
				rs.first();
				if (sumRow == 1) {				//只剩下一个用户
					msg = new Alert(AlertType.ERROR, "账号无法为空！");
					msg.show();
				}
				else {
					//删除用户
					sta.executeUpdate("DELETE FROM admininfo WHERE name='" + admin.getName() + "' AND password='" + admin.getPassword() + "'");
					//更新删除后的表格
					tableModel = FXCollections.observableArrayList();
					rs = sta.executeQuery("SELECT * FROM admininfo");
					while (rs.next()) {
						Admin tmp = new Admin(rs.getString("name"), rs.getString("password"));
						tmp.setButton(getButton(tmp));
						tableModel.add(tmp);
					}
					adminTable.setItems(tableModel);
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		return button;
	}
	public TableView getTable() {				//获取表格
		adminTable = new TableView();
		tableModel = FXCollections.observableArrayList();
		//添加表格列
		TableColumn tcs[] = new TableColumn[3];
		tcs[0] = new TableColumn("用户名");
		tcs[0].setCellValueFactory(new PropertyValueFactory<Admin, String>("name"));
		tcs[1] = new TableColumn("密码");
		tcs[1].setCellValueFactory(new PropertyValueFactory<Admin, String>("password"));
		tcs[2] = new TableColumn<Admin, JFXButton>("");
		tcs[2].setCellValueFactory(new PropertyValueFactory<Admin, JFXButton>("button"));
		for (int i = 0; i < 3; i++) {
			tcs[i].setPrefWidth(200);
			adminTable.getColumns().add(tcs[i]);
		}
		//添加数据
		try {
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sta.executeQuery("SELECT * FROM admininfo");
			while (rs.next()) {
				Admin admin = new Admin(rs.getString("name"), rs.getString("password"));
				admin.setButton(getButton(admin));
				tableModel.add(admin);
			}
			adminTable.setItems(tableModel);
			//关闭数据库
			rs.close(); sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return adminTable;
	}
	public VBox getMainPane() {				//获取主布局
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(getControls(), getTable());
		vb.setPadding(new Insets(10, 10, 10, 10));
		
		return vb;
	}
}
