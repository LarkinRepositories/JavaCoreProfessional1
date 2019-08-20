package Lesson_1.HW3;

import Lesson_1.HW3.Box.Box;
import Lesson_1.HW3.Fruits.Apple;
import Lesson_1.HW3.Fruits.Orange;

public class Main {
    public static void main(String[] args) {
       Box<Apple> appleBox = new Box<>(new Apple(), new Apple(), new Apple(), new Apple());
       Box<Orange> orangeBox = new Box<>(new Orange(), new Orange());

        System.out.println(appleBox.compare(orangeBox));
        System.out.println();

    }
}
