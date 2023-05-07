package gui;
import gestaohorarios.TimeTable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class ConvertFiles extends JFrame {
	private static final long serialVersionUID = 1L;
	private String downloadToFolder; 

	public ConvertFiles() {
		setSize(300,180);
		setTitle("Fénix 2.0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ao clicar em Sair, faz apenas dispose da Frame
		setLocationRelativeTo(null); // Para centrar a Frame no ecrã

		JButton buttonToUpload = new JButton("Carregar horário");
		JLabel filepathFromPC = new JLabel(); // Label que mostra o path escolhido acima
		buttonToUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".json, .csv", "json", "csv"); // File escolhido tem de ser .json ou .csv
				fileChooser.setFileFilter(filter); 
				int result = fileChooser.showOpenDialog(ConvertFiles.this); // Abre o file explorer
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filepathFromPC.setText(selectedFile.getAbsolutePath());
					System.out.println("Escolhido o ficheiro " + selectedFile.toString());
				}
			}
		});

		// Fica à escuta que o user feche a frame, e em caso afirmativo retorna à HomePage
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				HomePage home = new HomePage();
				home.setVisible(true);
			}
		});

		JLabel label = new JLabel("Ou então através de um URL: ");
		JTextField urlToFile = new JTextField(20);

		JButton buttonToDownload = new JButton("Converter");
		buttonToDownload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("3) Gogogogogo");
				//Por url:
				String filepathFromURL = urlToFile.getText();
				Pattern patternJSON = Pattern.compile("^https?://.*\\.json$");
				Pattern patternCSV = Pattern.compile("^https?://.*\\.csv$");

				//Escolher diretoria para download:
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(ConvertFiles.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					downloadToFolder = fileChooser.getSelectedFile().getPath();
					System.out.println("4) Diretoria para fazer download escolhida: " + downloadToFolder);

					if (patternJSON.matcher(filepathFromURL).matches() || filepathFromPC.getText().endsWith(".json")) {
						if("".equals(filepathFromPC.getText())) {
							TimeTable t2 = new TimeTable(filepathFromURL, "src/test/resources");
							String filePath = filepathFromURL;
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t2.saveAsCSV(downloadToFolder + "\\" + fileName + ".csv");
						}
						else {
							TimeTable t = new TimeTable(filepathFromPC.getText());
							String filePath = filepathFromPC.getText();
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t.saveAsCSV(downloadToFolder + "\\" + fileName + ".csv");
						}
					} else if (patternCSV.matcher(filepathFromURL).matches() || filepathFromPC.getText().endsWith(".csv")) {
						if("".equals(filepathFromPC.getText())) {
							TimeTable t2 = new TimeTable(filepathFromURL, "src/test/resources");
							String filePath = filepathFromURL;
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t2.saveAsJSON(downloadToFolder + "\\" + fileName + ".json");

						}
						else {
							TimeTable t = new TimeTable(filepathFromPC.getText());
							String filePath = filepathFromPC.getText();
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t.saveAsJSON(downloadToFolder + "\\" + fileName + ".json");
						}
						JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Formato do ficheiro inválido: deve ser .JSON ou .CSV\"");
					}
					urlToFile.setText("");
					filepathFromPC.setText("");
				}
			}
		});

		setLayout(new FlowLayout());
		add(buttonToUpload);
		add(filepathFromPC);
		add(label);
		add(urlToFile);
		add(buttonToDownload);
	}

	public static void main(String[] args) {
		ConvertFiles cf = new ConvertFiles();
		cf.setVisible(true);
	}
}
