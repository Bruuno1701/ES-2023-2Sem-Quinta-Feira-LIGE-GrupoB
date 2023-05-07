package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import table.Lesson;
import table.TimeTable;
import utilities.FileConverter;

public class FilterUcs extends JFrame {
	private List<Lesson> selectedUcs;
	private JCheckBox[] checkBoxes;
	private Set<String> addedUcs = new HashSet<>();
	private String path;
	private TimeTable timetable;

	public FilterUcs() {
		setSize(700, 600);
		setTitle("Criar Horário Por UCs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel boxesPanel = new JPanel(new GridLayout(0, 1));

//	Por alguma razão não funciona:
		JScrollPane scroll = new JScrollPane(boxesPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scroll, BorderLayout.CENTER);

		selectedUcs = new ArrayList<Lesson>(); // lista de aulas
		timetable = null; // que se vai buscar
		try {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".json, .csv", "json", "csv");																					// deixar JSON
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
//				file.FileConverter.csvToJson(file.getAbsolutePath(), file.getAbsolutePath());
				path = file.getAbsolutePath();
				timetable = new TimeTable(path);
//				if (timetable.isCSV()) {
//					timetable.saveAsJSON(file.getAbsolutePath());
//				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}

		selectedUcs = timetable.getLessonsList();
		checkBoxes = new JCheckBox[selectedUcs.size()];

		for (int i = 0; i < selectedUcs.size(); i++) {
			String ucName = selectedUcs.get(i).getUnidadeCurricular();
			if (!addedUcs.contains(ucName)) {
				checkBoxes[i] = new JCheckBox(selectedUcs.get(i).getUnidadeCurricular());
				if (checkBoxes[i] != null) {
					boxesPanel.add(checkBoxes[i]);
					addedUcs.add(ucName);
				}
			}
		}

		mainPanel.add(boxesPanel, BorderLayout.CENTER);

		JButton button = new JButton("Criar Horário");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> ucs = new ArrayList<String>();

				for (JCheckBox b : checkBoxes) {
					if (b != null && b.isSelected())
						ucs.add(b.getText());
				}
				
//				System.out.println(ucs);
				
				if (ucs.isEmpty())
					JOptionPane.showMessageDialog(null, "Por favor, selecione pelo menos 1 UC");
				else {
					TimeTable newTimetable = new TimeTable("Horário");

					try {
						newTimetable = timetable.filterUCs(ucs, path);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro: " + e2.getMessage());
					}

					if (!newTimetable.getLessonsList().isEmpty()) {
						DisplayTimeTable t = new DisplayTimeTable(newTimetable);
						t.setVisible(true);				
					} else {
						JOptionPane.showMessageDialog(null, "Não foram encontradas aulas para as UCs selecionadas");
					}
				}
			}
		});

		mainPanel.add(button, BorderLayout.SOUTH);
		add(mainPanel);
		setVisible(true);
	}

	private class DisplayTimeTable extends JFrame {
		public DisplayTimeTable(TimeTable timetable) {
			setSize(200, 150);
			setTitle("Criar Horário Por UCs");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);

			JPanel panel = new JPanel(new BorderLayout());
			
			JButton openButton = new JButton("Abrir horário filtrado");
			openButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultTableModel model = new DefaultTableModel(
							new Object[][] {},
							new String[] {"Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atribuída à aula", "Lotação da sala"}
							);
					
					java.lang.reflect.Field[] fields = TimeTable.class.getDeclaredFields();
					
					Object[] row = new Object[fields.length];
					
					for (int i = 0; i < fields.length; i++) {
						try {
							fields[i].setAccessible(true);
							row[i] = fields[i].get(timetable);
						}catch (Exception e3) {
							e3.printStackTrace();
						}
					}
					
					model.addRow(row);
					
					JTable table = new JTable(model);
					
					JFrame frame = new JFrame();
					
					frame.add(new JScrollPane(table));
					frame.pack();
					frame.setVisible(true);
				}
			});
			add(openButton, BorderLayout.NORTH);
			

			JButton saveButton = new JButton("Salvar horário");
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//fazer o display
			});
			add(saveButton, BorderLayout.SOUTH);
			add(panel, BorderLayout.CENTER);
		}
	}
}
