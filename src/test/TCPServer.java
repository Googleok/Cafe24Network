package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.print.attribute.standard.Severity;

public class TCPServer {

	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			// 1-1. Time-Wait 시간에 소켓에 포트번호 할당을 가능하게 하기 위하여
			serverSocket.setReuseAddress(true);
			
			// 2. 바인딩(binding)
			// 	  : Socket에 SocketAddress (IPAddress + Port)
			// 		를 바인딩 한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println(inetAddress);
			//String localhost = inetAddress.getHostAddress();
			//serverSocket.bind(new InetSocketAddress(localhost, 5000));
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 6000));
			
			// 3. accept 
			//    : 클라이언트의 연결요청을 기다림
			Socket socket = serverSocket.accept();	// block
		
			// 접속한 상대방 주소를 알아내기
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
		
			
			System.out.println("[server] connected by client[" + remoteHostAddress + ":" + remotePort + "]");
			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
			
				while(true) {
					// 5. data 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);	//blocking
					if(readByteCount == -1) {
						// 클라이언트가 정상종료 한 경우
						// close() 메소드 호출을 통해서
						System.out.println("[server] closed by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server] received : " + data);
					
					// 6. 데이터 쓰기
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					os.write(data.getBytes("UTF-8"));
					
				}
			}catch(SocketException e) {//IOException이 이미 이 기능이 있어서 socketException은 더 위에 작성 
	            System.out.println("[server] sudden close by client!");
	        }catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(socket != null && socket.isClosed() == false) {
						socket.close();
					}
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(serverSocket != null && serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
