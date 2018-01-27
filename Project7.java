// Demonstrates JPanel, GridLayout and a few additional useful graphical features.
// adapted from an example by john ramirez (cs prof univ pgh)
import java.awt.*; //importing libraries (look up in more detail)
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Project7
{
	//JFrame window;  // the main window which contains everything
	Container content ;
	JButton[] digits = new JButton[12]; //for the numbers
	JButton[] ops = new JButton[4]; //for the operations
	JTextField expression; //for the expression that you type in
	JButton equals; //the equals button
	
	//JTextField is connected with the ActionEvent
	JTextField result; //the result of the expression entered
	
	public Project7()
	{
		JFrame window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		//get a reference to the JFrame object's content pane
		
		content.setLayout(new GridLayout(2,1)); // 2 row, 1 col
		ButtonListener listener = new ButtonListener();
		
		// top panel holds expression field, equals sign and result field  
		// [4+3/2-(5/3.5)+3]  =   [3.456]
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3)); // 1 row, 3 col
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");
		
		equals = new JButton("=");
		equals.setFont(new Font("verdana", Font.BOLD, 20 ));
		equals.addActionListener( listener ); 
		
		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(equals);
		topPanel.add(result);
						
		// bottom panel holds the digit buttons in the left sub panel and the operators in the right sub panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2)); // 1 row, 2 col
	
		JPanel  digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(4,3));	
		
		for (int i=0 ; i<10 ; i++ )
		{
			digits[i] = new JButton( ""+i );
			digitsPanel.add( digits[i] );
			digits[i].addActionListener( listener ); //connecting the button to the listener
		}
		digits[10] = new JButton( "C" );
		digitsPanel.add( digits[10] );
		digits[10].addActionListener( listener ); 

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		
	
		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(4,1));
		String[] opCodes = { "+", "-", "*", "/" };
		for (int i=0 ; i<4 ; i++ )
		{
			ops[i] = new JButton( opCodes[i] );
			opsPanel.add( ops[i] );
			ops[i].addActionListener( listener ); 
		}
		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );
		
		content.add( topPanel );
		content.add( bottomPanel );
	
		window.setSize( 640,480);
		window.setVisible( true );
	}
	// We are again using an inner class here so that we can access
	// components from within the listener.  Note the different ways
	// of getting the int counts into the String of the label
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component whichButton = (Component) e.getSource();
			//System.out.println("WhichButton ==> " + whichButton);
			//System.out.println("Equals ==> " + equals);

			//handle the digit buttons
			for (int i=0 ; i<10 ; i++ ){
				if (whichButton == digits[i]){
					//this allows the digit corresponding to the button to appear in the
					//expression field
					expression.setText( expression.getText() + i );
				}
			}
			
			if (whichButton == digits[10]){
			//clears everything in the expression field
				expression.setText("");
				result.setText("");
			}
			
			if (whichButton == digits[11]){
			//clears the last entry
				expression.setText(""+expression.getText().substring(0, expression.getText().length()-1));
			
			}
			//handle the ops buttons
			if (whichButton == ops[0]){
				expression.setText(expression.getText() + "+");
			}
			if (whichButton == ops[1]){
				expression.setText(expression.getText() + "-");
			}
			if (whichButton == ops[2]){
				expression.setText(expression.getText() + "*");
			}
			if (whichButton == ops[3]){
				expression.setText(expression.getText() + "/");
			}
			
			//handle the equals button ==> want the equals button to evaluate expression
			if (whichButton == equals){
				System.out.println("Executing equals button");

				ArrayList<String> operatorList = new ArrayList<String>();
				ArrayList<String> operandList = new ArrayList<String>();
				
				StringTokenizer st = new StringTokenizer(expression.getText(), "+-*/", true);	
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					if ("+-*/".contains(token))
						operatorList.add(token);
					else
						operandList.add(token);
				}
				
				
				//this means that the expression should be correct, given that the first
				//token given is not an operator
				
				System.out.println("OperatorList ==>" + operatorList);
				System.out.println("OperandList ==>" + operandList);
				if(operandList.size() == operatorList.size()+1){
					//initialize variable
					double ans = 0;
					
					System.out.println("Answer ==> " + ans);
					for(int i=0; i<operatorList.size(); i++){
					
						if(operatorList.get(i).equals("*") || operatorList.get(i).equals("/")){
							if(operatorList.get(i).equals("*")){
								ans = Double.parseDouble(operandList.get(i)) * Double.parseDouble(operandList.get(i+1));
							}
							else{
								ans = Double.parseDouble(operandList.get(i)) / Double.parseDouble(operandList.get(i+1));
							}
							operatorList.remove(i);
							operandList.remove(i+1);
							operandList.set(i, Double.toString(ans));
						}
						if(operatorList.get(i).equals("+") || operatorList.get(i).equals("/")){
							if(operatorList.get(i).equals("+")){
								ans = Double.parseDouble(operandList.get(i)) + Double.parseDouble(operandList.get(i+1));
							}
							else{
								ans = Double.parseDouble(operandList.get(i)) - Double.parseDouble(operandList.get(i+1));
							}
							operatorList.remove(i);
							operandList.remove(i+1);
							operandList.set(i, Double.toString(ans));
						}
						//have to first get the result from the operandList (which is 
						//a string array) and then convert that Int to a JTextField
						//not appearing in the result Jtextfield....
						result.setText(Double.toString(ans));
						System.out.println("Answer ==> " + ans);
					}
				}
				//write a class that specifically calculates the string
			}
		}
	}
	


	public static void main(String [] args)
	{
		new Project7();
	}
}
