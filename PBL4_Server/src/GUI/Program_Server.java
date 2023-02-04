package GUI;

public class Program_Server {

	public static void main(String[] args) {
		new Thread( new Server_GUI()).start();
	}

}
