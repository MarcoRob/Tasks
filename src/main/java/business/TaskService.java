package business;

import java.util.List;

import presentation.Task;


public class TaskService implements presentation.TaskService{
	
	private TaskManager taskManager;
	
	public TaskService() {
		this.taskManager = new persistence.TaskFacade();
	}

	@Override
	public TaskWithReminder getTask(long taskId) {
		return (TaskWithReminder) taskManager.getTask(taskId);
		
	}

	@Override
	public List<Task> getUserTasks(String userId) {
		return taskManager.getUserTasks(userId);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskManager.getAllTasks();
	}

	@Override
	public void addTask(Task task) {
		taskManager.addTask(task);
	}

	@Override
	public void updateTask(Task task) {
		taskManager.updateTask(task);
		
	}


	@Override
	public void removeUser(String userId) {
		taskManager.removeUser(userId);
		
	}

}
