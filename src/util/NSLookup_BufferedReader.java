package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup_BufferedReader {

	public static void main(String[] args) {
		
		String ipAddress;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(true) {
				System.out.println("도메인을 입력하세요! :)");
				ipAddress = br.readLine();
				
				if(ipAddress.equals("exit")) {
					break;
				}
				
				try {
					InetAddress[] inetAddresses = InetAddress.getAllByName(ipAddress);
					for(InetAddress addr : inetAddresses) {
						System.out.println(addr.getHostAddress());
					}
			
				} catch (UnknownHostException e) {
					e.printStackTrace();
					System.out.println("잘못 입력하셨습니다. 다시 입력해주세요!!^^");
					continue;
				} finally {
					System.out.println();
				}
				
	 		}	// while end
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
