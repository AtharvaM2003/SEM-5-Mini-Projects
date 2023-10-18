package AJP_Microproject;

import AJP_Microproject.FoodOrder_3;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FoodOrder_2 extends JFrame implements ActionListener {
	JTextField t1, t2, t3, t5, t6;
	JTextArea t4;
	JPasswordField pass;
	JLabel l1, l2, l3, l4, l5, l6;
	JButton b, b1;
	JPanel p;

	FoodOrder_2() {
		setTitle("Food Ordering System Login");
		p = new JPanel();
		add(p);
		p.setLayout(null);
		l1 = new JLabel("Name");
		l1.setBounds(50, 50, 150, 30);
		t1 = new JTextField();
		t1.setBounds(120, 50, 300, 30);

		l2 = new JLabel("Mobile No");
		l2.setBounds(50, 100, 150, 30);
		t2 = new JTextField();
		t2.setBounds(120, 100, 100, 30);

		l3 = new JLabel("Email ID");
		l3.setBounds(50, 150, 150, 30);
		t3 = new JTextField();
		t3.setBounds(120, 150, 300, 30);

		l4 = new JLabel("Address");
		l4.setBounds(50, 200, 150, 30);
		t4 = new JTextArea();
		t4.setBounds(120, 200, 150, 100);

		l5 = new JLabel("UserName");
		l5.setBounds(50, 325, 150, 30);
		t5 = new JTextField();
		t5.setBounds(120, 325, 100, 30);

		l6 = new JLabel("Password");
		l6.setBounds(50, 375, 150, 30);
		pass = new JPasswordField();
		pass.setBounds(120, 375, 100, 30);

		b = new JButton("Register");
		b.setBounds(75, 425, 100, 30);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Name = t1.getText();
				String Mobile = t2.getText();
				int len = Mobile.length();
				String Email = t3.getText();
				String Address = t4.getText();
				String UserName = t5.getText();
				String Password = pass.getText();
				String msg = "" + Name;
				msg += " \n";
				if (len != 10) {
					JOptionPane.showMessageDialog(b, "Enter a valid mobile number");
				}
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database", "root",
							"Atharva");
					String query = "INSERT INTO registration values('" + Name + "','" + Mobile + "','" + Email + "','" +
							Address + "','" + UserName + "','" + Password + "')";
					Statement sta = con.createStatement();
					int x = sta.executeUpdate(query);
					if (x == 0) {
						JOptionPane.showMessageDialog(b, "This is alredy exist");
					} else {
						JOptionPane.showMessageDialog(b, "Welcome, " + msg + "Your account is sucessfully created");

					}
					FoodOrder_3 f = new FoodOrder_3();
					setVisible(true);
					setVisible(false);
					con.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			private void exit() {
				// TODO Auto-generated method stub

			}
		});

		b1 = new JButton("Reset");
		b1.setBounds(200, 425, 100, 30);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t1.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				pass.setText("");
			}

		});
		p.add(l1);
		p.add(l2);
		p.add(l3);
		p.add(l4);
		p.add(l5);
		p.add(l6);
		p.add(t1);
		p.add(t2);
		p.add(t3);
		p.add(t4);
		p.add(t5);
		p.add(pass);
		p.add(b1);
		p.add(b);

		setSize(500, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FoodOrder_2();
	}

	public void actionPerformed(ActionEvent e) {
	}
}
