package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Handle_Message.Message_Process;
import Handle_Message.Message_Screen;
import Utils.Fomat;

public class Screen_GUI extends JFrame implements Runnable{
	
	private JPanel jPanelDesktopRemote ;
	private JLabel lbNameUser ;
	private JLabel lbNameUser_1 ;
	private JButton btnChupHinh ;
	private JButton btnLuu ;
	private JButton btnThoat ;
	private JPanel jPanel ;
	
	
	Socket socket;
    BufferedImage img;
    boolean isContinued = true;
    
    
    public Screen_GUI(Socket socket)
	{
		this.socket = socket;
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	@Override
	public void run() {
		byte[] bytes;
        ObjectInputStream ois;
        while (isContinued) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                bytes = (byte[]) ois.readObject();
                if (bytes != null) {
                    showScreenShot(bytes);
                }
            } catch (Exception ex) {
                isContinued = false;
                ex.printStackTrace();
            }
        }
	}
	public void showScreenShot(byte[] bytes) 
	{
        try {
            img = ImageIO.read(new ByteArrayInputStream(bytes));
            SwingUtilities.invokeLater(
            		new Runnable() 
            		{
		                public void run() 
		                {
		                    try {
		                        Image image = img.getScaledInstance(jPanelDesktopRemote.getWidth()
		                        		, jPanelDesktopRemote.getHeight(), Image.SCALE_SMOOTH);
		                        if (image == null) 
		                        {
		                            return;
		                        }
		                        // Hiển thị ảnh lên panel
		                        Graphics graphics = jPanelDesktopRemote.getGraphics();
		                        graphics.drawImage(image, 0, 0, jPanelDesktopRemote.getWidth()
		                        		, jPanelDesktopRemote.getHeight(), jPanelDesktopRemote);
		                    } 
		                    catch (Exception ex) 
		                    {
		                    }
		                }
            		});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	private void formWindowClosing(WindowEvent evt) 
	{
        isContinued = false;
    }
	
	private void btnCaptureActionPerformed(ActionEvent evt) 
	{
		
		Message_Screen p = new Message_Screen(); 
		Message_Process.Send_message(socket, p.toString());
		 
    }
	private void btnSaveActionPerformed(ActionEvent evt) 
	{
        try {
            String filename = "ScreeenShot_" + socket.getInetAddress().getHostName()
                    + "_" + Fomat.formatDate(new Date(), "ddMMyyyyhhmmss") + ".png";

            JFileChooser chooser = new JFileChooser(filename);
            chooser.setFileFilter(new FileNameExtensionFilter("Images file", "png"));
            chooser.setSelectedFile(new File(filename));
            chooser.setMultiSelectionEnabled(false);

            if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            // retrieve image
            File outputfile = chooser.getSelectedFile();
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void initComponents() 
	{
    	this.setTitle("Print Screen");
		
		jPanelDesktopRemote = new JPanel();
		lbNameUser = new JLabel();
		lbNameUser_1 = new JLabel();
		btnChupHinh = new JButton();
		btnLuu = new JButton();
		btnThoat = new JButton();
		jPanel = new JPanel();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1000, 700);
		this.setLayout(null);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
		
		jPanel.setBounds(0, 0, 986, 103);
		jPanel.setLayout(null);
		jPanel.setBackground(Color.LIGHT_GRAY);;
		
		lbNameUser.setText("Name User:");
		lbNameUser.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNameUser.setBounds(10, 0, 100, 40);
		
		lbNameUser_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbNameUser_1.setForeground(Color.GREEN);
		lbNameUser_1.setBounds(10, 60, 100, 40);
		
		btnChupHinh.setText("Print Screen");
		btnChupHinh.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnChupHinh.setBounds(300, 25, 120, 30);
		btnChupHinh.setBorder(null);
		btnChupHinh.setBackground(Color.gray);
		btnChupHinh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCaptureActionPerformed(e);
			}
		});
		
		btnLuu.setText("Save");
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLuu.setBounds(460, 25, 120, 30);
		btnLuu.setBorder(null);
		btnLuu.setBackground(Color.gray);
		btnLuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
			}
		});
		
		btnThoat.setText("Exit");
		btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnThoat.setBounds(620, 25, 120, 30);
		btnThoat.setBorder(null);
		btnThoat.setBackground(Color.gray);
		btnThoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		jPanel.add(lbNameUser);
		jPanel.add(lbNameUser_1);
		jPanel.add(btnChupHinh);
		jPanel.add(btnLuu);
		jPanel.add(btnThoat);
		
		jPanelDesktopRemote.setBounds(0, 101, 986, 562);
		jPanel.setLayout(null);
		
		
		this.add(jPanel);
		this.add(jPanelDesktopRemote);
		
		this.setVisible(true);
	}
}
