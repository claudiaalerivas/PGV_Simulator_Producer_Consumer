import com.stardewvalley.elements.Restaurant;
import com.stardewvalley.threads.Consumer;
import com.stardewvalley.threads.Producer;

public class App {
  public static void main(String[] args) throws Exception {
    Restaurant restaurant = new Restaurant("La huerta", 10);

    System.out.println("----" + restaurant.getName().toUpperCase() + "----");

    Producer producer = new Producer("Paco(Granjero)", 5, restaurant);
    Consumer consumer = new Consumer("Faustino(Cliente)", 5, restaurant);
    
    producer.start();
    consumer.start();
    producer.join();
    consumer.join();
    System.out.println("El restaurante ha sido cerrado");
  }
}
