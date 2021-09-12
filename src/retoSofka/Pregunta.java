package retoSofka;
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
	
	// Código para validar si un string posee el formato de una pregunta
	public static Pregunta armarPregunta(String linea, int cantidadNiveles) throws Exception{
		String[] propiedades = linea.split(";");
		
		if (propiedades.length != 6){
			// Excepción, no cumple con la longitud requerida para ser una pregunta válida
			throw new Exception("La cantidad de parámetros debe ser 6, por favor corrija");
		}
		
		int nivel = 0;
		try {
			nivel = Integer.parseInt(propiedades[0].trim());
			if (nivel > cantidadNiveles){
				throw new Exception("El nivel debe ser un valor entero entre 1 y " + cantidadNiveles + ", por favor corrija");
			}
		} catch (NumberFormatException e){
			throw new Exception("El nivel debe ser un entero entre 1 y 5, por favor corrija");
		}
		
		String[] opcionesRespuesta = new String[4];
		for (int i=2; i<propiedades.length; i++)
			opcionesRespuesta[i-2] = propiedades[i].trim();
		
		return new Pregunta(propiedades[1].trim(),opcionesRespuesta,propiedades[2].trim());
	}
	
	public String[] desordenar(){
		List<String> opcionesMostrar =  Arrays.asList(opcionesRespuesta);
		Collections.shuffle(opcionesMostrar);	
		return opcionesMostrar.toArray(opcionesRespuesta);
	}
}