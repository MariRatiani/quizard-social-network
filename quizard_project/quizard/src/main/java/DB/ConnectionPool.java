package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static DB.DBConfigInfo.*;

public class ConnectionPool {
    private final int numConnections = 10;
    private BlockingQueue<Connection> queue;

    public ConnectionPool() {
        this(false);
    }

    public ConnectionPool(boolean test) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        queue = new ArrayBlockingQueue<>(numConnections);
        for (int i = 0; i < numConnections; i++) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://" + SERVER_NAME, USERNAME, PASSWORD);
                Statement stm = con.createStatement();
                String db = test ? TESTING_DATABASE_NAME : DATABASE_NAME;
                stm.execute("USE " + db + ";");
                queue.add(con);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Connection getConnection() {
        try {
            Connection conn = queue.take();
            return conn;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseConnection(Connection conn) {
        try {
            queue.put(conn);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void close() {
        while (!queue.isEmpty()) {
            try {
                queue.poll().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
