package Principal;

import Clases.PedidoBRIDGES;
import Clases.RepartidorBRIDGES;
import Clases.AsignacionBRIDGES;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<PedidoBRIDGES> listaPedidos = new ArrayList<>();
        ArrayList<RepartidorBRIDGES> listaRepartidores = new ArrayList<>();
        ArrayList<AsignacionBRIDGES> listaAsignaciones = new ArrayList<>(); // NUEVA LISTA

        int opcion;
        do {
            System.out.println("\n====== MENÚ PRINCIPAL BRIDGES ======");
            System.out.println("1. Módulo de Pedidos");
            System.out.println("2. Módulo de Repartidores");
            System.out.println("3. Módulo de Asignaciones");
            System.out.println("4. Salir");
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
                    moduloAsignaciones(sc, listaPedidos, listaRepartidores, listaAsignaciones);
                    break;
                case 4:
                    System.out.println("Cerrando sistema BRIDGES...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
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

    // ==================== MÓDULO ASIGNACIONES ====================
    private static void moduloAsignaciones(Scanner sc, ArrayList<PedidoBRIDGES> listaPedidos,
                                           ArrayList<RepartidorBRIDGES> listaRepartidores,
                                           ArrayList<AsignacionBRIDGES> listaAsignaciones) {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE ASIGNACIONES ---");
            System.out.println("1. Crear Asignación (Pedido → Repartidor)");
            System.out.println("2. Mostrar Asignaciones");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    if (listaPedidos.isEmpty() || listaRepartidores.isEmpty()) {
                        System.out.println("Debe haber al menos un pedido y un repartidor registrado.");
                        break;
                    }

                    System.out.println("\nPedidos disponibles:");
                    for (int i = 0; i < listaPedidos.size(); i++) {
                        System.out.println((i + 1) + ". " + listaPedidos.get(i).getCodigo());
                    }
                    System.out.print("Seleccione un pedido: ");
                    int idxPedido = sc.nextInt() - 1;

                    System.out.println("\nRepartidores disponibles:");
                    for (int i = 0; i < listaRepartidores.size(); i++) {
                        System.out.println((i + 1) + ". " + listaRepartidores.get(i).getNombre());
                    }
                    System.out.print("Seleccione un repartidor: ");
                    int idxRepartidor = sc.nextInt() - 1;

                    if (idxPedido >= 0 && idxPedido < listaPedidos.size() &&
                        idxRepartidor >= 0 && idxRepartidor < listaRepartidores.size()) {

                        AsignacionBRIDGES nueva = new AsignacionBRIDGES(
                                listaPedidos.get(idxPedido),
                                listaRepartidores.get(idxRepartidor)
                        );
                        listaAsignaciones.add(nueva);
                        System.out.println("Asignación creada con éxito.");
                    } else {
                        System.out.println("Selección inválida.");
                    }
                    break;

                case 2:
                    if (listaAsignaciones.isEmpty()) {
                        System.out.println("No hay asignaciones registradas.");
                    } else {
                        for (AsignacionBRIDGES a : listaAsignaciones) {
                            a.mostrarAsignacion();
                        }
                    }
                    break;

                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }
}
