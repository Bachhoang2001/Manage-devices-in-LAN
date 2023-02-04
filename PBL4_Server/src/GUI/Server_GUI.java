package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Utils.ComputerTable_Modle;

public class Server_GUI extends JFrame implements Runnable{

	private JTable table;
	private JPanel panel ;
	private JLabel lbNameNetwork ;
	private JLabel lbNameNetwork_1 ;
	private JButton btnScanIP ;
	private JButton btnPrintScreen ;
	private JButton btnRefresh ;
	
	
	private final int mainThreadPortNumber = 999;
	Timer timerUpdateListSocket;
    private int timeUpdateTable = 5; // second
    String columnNames[] = {"STT", "IP Computer", "Name Computer", "Port", "Status"};
	
	public Server_GUI()
	{
		initComponents();
		table.setModel(new ComputerTable_Modle( new ArrayList<Socket>()) );
		table.getColumnModel().getColumn(0).setMaxWidth(20); 

		  timerUpdateListSocket = new Timer();
		  timerUpdateListSocket.scheduleAtFixedRate( new TimerTask() {
		  
			  @Override public void run() { 
				  getTbModle().updateAllElement(); 
			  }
		  	},timeUpdateTable * 1000, timeUpdateTable * 1000);
	}
	
	private ComputerTable_Modle getTbModle() {
        return (ComputerTable_Modle) table.getModel();
    }
	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
            final ServerSocket server = new ServerSocket(mainThreadPortNumber);
            // Phục vụ nhiều client
            while (true) {
                Socket socket;
                try {
                    // Nếu không dùng thread
                    // chương trình sẽ chờ 1 client đầu tiên ở đây
                    socket = server.accept();
                    getTbModle().addElement(socket);
                    System.out.println("Server: Connected to client " + getTbModle().getSize());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	private void initComponents() {
		this.setTitle("Server");
		
		table = new JTable();
		panel = new JPanel();
		lbNameNetwork = new JLabel();
		lbNameNetwork_1 = new JLabel();
		btnScanIP = new JButton();
		btnPrintScreen = new JButton();
		btnRefresh = new JButton();
		
		this.setBounds(100, 100, 800, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		panel.setBounds(0, 0, 886, 79);
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);;
		
		lbNameNetwork.setText("Network:");
		lbNameNetwork.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNameNetwork.setBounds(10, 0, 100, 40);
		
		lbNameNetwork_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNameNetwork_1.setForeground(Color.GREEN);
		lbNameNetwork_1.setBounds(10, 60, 100, 40);
		
		btnScanIP.setText("Scan Network");
		btnScanIP.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnScanIP.setBounds(270, 25, 120, 30);
		btnScanIP.setBorder(null);
		btnScanIP.setBackground(Color.gray);
		btnScanIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					InetAddress address = InetAddress.getLocalHost();
					String ip = address.getHostAddress().toString();
					new Thread(new ScanIP_GUI(ip,254)).start();
				} catch (UnknownHostException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
		
		btnPrintScreen.setText("Print Sceen");
		btnPrintScreen.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnPrintScreen.setBounds(430, 25, 120, 30);
		btnPrintScreen.setBorder(null);
		btnPrintScreen.setBackground(Color.gray);
		btnPrintScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount() == 0) {
		            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn máy để chụp hình!");
		            return;
		        }
		        Socket mayClient = getTbModle().getItem(table.getSelectedRow());
		        // Mở form chụp hình với client đã chọn
		        new Thread(new Screen_GUI(mayClient)).start();
			}
		});		
		btnRefresh.setText("Refresh");
		btnRefresh.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnRefresh.setBounds(590, 25, 120, 30);
		btnRefresh.setBorder(null);
		btnRefresh.setBackground(Color.gray);
		
		panel.add(lbNameNetwork);
		panel.add(lbNameNetwork_1);
		panel.add(btnScanIP);
		panel.add(btnPrintScreen);
		panel.add(btnRefresh);
		
		table.setBounds(0, 81, 886, 432);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
//		table.setModel(new DefaultTableModel(
//				new Object[][] {
//
//		}, 
//				new String[] {"STT", "IP Computer", "Name Computer", "Port", "Status"}) 
//
//				);
//		if (table.getColumnModel().getColumnCount() > 0) {
//			table.getColumnModel().getColumn(0).setResizable(false);
//			table.getColumnModel().getColumn(0).setPreferredWidth(10);
//			table.getColumnModel().getColumn(1).setResizable(false);
//			table.getColumnModel().getColumn(1).setPreferredWidth(200);
//			table.getColumnModel().getColumn(2).setResizable(false);
//			table.getColumnModel().getColumn(2).setPreferredWidth(200);
//			table.getColumnModel().getColumn(3).setResizable(false);
//			table.getColumnModel().getColumn(3).setPreferredWidth(200);
//			table.getColumnModel().getColumn(4).setResizable(false);
//			table.getColumnModel().getColumn(4).setPreferredWidth(200);
//		}
		this.add(panel);
		this.add(table);
		
		this.setVisible(true);
	}
	
}
