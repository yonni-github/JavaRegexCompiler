package project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class CompilerWindow extends JFrame implements ActionListener {
	JLabel label1;
	JTextField txtBox1;
	JTextField txtBox2;
	JButton btnBrowse;
	JButton btnCompile;
	JTextArea textArea;
	public static boolean isDone;


	
	CompilerWindow(String title)
	{
		super(title);
		this.setLayout(new FlowLayout());
		setSize(600,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		isDone=false;
	
		label1 = new JLabel("\n\nSelect package directory to compile. eg.[C:\\Users\\me\\project\\javaTestFiles\\]");
		label1.setFont(new Font("Times New Roman",Font.BOLD,16));
		label1.setPreferredSize(new Dimension(550, 30));
		txtBox1 = new JTextField(55);
		txtBox1.setPreferredSize(new Dimension(560, 25));
		txtBox1.setFont(new Font("Times New Roman",Font.BOLD,12));
		txtBox1.setEditable(false);
		txtBox1.setBackground(new Color(255,255,255));
		//txtBox2 = new JTextField(50);
		textArea = new JTextArea("Result....\n");
		textArea.setFont(new Font("Serif", Font.ITALIC, 18));
		//textArea.setSize(500,500);    
	   // textArea.setLineWrap(true);
	    textArea.setEditable(false);
	    textArea.setVisible(true);

	    JScrollPane scroll = new JScrollPane (textArea);
	    scroll.setPreferredSize(new Dimension(560, 300));
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		btnBrowse= new JButton("BROWSE");
		btnCompile= new JButton("COMPILE CONTENTS");
		btnCompile.setEnabled(false);
		btnBrowse.addActionListener(this);
		btnCompile.addActionListener(this);
		
		add(label1);
		add(txtBox1);
		add(btnBrowse);
		add(btnCompile);
	    add(scroll);
	}



	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==btnBrowse)
		{
			JFileChooser fc= new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selection = fc.showOpenDialog(null);
				if(selection==JFileChooser.APPROVE_OPTION && fc.getSelectedFile().isDirectory())
				{
					txtBox1.setText(fc.getSelectedFile().getAbsolutePath());
					textArea.setText("Result....\n");
					btnCompile.setEnabled(true);
				}
				else if(selection==JFileChooser.APPROVE_OPTION && fc.getSelectedFile().isFile())
				{
					
					JOptionPane.showMessageDialog (null, "ERROR! Please Select a Directory not a File.", "Compiler", JOptionPane.ERROR_MESSAGE);
				}
		}
		else if(ae.getSource()==btnCompile)
		{
			if(!txtBox1.getText().equals("")){
				Compiler3.path=txtBox1.getText();
				isDone=true;//one package selection is done
			}
		}
		
	}

}
