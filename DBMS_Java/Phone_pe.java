package AJP_Microproject;

import AJP_Microproject.Phone_pe_2;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Phone_pe extends JFrame implements ActionListener{
	JLabel l,l1,l2,l3,l4;
	JTextField tf,tf1,tf2,tf3,tf4;
	JButton b;

	Phone_pe(){
		l=new JLabel("Name");
		l.setBounds(50,50,60,20);
		tf=new JTextField();
		tf.setBounds(120,50,250,20);
		
		l1=new JLabel("Address");
		l1.setBounds(50,100,60,20);
		tf1=new JTextField();
		tf1.setBounds(120,100,250,20);
		
		l2=new JLabel("Bank Name");
		l2.setBounds(50,150,80,20);
		tf2=new JTextField();
		tf2.setBounds(120,150,250,20);
		
		l3=new JLabel("Mobile No");
		l3.setBounds(50,200,60,20);
		tf3=new JTextField();
		tf3.setBounds(120,200,150,20);
		
		
		b=new JButton("Log in");
		b.setBounds(100,250,100,20);
		b.addActionListener(this);
		
		add(l);		add(tf);
		add(l1);	add(tf1);
		add(l2);	add(tf2);
		add(l3);	add(tf3);
		add(b);
		
		
		setSize(600,400);
		setLayout(null);
		setVisible(true);
		setTitle("Registration of User");
	}
	public void actionPerformed(ActionEvent e) {
		Phone_pe_2 p=new Phone_pe_2();
		setVisible(true);
		setVisible(false);
		
	}
	public static void main(String[] args) {
		new Phone_pe();

	}



}
