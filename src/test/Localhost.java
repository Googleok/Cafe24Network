package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost {

	public static void main(String[] args) {
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
		
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			
			System.out.println( hostname + ":" + hostAddress);
			
			InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
			
//			int i = 0;
//			for(InetAddress addr : inetAddresses) {
//				System.out.println(i++);
//				System.out.println(addr.getHostAddress());
//			}
			
			
			byte[] addresses = inetAddress.getAddress();
			for( byte address : addresses) {
				System.out.print(address & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
