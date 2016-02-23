package net.javierjimenez;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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

	String linkImg = "";

	static File contenedor = new File("Img");

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

		if (!contenedor.exists()) {

			contenedor.mkdir();

		}

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

					for (int i = 0; i < img.size(); i++) {

						String url = txtUrl.getText();

						String srcImg = img.get(i).attr("src");
						
						String[] nomsImg = srcImg.split("/");
						String nomImg = nomsImg[nomsImg.length - 1];

						linkImg = crearLink(url, srcImg);

						System.out.println(linkImg);
						
						String directori = contenedor + "/" + nomImg;

						URL conn = new URL(linkImg);

						listImg.addElement(nomImg);

						guardaImatge(conn, directori);

						//System.out.println("Imagen " + nomImg + " añadida.");
					}

					System.out.println("Descarga finalizada!");

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

	private String crearLink(String u, String src) {

		if (!src.contains(u)) {

			if (u.endsWith("/")) {

				return u.substring( u.lastIndexOf('/')+1, u.length() );

			} else {
				return u + src;
			}
		} else {
			return src;
		}
	}

	public static void guardaImatge(URL url, String dir) throws IOException {

		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File(dir));

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

}
