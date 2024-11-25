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

## Métodos de acceso

Se ha creado un método de acceso para acceder al atributo **name**.

## Método addVegetable

- El método **addVegetable(String vegetable)** recibe un parámetro de tipo String, el cual es el nombre del vegetal recibido, y agrega el nombre del vegetal recibido como un nuevo elemento en el atributo **warehouse**.

- Si el almacén esta lleno mostrará un mensaje por consola indcando que el almacén esta lleno, y hará que el hilo que haya entrado a este método se quede esperando.

- Caso contrario se añadirá el vegetal recibido al almacén, mostrará un mensaje por consola indicando que el vegetal ha sido agregado y notificará a todos los hilos que estén esperando.

## Método removeVegetable

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

## Método growTime

- El método **growTime()** se encarga de hacer el calculo del tiempo que se tarda en crecer un vegetal.

## Método getVegetable

- El método **getVegetable()** se encarga de obtener un vegetal aleatorio de la lista de vegetales.

## Método run

- El método **run()** se encarga de realizar el proceso lógico de producción de vegetales.

- Se crea un bucle **for** que se ejecuta en base a la cantdad de veces que el atributo **vegetablesToPlant** tenga como valor.

- En cada iteración del bucle se crea un nuevo vegetal, se muestra por consola el nombre del granjero que ha producido el vegetal, el nombre del vegetal y para simular su crecimiento se llama al método estático **Thread.sleep()** que recibirá por parámetro la cantidad del tiempo que se tardará en crecer el vegetal.

- Una vez que el vegetal haya crecido se llevará al restaurante.

# Consumer


```
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

```
La clase Consumer representa al cliente del restaurante que consume vegetales del almacén. Esta clase incluye atributos, un constructor para inicializarlos, método de acceso y una implementación del método run para ejecutar su funcionalidad como hilo.

## Atributos

- **consumerName**
  > nombre del consumidor.
- **vegetableQuantity**
  > número de vegetales que el cliente va a consumir.
- **wareHouse**
  > referencia al almacén del restaurante donde se obtendrán los vegetales.
- **MAX_CONSUME_TIME**
  > tiempo máximo de espera simulado para el consumo de un vegetal (constante, fijada en 2000 ms).

## Constructor

Esta clase cuenta con un solo constructor para inicializar todos los atributos (excepto el tiempo de consumo) con los valores colocados en los parametros indicados.

## Método consumeTime

- El método **consumeTime()** se encarga de hacer el calculo del tiempo que se tarda en el que un cliente consuma un vegetal.

## Método getConsumerName

- El método **getConsumerName()** se encarga de obtener el nombre del consumidor.

## Método run

- Método **run()**: Este método controla el consumo de vegetales en un bucle según la cantidad requerida, simulando un tiempo de consumo entre cada iteración y actualizando el almacén.

# Clase App

Esta clase es la que se ejecutará cuando se inicie la aplicación, se inicializa un objeto de tipo Restaurant, especificando su nombre y la capacidad de almacenamiento que tendrá.

```
Restaurant restaurant = new Restaurant("La huerta", 10);
```

Posteriormente, se crean las instancias de los hilos para los roles de productor y consumidor. Cada hilo ejecuta su lógica mediante el método run, y se utiliza el método join para garantizar que los hilos completen su ejecución antes de continuar con el flujo del programa. Esto asegura que las operaciones de los productores y consumidores se sincronicen adecuadamente.

```
    Producer producer = new Producer("Paco(Granjero)", 10, restaurant);
    Producer producer2 = new Producer("Ramon(Granjero)", 10, restaurant);
    Producer producer3 = new Producer("Kevin(Cliente)", 10, restaurant);
    Producer producer4 = new Producer("Xiao(Cliente)", 10, restaurant);
    Consumer consumer = new Consumer("Faustino(Cliente)", 10, restaurant);
    Consumer consumer2 = new Consumer("Mr.Gentleman(Cliente)", 10, restaurant);
    Consumer consumer3 = new Consumer("Loquendo(Cliente)", 20, restaurant);
    producer.start();
    producer2.start();
    producer3.start();
    producer4.start();
    consumer.start();
    consumer2.start();
    consumer3.start();
    producer.join();
    producer2.join();
    consumer.join();
    consumer2.join();
    consumer3.join();
```



