import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

class TableSensor extends Frame implements ActionListener{
	DefaultTableModel model;
	JTable table;
	private Button close;
	public TableSensor(){
		setSize(500,600);
		close = new Button("Close");
		try{
			operation();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Connection error");
		}
		setAlwaysOnTop(true);
	}
	public void operation() throws SQLException{
		DataAccess da = new DataAccess();
		ResultSet rs=null;
		String q = "select * from items";
		rs = da.getData(q);
		String [] c = {"Name","Price","Quantity"};
		model = new DefaultTableModel();
		int i;
		for(i=0; i<c.length; i++){
			model.addColumn(c[i]);
		}
		table = new JTable(model);
		model = (DefaultTableModel)table.getModel();
		
		while(rs.next()){
			String name = rs.getString("name");
			String price  = Integer.toString(rs.getInt("price"));
			String quantity  = Integer.toString(rs.getInt("quantity"));
			Vector row = new Vector();
			row.add(name);
			row.add(price);
			row.add(quantity);
			model.addRow(row);
		}
                JScrollPane jsp=new JScrollPane(table);
                add(jsp);
		add(close);
		close.addActionListener(this);
		table.setPreferredScrollableViewportSize(new Dimension(480,500));
		setLayout(new FlowLayout());
	}
	public void addToTable(String n,int p,int q){
		Vector r = new Vector();
		r.add(n);
		r.add(p);
		r.add(q);
		model.addRow(r);
	}
	public void updateTable(String n,int p,int q){
		for(int i = 0; i<table.getRowCount(); i++){
			String rowName = (String) model.getValueAt(i,0);
			if(rowName.toUpperCase().equals(n)){
				model.setValueAt(p,i,1);
				model.setValueAt(q,i,2);
				System.out.println("Price quantity");
			}
		}
	}
	public void updateTable(String n,int q){
		for(int i = 0; i<table.getRowCount(); i++){
			String rowName = (String) model.getValueAt(i,0);
			if(rowName.toUpperCase().equals(n)){
				model.setValueAt(q,i,2);
				System.out.println("quantity");
			}
		}
	}
	
	public void updateTable(String currentName,String newName,int p,int q){
		for(int i = 0; i<table.getRowCount(); i++){
			if(model.getValueAt(i,0).equals(currentName)){
				model.setValueAt(newName,i,0);
				model.setValueAt(p,i,1);
				model.setValueAt(q,i,2);
				System.out.println("name Price quantity");
			}
		}
	}
	public void updateTable(String currentName,String newName,int q){
		for(int i = 0; i<table.getRowCount(); i++){
			if(model.getValueAt(i,0).equals(currentName)){
				model.setValueAt(newName,i,0);
				model.setValueAt(q,i,2);
				System.out.println("name quantity");
			}
		}
	}
	
	
	public void actionPerformed(ActionEvent a){
		this.setVisible(false);
	}
}