/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasir;

import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

/**
 *
 * @author DAVY
 */
public class User extends javax.swing.JFrame {
    Connection koneksi;
    PreparedStatement pst;
    ResultSet rst;
    
    private static ModelUser User;
    /**
     * Creates new form User
     */
    public User(ModelUser user) {
        this.User = user;
        initComponents();
        
        koneksi=database.koneksiDB();
        update_tabel();
        delay();
        
        if(comboLvl.getSelectedItem().toString().equals("Admin")){
            autonumber("admin");
        } else if(comboLvl.getSelectedItem().toString().equals("Kasir")){
            autonumber("kasir");
        }
        
        if(!user.getRole().equals("admin")){
            jPanel7.setVisible(false);
        }
    }
        
    public void delay(){
    Thread clock=new Thread(){
        public void run(){
            for(;;){
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
                jTextField9.setText(format.format(cal.getTime()));
                 jTextField6.setText(format2.format(cal.getTime()));
                
            try { sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(transaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      };
    clock.start();
    }
    
    public void autonumber(String role){
    try{
        String sql = "SELECT MAX(RIGHT(UserId,4)) AS NO FROM login WHERE Role='"+role+"'"; // 
        pst=koneksi.prepareStatement(sql);
        rst=pst.executeQuery();
        while (rst.next()) {
            String tKode = "";
            if(role.equals("kasir")){
                tKode = "U";
            } else{
                tKode = "A";
            }
            
            if (rst.first() == false) {
                    userid.setText(tKode+"0001");
                } else {
                    rst.last();
                    int auto_id = rst.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    for (int j = 0; j < 4 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    userid.setText(tKode + no);
                }
                
            }
        rst.close();
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    
    public void update_tabel(){
       try {
        String sql="SELECT * FROM login "; //WHERE Role = 'kasir'
        pst=koneksi.prepareStatement(sql);
        rst=pst.executeQuery();
        jTable1.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ JOptionPane.showMessageDialog(null, e);}        
    }
    
    private void clsr(){
        userid.setText("");
        username.setText("");
        password.setText("");
    }
    
    private void simpan(){
     try {
            boolean usernameExists = false;
            String sql="SELECT * FROM login WHERE username = '"+ username.getText() +"'"; //WHERE Role = 'kasir'
            pst=koneksi.prepareStatement(sql);
            rst=pst.executeQuery();
            if(rst.next()) {
                usernameExists = true;
            }
            
            if(!usernameExists){
                sql="INSERT INTO login(Userid, Username, Password, Role) VALUES (?,?,?,?)";
                pst=koneksi.prepareStatement(sql);
                pst.setString(1, userid.getText());
                pst.setString(2, username.getText().toLowerCase());
                pst.setString(3, password.getText());
                pst.setString(4, comboLvl.getSelectedItem().toString().toLowerCase());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Tersimpan");
            } else{
                JOptionPane.showMessageDialog(null, "Username telah terdaftar! \nCoba dengan username yang lain.", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                clsr();
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
           update_tabel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        username = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboLvl = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        userid = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(153, 0, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 0, 255));

        jLabel13.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Data Barang");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel13)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 80));

        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 118, 48));

        jPanel4.setBackground(new java.awt.Color(153, 0, 255));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Laporan");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel15)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 118, 70));

        jPanel3.setBackground(new java.awt.Color(153, 0, 255));

        jLabel14.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Transaksi");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel14)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 118, 80));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel16.setText("Data User");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(22, 22, 22))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 118, 70));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        jPanel5.setBackground(new java.awt.Color(255, 255, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(153, 51, 255));

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Input Data User");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 280, 50));

        jTextField9.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jTextField9.setEnabled(false);
        jPanel5.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 86, -1));

        jTextField6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jTextField6.setEnabled(false);
        jPanel5.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 93, -1));

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel5.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 77, 41));

        simpan.setText("Save");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        jPanel5.add(simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 77, 41));

        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel5.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 310, 77, 41));

        jLabel12.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel12.setText("Data User");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 80, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 440, 340));

        username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        jPanel5.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 180, 30));

        jLabel8.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel8.setText("Username");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));

        password.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        jPanel5.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, 180, 30));

        jLabel9.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel9.setText("Password");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, -1, -1));

        jLabel10.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel10.setText("Level");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, -1, -1));

        comboLvl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboLvl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Kasir" }));
        comboLvl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboLvlItemStateChanged(evt);
            }
        });
        comboLvl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLvlActionPerformed(evt);
            }
        });
        jPanel5.add(comboLvl, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 260, 180, -1));

        jLabel11.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel11.setText("UserId");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        userid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        userid.setEnabled(false);
        userid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useridActionPerformed(evt);
            }
        });
        jPanel5.add(userid, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, 180, 30));
        jPanel5.add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, -1));

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jPanel5.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 80, 20));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        simpan();
        clsr();
        
        if(comboLvl.getSelectedItem().toString().equals("Admin")){
            autonumber("admin");
        } else if(comboLvl.getSelectedItem().toString().equals("Kasir")){
            autonumber("kasir");
        }
        simpan.setEnabled(true);
    }//GEN-LAST:event_simpanActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            int row=jTable1.getSelectedRow();
            String tabel_klik=(jTable1.getModel().getValueAt(row, 0).toString());
            String sql="SELECT * FROM login WHERE UserId='"+tabel_klik+"'";
            pst=koneksi.prepareStatement(sql);
            rst=pst.executeQuery();
            if (rst.next()) {
            String data1=rst.getString(("UserId"));
            userid.setText(data1);
            String data2=rst.getString(("Username"));
            username.setText(data2);
            String data3=rst.getString(("Password"));
            password.setText(data3);
            String data4=rst.getString(("Role"));
            comboLvl.setSelectedItem(data4);

            simpan.setEnabled(false);
            }
        }catch (Exception e) {JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable1MouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        try {
          String value1=userid.getText();
          String value2=username.getText();
          String value3=password.getText();
          String value4=comboLvl.getSelectedItem().toString().toLowerCase();
         String sql="UPDATE login SET Username='"+value2+"', Password='"+value3+"', Role='"+value4+"' WHERE UserId='"+value1+"'";
         pst=koneksi.prepareStatement(sql);
         pst.execute();
         JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
        }
        catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
        update_tabel();
        clsr();
        
        if(comboLvl.getSelectedItem().toString().equals("Admin")){
            autonumber("admin");
        } else if(comboLvl.getSelectedItem().toString().equals("Kasir")){
            autonumber("kasir");
        }
        simpan.setEnabled(true);
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        try { 
            String sql="delete from login where UserId=?";
            pst=koneksi.prepareStatement(sql);
            pst.setString(1, userid.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Terhapus");
        }catch (Exception e) {JOptionPane.showMessageDialog(null, e);} 
        
        update_tabel();
        clsr();
        
        if(comboLvl.getSelectedItem().toString().equals("Admin")){
            autonumber("admin");
        } else if(comboLvl.getSelectedItem().toString().equals("Kasir")){
            autonumber("kasir");
        }
        simpan.setEnabled(true);
    }//GEN-LAST:event_deleteActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        new transaksi(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        new laporan(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void comboLvlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLvlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboLvlActionPerformed

    private void useridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_useridActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        update_tabel();
        delay();
        clsr();
        
        simpan.setEnabled(true);
    }//GEN-LAST:event_refreshActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        new databarang(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void comboLvlItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboLvlItemStateChanged
        // TODO add your handling code here:
        if(comboLvl.getSelectedItem().toString().equals("Admin")){
            autonumber("admin");
        } else if(comboLvl.getSelectedItem().toString().equals("Kasir")){
            autonumber("kasir");
        }
    }//GEN-LAST:event_comboLvlItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new User(User).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboLvl;
    private javax.swing.JButton delete;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField password;
    private javax.swing.JButton refresh;
    private javax.swing.JButton simpan;
    private javax.swing.JButton update;
    private javax.swing.JTextField userid;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
