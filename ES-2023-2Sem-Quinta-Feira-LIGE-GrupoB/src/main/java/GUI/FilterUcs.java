package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

import table.Lesson;
import table.TimeTable;

public class FilterUcs extends JFrame {
	private List<Lesson> selectedUcs;
	private JCheckBox[] checkBoxes;
	private Set<String> addedUcs = new HashSet<>();
	private String path;
	private TimeTable timetable;

	public FilterUcs() {
		setSize(500, 400);
		setTitle("Criar Horário Por UCs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel boxesPanel = new JPanel(new GridLayout(0, 2));

		selectedUcs = new ArrayList<Lesson>(); // lista de aulas
		timetable = null; // que se vai buscar
		try {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				timetable = new TimeTable(file.getAbsolutePath());
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
					else
						System.out.println("null");
				}

				if (ucs.isEmpty())
					JOptionPane.showMessageDialog(null, "Por favor, selecione pelo menos 1 UC");
				else {
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showSaveDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						path = file.getAbsolutePath();
					}
					TimeTable newTimetable = new TimeTable("Horário");
					try {
						newTimetable = timetable.filterUCs(ucs, path);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro: " + e2.getMessage());
					}

					DisplayTimeTable t = new DisplayTimeTable(newTimetable);
					t.setVisible(true);
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

			JPanel panel = new JPanel(new GridLayout(0, 1));

			for (Lesson lesson : timetable.getLessonsList()) {
				JLabel label = new JLabel(lesson.getUnidadeCurricular());
				panel.add(label);
			}
			add(panel);
		}
	}
}
