package socket;

import java.io.*;
import java.net.*;

public class Countdown implements Runnable{

    ServerSocket sSocket;
    boolean flag,close=false;
    int time;

    public Countdown(ServerSocket sSocket,int time){
        this.sSocket = sSocket;
        this.time = time;
    }

    @Override
    public void run(){
        //System.out.println(sSocket.isBound()+" "+time); //isBound da sempre vero, i metodi is(Something) restituiscono un valore in base a ciò che è già accaduto al Socket... quindi se ritorna un boolean e la condizione si è già verificata sarà sempre vera
        while(!flag&&time!=-1){ //qui non entra mai con isBound perchè è sempre vero T^T
            System.out.println(time+"\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time--;
        }
        if(time==-1){
            System.out.println("Errore, nessun client connesso entro il timeout\n");
            try {
                sSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}