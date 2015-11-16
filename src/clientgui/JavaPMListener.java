/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author pratz
 */
public class JavaPMListener implements Runnable{
    
    ServerSocket serverSocket;

    public JavaPMListener() {
        try{
        this.serverSocket = new ServerSocket(15442);
        }
        catch(IOException e)
        {
            System.out.println("Exception in creating socket"+e);
        }
    }
    
    @Override
    public void run() {
        while(true){
            try {
                accept();
            } catch (IOException ex) {
                System.out.println("exception arose in accepting"+ex);
            }
        }
    }
    
    private void accept() throws IOException{
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out;
        try {
            clientSocket = serverSocket.accept();
            }catch (SocketException e){
                    System.out.println("server socket error"+ e);
                    System.exit(-1);
            } catch (IOException e) {
                System.out.println("ServerSocket accept error"+ e);
                System.exit(-1);
            }
        try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }
            catch(IOException e){
                System.out.println("error in opening printwriter on socket" +e);
            }
        if(in.readLine()=="ending JavaPM instance")
        {
            //prompt the user that server is exiting 
            JOptionPane.showMessageDialog(null, "This node is exiting , please switch to"+in.readLine());
            ClientGui.totalObjects = Integer.parseInt(in.readLine());
            ClientGui.migration = 1;
        }
    }
    
    
}
