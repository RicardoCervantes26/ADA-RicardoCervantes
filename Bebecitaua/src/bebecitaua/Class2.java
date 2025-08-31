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
   AVANCE PERSONAL DE ORDENACIÓN EXTERNA EN NETBEANS
=============================================================

Ya empecé a implementar la idea de ordenación externa dentro de mi trabajo
en NetBeans. La verdad todavía no lo termino, estoy probando y ajustando
los métodos, pero al menos ya logré que se lean los datos desde un archivo,
se dividan en bloques y se guarden en archivos temporales. 

Ahorita estoy en la parte de la mezcla de archivos, y como son varios,
quiero hacerlo con una PriorityQueue para que siempre vaya sacando el número
más pequeño de cada archivo y armar poco a poco el archivo final ordenado.

Lo que llevo hasta ahora es algo así:

// Método que guarda cada bloque ordenado en un archivo temporal
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

// Método que empieza a mezclar varios archivos ordenados
private static void mezclarArchivosOrdenados(List<File> archivos, File archivoFinal) throws IOException {
    PriorityQueue<NumeroArchivo> cola = new PriorityQueue<>();
    
    // Abro cada archivo temporal y lo guardo en un "buffer"
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
            cola.add(actual); // lo vuelvo a meter si aún tiene datos
        }
    }
    bw.close();
}

// Clase interna que estoy probando para manejar cada archivo ordenado
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

-------------------------------------------------------------
La verdad me emociona porque ya lo estoy viendo funcionar en NetBeans, 
aunque todavía tengo algunos errores con los archivos grandes y la mezcla
final se demora. Igual ya voy avanzando, poco a poco, y lo bueno es que 
ya no es solo una idea sino código que estoy escribiendo y probando.
=============================================================
*/




