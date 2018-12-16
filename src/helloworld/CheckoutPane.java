package helloworld;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import com.jfoenix.controls.*;

public class CheckoutPane {
	private BorderPane mainPage;
	private String chosenDormitory;
	private int chosenBed;
	private JFXButton beds[] = new JFXButton[4],
					chosenButton;
	private boolean isChosenBed = false;
	private Label bedLabel[] = new Label[4],
					chosenBedLabel;
	private Student bedsInfo[] = new Student[4];
	
	public CheckoutPane(String cd, BorderPane mp) {
		chosenDormitory = cd;
		mainPage = mp;
	}
	
	public Label getTitle() {				//获取标题
		Label title = new Label("退房登记");
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(30, 0, 0, 0));
		title.setFont(Font.font("Kai", 30));
		
		return title;
	}
	public void setButton(JFXButton bt, int index) {				//设置床位选择按钮
		//设置基本样式
		bt.setAlignment(Pos.CENTER);
		bt.setPrefWidth(50);
		bt.setPrefHeight(60);
		bt.setLayoutX(index % 2 == 0 ? 100 : 170);
		bt.setLayoutY(index < 2 ? 85 : 160);
		//根据床位是否已被占用判断标签背景颜色
		if (bedsInfo[index].getInfoName().equals("无")) bt.setStyle("-fx-background-color:#c3b6c7");
		else bt.setStyle("-fx-background-color:#a14f64");
		//设置按钮的单击选中响应事件
		bt.setOnAction(e -> {
			if (isChosenBed) {				//之前已选中床位
				//判断之前选中床位是否为空
				boolean isChosenFull = false;
				if (bedsInfo[Integer.valueOf(chosenBed) - 1].getInfoName().equals("无")) isChosenFull = false;
				else isChosenFull = true;
				if (chosenButton.getText().equals(bt.getText())) {				//之前选中同个床位，取消选择
					isChosenBed = false;
					chosenBedLabel.setText("未选择床位");
					if (isChosenFull) chosenButton.setStyle("-fx-background-color:#a14f64");
					else chosenButton.setStyle("-fx-background-color:#c3b6c7");
				}
				else {				//选择当前床位
					if (isChosenFull) chosenButton.setStyle("-fx-background-color:#a14f64");
					else chosenButton.setStyle("-fx-background-color:#c3b6c7");
					chosenBed = index + 1;
					chosenBedLabel.setText("选择了" + chosenBed + "号床位");
					chosenButton = bt;
					chosenButton.setStyle("-fx-background-color:#bc96a4");
				}
			}
			else {				//之前未选中床位
				isChosenBed = true;
				chosenBed = index + 1;
				chosenBedLabel.setText("选择了" + chosenBed + "号床位");
				chosenButton = bt;
				chosenButton.setStyle("-fx-background-color:#bc96a4");
			}
		});
	}
	public Pane getShapes() {				//获取左侧图形布局
		Pane pane = new Pane();
		pane.setPadding(new Insets(0, 50, 0, 0));
		
		Label notused = new Label("未使用"),
				used = new Label("已使用"),
				balcony = new Label("阳台");
		//绘制阳台样式
		balcony.setTextFill(Color.WHITE);
		balcony.setPrefWidth(120);
		balcony.setPrefHeight(30);
		balcony.setStyle("-fx-background-color: #543a4e");
		balcony.setAlignment(Pos.CENTER);
		
		JFXRippler balconyRippler = new JFXRippler(balcony);
		balconyRippler.setLayoutX(100);
		balconyRippler.setLayoutY(40);
		pane.getChildren().add(balconyRippler);
		//绘制矩形样式
		Rectangle useRec = new Rectangle(240, 40, 20, 30),
				notuseRec = new Rectangle(240, 85, 20, 30);
		useRec.setFill(Color.rgb(161, 79, 100));
		notuseRec.setFill(Color.rgb(195, 182, 199));
		pane.getChildren().addAll(useRec, notuseRec);
		
		used.setPrefHeight(30);
		used.setLayoutX(265);
		used.setLayoutY(40);
		pane.getChildren().add(used);
		
		notused.setPrefHeight(30);
		notused.setLayoutX(265);
		notused.setLayoutY(85);
		pane.getChildren().add(notused);
		//绘制床位
		for (int i = 0; i < 4; i++) {
			beds[i] = new JFXButton("0" + (i + 1) + "号\n床\n");
			setButton(beds[i], i);
			pane.getChildren().add(beds[i]);
		}
		
		return pane;
	}
	public void setBedLabel(Label l, int index) {				//设置床位信息标签
		try {
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs_room = sta.executeQuery("SELECT * FROM roominfo WHERE roomid=" + chosenDormitory);
			//初始化床位信息类
			bedsInfo[index] = new Student();
			bedsInfo[index].setBedIndex(index + 1);
			while (rs_room.next()) {
				bedsInfo[index].setInfoName(rs_room.getString("bed" + (index + 1)));
			}
			ResultSet rs_student = sta.executeQuery("SELECT * FROM studentinfo WHERE stuName='" + bedsInfo[index].getInfoName() + "'"); 
			while (rs_student.next()) {
				l.setText(l.getText() + " " + rs_student.getString("stuid"));
				bedsInfo[index].setInfoId(rs_student.getString("stuid"));
				
				l.setText(l.getText() + " " + rs_student.getString("colleget"));
				bedsInfo[index].setInfoCollege(rs_student.getString("colleget"));
				
				l.setText(l.getText() + " " + rs_student.getString("department"));
				bedsInfo[index].setInfoDepartment(rs_student.getString("department"));
				
				l.setText(l.getText() + " " + rs_student.getString("class"));
				bedsInfo[index].setInfoClass(rs_student.getString("class"));
			}
			l.setText(l.getText() + " " + bedsInfo[index].getInfoName());
			//关闭数据库
			rs_student.close(); rs_room.close(); 
			sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public JFXTabPane getBeds() {				//获取右侧床位信息布局
		JFXTabPane tabPane = new JFXTabPane();
		tabPane.setPrefWidth(400);
		tabPane.setPadding(new Insets(50, 100, 0, 0));
		
		for (int i = 0; i < 4 ; i++) {
			bedLabel[i] = new Label("0" + (i + 1) + "号床： ");
			setBedLabel(bedLabel[i], i);
			Tab tempTab = new Tab(String.valueOf(i + 1));
			tempTab.setStyle("-fx-pref-width:72");
			tempTab.setContent(bedLabel[i]);
			tabPane.getTabs().add(tempTab);
		}
		
		return tabPane;
	}
	public HBox getInformation() {				//获取当前寝室和床位信息布局
		chosenBedLabel = new Label("未选中寝室");
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		
		hb.getChildren().add(new Label("选择的寝室是： " + chosenDormitory));
		hb.getChildren().add(chosenBedLabel);
		
		return hb;
	}
	public VBox getControls() {				//获取下方按钮及信息布局
		JFXButton checkout = new JFXButton("确认退房"),
				back = new JFXButton("返回主页");
		//设置退房按钮的样式
		checkout.setTextFill(Color.WHITE);
		checkout.setStyle("-fx-background-color:#46456d");
		//设置退房按钮的单击响应事件
		checkout.setOnAction(e -> {
			if (!isChosenBed) {				//未选择床位
				Alert msg = new Alert(AlertType.INFORMATION, "请选择床位!");
				msg.show();
				return ;
			}
			else if (bedsInfo[chosenBed - 1].getInfoName().equals("无")) {
				//选择的床位无人居住
				Alert msg = new Alert(AlertType.ERROR, "该床位无人居住!");
				msg.show();
				return ;
			}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				//更新数据库信息
				sta.executeUpdate("UPDATE studentinfo SET isCheckin=0 WHERE stuId=" + bedsInfo[chosenBed - 1].getInfoId());
				sta.executeUpdate("UPDATE roominfo SET bed" + chosenBed + "='无' WHERE roomid=" + chosenDormitory);
				//更新页面信息
				bedsInfo[chosenBed - 1].setInfoName("无");
				beds[chosenBed - 1].setStyle("-fx-background-color:#c3b6c7");
				bedLabel[chosenBed - 1].setText("0" + chosenBed + "号床： 无");
				Alert msg = new Alert(AlertType.CONFIRMATION, "退房成功!");
				msg.show();
				//关闭数据库
				sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		//设置返回按钮的样式
		back.setTextFill(Color.WHITE);
		back.setStyle("-fx-background-color:#46456d");
		//设置返回按钮的单击响应事件
		back.setOnAction(e -> {
			MainStage mainStage = new MainStage(mainPage);
			mainPage.setCenter(mainStage.getMainPane());
		});
		//将按钮添加到布局中
		HBox hb = new HBox(20);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(checkout, back);

		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(hb, getInformation());
		vb.setPadding(new Insets(0, 0, 20, 0));
		
		return vb;
	}
	public BorderPane getMainPane() {				//获取主布局
		BorderPane MainPane = new BorderPane();
		MainPane.setTop(getTitle());
		MainPane.setRight(getBeds());
		MainPane.setLeft(getShapes());
		MainPane.setBottom(getControls());
		
		BorderPane.setAlignment(MainPane.getTop(), Pos.CENTER);
		BorderPane.setAlignment(MainPane, Pos.CENTER);
		
		return MainPane;
	}
}