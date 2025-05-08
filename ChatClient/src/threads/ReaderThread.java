/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author nagyg
 */
public class ReaderThread extends Thread{

    private JTextArea taUzenetek;
    private BufferedReader br;
    private JList lstKliensek;

    public ReaderThread(JTextArea taUzenetek, BufferedReader br, JList lstKliensek) {
        this.taUzenetek = taUzenetek;
        this.br = br;
        this.lstKliensek = lstKliensek;
        setDaemon(true);
    }

    @Override
    public void run() {
       boolean fut = true;
       while(fut){
           try {
               String message = br.readLine();
               
               if(message.startsWith("!!!ClientList;")){
                   message = message.substring(13, message.length()-1);
                   String[] splits = message.split(";");
                   lstKliensek.setListData(splits);
               }else{
                 taUzenetek.append(message + "\n");  
               }
               

           } catch (IOException ex) {
               taUzenetek.append("HIBA" + ex.toString());
           }
       }
    }
    
    
    
    
}
