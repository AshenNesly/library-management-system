/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;


import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 *
 * @author my pc
 */
public class ViewRecords extends javax.swing.JFrame {

    /**
     * Creates new form ManageBooks
     */
    


    DefaultTableModel model;

    public ViewRecords() {
        initComponents();
        model = (DefaultTableModel) tbl_issuebookdetails.getModel();
        setIssuedBookDetailsToTable();
    }

    
    
    
    public void setIssuedBookDetailsToTable() {
        String query = "SELECT * FROM issued_book_details";
        try {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
                 PreparedStatement pst = con.prepareStatement(query)) {
                executeQuery(pst);
            }
        } catch (SQLException e) {
            showErrorDialog("Error retrieving data: " + e.getMessage());
        }
    }

    
    
    
    public void clearTable() {
        model.setRowCount(0);
    }

    
    
    
    public void searchRecords() {
        clearTable();
        java.sql.Date fromDate = date_fromdate.getDatoFecha() != null ?
                new java.sql.Date(date_fromdate.getDatoFecha().getTime()) : null;
        java.sql.Date returnDate = date_returnDate.getDatoFecha() != null ?
                new java.sql.Date(date_returnDate.getDatoFecha().getTime()) : null;

        String bookName = txt_bookname.getText().trim();
        String memberName = txt_membername.getText().trim();

        StringBuilder query = new StringBuilder("SELECT * FROM issued_book_details WHERE 1=1");
        boolean hasConditions = false;

        if (fromDate != null) {
            query.append(" AND issue_date = ?");
            hasConditions = true;
        }
        if (returnDate != null) {
            query.append(" AND return_date = ?");
            hasConditions = true;
        }
        if (!bookName.isEmpty()) {
            query.append(" AND book_name LIKE ?");
            hasConditions = true;
        }
        if (!memberName.isEmpty()) {
            query.append(" AND member_name LIKE ?");
            hasConditions = true;
        }

        if (!hasConditions) {
            showErrorDialog("Please enter at least one search criterion.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
             PreparedStatement pst = con.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (fromDate != null) {
                pst.setDate(paramIndex++, fromDate);
            }
            if (returnDate != null) {
                pst.setDate(paramIndex++, returnDate);
            }
            if (!bookName.isEmpty()) {
                pst.setString(paramIndex++, "%" + bookName + "%");
            }
            if (!memberName.isEmpty()) {
                pst.setString(paramIndex++, "%" + memberName + "%");
            }

            executeQuery(pst);
        } catch (SQLException e) {
            showErrorDialog("Error retrieving data: " + e.getMessage());
        }
    }

    
    
    
    private void executeQuery(PreparedStatement pst) throws SQLException {
        try (ResultSet rs = pst.executeQuery()) {
            if (!rs.isBeforeFirst()) {
                showInfoDialog("No record found");
                return;
            }

            while (rs.next()) {
                String id = rs.getString("id");
                String bookName = rs.getString("book_name");
                String memberName = rs.getString("member_name");
                String issueDateStr = rs.getString("issue_date");
                String returnDateStr = rs.getString("return_date");
                String status = rs.getString("status");

                Object[] obj = {id, bookName, memberName, issueDateStr, returnDateStr, status};
                model.addRow(obj);
            }
        }
    }

    
    
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    
    
    
    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    
    
    
    private void deleteRecord() {
        int selectedRow = tbl_issuebookdetails.getSelectedRow();
        if (selectedRow == -1) {
            showErrorDialog("Please select a record to delete.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            String id = model.getValueAt(selectedRow, 0).toString(); // Get ID from the selected row
            String query = "DELETE FROM issued_book_details WHERE id = ?";

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
                 PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, id);
                pst.executeUpdate();

                // Remove the row from the table
                model.removeRow(selectedRow);
                showInfoDialog("Record deleted successfully.");

            } catch (SQLException e) {
                showErrorDialog("Error deleting record: " + e.getMessage());
            }
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
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        date_returnDate = new rojeru_san.componentes.RSDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        date_fromdate = new rojeru_san.componentes.RSDateChooser();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_membername = new javax.swing.JTextField();
        txt_bookname = new javax.swing.JTextField();
        btn_dlt = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_issuebookdetails = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("X");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 30, -1));

        jLabel11.setFont(new java.awt.Font("Felix Titling", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("Library management system");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 650, -1));

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 55)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("<");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        jPanel2.setBackground(new java.awt.Color(237, 246, 246));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setDisplayedMnemonic('a');
        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_returnDate.setPlaceholder("");
        jPanel3.add(date_returnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 270, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button_images/view_report.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 170, 170));

        jLabel17.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel17.setText("Issued Date ");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        jLabel14.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel14.setText("Return date");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, -1, -1));

        date_fromdate.setPlaceholder("");
        jPanel3.add(date_fromdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 270, 30));

        jButton3.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 230, 40));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel2.setText("Member name");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel3.setText("Book Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        txt_membername.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jPanel3.add(txt_membername, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 230, -1));

        txt_bookname.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_bookname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_booknameActionPerformed(evt);
            }
        });
        jPanel3.add(txt_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 230, -1));

        btn_dlt.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btn_dlt.setText("Delete Record");
        btn_dlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dltActionPerformed(evt);
            }
        });
        jPanel3.add(btn_dlt, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 170, 50));

        jButton2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jButton2.setText("View all Records");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 40, 170, 50));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setText("View Records");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 1020, 330));

        jPanel4.setBackground(new java.awt.Color(237, 246, 246));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_issuebookdetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbl_issuebookdetails.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        tbl_issuebookdetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Book Name", "Member Name", "Issued Date", "Return Date", "Status"
            }
        ));
        tbl_issuebookdetails.setFillsViewportHeight(true);
        tbl_issuebookdetails.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_issuebookdetails.setRowHeight(30);
        tbl_issuebookdetails.setShowGrid(true);
        tbl_issuebookdetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_issuebookdetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_issuebookdetails);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 980, 340));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1020, 380));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 770));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void tbl_issuebookdetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issuebookdetailsMouseClicked

    }//GEN-LAST:event_tbl_issuebookdetailsMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        
        this.dispose();
        new HomePage().setVisible(true);
        
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        clearTable();
        setIssuedBookDetailsToTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    searchRecords();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_dltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dltActionPerformed
               deleteRecord();
    }//GEN-LAST:event_btn_dltActionPerformed

    private void txt_booknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_booknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_booknameActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked



    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dlt;
    private rojeru_san.componentes.RSDateChooser date_fromdate;
    private rojeru_san.componentes.RSDateChooser date_returnDate;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_issuebookdetails;
    private javax.swing.JTextField txt_bookname;
    private javax.swing.JTextField txt_membername;
    // End of variables declaration//GEN-END:variables
}
