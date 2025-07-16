package com.kabodmuebles.clientes;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ClienteDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kabod_muebles";
    private static final String USER = "root";
    private static final String PASSWORD = "Mysql123";

    // Método para conectar
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insertar cliente
    public void insertarCliente(int idCliente, String nombreCliente, String telefonoCliente, String emailCliente, String direccionCliente, int idVendedor) {
        String sql = "INSERT INTO cliente (Id_Cliente, Nombre_Cliente, Telefono_Cliente, Email_Cliente, Direccion_Cliente, Id_Vendedor) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setString(2, nombreCliente);
            stmt.setString(3, telefonoCliente);
            stmt.setString(4, emailCliente);
            stmt.setString(5, direccionCliente);
            stmt.setInt(6, idVendedor);

            stmt.executeUpdate();
            System.out.println("✅ Cliente insertado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
        }
    }

    // Consultar clientes
    public void listarClientes() {
        String sql = "SELECT * FROM cliente";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id_Cliente"));
                System.out.println("Nombre: " + rs.getString("Nombre_Cliente"));
                System.out.println("Teléfono: " + rs.getString("Telefono_Cliente"));
                System.out.println("Email: " + rs.getString("Email_Cliente"));
                System.out.println("Dirección: " + rs.getString("Direccion_Cliente"));
                System.out.println("ID Vendedor: " + rs.getInt("Id_Vendedor"));
                System.out.println("----------");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar clientes: " + e.getMessage());
        }
    }

    // Actualizar cliente
    
    public void actualizarClienteFlexible(int idCliente, String nuevoNombre, String nuevoTelefono,
            String nuevoEmail, String nuevaDireccion, Integer nuevoIdVendedor) {
    	
    	StringBuilder sql = new StringBuilder("UPDATE cliente SET ");
    	List<Object> parametros = new ArrayList<>();

    	if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
    		sql.append("Nombre_Cliente = ?, ");
    		parametros.add(nuevoNombre);
    	}
    	if (nuevoTelefono != null && !nuevoTelefono.isEmpty()) {
    		sql.append("Telefono_Cliente = ?, ");
    		parametros.add(nuevoTelefono);
    	}
    	if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
    		sql.append("Email_Cliente = ?, ");
    		parametros.add(nuevoEmail);
    	}
    	if (nuevaDireccion != null && !nuevaDireccion.isEmpty()) {
    		sql.append("Direccion_Cliente = ?, ");
    		parametros.add(nuevaDireccion);
    	}
    	if (nuevoIdVendedor != null) {
    		sql.append("Id_Vendedor = ?, ");
    		parametros.add(nuevoIdVendedor);
    	}

    	// Quitar la última coma y espacio
    	if (parametros.isEmpty()) {
    		System.out.println("⚠️ No se proporcionaron campos para actualizar");
    		return;
    	}
    
    	sql.setLength(sql.length() - 2);
    	sql.append(" WHERE Id_Cliente = ?");
    	parametros.add(idCliente);

    	try (Connection conn = conectar();
    			PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

    		for (int i = 0; i < parametros.size(); i++) {
    			stmt.setObject(i + 1, parametros.get(i));
    		}

    		stmt.executeUpdate();
    		System.out.println("✅ Cliente actualizado correctamente (campos específicos)");

    	} catch (SQLException e) {
    		System.out.println("❌ Error al actualizar cliente: " + e.getMessage());
    	}
    }


    // Eliminar cliente
    public void eliminarCliente(int idCliente) {
        String sql = "DELETE FROM cliente WHERE Id_Cliente = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
            System.out.println("✅ Cliente eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar cliente: " + e.getMessage());
        }
    }


    // Metodos (insertar, actualizar, eliminar, listar)

    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();

        // INSERCIÓN
        dao.insertarCliente(1087463, "Camila Morales", "3213453456", "Camila_mm@gmail.com", "Calle 4 #18-70", 4);

        // CONSULTA
      // dao.listarClientes();

        // ACTUALIZACIÓN
       //dao.actualizarClienteFlexible(12345, "Alberto Salas" , null, null, null, null);

        // ELIMINACIÓN
        //dao.eliminarCliente(1);
    }
}


