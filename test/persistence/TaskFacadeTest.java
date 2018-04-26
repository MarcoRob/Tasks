/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.math.BigInteger;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import presentation.Task;

import java.sql.*;
import org.junit.Assert;

/**
 *
 * @author JoséRicardo
 */
public class TaskFacadeTest {
    
    private static final int ID_COL = 1;
    private static final int TITLE_COL = 2;
    private static final int DESCRIPTION_COL = 3;
    private static final int DUE_DATE_COL = 4;
    private static final int COMPLETED_DATE_COL = 5;
    private static final int USER_ID_COL = 6;
    private static final int REMINDER_DATE_COL = 7;
    
    
    private static String db_driver = "com.mysql.jdbc.Driver";
    private static String db_url = "jdbc:mysql://localhost:3306/tasks_db";
    private static String db_user = "root";
    private static String db_password = "bleach387";
    private static String db_table = "tasks";
    private static String test_user = "testUser";
    private static String test_remove_user = "testRemoveUser";
    private static String test_get_user = "testGetUser";
    private static int[] generatedKeys;
    
    public TaskFacadeTest() {
    }
    
    public persistence.Task findTask(Connection connection, String title) throws SQLException, IllegalArgumentException{
        long longNotFound = 0;
        String query = "SELECT * FROM tasks where title=\""+title+"\"";
        Statement statement = connection.createStatement();
        ResultSet queryResults = statement.executeQuery(query);
        if (!queryResults.next() ) {
        	throw new IllegalArgumentException("Task "+title+" was not found");
        } 
        long id = queryResults.getLong(ID_COL);
        String description = queryResults.getString(DESCRIPTION_COL);
        long dueDate = queryResults.getLong(DUE_DATE_COL);
        BigInteger completedDate = BigInteger.valueOf(queryResults.getLong(COMPLETED_DATE_COL));
        String taskUserId = queryResults.getString(USER_ID_COL);
        long remindDate = queryResults.getLong(REMINDER_DATE_COL);
        persistence.Task foundTask = new persistence.Task(id, title,
                description, dueDate, completedDate, taskUserId, remindDate);
        return foundTask;
    }
    
    @BeforeClass
    public static void setUpClass() {
        try{
            int numTestEntries = 2;
            Class.forName(TaskFacadeTest.db_driver);
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            String querySetTest = "INSERT INTO tasks"
                    + "(title, description, due_date, user_id)"
                    + "values(?, ?, ?, ?), (?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(querySetTest, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Task 1");
            statement.setString(2, "Description 1");
            statement.setLong(3, 9999);
            statement.setString(4, test_user);
            statement.setString(5, "Task 2");
            statement.setString(6, "Description 2");
            statement.setLong(7, 8888);
            statement.setString(8, test_user);
            statement.execute();
            ResultSet generatedKeysSet = statement.getGeneratedKeys();
            
            TaskFacadeTest.generatedKeys = new int[numTestEntries];
            
            
            for (int i=0; i<numTestEntries; i++){
                Assert.assertTrue(generatedKeysSet.next());
                TaskFacadeTest.generatedKeys[i] = generatedKeysSet.getInt(1);
                
            }
            
            
            connection.close();  
        }catch(Exception e){ 
            System.out.println("Connection with DB for setup failed: "+e);
        }  
    }
    
    @AfterClass
    public static void tearDownClass() {
        try{
            Class.forName(TaskFacadeTest.db_driver);  
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);   
            String queryCleanup = "delete from tasks where user_id in (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryCleanup);
            statement.setString(1, TaskFacadeTest.test_user);
            statement.setString(2, TaskFacadeTest.test_remove_user);
            statement.setString(3, TaskFacadeTest.test_get_user);
            statement.execute();
            connection.close();  
        }catch(Exception e){ 
            System.out.println("Cleanup of DB after setup failed: "+e);
        }  
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addTask method, of class TaskFacade.
     */
    @org.junit.Test
    public void testAddTask() {
        String testTitle = "Task For Add Test";
        System.out.println("addTask");
        Task entity = new persistence.Task((long) 0, testTitle,
                "Description of new task", 99999, null,
                TaskFacadeTest.test_user, 99999);
        TaskFacade instance = new TaskFacade();
        instance.addTask(entity);
        try{
        	
            Class.forName(TaskFacadeTest.db_driver);
            
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            
            String query = "SELECT * FROM tasks";
            Statement statement = connection.createStatement();
            ResultSet queryResults = statement.executeQuery(query);
            queryResults.next();

            persistence.Task foundTask = findTask(connection, testTitle);
            
            Assert.assertEquals(testTitle, foundTask.getTitle());
            connection.close();
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of updateTask method, of class TaskFacade.
     */
    @org.junit.Test
    public void testUpdateTask() {
        System.out.println("updateTask");
       
        try{
            Class.forName(TaskFacadeTest.db_driver);  
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);   
            String query = "SELECT * FROM tasks where title=\"Task 1\"";
            Statement statement = connection.createStatement();
            ResultSet queryResults = statement.executeQuery(query);
            queryResults.next();
            String newDescription = "A new description";
            long newDueDate = (long) 1111999;
            long oldId = queryResults.getLong(ID_COL);
            String oldTitle = queryResults.getString(TITLE_COL);
            String oldDescription = queryResults.getString(DESCRIPTION_COL);
            long oldDueDate = queryResults.getLong(DUE_DATE_COL);
            long oldCompletedDate = queryResults.getLong(COMPLETED_DATE_COL);
            String taskUserId = queryResults.getString(USER_ID_COL);
            long oldRemindDate = queryResults.getLong(REMINDER_DATE_COL);
            Task updatedTask = new persistence.Task(oldId, oldTitle,
                    newDescription, newDueDate, null, taskUserId, oldRemindDate);
            TaskFacade instance = new TaskFacade();
            instance.updateTask(updatedTask);
            String updatedQuery = "SELECT * FROM tasks where id="+oldId;
            queryResults = statement.executeQuery(query);
            queryResults.next();
            long updatedId = queryResults.getLong(ID_COL);
            String updatedTitle = queryResults.getString(TITLE_COL);
            String updatedDescription = queryResults.getString(DESCRIPTION_COL);
            long updatedDueDate = queryResults.getLong(DUE_DATE_COL);
            Assert.assertEquals(oldId, updatedId);
            Assert.assertEquals(oldTitle, updatedTitle);
            Assert.assertNotEquals(oldDescription, updatedDescription);
            Assert.assertEquals(newDescription, updatedDescription);
            Assert.assertNotEquals(oldDueDate, updatedDueDate);
            Assert.assertEquals(newDueDate, updatedDueDate);
            connection.close();
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of remove method, of class TaskFacade.
     */
    @org.junit.Test(expected=IllegalArgumentException.class)
    public void testRemove() {
        System.out.println("remove");

        try{
            String taskTitle = "Task to remove";
            Class.forName(TaskFacadeTest.db_driver);
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            String querySetTest = "INSERT INTO tasks"
                    + "(title, description, due_date, user_id)"
                    + "values(?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(querySetTest);
            statement.setString(1, taskTitle);
            statement.setString(2, "Description of task");
            statement.setLong(3, 9999);
            statement.setString(4, test_user);
            statement.execute();
            persistence.Task taskToRemove = findTask(connection, taskTitle);
            TaskFacade instance = new TaskFacade();
            instance.remove(taskToRemove);
            findTask(connection, taskTitle);
            
            connection.close();  
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of removeUser method, of class TaskFacade.
     */
    @org.junit.Test(expected=IllegalArgumentException.class)
    public void testRemoveUser() {
        System.out.println("removeUser");

        try{
            String taskRemoveTitle = "Remove task test";
            Class.forName(TaskFacadeTest.db_driver);
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            String querySetTest = "INSERT INTO tasks"
                    + "(title, description, due_date, user_id)"
                    + "values(?, ?, ?, ?), (?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(querySetTest);
            statement.setString(1, taskRemoveTitle);
            statement.setString(2, "Description of task");
            statement.setLong(3, 9999);
            statement.setString(4, test_remove_user);
            statement.setString(5, "Remove task test 2");
            statement.setString(6, "Description of task 2");
            statement.setLong(7, 77777);
            statement.setString(8, test_remove_user);
            statement.execute();
            
            TaskFacade instance = new TaskFacade();
            instance.removeUser(test_remove_user);
            findTask(connection, taskRemoveTitle);
            
            connection.close();  
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of getTask method, of class TaskFacade.
     */
    @org.junit.Test
    public void testGetTask() {
        System.out.println("getTask");

        try{
            String taskTitle = "Task to Retrieve";
            Class.forName(TaskFacadeTest.db_driver);
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            String querySetTest = "INSERT INTO tasks"
                    + "(title, description, due_date, user_id)"
                    + "values(?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(querySetTest, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, taskTitle);
            statement.setString(2, "Description of task");
            statement.setLong(3, 67676);
            statement.setString(4, test_user);
            statement.execute();
            ResultSet generatedKeySet = statement.getGeneratedKeys();
            generatedKeySet.next();
            long id = (long) generatedKeySet.getInt(1);
            
            persistence.Task expResult = findTask(connection, taskTitle);
            
            TaskFacade instance = new TaskFacade();
            Task result = instance.getTask(id);
            Assert.assertEquals(expResult, result);
            
            connection.close();  
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of getUserTasks method, of class TaskFacade.
     */
    @org.junit.Test
    public void testGetUserTasks() {
        System.out.println("getUserTasks");

        try{
            int expResult = 3;
            Class.forName(TaskFacadeTest.db_driver);
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);  
            String querySetTest = "INSERT INTO tasks"
                    + "(title, description, due_date, user_id)"
                    + "values(?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(querySetTest);
            statement.setString(1, "Task a");
            statement.setString(2, "Description of task");
            statement.setLong(3, 9999);
            statement.setString(4, test_get_user);
            statement.setString(5, "Task b");
            statement.setString(6, "Description of task b");
            statement.setLong(7, 77777);
            statement.setString(8, test_get_user);
            statement.setString(9, "Task c");
            statement.setString(10, "Description of task c");
            statement.setLong(11, 77777);
            statement.setString(12, test_get_user);
            statement.execute();
            
            TaskFacade instance = new TaskFacade();
            
            List<Task> result = instance.getUserTasks(test_get_user);

            System.out.println("SIZE: "+result.size());
            System.out.println(result);
            Assert.assertEquals(expResult, result.size());
            
            
            connection.close();  
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }

    /**
     * Test of getAllTasks method, of class TaskFacade.
     */
    @org.junit.Test
    public void testGetAllTasks() {
        System.out.println("getAllTasks");
        
        try{
            Class.forName(TaskFacadeTest.db_driver);  
            Connection connection = DriverManager.getConnection(  
                    TaskFacadeTest.db_url, TaskFacadeTest.db_user,
                    TaskFacadeTest.db_password);   
            String query = "SELECT COUNT(*) FROM tasks";
            Statement statement = connection.createStatement();
            ResultSet queryResults = statement.executeQuery(query);
            queryResults.next();
            int totalNumEntries = queryResults.getInt(1);
            
            TaskFacade instance = new TaskFacade();
            List<Task> result = instance.getAllTasks();
            Assert.assertEquals(totalNumEntries, result.size());
            connection.close();
        }catch(SQLException e){
            fail("DB connection failed during test: "+e);
        } catch (ClassNotFoundException e){
            fail("DB driver not found during test: "+e);
        }
    }
}
