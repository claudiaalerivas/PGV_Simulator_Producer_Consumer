package com.stardewvalley.threads;

import com.stardewvalley.manager.Restaurant;

public class Producer extends Thread {
  private Restaurant warehouse;
  private int vegetablesToPlant;
  private String farmerName;
  private final int MAX_GROWTH_TIME = 5000;

  private String[] vegetableList = { "lettuce", "cabbage", "onion", "spinach", "potato", "celery", "asparagus",
      "radish", "broccoli", "artichoke", "tomato", "cucumber", "eggplant", "carrot", "green bean" };

  public Producer(String farmerName, int vegetablesToPlant, Restaurant warehouse) {
    this.farmerName = farmerName;
    this.vegetablesToPlant = vegetablesToPlant;
    this.warehouse = warehouse;
  }

  private int growTime() {
    int growTime = (int) Math.floor(Math.random() * MAX_GROWTH_TIME) + 1000;
    return growTime > MAX_GROWTH_TIME ? MAX_GROWTH_TIME : growTime;
  }

  public String getVegetable() {
    return vegetableList[(int) (Math.random() * vegetableList.length)];
  }

  public void run() {
    try {
      for (int i = 0; i < vegetablesToPlant; i++) {
        String vegetal = getVegetable();
        System.out.println(farmerName + " ha plantado un/a rico/a " + vegetal);
        Thread.sleep(growTime());
        System.out.println("El/la " + vegetal + " ha crecido");
        warehouse.addVegetable(vegetal);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
