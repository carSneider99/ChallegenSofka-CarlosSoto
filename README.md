# ChallegenSofka-CarlosSoto
Repositorio creado para exponer la solución del reto de Sofka University Concurso de preguntas y respuestas. 

Consideraciones: 

1.	Para el funcionamiento del proyecto ChallegenSofka-CarlosSoto, es necesario contar con los archivos Nivelex.txt y Preguntas.txt en la ruta src/resources/. En la carpeta
	EjemploArchivos se encuentran ejemplos de estos. 
2.	El archivo  Niveles.txt debe contener los puntos, premios o dinero de las rondas del juego, separadas por ;
3. 	Dado que cada pregunta tiene 4 opciones de respuestas, 3 erradas y una correcta, se propone el siguiente formato para almacenar las preguntas en el archivo Preguntas.txt 
	Nivel; Pregunta; Respuesta Correcta; 3 respuestas incorrectas, por ejemplo; 3;¿Cuántos ángulos tiene un pentágono?;5;6;3;4. Así, los textos no deben contener ;
	No es importante almacenar las pregunas en orden según el nivel. En el archivo de ejemplo se tienen en orden, dado que así fueron guardadas utilizando el programa realizado. 
4.	A nivel de POO, se realizan 3 clases, la clase Pregunta, ControladorJuego, COncursoPreguntas.
5.	La clase Pregunta continue los atributos necesarios para almacenar las preguntas del juego, además de los métodos validarRespuesta, armarPregunta a partir de un String y 
	por último, el método desordenar, el cual al llamarlo, presentará al usuario las opciones de respuesta en forma aleatoria. 
6.	La clase ControladorJuego, permite cargar la información de los archivos de niveles y preguntas, además de retornar la puntuación de un nivel y una pregunta aleatoria por nivel. 
	Esta clase también valida los niveles y las preguntas, así toda linea que no cumpla las consideraciones para ser una pregunta, no se cargará y se presenta en la terminal el 
	mensaje el error para que se pueda corregir. En cambio, para los niveles, las puntuaciones deben ser números enteros mayores a cero y deberán ser separadas por ; 
	Esta clase también permite escribir en el archivo Pregunta, las preguntas que el usuario quiera guardar en la opción del juego ConfigurarJUego. Además de permitir guardar las 
	puntuaciones de los jugadores. Las puntuaciones se guardará en el siguiente formato: Fecha;Nombre; Puntuación en el archivo HistorialDePuntuaciones.txt, si no existe este 
	archivo se creará.
7. 	La clase ConcursoPreguntas contiene el método principal, método que presenta al usuario la pantalla prinicipal del juego en donde puede seleccionar usando la clase JOptionPane, 
	las opciones de Configurar Juego para guardar preguntas, Iniciar Juego, y ver Puntuaciones. Esta clase posee el atributo cantidadNiveles, el cuál estará inicialmente inicializado
	en 5. 
8.	El programa se finalizará de inmediato si la puntuación del nivel no es mayor a cero, o si no se tienen preguntas de un nivel. La salida voluntaria del jugador es considerada 
	al cerrar el JOptionPane que muestras las opciones de respuesta. 
	Si el jugador pierde, no se le permite guardar su puntuación dado que es nula, si por el contrario gana o tiene una salida voluntaria con puntuación mayor a cero, 
	se guardará la fecha, nombre y puntuación, cabe resaltar que el nombre debe ser de longitud mayor o igual a 3 y menor o igual a 10. Si es menor a 3, no se tomará en cuenta, si
	es mayor a 10, se recortará. 




