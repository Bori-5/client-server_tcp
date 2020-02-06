/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.*;
import java.net.*;

/**
 *
 * @author informatica
 */
public class Server {

	public static void main(String[] args) {
		ServerSocket sSocket;
		int porta = 3500;
		boolean flag = false;
		int time = 10; //tempo di attesa in secondi
		while (!flag) { //fino a che non è avvenuta la connessione o non si è verificata una delle eccezioni
			try{
				sSocket = new ServerSocket(porta);
				Socket connessione;
				System.out.println("in attesa della connessione...");
				Countdown cd = new Countdown(sSocket,time); //istanzio un oggetto dedito al countdown
				Thread t = new Thread(cd); //metto l'oggetto del countdown dentro ad una istanza della classe thread
				t.start(); //avvio il countdown
				//sSocket.setSoTimeout(time*1000); //la chiamata non è necessaria data la presenza del thread countdown
				connessione = sSocket.accept(); //quando la connessione avviene
				flag=true; //segnalo che la connessione è avvenuta
				cd.setFlag(flag); //lo comunico anche al thread in esecuzione per fermare il countdown
				System.out.println("Connessione stabilita");
				
				
				InputStreamReader r=new InputStreamReader(System.in);  
				BufferedReader buff=new BufferedReader(r);  
				System.out.println("Enter your message: ");  
				String msgout=buff.readLine();  
				
				// to send data to the client 
				PrintStream ps = new PrintStream(connessione.getOutputStream());
				ps.println(msgout);
				//Once accepted 
				InputStreamReader isr = new InputStreamReader(connessione.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String message = br.readLine();
				System.out.println("Il client mi ha detto... " + message + " :D");
				
				connessione.close(); //chiudo il socket
				System.out.println("Connessione chiusa");
			}catch(UnknownHostException e){
				System.out.println("Errore DNS\n"+e);
				flag=true; //segnalo l'eccezione interrompendo il ciclo
			}catch(SocketTimeoutException e2){
				System.out.println("Errore, nessun client connesso entro il timeout\n"+e2); //l'istruzione non è necessaria e non sarà mai eseguita perchè non vi sono chiamate a setSoTimeout
				flag=true; //segnalo l'eccezione interrompendo il ciclo
			}catch(BindException e3){
				System.out.println("Errore, un altro processo e' già in ascolto su questa porta\n"+e3);
				flag=true; //segnalo l'eccezione interrompendo il ciclo
			}catch(Exception e4) {
				flag=true; //segnalo l'eccezione generica interrompendo il ciclo
			}
		}
	}
}