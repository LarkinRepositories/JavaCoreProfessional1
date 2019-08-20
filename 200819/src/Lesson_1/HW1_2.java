package Lesson_1;

import java.util.ArrayList;
import java.util.Arrays;
/*
1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;
 */
public class HW1_2<T> {

    public static <T> T[] swapElements(T[] array, int indexFrom , int indexTo) {
        try {
            T tmp = array[indexTo];
            array[indexTo] = array[indexFrom];
            array[indexFrom] = tmp;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.print(String.format("Значения индексов для данного массива должны быть от 0 до %s", array.length-1));
        }
        return array;
    }

    public static <T> ArrayList toArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    public static void main(String[] args) {
        String[] strings = {"1", "10", "15", "27"};
        Integer[] integers = {1,10,15,271};
        System.out.println(Arrays.toString(swapElements(strings, 0, 3)));
        System.out.println(Arrays.toString(swapElements(integers, 0, 3)));
        System.out.println(toArrayList(strings).getClass().getSimpleName());
    }
}
