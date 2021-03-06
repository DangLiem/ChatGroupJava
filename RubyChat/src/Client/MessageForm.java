/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author dangliem
 */
public class MessageForm extends javax.swing.JFrame {

    /**
     * Creates new form MessageForm
     */
    public final static String SERVER_IP = "127.0.0.1";
    public final static int SERVER_PORT = 8686;
    public static Socket socket;
    public static DataInputStream inputFromServer;
    public static DataOutputStream outputToServer;
    
    public static String name;
    private int portServer;
    private ServerSocket serverSocket;
    
    public static DefaultListModel<String> model1;
    
    public static DefaultListModel<String> memberOnline;
    
    public static HashMap<String, DefaultListModel<String>> listModel;
    public static HashMap<String, Socket> listSocket;
    //public static HashMap<String, ReadFromServer> listThread;
    public static boolean isGroup = true;
    
    public static String nameSentTo = "";
    public MessageForm() throws IOException {
        initComponents();
        this.execute();
        this.setLocationRelativeTo(null);
        
    }
    
    private void execute() throws IOException{
        model1 = new DefaultListModel<>();
        memberOnline = new DefaultListModel<>();
        listModel = new HashMap<>();
        listSocket = new HashMap<>();
       // listThread = new HashMap<>();
        jList_Online.setModel(memberOnline);
        jList_Message.setModel(model1);
        
        socket = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
        System.out.println("Connected: " + socket);
        
        name = JOptionPane.showInputDialog("Type name: ");
        String port = JOptionPane.showInputDialog("Type port: ");
        portServer = Integer.parseInt(port);
        System.out.println("Port: " + portServer);
        
        //init server
        this.initServerP2P();
        
        //listening
        Listening listening = new Listening(serverSocket);
        listening.start();
        
        //send name
        MessageForm.send(name);
        System.out.println("Send name: " + name);
        //bind name to jlb_name
        jlb_name.setText(name);
        
        //send port 
        System.out.println("Send port: " + port);
        MessageForm.send(port);
        
        
    }
    
    public static void send(String str) throws IOException{
        outputToServer = new DataOutputStream(socket.getOutputStream());
        outputToServer.writeUTF(str);
    }
    
      public static void send(String str, Socket sk) throws IOException{
        DataOutputStream outputToClient = new DataOutputStream(sk.getOutputStream());
        outputToClient.writeUTF(str);
        System.out.println("Send: " + str + "to: " + sk);
    }
    
    public static void receive() throws IOException {
        inputFromServer = new DataInputStream(socket.getInputStream());
        String str = inputFromServer.readUTF();
        MessageForm.addMessage(str);
    }

    public static void addMessage(String str){
        model1.addElement(str);
    }
    public static void addMessage(String str, String name){
        listModel.get(name).addElement(str);
    }
    
    public static void addOnline(String memName, String memPort){
        if(!memberOnline.contains(memName)){
            try {
                memberOnline.addElement(memName);
                int port = Integer.parseInt(memPort);
                Socket newSocket = new Socket(SERVER_IP, port);
                System.out.println("Connected: " + newSocket);
                MessageForm.send(name, newSocket);
                listSocket.put(memName, newSocket);
                listModel.put(memName, new DefaultListModel<>());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error: addOnline");
            }
            
        }
    }
    public static void removeOnline(String mem){
        if(memberOnline.contains(mem)){
            memberOnline.removeElement(mem);
            listSocket.remove(mem);
            listModel.remove(mem);
            //listThread.get(mem).stop();
        }
            
        
    }
    
    public void initServerP2P(){
        try{
            serverSocket = new ServerSocket(portServer);
            System.out.println("Gán port cho server socket:" + portServer + "...");
            System.out.println("Server đã được khởi động: "  + serverSocket);
            System.out.println("Đợi kết nối từ các client...");
        } catch(IOException e){
            System.out.println("Tạo server lỗi !!!");
            e.printStackTrace();
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jlb_name = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Online = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_Message = new javax.swing.JList<>();
        jPanel7 = new javax.swing.JPanel();
        jTF_chat = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Message");

        jlb_name.setFont(new java.awt.Font("Lucida Grande", 2, 12)); // NOI18N
        jlb_name.setText("Liêm Đẹp Trai");
        jlb_name.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlb_name, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jlb_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Group");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 2, 14)); // NOI18N
        jLabel1.setText("Member");

        jList_Online.setForeground(new java.awt.Color(0, 204, 51));
        jList_Online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_OnlineMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList_Online);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jList_Message.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t[Me] : Ok dan \t\t\t\t\t", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choi", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choi", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choichoi[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t[Me] : Ok dan \t\t\t\t\t", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choi", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choi", "[AnhDaDen]:\tHello Dan Choi !!", "[AnhDaDo] :\tHello Liem Dz !!", "\t\t\t\t\tOK Dan choichoi" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList_Message);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTF_chat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTF_chatKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTF_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTF_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTF_chatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTF_chatKeyPressed
        // TODO add your handling code here:
        if(isGroup){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) { 
                String strChat = jTF_chat.getText();
                try {
                    String sms = "    " + strChat;
                    this.send(strChat);
                    this.addMessage(sms);
                    jTF_chat.setText("");

                } catch (IOException ex) {
                    Logger.getLogger(MessageForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) { 

                try {
                    String strChat = jTF_chat.getText();
                    String sms = "    " + strChat;
                    this.addMessage(sms, nameSentTo);
                    String smsSend = "[" + name + "]" + ": " + strChat;
                    this.send(smsSend, listSocket.get(nameSentTo));
                    jTF_chat.setText("");


                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
        }
    }    
    }//GEN-LAST:event_jTF_chatKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            MessageForm.send("**Close" + this.name);
            //socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jList_OnlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_OnlineMouseClicked
        // TODO add your handling code here:
        if(isGroup)
            isGroup = false;
        String nameChat = jList_Online.getSelectedValue();
       // System.out.println("test name chat: " + nameChat);
        jList_Message.setModel(listModel.get(nameChat));
        nameSentTo = nameChat;
        
    }//GEN-LAST:event_jList_OnlineMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!isGroup){
            isGroup = true;
            jList_Message.setModel(model1);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MessageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MessageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MessageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MessageForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MessageForm messageForm;
            try {
                messageForm = new MessageForm();
                messageForm.setVisible(true);
                ReadFromServer read = new ReadFromServer(MessageForm.socket);
                read.start();
            } catch (IOException ex) {
                Logger.getLogger(MessageForm.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        });
        
        
              
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList_Message;
    private javax.swing.JList<String> jList_Online;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTF_chat;
    private javax.swing.JLabel jlb_name;
    // End of variables declaration//GEN-END:variables
}

class ReadFromServer extends Thread{
    public Socket socket;
    public String name = "**Group";
    public ReadFromServer(Socket socket) {
        this.socket = socket;
    }
    public ReadFromServer(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }    
        @Override
        public void run() {
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(socket.getInputStream());
                while(true){
                    String sms = dis.readUTF();
                    System.out.println("sms la: " + sms);
                    
                    //layten
                    if(sms.substring(0, 6).equals("**Name")){
                        try{
                            String[] subs = sms.split(",");
                            String memName = subs[0].substring(6);
                            String memPort = subs[1];
                            MessageForm.addOnline(memName, memPort);
                            System.out.println("Test nhan memName: " + memName + ", port: " + memPort); 
                        }catch (Exception e1){
                            System.out.println("Error get name, port from client");
                            System.out.println(e1.getMessage());
                        }   
                    } else if(sms.substring(0, 8).equals("**Delete")){
                        String memRemove = sms.substring(8);
                        MessageForm.removeOnline(memRemove);
                    } else{
                        if(this.name=="**Group")
                            MessageForm.addMessage(sms);
                        else{
                            MessageForm.addMessage(sms, name);
                        }
                    }
                    
                    
                    
                }     
            } catch (IOException ex) {
                try {
                    dis.close();
                    socket.close();
                } catch (IOException ex1) {
                    System.out.println("Disconected");
                }
                System.out.println(ex.getMessage());
            }
        }         
}
class Listening extends Thread {
    private ServerSocket serverSocket;
    public Listening(ServerSocket socket){
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                Socket socket = serverSocket.accept();
                DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
                String memName = inputFromServer.readUTF();
                ReadFromServer temp = new ReadFromServer(socket, memName);
                temp.start();
                System.out.println("Thread Started");
            } catch (IOException ex) {
                Logger.getLogger(Listening.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}