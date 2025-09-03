package Principal;

import Clases.PedidoBRIDGES;
import Clases.RepartidorBRIDGES;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<PedidoBRIDGES> listaPedidos = new ArrayList<>();
        ArrayList<RepartidorBRIDGES> listaRepartidores = new ArrayList<>();

        int opcion;
        do {
            System.out.println("\n====== MENÚ PRINCIPAL BRIDGES ======");
            System.out.println("1. Módulo de Pedidos");
            System.out.println("2. Módulo de Repartidores");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    moduloPedidos(sc, listaPedidos);
                    break;
                case 2:
                    moduloRepartidores(sc, listaRepartidores);
                    break;
                case 3:
                    System.out.println("Cerrando sistema BRIDGES...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }

    // ==================== MÓDULO PEDIDOS ====================
    private static void moduloPedidos(Scanner sc, ArrayList<PedidoBRIDGES> listaPedidos) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE PEDIDOS ---");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Mostrar Pedidos");
            System.out.println("3. Ordenar Pedidos por Código (Inserción)");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código del pedido: ");
                    String codP = sc.nextLine();
                    System.out.print("Ingrese distancia (km): ");
                    double dist = sc.nextDouble();
                    System.out.print("Ingrese peso (kg): ");
                    double peso = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Ingrese prioridad (Normal/Urgente/Crítico): ");
                    String prioridad = sc.nextLine();

                    PedidoBRIDGES nuevoPedido = new PedidoBRIDGES(codP, dist, peso, prioridad);
                    PedidoBRIDGES.registrarPedido(listaPedidos, nuevoPedido);
                    break;

                case 2:
                    PedidoBRIDGES.mostrarPedidos(listaPedidos);
                    break;

                case 3:
                    PedidoBRIDGES.ordenarPorCodigoInsercion(listaPedidos);
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    // ==================== MÓDULO REPARTIDORES ====================
    private static void moduloRepartidores(Scanner sc, ArrayList<RepartidorBRIDGES> listaRepartidores) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE REPARTIDORES ---");
            System.out.println("1. Registrar Repartidor");
            System.out.println("2. Mostrar Repartidores");
            System.out.println("3. Ordenar Repartidores por Capacidad (Inserción)");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código del repartidor: ");
                    String codR = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese capacidad de carga (kg): ");
                    double capacidad = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("¿Está disponible? (true/false): ");
                    boolean disp = sc.nextBoolean();
                    sc.nextLine();

                    RepartidorBRIDGES nuevoRepartidor = new RepartidorBRIDGES(codR, nombre, capacidad, disp);
                    RepartidorBRIDGES.registrarRepartidor(listaRepartidores, nuevoRepartidor);
                    break;

                case 2:
                    RepartidorBRIDGES.mostrarRepartidores(listaRepartidores);
                    break;

                case 3:
                    RepartidorBRIDGES.ordenarPorCapacidadInsercion(listaRepartidores);
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }
}
/*
MAÑANA NO OLVIDAR:

He creado la clase `AsignacionBRIDGES` porque quiero que en mi sistema se pueda
llevar un control de quién entrega qué pedido, algo similar a las misiones en
Death Stranding donde cada paquete tiene un portador asignado.  

En vez de meter esta lógica en el `Main`, preferí crear esta nueva clase para que
mi proyecto se vea más ordenado y cada cosa tenga su espacio. Así puedo tener
una relación clara entre los pedidos y los repartidores.  

La idea es que esta clase guarde:  
- El pedido que se va a entregar.  
- El repartidor que se encargará de llevarlo.  

Además, agregué un método `mostrarAsignacion()` para que desde el menú pueda
mostrar fácilmente qué repartidor lleva qué pedido.  

Aquí dejo el código de la clase que hice:

package Clases;

public class AsignacionBRIDGES {
    private PedidoBRIDGES pedido;
    private RepartidorBRIDGES repartidor;

    // Constructor: recibe un pedido y un repartidor
    public AsignacionBRIDGES(PedidoBRIDGES pedido, RepartidorBRIDGES repartidor) {
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    // Getters para acceder a los datos
    public PedidoBRIDGES getPedido() {
        return pedido;
    }

    public RepartidorBRIDGES getRepartidor() {
        return repartidor;
    }

    // Método para mostrar la asignación
    public void mostrarAsignacion() {
        System.out.println("Pedido " + pedido.getCodigo() +
                           " Repartidor " + repartidor.getNombre());
    }
}
NOTA
Todavía estoy pensando en mejorar esta clase. Siento que me falta algo más, quizá
agregar la fecha de la asignación, el estado de la entrega o incluso un historial.
Por ahora cumple su función básica, pero sé que puedo pulirla más adelante.
*/
