package tec.bd.todo;

import tec.bd.todo.repository.TodoRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Todo {

    private TodoRepository todoRepository;

    public Todo(TodoRepository todoRepo) {
        this.todoRepository = todoRepo;
    }

    public List<TodoRecord> getAll() {
        return this.todoRepository.findAll();
    }

    public List<TodoRecord> getAll(Status status) {
        return this.todoRepository.findAll(status);
    }

    public TodoRecord getById(String id) {
        // TODO: validar que el id no sea nulo y si es nulo lanzar una excepcion
        if(id==null){
            throw new RuntimeException("IllegalArgumentException");
        }
        return this.todoRepository.findById(id);
    }

    public TodoRecord addTodoRecord(TodoRecord record) {
        Objects.requireNonNull(record);
        // Si el titulo es nulo, devolver exception
        if(null == record.getStartDate()) {
            record.setStartDate(new Date());
        }
        record.setStatus(Status.NEW);
        if (this.todoRepository.findById(record.getId()) != null &  record.getTitle()!= null) {
            return this.todoRepository.save(record);
        }
        else{
            throw new RuntimeException("IllegalArgumentException");
        }
    }

    public void deleteTodoRecord(String id) {
        if (this.todoRepository.findById(id) != null) {
            this.todoRepository.remove(id);
        }
        else{
            throw new RuntimeException("IllegalArgumentException");
        }
    }

    public List<TodoRecord> getStartDateRange(Date startDate, Date endDate){
        if(startDate==null || endDate==null){
            throw new RuntimeException("IllegalArgumentException");
        }
      return this.todoRepository.findByBetweenStartDates(startDate,endDate);
    }
    public List<TodoRecord> searchInTitle(String textToSearch){
        if(textToSearch==null){
            throw new RuntimeException("IllegalArgumentException");
        }
        return this.todoRepository.findByPatternInTitle(textToSearch);
    }


    public TodoRecord updateTodoRecord(TodoRecord todoRecord){
        if (this.todoRepository.findById(todoRecord.getId()) != null && todoRecord.getTitle()!= null && todoRecord.getStartDate()!=null && todoRecord.getStatus()!=null) {
            return this.todoRepository.update(todoRecord);
        }
        throw new RuntimeException("IllegalArgumentException");
    }




}
