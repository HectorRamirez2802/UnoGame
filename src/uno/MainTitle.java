package uno;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainTitle extends JFrame {

    //Elementos de la Ventana Principal
    private Image logo = new ImageIcon("src/Assets/logo.png").getImage(), img1 = new ImageIcon("src/Assets/Background.jpg").getImage(), imgP2 = new ImageIcon("src/Assets/BotonJ2.png").getImage(), imgP3 = new ImageIcon("src/Assets/BotonJ3.png").getImage(), imgP4 = new ImageIcon("src/Assets/BotonJ4.png").getImage();
    private Image dosSin=new ImageIcon("src/Assets/2JugasoresSinC.png").getImage(),tresSin=new ImageIcon("src/Assets/3JugasoresSinC.png").getImage(),cuatroSin=new ImageIcon("src/Assets/4JugasoresSinC.png").getImage();
    private ImageIcon icP2 = new ImageIcon(imgP2.getScaledInstance(300, 500, Image.SCALE_SMOOTH)), icP3 = new ImageIcon(imgP3.getScaledInstance(370, 300, Image.SCALE_SMOOTH)), icP4 = new ImageIcon(imgP4.getScaledInstance(370, 170, Image.SCALE_SMOOTH));
    private ImageIcon icP2Sin = new ImageIcon(dosSin.getScaledInstance(300, 500, Image.SCALE_SMOOTH)), icP3Sin = new ImageIcon(tresSin.getScaledInstance(370, 300, Image.SCALE_SMOOTH)), icP4Sin = new ImageIcon(cuatroSin.getScaledInstance(370, 170, Image.SCALE_SMOOTH));
    private JLabel fB = new JLabel(), lT = new JLabel();
    private JPanel fon = new JPanel(), cont = new JPanel();

    //Botones 
    private JButton jugar = new JButton(), salir = new JButton(), p2 = new JButton(), p3 = new JButton(), p4 = new JButton();

    //Sonido
    private Sound selectEffect = new Sound(Sound.loadSound("/Sound/select.wav"));
    private Sound mainTheme = new Sound(Sound.loadSound("/Sound/theme1.wav"));

    public MainTitle() {

        //Ajustes Basicos de la ventana
        setSize(800, 600);
        setTitle("UNO");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(logo);
        setLayout(null);

        //Fondo de la Ventana de Inicio
        fon.setLayout(null);
        fon.setBounds(0, 0, 800, 600);
        fB = new JLabel(new ImageIcon(img1.getScaledInstance(800, 600, Image.SCALE_SMOOTH)));
        fB.setBounds(0, 0, 800, 600);
        fon.add(fB);

        //Logo al inicio   
        lT = new JLabel(new ImageIcon(logo.getScaledInstance(380, 320, Image.SCALE_SMOOTH)));
        lT.setBounds(30, 100, 380, 320);

        //Botones
        jugar.setBounds(490, 100, 200, 120);
        jugar.setIcon(new ImageIcon(new ImageIcon("src/Assets/BotonIniciar.jpg").getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH)));
        jugar.addActionListener(new botonesListener());

        salir.setBounds(490, 300, 200, 120);
        salir.setIcon(new ImageIcon( new ImageIcon("src/Assets/BotonSalir.jpg").getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH)));
        salir.addActionListener(new botonesListener());

        //Botones NJugadores
        p2.setBounds(50, 35, 300, 500);
        p2.setIcon(icP2Sin);
        p2.addActionListener(new botonesListener());
        p2.addMouseListener(new escuchadorDos(p2));
        p3.setBounds(380, 35, 370, 300);
        p3.setIcon(icP3Sin);
        p3.addActionListener(new botonesListener());
        p3.addMouseListener(new escuchadorTres(p3));
        p4.setBounds(380, 360, 370, 170);
        p4.setIcon(icP4Sin);
        p4.addActionListener(new botonesListener());
        p4.addMouseListener(new escuchadorCuatro(p4));
        
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);

        add(p2);
        add(p3);
        add(p4);

        //AÃ±adir todo
        add(lT);
        add(jugar);
        add(salir);
        add(fon);

        mainTheme.loop();
    }

    public void numeroJugadores() {
        lT.setVisible(false);
        jugar.setVisible(false);
        salir.setVisible(false);

        p2.setVisible(true);
        p3.setVisible(true);
        p4.setVisible(true);
    }

    public class botonesListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                selectEffect.play();
            }).start();

            if (e.getSource() == jugar) {
                numeroJugadores();
            }
            if (e.getSource() == salir) {
                System.exit(0);
            }
            if (e.getSource() == p2) {
                setVisible(false);
                new Gameplay(2).setVisible(true);
                mainTheme.stop();
            }
            if (e.getSource() == p3) {
                setVisible(false);
                new Gameplay(3).setVisible(true);
                mainTheme.stop();

            }
            if (e.getSource() == p4) {
                setVisible(false);
                new Gameplay(4).setVisible(true);
                mainTheme.stop();
            }
        }
    }
    
    //acciones para darles un toque diferente a los botones en mi consideracion quedan muy bonitos haci
    public class escuchadorDos extends MouseAdapter{
        private JButton boton;
        
        public escuchadorDos(JButton boton){
            this.boton=boton;
        }
        
        @Override
        public void mouseEntered(MouseEvent e){
            boton.setIcon(icP2);
        }
        
        @Override
        public void mouseExited(MouseEvent e){
            boton.setIcon(icP2Sin);
        }
    }
    
    public class escuchadorTres extends MouseAdapter{
        private JButton boton;
        
        public escuchadorTres(JButton boton){
            this.boton=boton;
        }
        
        @Override
        public void mouseEntered(MouseEvent e){
            boton.setIcon(icP3);
        }
        
        @Override
        public void mouseExited(MouseEvent e){
            boton.setIcon(icP3Sin);
        }
    }
    
    public class escuchadorCuatro extends MouseAdapter{
        private JButton boton;
        
        public escuchadorCuatro(JButton boton){
            this.boton=boton;
        }
        
        @Override
        public void mouseEntered(MouseEvent e){
            boton.setIcon(icP4);
        }
        
        @Override
        public void mouseExited(MouseEvent e){
            boton.setIcon(icP4Sin);
        }
    }
    
}
