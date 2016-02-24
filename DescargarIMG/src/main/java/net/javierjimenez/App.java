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

/**
 * 
 * @author alumne1daw
 *
 */
public class App {

	/**
	 * Objeto DefaultListModel donde añadiremos el nombre de las imagenes que
	 * vamos a descargar desde una URL.
	 */
	private DefaultListModel<String> listImg = new DefaultListModel<>();

	/**
	 * Objeto principal del programa que controla la ventana emergente.
	 */
	private JFrame frame;

	/**
	 * Variable String que contendrá los nombres de imagenes que vamos a mostrar
	 * en el JList.
	 */
	private String linkImg = "";

	/**
	 * Objeto de tipo File que generara un directorio para almacenar en el las
	 * imagenes que vayamos a descargar desde la URL.
	 */
	private static File contenedor = new File("Img");

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
	 * Controlador encargado de crear la aplicación.
	 */
	public App() {
		initialize();
	}

	/**
	 * Metodo encargado de iniciar el contenido del frame.
	 */
	private void initialize() {

		/*
		 * Si no existe el contenedor, genera uno en la raiz de donde se ejecute
		 * el programa
		 */
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

		/*
		 * Objeto JTextField donde añadiremos una URL valida (https:// ... )
		 * para descargar las imagenes que esta contiene
		 */
		JTextField txtUrl = new JTextField();
		g.fill = GridBagConstraints.BOTH;

		g.gridwidth = 2;
		g.gridheight = 1;

		g.gridx = 1;
		g.gridy = 0;
		frame.getContentPane().add(txtUrl, g);

		/*
		 * Objeto JButton que inicia la descarga de las imagenes.
		 */
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

					/*
					 * A traves de Jsoup conectamos con la URL que hemos
					 * escrito.
					 */
					Document doc = Jsoup.connect(txtUrl.getText()).get();

					// Seleccionamos todas las etiquetas img del HTML.
					Elements img = doc.select("img");

					for (int i = 0; i < img.size(); i++) {

						String url = txtUrl.getText();

						String srcImg = img.get(i).attr("src");

						String[] nomsImg = srcImg.split("/");
						String nomImg = nomsImg[nomsImg.length - 1];

						/*
						 * Aqui deshecharemos los src que no nos serviran a la
						 * hora de descargar imagenes, como aquellos que no
						 * tienen un formato de imagen (.jpeg, .png, .gif ...) o
						 * redirecciones.
						 */
						if (srcImg.contains(".")) {

							String IMGformat = srcImg.substring(srcImg.lastIndexOf('.') + 1, srcImg.length());

							if (IMGformat.length() < 5) {

								linkImg = crearLink(url, srcImg);

								String directori = contenedor + "/" + nomImg;

								URL conn = new URL(linkImg);

								listImg.addElement(nomImg);

								guardaImatge(conn, directori);

								System.out.println("Imagen " + nomImg + " añadida");
							}
						}
					}

					System.out.println("Descarga finalizada!");
					System.out.println();

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

		/*
		 * Objeto JList que listará los nombres de imagenes que hemos añadido
		 * dentro de listImg
		 */
		JList<String> listIMG = new JList<>(listImg);

		/*
		 * Objeto JScrollPane encargado de mostrar en la ventana emergente el
		 * listado de strings que se encuentran dentro del JList que hemos
		 * creado antes.
		 */
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

	/**
	 * Metodo encargado de generar enlaces validos para poder descargar las
	 * imagenes correctamente.
	 * 
	 * @param u Variable String que contiene la URL que hemos escrito.
	 * @param src variable String que contiene el src de las etiquetas img.
	 * @return 
	 */
	private String crearLink(String u, String src) {
		if (!src.contains("http")) {
			if (u.endsWith("/") && src.startsWith("/")) {
				return u.substring(0, u.lastIndexOf('/')) + src;
			} else if (!u.endsWith("/") && !src.startsWith("/")) {
				return u + "/" + src;
			} else {
				return u + src;
			}
		} else {
			return src;
		}
	}

	/**
	 * Metodo encargado de descargar y guardar las imagenes
	 * que se encuentran dentro de la URL que hemos escrito
	 * y añadirlas en el directorio que le indiquemos.
	 * 
	 * @param url Objeto URL encargado de la conexion con la url que hemos escrito.
	 * @param dir Variable String que contiene el directorio donde 
	 * descargara y nombre del fichero que vamos a descargar.
	 * 
	 * @throws IOException
	 */
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
