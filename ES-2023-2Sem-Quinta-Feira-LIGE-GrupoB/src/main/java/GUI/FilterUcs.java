package GUI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class FilterUcs extends JFrame{
	public FilterUcs() {
		setSize(200,150);
		setTitle("Criar Horário Por UCs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JLabel label = new JLabel("Filtrar as UCs");
		add(label);
	}
}
