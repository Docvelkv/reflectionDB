package workOfDB;

import annotations.Column;
import annotations.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    /**
     * Получение имени таблицы БД
     * @param obj Объект аннотированного класса
     * @return String
     */
    private static String getTableName(Object obj) {
        String name = "";
        if(obj.getClass().isAnnotationPresent(Table.class))
            name = obj.getClass().getAnnotation(Table.class).name();
        return name;
    }

    /**
     * Получение имён полей аннотированного класса
     * @param obj Объект класса
     * @return String
     */
    private static String getNamesOfFields(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> names = new ArrayList<>();
        for (Field field : fields){
            if(field.isAnnotationPresent(Column.class))
                names.add(String.format("'%s'", field.getAnnotation(Column.class).name()));
        }
        return String.format("(%s)", String.join(", ", names));
    }

    /**
     * Получение значений полей аннотированного класса
     * @param obj Объект класса
     * @return String
     */
    private static String getValuesOfFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>();
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    values.add(String.format("'%s'", field.get(obj).toString()));
                }
            }
        }catch (IllegalAccessException e){
            System.out.println(e.getMessage());
        }
        return String.format("(%s)",String.join(", ", values));
    }

    /**
     * Получение первичного ключа
     * @param obj Объект аннотированного класса
     * @return String
     */
    private static String getPrimaryKey(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        String result = "";
        try {
            for(Field field : fields){
                if (field.isAnnotationPresent(Column.class) &&
                field.getAnnotation(Column.class).primaryKey()){
                    field.setAccessible(true);
                    result = String.format("'%s'='%s'",
                            field.getAnnotation(Column.class).name(),
                            field.get(obj).toString());
                }
            }
        }catch (IllegalAccessException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Формирование запроса на вставку
     * @param obj объект, данные которого будут сохранятся в БД
     * @return String
     */
    public static String queryToInsert(Object obj){
        return String.format("INSERT INTO %s %s VALUES %s",
                getTableName(obj),
                getNamesOfFields(obj),
                getValuesOfFields(obj));
    }

    /**
     * Формирование запроса на просмотр всей записи из таблицы БД
     * @param obj объект аннотированного класса
     * @return String
     */
    public static String queryToViewRecordFromTable(Object obj) {
        return String.format("SELECT * FROM %s WHERE %s",
                getTableName(obj),
                getPrimaryKey(obj));
    }

    /**
     * Формирование запроса на обновление существующей записи
     * @param obj объект аннотированного класса
     * @param fieldName список имён полей для обновления
     * @param values список новых значений
     * @return String
     */
    public static String queryToUpdateTableRecords(Object obj, List<String> fieldName, List<String> values){
        List<String> newFields = new ArrayList<>();
        for (int i = 0; i < fieldName.size(); i++){
            newFields.add(String.format("'%s'='%s'", fieldName.get(i), values.get(i)));
        }
        return String.format("UPDATE %s SET %s WHERE %s",
                getTableName(obj),
                String.join(", ", newFields),
                getPrimaryKey(obj));
    }

    /**
     * Формирование запроса на удаление существующей записи
     * @param obj объект аннотированного класса
     * @return String
     */
    public static String queryToDeleteTableRecord(Object obj){
        return String.format("DELETE FROM %s WHERE %s",
                getTableName(obj),
                getPrimaryKey(obj));
    }
}