/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientgui;

import com.jcraft.jsch.JSchException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pratyush Kumar(pratyush)
 * @author Vasu Vardhan(vardhan)
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
            } catch (JSchException ex) {
                Logger.getLogger(JavaPMListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void accept() throws IOException, JSchException{
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
        String str=in.readLine();
        System.out.println(str);
        if(str.equals("ending JavaPM instance") )
        {
            //prompt the user that server is exiting 
            System.out.println("entered if");
            JOptionPane.showMessageDialog(null, "This node is exiting , please switch to"+in.readLine());
            System.out.println("passed optionpane");
            //destroy the SSH connection
            System.out.println("disconnect ssh");
            ClientGui.streamCheck=0;
            //ClientGui.pos.close();
            //ClientGui.pis.close();
            ClientGui.xTerminal.channel.disconnect() ;
            ClientGui.xTerminal.session.disconnect();
            System.out.println(ClientGui.xTerminal.channel.isConnected() );
            //replace the old XTerminal with a new XTerminal
            System.out.println("new xterminal opening");
            
            
            //ClientGui.pos= new PipedOutputStream();
            //ClientGui.pis = new PipedInputStream();
            //ClientGui.pis.connect(ClientGui.pos);
            
            //ClientGui.streamChecker();
            ClientGui.xTerminal.newConnection();
            ClientGui.streamCheck=1;
            //ClientGui.xTerminal.setNewConnection();
            
            System.out.println("started new terminal    ");
            ClientGui.totalObjects = Integer.parseInt(in.readLine());
            System.out.println("objects passed on were in no" + ClientGui.totalObjects);
            ClientGui.migration = 1;
        }
    }
    
    
}
