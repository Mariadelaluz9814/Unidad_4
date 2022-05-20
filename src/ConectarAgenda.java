import java.sql.*;
import javax.swing.JOptionPane;
public class ConectarAgenda {
    Connection conexion=null;
    public Connection conexionAgenda(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion=DriverManager.getConnection("jdbc:mysql://localhost/agendaTelefonica","root","");
            System.out.println("Conexión realizada");
        }catch (ClassNotFoundException|SQLException e){
            JOptionPane.showMessageDialog(null, "Problemas al conectar "+e);
        }
        return conexion;
    }
}
