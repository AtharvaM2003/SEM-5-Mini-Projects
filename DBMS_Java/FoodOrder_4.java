package AJP_Microproject;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class FoodOrder_4 extends JFrame {
	JPanel p;
	JButton bt, bt1, bt2, bt4, bt5, bt6, bt7;
	JLabel l, l1, l2, l3, l5, l6, l7, c;
	JTextArea ta, ta1;

	FoodOrder_4() {

		p = new JPanel();
		p.setSize(800, 700);
		p.setLayout(null);

		c = new JLabel("Items in Cart");
		c.setBounds(500, 200, 150, 20);
		ta1 = new JTextArea("");
		ta1.setBounds(500, 220, 100, 100);

		ImageIcon i = new ImageIcon("A:/Programs/advJava/AJP_Microproject/pizza.png");
		l1 = new JLabel(i);
		l1.setBounds(30, 50, 200, 150);
		bt = new JButton("Pizza");
		bt.setBounds(50, 200, 100, 20);
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt, "Item Added to cart successfully");
				ta1.append("Pizza" + "\n");

			}
		});

		ImageIcon i1 = new ImageIcon("A:/Programs/advJava/AJP_Microproject/burger.png");
		l2 = new JLabel(i1);
		l2.setBounds(20, 240, 200, 150);
		bt1 = new JButton("Burger");
		bt1.setBounds(50, 400, 100, 20);
		bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt1, "Item Added to cart successfully");
				ta1.append("Burger" + "\n");
			}
		});

		ImageIcon i2 = new ImageIcon("A:/Programs/advJava/AJP_Microproject/sandwich.png");
		l3 = new JLabel(i2);
		l3.setBounds(30, 450, 200, 140);
		bt2 = new JButton("Sandwich");
		bt2.setBounds(50, 600, 100, 20);
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt2, "Item Added to cart successfully");
				ta1.append("Sandwich" + "\n");
			}
		});

		ImageIcon i4 = new ImageIcon("A:/Programs/advJava/AJP_Microproject/pasta.png");
		l5 = new JLabel(i4);
		l5.setBounds(250, 50, 200, 150);
		bt4 = new JButton("Pasta");
		bt4.setBounds(300, 200, 100, 20);
		bt4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt4, "Item Added to cart successfully");
				ta1.append("Pasta" + "\n");
			}
		});

		ImageIcon i5 = new ImageIcon("A:/Programs/advJava/AJP_Microproject/rice.png");
		l6 = new JLabel(i5);
		l6.setBounds(250, 240, 200, 150);
		bt5 = new JButton("Rice");
		bt5.setBounds(300, 400, 100, 20);
		bt5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt5, "Item Added to cart successfully");
				ta1.append("Rice" + "\n");
			}
		});

		ImageIcon i6 = new ImageIcon("A:/Programs/advJava/AJP_Microproject/tomato.png");
		l7 = new JLabel(i6);
		l7.setBounds(250, 450, 200, 140);
		bt6 = new JButton("Tomato soup");
		bt6.setBounds(300, 600, 150, 20);
		bt6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(bt6, "Item Added to cart successfully");
				ta1.append("Tomato soup" + "\n");
			}
		});

		bt7 = new JButton("Place Order");
		bt7.setBounds(550, 550, 150, 50);
		bt7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = ta.getText();
				String b = ta1.getText();
				JOptionPane.showMessageDialog(bt7,
						"Your Shipping Address:" + "\n" + a + "\n" + "Your Ordered items are:" + "\n" + b);
				JOptionPane.showMessageDialog(bt7, "Your Order is placed Successfully");
				setVisible(false);
			}
		});

		ta = new JTextArea("");
		ta.setBounds(500, 70, 200, 100);
		l = new JLabel("Enter Shiping Address");
		l.setBounds(500, 50, 150, 20);

		p.add(ta1);
		p.add(c);
		p.add(l1);
		p.add(l2);
		p.add(l3);
		p.add(l5);
		p.add(l6);
		p.add(l7);
		p.add(ta);
		p.add(l);
		p.add(bt);
		p.add(bt1);
		p.add(bt2);
		p.add(bt4);
		p.add(bt5);
		p.add(bt6);
		p.add(bt7);
		add(p);

		setSize(800, 700);
		setVisible(true);
		setTitle("Food Order System-Zomato");
	}

	public static void main(String args[]) {
		new FoodOrder_4();
	}
}
