package net.javierjimenez;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class App {

	DefaultListModel llistaImg = new DefaultListModel();
	
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
		g.weighty = 0.05;

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
		g.weighty = 0.08;

		g.gridwidth = 1;
		g.gridheight = 1;

		g.gridx = 2;
		g.gridy = 1;
		frame.getContentPane().add(btnDownload, g);
		
		JLabel lblText = new JLabel("Fitxers descarregats:");
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.05;

		g.gridwidth = 1;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 2;
		frame.getContentPane().add(lblText, g);
		
		JLabel lblImatges = new JLabel("");
		lblImatges.setBackground(Color.WHITE);
		lblImatges.setBorder(BorderFactory.createLineBorder(Color.black));
		lblImatges.setOpaque(true);
		g.fill = GridBagConstraints.BOTH;

		g.weightx = 0.5;
		g.weighty = 0.5;

		g.gridwidth = 2;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 3;
		frame.getContentPane().add(lblImatges, g);

	}
}
