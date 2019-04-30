package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String SERVER_IP = "192.168.1.35";
	private static final int SERVER_PORT = 7000;
	
	public static void main(String[] args) {

		// 1. 소켓 생성
		Scanner scanner = null;
		DatagramSocket socket = null;

		try {
			scanner = new Scanner(System.in);
			socket = new DatagramSocket();
			
			// 서버 연결 안함

			while (true) {
				// 3. 키보드 입력 받기
				System.out.print(">>");
				String line = scanner.nextLine();
				if ("quit".contentEquals(line)) {
					break;
				}

				// 4. 데이터 쓰고 보내기

				byte[] sendData = line.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));

				socket.send(sendPacket);

				// 5. 데이터 읽기
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],
						UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");

				// 6. console 출력
				System.out.println("<<" + message);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static void log(String log) {
		System.out.println("[server] " + log);
	}
}
