package Lesson_1.HW3;

import Lesson_1.HW3.Box.Box;
import Lesson_1.HW3.Fruits.Apple;
import Lesson_1.HW3.Fruits.Orange;

public class Main {
    public static void main(String[] args) {
      Box<Apple> appleBox = new Box<>();
      appleBox.add(new Apple(), new Apple(), new Apple(), new Apple());
      Box<Orange> orangeBox = new Box<>();
      Box<Apple> anotherAppleBox = new Box<>();
      anotherAppleBox.add(new Apple());
      orangeBox.add(new Orange(), new Orange(), new Orange());
      System.out.println(appleBox.compare(orangeBox));
      System.out.println(appleBox.getWeight());
      appleBox.sprinkle(anotherAppleBox);
      System.out.println(appleBox.toString());
      System.out.println(anotherAppleBox.toString());

    }
}
