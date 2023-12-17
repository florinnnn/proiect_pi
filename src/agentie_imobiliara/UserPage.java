package agentie_imobiliara;

import java.awt.EventQueue;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.opencsv.CSVWriter;


/**
 * Clasa aceasta reprezinta fereastra de User.
 * Foloseste butoane pentru add, delete, edit care
 * efectueaza modificari pe datele din tabela de imobile.
 * Datele sunt afisate pe JTable-ul ferestrei.
 * Buton search care cauta in tabela de imobile dupa
 * detaliile introduse.
 * @author Florin
 */

public class UserPage extends JFrame {
	/**
	 * Contine caracteristicile ferestrei de User.
	 */
	public JFrame userFrame;
	/**
	 * Contine toate butoanele, lable-uri, text field-uri si tabelul ferestrei Admin.
	 */
	private JPanel contentPane;
	/**
	 * Cotine datele imobilelor din DB.
	 */
	private JTable table;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'location',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField locationField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'type',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField typeField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'price',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField priceField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'state',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField stateField;
	/**
	 * Variabila folosita la adaugarea, stergerea, editarea coloanei 'surface',
	 * impreuna cu celelalte variabile JTextField.
	 */
	private JTextField surfaceField;

	/**
	 * Constructorul contine initializarea tuturor variabilelor
	 * si toate functiile ferestrei User.
	 */
	public UserPage() {
		
		userFrame = new JFrame();
		
		
		userFrame = new JFrame();
		userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		userFrame.setBounds(100, 100, 998, 643);
		userFrame.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 221, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		userFrame.getContentPane().add(contentPane);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		idLabel.setBounds(15, 157, 95, 46);
		contentPane.add(idLabel);
		
		final JLabel id2Label = new JLabel("");
		id2Label.setFont(new Font("Tahoma", Font.BOLD, 15));
		id2Label.setForeground(new Color(0, 0, 0));
		id2Label.setBounds(118, 156, 95, 46);
		contentPane.add(id2Label);
		
		JLabel angajatLabel = new JLabel("Angajat");
		angajatLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		angajatLabel.setBounds(25, 23, 153, 60);
		contentPane.add(angajatLabel);
		
		JLabel locationLabel = new JLabel("LOCATION");
		locationLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		locationLabel.setBounds(15, 197, 95, 46);
		contentPane.add(locationLabel);
		
		JLabel typeLabel = new JLabel("TYPE");
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		typeLabel.setBounds(15, 237, 95, 46);
		contentPane.add(typeLabel);
		
		JLabel priceLabel = new JLabel("PRICE");
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		priceLabel.setBounds(15, 277, 95, 46);
		contentPane.add(priceLabel);
		
		JLabel stateLabel = new JLabel("STATE");
		stateLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		stateLabel.setBounds(15, 317, 95, 46);
		contentPane.add(stateLabel);
		
		JLabel surfaceLabel = new JLabel("SURFACE");
		surfaceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		surfaceLabel.setBounds(15, 357, 95, 46);
		contentPane.add(surfaceLabel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(369, 146, 532, 270);
		contentPane.add(scrollPane);
		
		
		final Vector<String> columnNames = new Vector<String>();
		columnNames.add("Id");
		columnNames.add("Location");
		columnNames.add("Type");
		columnNames.add("Price");
		columnNames.add("State");
		columnNames.add("Surface");
		
		Connection connection;
		try {
			connection = ConnectDB.getConnection();
			try {
				String sql = "SELECT * FROM imobile ORDER BY id ASC";
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
					        String location = (String) table.getModel().getValueAt(row, 1);
					        locationField.setText(location);
					        String type = (String) table.getModel().getValueAt(row, 2);
					        typeField.setText(type);
					       
					        BigDecimal price = (BigDecimal) table.getModel().getValueAt(row, 3);
					        priceField.setText(price.toString());
					        String state = (String) table.getModel().getValueAt(row, 4);
					        stateField.setText(state);
					        BigDecimal surface = (BigDecimal) table.getModel().getValueAt(row, 5);
					        surfaceField.setText(surface.toString());
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
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					connection = ConnectDB.getConnection();
					Integer id = 0; 
					if(id2Label.getText().equals(null) || id2Label.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Nu este selectat nici un imobil pentru a fi modificat.");
					}
					else {
						id = Integer.parseInt(id2Label.getText());
						String location = locationField.getText().toString();
						String type = typeField.getText().toString();
						BigDecimal Price1 = BigDecimal.valueOf(Integer.valueOf(priceField.getText()));

						Integer price = Integer.parseInt(priceField.getText());
						String state = stateField.getText().toString();
						BigDecimal Surface1 = BigDecimal.valueOf(Integer.valueOf(surfaceField.getText()));
						Integer surface = Integer.parseInt(surfaceField.getText());
						
						String updateRow = "UPDATE imobile "
								+ "SET location = ?, type = ?,"
								+ " price = ?,"
								+ " state = ?,"
								+ " surface_area = ? "
								+ "WHERE id = ?";
						
						PreparedStatement stmt = connection.prepareStatement(updateRow);
						stmt.setString(1, location);
						stmt.setString(2, type);
						stmt.setInt(3, price);
						stmt.setString(4, state);
						stmt.setInt(5, surface);
						stmt.setInt(6, id);
						stmt.executeUpdate();
						
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						Integer selectedRow = table.getSelectedRow();
						
						model.setValueAt(location, selectedRow, 1);
						model.setValueAt(type, selectedRow, 2);
						model.setValueAt(Price1, selectedRow, 3);
						model.setValueAt(state, selectedRow, 4);
						model.setValueAt(Surface1, selectedRow, 5);
						
						table.setModel(model);
						
						id2Label.setText(null);
						locationField.setText(null);
						typeField.setText(null);
						priceField.setText(null);
						stateField.setText(null);
						surfaceField.setText(null);
						JOptionPane.showMessageDialog(null, "Imobil modificat.");
						
						
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
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Location = locationField.getText().toString();
				String Type = typeField.getText().toString();
				int Price = -1;
				BigDecimal Price1 = null;
				if(!priceField.getText().toString().equals("")) {
					Price = Integer.parseInt(priceField.getText());
					Price1 = BigDecimal.valueOf(Integer.valueOf(priceField.getText()));

				}
				String State = stateField.getText().toString();
				int Surface = -1;
				BigDecimal Surface1 = null;
				if(!surfaceField.getText().toString().equals("")) {
					Surface = Integer.parseInt(surfaceField.getText());
					Surface1 = BigDecimal.valueOf(Integer.valueOf(surfaceField.getText()));

				}				
				if(Location.equals("") || Type.equals("") || Price == -1 || State.equals("") || Surface == -1) {
					JOptionPane.showMessageDialog(null, "Detalii completate incorect!");

				}
				else {
					Connection connection;
					
					try {
						connection = ConnectDB.getConnection();
						try {
							
							String sql1 = "INSERT INTO imobile (location, type, price, state, surface_area) VALUES (?, ?, ?, ?, ?)";
							PreparedStatement stmt1 = connection.prepareStatement(sql1);
							stmt1.setString(1, Location);
							stmt1.setString(2, Type);
							stmt1.setInt(3, Price);
							stmt1.setString(4, State);
							stmt1.setInt(5, Surface);
							stmt1.executeUpdate();
							
							String sql2 = "SELECT * FROM imobile WHERE id=(SELECT MAX(id) FROM imobile)";
							PreparedStatement stmt2 = connection.prepareStatement(sql2);
							ResultSet results1 = stmt2.executeQuery();
							Integer Id = null;
							while(results1.next()) {
								Id = results1.getInt("id");
							}
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							model.addRow(new Object[]{Id, Location, Type, Price1, State, Surface1});
							id2Label.setText(Id.toString());
							id2Label.setText(null);
							locationField.setText(null);
							typeField.setText(null);
							priceField.setText(null);
							stateField.setText(null);
							surfaceField.setText(null);
							JOptionPane.showMessageDialog(null, "Imobil adaugat");
							
						} catch (SQLException e1) {
							// handle errors
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

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row >= 0) {
			        Integer eve = (Integer) table.getModel().getValueAt(row, 0);
					
					String deleteRow = "DELETE FROM imobile WHERE id="+eve;
					
					Connection connection;
					try {
						connection = ConnectDB.getConnection();
						try {
							PreparedStatement stmt = connection.prepareStatement(deleteRow);
							stmt.execute();
							DefaultTableModel model1 = (DefaultTableModel) table.getModel();
							model1.removeRow(row);
							id2Label.setText(null);
							locationField.setText(null);
							typeField.setText(null);
							priceField.setText(null);
							stateField.setText(null);
							surfaceField.setText(null);
							JOptionPane.showMessageDialog(null, "Imobil sters.");
							
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
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer counter = 0;
				String query = "";
				if(!locationField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " location='" + locationField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + "AND location='" + locationField.getText().toString() + "'";
						counter++;
					}
				}
				if(!typeField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " type='" + typeField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + " AND type='" + typeField.getText().toString() + "'";
						counter++;
					}
				}
				if(!priceField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " price=" + Integer.parseInt(priceField.getText());
						counter++;
					}
					else {
						query = query + " AND price=" + Integer.parseInt(priceField.getText());
						counter++;
					}
				}
				if(!stateField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " state='" + stateField.getText().toString() + "'";
						counter++;
					}
					else {
						query = query + " AND state='" + stateField.getText().toString() + "'";
						counter++;
					}
				}
				if(!surfaceField.getText().toString().equals("")) {
					if(counter == 0) {
						query = query + " surface_area=" + Integer.parseInt(surfaceField.getText());
						counter++;
					}
					else {
						query = query + " AND surface_area=" + Integer.parseInt(surfaceField.getText());
						counter++;
					}
				}
				String stmtq = "SELECT * FROM imobile ORDER BY id ASC";
				if(counter > 0) {
					stmtq = "SELECT * FROM imobile WHERE" + query + " ORDER BY id ASC";
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
							locationField.setText(null);
							typeField.setText(null);
							priceField.setText(null);
							stateField.setText(null);
							surfaceField.setText(null);
							table.getColumnModel().getColumn(0).setPreferredWidth(30);
							table.getColumnModel().getColumn(1).setPreferredWidth(200);
							table.getColumnModel().getColumn(2).setPreferredWidth(90);
							table.getColumnModel().getColumn(4).setPreferredWidth(70);
							table.getColumnModel().getColumn(5).setPreferredWidth(50);
							
							JOptionPane.showMessageDialog(null, "Cautare efectuata.");
						}
						else {
							JOptionPane.showMessageDialog(null, "Nu au fost gasite imobile.");

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

		JButton btnLogOff = new JButton("LOG OFF");
		btnLogOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage lgn = new LoginPage();
				lgn.userLogin.setVisible(true);
				userFrame.dispose();
			}
		});
		btnLogOff.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogOff.setBounds(770, 500, 130, 46);
		contentPane.add(btnLogOff);
		
		locationField = new JTextField();
		locationField.setForeground(new Color(255, 255, 255));
		locationField.setBackground(new Color(44, 102, 110));
		locationField.setBounds(118, 210, 96, 19);
		contentPane.add(locationField);
		locationField.setColumns(10);
		
		typeField = new JTextField();
		typeField.setForeground(new Color(255, 255, 255));
		typeField.setBackground(new Color(44, 102, 110));
		typeField.setColumns(10);
		typeField.setBounds(118, 250, 96, 19);
		contentPane.add(typeField);
		
		priceField = new JTextField();
		priceField.setForeground(new Color(255, 255, 255));
		priceField.setBackground(new Color(44, 102, 110));
		priceField.setColumns(10);
		priceField.setBounds(118, 290, 96, 19);
		contentPane.add(priceField);
		
		stateField = new JTextField();
		stateField.setForeground(new Color(255, 255, 255));
		stateField.setBackground(new Color(44, 102, 110));
		stateField.setColumns(10);
		stateField.setBounds(118, 330, 96, 19);
		contentPane.add(stateField);
		
		surfaceField = new JTextField();
		surfaceField.setForeground(new Color(255, 255, 255));
		surfaceField.setBackground(new Color(44, 102, 110));
		surfaceField.setColumns(10);
		surfaceField.setBounds(118, 370, 96, 19);
		contentPane.add(surfaceField);
		
		
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSearch.setBounds(400, 500, 104, 46);
		contentPane.add(btnSearch);
		
		JButton btnClearFields = new JButton("CLEAR FIELDS");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id2Label.setText(null);
				locationField.setText(null);
				typeField.setText(null);
				priceField.setText(null);
				stateField.setText(null);
				surfaceField.setText(null);
			}
		});
		btnClearFields.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClearFields.setBounds(535, 500, 144, 46);
		contentPane.add(btnClearFields);
		
		JButton btnExport = new JButton("Export");
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 15));
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
				        document.save("date_imobile.pdf");
				        document.close();

				        JOptionPane.showMessageDialog(null, "Table data exported to PDF successfully.");

				        
			        } else if(choice == 1) {
			        	// Execute the CSV export code
			        	CSVWriter csvWriter = new CSVWriter(new FileWriter("date_imobile.csv"));
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
			         
			        /*PDDocument document = new PDDocument();
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
			        document.save("date_imobile.pdf");
			        document.close();

			        JOptionPane.showMessageDialog(null, "Table data exported to PDF successfully.");
*/
			        
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
			}
		});
		btnExport.setBounds(585, 437, 94, 46);
		contentPane.add(btnExport);
		
		JButton btnView = new JButton("View");
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
                    String[] columnNames = {"Id", "Location", "Type", "Price", "State", "Surface"};
                	String type = "imobil";
                	new RowDetailsWindow(rowData, type, columnNames);
                }
			}
		});
		btnView.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnView.setBounds(150, 436, 94, 46);
		contentPane.add(btnView);
		
	}
}
