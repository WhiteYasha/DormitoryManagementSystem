package helloworld;

public class Student {
	private int bedIndex = 0;
	private String infoId, infoName, infoCollege, infoDepartment, infoClass, isCheckin;
	
	public Student() {}
	public Student(String id, String name, String col, String dep, String cla, int index) {
		this.infoId = id;
		this.infoName = name;
		this.infoCollege = col;
		this.infoDepartment = dep;
		this.infoClass = cla;
		this.bedIndex = index;
		isCheckin = (index == 0 ? "否" : "是");
	}
	
	public void setBedIndex(int bedIndex) {this.bedIndex = bedIndex;}
	public void setInfoId(String infoId) {this.infoId = infoId;}
	public void setInfoName(String infoName) {this.infoName = infoName;}
	public void setInfoCollege(String infoCollege) {this.infoCollege = infoCollege;}
	public void setInfoDepartment(String infoDepartment) {this.infoDepartment = infoDepartment;}
	public void setInfoClass(String infoClass) {this.infoClass = infoClass;}
	
	public int getBedIndex() {return this.bedIndex;}
	public String getInfoId() {return this.infoId;}
	public String getInfoName() {return this.infoName;}
	public String getInfoCollege() {return this.infoCollege;}
	public String getInfoDepartment() {return this.infoDepartment;}
	public String getInfoClass() {return this.infoClass;}
	public String getIsCheckin() {return this.isCheckin;}
}
