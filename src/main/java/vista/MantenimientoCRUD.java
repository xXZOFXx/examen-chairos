/*




 */
package vista;

//librerias 
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.*;

//se manda a llamar la clase que generamos en el paquete conexion
import conexion.conexion;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zof
 */
public class MantenimientoCRUD extends javax.swing.JFrame {

    
    DefaultTableModel model; //llamando a un objeto que va a tomar los datos llamado model
    conexion cn = new conexion();

    Connection con;

    Statement st;

    ResultSet rs;

    int id = 0;

    /**
     * Creates new form MantenimientoCRUD
     */
    public MantenimientoCRUD() { //inicia constructor
        initComponents();
        setLocationRelativeTo(null);
        this.setTitle("Examen equipo chairos :)");
        
      
        
        listar();    //lamada al metodo listar
        
        
        
    }

    //metodo  para alta de registros
    public void altaUsuarios() {

        String resultId = "";

        try {//inicia try

            id = Integer.parseInt(txtIdUsuario.getText()); //Guarda un valor entero de un usuario
            resultId = String.valueOf(id); //conversion de variable primitiva a objeto
        } catch (NumberFormatException e) {//inicia catsh

            JOptionPane.showMessageDialog(null, "Favor de ingresar solo numeros!!");

        }//termina catch

        String usuarioAcceso = this.txtUsuario.getText(); //guarda usuario

        String passwordAcceso = this.txtPassword.getText(); //guarda pass

        try {//inicia try dos

            if ((resultId.isEmpty()) || (usuarioAcceso.equals("")) || (passwordAcceso.equals(""))) {//inicia if

                JOptionPane.showMessageDialog(rootPane, "Debe ingresar datos");

            } else {

                String sqlAlta = "Insert INTO usuarios (id_usuario,usuario,password) values ('" + id + "','" + usuarioAcceso + "', '" + passwordAcceso + "')"; //variable objeto que lleva una instruccion de base de datos

                con = cn.getConnection();
                st = con.createStatement();
                st.executeUpdate(sqlAlta);

                JOptionPane.showMessageDialog(null, "ingreso de datos exitoso \n\n"
                        + "usuario: " + usuarioAcceso);

            }//termina else

        } catch (Exception e) { //inicia catch 2

            JOptionPane.showMessageDialog(null, "error base de datos\n " + e);

        }//termina catch 2

    }//termina try 2  y metodo alta 

    //se hace otro metodo para un boton y eliminar datos en la base
    public void Eliminar() {

        //tratamiento de errores (try catch)
        try {//inicia try 

            id = Integer.parseInt(txtIdUsuario.getText());

            con = cn.getConnection();

            st = con.createStatement();

            String sqlBaja = "delete from usuarios where id_usuario=" + id;

            st.executeUpdate(sqlBaja);

            JOptionPane.showMessageDialog(null, "Usuario Eliminado correctamente: " + id + "\n verifica tu consulta");
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "error grave de base de datos N. 3\n " + ex);

        }
    }//termina metodo eliminar 

    //inicia metodo limpiar 
    public void Limpiar() {

        txtIdUsuario.setText(null); //limpia las cajas de texto
        txtUsuario.setText(null);
        txtPassword.setText(null);
        txtIdUsuario.requestFocus(); //posiciona cursos en esa caga de texto
    }

    //metodo modificar datos
    public void EditarUsuarios() {

        String resultId = "";

        try {//inicia try

            id = Integer.parseInt(txtIdUsuario.getText()); //Guarda un valor entero de un usuario
            resultId = String.valueOf(id); //conversion de variable primitiva a objeto
        } catch (NumberFormatException e) {//inicia catsh

            JOptionPane.showMessageDialog(null, "Favor de ingresar solo numeros!!");

        }//termina catch

        String usuarioAcceso = this.txtUsuario.getText(); //guarda usuario

        String passwordAcceso = this.txtPassword.getText(); //guarda pass

        try {//inicia try dos

            if ((resultId.isEmpty()) || (usuarioAcceso.equals("")) || (passwordAcceso.equals(""))) {//inicia if

                JOptionPane.showMessageDialog(rootPane, "Debe ingresar datos");

            } else {

                String sqEditar = "update usuarios set usuario=' "+usuarioAcceso+"',password='"+passwordAcceso+"'where id_usuario="+id ;

                con = cn.getConnection();
                st = con.createStatement();
                st.executeUpdate(sqEditar);

                JOptionPane.showMessageDialog(null, "Usuario modificado de forma exitosa!! \n\n"
                        + "usuario: " + usuarioAcceso);

            }//termina else

        } catch (Exception e) { //inicia catch 2

            JOptionPane.showMessageDialog(null, "error base de datos N 4: \n " + e);

        }//termina catch 2

    }//termina try 2  y metodo modificar usuarios 

    public void listar(){//inicia metodo listar
    
        
        String sql = "select * from usuarios";
        
        
        //inicia tratamiento de errores
        
        
        try {//inicia try
            con = cn.getConnection();
            st =con.createStatement();
            rs=st.executeQuery(sql);
            
            //arreglo de objetos
            Object[] persona = new Object[3];
            String Titulos[] =  {"id_usuario","usuario","password"};
            
            model = new DefaultTableModel(null, Titulos);
            
            model = (DefaultTableModel) TablaDatos.getModel();
            
            while (rs.next()) {
                persona[0] = rs.getString("id_usuario");
                persona[1] = rs.getString("usuario");
                persona[2] = rs.getString("password");
                model.addRow(persona);
       
                
            }
            
            TablaDatos.setModel(model);//imprime las filas en el java Table que esta en la vista 
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "error base de datos BD 5: \n" + e);
            
            
        }//termina catch
        
    }//termina metodo listar
    
    
    //inicia metodo actualizar
    
    public void actualizar(){
    
        try {
            
            DefaultTableModel modeloDatos = new DefaultTableModel();
            
            TablaDatos.setModel(modeloDatos);
            con = cn.getConnection();
            st = con.createStatement();
            rs = st.executeQuery( "select * from usuarios");
            ResultSetMetaData  rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            modeloDatos.addColumn("Id usuario");
            modeloDatos.addColumn("usuario");
            modeloDatos.addColumn("Password");

            while (rs.next()) {
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i+1);
                }//termina for
                modeloDatos.addRow(fila);
            }
            
        } catch (SQLException ex) {//tratamiento de sqlexecption
            JOptionPane.showMessageDialog(null,"error base de datos numero 6: \n"+ ex);
            
        }
        
        
    }//termina metodo actualizar
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_idusuario = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblContra = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        btnRegistro = new javax.swing.JButton();
        panOperaciones = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        btnactualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setText("Examen equipo chairos");

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        lbl_idusuario.setText("id_usuario");

        lblUser.setText("Usuario");

        lblContra.setText("Password");

        txtIdUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdUsuarioActionPerformed(evt);
            }
        });

        btnRegistro.setText("Nuevo Registro");
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_idusuario)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(lblUser)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblContra)
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario)
                            .addComponent(txtIdUsuario)
                            .addComponent(txtPassword)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(btnRegistro)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_idusuario)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContra)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(btnRegistro)
                .addGap(21, 21, 21))
        );

        panOperaciones.setBackground(new java.awt.Color(102, 153, 255));
        panOperaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de sistema de usuario"));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar Usuario");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar Usuario");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnLimpiar1.setText("Limpiar datos");
        btnLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panOperacionesLayout = new javax.swing.GroupLayout(panOperaciones);
        panOperaciones.setLayout(panOperacionesLayout);
        panOperacionesLayout.setHorizontalGroup(
            panOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOperacionesLayout.createSequentialGroup()
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(70, 70, 70)
                .addComponent(btnLimpiar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(24, 24, 24))
        );
        panOperacionesLayout.setVerticalGroup(
            panOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOperacionesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar)
                    .addComponent(btnLimpiar1)
                    .addComponent(btnSalir))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDatos);

        btnactualizar.setText("actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnactualizar)
                .addGap(267, 267, 267))
            .addGroup(layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnactualizar)
                .addGap(118, 118, 118)
                .addComponent(panOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(277, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        // TODO add your handling code here:

        altaUsuarios(); //metodo mandado a llamar 
        Limpiar();
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void txtIdUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUsuarioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        
        System.exit(0);// cierra ventana
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        
        EditarUsuarios();    
        Limpiar();
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiar1ActionPerformed

        Limpiar();
    }//GEN-LAST:event_btnLimpiar1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:

        Eliminar();
        Limpiar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        // TODO add your handling code here:
        
        actualizar();
        
        
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        // TODO add your handling code here:
        
        int row = TablaDatos.getSelectedRow();
        if (row == -1) {
            
            JOptionPane.showMessageDialog(null, "No se pudo seleccionar");
            
        }else {
        
                id = Integer.parseInt((String) TablaDatos.getValueAt(row, 0) .toString());
                String usuario = (String) TablaDatos.getValueAt(row, 1);
                String password = (String) TablaDatos.getValueAt(row, 2);
                
                txtIdUsuario.setText(""+id);
                txtUsuario.setText(usuario);
                txtPassword.setText(password);

        }
        
    }//GEN-LAST:event_TablaDatosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MantenimientoCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantenimientoCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantenimientoCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantenimientoCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantenimientoCRUD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaDatos;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar1;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnactualizar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContra;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lbl_idusuario;
    private javax.swing.JPanel panOperaciones;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
