/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bebecitaua;

/**
 *
 * @author USUARIO
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conjunto de notas de estudiantes
        double[] notas = {15.5, 12.0, 18.0, 20.0, 11.5, 16.0, 17.0};

        double suma = 0;
        double mayor = notas[0];
        double menor = notas[0];

        // Recorriendo todo el arreglo con for
        for (int i = 0; i < notas.length; i++) {
            System.out.println("Nota del estudiante " + (i + 1) + ": " + notas[i]);

            // Acumulamos para promedio
            suma += notas[i];

            // Buscamos mayor y menor
            if (notas[i] > mayor) {
                mayor = notas[i];
            }
            if (notas[i] < menor) {
                menor = notas[i];
            }
        }

        double promedio = suma / notas.length;

        // Resultados
        System.out.println("\nResultados:");
        System.out.println("Promedio: " + promedio);
    

    }
}

