package com.stardewvalley.threads;

import java.util.Scanner;

public class Producer extends Thread{
  private static Scanner scanner = new Scanner(System.in);
  private WareHouse wareHouse;
  private int timeGrowth = 2000;

  public Producer(WareHouse wareHouse) {
    this.wareHouse = wareHouse;
  }

  @Override
  public void run() {
    System.out.println("Cuantas verduras quieres sembrar?");
    int numVegetables = scanner.nextInt();
    try {
      for (int i = 0; i < numVegetables; i++) {
        System.out.println("Ingrese nombre de la verdura a sembrar:");
        String vegetable = scanner.nextLine();
        Thread.sleep(timeGrowth);
        this.wareHouse.removeVegetable(vegetable);
      }
    } catch (InterruptedException e) {
      System.out.println("No se respeto el tiempo de crecimiento, intente de nuevo");
    }
    super.run();
  }
}
