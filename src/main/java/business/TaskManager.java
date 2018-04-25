package business;

import java.util.List;

import presentation.Task;

public interface TaskManager {

	public Task getTask(long taskId);
	
	public List<Task> getUserTasks(String userId);
	
	public List<Task> getAllTasks();
	
	public void addTask(Task task);
	
	public void updateTask(Task task);
	
	public void removeUser(String userId);
}
