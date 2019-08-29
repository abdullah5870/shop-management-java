import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

class Admin extends Frame implements ActionListener,ItemListener{
	String currentName,newName;
	boolean searched ;
	private SearchTable st;
	private TableSensor ts;
	private SManTable smt;
	private JScrollPane jsp1;
	private JTable table;
	private DefaultTableModel model;
	private NewSalesman newFrame;
	private int frameHeight,frameWidth;
	private Label adminLabel;
	private Label nameLabel,priceLabel,quantityLabel;
	private Button submit,reset,logout,updateSearch,newSMan;
	private TextField nameTF,priceTF,quantityTF;
	private Choice optionChoice;
	private Panel addPanel,updatePanel,searchPanel,searchTPanel,showPanel;
	private String selected,pressed;
	
	private MyFrame frame;
	public Admin(MyFrame f,int width, int height){
		super ("Admin");
		frame = f;
		setSize(width, height);
		setSize(800, 800);
		setBackground(Color.lightGray);
		st = new SearchTable();
		ts = new TableSensor();
		smt = new SManTable();
		newFrame = new NewSalesman(this,smt);
		WindowKeeper wk = new WindowKeeper();
		newFrame.addWindowListener(wk);
		
		adminLabel = new Label("Admin");
		add(adminLabel);
		
		optionChoice = new Choice();
		add(optionChoice);
		optionChoice.add("Add item");
		optionChoice.add("Update");
		optionChoice.add("Show all");
		optionChoice.add("Search item");

		addPanel = new Panel();
		updatePanel = new Panel();
		searchPanel = new Panel();
		searchTPanel = new Panel();
		showPanel = new Panel();
		
		updateSearch = new Button("Search");
		nameLabel = new Label("Item name : ");
		priceLabel = new Label("Item price : ");
		quantityLabel = new Label("Item quantity : ");
		nameTF = new TextField(30);
		priceTF = new TextField(30);
		quantityTF = new TextField(30);
		addPanel.add(nameLabel);
		addPanel.add(priceLabel);
		addPanel.add(quantityLabel);
		addPanel.add(nameTF);
		addPanel.add(priceTF);
		addPanel.add(quantityTF);
		model = new DefaultTableModel();
		table = new JTable(model);
		model = (DefaultTableModel)table.getModel();
		
		add(addPanel);	add(updatePanel);	add(searchPanel);	add(searchTPanel);	add(showPanel);
		
		submit = new Button("Submit");
		reset = new Button("Reset");
		logout = new Button("Logout");
		newSMan = new Button("Manage salesman");
		add(submit);	add(reset);	add(logout);	add(newSMan);	add(updateSearch);
		
		updateSearch.setVisible(false);
		
		submit.addActionListener(this);
		reset.addActionListener(this);
		logout.addActionListener(this);
		newSMan.addActionListener(this);
		updateSearch.addActionListener(this);
		
		optionChoice.addItemListener(this);
		
		addPanel.setVisible(true);	updatePanel.setVisible(false);	searchPanel.setVisible(false);	searchTPanel.setVisible(false);	showPanel.setVisible(false);
		setLayout(new FlowLayout());
		newFrame.setVisible(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	public void addToPanel(Panel p){
		p.add(nameLabel);
		p.add(priceLabel);
		p.add(quantityLabel);
		p.add(nameTF);		p.add(priceTF);		p.add(quantityTF);
		nameTF.setText("");
		priceTF.setText("");
		quantityTF.setText("");
	}
	public void paint(Graphics g){
		int addHeight,updateHeight,showHeight,searchHeight;
		frameHeight = getHeight();
		frameWidth = getWidth();
		
		addHeight = addPanel.getHeight();
		updateHeight = updatePanel.getHeight();
		searchHeight = searchPanel.getHeight();
		showHeight = showPanel.getHeight();
		
		adminLabel.setFont(new Font("",Font.BOLD,25));
		adminLabel.setLocation(frameWidth/2-45,frameHeight/10);
		optionChoice.setFont(new Font("",Font.BOLD,20));
		optionChoice.setSize(140,30);
		optionChoice.setLocation(frameWidth/2-70,frameHeight/10+60);
		
		addPanel.setSize(frameWidth/2,frameHeight*12/30);
		//addPanel.setBackground(Color.red);
		addPanel.setLocation(frameWidth/4,frameHeight/3);
		updatePanel.setSize(frameWidth/2,frameHeight*12/30);
		//updatePanel.setBackground(Color.yellow);
		updatePanel.setLocation(frameWidth/4,frameHeight/3);
		searchPanel.setSize(frameWidth/2,frameHeight*12/30);
		//searchPanel.setBackground(Color.green);
		searchPanel.setLocation(frameWidth/4,frameHeight/3);
		searchTPanel.setSize(frameWidth/4-20,frameHeight*2/3);
		//searchTPanel.setBackground(Color.orange);
		searchTPanel.setLocation(10,frameHeight/4);
		showPanel.setSize(frameWidth/2,frameHeight/2+50);
		//showPanel.setBackground(Color.blue);
		showPanel.setLocation(frameWidth/4,frameHeight/3-60);
		
		nameLabel.setFont(new Font("",Font.BOLD,15));
		nameLabel.setLocation(frameWidth/4-150,addHeight/8+10);
		priceLabel.setFont(new Font("",Font.BOLD,15));
		priceLabel.setLocation(frameWidth/4-150,addHeight*2/8+20);
		quantityLabel.setFont(new Font("",Font.BOLD,15));
		quantityLabel.setLocation(frameWidth/4-170,addHeight*3/8+30);
		
		nameTF.setLocation(frameWidth/4-50,addHeight/8+10);
		priceTF.setLocation(frameWidth/4-50,addHeight*2/8+20);
		quantityTF.setLocation(frameWidth/4-50,addHeight*3/8+30);
		
		updateSearch.setSize(80,30);
		updateSearch.setLocation(frameWidth/2-50,frameHeight*3/4+30);
		submit.setSize(80,30);
		submit.setLocation(frameWidth/2-130,frameHeight*3/4+80);
		reset.setSize(80,30);
		reset.setLocation(frameWidth/2-40,frameHeight*3/4+80);
		logout.setSize(80,30);
		logout.setLocation(frameWidth/2+50,frameHeight*3/4+80);
		newSMan.setSize(120,30);
		newSMan.setLocation(frameWidth/2+170,frameHeight/10+60);
	}
	public void itemStateChanged(ItemEvent ie){
		if(optionChoice.getSelectedItem().equals("Add item")){
			addToPanel(addPanel);
			addPanel.setVisible(true);
			updatePanel.setVisible(false);
			searchPanel.setVisible(false);
			showPanel.setVisible(false);
			st.setVisible(false);
			ts.setVisible(false);
			submit.setVisible(true);	reset.setVisible(true);	updateSearch.setVisible(false);
		}
		else if(optionChoice.getSelectedItem().equals("Update")){
			currentName = "";
			searched = false;
			addToPanel(updatePanel);
			addPanel.setVisible(false);
			updatePanel.setVisible(true);
			searchPanel.setVisible(false);
			showPanel.setVisible(false);
			nameLabel.setVisible(true);
			st.setVisible(true);
			ts.setVisible(false);
			submit.setVisible(true);	reset.setVisible(true);	updateSearch.setVisible(true);
		}
		else if(optionChoice.getSelectedItem().equals("Search item")){
			
			addToPanel(searchPanel);
			addPanel.setVisible(false);
			updatePanel.setVisible(false);
			searchPanel.setVisible(true);
			showPanel.setVisible(false);
			st.setVisible(true);
			ts.setVisible(false);
			submit.setVisible(true);	reset.setVisible(true);	updateSearch.setVisible(false);
		}
		else if(optionChoice.getSelectedItem().equals("Show all")){
			addPanel.setVisible(false);
			updatePanel.setVisible(false);
			searchPanel.setVisible(false);
			showPanel.setVisible(true);
			st.setVisible(false);
			ts.setVisible(true);
			submit.setVisible(false);	reset.setVisible(false);	updateSearch.setVisible(false);
		}
	}
	public void actionPerformed(ActionEvent a){
		pressed = a.getActionCommand();
		selected = optionChoice.getSelectedItem();
		if(selected.equals("Add item")){
			if(pressed.equals("Submit")){
				try{
					addOperation();
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error");
				}
			}
		}
		else if(selected.equals("Update")){
			if(pressed.equals("Search")){
				try{
					searchOperation();
					currentName = nameTF.getText();
					quantityTF.setText("");
					searched = true;
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error");
				}
			}
			if(pressed.equals("Submit")){
				try{
					if(!searched){
						//System.out.println("not searched");
						updateOperation();
					}
					else if(searched){
						//System.out.println("searched");
						newName = nameTF.getText();
						updateOperation(currentName,newName);
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error");
				}
			}
		}
		else if(selected.equals("Search item")){
			if(pressed.equals("Submit")){
				try{
					searchOperation();
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, "Database error");
				}
			}
		}
		if(pressed.equals("Manage salesman")){
			smt.setVisible(true);
			newFrame.setVisible(true);
			this.setVisible(false);
			st.setVisible(false);
			ts.setVisible(false);
		}
		if(pressed.equals("Reset")){
			nameTF.setText(" ");
			priceTF.setText(" ");
			quantityTF.setText(" ");
		}
		if(pressed.equals("Logout")){
			setVisible(false);
			frame.setVisible(true);
			st.setVisible(false);
			ts.setVisible(false);
			smt.setVisible(false);
		}
	}
	public void addOperation() throws SQLException{
		boolean found = false;
		String itemName = nameTF.getText().toUpperCase();
		String itemPrice = priceTF.getText();
		String itemQuantity = quantityTF.getText();
		if(itemName.equals("") || itemName.equals(" ") || itemPrice.equals("") || itemPrice.equals(" ") || itemQuantity.equals("") || itemQuantity.equals(" ")){
			JOptionPane.showMessageDialog(this, "Item name,price and quantity required");
		}
		else{
			int price = Integer.parseInt(itemPrice);
			int quantity = Integer.parseInt(itemQuantity);
			DataAccess da=new DataAccess();
			ResultSet rs=null;
			String query;
			query = "select * from items";
			rs=da.getData(query); 
			while(rs.next()){
				String name = rs.getString("uppername");
				if(itemName.equals(name)){
					nameTF.setText(" ");	priceTF.setText(" ");	quantityTF.setText(" ");
					JOptionPane.showMessageDialog(this, "This product is available");
					found = true;
				}
			}
			if(!found){
				query="insert into items values ('"+nameTF.getText()+"','"+itemName+"',"+itemPrice+","+itemQuantity+")";
				try{
					ts.addToTable(nameTF.getText(),price,quantity);
					st.addToTable(nameTF.getText());
				}
				catch(Exception e){
					
				}
				int c=da.updateDB(query);
				nameTF.setText(" ");	priceTF.setText(" ");	quantityTF.setText(" ");
				da.close();
			}
		}
	}
	public void updateOperation() throws SQLException{
		boolean found = false;
		String q;
		String itemName = nameTF.getText().toUpperCase();
		String itemPrice = priceTF.getText();
		String itemQuantity = quantityTF.getText();
		int price,quantity;
		if(itemName.equals("") || itemName.equals(" ")){
			JOptionPane.showMessageDialog(this, "Item name required");
		}
		if((itemPrice.equals("") || itemPrice.equals(" "))  && (itemQuantity.equals("") || itemQuantity.equals(" "))){
			JOptionPane.showMessageDialog(this, "price or quantity field equired");
			itemPrice = "0";
			itemQuantity = "0";
		}
		if(itemPrice.equals("") || itemPrice.equals(" ")){
			itemPrice = "0";
		}
		else if(itemQuantity.equals("") || itemQuantity.equals(" ")){
			itemQuantity = "0";
		}
		//System.out.println(itemPrice);
		price = Integer.parseInt(itemPrice);
		quantity = Integer.parseInt(itemQuantity);
		DataAccess da=new DataAccess();
		ResultSet rs=null;
		String query;
		query = "select * from items";
		rs=da.getData(query); 
		while(rs.next()){
			String name = rs.getString("uppername");
			int qnt = rs.getInt("quantity");
			if(itemName.equals(name))
			{
				if(price<0 || quantity<0){
					JOptionPane.showMessageDialog(this, "price or quantity should not be negative");
				}
				else{
					if(price != 0){
						q = "update items set price="+itemPrice+" , quantity=quantity+"+itemQuantity+" where uppername='"+itemName+"'";
						System.out.println("price not zero");
						ts.updateTable(itemName,price,quantity+qnt);
					}
					else{
						q = "update items set quantity=quantity+"+itemQuantity+" where uppername='"+itemName+"'";
						System.out.println("price not zero");
						ts.updateTable(itemName,quantity+qnt);
					}
					int c=da.updateDB(q);
					nameTF.setText("");
					priceTF.setText("");
					quantityTF.setText("");
					found = true;
					break;
				}
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "item not found");
		}
		da.close();
	}
	
	
	public void updateOperation(String currentName,String newName) throws SQLException{
		//System.out.println("Checked");
		boolean found = false;
		String q;
		String itemName = currentName.toUpperCase();
		String itemPrice = priceTF.getText();
		String itemQuantity = quantityTF.getText();
		int price,quantity;
		if(itemName.equals("") || itemName.equals(" ")){
			JOptionPane.showMessageDialog(this, "Item name required");
		}
		if((itemPrice.equals("") || itemPrice.equals(" "))  && (itemQuantity.equals("") || itemQuantity.equals(" "))){
			JOptionPane.showMessageDialog(this, "price or quantity field equired");
			itemPrice = "0";
			itemQuantity = "0";
		}
		if(itemPrice.equals("") || itemPrice.equals(" ")){
			itemPrice = "0";
		}
		else if(itemQuantity.equals("") || itemQuantity.equals(" ")){
			itemQuantity = "0";
		}
		//System.out.println(itemPrice);
		price = Integer.parseInt(itemPrice);
		quantity = Integer.parseInt(itemQuantity);
		DataAccess da=new DataAccess();
		ResultSet rs=null;
		String query;
		query = "select * from items";
		rs=da.getData(query); 
		while(rs.next()){
			String name = rs.getString("uppername");
			int qnt = rs.getInt("quantity");
			if(itemName.equals(name))
			{
				if(availableName(newName)){
					JOptionPane.showMessageDialog(this, "This item already exist");
				}
				else{
					
					if(price<0 || quantity<0){
						JOptionPane.showMessageDialog(this, "price or quantity should not be negative");
					}
					else{
						if(price != 0){
							q = "update items set name='"+newName+"' ,uppername='"+newName.toUpperCase()+"',price="+itemPrice+" , quantity=quantity+"+itemQuantity+" where uppername='"+itemName+"'";
							//System.out.println(q);
							ts.updateTable(currentName,newName,price,quantity+qnt);
							st.updateTable(currentName,newName);
						}
						else{
							q = "update items set name='"+newName+"' ,uppername='"+newName.toUpperCase()+"',quantity=quantity+"+itemQuantity+" where uppername='"+itemName+"'";
							//System.out.println(q);
							ts.updateTable(currentName,newName,quantity+qnt);
							st.updateTable(currentName,newName);
						}
						int c=da.updateDB(q);
						nameTF.setText("");
						priceTF.setText("");
						quantityTF.setText("");
						found = true;
						break;
					}
				}
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "item not found");
		}
		da.close();
	}
	
	public void searchOperation() throws SQLException{
		String query;
		boolean found = false;
		String itemName = nameTF.getText().toUpperCase();
		String itemPrice = priceTF.getText();
		String itemQuantity = quantityTF.getText();
		DataAccess da=new DataAccess();
		ResultSet rs=null;
		query = "select * from items";
		rs=da.getData(query);
		while(rs.next()){
			String name = rs.getString("uppername");
			if(itemName.equals(name)){
				String price = Integer.toString(rs.getInt("price"));
				String quantity = Integer.toString(rs.getInt("quantity"));
				priceTF.setText(price);	quantityTF.setText(quantity);
				found = true;
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(this, "item not found");
			nameTF.setText("");	priceTF.setText("");	quantityTF.setText("");
		}
		da.close();
	}
	
	public boolean availableName(String checkName) throws SQLException{
		String query;
		boolean found = false;
		DataAccess da=new DataAccess();
		ResultSet rs=null;
		query = "select * from items";
		rs=da.getData(query);
		while(rs.next()){
			String name = rs.getString("uppername");
			if(checkName.equals(name)){
				return true;
			}
		}
		da.close();
		return false;
	}
		
}