package Model;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Client implements Runnable{
    private final AnchorPane anchor;
    private Restaurant restaurant;
    private static String[] arrayPositions;
    public Client(AnchorPane anchor, Restaurant restaurant) {
        this.anchor = anchor;
        this.restaurant = restaurant;
        arrayPositions = new String[] {
                "256 56", "347 56", "441 56", "540 56",
                "256 130", "347 130", "441 130", "540 130",
                "256 414", "347 414", "441 414", "540 414",
                "256 495", "347 495", "441 495", "540 495",
                "256 576", "347 576", "441 576", "540 576"
        };



    }
    @Override
    public void run() {

        Image patrickWalking = new Image(getClass().getResource("/principal/Resource/gif/patrickWalking.gif").toExternalForm());
        ImageView clientPatrick = new ImageView(patrickWalking);

        Image patrickEating = new Image(getClass().getResource("/principal/Resource/gif/patrickEating.gif").toExternalForm());
        clientPatrick.setFitWidth(50);
        clientPatrick.setFitHeight(50);
        Platform.runLater(() -> {
            clientPatrick.setLayoutX(24);
            clientPatrick.setLayoutY(340);
            anchor.getChildren().add(clientPatrick);
        });


        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), clientPatrick);
        transition2.setOnFinished(event -> {
            clientPatrick.setOpacity(1);
            transition2.setCycleCount(1);
        });

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                transition2.setToX(clientPatrick.getLayoutX() + 300);
                transition2.play();
                transition2.setOnFinished(event -> clientPatrick.setOpacity(1));
            });
        }

        int countMesa = restaurant.entry(Thread.currentThread().getName());
        String[] layout = arrayPositions[countMesa].split(" ");
        Platform.runLater(() -> {
            clientPatrick.setLayoutX(Integer.parseInt(layout[0]));
            clientPatrick.setLayoutY(Integer.parseInt(layout[1]) + 20);
        });





            restaurant.ordenar();



        clientPatrick.setImage(patrickEating);
        try {
            TranslateTransition transition4 = new TranslateTransition(Duration.seconds(1), clientPatrick);
            Platform.runLater(() -> {
                transition4.play();
                transition4.setOnFinished(event -> clientPatrick.setOpacity(1));
            });

            restaurant.comer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        clientPatrick.setImage(patrickWalking);
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(1), clientPatrick);
        transition2.setOnFinished(event -> {
            clientPatrick.setOpacity(1);
            transition3.setCycleCount(1);
        });


       restaurant.salir(countMesa);
        Platform.runLater(() -> {
            anchor.getChildren().remove(clientPatrick);
        });


    }

    public static class Chef implements Runnable{
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
}




