package GUI;

public class Program_Client {
	public static void main(String[] args) {
		new Thread( new Client_GUI()).start();
	}
}
