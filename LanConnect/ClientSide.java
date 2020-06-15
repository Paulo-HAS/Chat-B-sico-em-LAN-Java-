import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientSide extends JFrame implements ActionListener, IMensagem{

	
	private int width = 800;
	private int height = 600;
	
	private JPanel painel;
	private JPanel painelChat;
	private JPanel painelEntrada;
	private JTextArea logChat;
	private JScrollPane logScroll;
	private JTextField caixaEntrada;
	private JButton serverConnectBtn;
	private JButton enviarBtn;
	
	public ClientSide() {
		super("Cliente");
		
		painel = new JPanel(null);
		painel.setBackground(Color.BLACK);
		
		painelChat = new JPanel(null);
		painelChat.setBackground(Color.DARK_GRAY);
		painelChat.setBounds(0, 0, width, height - 100);
		
		painelEntrada = new JPanel(null);
		painelEntrada.setBackground(Color.CYAN);
		painelEntrada.setBounds(0, painelChat.getHeight(), width, 100);
		
		logChat = new JTextArea();
		logChat.setBackground(Color.DARK_GRAY);
		logChat.setForeground(Color.WHITE);
		logScroll = new JScrollPane(logChat);
		logScroll.setBounds(5, 5, painelChat.getWidth() - 10, painelChat.getHeight() - 10);
		painelChat.add(logScroll);
		
		caixaEntrada = new JTextField();
		caixaEntrada.setBackground(Color.WHITE);
		caixaEntrada.setForeground(Color.BLACK);
		caixaEntrada.setBounds(10, 10 , 500, 30);
		painelEntrada.add(caixaEntrada);
		
		serverConnectBtn = new JButton("Conectar");
		serverConnectBtn.setBackground(Color.LIGHT_GRAY);
		serverConnectBtn.setForeground(Color.WHITE);
		serverConnectBtn.setBounds(painelEntrada.getWidth()-200, 10 , 100, 30);
		serverConnectBtn.addActionListener(this);
		painelEntrada.add(serverConnectBtn);
		
		enviarBtn = new JButton("=>");
		enviarBtn.setBackground(Color.LIGHT_GRAY);
		enviarBtn.setForeground(Color.WHITE);
		enviarBtn.setBounds(painelEntrada.getWidth()-300, 10 , 100, 30);
		enviarBtn.addActionListener(this);
		painelEntrada.add(enviarBtn);
		
		painel.add(painelChat);
		painel.add(painelEntrada);
		
		this.add(painel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClientSide();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == serverConnectBtn) {
			try {
				Client.getInstance().conectar(this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == enviarBtn) {
			try {
				Client.getInstance().enviarMsg(caixaEntrada.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void recebendoMensagem(String msg) {
		logChat.append("SV: " + msg + "\n");
		
	}

}
