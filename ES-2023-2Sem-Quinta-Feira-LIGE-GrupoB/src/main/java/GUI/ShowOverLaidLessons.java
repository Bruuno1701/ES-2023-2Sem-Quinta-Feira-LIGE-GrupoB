package GUI;

import table.TimeTable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;


public class ShowOverLaidLessons extends JFrame {
	private final TimeTable timeTable;

	public ShowOverLaidLessons() {
		this.timeTable = null;

		setSize(300, 180);
		setTitle("Fénix 2.0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		 addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	                CreateTimeTable ct = new CreateTimeTable();
	                ct.setVisible(true);
	            }
	        });

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
					ShowOverLaidLessons newJanela = new ShowOverLaidLessons(newTimeTable);
					newJanela.setVisible(true);
					dispose();
				}
			}
		});

		panel.add(button1, BorderLayout.NORTH);

		JButton button2 = new JButton("Ver aulas sobrepostas");
		
		button2.addActionListener(e -> {
			if (timeTable != null) {
				text.setText(timeTable.showOverlaidLessons());
			} else {
				text.setText("Por favor, selecione um horário!");
			}
		});
		
		panel.add(button2, BorderLayout.SOUTH);
		
		add(panel);
	}

	public ShowOverLaidLessons(TimeTable timeTable) {
		this.timeTable = timeTable;

		setSize(300, 150);
		setTitle("Aulas sobrepostas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());

		JTextArea text = new JTextArea();
		text.setEditable(false);

		JScrollPane scroll = new JScrollPane(text);

		panel.add(scroll, BorderLayout.CENTER);

		JButton button2 = new JButton("Ver aulas sobrepostas");

		button2.addActionListener(e -> {
			if (timeTable != null) {
				text.setText(timeTable.showOverlaidLessons());
			} else {
				text.setText("Por favor, selecione um horário!");
			}
		});

		panel.add(button2, BorderLayout.SOUTH);

		add(panel);
	}
}
