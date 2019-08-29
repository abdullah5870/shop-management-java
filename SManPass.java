import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class SManPass extends Frame implements ActionListener{
	private Salesman frame;
	private String user;
	private Label userNameLabel,currentPassLabel,oldPass1Label,oldPass2Label;
	private TextField userNameTF,currentPassTF,oldPass1TF,oldPass2TF;
	private Button submit,reset,back;
	
	public SManPass(Salesman f,String user){
		setSize(500,500);
		setBackground(Color.lightGray);
		frame = f;
		this.user = user;
		userNameLabel = new Label("Username");
		currentPassLabel = new Label("Current password");
		oldPass1Label = new Label("New password");
		oldPass2Label = new Label("New password");
		
		userNameTF = new TextField(40);
		currentPassTF = new TextField(40);
		oldPass1TF = new TextField(40);
		oldPass2TF = new TextField(40);
		currentPassTF.setEchoChar('*');
		oldPass1TF.setEchoChar('*');
		oldPass2TF.setEchoChar('*');
		
		submit = new Button("Submit");
		reset = new Button("Reset");
		back = new Button("Back");
		
		add(userNameLabel);	add(currentPassLabel);	add(oldPass1Label);	add(oldPass2Label);
		add(userNameTF);	add(currentPassTF);	add(oldPass1TF);	add(oldPass2TF);
		add(submit);	add(reset);	add(back);
		
		submit.addActionListener(this);
		reset.addActionListener(this);
		back.addActionListener(this);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new FlowLayout());
	}
	public void paint(Graphics g){
		userNameLabel.setFont(new Font("",Font.BOLD,15));
		userNameLabel.setLocation((getWidth()/2)-120,(getHeight()/3));
		currentPassLabel.setFont(new Font("",Font.BOLD,15));
		currentPassLabel.setLocation((getWidth()/2)-120,(getHeight()/3)+40);
		oldPass1Label.setFont(new Font("",Font.BOLD,15));
		oldPass1Label.setLocation((getWidth()/2)-112,getHeight()/3+80);
		oldPass2Label.setFont(new Font("",Font.BOLD,15));
		oldPass2Label.setLocation((getWidth()/2)-112,getHeight()/3+120);
		userNameTF.setLocation((getWidth()/2)-12,getHeight()/3);
		currentPassTF.setLocation((getWidth()/2)-12,getHeight()/3+40);
		oldPass1TF.setLocation((getWidth()/2)-12,getHeight()/3+80);
		oldPass2TF.setLocation((getWidth()/2)-12,getHeight()/3+120);
		submit.setSize(80,30);
		submit.setLocation(getWidth()/2,getHeight()/3+180);
		reset.setSize(80,30);
		reset.setLocation((getWidth()/2)+100,getHeight()/3+180);
		back.setSize(80,30);
		back.setLocation((getWidth()/2)+200,getHeight()/3+180);
	}
	public void actionPerformed(ActionEvent a){
		String pressed = a.getActionCommand();
		String uName,cPass,oPass1,oPass2;
		uName = userNameTF.getText();
		cPass = currentPassTF.getText();
		oPass1 = oldPass1TF.getText();
		oPass2 = oldPass2TF.getText();
		if(pressed.equals("Reset")){
			userNameTF.setText("");
			currentPassTF.setText("");
			oldPass1TF.setText("");
			oldPass2TF.setText("");
		}
		else if(pressed.equals("Back")){
			this.setVisible(false);
			frame.setVisible(true);
		}
		else{
			if(uName.equals("")){
				JOptionPane.showMessageDialog(this, "Username should not be blank");
			}
			if(cPass.equals("") || oPass1.equals("") || oPass2.equals("")){
				JOptionPane.showMessageDialog(this, "Password should not be blank");
			}
			else if(!user.equals(uName)){
				JOptionPane.showMessageDialog(this, "Wrong username");
			}
			else if(!oPass1.equals(oPass2)){
				JOptionPane.showMessageDialog(this, "Password does not match");
			}
			else{
				try{
					sqlOperation(uName,cPass,oPass1,oPass2);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error!");
				}
			}
		}
	}
	public void sqlOperation(String u,String p,String p1,String p2) throws SQLException{
		boolean found = false;
		DataAccess da = new DataAccess();
		ResultSet rs = null;
		String query="select * from salesman";
		rs=da.getData(query);
		while(rs.next()){
			String pass = Integer.toString(rs.getInt("password"));
			String name = rs.getString("username");
			if(u.equals(name) && pass.equals(p)){
				String q = "update salesman set password="+p1+" where username='"+name+"'";
				int c=da.updateDB(q);
				System.out.println(q);
				JOptionPane.showMessageDialog(this, "Password changed");
				userNameTF.setText("");
				currentPassTF.setText("");
				oldPass1TF.setText("");
				oldPass2TF.setText("");
				found = true;
				break;
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "Incorrect password");
		}
		da.close();
	}
}