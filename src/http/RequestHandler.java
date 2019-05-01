package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class RequestHandler extends Thread{
	private static  String documentRoot = "";
	
	static {
		try {
			documentRoot = new File(RequestHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
	
			documentRoot += "/webapp";
			System.out.println("documentRoot ------> "+documentRoot);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try { // 이미지랑 뭐 다른거 그냥 byte로 함
			
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort());
		
			// get IOStream
			OutputStream os = socket.getOutputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		
			String request = null;
			
			// 브라우저로부터 오는 요청 다 읽기
			while (true) {
				String line = br.readLine();
				// 브라우저가 연결을 끊으면 
				if(line == null) {
					break;
				}
				
				// line이 잘 온거임
				
				// Request Header 만 읽음
				if("".equals(line)) {
					break;
				}
			
				// Header의 첫번째 라인만 처리
				if(request == null) {
					request = line;
				}
			}
			String[] tokens = request.split(" ");
			
			if("GET".contentEquals(tokens[0])) {
				consoleLog("request: " + tokens[1]);
				responseStaticResource(os, tokens[1], tokens[2]);
			} else { // POST, PUT, DELETE, HEAD, CONNECT
					 // 와 같은 Method는 무시
				/*
				 * 응답 예시
				 *  HTTP/1.1 400 Bad Request \r\n
				 *  Content-Type:text/html; charset=utf-8 \r\n
				 *  
				 *  \r\n
				 *  HTML 에러문서 (./webapp/error/400.html
				 *  response400Error(os, protocol)   
				 */
				String protocol_400 = tokens[2];
				response400Error(os, protocol_400);
				consoleLog("Bad Request: " + request);
				
			}
			
			//예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			os.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
//			os.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
//		    os.write( "\r\n".getBytes() ); //header 와 body 부분 나눠지는 부분
//		    os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
		} catch (IOException ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public void responseStaticResource(OutputStream os, String url, String protocol) throws IOException {
		
		System.out.println("protocol="+protocol);
		if("/".equals(url)) {
			url = "/index.html";
		}

			
		
		// 파일 읽기
		File file = new File(documentRoot + url);
		if(file.exists() == false) {
			/*
			 * 응답 예시
			 *  HTTP/1.1 404 File Not Found \r\n
			 *  Content-Type:text/html; charset=utf-8 \r\n
			 *  
			 *  \r\n
			 *  HTML 에러문서 (./webapp/error/404.html
			 *  response404Error(os, protocol)   
			 */
			response404Error(os, protocol);
			return;
		}
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		// 응답
		os.write((protocol + " 200 OK\r\n").getBytes("UTF-8"));
		os.write(("Content-Type:"+ contentType +"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
	    os.write( "\r\n".getBytes() ); //header 와 body 부분 나눠지는 부분
	    os.write( body );
	}

	// 400 Error 처리
	private void response400Error(OutputStream os, String protocol_400) throws IOException {
		File file = new File(documentRoot + "/error/400.html");
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		os.write((protocol_400 + " 400 Bad Request\r\n").getBytes("UTF-8"));
		os.write( ("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
	    os.write( "\r\n".getBytes() ); //header 와 body 부분 나눠지는 부분
	    os.write(body);
	}
	
	// 404 Error 처리
	private void response404Error(OutputStream os, String protocol) throws IOException{
		File file = new File(documentRoot + "/error/404.html");
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		os.write((protocol + " 404 File Not Found\r\n").getBytes("UTF-8"));
		os.write( ("Content-Type:"+contentType+"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
	    os.write( "\r\n".getBytes() ); //header 와 body 부분 나눠지는 부분
	    os.write(body);
	}

	public void consoleLog( String message ) {
	      System.out.println( "[RequestHandler#" + getId() + "] " + message );
	 }
}
