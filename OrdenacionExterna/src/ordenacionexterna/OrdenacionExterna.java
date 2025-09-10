
package ordenacionexterna;
import javax.swing.*;       
import java.awt.FlowLayout; 
import java.awt.event.*;    
import java.io.*;           
import java.util.*;         

public class OrdenacionExterna {

    private static final int TAMANO_BLOQUE = 5; // Defino el tamaño del bloque de la ordenación externa

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ordenación Externa de Números");
        ventana.setSize(500, 350);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        JLabel etiquetaOriginal = new JLabel("Números originales: ---");
        JLabel etiquetaResultado = new JLabel("Resultado ordenado: ---");

        JTextField campoCantidad = new JTextField(10);
        JButton botonGenerar = new JButton("Generar y Ordenar");
        JButton botonFinalizar = new JButton("Finalizar");

        // Acción al presionar "Generar y Ordenar"
        botonGenerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int cantidad = Integer.parseInt(campoCantidad.getText());

                    // Yo genero números aleatorios y los guardo en entrada.txt
                    List<Integer> numeros = generarNumerosAleatorios(cantidad);
                    escribirEnArchivo("entrada.txt", numeros);

                    etiquetaOriginal.setText("Números originales: " + listaATexto(numeros));

                    // Aplico ordenación externa
                    ordenarArchivo(new File("entrada.txt"), new File("salida.txt"));

                    // Leo los datos ya ordenados
                    List<Integer> ordenados = leerDeArchivo("salida.txt");
                    etiquetaResultado.setText("Resultado ordenado: " + listaATexto(ordenados));

                    // Ahora guardo en un archivo "resultado.txt" para comparar
                    guardarComparacion("resultado.txt", numeros, ordenados);

                    JOptionPane.showMessageDialog(ventana, "Resultados guardados en resultado.txt");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage());
                }
            }
        });

        // Acción al presionar "Finalizar"
        botonFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ventana.add(new JLabel("Cantidad de números:"));
        ventana.add(campoCantidad);
        ventana.add(botonGenerar);
        ventana.add(etiquetaOriginal);
        ventana.add(etiquetaResultado);
        ventana.add(botonFinalizar);

        ventana.setVisible(true);
    }

    // Genero números aleatorios
    public static List<Integer> generarNumerosAleatorios(int cantidad) {
        List<Integer> lista = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            lista.add(random.nextInt(100));
        }
        return lista;
    }

    // Escribo una lista en un archivo
    public static void escribirEnArchivo(String nombreArchivo, List<Integer> lista) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Integer num : lista) {
                bw.write(num.toString());
                bw.newLine();
            }
        }
    }

    // Leo números desde un archivo
    public static List<Integer> leerDeArchivo(String nombreArchivo) {
        List<Integer> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                numeros.add(Integer.parseInt(linea));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;
    }

    // Convierto lista a texto
    public static String listaATexto(List<Integer> lista) {
        StringBuilder sb = new StringBuilder();
        for (Integer num : lista) {
            sb.append(num).append(" ");
        }
        return sb.toString();
    }

    // Guardo en resultado.txt los números originales y ordenados
    public static void guardarComparacion(String archivo, List<Integer> originales, List<Integer> ordenados) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write("Números originales:\n");
            bw.write(listaATexto(originales));
            bw.newLine();
            bw.newLine();
            bw.write("Números ordenados:\n");
            bw.write(listaATexto(ordenados));
        }
    }

    // Ordenación externa
    public static void ordenarArchivo(File archivoEntrada, File archivoSalida) throws IOException {
        List<File> archivosTemporales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
            List<String> buffer = new ArrayList<>();
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

        mezclarArchivos(archivosTemporales, archivoSalida);

        for (File temp : archivosTemporales) {
            temp.delete();
        }
    }

    private static File guardarBloqueOrdenado(List<String> bloque) throws IOException {
        Collections.sort(bloque);
        File temp = File.createTempFile("bloque", ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            for (String linea : bloque) {
                bw.write(linea);
                bw.newLine();
            }
        }
        return temp;
    }

    private static void mezclarArchivos(List<File> archivos, File archivoSalida) throws IOException {
        PriorityQueue<EntradaArchivo> pq = new PriorityQueue<>(Comparator.comparing(e -> e.linea));
        List<BufferedReader> lectores = new ArrayList<>();

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

    private static class EntradaArchivo {
        String linea;
        BufferedReader lector;

        EntradaArchivo(String linea, BufferedReader lector) {
            this.linea = linea;
            this.lector = lector;
        }
    }
}