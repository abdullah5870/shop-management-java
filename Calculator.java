import java.awt.*;
import java.awt.event.*;

class Calculator extends Frame implements ActionListener{
	private String t = "";
	private Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bAdd,bSub,bMul,bDiv,bSum,bPoint,bReset,bMod,bBack,close;
	private Label label;
	private static TextField textField;
	private String button;
	private double input=0 ;
	private double firstNumber, secondNumber;
	private double result;
	private String operation;
	public Calculator(){
		setSize(380,620);
		setBackground(Color.GRAY);
		setLocationRelativeTo(null);	//http://stackoverflow.com/questions/9543320/how-to-position-the-form-in-the-center-screen
		
		textField = new TextField("");
		add(textField);
		
		b0 = new Button("0");
		add(b0);
		b1 = new Button("1");
		add(b1);
		b2 = new Button("2");
		add(b2);
		b3 = new Button("3");
		add(b3);
		b4 = new Button("4");
		add(b4);
		b5 = new Button("5");
		add(b5);
		b6 = new Button("6");
		add(b6);
		b7 = new Button("7");
		add(b7);
		b8 = new Button("8");
		add(b8);
		b9 = new Button("9");
		add(b9);
		bDiv = new Button("/");
		add(bDiv);
		bMul = new Button("*");
		add(bMul);
		bSub = new Button("-");
		add(bSub);
		bAdd = new Button("+");
		add(bAdd);
		bSum = new Button("=");
		add(bSum);
		bReset = new Button("C");
		add(bReset);
		bPoint = new Button(".");
		add(bPoint);
		bMod = new Button("%");
		add(bMod);
		bBack = new Button("X");
		add(bBack);
		close = new Button("Close");
		add(close);
		setResizable(false);
	
		setLayout(new FlowLayout());
		
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		bDiv.addActionListener(this);
		bMul.addActionListener(this);
		bSub.addActionListener(this);
		bAdd.addActionListener(this);
		bSum.addActionListener(this);
		bBack.addActionListener(this);
		bReset.addActionListener(this);
		bMod.addActionListener(this);
		bPoint.addActionListener(this);
		close.addActionListener(this);
	}
	public void paint(Graphics g){
		
		textField.setLocation(30,50);
		textField.setSize(320,50);
		textField.setFont(new Font("",Font.BOLD,35));
		
		b1.setLocation(30,150);
		b1.setSize(80,80);
		b1.setFont(new Font("",Font.BOLD,25));
		
		b2.setLocation(110,150);
		b2.setSize(80,80);
		b2.setFont(new Font("",Font.BOLD,25));
		
		b3.setLocation(190,150);
		b3.setSize(80,80);
		b3.setFont(new Font("",Font.BOLD,25));
		
		bBack.setLocation(270,150);
		bBack.setSize(80,80);
		bBack.setFont(new Font("",Font.BOLD,25));
		
		b4.setLocation(30,230);
		b4.setSize(80,80);
		b4.setFont(new Font("",Font.BOLD,25));
		
		b5.setLocation(110,230);
		b5.setSize(80,80);
		b5.setFont(new Font("",Font.BOLD,25));
		
		b6.setLocation(190,230);
		b6.setSize(80,80);
		b6.setFont(new Font("",Font.BOLD,25));
		
		bAdd.setLocation(270,230);
		bAdd.setSize(80,80);
		bAdd.setFont(new Font("",Font.BOLD,25));
		
		b7.setLocation(30,310);
		b7.setSize(80,80);
		b7.setFont(new Font("",Font.BOLD,25));
		
		b8.setLocation(110,310);
		b8.setSize(80,80);
		b8.setFont(new Font("",Font.BOLD,25));
		
		b9.setLocation(190,310);
		b9.setSize(80,80);
		b9.setFont(new Font("",Font.BOLD,25));
		
		bSub.setLocation(270,310);
		bSub.setSize(80,80);
		bSub.setFont(new Font("",Font.BOLD,25));
		
		b0.setLocation(30,390);
		b0.setSize(80,80);
		b0.setFont(new Font("",Font.BOLD,25));
	
		bMod.setLocation(110,390);
		bMod.setSize(80,80);
		bMod.setFont(new Font("",Font.BOLD,25));
		
		bPoint.setLocation(190,390);
		bPoint.setSize(80,80);
		bPoint.setFont(new Font("",Font.BOLD,25));
		
		bMul.setLocation(270,390);
		bMul.setSize(80,80);
		bMul.setFont(new Font("",Font.BOLD,25));

		
		bReset.setLocation(30,470);
		bReset.setSize(80,80);
		bReset.setFont(new Font("",Font.BOLD,25));
		
		bSum.setLocation(110,470);
		bSum.setSize(160,80);
		bSum.setFont(new Font("",Font.BOLD,25));
		
		bDiv.setLocation(270,470);
		bDiv.setSize(80,80);
		bDiv.setFont(new Font("",Font.BOLD,25));
		
		close.setLocation(300,570);
		
	}
	public void actionPerformed(ActionEvent a){
		button = a.getActionCommand();
		if(button.equals("Close")){
			firstNumber = 0;
			secondNumber = 0;
			t = "";
			this.textField.setText(t);
			operation = "";
			this.setVisible(false);
		}
		if(button.equals("+")){
			firstNumber = Double.parseDouble(textField.getText());
			operation= "+";
			t = "";
			textField.setText("");
		}
		else if(button.equals("X")){
			try{
				t = "";
				textField.setText(t);
			}
			catch(Exception e){
			}
		}
		else if(button.equals("C")){
			try{
				t = t.substring(0,t.length()-1);
				textField.setText(t);
			}
			catch(Exception e){
			}
		}
		else if(button.equals("%")){
			firstNumber = Double.parseDouble(textField.getText());
			operation= "%";
			t = "";
			textField.setText("");
		}
		else if(button.equals("-")){
			firstNumber = Double.parseDouble(textField.getText());
			operation= "-";
			t = "";
			textField.setText("");
		}
		else if(button.equals("*")){
			firstNumber = Double.parseDouble(textField.getText());
			operation= "*";
			t = "";
			textField.setText("");
		}
		else if(button.equals("/")){
			firstNumber = Double.parseDouble(textField.getText());
			operation= "/";
			t = "";
			textField.setText("");
		}
		else if(button.equals("=")){
			secondNumber = Double.parseDouble(textField.getText());
			t = "";
			if(operation.equals("+")){
				result = firstNumber+secondNumber;
				textField.setText(Double.toString(result));
			}
			else if(operation.equals("-")){
				result = firstNumber-secondNumber;
				textField.setText(Double.toString(result));
			}
			else if(operation.equals("*")){
				result = firstNumber*secondNumber;
				textField.setText(Double.toString(result));
			}
			else if(operation.equals("/")){
				result = firstNumber/secondNumber;
				textField.setText(Double.toString(result));
			}
			else if(operation.equals("%")){
				result = (firstNumber*secondNumber)/100;
				textField.setText(Double.toString(result));
			}
		}
		else{
			if(!button.equals("Close")){
				t = t + button;
				textField.setText(t);
			}
		}
	}
}
