package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connectionToDataBase = Util.getConnection();
             PreparedStatement statement = connectionToDataBase
                     .prepareStatement("CREATE TABLE IF NOT EXISTS my_db.users(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                             "name VARCHAR(25), " +
                             "last_name VARCHAR(30), " +
                             "age INT)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connectionToDataBase = Util.getConnection();
             PreparedStatement statement = connectionToDataBase
                     .prepareStatement("DROP TABLE IF EXISTS my_db.users")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connectionToDataBase = Util.getConnection();
             PreparedStatement statement = connectionToDataBase
                     .prepareStatement("INSERT INTO my_db.users(name, last_name, age) VALUE (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connectionToDataBase = Util.getConnection();
             PreparedStatement statement = connectionToDataBase.prepareStatement("DELETE FROM my_db.users " +
                     "WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connectionToDataBase = Util.getConnection();
             ResultSet setUsers = connectionToDataBase.prepareStatement("SELECT * FROM my_db.users").executeQuery()) {
            while (setUsers.next()) {
                User user = new User(setUsers.getString("name"),
                        setUsers.getString("last_name"),
                        setUsers.getByte("age"));
                user.setId(setUsers.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connectionToDataBase = Util.getConnection();
             PreparedStatement statement = connectionToDataBase
                     .prepareStatement("TRUNCATE TABLE my_db.users")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
