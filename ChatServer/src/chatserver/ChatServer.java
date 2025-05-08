/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import model.ClientConnection;

/**
 *
 * @author nagyg
 */
public class ChatServer {

    public static void main(String[] args) {
        Vector<ClientConnection> cCons = new Vector<>();//több szál is fogja használni
        StringBuffer sb = new StringBuffer();

        try {
            ServerSocket ss = new ServerSocket(8888);
            do {
                System.out.println("Kliens kapcsolatra várok...");
                Socket s = ss.accept();

                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                String nev = br.readLine();

                //elküldi az előzményeket
                pw.println(sb.toString());
                pw.flush();

                ClientConnection cc = new ClientConnection(nev, br, pw);
                cCons.add(cc);

                ReaderThread rt = new ReaderThread(cc, cCons, sb);
                rt.start();

                System.out.println(nev + " kapcsolódása megtörtént.");
                System.out.println("Kliensek: ");
                StringBuffer clientString = new StringBuffer();
                cCons.forEach(cCon -> clientString.append(cCon.getName() + "; "));
                System.out.println(clientString);

                for (ClientConnection cCon : cCons) {
                    cCon.getPw().println("!!!ClientList;" + clientString.toString());
                     cCon.getPw().flush();
                }

//                for (ClientConnection cCon : cCons) {
//                    System.out.print(cCon.getName()+", ");
//                }
            } while (true);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
