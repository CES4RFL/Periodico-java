import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PeriodicoDP {

    Connection miconexion;
    Statement mienunciado;
    ResultSet resultado;

    //cadena de conexion  en el contructor 
    public PeriodicoDP() {
        try {
            String url = "jdbc:mysql://localhost:3306/periodico";
            String usuario = "root";
            String password = "";
            
            miconexion = DriverManager.getConnection(url, usuario, password);
            
            System.out.println("Conexion exitosa con Mysql periodico");
        }catch(SQLException sqe) {
            System.out.println(sqe + "Error de conexion");
        }
    }//fin de intento de conexion 
    
    //clase principal de que va a utilizar java 
      public static void main(String[] args) throws SQLException, IOException {
        LoginGUI p = new LoginGUI();
        p.setVisible(true);
    }
      

    public boolean LoginDP(String user, String pass, String perfil) {
        String Query = "SELECT * FROM Usuarios where USUARIO='" + user + "' and PASS='" + pass + "' and PERFIL='" + perfil + "'";
        String resultadoBusqueda = "";
        try {
            mienunciado = miconexion.createStatement();
            resultado = mienunciado.executeQuery(Query);
      
            while (resultado.next()) {
                String Nombre = resultado.getString(2);
                String Pass = resultado.getString(3);
                String Perfil = resultado.getString(4);
                resultadoBusqueda = resultadoBusqueda + Nombre + "_" + Pass + "_" + Perfil + "\n";
            }

            if (!"".equals(resultadoBusqueda)) { 
                return true;
            }
            if ("".equals(resultadoBusqueda)) {
                return false;
            };
        }catch(SQLException sqle){
            System.out.println(sqle + "\nError en la consulta");
        }
        return false;
    }

    public ResultSet Rowset(String user){
      
        String Query = "SELECT Titulo,Fecha,Estatus,Comentario FROM Columnista where Usuario='" + user + "'";
        try {
            mienunciado = miconexion.createStatement();
            resultado = mienunciado.executeQuery(Query);
            return resultado;
        }catch (SQLException sqle) {
            System.out.println(sqle + "\nError en la consulta");
        }
        return null;
     }
    
    public ResultSet NoticiaSet(String titulo) {
        String Query = "SELECT Noticia,Fecha,Archivo FROM Columnista where Titulo='" + titulo + "'";
        try {
            mienunciado = miconexion.createStatement();
            resultado = mienunciado.executeQuery(Query);
            System.out.println("Ejecutado");
            return resultado;
          
        } catch (SQLException sqle) {
            System.out.println(sqle + "\nError en la consulta");
         
        }
        return null;
    }
    
        public void InsertNoticia(String titulo, String noticia,String imagen_s,String usuario,String archivo) {
        String Query = "INSERT INTO Columnista (id_noticia, titulo, noticia, fecha, estatus, imagen,archivo, comentario, usuario)\n" +
        "VALUES (NULL,?,?,SYSDATE(),'OFFLINE',?,?,'En revision',?);";
            
         PreparedStatement ps;
           
        try{
            ps = miconexion.prepareStatement(Query);
            ps.setString(1, titulo);
            ps.setString (2, noticia);
            ps.setString (3, imagen_s);
            ps.setString (4, archivo);
            ps.setString (5, usuario);
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error en el insert");
        }
       
   }
   
       public ResultSet Editor_Sel(){
      
        String Query = "SELECT Titulo,Fecha,Estatus,Comentario FROM Columnista";
        try {
            mienunciado = miconexion.createStatement();
            resultado = mienunciado.executeQuery(Query);
            System.out.println("ejecutado");
            return resultado;
        }catch (SQLException sqle) {
            System.out.println(sqle + "\nError en la consulta");
        }
        return null;
     }
    
     public void Up_Editor(String Estatus, String Comentario, String Titulo){
        
         System.out.println(Estatus+Comentario+Titulo);
        String Query = "UPDATE columnista SET estatus=? , comentario=? Where titulo='"+Titulo+"'";
        
       
        PreparedStatement ps;
           
        try{
            ps = miconexion.prepareStatement(Query);
            ps.setString(1, Estatus);
            ps.setString (2, Comentario);;
            ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error en el up");
        }
     }
}
    
       

