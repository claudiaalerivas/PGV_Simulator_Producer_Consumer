# ¡Bienvenido al código fuente de nuestro programa!

En este documento podrás visualizar el como esta construido el programa a total detalle.

# Clase Restaurant

```
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
```
Esta clase es la base en la que se apoyarán los hilos Productor y Consumidor todo el proceso lógico tanto el de almacenar los productos como el de consumir.

## Atributos

- **warehouse**
  > Lista de vegetales que se encuentran en el almacén
- **name**
  > Nombre del restaurante

- **warehouseSize**
  > Tamaño del almacén

## Constructor

Esta clase cuenta con un solo constructor para inicializar todos los atributos con los valores colocados en los parametros indicados.

## Metodos de acceso

Se ha creado un método de acceso para acceder al atributo **name**.

## Metodo addVegetable

- El método **addVegetable(String vegetable)** recibe un parámetro de tipo String, el cual es el nombre del vegetal recibido, y agrega el nombre del vegetal recibido como un nuevo elemento en el atributo **warehouse**.

- Si el almacén esta lleno mostrará un mensaje por consola indcando que el almacén esta lleno, y hará que el hilo que haya entrado a este método se quede esperando.

- Caso contrario se añadirá el vegetal recibido al almacén, mostrará un mensaje por consola indicando que el vegetal ha sido agregado y notificará a todos los hilos que estén esperando.

## Metodo removeVegetable

- El método **removeVegetable(String consumerName)** recibe un parámetro de tipo String, el cual es el nombre del consumidor y eliminará un vegetal del almacén.

- Si el almacén esta vacío mostrará un mensaje por consola indicando que no hay vegetales para consumir, y hará que el hilo que haya entrado a este método se quede esperando.

- Caso contrario se eliminará el vegetal más antiguo del almacén, mostrará un mensaje por consola indicando quien ha consumido el vegetal y notificará a todos los hilos que estén esperando.


# Clase Producer

```
package com.stardewvalley.threads;

import com.stardewvalley.elements.Restaurant;

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

```
Esta hilo es el responsable de producir los vegetales y una vez que hayan crecido, se llevarán al restaurante.

## Atributos

- **warehouse**
  > Objeto de tipo Restaurant que representa el almacén
- **vegetablesToPlant**
  > Número de vegetales que se van a plantar
- **farmerName**
  > Nombre del granjero

- **MAX_GROWTH_TIME**
  > Tiempo máximo que se tarda en crecer un vegetal

- **growTime**
  > Tiempo que se tarda en crecer un vegetal

- **vegetableList**
  > Lista de vegetales que se van a plantar

## Constructor

Esta clase cuenta con un solo constructor para inicializar todos los atributos (excepto la lista de vegetales) con los valores colocados en los parametros indicados.

## Metodo growTime

- El método **growTime()** se encarga de hacer el calculo del tiempo que se tarda en crecer un vegetal.

## Metodo getVegetable

- El método **getVegetable()** se encarga de obtener un vegetal aleatorio de la lista de vegetales.

## Metodo run

- El método **run()** se encarga de realizar el proceso lógico de producción de vegetales.

- Se crea un bucle **for** que se ejecuta en base a la cantdad de veces que el atributo **vegetablesToPlant** tenga como valor.

- En cada iteración del bucle se crea un nuevo vegetal, se muestra por consola el nombre del granjero que ha producido el vegetal, el nombre del vegetal y para simular su crecimiento se llama al método estático **Thread.sleep()** que recibirá por parámetro la cantidad del tiempo que se tardará en crecer el vegetal.

- Una vez que el vegetal haya crecido se llevará al restaurante.