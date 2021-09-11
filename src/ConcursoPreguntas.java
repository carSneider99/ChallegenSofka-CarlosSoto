import javax.swing.JOptionPane;

public class ConcursoPreguntas {
	private boolean ganoJuego;
	private boolean retiroJuego;
	private int acumulado;
	
	public void iniciarJuego(){
		
		ganoJuego = false;
		retiroJuego = false;
		acumulado = 0;
		
		CargarPropiedades cp = new CargarPropiedades();
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
				if (continuarJuego)
					acumulado += puntaje;
				else{
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConcursoPreguntas jp = new ConcursoPreguntas();
		String[] options = {"Configurar juego", "Iniciar el juego"};
		
		int resp = JOptionPane.showOptionDialog(null, "Bienvenido, seleccione la funcionalidad:", "Concurso de preguntas y respuestas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
		switch (resp){
			case 0: 
				jp.configurarJuego();
			break; 
			
			case 1:
				jp.iniciarJuego();
				jp.finalizarJuego();
			break;

		}
	}

	private void configurarJuego() {
		// TODO Auto-generated method stub
		
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
				String nombre = JOptionPane.showInputDialog("Escribe tu nombre, si es menor a 3 caracteres, no se tendrá en cuenta");
				
			}
		}
		
		JOptionPane.showMessageDialog(null, mensaje + "\nNo puede guardar su puntuación dado que es nula"); 
	}
}