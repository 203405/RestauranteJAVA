package Model;

public class Chef implements Runnable{
    private Restaurant restaurant;
    public Chef(Restaurant restaurant){
        this.restaurant=restaurant;
    }
    @Override
    public void run() {
        while(true){
            restaurant.cook();
        }
    }
}
