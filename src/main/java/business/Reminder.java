package business;

public class Reminder {

	private long time;
	private long remindDate;
	
	public Reminder(long remindDate) {
		this.remindDate = remindDate;
		this.time = this.calculateRemainingTime();
	}
	
	private long calculateRemainingTime() {
		long currentUnixTime = System.currentTimeMillis() / 1000L;
		return this.remindDate - currentUnixTime;
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
