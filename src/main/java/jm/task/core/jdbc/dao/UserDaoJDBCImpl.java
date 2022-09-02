package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();


    public void createUsersTable() {   //сoздать таблицу узеров +++++
        String sql = "CREATE TABLE IF NOT EXISTS LUSER(ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, FIRSTNAME VARCHAR(20), LASTNAME VARCHAR(20), AGE TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void dropUsersTable() {  //удаления таблицы ++++
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS LUSER";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException { //добавление юзера в базу данных и сохранение

        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO LUSER(FIRSTNAME,LASTNAME,AGE)VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public void removeUserById(long id) { //удаления пользователя по id ++++++

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM LUSER WHERE ID = id");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers()  {        //достать всех пользователей из базы данных
        List<User> userList = new ArrayList<>();
        String sql = "SELECT FIRSTNAME,LASTNAME, AGE FROM LUSER";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user1 = new User();
                user1.setName(resultSet.getString("FIRSTNAME"));
                user1.setLastName(resultSet.getString("LASTNAME"));
                user1.setAge(resultSet.getByte("AGE"));
                userList.add(user1);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() { //очищения таблицы пользователей
        try (Statement statement = connection.createStatement()) {
            String sql = "TRUNCATE TABLE  LUSER";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}