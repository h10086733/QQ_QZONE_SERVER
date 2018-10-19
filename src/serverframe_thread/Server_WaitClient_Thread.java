package serverframe_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serverframe_servermanage.ServerManagePanel;

/**
 * ���������˽��յ��ͻ������ӵ��߳�
 * 
 * @author lisu
 * 
 */
public class Server_WaitClient_Thread implements Runnable {
	/**
	 * �������׽���
	 */
	private ServerSocket serverSocket;
	/**
	 * Ҫ���ӵ��׽���
	 */
	private Socket socket;

	/**
	 * ѡ�����_����������
	 */
	private ServerManagePanel serverManagePanel;

	/**
	 * ���������˽��յ��ͻ�����Ϣ���߳�
	 * 
	 */
	private Server_WaitIo_Thread serverWaitIoThread;

	/**
	 * �����������������˽��յ��ͻ������ӵ��̵߳ı���
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
				System.out.println("�˿�Ϊ" + socket.getPort() + "�ͻ�������");
				serverWaitIoThread = new Server_WaitIo_Thread(socket,
						serverManagePanel);
				serverWaitIoThread.start();
			} catch (IOException e) {
				System.out.println("���������ܹر�");
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
