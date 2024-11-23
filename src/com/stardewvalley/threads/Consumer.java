package com.stardewvalley.threads;

import java.util.Scanner;

import com.stardewvalley.manager.WareHouse;

public class Consumer extends Thread{
  private static Scanner scanner = new Scanner(System.in);
  private WareHouse wareHouse;

  public Consumer(WareHouse wareHouse) {
    this.wareHouse = wareHouse;
  }

  @Override
  public void run() {
    System.out.println("Cuantas verduras quieres comprar?");
        System.out.println("Ingrese nombre de la verdura a comprar:");
        String vegetable = scanner.nextLine();
        this.wareHouse.removeVegetable(vegetable);
    super.run();
  }
}
