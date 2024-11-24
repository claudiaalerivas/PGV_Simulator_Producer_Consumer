import com.stardewvalley.elements.Restaurant;
import com.stardewvalley.threads.Consumer;
import com.stardewvalley.threads.Producer;

public class App {
  public static void main(String[] args) throws Exception {
    Restaurant restaurant = new Restaurant("La huerta");

    System.out.println("----" + restaurant.getName().toUpperCase() + "----");

    Producer producer = new Producer("Paco", 10, restaurant);
    Producer producer2 = new Producer("Ramon", 10, restaurant);
    Producer producer3 = new Producer("APOWJAIODJWA", 10, restaurant);
    Producer producer4 = new Producer("we", 10, restaurant);
    Consumer consumer = new Consumer("Faustino", 10, restaurant);
    Consumer consumer2 = new Consumer("Mr.Gentleman", 10, restaurant);
    Consumer consumer3 = new Consumer("Loquendo", 20, restaurant);
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
    System.out.println("El restaurante ha sido cerrado");
  }
}
