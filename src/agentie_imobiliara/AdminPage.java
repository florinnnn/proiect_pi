package agentie_imobiliara;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.opencsv.CSVWriter;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

/**
 * Clasa aceasta reprezinta fereastra de Admin.
 * Foloseste butoane pentru add, delete, edit care
 * efectueaza modificari pe datele din tabela de angajati.
 * Datele sunt afisate pe JTable-ul ferestrei.
 * Buton search care cauta in tabela de angajati dupa
 * detaliile introduse.
 * @author Florin
 */

public class AdminPage extends JFrame {

	/**
	 * Contine caracteristicile ferestrei Admin.
	 */
	public JFrame adminFrame;
	/**
	 * Contine toate butoanele, lable-uri, text field-uri si tabelul ferestrei Admin.
	 */
	private JPanel contentPane;
	/**
	 * Contine datele angajatilor din DB
	 */
	private JTable table;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'name',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField nameField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'job',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField jobField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'salary',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField salaryField;
	/**
	 * Variabila folosita la adaugarea, stergerea editarea coloanei 'address',
	 * impreuna cu celelalte variabile JTextField.
	
	 */
	private JTextField addressField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'phone',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField phoneField;

	/**
	 * Constructorul contine initializarea tuturor variabilelor
	 * si toate functiile ferestrei Admin.
	 */
	public AdminPage() {
		adminFrame = new JFrame();
		adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		adminFrame.setBounds(100, 100, 998, 643);
		adminFrame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 221, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		adminFrame.getContentPane().add(contentPane);
		
		JLabel adminLable = new JLabel("Admin");
		adminLable.setBackground(new Color(255, 0, 0));
		adminLable.setFont(new Font("Tahoma", Font.BOLD, 30));
		adminLable.setBounds(25, 23, 153, 60);
		contentPane.add(adminLable);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		idLabel.setBounds(15, 157, 95, 46);
		contentPane.add(idLabel);
		
		final JLabel id2Label = new JLabel("");
		id2Label.setFont(new Font("Tahoma", Font.BOLD, 15));
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		id2Label.setBounds(118, 157, 95, 46);
		contentPane.add(id2Label);
		
		JLabel nameLabel = new JLabel("NAME");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		nameLabel.setBounds(15, 197, 95, 46);
		contentPane.add(nameLabel);
		
		JLabel jobLabel = new JLabel("JOB");
		jobLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		jobLabel.setBounds(15, 237, 95, 46);
		contentPane.add(jobLabel);
		
		JLabel salaryLabel = new JLabel("SALARY");
		salaryLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		salaryLabel.setBounds(15, 277, 95, 46);
		contentPane.add(salaryLabel);
		
		JLabel addressLabel = new JLabel("ADDRESS");
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		addressLabel.setBounds(15, 317, 95, 46);
		contentPane.add(addressLabel);
		
		JLabel phoneLabel = new JLabel("PHONE");
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		phoneLabel.setBounds(15, 357, 95, 46);
		contentPane.add(phoneLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(369, 146, 532, 270);
		contentPane.add(scrollPane);
		
		
		final Vector<String> columnNames = new Vector<String>();
		columnNames.add("Id");
		columnNames.add("Name");
		columnNames.add("Job");
		columnNames.add("Salary");
		columnNames.add("Address");
		columnNames.add("Phone");
		
		Connection connection;
		try {
			connection = ConnectDB.getConnection();
			try {
				String sql = "SELECT * FROM angajati ORDER BY id ASC";
				PreparedStatement stmt = connection.prepareStatement(sql);
				ResultSet results = stmt.executeQuery();

				Vector<Vector<Object>> data = new Vector<Vector<Object>>();
				
				while (results.next()) {
					Vector<Object> vector = new Vector<Object>();
					for(int col = 1; col <= 6; col++) {
						vector.add(results.getObject(col));
					}
					data.add(vector);
				}
				DefaultTableModel model = new DefaultTableModel(data, columnNames);
				table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				
				ListSelectionModel model1 = table.getSelectionModel();
				model1.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						
						int row = table.getSelectedRow();
						if(row >= 0) {
					        Integer id = (Integer) table.getModel().getValueAt(row, 0);
					        id2Label.setText(id.toString());
					        String name = (String) table.getModel().getValueAt(row, 1);
					        nameField.setText(name);
					        String job = (String) table.getModel().getValueAt(row, 2);
					        jobField.setText(job);
					       
					        BigDecimal salary = (BigDecimal) table.getModel().getValueAt(row, 3);
					        //Integer salary = (Integer) table.getModel().getValueAt(row, 3);
					        salaryField.setText(salary.toString());
					        String address = (String) table.getModel().getValueAt(row, 4);
					        addressField.setText(address);
					        String phone = (String) table.getModel().getValueAt(row, 5);
					        phoneField.setText(phone);
						}
						
					}
				});
			}
			catch (SQLException e) {
				table = new JTable(null, columnNames);
				e.printStackTrace();
			}
			finally {
				if(connection != null) {
					try {
						connection.close();
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		scrollPane.setViewportView(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(153);
		table.getColumnModel().getColumn(5).setPreferredWidth(91);
		
		jobField = new JTextField();
		jobField.setForeground(new Color(240, 237, 238));
		jobField.setBackground(new Color(44, 102, 110));
		jobField.setBounds(118, 250, 96, 19);
		contentPane.add(jobField);
		jobField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setForeground(new Color(240, 237, 238));
		nameField.setBackground(new Color(44, 102, 110));
		nameField.setColumns(10);
		nameField.setBounds(118, 210, 96, 19);
		contentPane.add(nameField);
		
		salaryField = new JTextField();
		salaryField.setForeground(new Color(240, 237, 238));
		salaryField.setBackground(new Color(44, 102, 110));
		salaryField.setColumns(10);
		salaryField.setBounds(118, 290, 96, 19);
		contentPane.add(salaryField);
		
		addressField = new JTextField();
		addressField.setForeground(new Color(240, 237, 238));
		addressField.setBackground(new Color(44, 102, 110));
		addressField.setColumns(10);
		addressField.setBounds(118, 330, 96, 19);
		contentPane.add(addressField);
		
		phoneField = new JTextField();
		phoneField.setForeground(new Color(240, 237, 238));
		phoneField.setBackground(new Color(44, 102, 110));
		phoneField.setColumns(10);
		phoneField.setBounds(118, 370, 96, 19);
		contentPane.add(phoneField);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					connection = ConnectDB.getConnection();
					Integer id = 0; 
					if(id2Label.getText().equals(null) || id2Label.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Nu este selectat nici un angajat pentru a fi modificat.");
					}
					else {
						id = Integer.parseInt(id2Label.getText());
						String name = nameField.getText().toString();
						String job = jobField.getText().toString();
						BigDecimal Salary1 = BigDecimal.valueOf(Integer.valueOf(salaryField.getText()));

						Integer salary = Integer.parseInt(salaryField.getText());
						String address = addressField.getText().toString();
						String phone = phoneField.getText().toString();
						
						String updateRow = "UPDATE angajati "
								+ "SET name = ?, job = ?,"
								+ " salary = ?,"
								+ " address = ?,"
								+ " phone = ? "
								+ "WHERE id = ?";
						
						PreparedStatement stmt = connection.prepareStatement(updateRow);
						stmt.setString(1, name);
						stmt.setString(2, job);
						stmt.setInt(3, salary);
						stmt.setString(4, address);
						stmt.setString(5, phone);
						stmt.setInt(6, id);
						stmt.executeUpdate();
						
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						Integer selectedRow = table.getSelectedRow();
						
						model.setValueAt(name, selectedRow, 1);
						model.setValueAt(job, selectedRow, 2);
						model.setValueAt(Salary1, selectedRow, 3);
						model.setValueAt(address, selectedRow, 4);
						model.setValueAt(phone, selectedRow, 5);
						
						table.setModel(model);
						
						id2Label.setText(null);
						nameField.setText(null);
						jobField.setText(null);
						salaryField.setText(null);
						addressField.setText(null);
						phoneField.setText(null);
						JOptionPane.showMessageDialog(null, "Angajat modificat.");
						
						
						connection.close();
					}
				} catch(SQLException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEdit.setBounds(25, 500, 94, 46);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(table.getSelectedRow());
				
				int row = table.getSelectedRow();
				if(row >= 0) {
			        Integer eve = (Integer) table.getModel().getValueAt(row, 0);
					
					String deleteRow = "DELETE FROM ANGAJATI WHERE id="+eve;
					
					Connection connection;
					try {
						connection = ConnectDB.getConnection();
						try {
							PreparedStatement stmt = connection.prepareStatement(deleteRow);
							stmt.execute();
							DefaultTableModel model1 = (DefaultTableModel) table.getModel();
							model1.removeRow(row);
							id2Label.setText(null);
							nameField.setText(null);
							jobField.setText(null);
							salaryField.setText(null);
							addressField.setText(null);
							phoneField.setText(null);
							JOptionPane.showMessageDialog(null, "Angajat sters.");
							
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
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(275, 500, 94, 46);
		contentPane.add(btnDelete);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Name = nameField.getText().toString();
				String Job = jobField.getText().toString();
				int Salary = -1;
				BigDecimal Salary1 = null;
				if(!salaryField.getText().toString().equals("")) {
					Salary = Integer.parseInt(salaryField.getText());
					Salary1 = BigDecimal.valueOf(Integer.valueOf(salaryField.getText()));
				}
				String Address = addressField.getText().toString();
				String Phone = phoneField.getText().toString();
				
				if(Name.equals("") || Job.equals("") || Salary == -1 || Address.equals("") || Phone.equals("")) {
					JOptionPane.showMessageDialog(null, "Detalii completate incorect!");

				}
				else {
					Connection connection;
					
					try {
						connection = ConnectDB.getConnection();
						try {
							
							String sql = "SELECT name, job, salary, address, phone FROM angajati";
							PreparedStatement stmt = connection.prepareStatement(sql);
							ResultSet results = stmt.executeQuery();
							
							boolean check_ang = true;
							
							while(results.next()) {
								
								String uname = results.getString("name");
								
								if(Name.equals(uname)) {
									JOptionPane.showMessageDialog(null, "Angajatul se afla deja in baza de date.");
									check_ang = false;
								}
								
							}
							results.close();
							
							if(check_ang) {
								String sql1 = "INSERT INTO angajati (name, job, salary, address, phone) VALUES (?, ?, ?, ?, ?)";
								PreparedStatement stmt1 = connection.prepareStatement(sql1);
								stmt1.setString(1, Name);
								stmt1.setString(2, Job);
								stmt1.setInt(3, Salary);
								stmt1.setString(4, Address);
								stmt1.setString(5, Phone);
								stmt1.executeUpdate();
								
								String sql2 = "SELECT * FROM angajati WHERE id=(SELECT MAX(id) FROM angajati)";
								PreparedStatement stmt2 = connection.prepareStatement(sql2);
								ResultSet results1 = stmt2.executeQuery();
								Integer Id = null;
								while(results1.next()) {
									Id = results1.getInt("id");
								}
								DefaultTableModel model = (DefaultTableModel) table.getModel();
								model.addRow(new Object[]{Id, Name, Job, Salary1, Address, Phone});
								id2Label.setText(Id.toString());
								id2Label.setText(null);
								nameField.setText(null);
								jobField.setText(null);
								salaryField.setText(null);
								addressField.setText(null);
								phoneField.setText(null);
								JOptionPane.showMessageDialog(null, "angajat adaugat");
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
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAdd.setBounds(150, 500, 94, 46);
		contentPane.add(btnAdd);
		contentPane.add(id2Label);
		
		JButton btnLogOff = new JButton("LOG OFF");
		btnLogOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage lgn = new LoginPage();
				lgn.userLogin.setVisible(true);
				adminFrame.dispose();
			}
		});
		btnLogOff.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogOff.setBounds(771, 500, 130, 46);
		contentPane.add(btnLogOff);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer counter = 0;
				String query = "";
				if(!nameField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " name='" + nameField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + "AND name='" + nameField.getText().toString() + "'";
						counter++;
					}
				}
				if(!jobField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " job='" + jobField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + " AND job='" + jobField.getText().toString() + "'";
						counter++;
					}
				}
				if(!salaryField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " salary=" + Integer.parseInt(salaryField.getText());
						counter++;
					}
					else {
						query = query + " AND salary=" + Integer.parseInt(salaryField.getText());
						counter++;
					}
				}
				if(!addressField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " address='" + addressField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + " AND address='" + addressField.getText().toString() + "'";
						counter++;
					}
				}
				if(!phoneField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " phone='" + phoneField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + " AND phone='" + phoneField.getText().toString() + "'";
						counter++;
					}
				}
				String stmtq = "SELECT * FROM angajati ORDER BY id ASC";
				if(counter > 0) {
					stmtq = "SELECT * FROM angajati WHERE" + query + " ORDER BY id ASC";
				}
				Connection connection;
				try {
					connection = ConnectDB.getConnection();
					try {
						PreparedStatement stmt = connection.prepareStatement(stmtq);
						ResultSet results = stmt.executeQuery();

						
						if(results.isBeforeFirst()) {
							
							Vector<Vector<Object>> data = new Vector<Vector<Object>>();
							
							while (results.next()) {
								Vector<Object> vector = new Vector<Object>();
								for(int col = 1; col <= 6; col++) {
									vector.add(results.getObject(col));
								}
								data.add(vector);
							}
							DefaultTableModel model = new DefaultTableModel(data, columnNames);
							table.setModel(model);
							id2Label.setText(null);
							nameField.setText(null);
							jobField.setText(null);
							salaryField.setText(null);
							addressField.setText(null);
							phoneField.setText(null);
							table.getColumnModel().getColumn(0).setPreferredWidth(30);
							table.getColumnModel().getColumn(1).setPreferredWidth(90);
							table.getColumnModel().getColumn(2).setPreferredWidth(90);
							table.getColumnModel().getColumn(4).setPreferredWidth(153);
							table.getColumnModel().getColumn(5).setPreferredWidth(91);
							
							JOptionPane.showMessageDialog(null, "Cautare efectuata.");
						}
						else {
							JOptionPane.showMessageDialog(null, "Nu au fost gasiti angajati.");
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
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSearch.setBounds(400, 500, 104, 46);
		contentPane.add(btnSearch);
		
		JButton btnClearFields = new JButton("CLEAR FIELDS");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id2Label.setText(null);
				nameField.setText(null);
				jobField.setText(null);
				salaryField.setText(null);
				addressField.setText(null);
				phoneField.setText(null);
			}
		});
		btnClearFields.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClearFields.setBounds(535, 500, 144, 46);
		contentPane.add(btnClearFields);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// code to export data from JTable

					try {
			    	
			    	String[] options = {"PDF", "CSV"};
			        int choice = JOptionPane.showOptionDialog(null, "Select export type:", "Export", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);


			        if(choice == 0) {
			        	PDDocument document = new PDDocument();
				        PDPage page = new PDPage();
				        document.addPage(page);

				        PDPageContentStream contentStream = new PDPageContentStream(document, page);

				        int rows = table.getRowCount();
				        int cols = table.getColumnCount();

				        float margin = 10;
				        float yStart = page.getMediaBox().getHeight() - margin;
				        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				        float tableHeight = table.getRowHeight() * (rows + 1);

				        float yPosition = yStart;

				        float marginB = 70;
				        float cellMargin = 5f;

				     // Draw table headers
				        float nextyStart = yStart - tableHeight - marginB;
				        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 15);
				        float nextxStart = margin + cellMargin;
			            
				        for (int i = 0; i < cols; i++) {
				            float colWidth = table.getColumnModel().getColumn(i).getWidth();
				            if(colWidth == 200.0) {
				            	colWidth = (float) 150.0;
				            }
				            else if(colWidth == 90.0) {
				            	colWidth = (float) 120.0;
				            }
				            else if(colWidth == 69.0) {
				            	colWidth = (float) 100.0;
				            }
				            //nextyStart -= table.getRowHeight();

				            contentStream.beginText();
				            contentStream.newLineAtOffset(nextxStart, nextyStart);
				            contentStream.showText(table.getColumnName(i));
				            contentStream.endText();
				            nextxStart += colWidth + cellMargin;
				        }

				     // Draw table content
				        for (int i = 0; i < rows; i++) {
				            float nextyStartContent = yStart - tableHeight - marginB - (i + 1) * table.getRowHeight();
				            float nextxStart1 = margin + cellMargin;  // Reset x-coordinate for each row

				            for (int j = 0; j < cols; j++) {
				                float colWidth = table.getColumnModel().getColumn(j).getWidth();
				                
				                if(colWidth == 200.0) {
					            	colWidth = (float) 150.0;
					            }
					            else if(colWidth == 90.0) {
					            	colWidth = (float) 120.0;
					            }
					            else if(colWidth == 69.0) {
					            	colWidth = (float) 100.0;
					            }
				                
				                String text = table.getValueAt(i, j).toString();
				                contentStream.beginText();
				                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 15);
				                contentStream.newLineAtOffset(nextxStart1, nextyStartContent);
				                contentStream.showText(text);
				                contentStream.endText();

				                nextxStart1 += colWidth + cellMargin;  // Increment x-coordinate for the next cell
				            }
				        }

				        contentStream.close();

				        // Save the document to a file
				        document.save("date_angajati.pdf");
				        document.close();

				        JOptionPane.showMessageDialog(null, "Table data exported to PDF successfully.");

				        
			        } else if(choice == 1) {
			        	// Execute the CSV export code
			        	CSVWriter csvWriter = new CSVWriter(new FileWriter("date_angajati.csv"));
			        	String[] headers = {"Id", "Location", "Type", "Price", "State", "Surface"};
			        	csvWriter.writeNext(headers);

			        	for (int i = 0; i < table.getRowCount(); i++) {
			        	    String[] rowData = new String[table.getColumnCount()];
			        	    for (int j = 0; j < table.getColumnCount(); j++) {
			        	        rowData[j] = table.getValueAt(i, j).toString();
			        	    }
			        	    csvWriter.writeNext(rowData);
			        	}

			        	csvWriter.close();
			        	JOptionPane.showMessageDialog(null, "Table data exported to CSV successfully.");
			        }
			         
			        
			        
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
			}
		});
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnExport.setBounds(585, 436, 94, 46);
		contentPane.add(btnExport);
		
		JButton btnView = new JButton("VIEW");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
                // Open a new window or perform any action based on the selected row
                // Example: Open a new window
                if (selectedRow != -1) {
                	Object[] rowData = new Object[table.getColumnCount()]; // Assuming table is the name of your JTable
                	for (int i = 0; i < table.getColumnCount(); i++) {
                	    rowData[i] = table.getValueAt(selectedRow, i);
                	}
                	String[] columnNames = {"Id", "Name", "Job", "Salary", "Address", "Phone"};
                	String type = "angajat";
                	new RowDetailsWindow(rowData, type, columnNames);
                
                }
			}
		});
		btnView.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnView.setBounds(150, 436, 94, 46);
		contentPane.add(btnView);

	}
}
