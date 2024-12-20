package com.stardewvalley.threads;

import com.stardewvalley.elements.Restaurant;

public class Consumer extends Thread {

  private String consumerName = "";
  private int vegetableQuantity;
  private Restaurant wareHouse;
  private final int MAX_CONSUME_TIME = 6000;

  public Consumer(String consumerName, int vegetableQuantity, Restaurant wareHouse) {
    this.consumerName = consumerName;
    this.vegetableQuantity = vegetableQuantity;
    this.wareHouse = wareHouse;
  }

  private int consumeTime() {
    int consumeTime = (int) Math.floor(Math.random() * MAX_CONSUME_TIME) + 1000;
    return consumeTime > MAX_CONSUME_TIME ? MAX_CONSUME_TIME : consumeTime;
  }

  public String getConsumerName() {
    return consumerName;
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < vegetableQuantity; i++) {
        Thread.sleep(consumeTime());
        this.wareHouse.removeVegetable(getConsumerName());
        super.run();
      }
    } catch (Exception e) {
      System.out.println("Se ha producido un error en el consumo de los vegetales! ");
    }
  }
}
