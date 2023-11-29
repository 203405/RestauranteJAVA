package Functions;

import Model.Client;
import Model.Restaurant;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.ThreadLocalRandom;

public class Waiter implements Runnable{
    private Restaurant restaurant;
    private AnchorPane padre;
    public Waiter(AnchorPane padre, Restaurant restaurant){
        this.restaurant=restaurant;
        this.padre=padre;
    }

    @Override
    public void run() {


        while (true){
            restaurant.servicioOrden();
        }
    }
}
