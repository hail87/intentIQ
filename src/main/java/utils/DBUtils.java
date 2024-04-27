package utils;

import common.Context.UiTestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.ConnectionDB;
import org.apache.ibatis.jdbc.ScriptRunner;
import common.Context.Context;
import common.MyPath;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBUtils {

    private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static ResultSet execute(String fullRequest) {
        ResultSet rs = null;
        try {
            rs = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement().executeQuery(fullRequest);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            logger.error("!!!Query:\n" + fullRequest + "\nhasn't been executed!!!");
        }
        return rs;
    }

    public static boolean update(String fullRequest) {
        int i = 0;
        try {
            i = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement().executeUpdate(fullRequest);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            logger.error("!!!Query:\n" + fullRequest + "\nhasn't been executed!!!");
        }
        logger.info(String.format("\nSQL UPDATE : \n'%s' \n was executed - '%b'", fullRequest, i == 1));
        return i == 1;
    }

    public static ResultSet executeUpdatable(String fullRequest) {
        ResultSet rs = null;
        try {
            PreparedStatement prepStmt = Context.getTestContext(UiTestContext.class).getConnectionSandbox().prepareStatement(
                    fullRequest,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = prepStmt.executeQuery();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            logger.error("!!!Query:\n" + fullRequest + "\nhasn't been executed!!!");
        }
        return rs;
    }

    public static String executeAndReturnString(String fullRequest) {
        ResultSet rs;
        try {
            rs = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement().executeQuery(fullRequest);
            rs.next();
            return rs.getString(1);
        } catch (SQLException | IOException throwables) {
            logger.error("Empty response from DB");
            return "";
        }
    }

    public static ArrayList<String> executeAndReturnStringArray(String fullRequest) {
        ResultSet rs;
        try {
            rs = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement().executeQuery(fullRequest);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            ArrayList<String> list = new ArrayList<>(columnCount);
            while (rs.next()) {
                int i = 1;
                while (i <= columnCount) {
                    list.add(rs.getString(i++));
                }
            }
            return list;
        } catch (SQLException | IOException throwables) {
            logger.error("Empty response from DB");
            return new ArrayList();
        }
    }

    public static String select(String tableName, int columnNumber) throws SQLException {
        ResultSet rs = getLastRow(tableName);
        rs.next();
        return rs.getString(columnNumber);
    }

    private static ResultSet getLastRow(String tableName) {
        return execute(String.format("SELECT * FROM %s ORDER by createdDate DESC LIMIT 1", tableName));
    }

    public static void executeSqlScript(String scriptName) {
        try {
            Connection connection = new ConnectionDB().getCurrentConnection();
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(MyPath.SQL_SCRIPTS_PATH.getPath() + scriptName));
            logger.info("\nExecuting SQL script: " + scriptName);
            sr.runScript(reader);
            logger.info("\nExecuting SQL script is finished\n");
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            logger.error("\n!!!DB was NOT cleaned after test execution!!!\n");
        }
    }

    public int insertJsonToStageOrder(String jsonContent) throws SQLException, IOException {
        Statement statement = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement();
        int createdId;
        statement.executeUpdate("INSERT INTO stageOrder " +
                        "(orderData, status, processingComment, createdDate, createdBy, modifiedDate, modifiedBy) " +
                        "Values ('" + jsonContent + "','N','',CURRENT_TIMESTAMP,'',CURRENT_TIMESTAMP,'')",
                Statement.RETURN_GENERATED_KEYS);
        //logger.info("SQL request was executed: " + jsonContent);
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                createdId = (int) generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        return createdId;
    }

    public int insertJsonToStageOrder(String jsonContent, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        int createdId;
        statement.executeUpdate("INSERT INTO stageOrder " +
                        "(orderData, status, processingComment, createdDate, createdBy, modifiedDate, modifiedBy) " +
                        "Values ('" + jsonContent + "','N','',CURRENT_TIMESTAMP,'',CURRENT_TIMESTAMP,'')",
                Statement.RETURN_GENERATED_KEYS);
        //logger.info("SQL request was executed: " + jsonContent);
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                createdId = (int) generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        return createdId;
    }

    public int insertJsonToStageProduct(String jsonContent) throws SQLException, IOException {
        Statement statement = Context.getTestContext(UiTestContext.class).getConnectionSandbox().createStatement();
        int createdId;
        statement.executeUpdate("INSERT INTO stageProduct " +
                        "(productData, status, createdDate, createdBy, modifiedDate, modifiedBy, processingComment) " +
                        "Values ('" + jsonContent + "','N',CURRENT_TIMESTAMP,'',CURRENT_TIMESTAMP,'','')",
                Statement.RETURN_GENERATED_KEYS);
        //logger.info("SQL request was executed: " + jsonContent);
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                createdId = (int) generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
        return createdId;
    }

    public String select(String tableName, int rowId, String columnName) {
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + tableName + "ID = " + rowId;
        ResultSet rs = execute(query);
        String result = null;
        try {
            rs.next();
            result = rs.getString(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert result != null;
        if (result.equalsIgnoreCase("C")) {
            logger.info("\n!!!Query :\n" + query + "\n has been executed with result : " + result);
        } else {
            logger.error("\n!!!Query :\n" + query + "\n has been executed with result : " + result);
        }
        return result;
    }

    public String select(String tableName, String columnName) throws SQLException {
        ResultSet rs = getLastRow(tableName);
        rs.next();
        return rs.getString(columnName);
    }

    public boolean closeConnection() throws SQLException, IOException {
        boolean isClosed = false;
        Connection connection = Context.getTestContext(UiTestContext.class).getConnectionSandbox();
        try {
            connection.close();
            isClosed = connection.isClosed();
            if (isClosed)
                logger.info("Connection is closed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isClosed;
    }
}


