import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	
	private static Client instancia;
	private Socket socket;
	private InputStreamReader reader;
	private OutputStreamWriter writer;
	
	private IMensagem i;
	
	public static Client getInstance() {
		if(instancia == null) {
			instancia = new Client();
		}
		return instancia;
			
	}
	
	public void conectar(IMensagem i) throws Exception{
		System.out.println(">Conectando ao servidor......");
		this.i = i;
		socket = new Socket("localhost", 3535);
		System.out.println(">Conectado ao servidor.");
		reader = new InputStreamReader(socket.getInputStream());
		writer = new OutputStreamWriter(socket.getOutputStream());
		
		listarMsg();
	}
	
	public void listarMsg() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					try {
						char[] msg = new char[1024];
						if(reader.read(msg) != -1) {
							String strMsg = new String(msg);
							System.out.println("["+strMsg);
							i.recebendoMensagem(strMsg);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}}).start();
	}
	
	public void enviarMsg(String msg) throws Exception {
		writer.write(msg);
		writer.flush();
	}
}
