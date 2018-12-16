package helloworld;

import java.sql.*;
import javafx.stage.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import com.jfoenix.controls.*;

public class MainStage {
	private JFXTextField inputDormitory = new JFXTextField();
	private boolean isChosen = false;
	private JFXButton chosenDormitoryButton;
	private BorderPane mainPage;
	
	public MainStage(BorderPane mp) {				//构造函数
		mainPage = mp;
	}
	
	public Label getTitle() {				//获取标题布局
		Label title = new Label("寝室列表");
		title.setFont(Font.font("Kai", 30));
		title.setAlignment(Pos.CENTER);
		
		return title;
	}
	public GridPane getDormitories() {				//获取寝室布局
		GridPane dormitories = new GridPane();
		dormitories.setAlignment(Pos.CENTER);
		dormitories.setHgap(10);
		dormitories.setVgap(10);
		try {
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);;
			//添加寝室按钮控件
			for (int i = 1; i <= 3; i++)
				for (int j = 1; j <= 10; j++) {
					JFXButton bt = new JFXButton(String.valueOf(i * 100 + j));
					bt.setPrefWidth(60);
					bt.setPrefHeight(40);
					//统计当前寝室的空余床位数量
					ResultSet rs = sta.executeQuery("SELECT * FROM roominfo WHERE roomid=" + (i * 100 + j));
					int emptyBeds = 0;
					while (rs.next()) {
						for (int i1 = 1; i1 <= 4; i1++)
							if (rs.getString("bed" + i1).equals("无")) emptyBeds++;
					}
					if (emptyBeds == 0) bt.setStyle("-fx-background-color:#a14f64;-fx-border-color:#a14f64; -fx-border-width:3");
					else bt.setStyle("-fx-background-color:#c3b6c7;-fx-border-color:#c3b6c7; -fx-border-width:3");
					//添加按钮响应事件
					bt.setOnAction(e -> {
						//统计当前寝室的空余床位数量
						int emptyBedsCurrent = 0;
						try {
							//连接数据库
							Class.forName("com.mysql.jdbc.Driver");
							Connection con_temp = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
							Statement sta_temp = con_temp.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
							ResultSet rs_current = sta_temp.executeQuery("SELECT * FROM roominfo WHERE roomid=" + bt.getText());
							while (rs_current.next()) {
								for (int i2 = 1; i2 <= 4; i2++)
									if (rs_current.getString("bed" + i2).equals("无")) emptyBedsCurrent++;
							}
							//关闭数据库
							rs_current.close(); sta_temp.close(); con_temp.close();
						} catch (SQLException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						boolean fullCurrent = (emptyBedsCurrent == 0 ? true : false);
						//之前有选中过寝室
						if (isChosen) {
							//统计之前选中寝室的空余床位数量
							int emptyBedsChosen = 0;
							try {
								//连接数据库
								Class.forName("com.mysql.jdbc.Driver");
								Connection con_chosen = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
								Statement sta_chosen = con_chosen.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
								ResultSet rs_chosen = sta_chosen.executeQuery("SELECT * FROM roominfo WHERE roomid=" + chosenDormitoryButton.getText());
								while (rs_chosen.next()) {
									for (int i2 = 1; i2 <= 4; i2++)
										if (rs_chosen.getString("bed" + i2).equals("无")) emptyBedsChosen++;
								}
								//关闭数据库
								rs_chosen.close(); sta_chosen.close(); con_chosen.close();
							} catch (SQLException | ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							boolean fullChosen = (emptyBedsChosen == 0 ? true : false);
							//之前选中的寝室与当前寝室一样（取消选择）
							if (chosenDormitoryButton.getText().equals(bt.getText())) {
								isChosen = false;
								inputDormitory.setText("");
								if (!fullChosen) chosenDormitoryButton.setStyle("-fx-background-color:#c3b6c7;-fx-border-color:#c3b6c7; -fx-border-width:3");
								else chosenDormitoryButton.setStyle("-fx-background-color:#a14f64;-fx-border-color:#a14f64;-fx-border-width:3");
							}
							//之前选中的寝室与当前寝室不一样
							else {
								//根据之前选中的寝室床位剩余数量判断颜色
								if (!fullChosen) chosenDormitoryButton.setStyle("-fx-background-color:#c3b6c7;-fx-border-color:#c3b6c7; -fx-border-width:3");
								else chosenDormitoryButton.setStyle("-fx-background-color:#a14f64;-fx-border-color:#a14f64;-fx-border-width:3");
								//根据当前寝室床位剩余数量判断颜色
								if (!fullCurrent) bt.setStyle("-fx-background-color:#c3b6c7;-fx-border-color:#bc96a4; -fx-border-width:3");
								else bt.setStyle("-fx-background-color:#a14f64;-fx-border-color:#bc96a4;-fx-border-width:3");
								isChosen = true;
								chosenDormitoryButton = bt;
								inputDormitory.setText(chosenDormitoryButton.getText());
							}
						}
						//之前未选中过别的寝室
						else {
							//根据当前寝室床位剩余数量判断颜色
							if (!fullCurrent) bt.setStyle("-fx-background-color:#c3b6c7;-fx-border-color:#bc96a4; -fx-border-width:3");
							else bt.setStyle("-fx-background-color:#a14f64;-fx-border-color:#bc96a4;-fx-border-width:3");
							isChosen = true;
							chosenDormitoryButton = bt;
							inputDormitory.setText(chosenDormitoryButton.getText());
						}
					});
					dormitories.add(bt, j - 1, i - 1);
				}
			sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		return dormitories;
	}
	public HBox getShapes() {				//获取右侧寝室示意图布局
		Rectangle fullRec = new Rectangle(0, 0, 30, 20),
				unfullRec = new Rectangle(0, 0, 30, 20);
		fullRec.setFill(Color.rgb(161, 79, 100));
		unfullRec.setFill(Color.rgb(195, 182, 199));
		
		HBox hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(fullRec, new Label("已满"), unfullRec, new Label("未满"));
		
		return hb;
	}
	public HBox getControls() {				//获取按钮控件布局
		inputDormitory.setEditable(false);
		JFXButton checkinButton = new JFXButton("办理入住"),
				checkoutButton = new JFXButton("办理退房");
		//添加办理入住按钮的样式
		checkinButton.setTextFill(Color.WHITE);
		checkinButton.setStyle("-fx-background-color:#46456d");
		//添加办理入住的单击响应事件
		checkinButton.setOnAction(e -> {
			if (isChosen) {
				CheckinPane checkinpane = new CheckinPane(chosenDormitoryButton.getText(), mainPage);
				mainPage.setCenter(checkinpane.getMainPane());
			}
			else {
				Alert msg = new Alert(AlertType.INFORMATION, "请选择寝室！");
				msg.show();
			}
		});
		//添加办理退房按钮的样式
		checkoutButton.setTextFill(Color.WHITE);
		checkoutButton.setStyle("-fx-background-color:#46456d");
		//添加办理退房的单击响应事件
		checkoutButton.setOnAction(e -> {
			if (isChosen) {
				CheckoutPane checkoutpane = new CheckoutPane(chosenDormitoryButton.getText(), mainPage);
				mainPage.setCenter(checkoutpane.getMainPane());
			}
			else {
				Alert msg = new Alert(AlertType.INFORMATION, "请选择寝室！");
				msg.show();
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(new Label("选择的寝室："), inputDormitory, checkinButton, checkoutButton);
		
		return hb;
	}
	public VBox getMainPane() {				//获取主布局
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(getTitle(), getDormitories(), getShapes(), getControls());
		
		return vb;
	}
	public Stage getStage() {				//获取主界面
		Stage stage = new Stage();
		
		mainPage.setLeft(new MenuPane(mainPage, stage).getMenu());
		mainPage.setCenter(getMainPane());
		BorderPane.setAlignment(mainPage.getCenter(), Pos.CENTER);
		
		stage.setTitle("寝室管理系统");
		stage.setScene(new Scene(mainPage, 900, 400));
		
		return stage;
	}
}