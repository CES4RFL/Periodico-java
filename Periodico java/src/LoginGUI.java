import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.Container;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class LoginGUI extends JFrame implements ActionListener{  

    public Container   cContenedor ;
    public JLabel      lUser;
    public JTextField  tfUser;
    public JLabel      lPass;
    public JTextField  tfPass;
    public JButton     bAceptar;
    public JComboBox    cbPerfiles;
    public PeriodicoDP ldp = new PeriodicoDP();
    public ColumnistaGUI columnistagui = new ColumnistaGUI();
    public EditorGUI editorgui = new EditorGUI();

    public  LoginGUI() throws IOException {
        // termina el proceso cuando cierro la ventana 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        String[] sPerfiles = {"Columnista", "Editor" };
        cContenedor = getContentPane();
        cContenedor.setLayout( null );
        setResizable(false);
        cbPerfiles= new JComboBox(sPerfiles); 
        lUser = new JLabel(); 
        tfUser = new JTextField();
        lPass = new JLabel(); 
        tfPass = new JTextField();
        bAceptar= new JButton();

        lUser.setText( "Usuario" );
        lPass.setText( "Password" );
        bAceptar.setText( "Aceptar" );
        lUser.setBounds (10, 20,80,30 );
        tfUser.setBounds (100,20,125,30); 
        lPass.setBounds (10,60,80, 30 );
        tfPass.setBounds (100,60,125,30); 
        bAceptar.setBounds (35,150, 160, 50);
        cbPerfiles.setBounds (10,100,100,30);

                          //Agrega componentes al Contenedor
        cContenedor.add ( lUser );
        cContenedor.add ( tfUser );
        cContenedor.add ( lPass );
        cContenedor.add ( tfPass );
        cContenedor.add ( bAceptar );
        cContenedor.add (cbPerfiles);


        setTitle     ("Login");
        setSize      ( 250,250 );
        setLocation  ( 100,100 );
        bAceptar.addActionListener ( this );
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0){
     
    if(arg0.getSource()==bAceptar)
	{       
        try {
            Login();
        } catch (IOException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    }
     
    public void Login() throws IOException {
        String User;
        String Pass;
        String Datos;

        User=tfUser.getText();
        Pass=tfPass.getText();
        Datos= (String) cbPerfiles.getSelectedItem();
        

        boolean valida=ldp.LoginDP(User,Pass,Datos);
        
        if(valida==true){
            if(Datos=="Columnista"){
            columnistagui.ColumnistaGUI(User);
            columnistagui.setVisible(true);
            dispose();
            }
           if(Datos=="Editor"){
            System.out.println("Editor");
            editorgui.EditorGUI();
            editorgui.setVisible(true);
            dispose();
            } 
        }else{
            System.out.println("incorrcto");
        }

        }
    }

