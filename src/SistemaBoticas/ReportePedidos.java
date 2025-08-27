
package SistemaBoticas;

import java.util.Scanner;
public class ReportePedidos {

    // Métodos
    public static void imprimirReportePedidos() {
        Scanner input = new Scanner(System.in); // Scanner para leer desde consola

        // Verifica si hay clientes o pedidos registrados
        if (Cliente.totalClientes == 0 || Pedido.totalPedidos == 0) {
            System.out.println("No hay clientes o pedidos registrados.");
            return;
        }

        // Solicita DNI del cliente a buscar
        System.out.print("Ingrese el DNI del cliente: ");
        String dniBuscado = input.nextLine();

        Cliente clienteEncontrado = null;

        // Busca al cliente por su DNI
        for (int i = 0; i < Cliente.totalClientes; i++) {
            if (Cliente.listaClientes[i].dni.equals(dniBuscado)) {
                clienteEncontrado = Cliente.listaClientes[i];
                break;
            }
        }

        // Si no se encuentra el cliente, se muestra mensaje
        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // Cabecera del reporte
        System.out.println("\nReporte de Pedidos del Cliente:");
        System.out.printf("%-12s %-30s %-15s %-15s %-15s\n",
                "Cod.cliente", "Cliente", "Número pedido", "Fecha pedido", "Monto total");
        System.out.println("----------------------------------------------------------------------------------------");

        boolean tienePedidos = false;

        // Recorre todos los pedidos para filtrar los del cliente buscado
        for (int i = 0; i < Pedido.totalPedidos; i++) {
            Pedido pedido = Pedido.listaPedidos[i];
            if (pedido.cliente.dni.equals(dniBuscado)) {
                tienePedidos = true;
                System.out.printf("%-12s %-30s %-15s %-15s S/%.2f\n",
                        pedido.cliente.dni,
                        pedido.cliente.getNombreCompleto(),
                        pedido.numeroPedido,
                        pedido.fecha,
                        pedido.subtotal);
            }
        }

        // Si el cliente no tiene pedidos registrados
        if (!tienePedidos) {
            System.out.println("El cliente no tiene pedidos registrados.");
        }
    }
}