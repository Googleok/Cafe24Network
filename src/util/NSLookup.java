package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		String ipAddress;
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("도메인을 입력하세요! :)");
			ipAddress = sc.nextLine();
			
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
		
		sc.close();
		
	}

}
