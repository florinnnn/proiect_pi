package agentie_imobiliara;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RowDetailsWindow extends JFrame {
    // ... (your existing code)

    public RowDetailsWindow(final Object[] rowData, String photoType, String[] columnNames) {
    	JFrame newFrame = new JFrame("Detalii");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setSize(400, 450);
        //newFrame.getContentPane().setBackground(Color.blue);
        
        Container contentPane1 = newFrame.getContentPane();
        

        JPanel mainPanel = new JPanel(new BorderLayout());
        String path_to_img = null;
        
       if(photoType == "imobil") {
    	   int random_int = (int)Math.floor(Math.random() * 5 + 1);
    	   path_to_img = "imobile/" + String.valueOf(random_int) + ".jpg";
       }

       else if(photoType == "angajat") {
    	   int random_int = (int)Math.floor(Math.random() * 5 + 1);
    	   path_to_img = "angajati/" + String.valueOf(random_int) + ".jpg";
           
       }
       ImageIcon imageIcon = new ImageIcon(path_to_img);
       JLabel imageLabel = new JLabel(imageIcon);
       imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
       mainPanel.add(imageLabel, BorderLayout.PAGE_START); // Add the imageLabel to the top of the panel

        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        for (int i = 0; i < columnNames.length; i++) {
            String labelText = columnNames[i] + ": " + rowData[i];
            JLabel label = new JLabel(labelText);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            labelPanel.add(label);
        }
        //JLabel label = new JLabel(Arrays.toString(rowData));
        //newFrame.add(label);
	    if(photoType == "imobil") {    
        	JLabel hyperlink = new JLabel("Location");
	        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        hyperlink.addMouseListener(new MouseAdapter() {
		
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            try {
		            	System.out.println(rowData[1]);
		            	String[] address = ((String) rowData[1]).split("\\s+");
		            	System.out.println(address);
		            	String address_plus = "";
		            	for (String word : address) {
		            		System.out.println(word);
		            		address_plus = address_plus + word + "+";
		            	}
		            	System.out.println(address_plus);
		                Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/" + address_plus));
		                 
		            } catch (IOException | URISyntaxException e1) {
		                e1.printStackTrace();
		            }
		        }
	        });
	        hyperlink.setHorizontalAlignment(SwingConstants.CENTER);
	        hyperlink.setForeground(Color.BLUE.darker());
	        labelPanel.add(hyperlink);
	    }
        labelPanel.setBackground(new Color(144, 221, 240));
        mainPanel.add(labelPanel, BorderLayout.CENTER); // Add the labelPanel below the image

        
        contentPane1.add(mainPanel);
        
        newFrame.pack();
        

        newFrame.setVisible(true);
    }

    // ... (your existing code)
}


