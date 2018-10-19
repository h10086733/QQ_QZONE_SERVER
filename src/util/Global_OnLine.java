package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import serverframe_thread.Server_WaitIo_Thread;

public class Global_OnLine {
	private Global_OnLine(){
		
	}
	public static List<Server_WaitIo_Thread> onLineUserList = new ArrayList<Server_WaitIo_Thread>();
	
	public static HashMap hashMap =new HashMap();
	
	public static void sendOrAll(String str){
		Collection con=hashMap.values();
		System.out.println(con);
		for (Object object : con) {
			Server_WaitIo_Thread serverWaitIoThread=(Server_WaitIo_Thread)object;
			try {
				serverWaitIoThread.getObjectOutputStream().writeObject(str);
//				System.out.println("服务器已经写出,客户端请接收"+str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
