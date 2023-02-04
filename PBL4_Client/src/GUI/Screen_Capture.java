package GUI;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Handle_IMG.Capture_IMG;


public class Screen_Capture {
	Socket socket;
    ObjectOutputStream oos;
    Capture_IMG cScreenShot;
    
    
    public Screen_Capture(Socket socket) {
        this.socket = socket;
    }
    public void Send_image() {
        try {
          cScreenShot = new Capture_IMG(1.0f);
          oos =  new ObjectOutputStream(socket.getOutputStream());
          // Lấy màn hình mặc định của hệ thống
          GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
          GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
          
          // Chuẩn bị robot thao tác màn hình
          Robot robot = new Robot(gDev);
          oos.writeObject(cScreenShot.execute(robot));
          oos.flush();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
