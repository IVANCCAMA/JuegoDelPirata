
package paquete;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Tablero extends JPanel{
    //Color colorFondo = Color.ORANGE;
    int tamMax, tamC, NumCasillas; // tamMaximo, tamC Casillas, num Casillas
    private Tesoro tesoro;
    private Pirata pirata;
    private Graphics pintor;
    private boolean TesoroColocado;
    private boolean PirataMovimiento;
    private boolean ganoPartida;
    private boolean perdioPartida;
    private int maxMovimientos;
    
    public Tablero(int cant){
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
    private void generarTablero(){
        super.paintComponent(pintor);
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
    private void colocarTesoro(){
        pintor.setColor(Color.RED);
        if(TesoroColocado == true){
            pintor.fillRect(tesoro.getX()*tamC, tesoro.getY()*tamC, tamC-1, tamC-1);
        }else{
            int limite = NumCasillas - 2; 
            int xT, yT;
            xT = (int)(Math.random()*limite)+1;
            yT = (int)(Math.random()*limite)+1;
            tesoro.setX(xT);
            tesoro.setY(yT);
            pintor.fillRect(tesoro.getX()*tamC, tesoro.getY()*tamC, tamC-1, tamC-1); 
            
            TesoroColocado = true;
        }
    }
    
    private void colocarPirata() {
        pintor.setColor(Color.BLACK);
        int limite = NumCasillas - 2; 
        int xP, yP, xT, yT;
        if(PirataMovimiento == true){
           pintor.fillRect(pirata.getX()*tamC, pirata.getY()*tamC, tamC-1, tamC-1); 
        }else{
            do {
            xP = (int)(Math.random()*limite)+1;
            yP = (int)(Math.random()*limite)+1;
            pirata.setX(xP);
            pirata.setY(yP);
            } while (tesoro.getX() == pirata.getX() && tesoro.getY() == pirata.getY());
            // POS PIRATA
            pintor.fillRect(pirata.getX()*tamC, pirata.getY()*tamC, tamC-1, tamC-1);
        }
    }
    
    public void moverPirata(){
        int direccion = (int)(Math.random()*4)+1;
        int nuevoX = pirata.getX();
        int nuevoY = pirata.getY();
        System.out.println("Pos Actual Pirata: "+ pirata.getX() + "," + pirata.getY());
        
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
                System.out.println("-------------¡ENCONTRASTE EL TESORO!-----------");
                // TERMINAR PARTIDA XD ARREGLAR
                //JOptionPane.showMessageDialog(this, "Encontaste el tesoro");
                //nuevoJuego();
            }
            }else{
                pirata.setX(nuevoX);
                pirata.setY(nuevoY);
                pirata.actualizarMov();
                //System.out.println("TE AHOGASTEEE!!!!!!!!");
                perdioPartida = true;
                // TERMINAR PARTIDA XD
                //JOptionPane.showMessageDialog(this, "Te ahogaste!");
            }

            PirataMovimiento = true;
            repaint();
            System.out.println("Pos Despues Pirata: "+ pirata.getX() + "," + pirata.getY());
        }else{
            perdioPartida = true;
            JOptionPane.showMessageDialog(this, "No tienes mas movimientos, Pediste!, Inicia un nuevo Juego");
        }
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
