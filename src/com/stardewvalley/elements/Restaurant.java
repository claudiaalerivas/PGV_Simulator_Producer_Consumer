package com.stardewvalley.elements;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
  private List<String> warehouse;
  private String name;
  private int warehouseSize;

  public Restaurant(String name, int warehouseSize) {
    this.name = name;
    this.warehouse = new ArrayList<>();
    this.warehouseSize = warehouseSize;
  }

  public String getName() {
    return name;
  }

  public synchronized void addVegetable(String vegetable) {
    try {
      while (warehouse.size() >= this.warehouseSize) {
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

  public synchronized void removeVegetable(String consumerName) {
    try {
      while (this.warehouse.isEmpty()) {
        System.out.println("No hay vegetales para consumir, espere un momentico :D");
        wait();
      }
      String vegetalConsumed = this.warehouse.remove(0);
      System.out.println("El consumidor: " + consumerName + ", ha consumido: " + vegetalConsumed);

      notifyAll();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
