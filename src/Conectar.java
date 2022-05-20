import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class Conectar {
    Connection con=null;
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/trabajo","root","");
            System.out.println("Conexión realizada");
        }catch (ClassNotFoundException|SQLException e){
         JOptionPane.showMessageDialog(null, "Problemas al conectar "+e);
        }
        return con;
    }
}
