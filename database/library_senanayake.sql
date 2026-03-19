-- ============================================================================
-- Library Management System — Complete Database Setup
-- ============================================================================
-- Database name : library_senanayake
-- MySQL user    : root (no password)
-- Connection URL: jdbc:mysql://localhost:3306/library_senanayake
--
-- Verified against every Java source file in the project:
--   DBConnection.java, LoginPage.java, SignupPage.java, ManageUsers.java,
--   ManageBooks.java, ManageMembers.java, IssueBook.java, ReturnBook.java,
--   ViewRecords.java, HomePage.java, Welcome.java
--
-- HOW TO IMPORT
--   Option 1 — phpMyAdmin: Import tab → Choose this file → Go
--   Option 2 — MySQL CLI : mysql -u root < library_senanayake.sql
-- ============================================================================

-- Create and select the database
CREATE DATABASE IF NOT EXISTS `library_senanayake`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `library_senanayake`;

-- ============================================================================
-- TABLE: users
-- ============================================================================
-- Used by: LoginPage.java   → SELECT * FROM users WHERE name = ? AND password = ?
--          SignupPage.java   → INSERT INTO users (name, password, email, contact) ...
--                              SELECT * FROM users WHERE name = ?
--          ManageUsers.java  → SELECT id, name, email, contact FROM users
--                              DELETE FROM users WHERE id = ?
-- ============================================================================
CREATE TABLE IF NOT EXISTS `users` (
  `id`       INT          NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email`    VARCHAR(150) DEFAULT NULL,
  `contact`  VARCHAR(30)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_users_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: book_details
-- ============================================================================
-- Used by: ManageBooks.java  → SELECT * FROM book_details
--                               SELECT * FROM book_details WHERE book_id = ?
--                               INSERT INTO book_details (book_id, book_name, book_author, book_quantity) ...
--                               UPDATE book_details SET book_name=?, book_author=?, book_quantity=? WHERE book_id=?
--                               DELETE FROM book_details WHERE book_id = ?
--          IssueBook.java    → SELECT book_name, book_author, book_quantity FROM book_details WHERE book_id = ?
--                               UPDATE book_details SET book_quantity = book_quantity - 1 WHERE book_id = ?
--          ReturnBook.java   → UPDATE book_details SET book_quantity = book_quantity + 1 WHERE book_id = ?
--
-- Note: Java code uses both setString() and setInt() for book_id.
--       MySQL silently converts numeric strings to INT, so INT is correct.
-- ============================================================================
CREATE TABLE IF NOT EXISTS `book_details` (
  `book_id`       INT          NOT NULL,
  `book_name`     VARCHAR(255) NOT NULL,
  `book_author`   VARCHAR(200) DEFAULT NULL,
  `book_quantity`  INT         NOT NULL DEFAULT 0,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: member_details
-- ============================================================================
-- Used by: ManageMembers.java → SELECT * FROM member_details
--                                SELECT * FROM member_details WHERE member_id = ?
--                                INSERT INTO member_details (member_name, member_gender, member_contact) ...
--                                UPDATE member_details SET member_name=?, member_gender=?, member_contact=? WHERE member_id=?
--                                DELETE FROM member_details WHERE member_id = ?
--          IssueBook.java     → SELECT member_name, member_gender, member_contact FROM member_details WHERE member_id = ?
--
-- member_id is AUTO_INCREMENT (members are added without specifying an id).
-- member_gender values from combo box: "Male", "Female"
-- ============================================================================
CREATE TABLE IF NOT EXISTS `member_details` (
  `member_id`      INT          NOT NULL AUTO_INCREMENT,
  `member_name`    VARCHAR(200) NOT NULL,
  `member_gender`  VARCHAR(20)  DEFAULT NULL,
  `member_contact` VARCHAR(30)  DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: issued_book_details
-- ============================================================================
-- Used by: IssueBook.java    → INSERT INTO issued_book_details
--                                 (book_id, book_name, member_id, member_name,
--                                  issue_date, return_date, status) ...
--          ReturnBook.java   → SELECT * FROM issued_book_details WHERE book_id=? AND member_id=? AND status=?
--                               SELECT * FROM issued_book_details WHERE status='pending'
--                               SELECT member_id, book_id FROM issued_book_details WHERE id=?
--                               UPDATE issued_book_details SET status=? WHERE member_id=? AND book_id=? AND status=?
--          ViewRecords.java  → SELECT * FROM issued_book_details
--                               SELECT * FROM issued_book_details WHERE 1=1 [+ filters]
--                               DELETE FROM issued_book_details WHERE id=?
--
-- NOTE: No foreign keys are defined here intentionally.
--       The Java app allows deleting books/members without first cleaning up
--       issued records, so FK constraints would cause unexpected SQL errors.
-- ============================================================================
CREATE TABLE IF NOT EXISTS `issued_book_details` (
  `id`          INT          NOT NULL AUTO_INCREMENT,
  `book_id`     INT          NOT NULL,
  `book_name`   VARCHAR(255) NOT NULL,
  `member_id`   INT          NOT NULL,
  `member_name` VARCHAR(200) NOT NULL,
  `issue_date`  DATE         NOT NULL,
  `return_date` DATE         NOT NULL,
  `status`      VARCHAR(20)  NOT NULL DEFAULT 'pending',
  PRIMARY KEY (`id`),
  KEY `idx_issued_book_id`   (`book_id`),
  KEY `idx_issued_member_id` (`member_id`),
  KEY `idx_issued_status`    (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ============================================================================
-- SAMPLE DATA
-- ============================================================================
-- You can log in with any of the users below (name / password).
-- Default admin account: admin / admin
-- ============================================================================

-- ----- Users (id is auto-generated) -----
INSERT INTO `users` (`name`, `password`, `email`, `contact`) VALUES
  ('admin',       'admin',       'admin@library.com',       '0771234567'),
  ('john',        '1234',        'john@library.com',        '0779876543'),
  ('sarah',       'sarah123',    'sarah@library.com',       '0715556677');

-- ----- Books -----
INSERT INTO `book_details` (`book_id`, `book_name`, `book_author`, `book_quantity`) VALUES
  (101, 'Effective Java',                   'Joshua Bloch',         5),
  (102, 'Clean Code',                       'Robert C. Martin',     3),
  (103, 'Head First Java',                  'Kathy Sierra',         4),
  (104, 'The Pragmatic Programmer',         'Andy Hunt',            2),
  (105, 'Design Patterns',                  'Gang of Four',         3),
  (106, 'Introduction to Algorithms',       'Thomas H. Cormen',     6),
  (107, 'Database System Concepts',         'Abraham Silberschatz', 4),
  (108, 'Computer Networking',              'James Kurose',         3),
  (109, 'Operating System Concepts',        'Abraham Silberschatz', 5),
  (110, 'Artificial Intelligence',          'Stuart Russell',       2);

-- ----- Members (member_id is auto-generated: 1, 2, 3, …) -----
INSERT INTO `member_details` (`member_name`, `member_gender`, `member_contact`) VALUES
  ('Kamal Perera',    'Male',   '0771112233'),
  ('Nimalka Silva',   'Female', '0772223344'),
  ('Ruwan Fernando',  'Male',   '0773334455'),
  ('Dilini Jayawardena', 'Female', '0714445566'),
  ('Ashan Bandara',   'Male',   '0775556677'),
  ('Tharushi Kumari', 'Female', '0716667788'),
  ('Saman Kumara',    'Male',   '0777778899'),
  ('Rashmi Dias',     'Female', '0718889900');

-- ----- Issued Book Records -----
-- Mix of "pending" (books currently out) and "returned" (history).
-- book_quantity in book_details already accounts for these issues.
INSERT INTO `issued_book_details` (`book_id`, `book_name`, `member_id`, `member_name`, `issue_date`, `return_date`, `status`) VALUES
  (101, 'Effective Java',             1, 'Kamal Perera',       '2026-01-10', '2026-01-24', 'returned'),
  (102, 'Clean Code',                 2, 'Nimalka Silva',      '2026-01-15', '2026-01-29', 'returned'),
  (103, 'Head First Java',            3, 'Ruwan Fernando',     '2026-02-01', '2026-02-15', 'pending'),
  (101, 'Effective Java',             4, 'Dilini Jayawardena', '2026-02-05', '2026-02-19', 'pending'),
  (106, 'Introduction to Algorithms', 5, 'Ashan Bandara',      '2026-02-07', '2026-02-21', 'pending'),
  (107, 'Database System Concepts',   1, 'Kamal Perera',       '2026-02-10', '2026-02-24', 'pending'),
  (104, 'The Pragmatic Programmer',   6, 'Tharushi Kumari',    '2026-01-20', '2026-02-03', 'returned'),
  (105, 'Design Patterns',            7, 'Saman Kumara',       '2026-02-12', '2026-02-26', 'pending'),
  (108, 'Computer Networking',        8, 'Rashmi Dias',        '2026-02-13', '2026-02-27', 'pending'),
  (110, 'Artificial Intelligence',    2, 'Nimalka Silva',      '2026-02-14', '2026-02-28', 'pending');

-- ============================================================================
-- VERIFICATION CHECKLIST (all confirmed against Java source code)
-- ============================================================================
-- ✅ Database name        : library_senanayake
-- ✅ MySQL user/password  : root / (empty)
-- ✅ Table: users          — columns: id, name, password, email, contact
-- ✅ Table: book_details   — columns: book_id, book_name, book_author, book_quantity
-- ✅ Table: member_details — columns: member_id, member_name, member_gender, member_contact
-- ✅ Table: issued_book_details — columns: id, book_id, book_name, member_id,
--                                          member_name, issue_date, return_date, status
-- ✅ No foreign keys (app deletes books/members independently)
-- ✅ member_id AUTO_INCREMENT (INSERT omits member_id)
-- ✅ book_id is user-supplied INT (INSERT includes book_id)
-- ✅ status values: 'pending' / 'returned'
-- ✅ Gender values: 'Male' / 'Female'
-- ============================================================================

-- End of SQL file
