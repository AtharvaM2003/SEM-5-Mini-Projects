package AJP_Microproject;
import AJP_Microproject.FoodOrder_2;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FoodOrder_1 extends JFrame implements ActionListener{
	JPanel p;
	JButton b;
	
	FoodOrder_1(){
	setSize(400,400);
	setLayout(null);
	setVisible(true);
	setTitle("Food Order System-Zomato");
	p=new JPanel();
	p.setSize(400,400);
	add(p);
	b=new JButton("Order Food");
	b.setBounds(100,100,100,30);
	p.add(b);
	b.addActionListener(this);

	}
	public void actionPerformed(ActionEvent e) {
		FoodOrder_2 f=new FoodOrder_2();
		setVisible(true);
	
		setVisible(false);
		
	}

	public static void main(String[] args) {
		new FoodOrder_1();
	}

}
