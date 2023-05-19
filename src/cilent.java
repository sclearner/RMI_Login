import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;

class RMILoginCilentView extends JFrame{
	private static final long serialVersionUID = 4548583211497469831L;
	
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	
	public RMILoginCilentView() {
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		btnLogin = new JButton();
		
		this.setSize(500,90);
		this.setLayout(new GridLayout(3,1));
		this.add(new JLabel());
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(1,5));
		main.add(new JLabel("Username: "));
		main.add(txtUsername);
		main.add(new JLabel("Password: "));
		main.add(txtPassword);
		btnLogin.setText("Login");
		btnLogin.setPreferredSize(new Dimension(20,10));
		main.add(btnLogin);
		this.add(main, 1);
	}
	
	public User getUser() {
		String user = txtUsername.getText();
		String password = new String(txtPassword.getPassword());
		return new User(user, password);
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(
				null, 
				message, 
				"Thông báo", 
				JOptionPane.WARNING_MESSAGE
				);
	}
	
	public void addLoginListener(ActionListener listener) {
		btnLogin.addActionListener(listener);
	}
}

class RMILoginCilentControl {
	private String serverHost = "localhost";
	private int serverPort = 777;
	private RMILoginInterface rmiServer;
	private String rmiService = "Login";
	private Registry registry;
	private RMILoginCilentView view;
	
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				String statusUsername = rmiServer.checkLogin(view.getUser());
				if (statusUsername != null) view.showMessage("Login successfully!");
				else view.showMessage("Invalid username and/or password!");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public RMILoginCilentControl(RMILoginCilentView view) throws MalformedURLException, RemoteException, NotBoundException{
		try {
			this.view = view;
			LoginListener listener = new LoginListener();
			view.addLoginListener(listener);
			this.rmiServer = (RMILoginInterface)Naming.lookup(String.format("rmi://%s:%d/%s",serverHost, serverPort, rmiService));
			
		}
		catch (Exception e) {
			view.showMessage("No conection to server.");
      		e.printStackTrace();
		}
	}	
}


class CilentRun {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		RMILoginCilentView view = new RMILoginCilentView();
		RMILoginCilentControl controller = new RMILoginCilentControl(view);
		view.setVisible(true);
	}
}