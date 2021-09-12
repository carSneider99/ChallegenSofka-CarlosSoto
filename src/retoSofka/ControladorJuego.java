package retoSofka;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ControladorJuego {
	
	public int cantidadNiveles;
	private int[] puntajesPorNivel;
	private Map<Integer,ArrayList<Pregunta>> preguntasPorNivel  = new HashMap<Integer,ArrayList<Pregunta>> () ;
	
	public ControladorJuego(){}
	
	public ControladorJuego(int cantidadNiveles){
		this.cantidadNiveles = cantidadNiveles;
		puntajesPorNivel = new int[cantidadNiveles];
	}
	
	public void cargarConfiguracion(){
		String[] nameFiles = {"Niveles.txt","Preguntas.txt"};
		String separador = System.getProperty("file.separator");
		
		for (String nameFile : nameFiles){

			String[] partesNameFile = nameFile.split("\\.");
			String rutaFile = System.getProperty("java.class.path").replace("bin", "src") + separador + "resources" + separador + nameFile;
			
			cargarArchivo(rutaFile,nameFile,partesNameFile[0].toLowerCase());
		}
	}

	public void cargarPreguntas (String linea) throws Exception{
		// Si no es válida la pregunta, no se carga
		Pregunta pre = Pregunta.armarPregunta(linea, cantidadNiveles);
		
		String[] propiedades = linea.split(";");
		int nivel = Integer.parseInt(propiedades[0]);
		
		if (!preguntasPorNivel.containsKey(nivel)) {
			preguntasPorNivel.put(nivel, new ArrayList<Pregunta>());
		}
		preguntasPorNivel.get(nivel).add(pre);
	}
	
	public void cargarNiveles (String linea) throws Exception {

		String[] puntajes = linea.split(";");

		if (puntajes.length != cantidadNiveles)
			throw new Exception("Se deben especificar " +  cantidadNiveles + " puntajes, por favor corrija");
		
		try {
			for (int i=0; i<cantidadNiveles; i++)
				puntajesPorNivel[i] = Integer.parseInt(puntajes[i].trim());
		} catch (NumberFormatException ex){
			throw new Exception("Error, los puntajes deben ser enteros");
		}
	}

	public void cargarArchivo(String rutaFile, String file, String caracteristica)
    {
		int contadorLinea = 1;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFile),"UTF-8"));
			String linea;
			while ((linea = br.readLine()) != null){
				if (linea.length() == 0)
					continue;
				
				if (linea.substring(0,1).equals("#"))
					continue;
				
				try{
					if (caracteristica.equals("niveles"))
						cargarNiveles(linea);
					if (caracteristica.equals("preguntas"))
						cargarPreguntas(linea);
					if (caracteristica.equals("puntuacion"))
						mostrarPuntuacion(linea);
				} catch (Exception e){
					System.err.println("Error en la línea " + contadorLinea + " del archivo "
							+ file + ", causada por: " + e.getMessage());
				}
				contadorLinea++;
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
    
    // Método usado para persisitir las preguntas y puntuaciones
    // formatLog: Si se desea guardar fecha y hora en el archivo
    public static void WriteLog(String rutaFile, String Message, boolean formatLog) throws Exception{
    	String Header = "";
        if (formatLog) { 
            String allYear = PadLeft(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),    4, "0");
            String month   = PadLeft(Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1), 2, "0");
            String day     = PadLeft(Integer.toString(Calendar.getInstance().get(Calendar.DATE)),    2, "0");
            String hour   = PadLeft(Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)), 2, "0");
            String minute = PadLeft(Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)), 2, "0");
            
            Header = allYear + "-" + month + "-" + day + " " + hour + ":" + minute + ";";
        }
        
    	Writer fichero = null;
        try {
            fichero = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaFile,true),"UTF-8"));
            fichero.write(Header + Message + "\n");
        } catch (Exception e) {
            throw new Exception("Error al escribir en el archivo del log");
        } finally {
        	fichero.close();
        }
    }
    
    public static String PadLeft (String cadena, int largo, String caracter) {
    	if (cadena.length() < largo ) {
    		largo -= cadena.length();
    		while ( largo-- > 0) {cadena = caracter + cadena;}
        }
    	return cadena;
	}
    
    public void mostrarPuntuacion(String linea) throws Exception{
    	String[] partes = linea.split(";");
    	if (partes.length != 3)
    		throw new Exception("Formato no válido, Debe corregir a: fecha;Nombre;puntuación");
    	
    	System.out.println(partes[0] + "\t" + partes[1] + "\t" + partes[2]);
    }
}