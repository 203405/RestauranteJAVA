package Functions;

import Model.Client;
import Model.Restaurant;
import javafx.scene.layout.AnchorPane;
import Controller.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreateClient extends Random implements Runnable{
    private AnchorPane anchor;
    private Restaurant restaurant;
    private Controller controller;
    public CreateClient(AnchorPane anchor, Restaurant restaurant, Controller controller){
        this.anchor = anchor;
        this.restaurant=restaurant;
        this.controller = controller;
    }
    private Client client;
    @Override
    public void run() {
        for(int i=0;i<100;i++){
            client =new Client(anchor,restaurant);
            Thread Hcliente = new Thread(client);
            Hcliente.setName("Client "+(i+1));
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(8000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Hcliente.setDaemon(true);
            Hcliente.start();
        }
    }

}
