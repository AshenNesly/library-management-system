/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Calendar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jframe.DBConnection;
import jframe.HomePage;

/**
 *
 * @author my pc
 */
public class IssueBook extends javax.swing.JFrame {

 public IssueBook() {
        initComponents();
        addDocumentListeners();
    }

    private void addDocumentListeners() {
        txt_memberid.getDocument().addDocumentListener(new MemberDocumentListener());
        txt_bookid.getDocument().addDocumentListener(new BookDocumentListener());
    }

    private class MemberDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            fetchMemberDetails(txt_memberid.getText().trim());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fetchMemberDetails(txt_memberid.getText().trim());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }
    }

    private class BookDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            fetchBookDetails(txt_bookid.getText().trim());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fetchBookDetails(txt_bookid.getText().trim());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }
}

private void fetchMemberDetails(String memberId) {
    String query = "SELECT member_name, member_gender, member_contact FROM member_details WHERE member_id = ?";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, memberId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            lbl_membername.setText(rs.getString("member_name"));
            lbl_gender.setText(rs.getString("member_gender"));
            lbl_contact.setText(rs.getString("member_contact"));
        } else {
            clearMemberFields();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error fetching member details: " + e.getMessage());
    }
}

private void fetchBookDetails(String bookId) {
    String query = "SELECT book_name, book_author, book_quantity FROM book_details WHERE book_id = ?";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_senanayake", "root", "");
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, bookId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            lbl_bookname.setText(rs.getString("book_name"));
            lbl_author.setText(rs.getString("book_author"));
            lbl_quantity.setText(rs.getString("book_quantity"));
        } else {
            clearBookFields(); 
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error fetching book details: " + e.getMessage());
    }
}

    private void clearMemberFields() {
        lbl_membername.setText("");
        lbl_gender.setText("");
        lbl_contact.setText("");
    }

    private void clearBookFields() {
        lbl_bookname.setText("");
        lbl_author.setText("");
        lbl_quantity.setText("");
    }


    public boolean issueBook() {
    boolean isIssued = false;

    try {
        int bookId = Integer.parseInt(txt_bookid.getText().trim());
        int memberId = Integer.parseInt(txt_memberid.getText().trim());
        String bookName = lbl_bookname.getText();
        String memberName = lbl_membername.getText();

        Date issueDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(issueDate);
        cal.add(Calendar.DAY_OF_MONTH, 14);
        Date returnDate = cal.getTime();

        java.sql.Date sqlIssueDate = new java.sql.Date(issueDate.getTime());
        java.sql.Date sqlReturnDate = new java.sql.Date(returnDate.getTime());

        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO issued_book_details(book_id, book_name, member_id, member_name, issue_date, return_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            pst.setString(2, bookName);
            pst.setInt(3, memberId);
            pst.setString(4, memberName);
            pst.setDate(5, sqlIssueDate);
            pst.setDate(6, sqlReturnDate);
            pst.setString(7, "pending");

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                isIssued = true;
                JOptionPane.showMessageDialog(this, "Book issued successfully");

                updateBookCount();
                clearFields(); 
            } else {
                JOptionPane.showMessageDialog(this, "Failed to issue the book.");
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid book or member ID format.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error issuing book: " + e.getMessage());
    }

    return isIssued;
    }

    private void clearFields() {
        txt_memberid.setText("");
        txt_bookid.setText("");
        clearMemberFields();
        clearBookFields();
    }
          
    
    
public void updateBookCount() {
    int bookId = Integer.parseInt(txt_bookid.getText());
    
    try {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE book_details SET book_quantity = book_quantity - 1 WHERE book_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        
        pst.setInt(1, bookId);
        
        int rowCount = pst.executeUpdate();
        
        if (rowCount > 0) {
            
            int initialCount = Integer.parseInt(lbl_quantity.getText());
            lbl_quantity.setText(Integer.toString(initialCount - 1));
        } else {
            JOptionPane.showMessageDialog(this, "Can't update book count");
        }
        
    } catch (SQLException e) {
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
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_memberid = new javax.swing.JTextField();
        txt_bookid = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lbl_quantity = new javax.swing.JLabel();
        lbl_membername = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_bookname = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
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
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel14.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel14.setText("Return date");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel15.setText("Enter Member ID");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jLabel17.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel17.setText("Date of issue");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        txt_memberid.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_memberid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memberidActionPerformed(evt);
            }
        });
        jPanel2.add(txt_memberid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 300, -1));

        txt_bookid.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txt_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookidActionPerformed(evt);
            }
        });
        jPanel2.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 300, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1030, 170));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setText("Student details");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("Book details");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button_images/student.png"))); // NOI18N
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button_images/book.png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel1.setText("Contact");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, 20));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel2.setText("Quantity");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel4.setText("Gender");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 20));

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel5.setText("Name");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel12.setText("Author");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 310, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel13.setText("Name");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, -1, -1));

        jButton1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jButton1.setText("Issue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, 190, 70));

        lbl_quantity.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_quantity.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 350, 250, 30));

        lbl_membername.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_membername.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_membername, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 250, 30));

        lbl_gender.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_gender.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 250, 30));

        lbl_contact.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_contact.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 250, 30));

        lbl_bookname.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_bookname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 250, 250, 30));

        lbl_author.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbl_author.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(lbl_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, 250, 30));

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

 String memberId = txt_memberid.getText().trim();
    String bookId = txt_bookid.getText().trim();

    // Check if member ID is entered
    if (memberId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a member ID.");
        return;
    }

    // Fetch member details
    fetchMemberDetails(memberId);

    // If member details are not found, show message and exit
    if (lbl_membername.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Member ID not found.");
        return;
    }

    // Check if book ID is entered
    if (bookId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a book ID.");
        return;
    }

    // Fetch book details
    fetchBookDetails(bookId);

    // If book details are not found, show message and exit
    if (lbl_bookname.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Book ID not found.");
        return;
    }

    // Check book quantity
    if (lbl_quantity.getText().equals("0")) {
        JOptionPane.showMessageDialog(this, "Selected book is not available.");
    } else {
        // Attempt to issue the book
        if (issueBook()) {
            clearFields(); 
        } else {
            JOptionPane.showMessageDialog(this, "Failed to issue the book.");
        }
    }
        


              
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_memberidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memberidActionPerformed

    }//GEN-LAST:event_txt_memberidActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBook().setVisible(true);
            }
        });

    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_bookname;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_membername;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_memberid;
    // End of variables declaration//GEN-END:variables
}