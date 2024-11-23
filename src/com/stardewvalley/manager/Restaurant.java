package com.stardewvalley.manager;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
  private List<String> vegetables = new ArrayList<>();
  private String name;

  public Restaurant(String name) {
    this.name = name;
  }

  public synchronized void addVegetable(String vegetable) {
    this.vegetables.add(vegetable);
  }

  public synchronized void removeVegetable(String vegetable) {
    this.vegetables.remove(vegetable);
  }
}
