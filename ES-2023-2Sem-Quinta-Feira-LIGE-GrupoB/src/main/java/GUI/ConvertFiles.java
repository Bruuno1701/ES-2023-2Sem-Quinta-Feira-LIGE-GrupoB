package GUI;
import table.TimeTable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JButton b1 = new JButton("Carregar horário");
		JLabel filepathFromPC = new JLabel();
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1) Estou a escolher file");
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".json, .csv", "json", "csv");
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(ConvertFiles.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filepathFromPC.setText(selectedFile.getAbsolutePath());
					System.out.println(selectedFile.toString());
					System.out.println("2) Ficheiro ok, é csv ou json");
				}
			}
		});

		JLabel l = new JLabel("Ou então através de um URL: ");
		JTextField urlToFile = new JTextField(20);

		JButton b2 = new JButton("Converter");
		b2.addActionListener(new ActionListener() {
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
				}

				/*Exemplo do file que vem diretamente do PC do user e é CSV */


				if (patternJSON.matcher(filepathFromURL).matches() || filepathFromPC.getText().endsWith(".json")) {
					if("".equals(filepathFromPC.getText())) {
						TimeTable t2 = new TimeTable(filepathFromURL, "src/test/resources");
						String filePath = filepathFromURL;
						File file = new File(filePath);
						String fileName = file.getName().split("\\.")[0];
						System.out.println("aaaaaaaaaaaaaaaaaaaaa" + fileName);
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
						System.out.println("aaaaaaaaaaaaaaaaaaaaa" + fileName);
						t2.saveAsJSON(downloadToFolder + "\\" + fileName + ".json");

					}
					else {
						TimeTable t = new TimeTable(filepathFromPC.getText());
						String filePath = filepathFromPC.getText();
						File file = new File(filePath);
						String fileName = file.getName().split("\\.")[0];
						t.saveAsJSON(downloadToFolder + "\\" + fileName + ".json");
					}

				}
				else {
					System.err.println("Formato do ficheiro inválido: deve ser .JSON ou .CSV");
					urlToFile.setText("");
				}
			}
		});


		JButton b3 = new JButton("Ver aulas sobrepostas");

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ShowOverLaidLessons sl = new ShowOverLaidLessons();
				sl.setVisible(true);
			}
		});

		setLayout(new FlowLayout());
		add(b1);
		add(filepathFromPC);
		add(l);
		add(urlToFile);
		add(b2);
		//add(b3);
	}

	public static void main(String[] args) {
		ConvertFiles cf = new ConvertFiles();
		cf.setVisible(true);
	}
}
