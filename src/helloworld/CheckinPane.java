package helloworld;

import java.sql.*;
import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;

public class CheckinPane {
	private String chosenDormitory;				//选中的寝室
	private int emptyBeds;				//空余的床位数量
	private BorderPane mainPage;				//用于切换的主页面
	private JFXTextField tfs[] = new JFXTextField[5];
	private JFXComboBox<Label> bedCombo = new JFXComboBox<Label>();
	private Label beds[] = new Label[4];
	private Label chosenDormitoryLabel, emptyBedsLabel;
	
	public CheckinPane(String str, BorderPane mp) {				//构造函数
		chosenDormitory = str;
		mainPage = mp;
		//获取当前寝室空余床位数
		try {
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sta.executeQuery("SELECT * FROM roominfo where roomid=" + chosenDormitory);
			//开始统计当前寝室的空床位
			while (rs.next()) {
				if (rs.getString("bed1").equals("无")) emptyBeds++;
				if (rs.getString("bed2").equals("无")) emptyBeds++;
				if (rs.getString("bed3").equals("无")) emptyBeds++;
				if (rs.getString("bed4").equals("无")) emptyBeds++;
			}
			//关闭数据库
			rs.close(); sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Label getTitle() {				//获取标题布局
		Label title = new Label("入住登记");
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(30, 0, 0, 0));
		title.setFont(Font.font("Kai", 30));
		
		return title;
	}
	public GridPane getInputs() {				//获取输入控件和文本控件布局
		Label lbs[] = new Label[6];
		lbs[0] = new Label("学号");
		lbs[1] = new Label("姓名");
		lbs[2] = new Label("学院");
		lbs[3] = new Label("系别");
		lbs[4] = new Label("班级");
		lbs[5] = new Label("床号");
		
		GridPane inputs = new GridPane();
		inputs.setHgap(20);
		inputs.setVgap(20);
		inputs.setAlignment(Pos.CENTER);
		//添加标签控件
		for (int i = 0; i < 6; i++) {
			inputs.add(lbs[i], i % 2 == 0 ? 0 : 2, i / 2);
			if (i < 5) {
				tfs[i] = new JFXTextField();
				tfs[i].setPrefWidth(150);
				inputs.add(tfs[i], i % 2 == 0 ? 1 : 3, i / 2);
			}
		}
		//添加下拉选择栏
		for (int i = 1; i <= 4; i++)
			bedCombo.getItems().add(new Label(String.valueOf(i)));
		bedCombo.setPromptText("请选择床位");
		bedCombo.setPrefWidth(150);
		inputs.add(bedCombo, 3, 2);
		
		return inputs;
	}
	public VBox getControls() {				//获取按钮控件及左侧布局
		VBox vb = new VBox(20);
		vb.setPadding(new Insets(0, 0, 0, 50));
		vb.setAlignment(Pos.CENTER);
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER_RIGHT);
		JFXButton checkin = new JFXButton("确定入住"),
				back = new JFXButton("返回主页");
		//添加入住按钮的样式
		checkin.setTextFill(Color.WHITE);
		checkin.setStyle("-fx-background-color:#46456d");
		//确定入住按钮的单击响应事件
		checkin.setOnAction(e -> {
			Alert msg;
			//判断是否有输入框内容为空
			for (int i = 0; i < 5; i++)
				if (tfs[i].getText().isEmpty()) {
					msg = new Alert(AlertType.ERROR ,"信息未填写完整！");
					msg.show();
					return ;
				}
			if (bedCombo.getValue() == null) {				//判断是否选择了床位
				msg = new Alert(AlertType.ERROR, "请选择床位!");
				msg.show();
				return ;
			}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = sta.executeQuery("SELECT * FROM studentinfo WHERE stuid='" + tfs[0].getText() + "'");
				int bedNumber = Integer.valueOf(bedCombo.getValue().getText());				//选择的床位值
				rs.last();
				int row_count = rs.getRow();				//查询到的数据总行数
				//判断输入内容是否合法以及是否该学生已入住
				boolean infoIllegal = false, checked = false;
				rs = sta.executeQuery("SELECT * FROM studentinfo WHERE stuid=" + tfs[0].getText());
				while (rs.next()) {
					if (!rs.getString("stuName").equals(tfs[1].getText())) infoIllegal = true;
					else if (!rs.getString("colleget").equals(tfs[2].getText())) infoIllegal = true;
					else if (!rs.getString("department").equals(tfs[3].getText())) infoIllegal = true;
					else if (!rs.getString("class").equals(tfs[4].getText())) infoIllegal = true;
					if (!rs.getString("isCheckin").equals("0")) checked = true;
				}
				if (row_count == 0) {				//判断该学号是否存在
					msg = new Alert(AlertType.ERROR, "学号有误!");
					msg.show();
				}
				else if (infoIllegal) {				//判断输入信息是否有误
					msg = new Alert(AlertType.ERROR, "信息错误！");
					msg.show();
				}
				else if (checked) {				//判断该学生是否已入住
					msg = new Alert(AlertType.ERROR, "该学生已入住！");
					msg.show();
				}
				else {
					ResultSet rss = sta.executeQuery("SELECT bed" + bedNumber + " FROM roominfo WHERE roomid=" + chosenDormitory);
					boolean bedAvailable = true;
					if (rss.next() && !rss.getString("bed" + bedNumber).equals("无")) bedAvailable = false;
					if (bedAvailable) {				//床位未被占用
						//数据库更新
						sta.executeUpdate("UPDATE roominfo SET bed" + bedNumber + "='" + tfs[1].getText() + "' WHERE roomid=" + chosenDormitory);
						sta.executeUpdate("UPDATE studentinfo SET isCheckin=" + bedNumber + " WHERE stuid=" + tfs[0].getText());
						//更新床位颜色
						beds[bedNumber - 1].setStyle("-fx-background-color: #a14f64");
						//更新剩余空床位数量
						emptyBeds--;
						emptyBedsLabel.setText("寝室空余床位数： " + emptyBeds);
						msg = new Alert(AlertType.CONFIRMATION, "入住成功!");
						msg.show();
					}
					else {				//床位已被占用
						msg = new Alert(AlertType.ERROR, "床位不可用!");
						msg.show();
					}
				}
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		//添加返回按钮的样式
		back.setTextFill(Color.WHITE);
		back.setStyle("-fx-background-color:#46456d");
		//返回主页按钮的单击响应事件
		back.setOnAction(e -> {
			MainStage ms = new MainStage(mainPage);
			mainPage.setCenter(ms.getMainPane());
		});
		hb.getChildren().addAll(checkin, back);
		
		vb.getChildren().addAll(getInputs(), hb);
		return vb;
	}
	public Pane getShapes() {				//获取右侧图形布局
		Pane pane = new Pane();
		pane.setPadding(new Insets(0, 50, 0, 0));
		
		Label notused = new Label("未使用"),
				used = new Label("已使用"),
				title = new Label("房\n间\n布\n局\n示\n意\n图"),
				balcony = new Label("阳台");
		//绘制阳台样式
		balcony.setTextFill(Color.WHITE);
		balcony.setPrefWidth(120);
		balcony.setPrefHeight(30);
		balcony.setStyle("-fx-background-color: #543a4e");
		balcony.setAlignment(Pos.CENTER);
		JFXRippler balconyRippler = new JFXRippler(balcony);
		balconyRippler.setLayoutX(0);
		balconyRippler.setLayoutY(40);
		pane.getChildren().add(balconyRippler);
		//绘制标题样式
		title.setLayoutX(230);
		title.setLayoutY(70);
		pane.getChildren().add(title);
		//绘制矩形样式
		Rectangle useRec = new Rectangle(140, 40, 20, 30),
				notuseRec = new Rectangle(140, 85, 20, 30);
		useRec.setFill(Color.rgb(161, 79, 100));
		notuseRec.setFill(Color.rgb(195, 182, 199));
		pane.getChildren().addAll(useRec, notuseRec);
		
		used.setPrefHeight(30);
		used.setLayoutX(165);
		used.setLayoutY(40);
		pane.getChildren().add(used);
		
		notused.setPrefHeight(30);
		notused.setLayoutX(165);
		notused.setLayoutY(85);
		pane.getChildren().add(notused);
		//根据床位是否已被占用判断标签背景颜色
		try {
			//设置基本样式
			for (int i = 0; i < 4; i++) {
				beds[i] = new Label("0" + (i + 1) + "\n号\n床");
				beds[i].setAlignment(Pos.CENTER);
				beds[i].setPrefWidth(50);
				beds[i].setPrefHeight(60);
				beds[i].setLayoutX(i % 2 == 0 ? 0 : 70);
				beds[i].setLayoutY(i < 2 ? 85 : 160);
				beds[i].setTextFill(Color.WHITE);
				pane.getChildren().add(beds[i]);
			}
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sta.executeQuery("SELECT * FROM roominfo WHERE roomid=" + chosenDormitory);
			while (rs.next()) {
				for (int i = 1; i <= 4; i++)
					if (rs.getString("bed" + i).equals("无")) beds[i - 1].setStyle("-fx-background-color: #c3b6c7");
					else beds[i - 1].setStyle("-fx-background-color: #a14f64");
			}
			//关闭数据库
			rs.close(); sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return pane;
	}
	public HBox getInformation() {				//获取寝室和床位信息布局
		chosenDormitoryLabel = new Label("选择的寝室是： " + chosenDormitory);
		emptyBedsLabel = new Label("寝室空余床位数： " + emptyBeds);
		
		HBox hb = new HBox(20);
		hb.setPadding(new Insets(0, 0, 20, 0));
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(chosenDormitoryLabel, emptyBedsLabel);
		
		return hb;
	}
	public StackPane getMainPane() {				//获取主布局
		BorderPane MainPane = new BorderPane();
		MainPane.setTop(getTitle());
		MainPane.setLeft(getControls());
		MainPane.setRight(getShapes());
		MainPane.setBottom(getInformation());

		BorderPane.setAlignment(MainPane.getTop(), Pos.CENTER);
		BorderPane.setAlignment(MainPane, Pos.CENTER);
		
		return new StackPane(MainPane);
	}
}