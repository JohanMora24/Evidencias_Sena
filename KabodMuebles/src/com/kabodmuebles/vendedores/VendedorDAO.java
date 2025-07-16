package com.kabodmuebles.vendedores;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VendedorDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kabod_muebles";
    private static final String USER = "root";
    private static final String PASSWORD = "Mysql123";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

 // Insertar Vendedor
    public void insertarVendedor(int idVendedor, String nombreVendedor, String telefonoVendedor, String emailVendedor) {
        String sql = "INSERT INTO Vendedor (Id_Vendedor, Nombre_Vendedor, Telefono_Vendedor, Email_Vendedor) VALUES (?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVendedor);
            stmt.setString(2, nombreVendedor);
            stmt.setString(3, telefonoVendedor);
            stmt.setString(4, emailVendedor);
           
            stmt.executeUpdate();
            System.out.println("✅ Vendedor insertado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar Vendedor: " + e.getMessage());
        }
    }
    
 // Consultar Vendedor
    
    public void listarVendedor() {
        String sql = "SELECT * FROM vendedor";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id_Vendedor"));
                System.out.println("Nombre: " + rs.getString("Nombre_Vendedor"));
                System.out.println("Teléfono: " + rs.getString("Telefono_Vendedor"));
                System.out.println("Email: " + rs.getString("Email_Vendedor"));
                System.out.println("----------");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar Vendedor: " + e.getMessage());
        }
    }
    
 // Actualizar Vendedor
    
    public void actualizarVendedor(int idVendedor, String nuevoNombre, String nuevoTelefono,
            String nuevoEmail) {
    	
    	StringBuilder sql = new StringBuilder("UPDATE vendedor SET ");
    	List<Object> parametros = new ArrayList<>();

    	if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
    		sql.append("Nombre_Vendedor = ?, ");
    		parametros.add(nuevoNombre);
    	}
    	if (nuevoTelefono != null && !nuevoTelefono.isEmpty()) {
    		sql.append("Telefono_Vendedor = ?, ");
    		parametros.add(nuevoTelefono);
    	}
    	if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
    		sql.append("Email_Vendedor = ?, ");
    		parametros.add(nuevoEmail);
    	}
    	

    	// Quitar la última coma y espacio
    	if (parametros.isEmpty()) {
    		System.out.println("⚠️ No se proporcionaron campos para actualizar");
    		return;
    	}
    
    	sql.setLength(sql.length() - 2);
    	sql.append(" WHERE Id_Vendedor = ?");
    	parametros.add(idVendedor);

    	try (Connection conn = conectar();
    			PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

    		for (int i = 0; i < parametros.size(); i++) {
    			stmt.setObject(i + 1, parametros.get(i));
    		}

    		stmt.executeUpdate();
    		System.out.println("✅ Vendedor actualizado correctamente (campos específicos)");

    	} catch (SQLException e) {
    		System.out.println("❌ Error al actualizar Vendedor: " + e.getMessage());
    	}
    }

 // Eliminar Vendedor
    public void eliminarVendedor(int idVendedor) {
        String sql = "DELETE FROM cliente WHERE Id_Vendedor = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVendedor);
            stmt.executeUpdate();
            System.out.println("✅ Vendedor eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar Vendedor: " + e.getMessage());
        }
    }
    
 // Métodos (insertar, actualizar, eliminar, listar)

    public static void main(String[] args) {
        VendedorDAO dao = new VendedorDAO();

        // INSERCIÓN VENDEDOR
        //dao.insertarVendedor(4, "David Murcia", "3004356473", "David@gmail.com");

        // CONSULTA VENDEDOR
        //dao.listarVendedor();

        // ACTUALIZACIÓN VENDEDOR
        dao.actualizarVendedor(3, null , null, "Carlos_mendez@gmail.com");

        // ELIMINACIÓN VENDEDOR
        //dao.eliminarVendedor(1);
    }
}
