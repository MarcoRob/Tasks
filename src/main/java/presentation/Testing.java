package presentation;

import org.codehaus.jackson.map.ObjectMapper;

public class Testing {

	protected TaskService taskService = new business.TaskService();
	
	public static String toJSON(Object object) 
    { 
        if ( object == null ){
        return "{}"; 
        } 
        try { 
           ObjectMapper mapper = new ObjectMapper(); 
           return mapper.writeValueAsString(object); 
           } 
        catch (Exception e) { 
         e.printStackTrace(); 
        } 
      return "{}"; 
      }
    public String list() {
        return toJSON(taskService.getAllTasks());
    }
	public static void main(String[] args) {
		Testing test = new Testing();
		System.out.println(test.list());
	}
}
