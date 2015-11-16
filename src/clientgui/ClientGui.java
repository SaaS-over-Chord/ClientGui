/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vardhan
 */
public class ClientGui {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s;
        try {
            XTerminal terminal = XTerminal.getInstance();
            //InputStream isr=
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    
             while(true){
                 //System.out.print(">>");
                 //s=reader.readLine();
                 //terminal.executeCommand(s);
             }
// TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
