package business;

import java.math.BigInteger;

import presentation.Reminder;
import presentation.Task;


public class TaskWithReminder implements Task {
	
	protected Reminder reminder;
	
	private String title,
				   description;
	
	private long dueDate,
				 userId;
	private BigInteger completedDate;

	public TaskWithReminder() {
		
	}
	
	public TaskWithReminder(String title, String description, long dueDate, BigInteger completedDate, long userId) {
		this.setTitle(title);
		this.setDescription(description);
		this.setDueDate(dueDate);
		this.setUserId(userId);
		this.setCompletedDate(completedDate);
		this.createReminder();
	}
	
	public void createReminder() {
		this.reminder = new Reminder(this.calculateRemainingTime());
	}
	
	private long calculateRemainingTime() {
		long currentUnixTime = System.currentTimeMillis() / 1000L;
		return this.getDueDate() - currentUnixTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDueDate() {
		return dueDate;
	}

	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}

	public Reminder getReminder() {
		return reminder;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public BigInteger getCompletedDate() {
		return this.completedDate;
	}

	@Override
	public void setCompletedDate(BigInteger completedDate) {
		this.completedDate = completedDate;
		
	}

}
