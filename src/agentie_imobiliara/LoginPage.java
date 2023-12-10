package agentie_imobiliara;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.awt.Color;
/**
 * Clasa aceasta reprezinta fereastra de Login.
 * Fereastra permite crearea unui nou cont sau
 * logarea intr-un cont deja existent.
 * Exista doua tipuri de cont dupa care se face
 * conexiunea: admin, angajat.
 * @author Florin
 */
public class LoginPage extends JFrame{

	/**
	 * Contine caracteristicile ferestrei de Login.
	 */
	public JFrame userLogin;

	/**
	 * Camp unde username-ul este introdus.
	 */
	private JTextField usernameField;
	/**
	 * Camp unde parola este introdusa.
	 */
	private JPasswordField passwordField;
	/**
	 * Indica spre locul unde se introduce username-ul.
	 */
	private JLabel usernameLB;
	/**
	 * Indica spre locul unde se introduce parola.
	 */
	private JLabel passwordLB;

	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.userLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	/**
	 * Apeleaza functia initialize() 
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/**
	 * Functia contine initializarea tuturor variabilelor
	 * si toate functiile ferestrei Login.
	 */
	private void initialize() {
		userLogin = new JFrame();
		userLogin.getContentPane().setBackground(new Color(144, 221, 240));
		userLogin.setBounds(100, 100, 449, 297);
		userLogin.setLocationRelativeTo(null);
		userLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userLogin.getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setForeground(new Color(240, 237, 238));
		passwordField.setBackground(new Color(44, 102, 110));
		passwordField.setBounds(145, 115, 110, 28);
		userLogin.getContentPane().add(passwordField);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setForeground(new Color(240, 237, 238));
		usernameField.setBackground(new Color(44, 102, 110));
		usernameField.setBounds(145, 85, 110, 28);
		userLogin.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel loginLB = new JLabel("Login");
		loginLB.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginLB.setHorizontalAlignment(SwingConstants.CENTER);
		loginLB.setBounds(-18, 0, 122, 39);
		userLogin.getContentPane().add(loginLB);
		
		JLabel typeLB = new JLabel("Type");
		typeLB.setToolTipText("");
		typeLB.setHorizontalAlignment(SwingConstants.CENTER);
		typeLB.setFont(new Font("Tahoma", Font.BOLD, 14));
		typeLB.setBounds(20, 146, 118, 28);
		userLogin.getContentPane().add(typeLB);
		
		final JToggleButton typeBTN = new JToggleButton("Admin");
		typeBTN.setForeground(new Color(255, 255, 255));
		typeBTN.setUI(new MetalToggleButtonUI() {
			@Override
			protected Color getSelectColor() {
				return new Color(44, 102, 110);
			}
		});
		typeBTN.setBackground(new Color(44, 102, 110));
		typeBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeBTN.isSelected()) {
					typeBTN.setText("Angajat");
				}
				else {
					typeBTN.setText("Admin");
				}
			}
		});
		
		usernameLB = new JLabel("Username");
		usernameLB.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLB.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLB.setBounds(20, 85, 118, 28);
		userLogin.getContentPane().add(usernameLB);
		
		passwordLB = new JLabel("Password");
		passwordLB.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLB.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLB.setBounds(20, 115, 118, 28);
		userLogin.getContentPane().add(passwordLB);
		
		JButton signInBTN = new JButton("Sign In");
		signInBTN.setBackground(new Color(240, 237, 238));
		signInBTN.setForeground(new Color(10, 9, 12));
		signInBTN.addActionListener(new ActionListener() {
			@SuppressWarnings({ "deprecation" })
			public void actionPerformed(ActionEvent e) {
				String Username = usernameField.getText().toString();
				String Password = String.valueOf(passwordField.getPassword());
				
				int Type;
				
				if(!typeBTN.isSelected()) {
					Type = 1;
				}
				else Type = 2;
								 
				boolean check_login = false;

				Connection connection;
				try {
					connection = ConnectDB.getConnection();
					try {
						// check user
						String sql = "SELECT name, password, type FROM user_data";
						PreparedStatement stmt = connection.prepareStatement(sql);
						ResultSet results = stmt.executeQuery();
						
						while(results.next()) {
							String uname = results.getString("name");
							String upasswd = results.getString("password");
							int utype = results.getInt("type");

							if(Username.equals(uname) && Password.equals(upasswd) && Type == utype) {
								check_login = true;
								if(Type == 1) {
									AdminPage adm = new AdminPage();
									adm.adminFrame.setVisible(true);
									userLogin.dispose();
								}
								else if (Type == 2) {
									UserPage usr = new UserPage();
									usr.userFrame.setVisible(true);
									userLogin.dispose();
								}
							}
						}
						
						results.close();
						if(!check_login) {
							JOptionPane.showMessageDialog(null, "Username sau parola gresita.");
						}
					} catch (SQLException e1) {
						// handle errors
						System.out.println("erori frt");
						e1.printStackTrace();
					} finally {
						// close conn
						if(connection != null) {
							try {
								connection.close();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
						}
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
		});
		
		signInBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
		signInBTN.setBounds(297, 104, 110, 21);
		userLogin.getContentPane().add(signInBTN);
		
		JButton signUpBTN = new JButton("Sign Up");
		signUpBTN.setForeground(new Color(10, 9, 12));
		signUpBTN.setBackground(new Color(240, 237, 238));
		signUpBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String Username = usernameField.getText().toString();
				String Password = String.valueOf(passwordField.getPassword());
				
				int Type;
				
				if(!typeBTN.isSelected()) {
					Type = 1;
				}
				else Type = 2;
				
				System.out.println(Username + " " + Password);
				
				boolean format = true;
				
				if(Username.isEmpty() || Password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "user and password format arent valid");
					format = false;
				}
				
				if(format) {
					
					boolean check_login = true;
	
					Connection connection;
					try {
						connection = ConnectDB.getConnection();
						try {
							// check user
							String sql = "SELECT name, password, type FROM user_data";
							PreparedStatement stmt = connection.prepareStatement(sql);
							ResultSet results = stmt.executeQuery();
							
							while(results.next()) {
								String uname = results.getString("name");
								String upasswd = results.getString("password");
								int utype = results.getInt("type");
								
								if(Username.equals(uname) && Password.equals(upasswd) && Type == utype) {
									check_login = false;
									JOptionPane.showMessageDialog(null, "Contul exista deja.");
								}
							}
							results.close();
							if(check_login) {
								String sql1 = "INSERT INTO user_data (name, password, type) VALUES (?, ?, ?)";
								PreparedStatement stmt1 = connection.prepareStatement(sql1);
								stmt1.setString(1, Username);
								stmt1.setString(2, Password);
								stmt1.setInt(3, Type);
								stmt1.executeUpdate();
								JOptionPane.showMessageDialog(null, "Cont nou creat");
								
								if(Type == 1) {
									AdminPage adm = new AdminPage();
									adm.adminFrame.setVisible(true);
									userLogin.dispose();
								}
								else if (Type == 2) {
									UserPage usr = new UserPage();
									usr.userFrame.setVisible(true);
									userLogin.dispose();
								}
							}
						} catch (SQLException e1) {
							// handle errors
							System.out.println("erori frt");
							e1.printStackTrace();
						} finally {
							// close conn
							if(connection != null) {
								try {
									connection.close();
								} catch (SQLException e2) {
									e2.printStackTrace();
									}
								}
							}
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}				
				}
			}
		});		
			
		signUpBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
		signUpBTN.setBounds(297, 139, 110, 21);
		userLogin.getContentPane().add(signUpBTN);
		
		typeBTN.setFont(new Font("Tahoma", Font.BOLD, 14));
		typeBTN.setBounds(145, 145, 110, 28);
		userLogin.getContentPane().add(typeBTN);
	}
}
