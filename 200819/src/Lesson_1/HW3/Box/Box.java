package Lesson_1.HW3.Box;

import Lesson_1.HW3.Fruits.Fruit;

import java.util.*;

public class Box<T extends Fruit> {
   private List<T> fruits;
   private float weight = 0.0f;

   public Box(T...fruits) {
       this.fruits = new ArrayList<>(Arrays.asList(fruits));
       for (Fruit fruit: fruits) {
           this.weight += fruit.getWeight();
       }
   }

    public Box(List<T> fruits) {
       this.fruits = fruits;
       for (Fruit fruit: fruits) {
           this.weight += fruit.getWeight();
       }
    }

    public void add(T...fruits) {
        this.fruits.addAll(Arrays.asList(fruits));
    }

    public float getWeight() {
        return weight;
    }

    public boolean compare(Box<?> box) {
       return Math.abs(this.weight - box.weight) < 0.001;
    }

    public void sprinkle(Box<T> box) {
       if (box != null)
       box.fruits.addAll(this.fruits);
       else box = new Box<T>(this.fruits);
       this.fruits.clear();
    }


    @Override
    public String toString() {
        return "Box{" +
                "fruits=" + fruits +
                '}';
    }
}
