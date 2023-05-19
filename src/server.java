import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

interface RMILoginInterface extends Remote {
	public String checkLogin(User user) throws RemoteException;
}

class RMILoginServerControl extends UnicastRemoteObject implements RMILoginInterface {
	private static final long serialVersionUID = 16361218902592279L;
	
	private int serverPort = 777;
	private Registry registry;
	private String rmiService = "Login";
	private RMILoginServerView view;
	
	public RMILoginServerControl(RMILoginServerView view) throws RemoteException, AlreadyBoundException {
		try {
			this.view = view;
			registry = LocateRegistry.createRegistry(serverPort);
			registry.bind(rmiService, this);
//			view.showMessage("Chạy server thành công");
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public String checkLogin(User user) throws RemoteException{
		if (checkUser(user)) {
//			System.out.print("Thành công");

				return user.getUser();
		}
		else {
//			System.out.print("Thất bại");
			return null;
		}
	}
	
	public boolean checkUser(User user) {
		String username = user.getUser();
		for (User curr: User.sampleUser) {
			if (username.equals(curr.getUser())) return user.getPassword().equals(curr.getPassword());
		}
		return false;
	}
}

class RMILoginServerView {
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Thông báo",JOptionPane.WARNING_MESSAGE);
	}
}

class ServerRun{
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		RMILoginServerView view = new RMILoginServerView();
		RMILoginServerControl controller = new RMILoginServerControl(view);
	}
}