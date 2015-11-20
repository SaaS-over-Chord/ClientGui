/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientgui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pratyush Kumar(pratyush)
 * @author Vasu Vardhan(vardhan)
 */
public class ProcessSender {
    
    Socket socket;
    public ProcessSender() throws IOException
    {
        
        try {
            socket = new Socket(XTerminal.host , 15440);
        } catch (IOException ex) {
            Logger.getLogger(ProcessSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( ClientGui.migration ==1)
        {
            Iterator <Object> it = ClientGui.objectList.iterator();
            while(it.hasNext())
            {
                String fileName = "i.txt";//to be modified later
                sendFile(socket, fileName);
                fileName = "o.txt";
                sendFile(socket, fileName);

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                boolean status = false;
                try {    	
                    out.writeObject(it.next());
                    status = in.readBoolean();
                }
                catch (IOException e1) {
                        System.out.println("IO Exception e1" +e1);
                        socket.close();
                        return;
                }
                if (status) {
                    System.out.println("Successfully migrated " );
                } 
                else {
                    System.out.println("Failed to migrate " );
                }
                try {
                    in.close();
                    out.close();
                }
                catch (IOException e) {
                        System.out.println("file close failed: " +
                                e.getMessage());
                }
            }
            ClientGui.migration=0;
            ClientGui.totalObjects =0;
        }
        socket.close();
    }
    
    private void sendFile(Socket socket,String filename) throws IOException
    {
        File myFile = new File(filename);
        byte[] mybytearray = new byte[(int) myFile.length()];
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt((int)myFile.length());
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = socket.getOutputStream();
        //os.write((int)myFile.length());
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        
    }
}
