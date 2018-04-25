package presentation;

import java.util.List;

public interface TaskService {
	
	public Task getTask(long taskId);
	
	public List<Task> getUserTasks(long userId);
	
	public List<Task> getAllTasks();
	
	public void addTask(Task task);
	
	public void updateTask(Task task);
}
