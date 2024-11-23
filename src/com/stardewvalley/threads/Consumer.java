package com.stardewvalley.threads;

import java.util.Scanner;

import com.stardewvalley.elements.Restaurant;

public class Consumer extends Thread {
  private static Scanner scanner = new Scanner(System.in);
  private Restaurant wareHouse;

  public Consumer(Restaurant wareHouse) {
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
