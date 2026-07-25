package uno;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JFrame {

    /*favor de agregarle los nombres en la parte de arriba a los jugadores para poder 
    visualizar a quien le toca y si los demas metodos estan bien diseñados*/
    //Ventana de Juego
    private JPanel cont = new JPanel();
    private ImageIcon fondo2 = new ImageIcon(new ImageIcon("src/Assets/back2.jpg").getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH));

    //contenedor para las cartas DiseÃ±o
    //quite lops otros dos arrys list optimizando un poco el codigo
    private ArrayList<JLabel> arriba = new ArrayList<>();
    private ArrayList<JLabel> abajo = new ArrayList<>();

    //Carta en medio
    private JLabel cartaDeEnmedio = new JLabel();
    private Carta aux = new Carta();

    //Mecanicas
    private Jugador[] jugador;
    private Carta[][] cartasN = new Carta[4][9];
    private Carta[] cartasE = new Carta[14];
    private int contador = 0;
    private JButton confirmation = new JButton("Continuar");
    private boolean hayWinner = false;
    private boolean event = false;

    //Miscelaneo
    private ImageIcon iCRED = new ImageIcon(new ImageIcon("src/Assets/UnknowCard.png").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
    private JLabel mazo = new JLabel(iCRED);

    private JLabel cCAzul = new JLabel();
    private JLabel cCAmarillo = new JLabel();
    private JLabel cCRojo = new JLabel();
    private JLabel cCVerde = new JLabel();

    private JLabel masAzul = new JLabel();
    private JLabel masAmarillo = new JLabel();
    private JLabel masRojo = new JLabel();
    private JLabel masVerde = new JLabel();

    private JLabel nombreJugador = new JLabel();
    private Font font = new Font("NiseSegaSonic", Font.PLAIN, 28);
    private Font font2 = new Font("NiseSegaSonic", Font.PLAIN, 25);

    private Sound cardEffect = new Sound(Sound.loadSound("/Sound/card.wav"));
    private Sound cardEffect2 = new Sound(Sound.loadSound("/Sound/card2.wav"));
    private Sound winnerTheme = new Sound(Sound.loadSound("/Sound/musicaGanador.wav"));
    private Sound winnerTheme2 = new Sound(Sound.loadSound("/Sound/musicaGanador2.wav"));

    private Sound twoPlayerTheme = new Sound(Sound.loadSound("/Sound/twoPlayerTheme.wav"));
    private Sound threePlayerTheme = new Sound(Sound.loadSound("/Sound/threePlayerTheme.wav"));
    private Sound fourPlayerTheme = new Sound(Sound.loadSound("/Sound/fourPlayerTheme.wav"));

    //para el cambio de direccion
    private boolean direc = true;
    private JLabel direccion = new JLabel();

    private int sumameLasCartasPalRival = 0;
   
    private ImageIcon cartastats = new ImageIcon(new ImageIcon("src/Assets/estadisticas.png").getImage().getScaledInstance(500, 650, Image.SCALE_SMOOTH));

    //Animacion de don coco
    private JLabel cocodilo = new JLabel(new ImageIcon(new ImageIcon("src/Assets/coco.png").getImage().getScaledInstance(500, 1000, Image.SCALE_SMOOTH)));
    private JLabel coronita = new JLabel(new ImageIcon(new ImageIcon("src/Assets/corona.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

    //Boton de salir, me da weba cerrar y abrir el programa xd 
    private JLabel salir = new JLabel();

    public Gameplay(int jugadores) {
        jugador = new Jugador[jugadores];

        if (jugadores == 2) {
            twoPlayerTheme.changeVolume(-10f);
            twoPlayerTheme.loop();
        } else if (jugadores == 3) {
            threePlayerTheme.loop();
            threePlayerTheme.changeVolume(-10f);
        } else {
            fourPlayerTheme.loop();
            fourPlayerTheme.changeVolume(-10f);
        }

        //Ajustes basicos de la ventana
        setSize(1200, 700);
        setTitle("UNO");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/Assets/logo.png").getImage());
        setLayout(null);

        //Fondo 
        //cambiel el metodo con el que agrgas un fondo al panel
        cont = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo2.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        cont.setLayout(null);
        cont.setBounds(0, 0, 1200, 700);

        //Inicializacion de cartas y reparticion de las mismas   
        inicializaCartas();
        repartirCartas();

        //inicializa las cartas que nos dan problemas
        inicializaLosMas4();
        incializaLosCambiaColor();

        //Agragar la carta de en medio para que al momento de agregar el mouse listener aux no quede definido en dicho como null y evite la interaccion
        agregaLaCartaDeEnmedio();

        //Inicializacion de los mazos del jugador y los rivales
        nombredelWey();
        jugadorP();
        agregarDisRivales();
        for (int i = 0; i < abajo.size(); i++) {
            cont.add(abajo.get(i));
        }
        for (int i = 0; i < arriba.size(); i++) {
            cont.add(arriba.get(i));
        }

        //Otras cosas Tecnicas
        confirmation.setVisible(false);
        confirmation.addActionListener(new siguienteListener());
        add(confirmation);

        //Ajustes visuales
        cartaDeEnmedio.setBounds(450, 250, 100, 150);
        mazo.setBounds(585, 240, 110, 165);
        mazo.addMouseListener(new mazoDeEnmedio( mazo));

        //agrega el label que muestra la direccion hacia donde va el juego
        muestraDireccion();
        direccion.setBounds(750, 240, 75, 75);

        //Boton de salida
        salir.setIcon(new ImageIcon(new ImageIcon("src/Assets/salir.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        salir.setBounds(0, 0, 50, 50);
        salir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                twoPlayerTheme.stop();
                threePlayerTheme.stop();
                fourPlayerTheme.stop();
                new MainTitle().setVisible(true);
            }
        });

        //Agregando el merequetengue         
        cont.add(direccion);
        cont.add(cartaDeEnmedio);
        cont.add(mazo);
        cont.add(salir);
        add(cont);
    }

    public void inicializaCartas() {
        for (int i = 1; i <= 9; i++) {
            cartasN[0][i - 1] = new Carta("src/CartasUno/" + i + "Amarillo.jpg", "Amarillo", i);
            cartasN[1][i - 1] = new Carta("src/CartasUno/" + i + "Azul.jpg", "Azul", i);
            cartasN[2][i - 1] = new Carta("src/CartasUno/" + i + "Rojo.jpg", "Rojo", i);
            cartasN[3][i - 1] = new Carta("src/CartasUno/" + i + "Verde.jpg", "Verde", i);
        }

        cartasE[0] = new Carta("src/CartasUno/+2Color_1.jpg", "Amarillo", "+2");
        cartasE[1] = new Carta("src/CartasUno/+2Color_2.jpg", "Azul", "+2");
        cartasE[2] = new Carta("src/CartasUno/+2Color_3.jpg", "Rojo", "+2");
        cartasE[3] = new Carta("src/CartasUno/+2Color_4.jpg", "Verde", "+2");
        cartasE[4] = new Carta("src/CartasUno/+4.jpg", "Negro", "+4");
        cartasE[5] = new Carta("src/CartasUno/BloqueoColor_1.jpg", "Amarillo", "Bloqueo");
        cartasE[6] = new Carta("src/CartasUno/BloqueoColor_2.jpg", "Azul", "Bloqueo");
        cartasE[7] = new Carta("src/CartasUno/BloqueoColor_3.jpg", "Rojo", "Bloqueo");
        cartasE[8] = new Carta("src/CartasUno/BloqueoColor_4.jpg", "Verde", "Bloqueo");
        cartasE[9] = new Carta("src/CartasUno/RColor_1.jpg", "Amarillo", "Reversa");
        cartasE[10] = new Carta("src/CartasUno/RColor_2.jpg", "Azul", "Reversa");
        cartasE[11] = new Carta("src/CartasUno/RColor_3.jpg", "Rojo", "Reversa");
        cartasE[12] = new Carta("src/CartasUno/RColor_4.jpg", "Verde", "Reversa");
        cartasE[13] = new Carta("src/CartasUno/CCNegro.jpg", "Negro", "CambiaColor");
    }

    public void repartirCartas() {
        for (int i = 0; i < jugador.length; i++) {
            jugador[i] = new Jugador("Jugador " + (i + 1));
            ArrayList<Carta> agregar = new ArrayList<Carta>();
            for (int j = 1; j <= 7; j++) {
                int x = ((int) (Math.random() * 5));
                if (x == 4) {
                    x = ((int) (Math.random() * 14));
                    agregar.add(cartasE[x]);
                } else {
                    int y = ((int) (Math.random() * 9));
                    agregar.add(cartasN[x][y]);
                }
            }
            jugador[i].setMaso(agregar);
        }
    }

    public void jugadorP() {
        // Limpia los arrays de labels y cartas del jugador
        abajo.clear();
        if (direc) {
            if (contador == jugador.length) {
                contador = 0;
            }
        } else {
            if (contador == -1) {
                contador = jugador.length - 1;
            }
        }

        // Vuelve a agregar las cartas del jugador a los arrays de labels y cartas
        int x = 0;
        int auxIz, auxDe;
        int derecha = auxDe = 1, izquierda = auxIz = -1;
        
        for (int i = 0; i < jugador[contador].getMaso().size(); i++) {

            Carta card = jugador[contador].getMaso().get(i);
            JLabel cartitaMamalona = new JLabel(new ImageIcon(new ImageIcon(card.getDireccion()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));

            // MÃ©todo especificado en la parte de abajo, que otorga una acciÃ³n al label con el mouse
            cartitaMamalona.addMouseListener(new MiMouseListener(card, cartitaMamalona, aux));

            if (i <= 14) {
                if (i == 0) {
                    x = 551;
                } else if (i % 2 == 0) {
                    x = (551 + (derecha * 65));
                    derecha++;
                } else {
                    x = (551 + (izquierda * 65));
                    izquierda--;
                }
                cartitaMamalona.setBounds(x, 560, 100, 150);
            } else {
                if (i == 15) {
                    x = 551;
                } else if (i % 2 == 0) {
                    x = (551 + (auxDe * 65));
                    auxDe++;
                } else {
                    x = (551 + (auxIz * 65));
                    auxIz--;
                }
                cartitaMamalona.setBounds(x, 490, 100, 150);
            }

            abajo.add(cartitaMamalona);
        }
    }

    public void ayudameChavito() {
        //inicializa con la estrutura de arriba
        abajo.clear();
        if (direc) {
            if (contador == jugador.length) {
                contador = 0;
            }
        } else {
            if (contador == -1) {
                contador = jugador.length - 1;
            }
        }

        int y = 0;

        for (int i = 0; i < jugador[contador].getMaso().size(); i++) {

            Carta card = jugador[contador].getMaso().get(i);

            if (card == cartasE[0] || card == cartasE[1] || card == cartasE[2] || card == cartasE[3] || card == cartasE[4]) {

                int x = (300 + (y * 70));
                JLabel cartitaMamalona = new JLabel(new ImageIcon(new ImageIcon(card.getDireccion()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));

                // MÃ©todo especificado en la parte de abajo, que otorga una acciÃ³n al label con el mouse
                cartitaMamalona.addMouseListener(new MiMouseListener(card, cartitaMamalona, aux));
                cartitaMamalona.setBounds(x, 540, 100, 150);

                abajo.add(cartitaMamalona);
                y++;
            }
        }
    }

    //agrega el diseÃ±o de los rivales
    public void agregarDisRivales() {
        int auxAl, auxBa;
        int alto = auxAl = 1, bajo = auxBa = -1;
        int y = 0;

        for (int i = 0; i < jugador[jugadorDeJunto(1)].getMaso().size(); i++) {
            if (i <= 25) {
                JLabel cartitaMamalona = agregarCartasAlPanel(1);
                if (i <= 12) {
                    if (i == 0) {
                        y = 551;
                    } else if (i % 2 == 0) {
                        y = (551 + (alto * 65));
                        alto++;
                    } else {
                        y = (551 + (bajo * 65));
                        bajo--;
                    }
                    cartitaMamalona.setBounds(y, -20, 100, 150);
                } else {
                    if (i == 13) {
                        y = 551;
                    } else if (i % 2 == 0) {
                        y = (551 + (auxAl * 65));
                        auxAl++;
                    } else {
                        y = (551 + (auxBa * 65));
                        auxBa--;
                    }
                    cartitaMamalona.setBounds(y, 0, 100, 150);
                }
                arriba.add(cartitaMamalona);
            }
        }

        if (jugador.length >= 3) {
            alto = auxAl = 1;
            bajo = auxBa = -1;
            for (int i = 0; i < jugador[jugadorDeJunto(2)].getMaso().size(); i++) {
                //una limitacion para que no se muestren el pantalla tantas cartas y manden a la chingada el diseño
                if (i <= 17) {
                    JLabel cartitaMamalona = agregarCartasAlPanel(2);
                    if (i <= 8) {
                        if (i == 0) {
                            y = 300;
                        } else if (i % 2 == 0) {
                            y = (300 + (alto * 60));
                            alto++;
                        } else {
                            y = (300 + (bajo * 60));
                            bajo--;
                        }
                        cartitaMamalona.setBounds(-50, y, 145, 100);
                    } else {
                        if (i == 9) {
                            y = 300;
                        } else if (i % 2 == 0) {
                            y = (300 + (auxAl * 60));
                            auxAl++;
                        } else {
                            y = (300 + (auxBa * 60));
                            auxBa--;
                        }
                        cartitaMamalona.setBounds(-30, y, 145, 100);
                    }
                    arriba.add(cartitaMamalona);
                }
            }
        }

        if (jugador.length == 4) {
            alto = auxAl = 1;
            bajo = auxBa = -1;
            for (int i = 0; i < jugador[jugadorDeJunto(3)].getMaso().size(); i++) {
                //una limitacion para que no se muestren el pantalla tantas cartas y manden a la chingada el diseño
                if (i <= 17) {
                    JLabel cartitaMamalona = agregarCartasAlPanel(3);
                    if (i <= 8) {
                        if (i == 0) {
                            y = 300;
                        } else if (i % 2 == 0) {
                            y = (300 + (alto * 60));
                            alto++;
                        } else {
                            y = (300 + (bajo * 60));
                            bajo--;
                        }
                        cartitaMamalona.setBounds(1090, y, 145, 100);
                    } else {
                        if (i == 9) {
                            y = 300;
                        } else if (i % 2 == 0) {
                            y = (300 + (auxAl * 60));
                            auxAl++;
                        } else {
                            y = (300 + (auxBa * 60));
                            auxBa--;
                        }
                        cartitaMamalona.setBounds(1070, y, 145, 100);
                    }
                    arriba.add(cartitaMamalona);
                }
            }
        }
    }

    public JLabel agregarCartasAlPanel(int poscicion) {
        JLabel cartitaMamalona = new JLabel();
        if (poscicion == 1) {
            cartitaMamalona.setIcon(iCRED);
        } else if (poscicion == 2) {
            cartitaMamalona.setIcon(new ImageIcon(new ImageIcon("src/Assets/UnknowCardIz.png").getImage().getScaledInstance(145, 100, Image.SCALE_SMOOTH)));
        } else if (poscicion == 3) {
            cartitaMamalona.setIcon(new ImageIcon(new ImageIcon("src/Assets/UnknowCardDer.png").getImage().getScaledInstance(145, 100, Image.SCALE_SMOOTH)));
        }
        return cartitaMamalona;
    }

    //verifiva el jugador que esta junto y retorna su poscicion 
    public int jugadorDeJunto(int x) {
        int indice = (contador + x) % jugador.length;
        if (indice < 0) {
            indice += jugador.length;
        }
        return indice;

    }

    //PARTE JODIDAMENTE INESTABLE DEL CODIGO, FAVOR DE PROCEDER A MODIFICAR CON CUIDADO PORQUE SE PUEDE IR A LA CHUUUUCHAAAAA
    public void agregaLaCartaDeEnmedio() {
        if (cartaDeEnmedio.getIcon() == null) {//QUE TE DIJE DE LOS PARENTESIS HJDTPLDMMASDPFMASIODFNAIOSD
            aux = cartasN[ (int) (Math.random() * 4) ] [ (int) (Math.random() * 9) ];
            cartaDeEnmedio.setIcon(new ImageIcon(new ImageIcon(aux.getDireccion()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        }
    }

    //hace lo que dice su nombre, sera ocupado en los demas metodos
    public void sustituyeCartaDeEnmedio(Carta carta) {
        aux = carta;
        cartaDeEnmedio.setIcon(new ImageIcon(new ImageIcon(aux.getDireccion()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
    }

    //Elimina la carta del label del jugador  sin embargo tiene un error que impide remover la primera carta
    public void eliminaCarta(Carta carta) {
        new Thread(() -> {
            cardEffect.play();
        }).start();
        for (int i = 0; i < jugador[contador].getMaso().size(); i++) {
            Carta cartaActual = jugador[contador].getMaso().get(i);
            if (cartaActual.equals(carta)) {
                jugador[contador].getMaso().remove(i);
                break; // Termina el bucle una vez que se elimina la carta
            }
        }
    } //No he visto el error honestamente supongo que ya estara corregido?

    //necesitas explicacion?
    //Nah se entiende perfecto, me gusta. 
    public void muestraDireccion() {
        if (direc) {
            direccion.setIcon(new ImageIcon(new ImageIcon("src/Assets/AntiHorario.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
        } else {
            direccion.setIcon(new ImageIcon(new ImageIcon("src/Assets/Horario.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
        }
    }

    //su puta perra madre me tarde hora y media solo haciendo esto, en fin le otorga una accion diferente a cada carta
    public class MiMouseListener extends MouseAdapter {

        private Carta carta, comparador;
        private JLabel label;

        public MiMouseListener(Carta carta, JLabel label, Carta comparador) {
            this.carta = carta;
            this.label = label;
            this.comparador = comparador;
        }
        //le otorga una carta a cartaDeEnmedio y una direccion de memoria a aux    

        //cuando una carta es presionada realiza una accion
        @Override
        public void mouseClicked(MouseEvent e) {
            if (carta.getTipo() == null) {//si la carta es normal getTipo es null por lo q entrara al bloque
                if (carta.getColor() == comparador.getColor() || carta.getValor() == comparador.getValor()) {
                    sustituyeCartaDeEnmedio(carta);
                    eliminaCarta(this.carta);
                    label.removeMouseListener(this);
                    jugador[contador].setCartasJugadas(jugador[contador].getCartasJugadas() + 1);
                    comprobarGanador();

                    if (direc) {
                        contador++;
                    } else {
                        contador--;
                    }

                    cambiaTurno();

                }
            } else {//ic aun mas optimizaciones de codigo en para los if reduciendo el codigo
                //si es una carta especial entra a este bloque
                //este bloque sirve para el cambia color y el +4
                if (carta.getColor() == "Negro") {
                    cambiameElColor(carta);
                    label.removeMouseListener(this);
                    eliminaCarta(this.carta);
                    comprobarGanador();

                    if (direc) {
                        contador++;
                    } else {
                        contador--;
                    }

                    if (carta.getTipo() == "+4") {
                        seLaMetoALosRivales(4, this.carta);
                    }

                    //bloque q sirve para el +2
                } else if ((carta.getTipo() == "+2" && carta.getColor() == comparador.getColor()) || (carta.getTipo() == "+2" && comparador.getTipo() == "+2") || (carta.getTipo() == "+2" && comparador.getTipo() == "+4")) {
                    sustituyeCartaDeEnmedio(carta);
                    eliminaCarta(this.carta);
                    label.removeMouseListener(this);
                    jugador[contador].setMasDosJugados(jugador[contador].getMasDosJugados() + 1);
                    comprobarGanador();

                    //agrgar metodo +2
                    if (direc) {
                        contador++;
                    } else {
                        contador--;
                    }
                    seLaMetoALosRivales(2, this.carta);
                    cambiaTurno();

                    //bloque que sirve para el cambio de direccion ☝️🤓
                } else if ((carta.getTipo() == "Reversa" && comparador.getColor() == carta.getColor()) || (carta.getTipo() == "Reversa" && comparador.getTipo() == "Reversa")) {
                    sustituyeCartaDeEnmedio(carta);
                    eliminaCarta(this.carta);
                    label.removeMouseListener(this);
                    jugador[contador].setReversasJugados(jugador[contador].getReversasJugados() + 1);
                    comprobarGanador();

                    if (jugador.length != 2) {
                        if (direc) {
                            direc = false;
                            contador--;
                        } else {
                            direc = true;
                            contador++;
                        }
                    }

                    muestraDireccion();
                    cambiaTurno();

                    //bloque que sirve para bloqueo del jugador siguiente  
                } else if ((carta.getTipo() == "Bloqueo" && comparador.getColor() == carta.getColor()) || (carta.getTipo() == "Bloqueo" && comparador.getTipo() == "Bloqueo")) {
                    sustituyeCartaDeEnmedio(carta);
                    eliminaCarta(this.carta);
                    label.removeMouseListener(this);
                    jugador[contador].setBloqueosJugados(jugador[contador].getBloqueosJugados() + 1);
                    comprobarGanador();

                    //agregar metodo
                    if (jugador.length != 2 && jugador.length != 3) {
                        if (direc) {
                            if (contador == 3) {
                                contador = 1;
                            } else {
                                contador += 2;
                            }
                        } else {
                            if (contador == 0) {
                                contador = 2;
                            } else {
                                contador -= 2;
                            }
                        }
                    }

                    //3 Jugadores
                    if (jugador.length == 3) {
                        if (direc) {
                            contador += 2;
                            if (contador == 3) {
                                contador = 0;
                            } else if (contador == 4) {
                                contador = 1;
                            }
                        } else {
                            contador -= 2;
                            if (contador == -2) {
                                contador = 1;
                            } else if (contador == -1) {
                                contador = 2;
                            }
                        }
                    }

                    cambiaTurno();
                }
            }
        }

        //cuando el maus pasa sobre una carta realiza una accion
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setBounds(label.getX(), label.getY() - 50, 100, 150);
        }

        //cuando el maus sale de la carta esta realiaza otra accion
        @Override
        public void mouseExited(MouseEvent e) {
            label.setBounds(label.getX(), label.getY() + 50, 100, 150);
        }
    }

    public void incializaLosCambiaColor() {
        cCAzul.setIcon( new ImageIcon(new ImageIcon("src/Assets/CCAzul.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        cCAmarillo.setIcon(new ImageIcon(new ImageIcon("src/Assets/CCAmarillo.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        cCRojo.setIcon( new ImageIcon(new ImageIcon("src/Assets/CCRojo.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        cCVerde.setIcon(new ImageIcon(new ImageIcon("src/Assets/CCVerde.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));

        cCAzul.setBounds(290, 540, 100, 150);
        cCAmarillo.setBounds(430, 540, 100, 150);
        cCRojo.setBounds(570, 540, 100, 150);
        cCVerde.setBounds(710, 540, 100, 150);
    }

    public void inicializaLosMas4() {
        masAzul.setIcon( new ImageIcon(new ImageIcon("src/Assets/+4Azul.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        masAmarillo.setIcon(new ImageIcon(new ImageIcon("src/Assets/+4Amarillo.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        masRojo.setIcon(new ImageIcon(new ImageIcon("src/Assets/+4Rojo.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
        masVerde.setIcon(new ImageIcon(new ImageIcon("src/Assets/+4Verde.jpg").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));

        masAzul.setBounds(290, 540, 100, 150);
        masAmarillo.setBounds(430, 540, 100, 150);
        masRojo.setBounds(570, 540, 100, 150);
        masVerde.setBounds(710, 540, 100, 150);
    }

    //metodo que sirve para la carta cambiaColor (igual deberia de funcionar para el +4)
    public void cambiameElColor(Carta carta) {
        event = true;
        for (int i = 0; i < abajo.size(); i++) {
            abajo.get(i).setVisible(false);
        }
        //las chingadas cartas +4 no desaparecen despues de darles click, es la misma sintaxis que con las cambiaColor, pero
        //estas no se estan comportando igual, porfa checa que pedo, a veces jala bien, y otras falla, es una mamada
        //igual el metodo tarda un poco en cargar, ten paciencia xd
        if (carta.getTipo() == "CambiaColor") {
            cCAzul.addMouseListener(new accionCambiaColor(cCAzul));
            cCAmarillo.addMouseListener(new accionCambiaColor(cCAmarillo));
            cCVerde.addMouseListener(new accionCambiaColor(cCVerde));
            cCRojo.addMouseListener(new accionCambiaColor(cCRojo));

            cont.add(cCAzul);
            cont.add(cCRojo);
            cont.add(cCAmarillo);
            cont.add(cCVerde);

            cCAzul.setVisible(true);
            cCAmarillo.setVisible(true);
            cCRojo.setVisible(true);
            cCVerde.setVisible(true);

        } else {
            masAzul.addMouseListener(new accionCambiaColor(masAzul));
            masAmarillo.addMouseListener(new accionCambiaColor(masAmarillo));
            masVerde.addMouseListener(new accionCambiaColor(masVerde));
            masRojo.addMouseListener(new accionCambiaColor(masRojo));

            cont.add(masAzul);
            cont.add(masRojo);
            cont.add(masAmarillo);
            cont.add(masVerde);

            masAzul.setVisible(true);
            masAmarillo.setVisible(true);
            masRojo.setVisible(true);
            masVerde.setVisible(true);
        }
        cont.repaint();
    }

    //accion de las cartas cambiaColor o +4
    public class accionCambiaColor extends MouseAdapter {
        private JLabel label;
        
        public accionCambiaColor(JLabel label){
            this.label=label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == cCAzul) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/CCAzul.jpg", "Azul", "CambiaColor"));
            } else if (e.getSource() == cCAmarillo) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/CCAmarillo.jpg", "Amarillo", "CambiaColor"));
            } else if (e.getSource() == cCRojo) {
                sustituyeCartaDeEnmedio( new Carta("src/Assets/CCRojo.jpg", "Rojo", "CambiaColor"));
            } else if (e.getSource() == cCVerde) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/CCVerde.jpg", "Verde", "CambiaColor"));
            } else if (e.getSource() == masAmarillo) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/+4Amarillo.jpg", "Amarillo", "+4"));
            } else if (e.getSource() == masAzul) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/+4Azul.jpg", "Azul", "+4"));
            } else if (e.getSource() == masRojo) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/+4Rojo.jpg", "Rojo", "+4"));
            } else if (e.getSource() == masVerde) {
                sustituyeCartaDeEnmedio(new Carta("src/Assets/+4Verde.jpg", "Verde", "+4"));
            }

            if (e.getSource() == masVerde || e.getSource() == masRojo || e.getSource() == masAzul || e.getSource() == masAmarillo) {
                masAzul.setVisible(false);
                masAmarillo.setVisible(false);
                masRojo.setVisible(false);
                masVerde.setVisible(false);
            } else {
                cCAzul.setVisible(false);
                cCAmarillo.setVisible(false);
                cCRojo.setVisible(false);
                cCVerde.setVisible(false);
            }
            cambiaTurno();
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setBounds(label.getX(), label.getY() - 50, 100, 150);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setBounds(label.getX(), label.getY() + 50, 100, 150);
        }
    }

    //accione que realizara el label mazo para afrefar cartas al panel del jugador
    public class mazoDeEnmedio extends MouseAdapter {
        private JLabel label;
        private ImageIcon iconG=new ImageIcon(new ImageIcon("src/Assets/UnknowCard.png").getImage().getScaledInstance(110, 165, Image.SCALE_SMOOTH));;
        private ImageIcon iconN= new ImageIcon(new ImageIcon("src/Assets/UnknowCard.png").getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        
        public mazoDeEnmedio(JLabel label){
            this.label=label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!event) { //Evita que en medio de un cambio algun hjdp quiera poner cartas 
                new Thread(() -> {
                    cardEffect2.play();
                }).start();
                //practicamente reciclada de codigo
                int x = (int) (Math.random() * 5);
                Carta nueva = new Carta();
                if (x < 4) {
                    int y = (int) (Math.random() * 9);
                    nueva = cartasN[x][y];
                } else {
                    x = (int) (Math.random() * 14);
                    nueva = cartasE[x];
                }
                //agrega la carta al jugador, borra todas del panel y las vuelve a agregar
                jugador[contador].getMaso().add(nueva);

                for (int i = 0; i < abajo.size(); i++) {
                    abajo.get(i).setVisible(false);
                }
                jugadorP();
                for (int i = 0; i < abajo.size(); i++) {
                    cont.add(abajo.get(i));
                }
                cont.repaint();
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setIcon(iconG);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setIcon(iconN);
        }
    }

    public void cambiaTurno() {
        if (!hayWinner) {
            event = true;
            for (int i = 0; i < arriba.size(); i++) {
                arriba.get(i).setVisible(false);
            }
            arriba.clear();
            for (int i = 0; i < abajo.size(); i++) {
                abajo.get(i).setVisible(false);
            }
            abajo.clear();

            confirmation.setBounds(530, 430, 90, 60);
            confirmation.setVisible(true);
            nombreJugador.setVisible(false);
        }
    }

    public class siguienteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            event = false;

            if (sumameLasCartasPalRival != 0) {
                //metodo que solo permitira al jugador en turno, tirar cartas de +2 0 el +4
                ayudameChavito();
            } else {
                //en caso contrario las cartes ya se le agregaron a su maso 
                jugadorP();
            }

            nombredelWey();
            agregarDisRivales();

            confirmation.setVisible(false);

            for (int i = 0; i < abajo.size(); i++) {
                cont.add(abajo.get(i));
            }
            for (int i = 0; i < arriba.size(); i++) {
                cont.add(arriba.get(i));
            }

            cont.repaint();
        }
    }

    public void comprobarGanador() {
        if (jugador[contador].getMaso().isEmpty()) {

            //Activa bandera y remueve lo que hay
            hayWinner = true;
            cont.removeAll();
            cont.repaint();

            //Detener la musica que suena en ese momento
            if (jugador.length == 2) {
                twoPlayerTheme.stop();
            } else if (jugador.length == 3) {
                threePlayerTheme.stop();
            } else {
                fourPlayerTheme.stop();
            }

            if ( ( (int)(Math.random()*2) ) == 0) {
                winnerTheme.changeVolume(-10f);
                winnerTheme.loop();
            } else {
                winnerTheme2.changeVolume(-10f);
                winnerTheme2.loop();
            }

            //Label que dice quien gano
            JLabel ganador = new JLabel("GANO EL JUGADOR " + (contador + 1));
            ganador.setBounds(750, -295, 500, 650);
            ganador.setFont(font);
            ganador.setForeground(Color.black);
            
            if (contador == 0) {
                
                fondo2 = new ImageIcon(new ImageIcon("src/Assets/WINJ1.jpg").getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH));

            } else if (contador == 1) {
                
                fondo2 =  new ImageIcon(new ImageIcon("src/Assets/WINJ2.jpg").getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH));

            } else if (contador == 2) {
                
                fondo2 = new ImageIcon(new ImageIcon("src/Assets/WINJ3.jpg").getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH));

            } else {
                
                fondo2 =  new ImageIcon( new ImageIcon("src/Assets/WINJ4.jpg").getImage().getScaledInstance(1200, 700, Image.SCALE_SMOOTH));;
            }

            cont.setLayout(null);

            //AÑADIR TEXTO
            cont.add(ganador);

            //mostrar stats xd
            mostrarstats();

            //LABEL DONDE SE VEN LOS STATS
            JLabel stats = new JLabel(cartastats);
            stats.setBounds(680, 5, 500, 650);
            cont.add(stats);

            //Pa la animacion del coco
            coronita.setBounds(300, -90, 100, 100);
            cont.add(coronita);
            cocodilo.setBounds(120, -100, 500, 1000);
            cont.add(cocodilo);

            animacion.start();

            //BOTON PARA VOLVER AL MENU DE INICIO
            JButton volver = new JButton("Volver");
            volver.setBounds(530, 430, 90, 60);
            volver.setVisible(true);
            volver.addActionListener(e -> {
                setVisible(false);
                winnerTheme.stop();
                winnerTheme2.stop();
                new MainTitle().setVisible(true);
            });
            cont.add(volver);
        }
    }

    private Timer animacion = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (coronita.getY() <= 200) {
                coronita.setLocation(coronita.getX(), coronita.getY() + 1);
            }
        }
    });

    //El sacrosanto desmadre de los stats
    public void mostrarstats() {
        JLabel cj = new JLabel("Cartas Jugadas"), md = new JLabel("Mas Dos Jugados"), bj = new JLabel("Bloqueos Jugados"), rj = new JLabel("Reversas Jugados");
        JLabel ncj = new JLabel(String.valueOf(jugador[contador].getCartasJugadas())), nmd = new JLabel(String.valueOf(jugador[contador].getMasDosJugados())), nbj = new JLabel(String.valueOf(jugador[contador].getBloqueosJugados())), nrj = new JLabel(String.valueOf(jugador[contador].getReversasJugados()));

        cj.setBounds(690, -210, 500, 650);
        cj.setFont(font2);
        cj.setForeground(Color.black);
        cont.add(cj);

        ncj.setBounds(690, -150, 500, 650);
        ncj.setFont(font2);
        ncj.setForeground(Color.black);
        cont.add(ncj);

        md.setBounds(690, -90, 500, 650);
        md.setFont(font2);
        md.setForeground(Color.black);
        cont.add(md);

        nmd.setBounds(690, -30, 500, 650);
        nmd.setFont(font2);
        nmd.setForeground(Color.black);
        cont.add(nmd);

        bj.setBounds(690, 30, 500, 650);
        bj.setFont(font2);
        bj.setForeground(Color.black);
        cont.add(bj);

        nbj.setBounds(690, 90, 500, 650);
        nbj.setFont(font2);
        nbj.setForeground(Color.black);
        cont.add(nbj);

        rj.setBounds(690, 160, 500, 650);
        rj.setFont(font2);
        rj.setForeground(Color.black);
        cont.add(rj);

        nrj.setBounds(690, 220, 500, 650);
        nrj.setFont(font2);
        nrj.setForeground(Color.black);
        cont.add(nrj);
    }

    //metodo para el nombre del jugador
    //modifique un poco el tamaño y mivi el label 
    public void nombredelWey() {
        nombreJugador.setVisible(true);
        nombreJugador.setBounds(720, 350, 120, 50);

        nombreJugador.setIcon( new ImageIcon(new ImageIcon("src/Assets/" + (contador + 1) + "P.jpg").getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH)));

        cont.add(nombreJugador);
    }

    //metodo para aunmentarle cartas a los rivales en caso de un mas +4 o +2
    public void seLaMetoALosRivales(int x, Carta carta) {
        int rival = contador;

        if (direc) {
            if (rival == jugador.length) {
                rival = 0;
            }
        } else {
            if (rival == -1) {
                rival = jugador.length - 1;
            }
        }

        if (jugador[rival].getMaso().contains(cartasE[0]) || jugador[rival].getMaso().contains(cartasE[1]) || jugador[rival].getMaso().contains(cartasE[2]) || jugador[rival].getMaso().contains(cartasE[3]) || jugador[rival].getMaso().contains(cartasE[4])) {

            sumameLasCartasPalRival += x;

        } else {
            sumameLasCartasPalRival += x;
            for (int i = 0; i < sumameLasCartasPalRival; i++) {
                x = ((int) (Math.random() * 5));

                if (x == 4) {

                    x = ((int) (Math.random() * 14));
                    jugador[rival].getMaso().add(cartasE[x]);

                } else {

                    int y = ((int) (Math.random() * 9));
                    jugador[rival].getMaso().add(cartasN[x][y]);

                }
            }
            sumameLasCartasPalRival = 0;
        }
    }
}//hay que hacer pruebas para verificar q asta aqui todo valla bien, con 2 y 3 jugadores
//igual hacer pruebas con la carta +4 a veces provoca un error perro la sintaxis es la misma q la de cambiaColor