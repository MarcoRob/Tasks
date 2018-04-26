package business;

import java.math.BigInteger;

import presentation.Task;


public class TaskWithReminder implements Task {
	
	
	private String title;
	private String description;
	private String userId;
	
	protected long dueDate;
	protected long remind;
	private BigInteger completedDate;

	public TaskWithReminder() {
		
	}
	
	public TaskWithReminder(String title, String description, long dueDate, BigInteger completedDate, String userId) {
		this.setTitle(title);
		this.setDescription(description);
		this.setDueDate(dueDate);
		this.setUserId(userId);
		this.setCompletedDate(completedDate);
		business.Reminder reminder = new business.Reminder(this.getDueDate());
		this.setRemind(reminder.getTime());
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

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
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

	public long getRemind() {
		return this.remind;
	}

	public void setRemind(long remind) {
		this.remind = remind;
		
	}

}
