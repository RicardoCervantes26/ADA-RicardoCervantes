
package SistemaBoticas;
import java.io.*;

public class Pedido {

    // Atributos del pedido
    String numeroPedido, fecha;
    Cliente cliente;
    Producto producto;
    int cantidad;
    double subtotal;

    // Arreglo estático para pedidos
    public static Pedido[] listaPedidos = new Pedido[100];
    public static int totalPedidos = 0;

    // Ruta del archivo
    private static final String RUTA = "pedidos.txt";

    // Constructor
    public Pedido(String numeroPedido, String fecha, Cliente cliente, Producto producto, int cantidad) {
        this.numeroPedido = numeroPedido;
        this.fecha = fecha;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = cantidad * producto.precio;
    }

    // Muestra resumen del pedido
    @Override
    public String toString() {
        String datos = "";
        datos = datos.concat("Pedido N°: " + numeroPedido + ", ");
        datos = datos.concat("Fecha: " + fecha + ", ");
        datos = datos.concat("Cliente: " + cliente.getNombreCompleto() + ", ");
        datos = datos.concat("Producto: " + producto.nombre + ", ");
        datos = datos.concat("Cantidad: " + cantidad + ", ");
        datos = datos.concat("Subtotal: S/." + subtotal);
        return datos;
    }

    // ------------------------
    // MÉTODOS DE ARCHIVO
    // ------------------------

    // Guardar nuevo pedido en archivo
    public static void guardarPedido(Pedido pedidoNuevo) {
        try (BufferedWriter leer = new BufferedWriter(new FileWriter(RUTA, true))) {
            leer.write(pedidoNuevo.numeroPedido + ";" + pedidoNuevo.fecha + ";" + pedidoNuevo.cliente.dni + ";" +
                         pedidoNuevo.producto.codigo + ";" + pedidoNuevo.cantidad + ";" + pedidoNuevo.subtotal);
            leer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    // Cargar pedidos desde archivo
    public static void cargarPedidos() {
        try (BufferedReader leer = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = leer.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    String num = partes[0];
                    String fecha = partes[1];
                    String dniCliente = partes[2];
                    String codProducto = partes[3];
                    int cantidad = Integer.parseInt(partes[4]);

                    Cliente cliente = buscarClientePorDNI(dniCliente);
                    Producto producto = buscarProductoPorCodigo(codProducto);

                    if (cliente != null && producto != null) {
                        listaPedidos[totalPedidos++] = new Pedido(num, fecha, cliente, producto, cantidad);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // No hay archivo aún, no hacer nada
        } catch (IOException e) {
            System.out.println("Error al leer pedidos: " + e.getMessage());
        }
    }

    // Buscar cliente por DNI
    private static Cliente buscarClientePorDNI(String dni) {
        for (int i = 0; i < Cliente.totalClientes; i++) {
            if (Cliente.listaClientes[i].dni.equals(dni)) {
                return Cliente.listaClientes[i];
            }
        }
        return null;
    }

    // Buscar producto por código
    private static Producto buscarProductoPorCodigo(String cod) {
        for (int i = 0; i < Producto.totalProductos; i++) {
            if (Producto.listaProductos[i].codigo.equals(cod)) {
                return Producto.listaProductos[i];
            }
        }
        return null;
    }
}