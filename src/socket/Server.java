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
		int players_n = 0; //numero giocatori
		Socket connessione; //!!!!!!!!da cambiare in serverEcho, Socket connessione non è più necessario come oggetto!!!!!!!!!!!!!!!!!!!!!!!!
		ServerEcho echos;
		
		while (!flag && players_n < 3) { //fino a che non si sono connessi tutti i giocatori o non è avvenuta una eccezione
			try{
				sSocket = new ServerSocket(porta);
				
				System.out.println("in attesa della connessione...");
				Countdown cd = new Countdown(sSocket,time); //istanzio un oggetto dedito al countdown
				Thread t = new Thread(cd); //metto l'oggetto del countdown dentro ad una istanza della classe thread
				t.start(); //avvio il countdown
				
				
				connessione = sSocket.accept(); //quando la connessione avviene
				flag=true; //segnalo che la connessione è avvenuta
				cd.setFlag(flag); //lo comunico anche al thread in esecuzione per fermare il countdown
				System.out.println("Connessione stabilita");
				
				message_handler(connessione);
				
				
			}catch(UnknownHostException e){
				System.out.println("Errore DNS\n"+e);
				flag=true; //segnalo l'eccezione interrompendo il ciclo
			}catch(BindException e2){
				System.out.println("Errore, un altro processo e' già in ascolto su questa porta\n"+e2);
				flag=true; //segnalo l'eccezione interrompendo il ciclo
			}catch(Exception e3) {
				flag=true; //segnalo l'eccezione generica interrompendo il ciclo
			}
		}
		
		
		if(flag){
			try{
				//!!!!!!!connessione da cambiare con un ciclo che chiude la connessione da tutti i thread!!!!!
				connessione.close(); //chiudo il socket
				System.out.println("Connessione chiusa");
			}catch(IOException e){
				System.out.println("Error: Generic error\n"+e);
			}
			
		}
	}
	
	public static void message_handler(Socket connessione){
		//da spostare sul thread
		try{
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
		}catch(IOException e){
			
		}
	}
}