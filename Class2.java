
package s02.s4_restaurante;

import javax.swing.*;   // Para la interfaz gráfica
import java.awt.*;      // Para layouts y componentes gráficos
import java.io.*;       // Para manejo de archivos

public class Class2 {

    private static final int TAMANO_BLOQUE = 50; // Tamaño de cada bloque en memoria

    // Método para dividir y ordenar bloques del archivo
    private static java.util.List<File> dividirEnBloques(String archivoEntrada) throws IOException {
        java.util.List<File> archivosTemporales = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
            java.util.List<String> buffer = new java.util.ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                buffer.add(linea);
                if (buffer.size() == TAMANO_BLOQUE) {
                    archivosTemporales.add(guardarBloqueOrdenado(buffer));
                    buffer.clear();
                }
            }
            if (!buffer.isEmpty()) {
                archivosTemporales.add(guardarBloqueOrdenado(buffer));
            }
        }
        return archivosTemporales;
    }

    // Guardar bloque ordenado en archivo temporal
    private static File guardarBloqueOrdenado(java.util.List<String> bloque) throws IOException {
        bloque.sort(String::compareTo); // Ordenar dentro del bloque
        File temp = File.createTempFile("bloque", ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            for (String linea : bloque) {
                bw.write(linea);
                bw.newLine();
            }
        }
        return temp;
    }

    // Mezcla de los archivos ordenados en uno final
    private static void mezclarArchivos(java.util.List<File> archivos, String archivoSalida) throws IOException {
        java.util.PriorityQueue<EntradaArchivo> pq = new java.util.PriorityQueue<>(
                java.util.Comparator.comparing(e -> e.linea)
        );
        java.util.List<BufferedReader> lectores = new java.util.ArrayList<>();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            for (File f : archivos) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                lectores.add(br);
                String linea = br.readLine();
                if (linea != null) {
                    pq.add(new EntradaArchivo(linea, br));
                }
            }

            while (!pq.isEmpty()) {
                EntradaArchivo ea = pq.poll();
                bw.write(ea.linea);
                bw.newLine();
                String nuevaLinea = ea.lector.readLine();
                if (nuevaLinea != null) {
                    pq.add(new EntradaArchivo(nuevaLinea, ea.lector));
                }
            }
        }

        for (BufferedReader br : lectores) {
            br.close();
        }
    }

    // Clase interna para manejar entradas de archivo
    private static class EntradaArchivo {
        String linea;
        BufferedReader lector;

        EntradaArchivo(String linea, BufferedReader lector) {
            this.linea = linea;
            this.lector = lector;
        }
    }

    // Método que ejecuta la ordenación externa completa
    private static void ordenarArchivo(String archivoEntrada, String archivoSalida) throws IOException {
        java.util.List<File> temporales = dividirEnBloques(archivoEntrada);
        mezclarArchivos(temporales, archivoSalida);

        // Eliminar archivos temporales
        for (File temp : temporales) {
            temp.delete();
        }
    }

    // Método para generar un archivo con números aleatorios
    private static void generarArchivo(String archivo, int cantidad) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < cantidad; i++) {
                int numero = (int) (Math.random() * 1000); // números entre 0 y 999
                bw.write(String.valueOf(numero));
                bw.newLine();
            }
        }
    }

    // Convierte archivo a String para mostrar en la interfaz
    private static String leerArchivoComoTexto(String archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append(" ");
            }
        }
        return sb.toString();
    }

    // Interfaz gráfica
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ordenación Externa - Ejemplo");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        JLabel etiquetaOriginal = new JLabel("Archivo original: ---");
        JLabel etiquetaOrdenado = new JLabel("Archivo ordenado: ---");

        JButton botonGenerar = new JButton("Generar archivo aleatorio");
        JButton botonOrdenar = new JButton("Ordenar con Externa");

        final String archivoEntrada = "entrada.txt";
        final String archivoSalida = "salida.txt";

        // Botón para generar archivo
        botonGenerar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese cantidad de números aleatorios:");
            try {
                int cantidad = Integer.parseInt(input);
                generarArchivo(archivoEntrada, cantidad);
                etiquetaOriginal.setText("Archivo original: " + leerArchivoComoTexto(archivoEntrada));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage());
            }
        });

        // Botón para ordenar usando ordenación externa
        botonOrdenar.addActionListener(e -> {
            try {
                ordenarArchivo(archivoEntrada, archivoSalida);
                etiquetaOrdenado.setText("Archivo ordenado: " + leerArchivoComoTexto(archivoSalida));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error al ordenar: " + ex.getMessage());
            }
        });

        ventana.add(botonGenerar);
        ventana.add(botonOrdenar);
        ventana.add(etiquetaOriginal);
        ventana.add(etiquetaOrdenado);

        ventana.setVisible(true);
    }
}
