package uno;

public class Carta {

    private String direccion, color, tipo;
    private int valor;

    public Carta() {
    }

    public Carta(String direccion, String color, String tipo) {
        this.direccion = direccion;
        this.color = color;
        this.tipo = tipo;
    }

    public Carta(String direccion, String color, int valor) {
        this.direccion = direccion;
        this.color = color;
        this.valor = valor;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public int getValor() {
        return valor;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public void setValor(int valor){
        this.valor = valor;
    }

}
