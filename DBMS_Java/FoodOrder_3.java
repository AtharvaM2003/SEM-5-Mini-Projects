package AJP_Microproject;
import AJP_Microproject.FoodOrder_4;

import javax.swing.JOptionPane;

import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FoodOrder_3 extends JFrame implements ActionListener{
JTextField t1;
JPasswordField pass;
JLabel l1,l2,l3;
JButton b;
JPanel p;
FoodOrder_3(){
setTitle("Food Ordering System Login");
p = new JPanel();
add(p);
p.setLayout(null);
l1= new JLabel("Username");
l1.setBounds(50,50,150,30);
l2= new JLabel("Password");
l2.setBounds(50,100,150,30);
t1=new JTextField();
t1.setBounds(120,50,100,30);
pass=new JPasswordField();
pass.setBounds(120,100,100,30);
b= new JButton("Login");
b.setBounds(75,150,70,30);
l3=new JLabel("Password length must be >6 characters");
l3.setBounds(50,200,500,30);
p.add(l1);
p.add(l2);
p.add(t1);
p.add(pass);
p.add(b);
setSize(300,400);
setVisible(true);
b.addActionListener(this);
}
public void actionPerformed (ActionEvent e) {
	String Password=pass.getText();
	String UserName=t1.getText();

	 try {
         Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database", "root", "Atharva");

         PreparedStatement st = (PreparedStatement) con.prepareStatement("Select UserName, Password from registration where UserName=? and Password=?");

         st.setString(1, UserName);
         st.setString(2, Password);
         ResultSet rs = st.executeQuery();
         if (rs.next()) {
        	 dispose();
        	 FoodOrder_4 f=new FoodOrder_4();
        	 setVisible(true); // The next Window will get Visible
        		setVisible(false); // The Previous Window will get closed
              JOptionPane.showMessageDialog(b, "You have successfully logged in");
         } else {
             JOptionPane.showMessageDialog(b, "Wrong Username & Password");
             t1.setText("");
             pass.setText("");
         }
     } catch (SQLException sqlException) {
         sqlException.printStackTrace();
     }
 }

       

public static void main(String[] args) {
new FoodOrder_2();
}
}