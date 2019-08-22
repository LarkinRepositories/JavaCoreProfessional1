package Lesson_1.HW3.Box;

import Lesson_1.HW3.Fruits.Fruit;

import java.util.*;

public class Box<T extends Fruit> {
   private List<T> fruits = new ArrayList<>();
   private float weight = 0.0f;

   public Box() {

   }

    public Box(List<T> fruits) {
       this.fruits = fruits;
    }

    public void add(T...fruits) {
        this.fruits.addAll(Arrays.asList(fruits));
    }

    public float getWeight() {
       return this.fruits.size() * this.fruits.get(0).getWeight();
    }

    public boolean compare(Box<?> box) {
       return Math.abs(this.weight - box.weight) < 0.001;
    }

    public void sprinkle(Box<T> box) {
       box.fruits.addAll(this.fruits);
       this.fruits.clear();
    }


    @Override
    public String toString() {
        return "Box{" +
                "fruits=" + fruits +
                '}';
    }
}
