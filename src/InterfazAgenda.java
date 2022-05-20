import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfazAgenda extends JFrame{
    private JPanel     panelAgenda;
    private JTextField txtNombre;
    private JLabel lbNombre;
    private JLabel     lbApellidos;
    private JTextField txtApellidos;
    private JLabel lbCasa;
    private JLabel lbCelular;
    private JLabel lbTrabajo;
    private JLabel lbCorreo;
    private JLabel lbDireccion;
    private JLabel lbCumpleaños;
    private JLabel     lbCategoria;
    private JTextField txtCasa;
    private JTextField txtCelular;
    private JTextField txtTrabajo;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JTextField  txtCumpleaños;
    private JComboBox   comboCategoria;
    private JScrollPane scrollPaneCategoria;
    private JButton     btnAgregar;
    private JScrollPane scrollPaneConsulta;
    private JComboBox   comboConsulta;
    private JButton btnConsulta;
    private JLabel lbConsultar;
    private JScrollPane scrollPaneTabla;
    private JTable      tablaAgenda;
    private JLabel     lbTitulo;
    private JTextField txtConsulta;
    private JButton btnModificar;
    private JButton btnEliminar;

    private Connection reg;
    private ConectarAgenda   con;

    InterfazAgenda(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900,900);
        botonAgendaActionListener();
        configuarComponentes();
        conectarBaseDatosAgenda();
        verTabla("");
        this.setContentPane(panelAgenda);
        this.setVisible(true);
    }

    private void configuarComponentes() {
    }

    public static void main(String[] args) {
        InterfazAgenda objeto=new InterfazAgenda();
    }

    public void verTabla(String idpersona){
        DefaultTableModel defaultTableModel =new DefaultTableModel();
        defaultTableModel.addColumn("IDPersona");
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Apellidos");
        defaultTableModel.addColumn("TelCasa");
        defaultTableModel.addColumn("TelCel");
        defaultTableModel.addColumn("TelTrabajo");
        defaultTableModel.addColumn("Correo");
        defaultTableModel.addColumn("Dirección");
        defaultTableModel.addColumn("Cumpleaños");
        defaultTableModel.addColumn("Categoría");
        tablaAgenda.setModel(defaultTableModel);

        String [] datos=new String[10];
        String sql="";

        if (idpersona.equals("")){
            sql="SELECT * FROM agenda";
        }else {
            sql="SELECT * FROM agenda WHERE IDPersona='"+idpersona+"'";
        }

        try {
            Statement sentencia =reg.createStatement();
            ResultSet rs        =sentencia.executeQuery(sql);
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                defaultTableModel.addRow(datos);
            }
            tablaAgenda.setModel(defaultTableModel);
        }catch (SQLException ex){
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public void verTabla2(String otro){
        DefaultTableModel defaultTableModel =new DefaultTableModel();
        defaultTableModel.addColumn("IDPersona");
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Apellidos");
        defaultTableModel.addColumn("Tel.Casa");
        defaultTableModel.addColumn("Tel.Cel.");
        defaultTableModel.addColumn("Tel.Trabajo");
        defaultTableModel.addColumn("Correo");
        defaultTableModel.addColumn("Dirección");
        defaultTableModel.addColumn("Cumpleaños");
        defaultTableModel.addColumn("Categoría");
        tablaAgenda.setModel(defaultTableModel);

        String [] datos=new String[10];
        String sql="";


        switch (otro){
            case "Nombre":
                sql="SELECT * FROM agenda WHERE Nombre='"+otro+"'";
                break;
            case "Apellidos":
                sql="SELECT * FROM agenda WHERE Apellidos='"+otro+"'";
                break;
            case "Categoría":
                sql="SELECT * FROM agenda WHERE Categoria='"+otro+"'";
                break;
        }


        try {
            Statement sentencia =reg.createStatement();
            ResultSet rs        =sentencia.executeQuery(sql);
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                defaultTableModel.addRow(datos);
            }
            tablaAgenda.setModel(defaultTableModel);
        }catch (SQLException ex){
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    private void botonAgendaActionListener(){
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectarBaseDatosAgenda();

                String sql,nom,apellidos,casa,cel,trabajo,correo,direccion,cumpleaños,categoria;
                nom=txtNombre.getText();
                nom=nom.toUpperCase();
                apellidos=txtApellidos.getText();
                apellidos=apellidos.toUpperCase();
                casa=txtCasa.getText();
                cel=txtCelular.getText();
                trabajo=txtTrabajo.getText();
                correo=txtCorreo.getText();
                direccion=txtDireccion.getText();
                cumpleaños=txtCumpleaños.getText();
                categoria=String.valueOf(comboCategoria.getSelectedItem());
                sql="INSERT INTO agenda(Nombre,Apellidos,TelCasa,TelCel,TelTrabajo,Correo,Dirección,Cumpleaños,Categoria)VALUES(?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps =reg.prepareStatement(sql);
                    ps.setString(1,nom);
                    ps.setString(2,apellidos);
                    ps.setString(3,casa);
                    ps.setString(4,cel);
                    ps.setString(5,trabajo);
                    ps.setString(6,correo);
                    ps.setString(7,direccion);
                    ps.setString(8,cumpleaños);
                    ps.setString(9,categoria);
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
                String idPersona,nombre,apellidos,categoria;
                switch (comboConsulta.getSelectedIndex()) {
                    case 0:
                    idPersona = txtConsulta.getText();
                    if (idPersona.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No ha puesto el numero de Identificación de la persona");
                    } else {
                        verTabla(idPersona);
                    }
                    break;
                    case 1:
                        nombre = txtConsulta.getText();
                        nombre = nombre.toUpperCase();
                        if (nombre.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No ha puesto el nombre de la persona");
                        } else {
                            verTabla2(nombre);
                        }
                        break;
                    case 2:
                        apellidos = txtConsulta.getText();
                        apellidos = apellidos.toUpperCase();
                        if (apellidos.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No ha puesto los apellidos de la persona");
                        } else {
                            verTabla2(apellidos);
                        }
                        break;
                    case 3:
                        categoria = txtConsulta.getText();
                        if (categoria.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No ha puesto la categoría de la persona");
                        } else {
                            verTabla2(categoria);
                        }
                        break;
                }
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mod="",sql="";
                String[]datos=new String[10];
                mod=txtConsulta.getText();
                sql="SELECT * FROM agenda WHERE IDPersona='"+mod+"'";
                if (mod.equals("")){
                    JOptionPane.showMessageDialog(null,"No hay número de identificación");
                }else {
                    try {
                        Statement sentencia = reg.createStatement();
                        ResultSet rs=sentencia.executeQuery(sql);
                        while (rs.next()){
                            datos[0]=rs.getString(1);
                            datos[1]=rs.getString(2);
                            datos[2]=rs.getString(3);
                            datos[3]=rs.getString(4);
                            datos[4]=rs.getString(5);
                            datos[5]=rs.getString(6);
                            datos[6]=rs.getString(7);
                            datos[7]=rs.getString(8);
                            datos[8]=rs.getString(9);
                            datos[9]=rs.getString(10);
                        }
                        txtNombre.setText(datos[1]);
                        txtApellidos.setText(datos[2]);
                        txtCasa.setText(datos[3]);
                        txtCelular.setText(datos[4]);
                        txtTrabajo.setText(datos[5]);
                        txtCorreo.setText(datos[6]);
                        txtDireccion.setText(datos[7]);
                        txtCumpleaños.setText(datos[8]);
                        switch (datos[9]){
                            case "Familia":
                                comboCategoria.setSelectedIndex(0);
                                break;
                            case "Amigo" :
                                comboCategoria.setSelectedIndex(1);
                                break;
                            case "Trabajo":
                                comboCategoria.setSelectedIndex(2);
                                break;
                            case "Escuela" :
                                comboCategoria.setSelectedIndex(3);
                                break;
                            case "Fiesta":
                                comboCategoria.setSelectedIndex(4);
                                break;
                            case "Tienda/Local":
                                comboCategoria.setSelectedIndex(5);
                                break;
                            case "Vecino" :
                                comboCategoria.setSelectedIndex(6);
                                break;
                            case "Internet":
                                comboCategoria.setSelectedIndex(7);
                                break;
                            case "Hobby" :
                                comboCategoria.setSelectedIndex(8);
                                break;
                            case "Otro":
                                comboCategoria.setSelectedIndex(9);
                                break;
                        }
                    }catch (SQLException ex){
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
                try {
                    int confirmar=JOptionPane.showConfirmDialog(null,"¿Está seguro que desea modificar los datos?");
                    if (confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement ps=reg.prepareStatement("UPDATE agenda SET Nombre='"+txtNombre.getText()+"',Apellidos='"+txtApellidos.getText()+"',TelCasa='"+txtCasa.getText()+"',TelCel='"+txtCelular.getText()+"',TelTrabajo='"+txtTrabajo.getText()+"',Correo='"+txtCasa.getText()+"',Dirección='"+txtDireccion.getText()+"',Cumpleaños='"+txtCumpleaños.getText()+"',Categoria='"+String.valueOf(comboCategoria.getSelectedItem())+"'WHERE IDPersona='"+txtConsulta.getText()+"'");
                        ps.executeUpdate();
                        verTabla("");
                        JOptionPane.showMessageDialog(null,"Modificación realizada");}
                }catch (SQLException ex){
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int pregunta=JOptionPane.showConfirmDialog(null,"¿Desea eliminar el elemento de la tabla?");
                    if (pregunta==JOptionPane.YES_OPTION){
                        PreparedStatement ps=reg.prepareStatement("DELETE FROM agenda WHERE IDPersona='"+txtConsulta.getText()+"'");
                        ps.executeUpdate();
                        verTabla("");
                        JOptionPane.showMessageDialog(null,"Modificación realizada");}
                }catch (SQLException ex){
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        });
    }
    private void conectarBaseDatosAgenda() {
        con=new ConectarAgenda();
        reg=con.conexionAgenda();
    }
    private void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCasa.setText("");
        txtCelular.setText("");
        txtTrabajo.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtCumpleaños.setText("");

    }
}
