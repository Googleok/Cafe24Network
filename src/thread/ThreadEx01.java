package thread;

public class ThreadEx01 {

	public static void main(String[] args) {
//		for (int i = 0; i <= 10; i++) {
//			System.out.print(i);
//		}
		
		Thread digitThread = new DigitThread();

		digitThread.start();
		
		// 여기 c는 메모리에 저장 될때 ASCII코드로 저장된다. 97~122
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
