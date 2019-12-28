/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dangliem
 */
public class Server {
    private int port;
    //public static ArrayList<Socket> listSocket;
    public static HashMap<String, Socket> listClient;
    public static HashMap<String, String> listPort;

    public Server(int port) {
        this.port = port;
    }
    
    private void excute() throws IOException{
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is listening...");
        while(true){
            Socket socket = server.accept();
            System.out.println("Connected: " + socket);
//            Server.listSocket.add(socket);
            ReadServer read = new ReadServer(socket);
            read.start();
        }
        
    }
     public static void send(String str, Socket socket) throws IOException{
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        outputToClient.writeUTF(str);
    }
    
    public static void main(String[] args) throws IOException {
        //Server.listSocket = new ArrayList<>();
        Server.listClient = new HashMap<>();
        Server.listPort = new HashMap<>();
        Server server = new Server(8686);
        server.excute();
    }
    
    class ReadServer extends Thread{
        private Socket server;

        public ReadServer(Socket server) {
            this.server = server;
        }

        @Override
        public void run() {
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(server.getInputStream());
                
                //put to listClient
                String name = dis.readUTF();
                Server.listClient.put(name, server);
                System.out.println("put name:" + name);
                
                //put to listPort;
                String port = dis.readUTF();
                Server.listPort.put(name, port);
                System.out.println("put port: " + name + ", " + port);
                //send mem to client
                Iterator<String> listMem = Server.listClient.keySet().iterator();
                while(listMem.hasNext()){
                    String mem = listMem.next();
                    Iterator<String> listName = Server.listClient.keySet().iterator();
                    while(listName.hasNext()){
                        String nameOnline = listName.next();
                        //send name + port
                        String memSend = "**Name" + nameOnline + "," + listPort.get(nameOnline);
                        System.out.println("memSend: " + memSend);
                        Server.send(memSend, Server.listClient.get(mem));
                    }
                }
                while(true){
                    String sms = dis.readUTF();
                    if(sms.contains("**Close")){
                        String nameCl = sms.substring(7);
                        System.out.println("Dong ket noi voi: " + nameCl);
                        Server.listClient.remove(nameCl);
                        Server.listPort.remove(nameCl);
                        //send delete online
                        Iterator<String> delete = Server.listClient.keySet().iterator();
                        while(delete.hasNext()){
                            String nameDelete = delete.next();
                            String memDelete = "**Delete" + nameCl;
                            Server.send(memDelete, Server.listClient.get(nameDelete));
                          
                        }
                        dis.close();
                        server.close();
                        continue;
                    }
                    Iterator<String> listName = Server.listClient.keySet().iterator();
                    while(listName.hasNext()){
                        String nameClient = listName.next();
                        if(Server.listClient.get(nameClient).getPort() != server.getPort()){
                            DataOutputStream dos = new DataOutputStream(Server.listClient.get(nameClient).getOutputStream());
                            String smsSend = "[" + name + "]" + ": " + sms;
                            dos.writeUTF(smsSend);
                        }
                    }
                    
                    System.out.println(sms);
                }
               
            } catch (IOException ex) {
                try {
                    dis.close();
                    server.close();
                } catch (IOException ex1) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
   
}
