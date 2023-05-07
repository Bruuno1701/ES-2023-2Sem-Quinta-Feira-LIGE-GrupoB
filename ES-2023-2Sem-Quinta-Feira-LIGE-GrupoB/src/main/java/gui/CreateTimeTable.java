package gui;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class CreateTimeTable extends JFrame {
	public CreateTimeTable() {
		setSize(300,180);
		setTitle("Fénix 2.0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HomePage home = new HomePage();
                home.setVisible(true);
            }
        });
		
		JButton b1 = new JButton("Criar Horário Por UCs");
		
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterUcs f = new FilterUcs();
				f.setVisible(true);
			}
		});
		
		JButton b2 = new JButton("Ver aulas lotadas");
		
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ShowOverBookedLessons sb = new ShowOverBookedLessons();
				sb.setVisible(true);
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
		add(b2);
		add(b3);
	}
	
	public static void main(String[] args) {
		CreateTimeTable tt = new CreateTimeTable();
		tt.setVisible(true);
	}
}
