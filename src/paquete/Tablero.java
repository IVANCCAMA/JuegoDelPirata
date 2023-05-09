
package paquete;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Tablero extends JPanel{
    //Color colorFondo = Color.ORANGE;
    int tamMax, tamC, NumCasillas; // tamMaximo, tamC Casillas, num Casillas
    private Tesoro tesoro;
    private Pirata pirata;
    Graphics pintor;
    
    public Tablero(int cant){
        this.tamMax = 500; // tamanio de casillas
        this.NumCasillas = cant;
        this.tamC = tamMax/cant; // tamanio de cada casilla
        
        tesoro = new Tesoro();
        pirata = new Pirata();
    }
  
    // Genera el tablero
    @Override
    public void paint(Graphics pintor){
        this.pintor = pintor;
        super.paint(pintor);
        generarTablero();
        colocarPirataYTesoro();
    }
    private void generarTablero(){
        //super.paint(pintor);
        //pintor.setColor(Color.ORANGE);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                if (i == 0 || i == NumCasillas - 1 || j == 0 || j == NumCasillas - 1) { //AGUA
                    pintor.setColor(Color.BLUE);
                    pintor.fillRect(i*tamC, j*tamC, tamC-1, tamC-1);
                }else{ //CASILLAS
                    pintor.setColor(Color.ORANGE);
                    pintor.fillRect(i*tamC, j*tamC, tamC-1, tamC-1);
                }                
            }
        }
        // PUENTES
        pintor.setColor(Color.MAGENTA);
        pintor.fillRect(0*tamC, (NumCasillas-1)*tamC, tamC-1, tamC-1);
        pintor.fillRect((NumCasillas-1)*tamC, 0*tamC, tamC-1, tamC-1);
                
    }
    private void colocarPirataYTesoro() {
        int limite = NumCasillas - 2; 
        int xP, yP, xT, yT;
        do {
            xP = (int)(Math.random()*limite)+1;
            yP = (int)(Math.random()*limite)+1;
            pirata.setX(xP);
            pirata.setY(yP);
            xT = (int)(Math.random()*limite)+1;
            yT = (int)(Math.random()*limite)+1;
            tesoro.setX(xT);
            tesoro.setY(yT);
        } while (tesoro.getX() == pirata.getX() && tesoro.getY() == pirata.getY());
        // POS PIRATA
        pintor.setColor(Color.BLACK);
        pintor.fillRect(pirata.getX()*tamC, pirata.getY()*tamC, tamC-1, tamC-1);
        // POS TESORO
        pintor.setColor(Color.RED);
        pintor.fillRect(tesoro.getX()*tamC, tesoro.getY()*tamC, tamC-1, tamC-1); 
    }
    public void moverPirata(){
        int direccion = (int)(Math.random()*4)+1;
        int nuevoX = pirata.getX();
        int nuevoY = pirata.getY();
        int antesX = pirata.getX();
        int antesY = pirata.getY();
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
        if (esValido(nuevoX, nuevoY)) {
            // Actualizar la posición del pirata
            pirata.setX(nuevoX);
            pirata.setY(nuevoY);
            actualizarPosPirata(antesX, antesY, pirata.getX(), pirata.getY());

            // Verificar si se ha encontrado el tesoro
            if (esTesoro(pirata.getX(), pirata.getY())) {
                System.out.println("¡Encontraste el tesoro!");
            }
        } else {
            System.out.println("¡Cuidado! Te caerás al agua");
        }
        
    }
    public boolean esValido(int nuevoX, int nuevoY){
        return true;
    }
    
    public boolean esTesoro(int x, int y){
        return false;
    }
    public void actualizarPosPirata(int antesX, int antesY, int nuevoX, int nuevoY){
        pintor.setColor(Color.BLUE);
        pintor.fillRect(antesX*tamC, antesY*tamC, tamC-1, tamC-1);
        
        pintor.setColor(Color.BLACK);
        pintor.fillRect(nuevoX*tamC, nuevoY*tamC, tamC-1, tamC-1);
    }
}
