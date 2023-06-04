
package paquete;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Tablero extends JPanel{
    
    Image imagenTesoro;
    Image imagenPirata;
            
    int tamMax, tamC, NumCasillas; // tamMaximo, tamC Casillas, num Casillas
    private final Tesoro tesoro;
    private final Pirata pirata;
    private Graphics pintor;
    private boolean TesoroColocado;
    private boolean PirataMovimiento;
    private boolean ganoPartida;
    private boolean perdioPartida;
    private final int maxMovimientos;
    
    public Tablero(int cant) throws IOException{
        this.tamMax = 500; // tamanio de casillas
        this.NumCasillas = cant;
        this.tamC = tamMax/cant; // tamanio de cada casilla
        
        TesoroColocado = false;
        PirataMovimiento = false;
        ganoPartida = false;
        perdioPartida = false;
        maxMovimientos = 50;
    
        tesoro = new Tesoro();
        pirata = new Pirata();
        
        imagenTesoro = ImageIO.read(new File("C:\\Users\\DELL\\Desktop\\JuegoDelPirata\\src\\Imagenes\\tesoro.png"));
        imagenPirata = ImageIO.read(new File("C:\\Users\\DELL\\Desktop\\JuegoDelPirata\\src\\Imagenes\\pirata.png"));

    }
  
    // Genera el tablero
    @Override
    public void paintComponent(Graphics pintor){
        this.pintor = pintor;
        super.paintComponent(this.pintor);
        generarTablero();
        colocarTesoro();
        colocarPirata();
    }
    private void generarTablero() {
        super.paintComponent(pintor);
        generarAgua();
        generarCasillas();
        generarPuentes();
    }

    private void generarAgua() {
        pintor.setColor(Color.BLUE);
        for (int i = 0; i < NumCasillas; i++) {
            for (int j = 0; j < NumCasillas; j++) {
                if (i == 0 || i == NumCasillas - 1 || j == 0 || j == NumCasillas - 1) {
                    pintor.fillRect(i * tamC, j * tamC, tamC - 1, tamC - 1);
                }
            }
        }
    }

    private void generarCasillas() {
        pintor.setColor(Color.ORANGE);
        for (int i = 1; i < NumCasillas - 1; i++) {
            for (int j = 1; j < NumCasillas - 1; j++) {
                pintor.fillRect(i * tamC, j * tamC, tamC - 1, tamC - 1);
            }
        }
    }

    private void generarPuentes() {
        pintor.setColor(Color.MAGENTA);
        pintor.fillRect(0, (NumCasillas - 1) * tamC, tamC - 1, tamC - 1);
        pintor.fillRect((NumCasillas - 1) * tamC, 0, tamC - 1, tamC - 1);
    }
    private void colocarTesoro() {
            pintor.setColor(Color.RED);
            if (TesoroColocado) {
                pintor.drawImage(imagenTesoro, tesoro.getX() * tamC, tesoro.getY() * tamC, tamC, tamC, this);
            } else {
                generarPosicionT(tesoro);
                pintor.drawImage(imagenTesoro, tesoro.getX() * tamC, tesoro.getY() * tamC, tamC, tamC, this);
                TesoroColocado = true;
            }
    }
    
    private void colocarPirata() {
            pintor.setColor(Color.BLACK);
            int limite = NumCasillas - 2;
            if (PirataMovimiento) {
                pintor.drawImage(imagenPirata, pirata.getX() * tamC, pirata.getY() * tamC, tamC, tamC, this);
            } else {
                do {
                    generarPosicionP(pirata);
                } while (tesoro.getX() == pirata.getX() && tesoro.getY() == pirata.getY());
                pintor.drawImage(imagenPirata, pirata.getX() * tamC, pirata.getY() * tamC, tamC, tamC, this);
            }
    }
    
    private void generarPosicionT(Tesoro tesoro) {
            int limite = NumCasillas - 2;
            int xT = (int)(Math.random() * limite) + 1;
            int yT = (int)(Math.random() * limite) + 1;
            tesoro.setX(xT);
            tesoro.setY(yT);
    }

    private void generarPosicionP(Pirata pirata) {
        int limite = NumCasillas - 2;
        int xP = (int)(Math.random() * limite) + 1;
        int yP = (int)(Math.random() * limite) + 1;
        pirata.setX(xP);
        pirata.setY(yP);
    }
    

    public void moverPirata(){
        int direccion = (int)(Math.random()*4)+1;
        int nuevoX = pirata.getX();
        int nuevoY = pirata.getY();
        //generarMovimiento();
        switch (direccion) {
            case 1: // Norte
                nuevoY--;
                break;
            case 2: // Sur
                nuevoY++;
                break;
            case 3: // Este
                nuevoX++;
                break;
            case 4: // Oeste
                nuevoX--;
                break;
            default:
                // Dirección inválida
                return;
        }
        // Verificar si el movimiento es válido
        if(pirata.getMovimientos() < maxMovimientos){
            if (esValido(nuevoX, nuevoY)) {
            pirata.setX(nuevoX);
            pirata.setY(nuevoY);
            pirata.actualizarMov();
            // Verificar si se ha encontrado el tesoro
            if (esTesoro(pirata.getX(), pirata.getY())) {
                terminarPartida("¡Encontraste el tesoro!");
            }
            }else if(esValidoTablero(nuevoX, nuevoY)){
                pirata.setX(nuevoX);
                pirata.setY(nuevoY);
                pirata.actualizarMov();
                perdioPartida = true;
                // TERMINAR PARTIDA 
                terminarPartida("Te ahogaste!");

            }
            PirataMovimiento = true;
            repaint();
        }else{
            perdioPartida = true;
            terminarPartida("No tienes mas movimientos, Pediste!, Inicia un nuevo Juego");
        }
    }
    
    private void terminarPartida(String mensaje) {
    // TERMINAR PARTIDA 
        JOptionPane.showMessageDialog(this, mensaje);
    }   
    
    public boolean esValido(int nuevoX, int nuevoY){
        boolean esValido;
        if(nuevoX > 0 && nuevoY > 0 && nuevoX < NumCasillas-1 && nuevoY < NumCasillas-1){
            esValido = true;
        }else{
            esValido = false;
        }
        return esValido;
    }
    // Valido en el tablero
    public boolean esValidoTablero(int nuevoX, int nuevoY){
        boolean esValido;
        if(nuevoX >= 0 && nuevoY >= 0 && nuevoX < NumCasillas && nuevoY < NumCasillas){
            esValido = true;
        }else{
            esValido = false;
        }
        return esValido;
    }
    
    public boolean esTesoro(int xP, int yP){
        if(xP == tesoro.getX() && yP == tesoro.getY()){
            pirata.setEncontroTesoro(true);
            ganoPartida = true;
        }else{
            pirata.setEncontroTesoro(false);
            ganoPartida = false;
        }
        return pirata.getEncontroTesoro();
    }   
    
    public boolean getGanoPartida(){
        return ganoPartida;
    }
    
    public boolean getPerdioPartida(){
        return perdioPartida;
    }
    
    public int getMovimientos(){
        return pirata.getMovimientos();
    }
}
