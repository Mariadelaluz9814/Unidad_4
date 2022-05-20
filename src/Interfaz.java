import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interfaz extends JFrame{
    private JLabel     lbNombre;
    private JTextField txtNombre;
    private JLabel     lbPuesto;
    private JTextField txtPuesto;
    private JLabel     lbDepartamento;
    private JTextField txtDepartamento;
    private JButton    btnGuardar;
    private JPanel panelPrincipal;
    private JButton btnConectar;
    private JButton btnSalir;
    private JTable     tablaEmpleado;
    private JTextField txtConsulta;
    private JLabel     lbConsulta;
    private JButton btnConsulta;
    private JButton btnCargarRegistro;
    private JButton    btnModificar;
    private JButton    btnTodos;
    private JButton    btnEliminar;
    private Connection reg;
    private Conectar con;

    public Interfaz(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600,600);
        botonActionListener();
        configuarComponentes();
        conectarBaseDatos();
        verTabla("");
        this.setContentPane(panelPrincipal);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Interfaz objeto=new Interfaz();
    }
    private void configuarComponentes(){

    }

    public void verTabla(String numTarjeta){
        DefaultTableModel defaultTableModel =new DefaultTableModel();
        defaultTableModel.addColumn("Tarjeta");
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Puesto");
        defaultTableModel.addColumn("Departamento");
        tablaEmpleado.setModel(defaultTableModel);

        String [] datos=new String[4];
        String sql="";


        if (numTarjeta.equals("")){
            sql="SELECT * FROM empleado";
        }else {
            sql="SELECT * FROM empleado WHERE tarjeta='"+numTarjeta+"'";
        }

        try {
            Statement sentencia=reg.createStatement();
            ResultSet rs=sentencia.executeQuery(sql);
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                defaultTableModel.addRow(datos);
            }
            tablaEmpleado.setModel(defaultTableModel);
        }catch (SQLException ex){
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    private void botonActionListener(){
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conectarBaseDatos();

                String sql,nom,puesto,dpto;
                nom=txtNombre.getText();
                puesto=txtPuesto.getText();
                dpto=txtDepartamento.getText();
                sql="INSERT INTO empleado(nombre,puesto,departamento)VALUES(?,?,?)";
                try {
                    PreparedStatement ps=reg.prepareStatement(sql);
                    ps.setString(1,nom);
                    ps.setString(2,puesto);
                    ps.setString(3,dpto);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Registro guardado");
                    limpiar();
                }catch (SQLException ex){
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarjeta;
                tarjeta=txtConsulta.getText();
                if (tarjeta.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"No ha puesto numero de tarjeta");
                }else{
                    verTabla(tarjeta);
                }
            }
        });
        btnCargarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mod="",sql="";
                String[]datos=new String[4];
                mod=txtConsulta.getText();
                sql="SELECT * FROM empleado WHERE tarjeta='"+mod+"'";
                if (mod.equals("")){
                    JOptionPane.showMessageDialog(null,"No hay número de tarjeta");
                }else {
                    try {
                        Statement sentencia = reg.createStatement();
                        ResultSet rs=sentencia.executeQuery(sql);
                        while (rs.next()){
                            datos[0]=rs.getString(1);
                            datos[1]=rs.getString(2);
                            datos[2]=rs.getString(3);
                            datos[3]=rs.getString(4);
                        }
                        txtNombre.setText(datos[1]);
                        txtPuesto.setText(datos[2]);
                        txtDepartamento.setText(datos[3]);
                    }catch (SQLException ex){
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int confirmar=JOptionPane.showConfirmDialog(null,"¿Está seguro que desea modificar los datos?");
                    if (confirmar==JOptionPane.YES_OPTION){
                    PreparedStatement ps=reg.prepareStatement("UPDATE empleado SET nombre='"+txtNombre.getText()+"',puesto='"+txtPuesto.getText()+"',departamento='"+txtDepartamento.getText()+"'WHERE tarjeta='"+txtConsulta.getText()+"'");
                    ps.executeUpdate();
                    verTabla("");
                    JOptionPane.showMessageDialog(null,"Modificación realizada");}
                }catch (SQLException ex){
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
        btnTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTabla("");
            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int pregunta=JOptionPane.showConfirmDialog(null,"¿Desea eliminar el elemento de la tabla?");
                    if (pregunta==JOptionPane.YES_OPTION){
                    PreparedStatement ps=reg.prepareStatement("DELETE FROM empleado WHERE tarjeta='"+txtConsulta.getText()+"'");
                    ps.executeUpdate();
                    verTabla("");
                    JOptionPane.showMessageDialog(null,"Modificación realizada");}
                }catch (SQLException ex){
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
    }

    private void conectarBaseDatos() {
        con=new Conectar();
        reg=con.conexion();
    }


    private void limpiar(){
        txtNombre.setText("");
        txtPuesto.setText("");
        txtDepartamento.setText("");
    }
}
