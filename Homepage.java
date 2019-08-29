import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
class WindowKeeper extends WindowAdapter{
	public void windowClosing(WindowEvent w){
		System.exit(1);
	}
}
class MyFrame extends Frame implements ActionListener,ItemListener{
	private int frameHeight,frameWidth;
	private Label welcomeMessage,loginLabel,userLabel,passLabel;
	private Button login,reset,forgotPass;
	private TextField userTF,passTF;
	private Choice userChoice;
	public MyFrame(){
		super("Demo super shop");
		setSize(600,500);
		setBackground(Color.lightGray);
		setVisible(true);
		
		welcomeMessage = new Label("Welcome to demo super shop");
		loginLabel = new Label("Login:");
		userLabel = new Label("User Name: ");
		passLabel = new Label("Password: ");
		add(welcomeMessage);
		userChoice = new Choice();
		add(userChoice);
		userChoice.add("Admin");
		userChoice.add("Salesman");
		
		add(loginLabel);
		add(userLabel);	 	add(passLabel);
		userTF = new TextField(25);
		passTF = new TextField(25);
		passTF.setEchoChar('*');
		add(userTF);	add(passTF);
		login = new Button("Login");
		reset = new Button("Reset");
		forgotPass = new Button("Forgot password");
		add(login);	add(reset);	add(forgotPass);
		forgotPass.setVisible(false);
		
		
		userChoice.addItemListener(this);
		forgotPass.addActionListener(this);
		login.addActionListener(this);
		reset.addActionListener(this);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new FlowLayout());
	}
	public void paint(Graphics g){
		frameHeight = getHeight();
		frameWidth = getWidth();
		welcomeMessage.setLocation((frameWidth/2)-180,frameHeight/10);
		welcomeMessage.setFont(new Font("MONOSPACED",Font.BOLD,25));
		
		loginLabel.setLocation((frameWidth/2)-70,frameHeight/3);
		loginLabel.setFont(new Font("",Font.BOLD,20));
		userChoice.setFont(new Font("",Font.BOLD,20));
		userChoice.setLocation((frameWidth/2)+10,frameHeight/3+6);
		
		userLabel.setFont(new Font("",Font.BOLD,15));
		userLabel.setLocation((frameWidth/2)-98,frameHeight/2+40);
		passLabel.setFont(new Font("",Font.BOLD,15));
		passLabel.setLocation((frameWidth/2)-90,frameHeight/2+80);
		userTF.setLocation((frameWidth/2)+10,frameHeight/2+40);
		passTF.setLocation((frameWidth/2)+10,frameHeight/2+80);
		login.setSize(80,30);
		login.setLocation(frameWidth/2+30,frameHeight/2+120);
		reset.setSize(80,30);
		reset.setLocation((frameWidth/2)+130,frameHeight/2+120);
		forgotPass.setSize(110,30);
		forgotPass.setLocation(frameWidth/2+230,frameHeight/2+120);
	}
	public void itemStateChanged(ItemEvent ie){
		if(userChoice.getSelectedItem().equals("Salesman")){
			forgotPass.setVisible(true);
		}
		else if(userChoice.getSelectedItem().equals("Admin")){
			forgotPass.setVisible(false);
		}
	}
	public void actionPerformed(ActionEvent a){
		String button = a.getActionCommand();
		String userName,password;
		
		userName = userTF.getText();
		password = passTF.getText();
		if(button.equals("Forgot password")){
			JOptionPane.showMessageDialog(this, "Contact with admin");
		}
		if(button.equals("Login")){
			if(userName.equals("") || password.equals("")){
				JOptionPane.showMessageDialog(this, "Username or password should not be blank");
			}
			else{
				try{
					login(userName,password);
				}
				catch(Exception e){
				}
			}
		}
		else{
			userTF.setText("");
			passTF.setText("");
		}
	}
	public void login(String userName,String password) throws SQLException{
		boolean found = false;
		DataAccess da = new DataAccess();
		ResultSet rs = null;
		String q;
		String selected = userChoice.getItem(userChoice.getSelectedIndex());
		q="select * from "+selected;
		rs = da.getData(q);
		while(rs.next()){
			String user = rs.getString("username");
			String pass = rs.getString("password");
			if(user.equals(userName) && pass.equals(password)){
				userTF.setText("");
				passTF.setText("");
				WindowKeeper adminWK = new WindowKeeper();
				setVisible(false);
				if(selected.equals("Admin")){
					Admin admin = new Admin(this,frameWidth,frameWidth);
					admin.addWindowListener(adminWK);
				}
				else{
					Salesman salesman = new Salesman(this,userName,800,800);
					salesman.addWindowListener(adminWK);
				}
				found = true;
				break;
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "Incorrect username or password");
		}
	}
}

public class Homepage{
	public static void main(String [] args){
		MyFrame frame = new MyFrame();
		WindowKeeper wk = new WindowKeeper();
		frame.addWindowListener(wk);
	}
}