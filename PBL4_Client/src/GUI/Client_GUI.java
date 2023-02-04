package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.Border;

public class Client_GUI extends JFrame implements Runnable{
	
	private JPanel panel;
	private JLabel IpServer,lbNameClient,lbNameClient_1,lbIPAddress,lbIPAddress_1,lbStatus,lbStatus_1;
	private JTextField tf_IPServer;
	private JButton btnConnect_Server, btnExit;
	
	String ipServer ;
	final int mainPortServer = 999;
	
	Socket socketFromServer; 
	boolean continueThread = true;
	Screen_Capture screenCapture;
	
	public Client_GUI()
	{
		try {
			initComponents();
			setVisible(true);
			ipServer = tf_IPServer.getText();
			lbNameClient_1.setText(InetAddress.getLocalHost().getHostName());
			lbIPAddress_1.setText(InetAddress.getLocalHost().getHostAddress());
	        lbStatus_1.setText("Đang chờ kết nối đến server...");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while (continueThread) {
            try {
            	BufferedReader inputServer = new BufferedReader(new InputStreamReader(socketFromServer.getInputStream()));
                String msg = inputServer.readLine();
                if (msg != null && !msg.isEmpty()) {
                	System.out.println(msg);
                	Central_Processing(msg);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
	}
	
	private void Central_Processing (String msg) throws UnknownHostException, IOException
	{
		if (screenCapture == null) 
		{
			 screenCapture = new Screen_Capture(socketFromServer); 
		}
			 screenCapture.Send_image(); 
	}
	
	private void initComponents()
	{
		Border blackline;
		blackline = BorderFactory.createLineBorder(Color.black);
		Font f1 = new Font("Arial",Font.BOLD,15);
		this.setTitle("Client");
		
		
		panel = new JPanel();
		IpServer = new JLabel();
		tf_IPServer = new JTextField();
		btnConnect_Server = new JButton();
		lbNameClient = new JLabel();
		lbNameClient_1 = new JLabel();
		lbIPAddress = new JLabel();
		lbIPAddress_1 = new JLabel();
		lbStatus  = new JLabel();
		lbStatus_1 = new JLabel();
		btnExit = new JButton();
		
		this.setBounds(100, 100, 450, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		panel.setBounds(0, 0, 436, 240);
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);;
		
		IpServer.setText("IP Server");
		IpServer.setFont(new Font("Tahoma", Font.BOLD, 13));
		IpServer.setBounds(30, 25, 80, 16);	
		tf_IPServer.setBounds(130, 25, 169, 19);
		tf_IPServer.setColumns(10);
		
		
		panel.add(IpServer);
		panel.add(tf_IPServer);
		
		lbNameClient.setText("Name Client");
		lbNameClient.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbNameClient.setBounds(30, 65, 80, 16);
		
		lbNameClient_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbNameClient_1.setForeground(Color.GREEN);
		lbNameClient_1.setBounds(130, 68, 169, 13);
		
		lbIPAddress.setText("IP Address");
		lbIPAddress.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbIPAddress.setBounds(30, 101, 80, 16);
		
		lbIPAddress_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbIPAddress_1.setForeground(Color.GREEN);
		lbIPAddress_1.setBounds(130, 104, 169, 13);
		
		lbStatus.setText("Status");
		lbStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus.setBounds(30, 134, 80, 16);
		
		lbStatus_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus_1.setForeground(Color.RED);
		lbStatus_1.setBounds(137, 130, 200, 13);
		
		panel.add(lbNameClient);
		panel.add(lbNameClient_1);
		panel.add(lbIPAddress);
		panel.add(lbIPAddress_1);
		panel.add(lbStatus);
		panel.add(lbStatus_1);
		
		btnConnect_Server.setText("Connect");
		btnConnect_Server.setBackground(Color.gray);
		btnConnect_Server.setBounds(100, 170, 101, 30);
		btnConnect_Server.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
	            	ipServer = tf_IPServer.getText();
					if(ipServer == null || ipServer.isEmpty())
					{
						JOptionPane.showMessageDialog(getContentPane(), "Enter IP Server to connect server:", "Thông báo lại", -1);
					}
					else 
					{
						lbStatus_1.setText("Đang chờ kết nối đến Server...");
		            	socketFromServer = new Socket(ipServer, mainPortServer);
						lbStatus_1.setText("Đã kết nối đến Server thành công."  );
						lbStatus_1.setForeground(Color.GREEN);
					}
					JOptionPane.showMessageDialog(null, "Bạn đã kết nối thành công", "Thông báo", JOptionPane.WARNING_MESSAGE);
	            	
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Không thể kết nối đến Server!");
				} 
			}
		});
		
		btnExit.setText("Exit");
		btnExit.setBackground(Color.gray);
		btnExit.setBounds(230, 170, 101, 30);
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panel.add(btnConnect_Server);
		panel.add(btnExit);
		this.add(panel);
		this.setVisible(true);
	}
}
