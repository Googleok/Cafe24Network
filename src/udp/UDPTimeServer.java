package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT = 7000;
	public static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. UDP 소켓 생성
			socket = new DatagramSocket(PORT);
			System.out.println("안녕");
			
			
			while(true) {
				// 2. Data 수신
				DatagramPacket receivePacket =
						new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0,length, "UTF-8");
				
				byte[] sendData = null;
				DatagramPacket sendPacket = null;
				
				// "" 로 수신 시 Time 을 전송
				if(message.contentEquals("")) {
				
					System.out.println("[server] received:" + "시간 요청 !!");
			
					// TimeFormat
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
					String timeData = format.format(new Date());
	
					// 3. Data 전송
					sendData = timeData.getBytes("utf-8");
				
					sendPacket = 
							new DatagramPacket(sendData,
											   sendData.length,
											   receivePacket.getAddress(),
											   receivePacket.getPort());
					socket.send(sendPacket);
					
					continue;
				}
				
				System.out.println("[server] received:" + message);

				sendData = message.getBytes("utf-8");
				sendPacket = 
						new DatagramPacket(sendData,
										   sendData.length,
										   receivePacket.getAddress(),
										   receivePacket.getPort());
				socket.send(sendPacket);
				
			}
		
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
		
		
		
	}

}
