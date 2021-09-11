import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class CargarPropiedades {
	
	private int cantidadNiveles = 5;
	private int[] puntajesPorNivel = new int[cantidadNiveles];
	private Map<Integer,ArrayList<Pregunta>> preguntasPorNivel  = new HashMap<Integer,ArrayList<Pregunta>> () ;
	
	public CargarPropiedades(){
		String[] nameFiles = {"Niveles.txt","Preguntas.txt"};
		String separador = System.getProperty("file.separator");
		
		for (String nameFile : nameFiles){

			String[] partesNameFile = nameFile.split("\\.");
			String rutaFile = System.getProperty("java.class.path").replace("bin","src") + separador + "resources" + separador + nameFile;
			
			cargarArchivo(rutaFile,partesNameFile[0].toLowerCase());
		}
	}

	public void cargarPreguntas (String linea) {
		String[] propiedades = linea.split(";");
		if (propiedades.length != 6){
			// Excepción, no cumple con la longitud requerida para ser una pregunta válida
			System.err.println("La cantidad de parámetros de la pregunta no es válida");
			return;
		}
		
		int nivel = 0;
		try {
			nivel = Integer.parseInt(propiedades[0].trim());
			// Validar que el nivel sea menor a nivelMáximo
		} catch (NumberFormatException e){
			// Error en el nivel
			System.err.println("El nivel debe ser un entero entre 1 y 5, por favor corrija");
			return;
		}
		
		if (!preguntasPorNivel.containsKey(nivel)) {
			preguntasPorNivel.put(nivel, new ArrayList<Pregunta>());
		}
		
		String[] opcionesRespuesta = new String[4];
		for (int i=2; i<propiedades.length; i++)
			opcionesRespuesta[i-2] = propiedades[i].trim();
		
		preguntasPorNivel.get(nivel).add(new Pregunta(propiedades[1].trim(),opcionesRespuesta,propiedades[2].trim()));
	}
	
	public void cargarPuntajes (String linea) {

		String[] puntajes = linea.split(";");

		if (puntajes.length != cantidadNiveles){
			// Excepción dado que no se tiene la cantidad de puntajes adecuados
			System.err.println("Se deben especificar 5 puntajes, por favor corrija");
		}
		
		try {
			for (int i=0; i<cantidadNiveles; i++){
				puntajesPorNivel[i] = Integer.parseInt(puntajes[i].trim());
			}
		} catch (NumberFormatException ex){
			System.err.println("Error, los puntajes deben ser enteros");
			// Excepción generada dado que el nivel no es entero
		}
	}

	public void cargarArchivo(String rutaFile, String caracteristica)
    {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFile),"UTF-8"));
			
			String linea;
			while ((linea = br.readLine()) != null){
				if (linea.length() == 0)
					continue;
				
				if (linea.substring(0,1).equals("#"))
					continue;
				
				if (caracteristica.equals("niveles")){
					cargarPuntajes(linea);
				}else if (caracteristica.equals("preguntas")){
					cargarPreguntas(linea);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("No se encuentra archivo de Inicialización: " + rutaFile);
		} catch (IOException e){
			System.err.println("Error al leer el archivo de inicialización: " + rutaFile);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				System.err.println("Error en la lectura del archivo: " + rutaFile);
			}
		}
    }
    
    public int getPuntajePorNivel(int nivel) throws Exception{
    	if (nivel > 0 && nivel <= cantidadNiveles)
    		return puntajesPorNivel[nivel-1];
    	else{
    		// Excepción, dado que el nivel es mayor al nivel máximo
    		System.err.println("Error en el nivel, el nivel supera el máximo permitido");
    		throw new Exception();
    	}
    }
    
    public Pregunta getPreguntaPorNivel(int nivel) throws Exception{
    	if (preguntasPorNivel.containsKey(nivel)){
    		Random rm = new Random();
    		int tamaño = preguntasPorNivel.get(nivel).size();
    		return preguntasPorNivel.get(nivel).get(rm.nextInt(tamaño));
    	}
    	throw new Exception("No se encuentran preguntas del nivel: " + nivel);	
    }
    
    public void mostrarPreguntas(){
    	for (Entry<Integer, ArrayList<Pregunta>> preguntas : preguntasPorNivel.entrySet()) {
            System.out.println("Nivel: " + preguntas.getKey());
            for (Pregunta p:preguntas.getValue())
            	System.out.println(p);
        }
    }
}