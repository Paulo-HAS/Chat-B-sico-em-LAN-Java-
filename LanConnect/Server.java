import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static Server instancia;
	private ServerSocket svs;
	private Socket socket;
	private InputStreamReader reader;
	private OutputStreamWriter writer;
	
	private IMensagem i;
	
	//Conexão no padrão Singleton
	public static Server getInstance() {
		if(instancia==null)
			instancia = new Server();
		return instancia;
	}
	
	public void iniciarServidor(IMensagem i) throws IOException {
		this.i = i;
		svs = new ServerSocket(3535);
		System.out.println(">Inicializando conexao.........");
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						socket = svs.accept();
						System.out.println(">Conexao OK.");
						reader = new InputStreamReader(socket.getInputStream());
						writer = new OutputStreamWriter(socket.getOutputStream());
						
						listarMsg();
						break;
					} catch (Exception e) {
						System.err.println(">Erro na conexao!");
						break;
					}
				}	
		}}).start();
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
