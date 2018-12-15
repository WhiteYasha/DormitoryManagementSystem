package helloworld;

import java.sql.*;
import com.jfoenix.controls.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;

public class SearchPane {
	private BorderPane mainPage;
	private JFXTextField filters[] = new JFXTextField[5];
	private TableView table;
	private	ObservableList<Student> tableModel;
	private int chosenIndex = -1;
	
	public SearchPane(BorderPane mp) {				//构造函数
		mainPage = mp;
	}
	
	public GridPane getFilters() {				//获取输入及控制布局
		Label lbs[] = new Label[5];
		lbs[0] = new Label("学号： ");
		lbs[1] = new Label("姓名： ");
		lbs[2] = new Label("学院： ");
		lbs[3] = new Label("系别： ");
		lbs[4] = new Label("班级： ");
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(20, 0, 0, 0));
		gp.setHgap(20);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		//添加标签控件
		for (int i = 0; i <  5; i++) {
			gp.add(lbs[i], (i % 3) * 2, i / 3);
			filters[i] = new JFXTextField();
			gp.add(filters[i], (i % 3) * 2 + 1, i / 3);
		}
		//添加按钮控件
		gp.add(getButtons(), 5, 1);
		return gp;
	}
	public String filtersSQL(String sql) {				//获取筛选条件的SQL字符串
		boolean isAnd = false;
		if (!filters[0].getText().isEmpty()) {
			sql += (isAnd ? " AND " : " WHERE ") + "stuId=" + filters[0].getText();
			isAnd = true;
		}
		if (!filters[1].getText().isEmpty()) {
			sql += (isAnd ? " AND " : " WHERE ") + "stuName='" + filters[1].getText() + "'";
			isAnd = true;
		}
		if (!filters[2].getText().isEmpty()) {
			sql += (isAnd ? " AND " : " WHERE ") + "colleget='" + filters[2].getText() + "'";
			isAnd = true;
		}
		if (!filters[3].getText().isEmpty()) {
			sql += (isAnd ? " AND " : " WHERE ") + "department='" + filters[3].getText() + "'";
			isAnd = true;
		}
		if (!filters[4].getText().isEmpty()) 
			sql += (isAnd ? " AND " : " WHERE ") + "class='" + filters[4].getText() + "'";
		
		return sql;
	}
	public TableView<Student> getTable() {				//获取表格布局
		table = new TableView();
		tableModel = FXCollections.observableArrayList();
		//初始化表格列信息
		TableColumn tcs[] = new TableColumn[6];
		String elements[] = {"infoId", "infoName", "infoCollege", "infoDepartment", "infoClass", "isCheckin"};
		tcs[0] = new TableColumn("学号"); 
		tcs[1] = new TableColumn("姓名");
		tcs[2] = new TableColumn("学院");
		tcs[3] = new TableColumn("系别");
		tcs[4] = new TableColumn("班级");
		tcs[5] = new TableColumn("是否入住");
		for (int i = 0; i < 6; i++) {
			tcs[i].setCellValueFactory(new PropertyValueFactory<Student, String>(elements[i]));
			tcs[i].setPrefWidth(100);
			table.getColumns().add(tcs[i]);
		}
		//获取数据
		try {
			//连接数据库
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery("SELECT * FROM studentinfo");
			while (rs.next()) {
				Student tempStu = new Student(rs.getString("stuId"), rs.getString("stuName"), rs.getString("colleget"), 
						rs.getString("department"), rs.getString("class"), rs.getInt("isCheckin"));
				tableModel.add(tempStu);
			}
			table.setItems(tableModel);
			//关闭数据库
			rs.close(); sta.close(); con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		//设置表格单击选中事件
		table.setOnMouseClicked(e -> {
			int selectedRow = table.getSelectionModel().getSelectedIndex();
			if (selectedRow != -1) {				//选中了单行信息
				if (chosenIndex != selectedRow) {
					Student stuInfo = (Student)tableModel.get(selectedRow);
					filters[0].setText(stuInfo.getInfoId());
					filters[1].setText(stuInfo.getInfoName());
					filters[2].setText(stuInfo.getInfoCollege());
					filters[3].setText(stuInfo.getInfoDepartment());
					filters[4].setText(stuInfo.getInfoClass());
					chosenIndex = selectedRow;
				}
				else {				//取消选中
					for (int i = 0; i < 5; i++) filters[i].setText("");
					chosenIndex = -1;
				}
			}
		});
		return table;
	}
	public HBox getButtons() {				//获取按钮控件
		JFXButton searchButton = new JFXButton("查询");
		//添加查询按钮的样式
		searchButton.setTextFill(Color.WHITE);
		searchButton.setStyle("-fx-background-color:#46456d");
		//设置查询按钮的单击响应事件
		searchButton.setOnAction(e -> {
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String sql = filtersSQL("SELECT * FROM studentinfo");
				//查询
				ResultSet rs = sta.executeQuery(sql);
				tableModel = FXCollections.observableArrayList();
				table.setItems(tableModel);				//清空当前表格
				while (rs.next()) {
					Student tempStu = new Student(rs.getString("stuid"), rs.getString("stuName"), rs.getString("colleget"), 
							rs.getString("department"), rs.getString("class"), rs.getInt("isCheckin"));
					tableModel.add(tempStu);
				}
				table.setItems(tableModel);				//更新查询后的表格信息
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		JFXButton addButton = new JFXButton("添加");
		//添加增加按钮的样式
		addButton.setTextFill(Color.WHITE);
		addButton.setStyle("-fx-background-color:#46456d");
		//设置增加按钮的单击响应事件
		addButton.setOnAction(e -> {
			Alert msg;
			//判断是否有输入信息为空
			for (int i = 0; i < 5; i++)
				if (filters[i].getText().isEmpty()) {
					msg = new Alert(AlertType.ERROR, "请填写完整信息!");
					msg.show();
					return ;
				}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = sta.executeQuery("SELECT * FROM studentinfo WHERE stuId=" + filters[0].getText());
				//获取行数
				rs.last();
				int idExist = rs.getRow();
				rs.first();
				if (idExist != 0) {				//学号为主键不能重复存在
					msg = new Alert(AlertType.ERROR, "该学号已存在!");
					msg.show();
				}
				else {
					String sql = "INSERT INTO studentinfo (stuId, stuName, colleget, department, class, isCheckin) VALUES (";
					for (int i = 0; i < 5; i++) sql += "'" + filters[i].getText() + "',";
					sql += "0)";
					sta.executeUpdate(sql);
					rs = sta.executeQuery("SELECT * FROM studentinfo");
					//显示更新后的所有数据信息
					tableModel = FXCollections.observableArrayList();
					while (rs.next()) {
						Student tempStu = new Student(rs.getString("stuid"), rs.getString("stuName"), rs.getString("colleget"), 
								rs.getString("department"), rs.getString("class"), rs.getInt("isCheckin"));
						tableModel.add(tempStu);
					}
					table.setItems(tableModel);
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e2) {
				e2.printStackTrace();
			}
		});
		JFXButton deleteButton = new JFXButton("删除");
		//添加删除按钮的样式
		deleteButton.setTextFill(Color.WHITE);
		deleteButton.setStyle("-fx-background-color:#46456d");
		//设置删除按钮的单击响应事件
		deleteButton.setOnAction(e -> {
			Alert msg;
			//判断是否有输入框为空
			for (int i = 0; i < 5; i++)
				if (filters[i].getText().isEmpty()) {
					msg = new Alert(AlertType.ERROR, "请填写完整信息!");
					msg.show();
					return ;
				}
			try {
				//连接数据库
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dormitory?useSSL=false", "root", "woshinst1");
				Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = sta.executeQuery(filtersSQL("SELECT * FROM studentinfo"));
				//获取行数
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				if (rowCount == 0) {				//数据不属实
					msg = new Alert(AlertType.ERROR, "不存在此人!");
					msg.show();
				}
				else if (!rs.getString("isCheckin").equals("0")) {				//该学生已入住
					msg = new Alert(AlertType.ERROR, "该学生已入住!");
					msg.show();
				}
				else {
					sta.executeUpdate(filtersSQL("DELETE FROM studentinfo"));
					tableModel = FXCollections.observableArrayList();
					rs = sta.executeQuery("SELECT * FROM studentinfo");
					while (rs.next()) {
						Student tempStu = new Student(rs.getString("stuid"), rs.getString("stuName"), rs.getString("colleget"), 
								rs.getString("department"), rs.getString("class"), rs.getInt("isCheckin"));
						tableModel.add(tempStu);
					}
					//更新删除后的表格
					table.setItems(tableModel);
				}
				//关闭数据库
				rs.close(); sta.close(); con.close();
			} catch (ClassNotFoundException | SQLException e3) {
				e3.printStackTrace();
			}
		});
		
		HBox hb = new HBox(20);
		hb.getChildren().addAll(searchButton, addButton, deleteButton);
		return hb;
	}
	public VBox getMainPane() {				//获取主布局
		VBox vb = new VBox(10);
		vb.setPadding(new Insets(0, 10, 20, 10));
		vb.getChildren().addAll(getFilters(), getTable());
		
		return vb;
	}
}
