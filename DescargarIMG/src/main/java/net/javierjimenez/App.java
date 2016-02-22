package net.javierjimenez;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App {

	private DefaultListModel<String> listImg = new DefaultListModel<>();
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblUrl = new JLabel("URL:", SwingConstants.CENTER);

		lblUrl.setOpaque(true);
		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.01;

		g.gridwidth = 1;
		g.gridheight = 1;

		g.gridx = 0;
		g.gridy = 0;
		frame.getContentPane().add(lblUrl, g);

		JTextField txtUrl = new JTextField();
		g.fill = GridBagConstraints.BOTH;

		g.gridwidth = 2;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 0;
		frame.getContentPane().add(txtUrl, g);

		JButton btnDownload = new JButton("Descarregar");
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.1;

		g.gridwidth = 1;
		g.gridheight = 1;

		g.gridx = 2;
		g.gridy = 1;
		frame.getContentPane().add(btnDownload, g);
		
		btnDownload.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Document doc = Jsoup.connect(txtUrl.getText()).get();
					
					Elements img = doc.select("img");
					
					for(int i = 0; i < img.size(); i++){	
						listImg.addElement(img.get(i).attr("src"));	
					}
					
					txtUrl.setText("");
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		});
		
		JLabel lblText = new JLabel("Fitxers descarregats:");
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.01;

		g.gridwidth = 1;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 2;
		frame.getContentPane().add(lblText, g);
		
		JList<String> listIMG = new JList<>(listImg);
		
		JScrollPane listScroll = new JScrollPane(listIMG);
		listScroll.setBackground(Color.WHITE);
		listScroll.setBorder(BorderFactory.createLineBorder(Color.black));
		
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.5;

		g.gridwidth = 2;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 3;
		
		frame.getContentPane().add(listScroll, g);

	}
}
