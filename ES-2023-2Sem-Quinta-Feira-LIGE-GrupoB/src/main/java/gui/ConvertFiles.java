package gui;
import gestaohorarios.TimeTable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
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
	private static final String formatJSON = ".json";
	private static final String formatCSV = ".csv";

	public ConvertFiles() {
		setSize(300,180);
		setTitle("Fénix 2.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
		setLocationRelativeTo(null); 

		JButton buttonToUpload = new JButton("Carregar horário");
		JLabel filepathFromPC = new JLabel(); 
		buttonToUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".json, .csv", "json", "csv");
				fileChooser.setFileFilter(filter); 
				int result = fileChooser.showOpenDialog(ConvertFiles.this); 
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filepathFromPC.setText(selectedFile.getAbsolutePath());
				}
			}
		});


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

				String filepathFromURL = urlToFile.getText();
				Pattern patternJSON = Pattern.compile("^https?://.*\\.json$");
				Pattern patternCSV = Pattern.compile("^https?://.*\\.csv$");

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(ConvertFiles.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					downloadToFolder = fileChooser.getSelectedFile().getPath();
					if (patternJSON.matcher(filepathFromURL).matches() || filepathFromPC.getText().endsWith(formatJSON)) {
						if("".equals(filepathFromPC.getText())) {
							TimeTable t2 = new TimeTable(filepathFromURL, "src/test/resources");
							String filePath = filepathFromURL;
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t2.saveAsCSV(downloadToFolder + "\\" + fileName + formatCSV);
						}
						else {
							TimeTable t = new TimeTable(filepathFromPC.getText());
							String filePath = filepathFromPC.getText();
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t.saveAsCSV(downloadToFolder + "\\" + fileName + formatCSV);
						}
						JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!");
					} else if (patternCSV.matcher(filepathFromURL).matches() || filepathFromPC.getText().endsWith(formatCSV)) {
						if("".equals(filepathFromPC.getText())) {
							TimeTable t2 = new TimeTable(filepathFromURL, "src/test/resources");
							String filePath = filepathFromURL;
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t2.saveAsJSON(downloadToFolder + "\\" + fileName + formatJSON);

						}
						else {
							TimeTable t = new TimeTable(filepathFromPC.getText());
							String filePath = filepathFromPC.getText();
							File file = new File(filePath);
							String fileName = file.getName().split("\\.")[0];
							t.saveAsJSON(downloadToFolder + "\\" + fileName + formatJSON);
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
