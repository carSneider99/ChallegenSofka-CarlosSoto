import javax.swing.JOptionPane;

public class JuegoPrincipal {
	
	public void iniciarJuego(){
		
		CargarPropiedades cp = new CargarPropiedades();
		boolean continuarJuego = true;
		int acumulado = 0;
		for (int nivel = 1; nivel <= 5; nivel++){
			try {
				Pregunta p = cp.getPreguntaPorNivel(nivel);
				String[] options = p.desordenar();
				int puntaje = cp.getPuntajePorNivel(nivel);
			
				int resp = JOptionPane.showOptionDialog(null, "Por " + puntaje + " puntos, responda de forma correcta\n" + p.getPregunta(), "Nivel " + nivel + ", acumulado: " + acumulado, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
				// Que pasa si resp es igual a -1, es decir cierra el dialogo de pregunta
				continuarJuego = p.verificarRespuesta(options[resp]);
				
				if (continuarJuego)
					acumulado += puntaje;
				else{
					System.out.println("Error, respuesta incorrecta, fin del juego");
					break;
				}
				
			} catch (Exception e) {
				System.err.println("Finalización del juego por excepción: " + e.getMessage());
				continuarJuego = false;
				return;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JuegoPrincipal jp = new JuegoPrincipal();
		String[] options = {"Configurar juego", "Iniciar el juego", "Evaluar archivos"};
		
		int resp = JOptionPane.showOptionDialog(null, "Bienvenido, seleccione la funcionalidad:", "Concurso de preguntas y respuestas", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]); 
		switch (resp){
			case 0: 
				jp.configurarJuego();
			break; 
			
			case 1:
				jp.iniciarJuego();
			break;
			
			case 2: 
				jp.evaluarArchivos();
			break;	
		}
	}

	private void evaluarArchivos() {
		// TODO Auto-generated method stub
		
	}

	private void configurarJuego() {
		// TODO Auto-generated method stub
		
	}
}