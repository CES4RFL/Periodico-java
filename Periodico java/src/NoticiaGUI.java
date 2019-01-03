import java.awt.Container;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class NoticiaGUI extends JFrame {
    
    public Container cContenedor;
    public JLabel lTitulo;
    public JTextArea taNoticia;
    public ResultSet resultado;
    public JScrollPane scNoticia;
    public JLabel lFecha;
    public JLabel lImagen;
    public ResultSet resultado1;
    public PeriodicoDP ldp = new PeriodicoDP();
    
    public void NoticiaGUI(String titulo)  {
        String  Noticia ="";
        String Fecha = "";
        String Archivo="";
        resultado1=ldp.NoticiaSet(titulo); 

        cContenedor= getContentPane();
        cContenedor.setLayout(null);
        setResizable(false);

        lImagen=new JLabel(Fecha);
        lImagen.setBounds(300,40,200,200);
        
        try{
            while(resultado1.next()){
                    Noticia = resultado1.getString(1);
                    Fecha = "Fecha: "+resultado1.getString(2);
                    String Imagen = resultado1.getString(3); 
                    Archivo="C:\\xampp\\htdocs\\proyecto\\assets\\Imagenes_resp\\"+Imagen;
                    ImageIcon foto = new ImageIcon(Archivo);
                    Image img = foto.getImage();
                    Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newicon = new ImageIcon(newimg);
                
                    lImagen.setIcon(newicon);
            }
            }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane,"Error cargando los datos"+ex);
            }
        lTitulo=new JLabel(titulo);
        lTitulo.setBounds(10,20,200,20);
        taNoticia=new JTextArea(Noticia);
        taNoticia.setBounds(10,40,250,250);
        taNoticia.setLineWrap(true); 
        taNoticia.setWrapStyleWord(true);
        lFecha=new JLabel(Fecha);
        lFecha.setBounds(10,300,200,20);
        scNoticia = new JScrollPane(taNoticia);
        scNoticia.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
   
        
        cContenedor.add(lTitulo);
        cContenedor.add(lFecha);
        cContenedor.add(lImagen);
        cContenedor.add(taNoticia);


        setTitle     ("Noticia");
        setSize      ( 600,400 );
        setLocation  ( 100,100 );

    }
    
   
    
}
