/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientgui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author vardhran
 */
public class Terminal {
    private static Terminal terminal;
    public BufferedWriter writer;
    public BufferedReader reader;
    public Process proc;
    private Terminal() throws IOException
    {
    //    String[] command = { "xterm", "-e", "my", "command", "with", "parameters" };
        //proc = new ProcessBuilder("bash","-e","/bin/bash").start();
        ProcessBuilder builder =  new ProcessBuilder("sh");
        //ProcessBuilder builder =  new ProcessBuilder("gnome-terminal","-e");
        builder.redirectErrorStream(true);
        proc = builder.start();
        System.out.print(proc);
        writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
             reader = new BufferedReader (new InputStreamReader(proc.getInputStream()));
        //Process process = Runtime.getRuntime().exec("gnome-terminal");
    }
    
    public static synchronized Terminal getInstance() throws IOException
    {
        if(terminal == null)
        {
            terminal = new Terminal();
             
        }
        return terminal;
    }
    public InputStream executeCommand(String input) throws IOException
    {
        try{
            writer.write("((" + input + ") && echo --EOF-- \n) || echo --EOF-- \n");
        }
        catch(Exception e)
        {
            System.out.println("Exception is thrown"+e);
        }
        finally{
        writer.flush();
        }
        String line;
        
        line = reader.readLine ();
        while (  line!= null && !line.trim().equals("--EOF--") ) {
            System.out.println (line);
            line = reader.readLine ();
        }
        
        //reader.close();
        return null;
    }
}