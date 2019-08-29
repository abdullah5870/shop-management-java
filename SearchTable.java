import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
public class SearchTable extends Frame implements ActionListener{
	private DefaultTableModel model;
	private Button close;
	public SearchTable(){
		setSize(400,600);
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
		String c = "Name";
		model = new DefaultTableModel();
		model.addColumn(c);
		JTable table = new JTable(model);
		model = (DefaultTableModel)table.getModel();
		
		while(rs.next()){
			String name = rs.getString("name");
			Vector row = new Vector();
			row.add(name);
			model.addRow(row);
		}
                JScrollPane jsp=new JScrollPane(table);
                add(jsp);
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
	public void updateTable(String currentName,String newName){
		for(int i = 0; i<model.getRowCount(); i++){
			if(model.getValueAt(i,0).equals(currentName)){
				model.setValueAt(newName,i,0);
			}
		}
	}
	public void actionPerformed(ActionEvent a){
		this.setVisible(false);
	}
}