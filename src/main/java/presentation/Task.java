package presentation;

import java.math.BigInteger;

public interface Task {

	
	
	public String getTitle();
	
	public void setTitle(String title);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public long getDueDate();
	
	public void setDueDate(long dueDate);
	
	public BigInteger getCompletedDate();
	
	public void setCompletedDate(BigInteger completedDate);
	
	public long getRemind();
	
	public void setRemind(long remind);
	

	
}
