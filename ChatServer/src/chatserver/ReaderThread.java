/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientConnection;

/**
 *
 * @author nagyg
 */
public class ReaderThread extends Thread {

    private ClientConnection theClient;
    private Vector<ClientConnection> cCons;
    private StringBuffer sb;

    public ReaderThread(ClientConnection theClient, Vector<ClientConnection> cCons, StringBuffer sb) {
        this.theClient = theClient;
        this.cCons = cCons;
        this.sb = sb;
    }

    @Override
    public void run() {
        boolean fut = true;

        do {
            try {
                String message = theClient.getBr().readLine();//jön a klienstől egy üzenet a szerverre

                if ("-=EXIT=-".equals(message)) {
                    System.out.println(theClient.getName() + " kilépett.");
                    sb.append(theClient.getName() + " kilépett.");
                    cCons.remove(theClient);
                    fut = false;

                    StringBuffer clientString = new StringBuffer();
                    cCons.forEach(cCon -> clientString.append(cCon.getName() + "; "));

                    for (ClientConnection cCon : cCons) {
                        cCon.getPw().println(theClient.getName() + " kilépett");//elküldjük a nevét és amit küldött
                        cCon.getPw().println("!!!ClientList;" + clientString.toString());
                        cCon.getPw().flush();
                    }

                } else {
                    System.out.println(theClient.getName() + ": " + message);
                    sb.append(theClient.getName() + ": " + message + "\n");

                    for (ClientConnection cCon : cCons) {
                        if (!cCon.equals(theClient)) {//aki küldte annak nem küldjük el
                            cCon.getPw().println(theClient.getName() + ": " + message);//elküldjük a nevét és amit küldött
                            cCon.getPw().flush();
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } while (fut);
    }

}
