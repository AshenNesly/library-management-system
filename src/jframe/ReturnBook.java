/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import jframe.DBConnection;
import jframe.HomePage;

/**
 *
 * @author my pc
 */
public class ReturnBook extends javax.swing.JFrame {

    public ReturnBook() {
        initComponents();
        model = (DefaultTableModel) tbl_issuedbookdetails.getModel();
        setIssuedBookDetailsToTable();
    }
  
    
        DefaultTableModel model;
 public void getIssuedBookDetails(){
        
        String bookIdText = txt_bookid.getText().trim();
        if (bookIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID");
            return;
        }
        
        int bookId = Integer.parseInt(bookIdText);
        
        try {
            Connection con  = DBConnection.getConnection();
            String sql = "SELECT * FROM issued_book_details WHERE book_id = ? AND status = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookId);
            pst.setString(2, "pending");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                lbl_issueid.setText(rs.getString("id"));
                lbl_bookname.setText(rs.getString("book_name"));
                lbl_membername.setText(rs.getString("member_name"));
                lbl_issueddate.setText(rs.getString("issue_date"));
                lbl_returndate1.setText(rs.getString("return_date"));                
            } else {
                JOptionPane.showMessageDialog(this, "No pending issue found for this Book ID");
                lbl_issueid.setText("");
                lbl_bookname.setText("");
                lbl_membername.setText("");
                lbl_issueddate.setText("");
                lbl_returndate1.setText(""); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 
  
    public boolean returnBook(){
        
        boolean isReturned = false;
        String issueId = lbl_issueid.getText().trim();
        
        if (issueId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please check book details first");
            return false;
        }
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE issued_book_details SET status = ? WHERE id = ? AND status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "returned");
            pst.setInt(2, Integer.parseInt(issueId));
            pst.setString(3, "pending");
            
            int rowCount = pst.executeUpdate();
            
            if (rowCount > 0) {
                isReturned = true;
            } else {
                isReturned = false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isReturned;
    }

 

    
    
public void updateBookCount() {
    int bookId = Integer.parseInt(txt_bookid.getText());
    
    try {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE book_details SET book_quantity = book_quantity + 1 WHERE book_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        
        pst.setInt(1, bookId);
        
        int rowCount = pst.executeUpdate();
        
        if (rowCount > 0) {
            

        } else {
               JOptionPane.showMessageDialog(this, "Can't update book count");
        }
        
        } catch (SQLException e) {
        e.printStackTrace();
        }
}
   

        public void setIssuedBookDetailsToTable() {
        String query = "SELECT * FROM issued_book_details WHERE status = '"+"pending"+"'";
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String bookName = rs.getString("book_name");
                String memberName = rs.getString("member_name");
                String issueDate = rs.getString("issue_date");
                String returnDate = rs.getString("return_date");
                String status = rs.getString("status");

                Object[] obj = {id, bookName, memberName, issueDate, returnDate, status};
                model.addRow(obj);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel7 = new javax.swing.JLabel();

        txt_bookid = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_issueid = new javax.swing.JLabel();
        lbl_membername = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_bookname = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_issueddate = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        lbl_returndate1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_issuedbookdetails = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(0, 0));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1024, 768));
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

        jLabel7.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel7.setText("Enter Book ID");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, -1, -1));

        txt_bookid.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookidActionPerformed(evt);
            }
        });
        jPanel2.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 300, -1));

        jButton1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jButton1.setText("Check Details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 90, 190, 70));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("Return Books");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button_images/return.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1030, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel2.setText("Return date");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel12.setText("Member Name");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel13.setText("Issue ID");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, -1, -1));

        lbl_issueid.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_issueid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_issueid, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 250, 30));

        lbl_membername.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_membername.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_membername, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, 250, 30));

        jLabel18.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel18.setText("Book Name");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, -1, -1));

        lbl_bookname.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_bookname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, 250, 30));

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel4.setText("Issued Date");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, -1, -1));

        lbl_issueddate.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_issueddate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_issueddate, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 250, 30));

        jButton2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jButton2.setText("Confirm Return");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 450, 190, 70));

        lbl_returndate1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_returndate1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_returndate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 250, 30));

        tbl_issuedbookdetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbl_issuedbookdetails.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        tbl_issuedbookdetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Book Name", "Member Name", "Issued Date", "Return Date", "Status"
            }
        ));
        tbl_issuedbookdetails.setFillsViewportHeight(true);
        tbl_issuedbookdetails.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_issuedbookdetails.setRowHeight(30);
        tbl_issuedbookdetails.setShowGrid(true);
        tbl_issuedbookdetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_issuedbookdetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_issuedbookdetails);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 990, 320));

        jPanel4.setBackground(new java.awt.Color(237, 246, 246));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 1030, 160));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 1030, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    
    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
       System.exit(0);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
    this.dispose();
    new HomePage().setVisible(true);

    }//GEN-LAST:event_jLabel16MouseClicked

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed


    }//GEN-LAST:event_txt_bookidActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    getIssuedBookDetails();
              
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
    if (returnBook()) {
        JOptionPane.showMessageDialog(this, "Book returned successfully");
        updateBookCount();
        
        // Refresh table
        model.setRowCount(0); // Clear data
        setIssuedBookDetailsToTable(); // Reload updated data
        
        // Clear fields
        txt_bookid.setText("");
        
        // Clear the labels
        lbl_issueid.setText("");
        lbl_bookname.setText("");
        lbl_membername.setText("");
        lbl_issueddate.setText("");
        lbl_returndate1.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "Book return NOT successful!");
    }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbl_issuedbookdetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_issuedbookdetailsMouseClicked
        
                                                  
   // Get selected row 
    int selectedRowIndex = tbl_issuedbookdetails.getSelectedRow();

    // Ensure a row is selected
    if (selectedRowIndex != -1) {
        // Get the issue ID from the selected row
        String issueId = tbl_issuedbookdetails.getValueAt(selectedRowIndex, 0).toString();

        // get book id considering issue id
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT book_id FROM issued_book_details WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, issueId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                txt_bookid.setText(String.valueOf(bookId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // update labels with data retrieved
        lbl_issueid.setText(issueId);
        lbl_bookname.setText(tbl_issuedbookdetails.getValueAt(selectedRowIndex, 1).toString());
        lbl_membername.setText(tbl_issuedbookdetails.getValueAt(selectedRowIndex, 2).toString());
        lbl_issueddate.setText(tbl_issuedbookdetails.getValueAt(selectedRowIndex, 3).toString());
        lbl_returndate1.setText(tbl_issuedbookdetails.getValueAt(selectedRowIndex, 4).toString());
    }
        
    }//GEN-LAST:event_tbl_issuedbookdetailsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnBook().setVisible(true);
            }
        });

    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_bookname;
    private javax.swing.JLabel lbl_issueddate;
    private javax.swing.JLabel lbl_issueid;
    private javax.swing.JLabel lbl_membername;
    private javax.swing.JLabel lbl_returndate1;
    private javax.swing.JTable tbl_issuedbookdetails;
    private javax.swing.JTextField txt_bookid;
    // End of variables declaration//GEN-END:variables
}