package ru.chernykh.io;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by mikhail on 04.11.16.
 * Аккуратный вывод коллекций
 */
public class PPrint {
    public static String pformat(Collection<?> c) {
        if (c.size() == 0) return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Object elem: c
             ) {
            //все с новой строки, кроме первого
            if (c.size() != 1)
                result.append("\n ");
            result.append(elem);
        }

        if(c.size() != 1)
            result.append("\n");
        result.append("]");

        return result.toString();
    }
    //Печатаем любую коллекцию
    public static void pprint(Collection<?> c) {
        System.out.println(pformat(c));
    }
    //Коллекция объектов
    public static void pprint(Object[] c) {
        System.out.println(pformat(Arrays.asList(c)));
    }
}
