
package SistemaBoticas;

import java.util.Scanner;

public class SistemaBoticas {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Cargar información desde archivos al iniciar
        Cliente.cargarClientes();
        Producto.cargarProductos();
        Pedido.cargarPedidos();

        int opcion;
        do {
            // Menú principal
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Módulo Clientes");
            System.out.println("2. Módulo Productos");
            System.out.println("3. Registrar Pedido");
            System.out.println("4. Imprimir Pedidos");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            opcion = input.nextInt(); input.nextLine();

            // Ejecuta la opción seleccionada
            switch (opcion) {
                case 1:
                    moduloClientes();
                    break;
                case 2:
                    moduloProductos();
                    break;
                case 3:
                    registrarPedido();
                    break;
                case 4:
                    ReportePedidos.imprimirReportePedidos();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // Menú y operaciones para clientes
    public static void moduloClientes() {
        Scanner input = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- MÓDULO CLIENTES ---");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");
            opcion = input.nextInt(); input.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        // Registro de cliente con validaciones
                        System.out.print("DNI (8 dígitos numéricos): ");
                        String dni = input.nextLine();
                        if (!dni.matches("\\d{8}")) {
                            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos numéricos.");
                        }
                        if (Cliente.buscarPorDni(dni) != null) {
                            throw new IllegalArgumentException("Ya existe un cliente con ese DNI.");
                        }

                        System.out.print("Nombres: ");
                        String nombres = input.nextLine();

                        System.out.print("Apellidos: ");
                        String apellidos = input.nextLine();

                        System.out.print("Dirección: ");
                        String direccion = input.nextLine();

                        System.out.print("Distrito: ");
                        String distrito = input.nextLine();

                        System.out.print("Correo: ");
                        String correo = input.nextLine();
                        if (!correo.contains("@")) {
                            throw new IllegalArgumentException("Correo inválido. Debe contener '@'.");
                        }

                        System.out.print("Celular (9 dígitos): ");
                        String celular = input.nextLine();
                        if (!celular.matches("\\d{9}")) {
                            throw new IllegalArgumentException("El número celular debe tener 9 dígitos.");
                        }
                        if (Cliente.buscarPorCelular(celular) != null) {
                            throw new IllegalArgumentException("Ya existe un cliente con ese número de celular.");
                        }

                        Cliente cliente1 = new Cliente(dni, nombres, apellidos, direccion, distrito, correo, celular);
                        Cliente.listaClientes[Cliente.totalClientes++] = cliente1;
                        Cliente.guardarCliente(cliente1);
                        System.out.println("Cliente registrado correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                    case 2:
                    System.out.print("Ingrese el DNI del cliente que desea buscar: ");
                    String dniBuscado = input.nextLine();
                    Cliente clienteEncontrado = Cliente.buscarPorDni(dniBuscado);
                    if (clienteEncontrado != null) {
                        System.out.println("Cliente encontrado:");
                        System.out.println(clienteEncontrado);
                    } else {
                        System.out.println("No se encontró un cliente con ese DNI.");
                    }
                    break;

                case 3:
                    // Modificación de datos del cliente
                    System.out.print("DNI del cliente a modificar: ");
                    String dniModificar = input.nextLine();
                    Cliente clienteModificar = Cliente.buscarPorDni(dniModificar);
                    if (clienteModificar != null) {
                        try {
                            System.out.print("Nuevos nombres: ");
                            clienteModificar.nombres = input.nextLine();

                            System.out.print("Nuevos apellidos: ");
                            clienteModificar.apellidos = input.nextLine();

                            System.out.print("Nueva dirección: ");
                            clienteModificar.direccion = input.nextLine();

                            System.out.print("Nuevo distrito: ");
                            clienteModificar.distrito = input.nextLine();

                            System.out.print("Nuevo correo: ");
                            String nuevoCorreo = input.nextLine();
                            if (!nuevoCorreo.contains("@")) {
                                throw new IllegalArgumentException("Correo inválido. Debe contener '@'.");
                            }
                            clienteModificar.correo = nuevoCorreo;

                            System.out.print("Nuevo celular (9 dígitos): ");
                            String nuevoCelular = input.nextLine();
                            if (!nuevoCelular.matches("\\d{9}")) {
                                throw new IllegalArgumentException("El número celular debe tener 9 dígitos.");
                            }
                            Cliente Cliente2 = Cliente.buscarPorCelular(nuevoCelular);
                            if (Cliente2 != null && Cliente2 != clienteModificar) {
                                throw new IllegalArgumentException("Ese celular ya está en uso por otro cliente.");
                            }
                            clienteModificar.celular = nuevoCelular;

                            Cliente.sobrescribirClientes();
                            System.out.println("Cliente modificado correctamente.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 4:
                    // Eliminar cliente
                    System.out.print("DNI del cliente a eliminar: ");
                    String eliminarDNI = input.nextLine();
                    Cliente eliminarCliente = Cliente.buscarPorDni(eliminarDNI);
                    if (eliminarCliente != null) {
                        Cliente.eliminarCliente(eliminarCliente);
                        Cliente.sobrescribirClientes();
                        System.out.println("Cliente eliminado correctamente.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 5:
                    break;
            }
        } while (opcion != 5);
    }
public static void moduloProductos() {
    Scanner input = new Scanner(System.in);
    int opcion;

    do {
        System.out.println("\n--- MÓDULO PRODUCTOS ---");
        System.out.println("1. Registrar Producto");
        System.out.println("2. Buscar por Código");
        System.out.println("3. Buscar por Nombre");
        System.out.println("4. Buscar por Categoría");
        System.out.println("5. Modificar Producto por Código");
        System.out.println("6. Eliminar Producto");
        System.out.println("7. Ordenar por codigo del producto");
        System.out.println("8. Volver al menu");
        System.out.print("Opción: ");

        try {
            opcion = Integer.parseInt(input.nextLine());

            switch (opcion) {
                case 1:
                    // Registrar producto
                    try {
                        System.out.print("Código: ");
                        String codigo = input.nextLine();
                        if (Producto.verificarCodigoRepetido(codigo)) {
                            throw new IllegalArgumentException("Ya existe un producto con ese código.");
                        }

                        System.out.print("Nombre: ");
                        String nombre = input.nextLine();

                        System.out.print("Descripción: ");
                        String descripcion = input.nextLine();

                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(input.nextLine());

                        System.out.print("Marca: ");
                        String marca = input.nextLine();

                        System.out.print("Presentación: ");
                        String presentacion = input.nextLine();

                        System.out.print("Stock: ");
                        int stock = Integer.parseInt(input.nextLine());

                        System.out.print("Categoría: ");
                        String categoria = input.nextLine();

                        Producto nuevo = new Producto(codigo, nombre, descripcion, precio, marca, presentacion, stock, categoria);
                        Producto.listaProductos[Producto.totalProductos++] = nuevo;
                        Producto.guardarProducto(nuevo);
                        System.out.println("✅ Producto registrado correctamente.");

                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error: Formato numérico inválido.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Buscar por código
                    System.out.print("Ingrese código del producto: ");
                    String cod = input.nextLine();
                    Producto encontrado = Producto.buscarPorCodigo(cod);
                    if (encontrado != null) {
                        System.out.println("✅ Producto encontrado:");
                        System.out.println(encontrado);
                    } else {
                        System.out.println("❌ Producto no encontrado.");
                    }
                    break;

                case 3:
                    // Buscar por nombre
                    System.out.print("Ingrese nombre del producto: ");
                    String nomBuscar = input.nextLine();
                    Producto.buscarPorNombre(nomBuscar);
                    break;

                case 4:
                    // Buscar por categoría
                    System.out.print("Ingrese categoría: ");
                    String catBuscar = input.nextLine();
                    Producto.buscarPorCategoria(catBuscar);
                    break;

                case 5:
                    // Modificar producto por código
                    System.out.print("Ingrese el código del producto a modificar: ");
                    String codigoModificar = input.nextLine();
                    Producto prodMod = Producto.buscarPorCodigo(codigoModificar);
                    if (prodMod != null) {
                        try {
                            System.out.print("Nuevo nombre: ");
                            prodMod.nombre = input.nextLine();

                            System.out.print("Nueva descripción: ");
                            prodMod.descripcion = input.nextLine();

                            System.out.print("Nuevo precio: ");
                            prodMod.precio = Double.parseDouble(input.nextLine());

                            System.out.print("Nueva marca: ");
                            prodMod.marca = input.nextLine();

                            System.out.print("Nueva presentación: ");
                            prodMod.presentacion = input.nextLine();

                            System.out.print("Nuevo stock: ");
                            prodMod.stock = Integer.parseInt(input.nextLine());

                            System.out.print("Nueva categoría: ");
                            prodMod.categoria = input.nextLine();

                            Producto.sobrescribirProductos();
                            System.out.println("✅ Producto modificado correctamente.");
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Error: Formato numérico inválido.");
                        }
                    } else {
                        System.out.println("❌ Producto no encontrado.");
                    }
                    break;

                case 6:
                    // Eliminar producto
                    System.out.print("Ingrese el código del producto a eliminar: ");
                    String codEliminar = input.nextLine();
                    boolean eliminado = Producto.eliminarProducto(codEliminar);
                    if (eliminado) {
                        Producto.sobrescribirProductos();
                        System.out.println("✅ Producto eliminado correctamente.");
                    } else {
                        System.out.println("❌ Producto no encontrado.");
                    }
                    break;

                case 7:
                    System.out.println("Ordenar productos por codigo");
                    Producto.ordenarPorCodigo();
                    Producto.mostrarProductos();
                    break;
                case 8:

                    System.out.println("↩️ Regresando al menú principal...");
                    break;

                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Debes ingresar solo números.");
            opcion = -1; // evitar salir por error
        }

    } while (opcion != 7);
}
    // Registro de un nuevo pedido
    public static void registrarPedido() {
        Scanner input = new Scanner(System.in);

        try {
            System.out.print("Número de pedido: ");
            String numero = input.nextLine();
            for (int i = 0; i < Pedido.totalPedidos; i++) {
                if (Pedido.listaPedidos[i].numeroPedido.equals(numero)) {
                    throw new IllegalArgumentException("Ya existe un pedido con ese número.");
                }
            }

            System.out.print("Fecha (dd/mm/yyyy): ");
            String fecha = input.nextLine();
            if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
                throw new IllegalArgumentException("Formato de fecha inválido. Debe ser dd/mm/yyyy y contener solo números.");
            }

            System.out.print("DNI del cliente: ");
            String dni = input.nextLine();
            Cliente cliente3 = Cliente.buscarPorDni(dni);
            if (cliente3 == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            System.out.print("Código del producto: ");
            String codigo = input.nextLine();
            Producto producto = Producto.buscarPorCodigo(codigo);

            if (producto == null) {
                System.out.println("Producto no encontrado.");
                return;
            }
            if (producto.stock == 0) {
                System.out.println("Ya no queda stock de este producto.");
                return;
            }

            System.out.print("Cantidad: ");
            int cantidad = input.nextInt(); input.nextLine();
            if (cantidad > producto.stock) {
                System.out.println("Stock insuficiente para la cantidad solicitada.");
                return;
            }

            Pedido pedido1 = new Pedido(numero, fecha, cliente3, producto, cantidad);
            Pedido.listaPedidos[Pedido.totalPedidos++] = pedido1;
            producto.stock -= cantidad;
            Pedido.guardarPedido(pedido1);
            Producto.sobrescribirProductos();
            System.out.println("Pedido registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
