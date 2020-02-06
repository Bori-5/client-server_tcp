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
public class Client {
	public static void main(String[] args){
        int porta=3500;
        try {
            Socket connessione;
            String server = "localhost";
            connessione = new Socket(server,porta);
            System.out.println("connesione aperta");
	    
	    
		InputStreamReader r=new InputStreamReader(System.in);  
		BufferedReader buff=new BufferedReader(r);  
		System.out.println("Enter your message: ");  
		String msgout=buff.readLine();  
				
	    
		//Once accepted 
		InputStreamReader isr = new InputStreamReader(connessione.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String message = br.readLine();
		System.out.println("Il server mi ha detto... " + message + " :O");
		// to send data to the client 
		PrintStream ps = new PrintStream(connessione.getOutputStream());
		ps.println(msgout);
	    
            connessione.close();
            System.out.println("connesione chiusa");
        } catch(BindException e){
            System.out.println("Error: unable to bind client socket to indicated port ("+porta+"), the port may be already in use\n"+e);
        } catch (ConnectException e2) {
            System.out.println("Error: unable to connect to server\n"+e2);
        } catch(InterruptedIOException e3){
            System.out.println("Error: action of IO interrupted enough to cause timeout\n"+e3);
        } catch (SocketException e4) {
            System.out.println("Error: Generic socket exception\n"+e4);
        } catch (IOException e5) {
            System.out.println("Error: Generic error\n"+e5);
        }

	}
			
}