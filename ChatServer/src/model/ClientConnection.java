/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author nagyg
 */
public class ClientConnection {
    private String name;
    private BufferedReader br;
    private PrintWriter pw;

    public ClientConnection(String name, BufferedReader br, PrintWriter pw) {
        this.name = name;
        this.br = br;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }
    
    
    
}
