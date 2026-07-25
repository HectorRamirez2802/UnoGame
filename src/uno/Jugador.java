package uno;

import java.util.ArrayList;

public class Jugador {

    private String nombreJ;
    private ArrayList<Carta> maso = new ArrayList<>();
    private int cartasJugadas = 0, masdosjugados = 0, bloqueosjugados = 0, reversasjugados = 0;

    public Jugador(String nombreJ) {
        this.nombreJ = nombreJ;
    }

    public String getNombreJ() {
        return nombreJ;
    }

    public ArrayList<Carta> getMaso() {
        return maso;
    }

    public void setMaso(ArrayList<Carta> maso) {
        this.maso = maso;
    }

    //ESTADISTICAS 
    public void setCartasJugadas(int c) {
        this.cartasJugadas = c;
    }

    public int getCartasJugadas() {
        return cartasJugadas;
    }

    public void setMasDosJugados(int c) {
        this.masdosjugados = c;
    }

    public int getMasDosJugados() {
        return masdosjugados;
    }

    public void setBloqueosJugados(int c) {
        this.bloqueosjugados = c;
    }

    public int getBloqueosJugados() {
        return bloqueosjugados;
    }
    
    public void setReversasJugados(int c) {
        this.reversasjugados = c;
    }

    public int getReversasJugados() {
        return reversasjugados;
    }

}
