package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardTest {

	public static void main(String[] args) {
		// 기반 스트림 ( 표준 입력, 키보드, System.in)
		BufferedReader br = null;
		InputStreamReader isr = null;

		try {
			// 보조 스트림1 ( InputStreamReader : byte를 char로 )
			// byte|byte|byte -> char
			isr = new InputStreamReader(System.in, "UTF-8");
			
			//보조스트림 2 ( BufferedReader ) byte 를 buffer 에 다 채워서 한번에 보냄 ( flush 함수를 사용? , readLine())
			// char1|char2|char3|\n -> "char1char2char3
			br = new BufferedReader(isr);
			
			// read 
			String line = null;
			while((line = br.readLine()) != null) {
				if("exit".equals(line)) break;
				System.out.println(">>" + line);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 맨 앞에 것만 닫아주면 다 닫긴다.
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
