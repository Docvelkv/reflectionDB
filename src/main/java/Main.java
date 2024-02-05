import objectInfrasructure.Employee;
import workOfDB.QueryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Константин", "example@docvel.ru");
        // запрос на вставку
        System.out.println(QueryBuilder.queryToInsert(emp1));
        // запрос на просмотр всей записи по соответствию PrimaryKey
        System.out.println(QueryBuilder.queryToViewRecordFromTable(emp1));
        // запрос на обновление полей существующей записи
        List<String> fieldName = List.of("name", "eMail");
        List<String> values = List.of("Владимир", "vladimir@gmail.com");
        System.out.println(QueryBuilder.queryToUpdateTableRecords(emp1, fieldName, values));
        // запрос на удаление записи по соответствию PrimaryKey
        System.out.println(QueryBuilder.queryToDeleteTableRecord(emp1));
    }
}
