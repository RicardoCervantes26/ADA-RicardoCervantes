
package SistemaBoticas;
import java.io.*;

public class Producto {

    // Atributos del producto
    public String codigo, nombre, descripcion, marca, presentacion, categoria;
    double precio;
    int stock;

    // Arreglo estático para guardar productos
    static Producto[] listaProductos = new Producto[100];
    static int totalProductos = 0;

    // Constructor para crear un producto
    public Producto(String codigo, String nombre, String descripcion, double precio, String marca,
                    String presentacion, int stock, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
        this.presentacion = presentacion;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Devuelve información del producto como cadena de texto
    @Override
    public String toString() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Descripción: " + descripcion + ", Precio: S/." + precio +
               ", Marca: " + marca + ", Presentación: " + presentacion + ", Stock: " + stock + ", Categoría: " + categoria;
    }

    // Guardar producto en archivo
    public static void guardarProducto(Producto productoNuevo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt", true))) {
            writer.write(productoNuevo.codigo + ";" + productoNuevo.nombre + ";" + productoNuevo.descripcion + ";" + productoNuevo.precio + ";" +
                         productoNuevo.marca + ";" + productoNuevo.presentacion + ";" + productoNuevo.stock + ";" + productoNuevo.categoria);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }
    }

    // Cargar productos desde archivo
    public static void cargarProductos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 8) {
                    listaProductos[totalProductos++] = new Producto(
                        partes[0], partes[1], partes[2],
                        Double.parseDouble(partes[3]),
                        partes[4], partes[5],
                        Integer.parseInt(partes[6]),
                        partes[7]
                    );
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe aún
        } catch (IOException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }

    // Reescribir archivo completo
    public static void sobrescribirProductos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (int i = 0; i < totalProductos; i++) {
                Producto p = listaProductos[i];
                writer.write(p.codigo + ";" + p.nombre + ";" + p.descripcion + ";" + p.precio + ";" +
                             p.marca + ";" + p.presentacion + ";" + p.stock + ";" + p.categoria);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al sobrescribir productos: " + e.getMessage());
        }
    }

    // Verificar código repetido
    public static boolean verificarCodigoRepetido(String cod) {
        for (int i = 0; i < totalProductos; i++) {
            if (listaProductos[i].codigo.equalsIgnoreCase(cod)) return true;
        }
        return false;
    }

    // Buscar por código
    public static Producto buscarPorCodigo(String codBuscado) {
        for (int i = 0; i < totalProductos; i++) {
            if (listaProductos[i].codigo.equalsIgnoreCase(codBuscado)) {
                return listaProductos[i];
            }
        }
        return null;
    }

    // Buscar por nombre
    public static void buscarPorNombre(String nombreBuscado) {
        boolean encontrado = false;
        for (int i = 0; i < totalProductos; i++) {
            if (listaProductos[i].nombre.equalsIgnoreCase(nombreBuscado)) {
                System.out.println(listaProductos[i]);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos con ese nombre.");
        }
    }

    // Buscar por categoría
    public static void buscarPorCategoria(String categoriaBuscada) {
        boolean encontrado = false;
        for (int i = 0; i < totalProductos; i++) {
            if (listaProductos[i].categoria.equalsIgnoreCase(categoriaBuscada)) {
                System.out.println(listaProductos[i]);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos en esa categoría.");
        }
    }

    // Eliminar producto
    public static boolean eliminarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (listaProductos[i].codigo.equalsIgnoreCase(codigo)) {
                for (int j = i; j < totalProductos - 1; j++) {
                    listaProductos[j] = listaProductos[j + 1];
                }
                totalProductos--;
                return true;
            }
        }
        return false;
    }
    public static void ordenarPorCodigo() {
    for (int i = 1; i < totalProductos; i++) {
        Producto actual = listaProductos[i];
        int j = i - 1;

        // Compara los códigos y los mueve si es necesario
        while (j >= 0 && listaProductos[j].codigo.compareToIgnoreCase(actual.codigo) > 0) {
            listaProductos[j + 1] = listaProductos[j];
            j--;
        }
        listaProductos[j + 1] = actual;
    }
    System.out.println("Productos ordenados correctamente por código.");
}
    public static void mostrarProductos() {
    if (totalProductos == 0) {
        System.out.println("No hay productos registrados.");
        return;
    }
    for (int i = 0; i < totalProductos; i++) {
        System.out.println(listaProductos[i]);
    }
}

}
