package gui;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class HomePage extends JFrame {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		setSize(300,180);
		setTitle("Fénix 2.0");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JButton b1 = new JButton("Criar um horário");
		b1.setPreferredSize(new Dimension(400, 50));
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateTimeTable ct = new CreateTimeTable();
				ct.setVisible(true);
				dispose();
			}
		});

		JButton b2 = new JButton("Converter horários JSON <-> CSV");
		b2.setPreferredSize(new Dimension(400, 50));
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConvertFiles cf = new ConvertFiles();
				cf.setVisible(true);
				dispose();
			}
		});

		setLayout(new FlowLayout());
		add(b1);
		add(b2);
	}

	public static void main(String[] args) {
		HomePage home = new HomePage();
		home.setVisible(true);
	}
}
