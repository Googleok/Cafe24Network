package test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

public class Localhost {

	public static void main(String[] args) {
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
		
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			
			System.out.println( hostname + ":" + hostAddress);
			
			InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
			
			int i = 0;
			for(InetAddress addr : inetAddresses) {
				System.out.println(i++);
				System.out.println(addr.getHostAddress());
			}
			
			System.out.println("===================================");
			
			byte[] addresses = inetAddress.getAddress();
			for( byte address : addresses) {
				System.out.print(address & 0x000000ff);
				System.out.print(".");
			}
			System.out.println();
			System.out.println("===================================");
			
			NetworkInterface nif = NetworkInterface.getByName("eth0");
			Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();
			System.out.println(nifAddresses.nextElement());
			
			for(NetworkInterface ifc : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				if( ifc.isUp() == true &&
					ifc.isVirtual() == false &&
					ifc.isLoopback() == false) {
					for(InetAddress inetAddr : Collections.list(ifc.getInetAddresses())) {
						if(inetAddr.isLinkLocalAddress() == false) {
							System.out.println(inetAddr.getHostAddress());
						}
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
