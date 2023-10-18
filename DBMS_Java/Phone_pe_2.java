package AJP_Microproject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Phone_pe_2 extends JFrame implements ActionListener{
	JLabel l,l1,l2,l3,l4;
	JTextField tf,tf1,tf2,tf3,tf4;
	JButton b;

	Phone_pe_2(){
		l=new JLabel("Mobile No");
		l.setBounds(50,50,60,20);
		tf=new JTextField();
		tf.setBounds(120,50,100,20);
		
		l1=new JLabel("Bank Account No");
		l1.setBounds(50,100,150,20);
		tf1=new JTextField();
		tf1.setBounds(160,100,250,20);
		
		l2=new JLabel("Amount");
		l2.setBounds(50,150,80,20);
		tf2=new JTextField();
		tf2.setBounds(120,150,250,20);
		
		l3=new JLabel("UPI Pin");
		l3.setBounds(50,200,60,20);
		tf3=new JTextField();
		tf3.setBounds(120,200,150,20);
		
		
		b=new JButton("PAY");
		b.setBounds(100,250,100,20);
		
		
		add(l);		add(tf);
		add(l1);	add(tf1);
		add(l2);	add(tf2);
		add(l3);	add(tf3);
		add(b);
		
		b.addActionListener(this);
		setSize(600,400);
		setLayout(null);
		setVisible(true);
		setTitle("Final Payment");
	}
	
public void actionPerformed(ActionEvent e) {
	JOptionPane.showMessageDialog(b, "Payment Successful to "+tf.getText()+" of Rs "+tf2.getText());
		
	}
	public static void main(String[] args) {
		new Phone_pe_2();

	}
	
	

}
