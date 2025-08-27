
package SistemaBoticas;
import java.io.*;
public class Cliente {
    //Atributos
    String dni, nombres, apellidos, direccion, distrito, correo, celular;
    static Cliente[] listaClientes = new Cliente[100];
    static int totalClientes = 0;
    private static final String RUTA = "clientes.txt"; // Ruta del archivo

    //Constructor
    public Cliente(String dni, String nombres, String apellidos, String direccion, String distrito, String correo, String celular) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.distrito = distrito;
        this.correo = correo;
        this.celular = celular;
    }

    //Metodo Getter
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    //METODOS
      //Buscar cliente por DNI
        public static Cliente buscarPorDni(String dniBuscado) {
        for (int i = 0; i < totalClientes; i++) {
            if (listaClientes[i].dni.equals(dniBuscado)) {
                return listaClientes[i];
                }
            }
            return null;
        }

    //Buscar cliente por celular
    public static Cliente buscarPorCelular(String celularBuscado) {
        for (int i = 0; i < totalClientes; i++) {
            if (listaClientes[i].celular.equals(celularBuscado)) {
                return listaClientes[i];
            }
        }
        return null;
    }

    //Muestra todos los clientes registrados
    public static void mostrarClientes() {
        if (totalClientes == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (int i = 0; i < totalClientes; i++) {
            System.out.println(listaClientes[i]);
        }
    }

    //Elimina un cliente del arreglo
    public static boolean eliminarCliente(Cliente c) {
        for (int i = 0; i < totalClientes; i++) {
            if (listaClientes[i].dni.equals(c.dni)) {
                for (int j = i; j < totalClientes - 1; j++) {
                    listaClientes[j] = listaClientes[j + 1];
                }
                totalClientes--;
                return true;
            }
        }
        return false;
    }


    //Devuelve datos del cliente en formato legible
    @Override
    public String toString() {
        String datos = "";
        datos = datos.concat("DNI: " + dni + ", ");
        datos = datos.concat("Nombre: " + nombres + " " + apellidos + ", ");
        datos = datos.concat("Dirección: " + direccion + ", ");
        datos = datos.concat("Distrito: " + distrito + ", ");
        datos = datos.concat("Correo: " + correo + ", ");
        datos = datos.concat("Celular: " + celular);
        return datos;
    }

// ---------------------------------------
// Autor: Ricardo Cervantes Tejada
// ---------------------------------------

    //Guarda un cliente al final del archivo
    public static void guardarCliente(Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA, true))) {
            writer.write(cliente.dni + ";" + cliente.nombres + ";" + cliente.apellidos + ";" + cliente.direccion + ";" +
                         cliente.distrito + ";" + cliente.correo + ";" + cliente.celular);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    //Sobrescribe el archivo con todos los clientes actuales
    public static void sobrescribirClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA))) {
            for (int i = 0; i < totalClientes; i++) {
                Cliente cliente = listaClientes[i];
                writer.write(cliente.dni + ";" + cliente.nombres + ";" + cliente.apellidos + ";" + cliente.direccion + ";" +
                             cliente.distrito + ";" + cliente.correo + ";" + cliente.celular);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al sobrescribir archivo: " + e.getMessage());
        }
    }

    //Carga todos los clientes desde el archivo al arreglo
    public static void cargarClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 7) {
                    Cliente nuevo = new Cliente(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]);
                    listaClientes[totalClientes++] = nuevo;
                }
            }
        } catch (FileNotFoundException e) {
            // No hacer nada si el archivo aún no existe
        } catch (IOException e) {
            System.out.println("Error al leer archivo de clientes: " + e.getMessage());
        }
    }
}
