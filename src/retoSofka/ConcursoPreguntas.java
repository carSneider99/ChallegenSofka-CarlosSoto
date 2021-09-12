package retoSofka;
import javax.swing.JOptionPane;

public class ConcursoPreguntas {
	private boolean ganoJuego;
	private boolean retiroJuego;
	private int 	acumulado;
	private static int cantidadNiveles = 5; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConcursoPreguntas jp = new ConcursoPreguntas();
		String[] options = {"Configurar juego", "Iniciar el juego","Ver Puntuaciones"};
		int resp = JOptionPane.showOptionDialog(null, "Bienvenido, seleccione la funcionalidad:", "Concurso de preguntas y respuestas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
		
		while (resp != -1) {
			switch (resp){
				case 0: 
					jp.configurarJuego();
				break; 
				
				case 1:
					jp.iniciarJuego();
					jp.finalizarJuego();
				break;
				
				case 2:
					jp.mostrarPuntuaciones();
				break;
			}
			resp = JOptionPane.showOptionDialog(null, "Bienvenido, seleccione la funcionalidad:", "Concurso de preguntas y respuestas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
		}
		System.out.println("Gracias por participar");
	}

	// Usado para crear las preguntas del juego
	private void configurarJuego() {
		
		String preguntaGuardar = JOptionPane.showInputDialog("Ingrese la pregunta siguiendo el siguiente formato: N;P;RC;RI;RI;RI\nCon N:Nivel, P: pregunta, RC: Respuesta correcta, RI:Respuesta Incorrecta, por ejemplo  \n1; Ayer fue martes, ¿Qué día será mañana?; Jueves;  Miércoles; Lunes; Viernes");
		while (preguntaGuardar != null){
			String mensaje = "";
			try {
				Pregunta.armarPregunta(preguntaGuardar, cantidadNiveles);
				try {
					String separador = System.getProperty("file.separator");
					String rutaFile = System.getProperty("java.class.path").replace("bin", "src") + separador + "resources" + separador + "Preguntas.txt";
					ControladorJuego.WriteLog(rutaFile, preguntaGuardar, false);
					mensaje = "Pregunta agregada correctamente\n";
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error en el texto Ingresado", JOptionPane.ERROR_MESSAGE);
			}	
			preguntaGuardar = JOptionPane.showInputDialog(mensaje + "Ingrese la pregunta siguiendo el siguiente formato: N;P;RC;RI;RI;RI\nCon N:Nivel, P: pregunta, RC: Respuesta correcta, RI:Respuesta Incorrecta, por ejemplo  \n1; Ayer fue martes, ¿Qué día será mañana?; Jueves;  Miércoles; Lunes; Viernes");
		}
	}
	
	public void iniciarJuego(){
		
		ganoJuego = false;
		retiroJuego = false;
		acumulado = 0;
		
		ControladorJuego cp = new ControladorJuego(cantidadNiveles);
		cp.cargarConfiguracion();
		boolean continuarJuego = true;
		for (int nivel = 1; nivel <= 5; nivel++){
			try {
				Pregunta pre = cp.getPreguntaPorNivel(nivel);
				String[] options = pre.desordenar();
				int puntaje = cp.getPuntajePorNivel(nivel);
			
				int resp = JOptionPane.showOptionDialog(null, "Por " + puntaje + " puntos, responda de forma correcta\n" + pre.getPregunta(), "Nivel " + nivel + ", acumulado: " + acumulado, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
				
				// Al cerrar el JOptionPane, se entiende salida voluntaria
				if (resp == -1){
					retiroJuego = true;
					return;
				}
				
				// Se valida la respuesta del usuario
				continuarJuego = pre.verificarRespuesta(options[resp]);
				if (continuarJuego){
					acumulado += puntaje;
					System.out.println("Respuesta correcta, avanza al siguiente nivel");
				} else{
					System.out.println("Error, respuesta incorrecta, fin del juego");
					acumulado = 0;
					return;
				}
				
			} catch (Exception e) {
				System.err.println("Finalización del juego por excepción: " + e.getMessage());
				System.exit(0);
			}
		}
		ganoJuego = true;
	}
	
	private void finalizarJuego() {
		String mensaje;
		int opcionMostrar = JOptionPane.INFORMATION_MESSAGE;
		
		if (ganoJuego){
			mensaje = "Felicitaciones, ha ganado todas las rondas";
		}else if (retiroJuego) {
			mensaje = "Salida voluntaria, conserva el acumulado";
		} else {
			mensaje = "Respuesta incorrecta, pierde el acumulado";
			opcionMostrar = JOptionPane.WARNING_MESSAGE;
		}
		
		mensaje = mensaje + "\nSu puntuación es: " + acumulado;
		
		if (acumulado > 0){
			String[] options2 = {"Si","No"};
			int resp = JOptionPane.showOptionDialog(null, mensaje + "\n¿Desea guardar el registro?", "Fin del Juego", JOptionPane.DEFAULT_OPTION, opcionMostrar,null, options2, options2[0]);
			
			if (resp == 0){
				String nombre = JOptionPane.showInputDialog("Escribe tu nombre\nSi es menor a 3 caracteres, no se tendrá en cuenta\nSi es mayor a 10 caracteres, se recortará");
				if (nombre.length() >= 10)
					nombre = nombre.substring(0, 10);
				
				if (nombre.length() >= 3){
					String separador = System.getProperty("file.separator");
					String rutaFile = System.getProperty("java.class.path").replace("bin", "src") + separador + "resources" + separador + "HistorialDePuntuaciones.txt";
					String Message  = nombre.replace(";", " ") + ";" + acumulado;
					try {
						ControladorJuego.WriteLog(rutaFile, Message, true);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			}
		} else
			JOptionPane.showMessageDialog(null, mensaje + "\nNo puede guardar su puntuación dado que es nula"); 
	}
	
	private void mostrarPuntuaciones() {
		System.out.println("Fecha\t\t\tNombre\tPuntuación");
		
		ControladorJuego jp = new ControladorJuego();
		String separador = System.getProperty("file.separator");
		String rutaFile = System.getProperty("java.class.path").replace("bin", "src") + separador + "resources" + separador + "HistorialDePuntuaciones.txt";
		
		jp.cargarArchivo(rutaFile,"HistorialDePuntuaciones.txt","puntuacion");
	}
}