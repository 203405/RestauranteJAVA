package Model;

import java.util.Arrays;
import java.util.Observable;

public class Restaurant extends Observable {
    public boolean clientExists;
    public int count=0;
    public boolean confirm;
    public int limitClient;
    public boolean[] arrayTables;
    public int tableAuxiliar;
    public int contClient;
    public boolean isAccess;
    public int contPeticiones;
    public int order;
    public int food;

    public Restaurant(){
        arrayTables = new boolean[20];
        Arrays.fill(arrayTables, false);
        order=0;
        food=0;
        confirm=false;
        limitClient = 0;
        contPeticiones =0;
        clientExists =false;
        isAccess =false;
        contClient =0;
        tableAuxiliar = -1;


    }


    public synchronized int entry(String nombre){
        int numMesa = -1;
        try {
            while (limitClient > 20) {
                wait();
            }
            contClient++;
            limitClient++;
            isAccess = true;
            clientExists = true;
            for (int i = 0; i < 21; i++) {
                if (!arrayTables[i]) {
                    numMesa = i;
                    tableAuxiliar = i;
                    arrayTables[i] = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numMesa);

        return numMesa;
    }

    public int ordenar(){
        synchronized (this) {
            order++;
            notifyAll();
            int rest=order-1;
            return rest  ;
        }
    }

    public void servicioOrden() {
        synchronized (this) {
            if (order <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
                setChanged();
                notifyObservers("libreMesero " + tableAuxiliar);
            } else {
                order--;
                contPeticiones++;
                notifyAll();
                setChanged();
                notifyObservers("ocupadoMesero " + tableAuxiliar);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void cook() {
        boolean notify = false;
        synchronized (this) {
            if (contPeticiones > 0) {
                food++;
                contPeticiones--;
                notify = true;
            }
        }
        if (notify) {
            synchronized (this) {
                setChanged();
                notifyObservers("ocupado");
                notifyAll();
            }
        }
    }



    public synchronized void comer() throws InterruptedException {
        while (food <= 0) {
            wait();
        }
        food--;
        Thread.sleep(6000
        );
    }

    public void salir(int numMesaLibre){
        synchronized (this) {
            if(!confirm){
                confirm=true;

            }else{
                contClient--;
                limitClient--;
                clientExists =false;

            }
            arrayTables[numMesaLibre] = false;
            notifyAll();
            count++;
            setChanged();
            notifyObservers("" + count);
        }
    }

    public void recepcion(){
        synchronized (this) {
            while(contClient < 1 || clientExists){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isAccess =false;
        }
        synchronized (this) {
            notifyAll();
        }
    }


}
