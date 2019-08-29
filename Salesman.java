import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

class Item{
	public String itemName;
	public int itemPrice;
	public int itemQuantity;
	
	public Item(String name, int price, int quantity){
		itemName = name;
		itemPrice = price;
		itemQuantity = quantity;
	}
}

class Salesman extends Frame implements ActionListener{
	private SearchTable st;
	private Calculator cal;
	private MyFrame frame;
	private SManPass sManPass;
	private DefaultTableModel model;
	private JTable table;
	private String user;
	
	private ArrayList <Item>ar = new ArrayList<Item>();
	private static int totalPrice = 0;
	private Panel topPanel,tablePanel,bottomPanel;
	private int frameWidth, frameHeight;
	private Label salesmanLabel,nameLabel,quantityLabel,netTotal;
	private TextField itemTF,quantityTF,priceTF;
	private Button addItem,delete,print,logout,calculator,editPass;
	public Salesman(MyFrame f,String user,int width, int height){
		super("Salesman");
		setSize(width,height);
		setBackground(Color.lightGray);
		this.user = user;
		frame = f;
		cal = new Calculator();
		st = new SearchTable();
		sManPass = new SManPass(this,user);
		st.setVisible(true);
		topPanel = new Panel();
		tablePanel = new Panel();
		bottomPanel = new Panel();
		salesmanLabel = new Label("Salesman");
		nameLabel = new Label("Name : ");
		quantityLabel = new Label("Quantity : ");
		netTotal = new Label("Total price : ");
		itemTF = new TextField(40);
		quantityTF = new TextField(10);
		priceTF = new TextField(15);
		
		calculator = new Button("Calculator");
		editPass = new Button("Edit password");
		addItem = new Button("Add");
		delete = new Button("Delete");
		print = new Button("Print");
		logout = new Button("Logout");
		
		model = new DefaultTableModel();
		table = new JTable(model);
		
		add(topPanel);
		add(tablePanel);
		add(bottomPanel);
		topPanel.add(salesmanLabel);
		topPanel.add(nameLabel);
		topPanel.add(quantityLabel);
		topPanel.add(itemTF);
		topPanel.add(quantityTF);
		topPanel.add(calculator);
		topPanel.add(editPass);
		bottomPanel.add(netTotal);
		bottomPanel.add(priceTF);
		bottomPanel.add(addItem);
		bottomPanel.add(delete);
		bottomPanel.add(print);
		bottomPanel.add(logout);
		
		editPass.addActionListener(this);
		addItem.addActionListener(this);
		delete.addActionListener(this);
		print.addActionListener(this);
		logout.addActionListener(this);
		calculator.addActionListener(this);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new FlowLayout());
		setTable();
		setVisible(true);
		cal.setVisible(false);
	}
	public void paint(Graphics g){
		frameHeight = getHeight();
		frameWidth = getWidth();
		topPanel.setSize(frameWidth*4/5,frameHeight/6+50);
		topPanel.setLocation(frameWidth/10,5);
		tablePanel.setSize(frameWidth*3/5,frameHeight*2/3-frameHeight/8);
		tablePanel.setLocation(frameWidth/3,frameHeight/6+55);
		bottomPanel.setSize(frameWidth*4/5,frameHeight/6);
		bottomPanel.setLocation(frameWidth/10,frameHeight*10/13+10);
		
		salesmanLabel.setFont(new Font("",Font.BOLD,25));
		salesmanLabel.setLocation(topPanel.getWidth()/2-50,frameHeight/22);
		nameLabel.setFont(new Font("",Font.BOLD,15));
		nameLabel.setLocation(topPanel.getWidth()/2-150,frameHeight/8);
		netTotal.setFont(new Font("",Font.BOLD,15));
		netTotal.setLocation(bottomPanel.getWidth()*3/4,bottomPanel.getHeight()/11);
		quantityLabel.setFont(new Font("",Font.BOLD,15));
		quantityLabel.setLocation(topPanel.getWidth()/2-170,frameHeight/8+40);
		itemTF.setLocation(topPanel.getWidth()/2-50,frameHeight/8);
		quantityTF.setLocation(topPanel.getWidth()/2-50,frameHeight/8+40);
		calculator.setSize(80,30);
		calculator.setLocation(topPanel.getWidth()*5/6,frameHeight/8+40);
		editPass.setSize(80,30);
		editPass.setLocation(topPanel.getWidth()*5/6+100,frameHeight/8+40);
		priceTF.setLocation(bottomPanel.getWidth()*3/4+110,bottomPanel.getHeight()/11);
		
		addItem.setSize(80,30);
		addItem.setLocation(bottomPanel.getWidth()*2/10, bottomPanel.getHeight()*2/3);
		delete.setSize(80,30);
		delete.setLocation(bottomPanel.getWidth()*4/10, bottomPanel.getHeight()*2/3);
		print.setSize(80,30);
		print.setLocation(bottomPanel.getWidth()*6/10, bottomPanel.getHeight()*2/3);
		logout.setSize(80,30);
		logout.setLocation(bottomPanel.getWidth()*8/10, bottomPanel.getHeight()*2/3);
		
	}
	
	public void setTable(){
		String [] c = {"Name","Unit price","Quantity","Net price"};
		int i;
		for(i=0; i<c.length; i++){
			model.addColumn(c[i]);
		}
		model = (DefaultTableModel)table.getModel();
                
		JScrollPane jsp=new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(800,350));
                tablePanel.add(jsp);
	}
	public void actionPerformed(ActionEvent ae){
		String pressed;
		pressed = ae.getActionCommand();
		if(pressed.equals("Calculator")){
			cal.setVisible(true);
		}
		if(pressed.equals("Edit password")){
			this.setVisible(false);
			sManPass.setVisible(true);
		}
		if(pressed.equals("Print")){
			printOperation();
		}
		if(pressed.equals("Logout")){
			st.setVisible(false);
			this.setVisible(false);
			frame.setVisible(true);
		}
		if(pressed.equals("Add")){
			try{
				String item = itemTF.getText().toUpperCase();
				int n = Integer.parseInt(quantityTF.getText());
				addOperation(item,n);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Item name and quantity required");
			}
		}
		if(pressed.equals("Delete")){
			int selectRow =table.getSelectedRow();
			if(selectRow<0 || selectRow<0){
				JOptionPane.showMessageDialog(table, "Select a cell first !");
			}
			else{
				deleteOperation(selectRow);
			}
		}
	}
	public void addOperation(String item,int n) throws SQLException{
		boolean arFound = false;
		int newQ;
		int found = 0;
		DataAccess da = new DataAccess();
		ResultSet rs = null;
		String q = "select * from items";
		rs = da.getData(q);
		while(rs.next()){
			String name = rs.getString("name");
			String uName = rs.getString("uppername");
			int price = rs.getInt("price");
			int unit = rs.getInt("quantity");
			if(uName.equals(item) && n<=unit){
				Item itm = new Item(name,price,n);
				found = 1;
				for(int z = 0; z<ar.size(); z++){
					Item i = ar.get(z);
					newQ = i.itemQuantity;
					String newName = i.itemName;
					if(newName.equals(name)){
						arFound = true;
						System.out.println("found");
						if(n+newQ>unit){
							JOptionPane.showMessageDialog(this, "Available : "+(unit-newQ)+"unit");
							break;
						}
						else{
							ar.add(itm);
							totalPrice += price*n;
							Vector row = new Vector();
							row.add(name);
							row.add(price);
							row.add(n);
							row.add(price*n);
							model.addRow(row);
							itemTF.setText("");
							quantityTF.setText("");
							priceTF.setText(Integer.toString(totalPrice));
							break;
						}
					}
				}
				if(!arFound){
					ar.add(itm);
					totalPrice += price*n;
					Vector row = new Vector();
					row.add(name);
					row.add(price);
					row.add(n);
					row.add(price*n);
					model.addRow(row);
					itemTF.setText("");
					quantityTF.setText("");
					priceTF.setText(Integer.toString(totalPrice));
				}
			}
			else if(uName.equals(item) && n>unit){
				JOptionPane.showMessageDialog(this, "Available : "+unit+"unit");
				found = 2;
				break;
			}
		}
		if(found==0){
			JOptionPane.showMessageDialog(this, "Item not found");
		}
	}
	
	public void deleteOperation(int delRow){
		model.removeRow(delRow);
		Item i = ar.get(delRow);
		totalPrice -= i.itemPrice*i.itemQuantity;
		priceTF.setText(Integer.toString(totalPrice));
		ar.remove(delRow);
	}
	public void printOperation(){
		while(model.getRowCount()>0){
			model.removeRow(0);
		}
		try{
			databaseOperation();
			JOptionPane.showMessageDialog(this, "Total price is " + totalPrice+" BDT");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error in database connection");
		}
		ar.clear();
		totalPrice = 0;
		priceTF.setText(Integer.toString(totalPrice));
	}
	public void databaseOperation() throws SQLException{
		for(int i = 0; i<ar.size(); i++){
			Item item = ar.get(i);
			String name = item.itemName;
			int unit = item.itemQuantity;
			DataAccess da = new DataAccess();
			ResultSet rs = null;
			String q = "update items set quantity=quantity-"+unit+" where name='"+name+"'";
			int c=da.updateDB(q);
			da.close();
		}
	}
}