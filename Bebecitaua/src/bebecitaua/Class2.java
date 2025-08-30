package bebecitaua; 

import javax.swing.*;   // Para la interfaz gráfica (JFrame, JButton, etc.)
import java.awt.*;      // Para manejar layouts y diseño
import java.awt.event.*; // Para manejar eventos de los botones

public class Class2 {

    //  Método de ordenamiento por inserción de MENOR a MAYOR
    public static void insercionAscendente(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {   // recorremos desde el segundo elemento
            int actual = arreglo[i];                 // guardamos el número actual
            int j = i - 1;                           // índice del elemento anterior

            //  Mueve los elementos mayores una posición adelante
            while (j >= 0 && arreglo[j] > actual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = actual; // insertamos el número en su lugar correcto
        }
    }

    //  Método de ordenamiento por inserción de MAYOR a MENOR
    public static void insercionDescendente(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            int actual = arreglo[i];
            int j = i - 1;

            //  Mueve los elementos menores una posición adelante
            while (j >= 0 && arreglo[j] < actual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = actual;
        }
    }

    //  Método que convierte un arreglo a texto para mostrarlo
    public static String arregloATexto(int[] arreglo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arreglo.length; i++) {
            sb.append(arreglo[i]).append(" ");
        }
        return sb.toString();
    }

    //  Método principal con interfaz gráfica
    public static void main(String[] args) {
        // Creamos un arreglo de ejemplo (notas de estudiantes)
        int[] notas = {15, 10, 18, 12, 8, 20, 14};

        //  Ventana principal
        JFrame ventana = new JFrame("Ordenamiento de Notas - Inserción");
        ventana.setSize(400, 250); // tamaño de la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout()); // disposición de los elementos

        //  Etiqueta para mostrar el arreglo original
        JLabel etiquetaOriginal = new JLabel("Notas originales: " + arregloATexto(notas));

        //  Etiqueta para mostrar los resultados
        JLabel etiquetaResultado = new JLabel("Resultado: ---");

        //  Botón para ordenar de menor a mayor
        JButton botonAsc = new JButton("Ordenar Ascendente");

        //  Botón para ordenar de mayor a menor
        JButton botonDesc = new JButton("Ordenar Descendente");

        // Botón para salir
        JButton botonSalir = new JButton("Salir");

        // Acción del botón ascendente
        botonAsc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] copia = notas.clone(); // clonamos para no modificar el original
                insercionAscendente(copia);  // aplicamos el ordenamiento ascendente
                etiquetaResultado.setText("Ascendente: " + arregloATexto(copia));
            }
        });

        // Acción del botón descendente
        botonDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] copia = notas.clone(); // clonamos para no modificar el original
                insercionDescendente(copia); // aplicamos el ordenamiento descendente
                etiquetaResultado.setText("Descendente: " + arregloATexto(copia));
            }
        });

        // Acción del botón salir
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // cierra la aplicación
            }
        });

        //  Agregamos todo a la ventana
        ventana.add(etiquetaOriginal);
        ventana.add(botonAsc);
        ventana.add(botonDesc);
        ventana.add(etiquetaResultado);
        ventana.add(botonSalir);

        // Hacemos visible la ventana
        ventana.setVisible(true);
    }
}
/*
=============================================================
   MI IDEA PARA IMPLEMENTAR ORDENACIÓN EXTERNA EN JAVA
=============================================================

Yo pienso que, si en algún momento quiero ordenar MUCHOS datos que no 
entran en memoria, lo mejor es trabajar con un "Merge Sort Externo". 
Esto me permite dividir el archivo en partes pequeñas, ordenarlas en 
memoria y después unirlas de nuevo en un archivo final ya ordenado. 

Lo que planeo hacer es más o menos así:

1. Tener un archivo grande (por ejemplo "notas.txt") con miles de números.
2. Leerlos en bloques pequeños que sí puedan entrar en memoria, 
   por ejemplo de 1000 en 1000.
3. Ordenar cada bloque en memoria con Arrays.sort().
4. Guardar cada bloque ordenado en un archivo temporal.
5. Al final, juntar todos esos archivos temporales en un único archivo 
   "notas_ordenadas.txt" usando una mezcla (merge).

Un avance del código que me imagino sería algo así:

// Primero leo el archivo original por bloques
BufferedReader br = new BufferedReader(new FileReader("notas.txt"));
List<Integer> buffer = new ArrayList<>();
List<File> archivosTemporales = new ArrayList<>();
String linea;
int MAX = 1000; // cantidad máxima que quiero manejar en RAM

while ((linea = br.readLine()) != null) {
    buffer.add(Integer.parseInt(linea));

    // Si ya tengo el bloque lleno, lo ordeno y lo guardo
    if (buffer.size() == MAX) {
        Collections.sort(buffer);
        File temp = guardarBloqueOrdenado(buffer); // este método lo crearé
        archivosTemporales.add(temp);
        buffer.clear();
    }
}

// Si quedó un bloque más pequeño al final también lo guardo
if (!buffer.isEmpty()) {
    Collections.sort(buffer);
    File temp = guardarBloqueOrdenado(buffer);
    archivosTemporales.add(temp);
}

// Después de eso pienso hacer otro método que reciba todos los archivos 
// temporales y los vaya mezclando poco a poco, siempre manteniendo el orden.
// Algo así:

File archivoFinal = new File("notas_ordenadas.txt");
mezclarArchivosOrdenados(archivosTemporales, archivoFinal);

// En "guardarBloqueOrdenado" lo que haría es crear un archivo temporal,
// escribir los números ordenados y devolver ese archivo para luego usarlo 
// en la mezcla final. Con "mezclarArchivosOrdenados" usaría una especie 
// de cola de prioridad (PriorityQueue) que siempre me entregue el número 
// más pequeño de cada archivo y así voy construyendo el archivo final 
// ordenado línea por línea.

// Todavía no lo termino, pero la idea es que quede un programa que pueda 
// ordenar un archivo enorme, aunque no entre en la memoria de la computadora. 
// Ya lo tengo más claro, lo que me falta es sentarme a escribir esos métodos 
// extras y probarlos.

=============================================================
*/



