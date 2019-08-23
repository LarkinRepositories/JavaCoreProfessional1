package Server;

import java.sql.*;
import java.util.ArrayList;


public class AuthService {
    private static Connection connection;
    private static Statement statement;


    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/zChat", "postgres", "postgres");
        statement = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createNewAccount(String login, String nickname, String password) throws SQLException {
        String query = String.format("INSERT INTO users (login, password, nickname) VALUES ('%s', '%s', '%s')", login, password.hashCode(), nickname);
        statement.executeUpdate(query);
    }

    public static int getUserIDByLoginAndPass(String login, String password) throws SQLException {
        String query = String.format("SELECT id FROM users WHERE login='%s' and password = '%s'", login, password.hashCode());
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    public static String getNickNameByLoginAndPass(String login, String password) throws SQLException {
        String query = String.format("SELECT nickname FROM users WHERE login='%s' and password = '%s'", login, password.hashCode());
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) return rs.getString(1);
        return null;
    }
    public static void passToHashCode(int userID, String password) throws SQLException {
        String query = String.format("UPDATE users SET password='%s' WHERE id=%s", password.hashCode(), userID );
        statement.executeUpdate(query);
    }

    public static void changeNickname(int userID, String nickname) throws SQLException {
        String query = String.format("UPDATE users SET nickname='%s' WHERE id=%s", nickname, userID );
        statement.executeUpdate(query);
    }

    public static void blacklist(int userID, String nickname) throws SQLException {
        String query = String.format("INSERT into blacklist VALUES(%s, '%s')", userID, nickname);
        statement.executeUpdate(query);
    }

    public static ArrayList getBlacklistedUsers(int userID) throws SQLException {
        String query = String.format("SELECT blacklisted_users from blacklist WHERE user_id = %s", userID);
        ResultSet rs = statement.executeQuery(query);
        ArrayList<String> blacklisted = new ArrayList<>();
        while (rs.next()) {
            blacklisted.add(rs.getString(1));
        }
        return blacklisted;
    }
}
