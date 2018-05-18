package modle;

public class Remind {
	private int id;
	private int uid;
	private int bid;
	
	public Remind() {}
	public Remind(int id, int uid, int bid) {
		this.id = id;
		this.uid = uid;
		this.bid = bid;
	}
	
	public Remind(int bid) {
		this.bid = bid;
	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public int getUid() {return uid;}
	public void setUid(int uid) {this.uid = uid;}
	
	public int getBid() {return bid;}
	public void setBid(int bid) {this.bid = bid;}
}
