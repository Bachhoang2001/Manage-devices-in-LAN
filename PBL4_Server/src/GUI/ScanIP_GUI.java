package GUI;

import java.awt.Color;
import java.awt.Font;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import REMOTE.Request_ScanIP;
import Utils.IPTable_Modle;

public class ScanIP_GUI extends JFrame implements Runnable{
	
	private JPanel panel;
	private JLabel label;
	private JTable table;
	private String firstIpInTheNetwork;
	private int numOfIps;
	
	
	public ScanIP_GUI(String firstIpInTheNetwork, int numOfIps)
	{
        initComponents();
        this.firstIpInTheNetwork = firstIpInTheNetwork;
        this.numOfIps = numOfIps;
        table.setModel(new IPTable_Modle( new ArrayList<String>()) );
	}
	private IPTable_Modle getTbModle() {
        return (IPTable_Modle) table.getModel();
    }
	@Override
	public void run() {
		
		for (String ip : Request_ScanIP.Scan(this.firstIpInTheNetwork, this.numOfIps) ) {
			getTbModle().addElement(ip);
	        }
		label.setText(getTbModle().getRowCount() + " (online) devices of " + getTbModle().getRowCount() +"(SUM)");
		
	}
	private void initComponents() 
	{
		this.setTitle("Scan IP");
		panel = new JPanel();
		label = new JLabel();
		table = new JTable();
		
		this.setBounds(100, 100, 450, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		panel.setBounds(0, 0, 436, 40);
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);;
		
		label.setText("...(online) devices of  ...(SUM)");
		label.setFont(new Font("Tahoma", Font.BOLD, 10));
		label.setBounds(10, 8, 436, 30);	
		
		table.setBounds(0, 40,436,430);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
				new Object[][] { 
					
				}, 
				new String[] {}) 

				);
		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
		}
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollpane = new JScrollPane(table);
		panel.add(label);
		
		this.add(panel);
		this.add(table);
		this.setVisible(true);
	}

}
