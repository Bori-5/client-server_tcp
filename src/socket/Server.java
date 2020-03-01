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
		ServerSocket sSocket = null;
		int porta = 3500;
		boolean flag = false;
		int time = 10; //tempo di attesa in secondi
		int players_n = 0; //numero giocatori
		final int MAX_PLAYERS=1;
		ServerEcho echo;
		Thread[] trds = new Thread[MAX_PLAYERS];


		try{
			sSocket = new ServerSocket(porta);
		}catch(BindException e){
			System.out.println("Errore, un altro processo e' già in ascolto su questa porta\n"+e);
			flag=true; //segnalo l'eccezione interrompendo il ciclo
		}catch(Exception e2) {
			flag=true; //segnalo l'eccezione generica interrompendo il ciclo
		}


		while (!flag && players_n < MAX_PLAYERS) { //fino a che non si sono connessi tutti i giocatori o non è avvenuta una eccezione
			try{
				
				System.out.println("in attesa della connessione...");
				Countdown cd = new Countdown(sSocket,time); //istanzio un oggetto dedito al countdown
				Thread t = new Thread(cd); //metto l'oggetto del countdown dentro ad una istanza della classe thread
				t.start(); //avvio il countdown
				
				
				echo = new ServerEcho(sSocket.accept()); //quando la connessione avviene
				cd.setFlag(true); //lo comunico anche al thread in esecuzione per fermare il countdown
				trds[players_n] = new Thread(echo);
				trds[players_n].start(); //avvio il thread socket
				System.out.println("Connessione stabilita");

				players_n++;
				
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
		
		
		if(players_n==MAX_PLAYERS){
			//for(int c=0;c<players_n;c++){
			try{
				sSocket.close(); //chiudo il socket
				System.out.println("Connessione chiusa");
			}catch(IOException e){
				System.out.println("Error: Generic error\n"+e);
			}

			
		}
	}
	

}
