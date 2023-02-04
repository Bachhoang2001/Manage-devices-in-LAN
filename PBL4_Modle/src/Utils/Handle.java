package Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Handle {
	public static String GetNameCom(Socket socket) {
        return socket.getInetAddress().getHostName();
    }

public static String GetIPCom(Socket socket) {
    return socket.getInetAddress().getHostAddress();
}

public static boolean checkConnectClosed(Socket socket){
    PrintWriter out;
    try {
        out = new PrintWriter(socket.getOutputStream(), true);
        out.print(" ");
        return out.checkError();
    } catch (IOException ex) {
    }
    return true;
}
}
