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
        String sql = "CREATE TABLE LUSER(ID INT NOT NULL AUTO_INCREMENT, 'NAME' VARCHAR(20), LASTNAME VARCHAR(20), AGE INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {  //удаления таблицы ++++
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE LUSER";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException { //добавление юзера в базу данных и сохранение
        String sql = "INSERT INTO LUSER('NAME',LASTNAME,AGE)VALUES(?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }

    }

    public void removeUserById(long id) { //удаления пользователя по id ++++++

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM LUSER WHERE ID 'id'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() throws SQLException {//достать всех пользователей из базы данных
        List<User> userList = new ArrayList<>();
        String sql = "SELECT 'NAME',LASTNAME, AGE FROM LUSER";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user1 = new User();
                user1.setName(resultSet.getString("NAME"));
                user1.setLastName(resultSet.getString("LASTNAME"));
                user1.setAge(resultSet.getByte("AGE"));
                userList.add(user1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }
        return userList;
    }

    public void cleanUsersTable() { //очищения таблицы пользователей
        try (Statement statement = connection.createStatement()){
            String sql = "TRUNCATE TABLE  LUSER";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}