/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasir;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

/**
 *
 * @author DAVY
 */
public class laporan extends javax.swing.JFrame {
    Connection koneksi;
    PreparedStatement pst;
    ResultSet rst;
    String tanggal,tanggal2, sql;
    
    private static ModelUser User;
    /**
     * Creates new form subpopup
     */
    public laporan(ModelUser user) {
        this.User = user;
        initComponents();
        koneksi=database.koneksiDB();
        delay();
        
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
                 jTextField3.setText(format2.format(cal.getTime()));                
            try { sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(transaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      };
    clock.start();
    }
    
    Object[] ColumnNames = {"Nomor", "Kode Transaksi", "Kode Detail", "Nama Barang", "Jumlah Barang", "Tanggal Transaksi", "Jam Transaksi", "Sub Total", "Total Transaksi" };
    public void getAllData(){
                                  
       SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
       tanggal=format.format(jDateChooser2.getDate());
       tanggal2=format.format(jDateChooser3.getDate());
       try {          
        
        DefaultTableModel model = new DefaultTableModel(ColumnNames, 0);
        
        jTableTrx.setModel(model);
        //jTableTrx.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        pst=koneksi.prepareStatement("SELECT "+ 
            "trx.Kode_Transaksi,\n" +
            "dtl.Kode_Detail,\n" +
            "brg.Nama_Barang,\n" +
            "dtl.Jumlah,\n" +
            "trx.Tanggal,\n" +
            "trx.Jam,\n" +
            "dtl.Subtotal,\n" +
            "trx.Total\n" +
            "FROM transaksi trx\n" +
            "INNER JOIN detail_barang dtl ON dtl.Kode_Detail = trx.Kode_Detail\n" +
            "INNER JOIN barang brg ON brg.Kode_Barang = dtl.Kode_Barang\n" +
            "WHERE trx.Tanggal between '"+tanggal+"' and '"+tanggal2+"'\n" +
            "ORDER By trx.Kode_Transaksi;");
        rst=pst.executeQuery();
        /*jTable3.setModel(DbUtils.resultSetToTableModel(rst));*/
        
        ResultSetMetaData md = rst.getMetaData();
        int columns = md.getColumnCount();
        
        while (rst.next())
        {
           Vector row = new Vector();
            for (int i = 1; i <= columns; i++)
            {
                row.addElement( rst.getObject(i) );
            }
            model.addRow(row);
        }
                        
       } catch (Exception e){ 
            Logger.getLogger(datatransaksi.class.getName()).log(Level.SEVERE, null, e);}
    }
    
    public void PrintToPDF() throws DocumentException, FileNotFoundException{
        //create a PDF document
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setApproveButtonText("Save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Path
        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            try {
            Document pdftrx = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f); // A4 Landscape
            PdfWriter.getInstance(pdftrx, new FileOutputStream(new File(chooser.getSelectedFile(),"Laporan Transaksi.pdf"))); // Path + Nama File
            pdftrx.open();
            
            pdftrx.add(new Paragraph("Toko Pandhawa",FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLUE)));
            //pdftrx.add(new Paragraph(new Date().toString()));
            pdftrx.add(new Paragraph("Laporan Transaksi Penjualan"));
            pdftrx.add(new Paragraph("----------------------------------------------------------------------------------------------------------------"));
            
            PdfPTable tableTrx= new PdfPTable(ColumnNames.length); // Buat tabel berdasarkan jumlah kolom.
            PdfPCell cell = new PdfPCell();
            Phrase column = null;
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorderWidthLeft(1);
            
            for(int i=0;i<jTableTrx.getColumnCount();i++)
            {
                column = new Phrase((String) ColumnNames[i]); 
                cell = new PdfPCell(column);
                tableTrx.addCell(cell);
            }
            
            //int[] colWidths = new int[ColumnNames.length];
            //colWidths = ConvertToIntArray(listColWidths);
            
            for(int i = 0; i< jTableTrx.getRowCount(); i++){
                String Nomor = String.valueOf(i+1);
                String KodeTrx = jTableTrx.getValueAt(i, 0).toString();
                String KodeDetailTrx = jTableTrx.getValueAt(i, 1).toString();
                String NamaBrg = jTableTrx.getValueAt(i, 2).toString();
                String JumlahBrg = jTableTrx.getValueAt(i, 3).toString();
                String TglTrx = jTableTrx.getValueAt(i, 4).toString();
                String JamTrx = jTableTrx.getValueAt(i, 5).toString();
                String SubTotalTrx = jTableTrx.getValueAt(i, 6).toString();
                String TotalTrx = jTableTrx.getValueAt(i, 7).toString();
                
                tableTrx.addCell(Nomor);
                tableTrx.addCell(KodeTrx);
                tableTrx.addCell(KodeDetailTrx);
                tableTrx.addCell(NamaBrg);
                tableTrx.addCell(JumlahBrg);
                tableTrx.addCell(TglTrx);
                tableTrx.addCell(JamTrx);
                tableTrx.addCell(SubTotalTrx);
                tableTrx.addCell(TotalTrx);
            }
            
            pdftrx.add(tableTrx);
            int Total = 0;
            try {
                sql="SELECT SUM(total) as Total FROM transaksi where Tanggal between '"+tanggal+"' and '"+tanggal2+"'";
                pst=koneksi.prepareStatement(sql);
                rst=pst.executeQuery();
                while (rst.next())
                {
                   Total = Integer.parseInt(rst.getString("Total")); // VAR | VARIANT
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(laporan.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Mata Uang 
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            // End
            
            pdftrx.add(new Paragraph("----------------------------------------------------------------------------------------------------------------"));
            pdftrx.add(new Paragraph("Total Keuntungan selama "+tanggal+" sampai "+tanggal2+" adalah " + kursIndonesia.format(Total)));
            pdftrx.close();
            
            JOptionPane.showMessageDialog(null, "Laporan berhasil disimpan...");
        
            } catch (DocumentException ex) {
                Logger.getLogger(datatransaksi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(datatransaksi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(datatransaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            System.out.println("Gagal..");
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTrx = new javax.swing.JTable();
        btnCetak = new javax.swing.JButton();

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
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 80));

        jButton5.setText("Logout");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 438, 120, 48));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
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
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(23, 23, 23))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 120, 70));

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
                .addGap(21, 21, 21)
                .addComponent(jLabel14)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 120, 70));

        jPanel7.setBackground(new java.awt.Color(153, 0, 255));

        jLabel16.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(240, 240, 240));
        jLabel16.setText("Data User");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel16)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel16)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 120, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 490));

        jPanel5.setBackground(new java.awt.Color(255, 255, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel8.setText("Transaksi dari tanggal ke tanggal");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));
        jPanel5.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, 210, 30));
        jPanel5.add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 240, 210, 30));

        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 280, -1, 30));

        jPanel6.setBackground(new java.awt.Color(153, 51, 255));

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Form Laporan");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 250, 50));

        jTextField3.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jTextField3.setEnabled(false);
        jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 93, -1));

        jTextField9.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jTextField9.setEnabled(false);
        jPanel5.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 86, -1));

        jTableTrx.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableTrx);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 660, -1, 80));

        btnCetak.setText("Cetak Laporan");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jPanel5.add(btnCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, -1, 30));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        tanggal=format.format(jDateChooser2.getDate());
        tanggal2=format.format(jDateChooser3.getDate());
        sql="SELECT trx.Kode_Transaksi as 'Kode Transaksi', "
                + "trx.Kode_Detail as 'Kode Detail', "
                + "trx.Tanggal, "
                + "trx.Jam, "
                + "trx.Total, "
                + "log.Username as 'User' "
                + "FROM transaksi trx "
                + "INNER JOIN login log ON trx.LastUpdate_by = log.UserId "
                + "WHERE Tanggal between '"+tanggal+"' and '"+tanggal2+"'";
        new datatransaksi(sql).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        new transaksi(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        new databarang(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:             
//        if(tanggal.isEmpty() || tanggal2.isEmpty() ){
//            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong!", "InfoBox: " + "Peringatan!", JOptionPane.WARNING_MESSAGE);
//        }
        getAllData();
        try {
            PrintToPDF();
        } catch (DocumentException ex) {
            Logger.getLogger(laporan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        new User(User).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

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
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporan(User).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTrx;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
