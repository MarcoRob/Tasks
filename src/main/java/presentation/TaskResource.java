package presentation;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;




@Path("") 
public class TaskResource {
	TaskService taskService = new business.TaskService();
	@GET
	@Path("/tasks") 
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getTasks()
	{
		return taskService.getAllTasks();
	}
	
	@GET
	@Path("/task/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTask(@PathParam("taskId")long taskId)
	{
		return this.taskService.getTask(taskId);
	}
	
	@GET
	@Path("/users/{userId}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getUserTasks(@PathParam("userId")String userId)
	{
		return this.taskService.getUserTasks(userId);
	}
	
	@POST 
	@Path("/tasks") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task addTask(persistence.Task task){
		this.taskService.addTask(task);
		return task;
	}
	@PUT 
	@Path("/task/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task update(@PathParam("taskId")long taskId, persistence.Task task){
		task.setId(taskId);
		this.taskService.updateTask(task);
		return task;
	}
	
	@DELETE 
	@Path("/users/{userId}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public void update(@PathParam("userId")String userId){
		this.taskService.removeUser(userId);
	}
}
