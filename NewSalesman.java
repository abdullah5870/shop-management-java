import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class NewSalesman extends Frame implements ActionListener{
	private SManTable smt;
	private Admin frame;
	private Label nameLabel,pass1Label,pass2Label;
	private TextField nameTF,pass1TF,pass2TF;
	private Button submit,reset,back,delete;
	
	public NewSalesman(Admin a,SManTable s){
		setSize(500,500);
		setBackground(Color.lightGray);
		frame = a;
		smt = s;
		nameLabel = new Label("Username");
		pass1Label = new Label("Password");
		pass2Label = new Label("Password");
		
		nameTF = new TextField(40);
		pass1TF = new TextField(40);
		pass2TF = new TextField(40);
		pass1TF.setEchoChar('*');
		pass2TF.setEchoChar('*');
		
		delete = new Button("Delete");
		submit = new Button("Submit");
		reset = new Button("Reset");
		back = new Button("Back");
		
		add(nameLabel);	add(pass1Label);	add(pass2Label);
		add(nameTF);	add(pass1TF);	add(pass2TF);
		add(submit);	add(reset);	add(back);	add(delete);
		
		delete.addActionListener(this);
		submit.addActionListener(this);
		reset.addActionListener(this);
		back.addActionListener(this);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new FlowLayout());
	}
	public void paint(Graphics g){
		nameLabel.setFont(new Font("",Font.BOLD,15));
		nameLabel.setLocation((getWidth()/2)-120,(getHeight()/3)+40);
		pass1Label.setFont(new Font("",Font.BOLD,15));
		pass1Label.setLocation((getWidth()/2)-112,getHeight()/3+80);
		pass2Label.setFont(new Font("",Font.BOLD,15));
		pass2Label.setLocation((getWidth()/2)-112,getHeight()/3+120);
		nameTF.setLocation((getWidth()/2)-12,getHeight()/3+40);
		pass1TF.setLocation((getWidth()/2)-12,getHeight()/3+80);
		pass2TF.setLocation((getWidth()/2)-12,getHeight()/3+120);
		
		delete.setSize(80,30);
		delete.setLocation(getWidth()/2-100,getHeight()/3+180);
		submit.setSize(80,30);
		submit.setLocation(getWidth()/2,getHeight()/3+180);
		reset.setSize(80,30);
		reset.setLocation((getWidth()/2)+100,getHeight()/3+180);
		back.setSize(80,30);
		back.setLocation((getWidth()/2)+200,getHeight()/3+180);
	}
	public void actionPerformed(ActionEvent a){
		String pressed = a.getActionCommand();
		String uName,pass1,pass2;
		uName = nameTF.getText();
		pass1 = pass1TF.getText();
		pass2 = pass2TF.getText();
		if(pressed.equals("Reset")){
			nameTF.setText("");
			pass1TF.setText("");
			pass2TF.setText("");
		}
		else if(pressed.equals("Back")){
			this.setVisible(false);
			frame.setVisible(true);
			smt.setVisible(false);
		}
		else if(pressed.equals("Delete")){
			if(uName.equals("")){
				JOptionPane.showMessageDialog(this, "Username should not be blank");
			}
			else{
				try{
					deleteOperation(uName);
					smt.removeFromTable(uName);
					smt.setVisible(false);
					smt = new SManTable();
					smt.setVisible(true);
				}
				catch(Exception e){
				}
			}
				
		}
		else{
			if(uName.equals("") || pass1.equals("") || pass2.equals("")){
				JOptionPane.showMessageDialog(this, "Username or password should not be blank");
			}
			else if(!pass1.equals(pass2)){
				JOptionPane.showMessageDialog(this, "Password does not match");
			}
			else{
				try{
					sqlOperation(uName,pass1,pass2);
					smt.addToTable(uName);
					smt.setVisible(false);
					smt = new SManTable();
					smt.setVisible(true);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error!");
				}
			}
		}
	}
	public void sqlOperation(String u,String p1,String p2) throws SQLException{
		boolean found = false;
		DataAccess da = new DataAccess();
		ResultSet rs = null;
		String query="select * from salesman";
		rs=da.getData(query);
		while(rs.next()){
			int p = rs.getInt("password");
			String n = rs.getString("username");
			if(u.equals(n)){
				JOptionPane.showMessageDialog(this, "Username already taken");
				found = true;
				break;
			}
		}
		if(!found){
			String q="insert into salesman values ('"+u+"',"+p1+")";
			nameTF.setText("");
			pass1TF.setText("");
			pass2TF.setText("");
			int c=da.updateDB(q);
		}
		da.close();
	}
	public void deleteOperation(String user) throws SQLException{
		boolean found = false;
		DataAccess da = new DataAccess();
		ResultSet rs = null;
		String query="select * from salesman";
		rs=da.getData(query);
		while(rs.next()){
			String name = rs.getString("username");
			if(user.equals(name)){
				int c = da.updateDB("delete from salesman where username='"+user+"'");
				found = true;
				break;
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "Wrong username");
		}
		da.close();
	}
}