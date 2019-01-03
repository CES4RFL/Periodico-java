import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ColumnistaGUI extends JFrame implements ActionListener {
    public Container cContenedor;
    public JTable taColumnista;
    public JScrollPane scColumnista;
    public JLabel lUser;
    public JButton btAgregar;
    public JButton btSalir;
    public DefaultTableModel modelo;
    public ResultSet resultado;
    public  PeriodicoDP p= new PeriodicoDP();
    public NoticiaGUI object=new NoticiaGUI();
    public AgregarGUI agregar=new AgregarGUI();
    public String user_send;
    public void ColumnistaGUI(String User) throws IOException  {   

        cContenedor= getContentPane();
        cContenedor.setLayout( null );
        setResizable(false);

        lUser=new JLabel();
        lUser.setText("Mis Articulos "+User);
        lUser.setBounds(10,20,120,20);
        cContenedor.add(lUser);
        JButton ver=new JButton("Ver");



        modelo= new DefaultTableModel();
        
        taColumnista = new JTable(modelo) {
        @Override
        public boolean isCellEditable(int row, int col) {return false;} 
        };

        modelo.addColumn("Titulo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Status");
        modelo.addColumn("Comentario");
        modelo.addColumn("Ver");

        resultado=p.Rowset(User);
        try {
            while (resultado.next()) {
                Object[] fila = new Object[5];

                fila[0]=resultado.getObject(1);
                fila[1]=resultado.getObject(2);
                fila[2]=resultado.getObject(3);
                fila[3]=resultado.getObject(4);
                fila[4]=ver;

                modelo.addRow(fila);
            }
        }catch(SQLException ex) {
            System.out.println("Error llenando tabla");
        }

        taColumnista.setDefaultRenderer(Object.class,new Render());
        taColumnista.setRowHeight(30);
        
        scColumnista= new JScrollPane(taColumnista);
        scColumnista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        btAgregar=new JButton("Agregar");
        btSalir=new JButton("Salir");
        
        scColumnista.setBounds(10, 50,770,250);
        btAgregar.setBounds(10,300,160,50);
        btSalir.setBounds(180,300,160,50);
        
        cContenedor.add(scColumnista);
        cContenedor.add(btAgregar);
        cContenedor.add(btSalir);


        setTitle     ("Mis articulos");
        setSize      ( 800,450 );
        setLocation  ( 100,100 );
        setEventoMouseClicked(taColumnista);
        user_send=User;
         
        btAgregar.addActionListener ( this );
        btSalir.addActionListener ( this );
    } 

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
       if(arg0.getSource()==btAgregar)
	{     
               agregar= new AgregarGUI();
               agregar.AgregarGUI(user_send);
               agregar.setVisible(true);
        }
       
          
       if(arg0.getSource()==btSalir)
	{     dispose();
              LoginGUI p=null;
           try {
               p = new LoginGUI();
           } catch (IOException ex) {
               System.out.println("Error");
           }
               p.setVisible(true);
        }
       
     
    }
    
    private void setEventoMouseClicked(JTable tbl){
        
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
            
                    tablaMouseClicked(e);
               
            }
        }
        );
    }
 
    private void  tablaMouseClicked(java.awt.event.MouseEvent evt){
    String Titulo="";
    int clic_tabla = 0;
    int colum=taColumnista.getColumnModel().getColumnIndexAtX(evt.getX());
    int row=evt.getY()/taColumnista.getRowHeight();
    
    clic_tabla = this.taColumnista.rowAtPoint(evt.getPoint());
    Titulo = (String) taColumnista.getValueAt(clic_tabla, 0);

        if(row < taColumnista.getRowCount() && row >= 0 && colum < taColumnista.getColumnCount() && colum >= 0){
            Object value=taColumnista.getValueAt(row, colum);
                if(value instanceof JButton){
                    ((JButton)value).doClick();
                    JButton boton=(JButton)value;
                    object= new NoticiaGUI();
                    object.NoticiaGUI(Titulo);
                    object.setVisible(true);
                }
        }
    }
    
    
public class Render extends DefaultTableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value,
    boolean isSelected, boolean hasFocus, int row, int column) {
    if (value instanceof JButton) {
      JButton bver=(JButton)value;
      return bver;
    } 
      return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  }
    
}

}
