package presentation;

import java.math.BigInteger;

public interface Task {

	
	
	public String getTitle();
	
	public void setTitle(String title);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public long getUserId();
	
	public void setUserId(long userId);
	
	public long getDueDate();
	
	public void setDueDate(long dueDate);
	
	public BigInteger getCompletedDate();
	
	public void setCompletedDate(BigInteger completedDate);
	
	public Reminder getReminder();
	
}
