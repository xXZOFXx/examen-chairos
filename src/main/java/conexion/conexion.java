/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




package conexion;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.*;


/**
 *
 * @author zof
 */
public class conexion {
    
    
    
      //conexion
    
    Connection con;
    
    //constructor 
    
    public conexion(){
    
    //estructura de control de tratamiento de errores. try catch
    
    
        try {
           
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/basexamen","root","");
            
        } catch (Exception e) {
            
            System.err.println("Error de base de datos: " + e);
            
        }
    
    
 
        
    }
    
    
    //metodo de tipo conexion
    public Connection getConnection(){
    
        return con;
    
    }
    
}
