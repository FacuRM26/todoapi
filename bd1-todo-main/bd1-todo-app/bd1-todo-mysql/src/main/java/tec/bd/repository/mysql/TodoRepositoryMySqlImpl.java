package tec.bd.repository.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tec.bd.todo.repository.TodoRepository;
import tec.bd.todo.*;

public class TodoRepositoryMySqlImpl extends BaseRepository<TodoRecord> implements TodoRepository {


    private final static String SELECT_ALL_TODOS = "select tid, title, description, state, startdate, enddate from todorecord";
    private final static String SELECT_BY_STATUS =  "select tid, title, description, state, startdate, enddate from todorecord where state = ?";
    private final static String DELETE_BY_ID = "delete from todorecord where tid = ?";
    private final static String SELECT_BY_ID = "select tid, title, description, state, startdate, enddate from todorecord where tid = ?";
    private final static String INSERT_TODO = "insert into todorecord(tid, title, description, state, startdate, enddate) values (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_BY_TEXT_IN_TITLE = "select tid, title, description, state, startdate, enddate from todorecord where title like ?";
    private final static String UPDATE = "update todorecord SET title=?, description=?,state=?,startdate=?,enddate=? WHERE tid=?";
    private final static String between_two_dates ="SELECT tid, title, description, state, startdate, enddate FROM todorecord WHERE startdate >= ? AND enddate <= ?";



    @Override
    public List<TodoRecord> findAll() {

        var todoRecordList = new ArrayList<TodoRecord>();

        try {
            var resultSet = this.query(SELECT_ALL_TODOS);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoRecordList;
    }

    @Override
    public List<TodoRecord> findAll(Status status) {

        var todoRecordList = new ArrayList<TodoRecord>();

        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(SELECT_BY_STATUS);
            statement.setString(1, status.toString().toLowerCase());
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoRecordList;
    }

    @Override
    public TodoRecord findById(String id) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(SELECT_BY_ID);
            statement.setString(1, id);
            var resultSet = this.query(statement);

            if(resultSet.next()) {
                return toEntity(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TodoRecord save(TodoRecord todoRecord) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(INSERT_TODO);
            statement.setString(1,todoRecord.getId());
            statement.setString(2,todoRecord.getTitle());
            statement.setString(3,todoRecord.getDescription());
            statement.setString(4, String.valueOf(todoRecord.getStatus()));
            statement.setTimestamp(5, new Timestamp(todoRecord.getStartDate().getTime()));
            if(null != todoRecord.getEndDate()) {
                statement.setTimestamp(6, new Timestamp(todoRecord.getEndDate().getTime()));
            } else {
                statement.setTimestamp(6, null);
            }
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
            return todoRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(String id) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(DELETE_BY_ID);
            statement.setString(1, id);
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TodoRecord update(TodoRecord todoRecord) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(UPDATE);
            statement.setString(1,todoRecord.getTitle());
            statement.setString(2,todoRecord.getDescription());
            statement.setString(3, String.valueOf(todoRecord.getStatus()));
            statement.setTimestamp(4, new Timestamp(todoRecord.getStartDate().getTime()));
            if(null != todoRecord.getEndDate()) {
                statement.setTimestamp(5, new Timestamp(todoRecord.getEndDate().getTime()));
            } else {
                statement.setTimestamp(5, null);
            }
            statement.setString(6,todoRecord.getId());
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
            return todoRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TodoRecord> findByPatternInTitle(String textToSearch) {
        var todoRecordList = new ArrayList<TodoRecord>();
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(SELECT_BY_TEXT_IN_TITLE);
            statement.setString(1, "%"+textToSearch+"%");
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todoRecordList;
    }


    @Override
    public List<TodoRecord> findByBetweenStartDates(Date startDate, Date endDate) {
        var todoRecordList = new ArrayList<TodoRecord>();
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(between_two_dates);
            statement.setTimestamp(1, new Timestamp(startDate.getTime()));
            statement.setTimestamp(2, new Timestamp(endDate.getTime()));
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var todoRecord = toEntity(resultSet);
                if(null != todoRecord) {
                    todoRecordList.add(todoRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoRecordList;
    }

    @Override
    public TodoRecord toEntity(ResultSet resultSet) {
        try {
            var todoRecord = new TodoRecord(
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    Status.valueOf(resultSet.getString("state").toUpperCase()),
                    resultSet.getDate("startdate"),
                    resultSet.getDate("enddate")
            );
            todoRecord.setId(resultSet.getString("tid"));
            return todoRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}