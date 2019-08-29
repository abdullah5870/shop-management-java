import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

class SManTable extends Frame implements ActionListener{
	private DefaultTableModel model;
	private JTable table;
	private Button close;
	public SManTable(){
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
		String q = "select * from salesman";
		rs = da.getData(q);
		String [] c = {"Name","Password"};
		model = new DefaultTableModel();
		int i;
		for(i=0; i<c.length; i++){
			model.addColumn(c[i]);
		}
		table = new JTable(model);
		model = (DefaultTableModel)table.getModel();
		
		while(rs.next()){
			String name = rs.getString("username");
			String password = rs.getString("password");
			Vector row = new Vector();
			row.add(name);
			row.add(password);
			model.addRow(row);
		}
                JScrollPane jsp=new JScrollPane(table);
                add(jsp);
		table.setRowHeight(30);
		add(close);
		close.addActionListener(this);
		table.setPreferredScrollableViewportSize(new Dimension(300,500));
		setLayout(new FlowLayout());
	}
	public void addToTable(String n){
		Vector r = new Vector();
		r.add(n);
		model.addRow(r);
	}
	
	public void removeFromTable(String name){
		for(int i = 0; i<model.getRowCount(); i++){
			if(model.getValueAt(i,0).equals(name)){
				model.removeRow(i);
				break;
			}
		}
	}
	
	public void actionPerformed(ActionEvent a){
		this.setVisible(false);
	}
}