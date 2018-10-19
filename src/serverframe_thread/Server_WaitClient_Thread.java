package serverframe_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serverframe_servermanage.ServerManagePanel;

/**
 * 服务器不端接收到客户端连接的线程
 * 
 * @author lisu
 * 
 */
public class Server_WaitClient_Thread implements Runnable {
	/**
	 * 服务器套接字
	 */
	private ServerSocket serverSocket;
	/**
	 * 要连接的套接字
	 */
	private Socket socket;

	/**
	 * 选项卡属性_服务管理面板
	 */
	private ServerManagePanel serverManagePanel;

	/**
	 * 服务器不端接收到客户端信息的线程
	 * 
	 */
	private Server_WaitIo_Thread serverWaitIoThread;

	/**
	 * 用来开启服务器不端接收到客户端连接的线程的变量
	 */
	private boolean statwait;

	public Server_WaitClient_Thread(ServerSocket serverSocket,
			ServerManagePanel serverManagePanel) {
		this.serverSocket = serverSocket;
		statwait = true;
		this.serverManagePanel = serverManagePanel;
	}

	public void run() {
		while (statwait) {
			try {
				socket = serverSocket.accept();
				System.out.println("端口为" + socket.getPort() + "客户机连上");
				serverWaitIoThread = new Server_WaitIo_Thread(socket,
						serverManagePanel);
				serverWaitIoThread.start();
			} catch (IOException e) {
				System.out.println("服务器可能关闭");
				// e.printStackTrace();
				break;
			}
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ServerManagePanel getServerManagePanel() {
		return serverManagePanel;
	}

	public void setServerManagePanel(ServerManagePanel serverManagePanel) {
		this.serverManagePanel = serverManagePanel;
	}

	public Server_WaitIo_Thread getServerWaitIoThread() {
		return serverWaitIoThread;
	}

	public void setServerWaitIoThread(Server_WaitIo_Thread serverWaitIoThread) {
		this.serverWaitIoThread = serverWaitIoThread;
	}

	public boolean isStatwait() {
		return statwait;
	}

	public void setStatwait(boolean statwait) {
		this.statwait = statwait;
	}

}
