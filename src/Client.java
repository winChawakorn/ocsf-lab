import java.io.IOException;
import java.util.Scanner;

import com.lloseng.ocsf.client.AbstractClient;

/**
 * A Client for joining a Server.
 * 
 * @author Chawakorn Suphepre
 * @version 2017.05.17
 *
 */
public class Client extends AbstractClient {
	private static String message;
	private static Client client;

	/**
	 * Initialize a new Client with IP address and port.
	 * 
	 * @param host
	 *            is an IP address of the server.
	 * @param port
	 *            is the port code.
	 */
	public Client(String host, int port) {
		super(host, port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lloseng.ocsf.client.AbstractClient#handleMessageFromServer(java.lang
	 * .Object)
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		message = (String) msg;
		System.out.println(msg);
		generateAnswer();
	}

	/**
	 * Send the answer to the server.
	 */
	public static void generateAnswer() {
		String[] words = message.split(" ");
		int num1 = 0;
		int num2 = 0;
		if (message.charAt(0) == 'W') {
			num1 = Integer.parseInt(words[2]);
			num2 = Integer
					.parseInt(words[4].substring(0, words[4].length() - 1));
		}
		int ans = 99999999;
		switch (words[3]) {
		case "^":
			ans = num1 ^ num2;
			break;
		case "|":
			ans = num1 | num2;
			break;
		case "&":
			ans = num1 & num2;
			break;
		}
		if (ans != 99999999) {
			try {
				client.sendToServer(ans + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				client.sendToServer("Login Win");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Main for run the client.
	 * 
	 * @param args
	 *            not use
	 */
	public static void main(String[] args) {
		client = new Client("10.2.12.83", 5001);
		Scanner sc = new Scanner(System.in);
		try {
			client.openConnection();
			while (client.isConnected()) {
				client.sendToServer(sc.nextLine());
			}
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
