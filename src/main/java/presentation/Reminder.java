package presentation;

public class Reminder {

	long time;
	
	public Reminder(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public String toString(){
		return new StringBuffer("time: ").append(this.time).toString();
	}
}
