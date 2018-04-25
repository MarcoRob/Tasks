package presentation;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import business.TaskWithReminder;



@Path("task") 
public class TaskResource {
	TaskService taskService = new business.TaskService();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getTasks()
	{
		return taskService.getAllTasks();
	}
	
	@GET
	@Path("/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTask(@PathParam("taskId")long taskId)
	{
		return this.taskService.getTask(taskId);
	}
	
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getUserTasks(@PathParam("userId")long userId)
	{
		return this.taskService.getUserTasks(userId);
	}
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task addTask(persistence.Task task){
		this.taskService.addTask(task);
		return task;
	}
	@PUT 
	@Path("/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task update(@PathParam("taskId")long taskId, persistence.Task task){
		//Task oldTask = this.taskService.getTask(taskId);
		task.setId(taskId);
		this.taskService.updateTask(task);
		return task;
	}
}
