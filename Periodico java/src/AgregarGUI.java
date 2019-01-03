
import java.awt.Container;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class AgregarGUI  extends JFrame implements ActionListener{
    
    public Container cContenedor;
    public JTextField tfTitulo;
    public JLabel lAutor;
    public JTextArea taNoticia;
    public JScrollPane scNoticia;
    public JButton bSubir;
    public JButton bAbrir;
    public JLabel  lImagen;
    public JLabel  lNoticia;
    public JTextField  tfUrl;
    public JLabel  lTitulo;
    public JLabel  lVim;
    String sImagen;
    String user;
    String Archivo;
   
    
    
    public void  AgregarGUI(String Autor) {

   
    cContenedor= getContentPane();
    cContenedor.setLayout(null);
    setResizable(false);
    user=Autor;

    tfTitulo = new JTextField();
    lAutor = new JLabel("Autor: " + Autor);
    lTitulo = new JLabel("Titulo:");
    tfUrl = new JTextField();
    lNoticia = new JLabel("Noticia:");
    lImagen = new JLabel("Imagen:");
    lVim = new JLabel();
    taNoticia=new JTextArea();
    taNoticia.setLineWrap(true); 
    taNoticia.setWrapStyleWord(true); 
    bSubir= new JButton("Subir");
     bAbrir= new JButton("...");
     scNoticia = new JScrollPane(taNoticia);
     scNoticia.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
   
     lAutor.setBounds(10, 20, 120, 20);
     lTitulo.setBounds(10, 50, 80, 20);
     tfTitulo.setBounds(60, 40, 330, 40);
     lNoticia.setBounds(10,100,80,20);
     scNoticia.setBounds(10, 120, 380, 340);
     lImagen.setBounds(420 , 20, 80, 20);
     tfUrl.setBounds(470, 40, 290, 20);
     bAbrir.setBounds(760, 40, 20, 20);
     lVim.setBounds(450,60,400,400);
     bSubir.setBounds(10, 480, 80, 20);

     cContenedor.add(lAutor);
     cContenedor.add(lTitulo);
     cContenedor.add(tfTitulo);
     cContenedor.add(lNoticia);
     cContenedor.add(scNoticia);
     cContenedor.add(lImagen);
     cContenedor.add(tfUrl);
     cContenedor.add(bAbrir);
     cContenedor.add(lVim);
     cContenedor.add(bSubir);
     
     
     setTitle     ("Noticia");
     setSize      ( 840,600 );
     setLocation  ( 100,100 );
      
      bAbrir.addActionListener ( this );
      bSubir.addActionListener ( this );
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
          if(arg0.getSource()==bAbrir)
	{       
           JFileChooser url= new JFileChooser();
           int ap=url.showOpenDialog(this);
           
           if(ap==JFileChooser.APPROVE_OPTION){
               sImagen= url.getSelectedFile().getAbsolutePath();   
               Archivo= url.getSelectedFile().getName();
                    String rpta= sImagen;
                    ImageIcon foto = new ImageIcon(rpta);
                    Image img = foto.getImage();
                    Image newimg = img.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                    ImageIcon newicon = new ImageIcon(newimg);
                
               lVim.setIcon(newicon);
               tfUrl.setText(sImagen);
           }
	}
        
            if(arg0.getSource()==bSubir)
	{       
           
              try {
                  String titulo = tfTitulo.getText();
                  String noticia = taNoticia.getText();
                  String icono =  sImagen;
                  String usuario=user;
                  String archivo=Archivo;
                  File origen = new File(sImagen);
                  System.out.println("C:\\xampp\\htdocs\\proyecto\\assets\\Imagenes_resp"+Archivo);
                  File destino = new File("C:\\xampp\\htdocs\\proyecto\\assets\\Imagenes_resp\\"+Archivo);
                  InputStream in = new FileInputStream(origen);
                  OutputStream out = new FileOutputStream(destino);
                  byte[] buf = new byte[1024];
                  int len;
 
                    while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                    }
                    
                  in.close();
                  out.close();
                  PeriodicoDP ldp=new PeriodicoDP();
                  ldp.InsertNoticia(titulo, noticia, icono, usuario,archivo);
                  ColumnistaGUI columnistagui= new ColumnistaGUI();
                  columnistagui.ColumnistaGUI(usuario);
                  columnistagui.setVisible(true);
                  dispose();
              } catch (IOException ex) {
                  JOptionPane.showMessageDialog(rootPane,"Error generando noticia"+ex);
              }
	}
       
    }
}
    
  
    

