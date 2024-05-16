package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;
/**
 * The TableGenerator class is responsible for generating a JTable from a list of objects.
 * @param <T> the type of the objects in the list
 */
public class TableGenerator<T> {
    /**
     * Generates a JTable from a list of objects.
     * @param list the list of objects
     * @return the JTable
     */
    public JTable generateTableFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return new JTable();
        }

        T firstObject = list.get(0);
        Vector<String> header = new Vector<>();
        for (Field field : firstObject.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                header.add(field.getName());
            }
        }
        Vector<Vector<Object>> data = new Vector<>();
        for (T object : list) {
            Vector<Object> row = new Vector<>();
            for (Field field : object.getClass().getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    try {
                        row.add(field.get(object));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            data.add(row);
        }
        return new JTable(new DefaultTableModel(data, header));
    }
}