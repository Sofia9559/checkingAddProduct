package tests.dataBase;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.*;

public class DataBaseTest {

    @Test
    @DisplayName("simpleApplication")
    public void simpleApplication() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user", "pass");

    /*
        Убедиться что в БД нет товаров, которые нужно добавить
        Проверить появились ли в БД новые записи

    */
        String checkQuery = "SELECT * FROM FOOD WHERE FOOD_NAME = 'Банан' OR FOOD_NAME = 'Питайя'";
        String checkQueryCNT = "SELECT COUNT(*) AS CNT FROM FOOD WHERE FOOD_NAME = 'Банан' OR FOOD_NAME = 'Питайя'";
    /*
        Заполнить таблицу данными
    */
        String insertFruit =
                "INSERT INTO FOOD(FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC ) values ('Банан', 'FRUIT', false), ('Питайя', 'FRUIT', true)";
        String insert =
                "INSERT INTO FOOD(FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC ) values (?, ?, ?)";
    /*
        Попробуем добавить товар, который уже есть в бд
     */
        String dubInsert = "INSERT INTO person(FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC ) values ('Банан', 'FRUIT', false)";
    /*
        Проверяем появился ли дубликат нашего товара в БД
    */
        String dublQuery = "SELECT COUNT(*) FROM FOOD WHERE FOOD_NAME = 'Банан'";
    /*
        Удалим созданные товары из таблицы
    */
        String removeProd = "DELETE FROM FOOD WHERE FOOD_NAME = 'Банан' OR FOOD_NAME = 'Питайя'";
    /*
        Проверка состояния бд
    */
        String query = "SELECT * FROM FOOD";

/*
        statement.executeUpdate(insertFruit);
*/
        Statement statement = connection.createStatement();

        //Убедимся что в бд нет товаров, которые собираемся добавлять
        ResultSet resultSet = statement.executeQuery(checkQueryCNT);
        while (resultSet.next()) {
            int cnt = resultSet.getInt("CNT");
            System.out.printf("%d%n", cnt);
        }

        // int count = resultSet.getInt("CNT");

        //Добавим товар и убедимся что он появился в бд
        PreparedStatement pstm = connection.prepareStatement(insert);

        pstm.setString(1, "Банан");
        pstm.setString(2, "FRUIT");
        pstm.setBoolean(3, false);
        pstm.executeUpdate();

        resultSet = statement.executeQuery(checkQuery);

        while (resultSet.next()) {
            int id = resultSet.getInt("FOOD_ID");
            String name = resultSet.getString("FOOD_NAME");
            String type = resultSet.getString("FOOD_TYPE");
            boolean exot = resultSet.getBoolean("FOOD_EXOTIC");
            System.out.printf("%d, %s, %s, %s%n", id, name, type, exot);
        }

        //Добавим товар и убедимся что он появился в бд
        pstm = connection.prepareStatement(insert);

        pstm.setString(1, "Питайя");
        pstm.setString(2, "FRUIT");
        pstm.setBoolean(3, true);
        pstm.executeUpdate();

        System.out.println(statement.executeQuery(checkQuery));

        resultSet = statement.executeQuery(checkQuery);

        while (resultSet.next()) {
            int id = resultSet.getInt("FOOD_ID");
            String name = resultSet.getString("FOOD_NAME");
            String type = resultSet.getString("FOOD_TYPE");
            boolean exot = resultSet.getBoolean("FOOD_EXOTIC");
            System.out.printf("%d, %s, %s, %s%n", id, name, type, exot);
        }

        //Очистим таблицу от наших записей и убедимся что она вернулась в первоначальное состояние
        statement.execute(removeProd);

        ResultSet resultSet2 = statement.executeQuery(query);

        while (resultSet2.next()) {
            int id = resultSet2.getInt("FOOD_ID");
            String name = resultSet2.getString("FOOD_NAME");
            String type = resultSet2.getString("FOOD_TYPE");
            boolean exot = resultSet2.getBoolean("FOOD_EXOTIC");
            System.out.printf("%d, %s, %s, %s%n", id, name, type, exot);
        }

        connection.close();
    }
}
