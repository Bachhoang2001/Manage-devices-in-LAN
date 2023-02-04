package Handle_Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Message_Process {
	public static String Receive_message(Socket sender) {
        try {
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(sender.getInputStream()));
            String msg = inputServer.readLine();
            return msg;
        } catch (IOException ex) {
            return "";
        }
    }
	
	public static boolean Send_message(Socket receiver, String msg) {
        PrintWriter outputToClient;
        try {
            outputToClient = new PrintWriter(receiver.getOutputStream(), true);
            outputToClient.println(msg);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
