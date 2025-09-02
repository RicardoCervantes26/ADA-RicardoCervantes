package bebecitaua; 

import javax.swing.*;   // Para la interfaz gráfica (JFrame, JButton, etc.)
import java.awt.*;      // Para manejar layouts y diseño
import java.awt.event.*; // Para manejar eventos de los botones
import java.io.*;       // Para manejar archivos
import java.util.*;     // Para listas, colecciones y PriorityQueue

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

    // ---------------- ORDENACIÓN EXTERNA (MERGE SORT EXTERNO) ----------------

    public static void externalSort(String inputFile, String outputFile, int maxMemory) throws IOException {
        List<File> archivosTemporales = new ArrayList<>();
        List<Integer> buffer = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String linea;
        while ((linea = br.readLine()) != null) {
            buffer.add(Integer.parseInt(linea));
            if (buffer.size() == maxMemory) {
                Collections.sort(buffer);
                File temp = guardarBloqueOrdenado(buffer);
                archivosTemporales.add(temp);
                buffer.clear();
            }
        }
        // si queda un bloque pequeño
        if (!buffer.isEmpty()) {
            Collections.sort(buffer);
            File temp = guardarBloqueOrdenado(buffer);
            archivosTemporales.add(temp);
        }
        br.close();

        // Mezclar todos los bloques en el archivo final
        File archivoFinal = new File(outputFile);
        mezclarArchivosOrdenados(archivosTemporales, archivoFinal);
    }

    private static File guardarBloqueOrdenado(List<Integer> buffer) throws IOException {
        File temp = File.createTempFile("bloqueOrdenado", ".txt");
        temp.deleteOnExit();
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        for (int num : buffer) {
            bw.write(num + "\n");
        }
        bw.close();
        return temp;
    }

    private static void mezclarArchivosOrdenados(List<File> archivos, File archivoFinal) throws IOException {
        PriorityQueue<NumeroArchivo> cola = new PriorityQueue<>();

        for (File f : archivos) {
            NumeroArchivo na = new NumeroArchivo(f);
            if (!na.estaVacio()) {
                cola.add(na);
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoFinal));

        while (!cola.isEmpty()) {
            NumeroArchivo actual = cola.poll();
            Integer val = actual.sacarNumero();
            bw.write(val + "\n");

            if (!actual.estaVacio()) {
                cola.add(actual);
            }
        }
        bw.close();
    }

    // Clase auxiliar para manejar archivos ordenados
    static class NumeroArchivo implements Comparable<NumeroArchivo> {
        private BufferedReader br;
        private Integer cache;

        public NumeroArchivo(File file) throws IOException {
            br = new BufferedReader(new FileReader(file));
            recargar();
        }

        private void recargar() throws IOException {
            String linea = br.readLine();
            if (linea == null) cache = null;
            else cache = Integer.parseInt(linea);
        }

        public boolean estaVacio() { return cache == null; }

        public Integer sacarNumero() throws IOException {
            Integer val = cache;
            recargar();
            return val;
        }

        @Override
        public int compareTo(NumeroArchivo otro) {
            return this.cache.compareTo(otro.cache);
        }
    }

    // ---------------- INTERFAZ GRÁFICA ----------------
    public static void main(String[] args) {
        // Creamos un arreglo de ejemplo (notas de estudiantes)
        int[] notas = {15, 10, 18, 12, 8, 20, 14};

        //  Ventana principal
        JFrame ventana = new JFrame("Ordenamiento de Notas - Inserción y Externa");
        ventana.setSize(500, 300); 
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout()); 

        //  Etiqueta para mostrar el arreglo original
        JLabel etiquetaOriginal = new JLabel("Notas originales: " + arregloATexto(notas));

        //  Etiqueta para mostrar los resultados
        JLabel etiquetaResultado = new JLabel("Resultado: ---");

        //  Botón para ordenar de menor a mayor
        JButton botonAsc = new JButton("Ordenar Ascendente");

        //  Botón para ordenar de mayor a menor
        JButton botonDesc = new JButton("Ordenar Descendente");

        //  Botón para probar ordenación externa
        JButton botonExterna = new JButton("Ordenación Externa (archivo)");

        //  Botón para salir
        JButton botonSalir = new JButton("Salir");

        // Acción del botón ascendente
        botonAsc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] copia = notas.clone(); 
                insercionAscendente(copia);  
                etiquetaResultado.setText("Ascendente: " + arregloATexto(copia));
            }
        });

        // Acción del botón descendente
        botonDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] copia = notas.clone(); 
                insercionDescendente(copia); 
                etiquetaResultado.setText("Descendente: " + arregloATexto(copia));
            }
        });

        // Acción del botón externa
        botonExterna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Generar un archivo con datos de prueba
                    String input = "notas.txt";
                    String output = "notas_ordenadas.txt";

                    BufferedWriter bw = new BufferedWriter(new FileWriter(input));
                    for (int nota : notas) {
                        bw.write(nota + "\n");
                    }
                    bw.close();

                    // Ejecutar ordenación externa
                    externalSort(input, output, 3); // bloques de 3 números

                    etiquetaResultado.setText("Archivo ordenado generado: " + output);
                } catch (Exception ex) {
                    etiquetaResultado.setText("Error en ordenación externa: " + ex.getMessage());
                }
            }
        });

        // Acción del botón salir
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        //  Agregamos todo a la ventana
        ventana.add(etiquetaOriginal);
        ventana.add(botonAsc);
        ventana.add(botonDesc);
        ventana.add(botonExterna);
        ventana.add(etiquetaResultado);
        ventana.add(botonSalir);

        // Hacemos visible la ventana
        ventana.setVisible(true);
    }
}

/* Avance personal:
Ya integré el algoritmo de ordenación externa (Merge Sort Externo) en mi proyecto usando NetBeans. 
Primero logré que el programa divida un archivo grande en bloques pequeños, cada uno ordenado en memoria 
con Collections.sort. Después implementé la parte de mezclar todos esos bloques en un archivo final ya ordenado, 
utilizando una PriorityQueue. 
Por ahora estoy haciendo pruebas con archivos de texto simples (entrada.txt y salida.txt) para verificar 
que realmente ordene bien los datos. Todavía me falta refinar algunos detalles, como ajustar el tamaño de bloque, 
manejar archivos más grandes y asegurarme de que los temporales se eliminen bien en todas las pruebas. 
Pero siento que el avance ya es sólido porque la base del algoritmo está implementada y funcionando.
*/




