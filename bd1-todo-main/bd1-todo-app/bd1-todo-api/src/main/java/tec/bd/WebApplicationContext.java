package tec.bd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tec.bd.controller.TodoController;
import tec.bd.repository.mysql.TodoRepositoryMySqlImpl;
import tec.bd.todo.repository.TodoRepository;

public class WebApplicationContext {

    private TodoRepository mysqlTodoRepository;

    private TodoController todoController;

    private ApplicationContext todoAppContext;

    private WebApplicationContext() {

    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initTodoMySQLRepository(webAppContext);

        webAppContext.todoAppContext = ApplicationContext.init(webAppContext.mysqlTodoRepository);

        initTodoController(webAppContext);

        return webAppContext;
    }

    private static void initTodoMySQLRepository(WebApplicationContext webApplicationContext) {
        webApplicationContext.mysqlTodoRepository = new TodoRepositoryMySqlImpl();
    }

    private static void initTodoController(WebApplicationContext webApplicationContext) {
        var todo = webApplicationContext.todoAppContext.getTodo();
        webApplicationContext.todoController = new TodoController(todo);
    }

    public ApplicationContext getTodoAppContext() {
        return todoAppContext;
    }

    public TodoController getTodoController() {
        return todoController;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

}
