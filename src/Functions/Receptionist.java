package Functions;

import Model.Restaurant;

public class Receptionist implements Runnable{
    private Restaurant restaurant;
    public Receptionist(Restaurant restaurant){
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            restaurant.recepcion();

        }
    }
}
