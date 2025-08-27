package bebecitaua; // 📦 El paquete donde está tu clase

import javax.swing.*;   // Para la interfaz gráfica (JFrame, JButton, etc.)
import java.awt.*;      // Para manejar layouts y diseño
import java.awt.event.*; // Para manejar eventos de los botones

public class Class2 {

    // 🔹 Método de ordenamiento por inserción de MENOR a MAYOR
    public static void insercionAscendente(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {   // recorremos desde el segundo elemento
            int actual = arreglo[i];                 // guardamos el número actual
            int j = i - 1;                           // índice del elemento anterior

            // 🔽 Mueve los elementos mayores una posición adelante
            while (j >= 0 && arreglo[j] > actual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = actual; // insertamos el número en su lugar correcto
        }
    }

    // 🔹 Método de ordenamiento por inserción de MAYOR a MENOR
    public static void insercionDescendente(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            int actual = arreglo[i];
            int j = i - 1;

            // 🔽 Mueve los elementos menores una posición adelante
            while (j >= 0 && arreglo[j] < actual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = actual;
        }
    }

    // 🔹 Método que convierte un arreglo a texto para mostrarlo
    public static String arregloATexto(int[] arreglo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arreglo.length; i++) {
            sb.append(arreglo[i]).append(" ");
        }
        return sb.toString();
    }

    // 🔹 Método principal con interfaz gráfica
    public static void main(String[] args) {
        // Creamos un arreglo de ejemplo (notas de estudiantes)
        int[] notas = {15, 10, 18, 12, 8, 20, 14};

        // 🔹 Ventana principal
        JFrame ventana = new JFrame("Ordenamiento de Notas - Inserción");
        ventana.setSize(400, 250); // tamaño de la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout()); // disposición de los elementos

        // 🔹 Etiqueta para mostrar el arreglo original
        JLabel etiquetaOriginal = new JLabel("Notas originales: " + arregloATexto(notas));

        // 🔹 Etiqueta para mostrar los resultados
        JLabel etiquetaResultado = new JLabel("Resultado: ---");

        // 🔹 Botón para ordenar de menor a mayor
        JButton botonAsc = new JButton("Ordenar Ascendente");

        // 🔹 Botón para ordenar de mayor a menor
        JButton botonDesc = new JButton("Ordenar Descendente");

        // 🔹 Botón para salir
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

        // 🔹 Agregamos todo a la ventana
        ventana.add(etiquetaOriginal);
        ventana.add(botonAsc);
        ventana.add(botonDesc);
        ventana.add(etiquetaResultado);
        ventana.add(botonSalir);

        // Hacemos visible la ventana
        ventana.setVisible(true);
    }
}


