import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pregunta {
	private String pregunta;
	private String[] opcionesRespuesta; 
	private String resCorrecta; 
	
	public Pregunta(String pregunta, String[] opcionesRespuesta, String resCorrecta){
		this.pregunta = pregunta;
		this.opcionesRespuesta = opcionesRespuesta;
		this.resCorrecta = resCorrecta;
	}
	
	public String getPregunta () {
		return pregunta;
	}
	
	public boolean verificarRespuesta(String respuesta) throws Exception{
		if (respuesta.equals(resCorrecta))
			return true;
		else{
			for (String opcionIncorrecta : opcionesRespuesta){
				if (opcionIncorrecta.equals(respuesta))
					return false;
			}
		}
		System.err.println("Respuesta no incluida en las opciones, por favor revise");
		throw new Exception("Respuesta no incluida en las opciones, por favor revise");		
	}
	
	public String[] desordenar(){
		List<String> opcionesMostrar =  Arrays.asList(opcionesRespuesta);
		Collections.shuffle(opcionesMostrar);	
		return opcionesMostrar.toArray(opcionesRespuesta);
	}
	
	public String toString(){
		String mostrar = pregunta; 
		for (String opcion : opcionesRespuesta){
			mostrar = mostrar + "\n" + opcion;
		}
		
		return mostrar;
	}
	
}