package modle;

public class Record {
	private int id;
	private int uid;
	private int bid;
	private String lendTime;
	private String returnTime;
	
	public Record(){}
	public Record(int uid, int bid, String lendTime, String returnTime) {
		//this.id = id;
		this.uid = uid;
		this.bid = bid;
		this.lendTime = lendTime;
		this.returnTime = returnTime;
	}
	
	public Record(int bid, String lendTime, String returnTime) {
		//this.id = id;
		//this.uid = uid;
		this.bid = bid;
		this.lendTime = lendTime;
		this.returnTime = returnTime;
	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public int getUid() {return uid;}
	public void setUid(int uid) {this.uid = uid;}
	
	public int getBid() {return bid;}
	public void setBid(int bid) {this.bid = bid;}
	
	public String getLendTime() {return lendTime;}
	public void setLendTime(String lendTime) {this.lendTime = lendTime;}
	
	public String getReturnTime() {return returnTime;}
	public void setReturnTime(String returnTime) {this.returnTime = returnTime;}
	
}