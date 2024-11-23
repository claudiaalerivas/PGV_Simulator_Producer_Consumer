package com.stardewvalley.manager;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
  private List<String> warehouse = new ArrayList<>();
  private String name;

  public Restaurant(String name) {
    this.name = name;
  }

  public synchronized void addVegetable(String vegetable) {
    try {
      while (warehouse.size() >= 20) {
        System.out.println("El almacén esta llenito.");
        wait();
      }

      warehouse.add(vegetable);
      System.out.println("El/La " + vegetable + " ha sido depositado en el almacén");
      notifyAll();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public synchronized void removeVegetable(String vegetable) {
    this.warehouse.remove(vegetable);
  }
}
