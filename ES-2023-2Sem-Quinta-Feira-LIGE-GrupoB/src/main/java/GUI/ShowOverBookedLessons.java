package GUI;

import table.TimeTable;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
//import java.awt.FlowLayout;

public class ShowOverBookedLessons extends JFrame {
	private final TimeTable timeTable;

	public ShowOverBookedLessons() {
		this.timeTable = null;

		setSize(300, 150);
		setTitle("Escolher horário para ver aulas lotadas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);

		JScrollPane scroll = new JScrollPane(text);

		panel.add(scroll, BorderLayout.CENTER);

		JButton button1 = new JButton("Carregar horário");

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();

				int result = file.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File horario = file.getSelectedFile();
					TimeTable newTimeTable = new TimeTable(horario.getAbsolutePath());
					ShowOverBookedLessons newJanela = new ShowOverBookedLessons(newTimeTable);
					newJanela.setVisible(true);
					dispose();
				}
			}
		});

		panel.add(button1, BorderLayout.NORTH);

		JButton button2 = new JButton("Ver aulas lotadas");
		
		button2.addActionListener(e -> {
			if (timeTable != null) {
				text.setText(timeTable.showOverbookedLessons());
			} else {
				text.setText("Por favor, selecione um horário!");
			}
		});
		
		panel.add(button2, BorderLayout.SOUTH);
		
		add(panel);
	}

	public ShowOverBookedLessons(TimeTable timeTable) {
		this.timeTable = timeTable;

		setSize(300, 150);
		setTitle("Aulas lotadas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);

		JScrollPane scroll = new JScrollPane(text);

		panel.add(scroll, BorderLayout.CENTER);

		JButton button2 = new JButton("Ver aulas lotadas");

		button2.addActionListener(e -> {
			if (timeTable != null) {
				text.setText(timeTable.showOverbookedLessons());
			} else {
				text.setText("Por favor, selecione um horário!");
			}
		});

		panel.add(button2, BorderLayout.SOUTH);

		add(panel);
	}
}
