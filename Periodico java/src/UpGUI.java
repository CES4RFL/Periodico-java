import java.awt.Container;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class UpGUI extends JFrame implements ActionListener {
    
    public Container cContenedor;
    public JLabel lTitulo;
    public JTextArea taNoticia;
    public ResultSet resultado;
    public JScrollPane scNoticia;
    public JComboBox    cbComentario;
    public JComboBox    cbEstado;
    public JButton bActualizar;
    public JLabel lFecha;
    public JLabel lImagen;
    public ResultSet resultado1;
    public PeriodicoDP ldp = new PeriodicoDP();
    
    public void UpGUI(String titulo)  {
        String  Noticia ="";
        String Fecha = "";
        String Archivo="";
        String[] sComentario = {"En revision", "Aprobado","Rechazado" };
        String[] sEstado = {"ONLINE", "OFFLINE" };
        resultado1=ldp.NoticiaSet(titulo); 

        cContenedor= getContentPane();
        cContenedor.setLayout(null);
        setResizable(false);
        
        bActualizar= new JButton("Actualizar");
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
        taNoticia.setLineWrap(true); 
        taNoticia.setWrapStyleWord(true);
        taNoticia.setBounds(10,40,250,250);
        lFecha=new JLabel(Fecha);
        lFecha.setBounds(10,300,200,20);
        scNoticia = new JScrollPane(taNoticia);
        scNoticia.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
   
        cbEstado= new JComboBox(sEstado);
        cbEstado.setBounds(300,250,80,30);
        cbComentario= new JComboBox(sComentario); 
        cbComentario.setBounds(381,250,100,30);
        bActualizar.setBounds(300, 290, 100, 30);
        
        cContenedor.add(lTitulo);
        cContenedor.add(lFecha);
        cContenedor.add(lImagen);
        cContenedor.add(taNoticia);
        cContenedor.add(cbEstado);
        cContenedor.add(cbComentario);
        cContenedor.add(bActualizar);


        setTitle     ("Noticia");
        setSize      ( 600,400 );
        setLocation  ( 100,100 );
        
        bActualizar.addActionListener ( this );

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
         if(arg0.getSource()==bActualizar)
	{       
                String Estado= (String) cbEstado.getSelectedItem();
                String Comenatario= (String) cbComentario.getSelectedItem();
               ldp.Up_Editor(Estado, Comenatario,lTitulo.getText());
               dispose();
               EditorGUI objetoeditor;
               objetoeditor=new EditorGUI();
             try {
                 objetoeditor.EditorGUI();
             } catch (IOException ex) {
                 
                 System.out.println("Error");
             }
               objetoeditor.setVisible(true);
               
        }
    }
    
}