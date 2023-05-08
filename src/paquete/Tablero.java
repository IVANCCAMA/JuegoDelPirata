
package paquete;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Tablero extends JPanel{
    //Color colorFondo = Color.ORANGE;
    int tamMax, tam, NumCasillas; // tamMaximo, tam Casillas, num Casillas
    private Tesoro tesoro;
    private Pirata pirata;
    
    public Tablero(int cant){
        this.tamMax = 500; // tamanio de casillas
        this.NumCasillas = cant;
        this.tam = tamMax/cant;
        
        tesoro = new Tesoro(0,0);
        pirata = new Pirata(0,0);
    }
  
    // Genera el tablero
    @Override
    public void paint(Graphics pintor){
        super.paint(pintor);
        generarTablero(pintor);
        colocarPirataYTesoro(pintor);
    }
    private void generarTablero(Graphics pintor){
        //super.paint(pintor);
        //pintor.setColor(Color.ORANGE);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                if (i == 0 || i == NumCasillas - 1 || j == 0 || j == NumCasillas - 1) { //AGUA
                    pintor.setColor(Color.BLUE);
                    pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
                }else{ //CASILLAS
                    pintor.setColor(Color.ORANGE);
                    pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
                }                
            }
        }
        // PUENTES
        pintor.setColor(Color.MAGENTA);
        pintor.fillRect(0*tam, (NumCasillas-1)*tam, tam-1, tam-1);
        pintor.fillRect((NumCasillas-1)*tam, 0*tam, tam-1, tam-1);
                
    }
    private void colocarPirataYTesoro(Graphics pintor) {
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
        
        pintor.setColor(Color.BLACK);
        pintor.fillRect(pirata.getX()*tam, pirata.getY()*tam, tam-1, tam-1);
        pintor.setColor(Color.RED);
        pintor.fillRect(tesoro.getX()*tam, tesoro.getY()*tam, tam-1, tam-1); 
    }
        
    /*private void generarAgua(Graphics pintor){
        //super.paint(pintor);
        pintor.setColor(Color.BLUE);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
            }
        }
    }
    private void generPuentes(Graphics pintor){
        //super.paint(pintor);
        pintor.setColor(Color.MAGENTA);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                boolean esquina1, esquina2;
                esquina1 = (i == 0 && j == NumCasillas-1);
                esquina2 = (i == NumCasillas-1 && j == 0);
                if(esquina1 || esquina2){
                    pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
                }
            }
        }
    }
    private void colocarPirata(Pirata pirata, Graphics pintor){
        int limite = NumCasillas - 2;
        pintor.setColor(Color.BLACK);
        do {
            int x = (int)(Math.random()*limite)+1;
            int y = (int)(Math.random()*limite)+1;
            pirata = new Pirata(x,y);
            pintor.fillRect(x*tam, y*tam, tam-1, tam-1);
        } while (pirata.getX() == tesoro.getX() && pirata.getY() == tesoro.getY());
    }
    private void colocarTesoro(Tesoro tesoro, Graphics pintor){
        int limite = NumCasillas - 2;   
        int x = (int)(Math.random()*limite)+1;
        int y = (int)(Math.random()*limite)+1;
        tesoro.setX(x);
        tesoro.setY(x);
        
        pintor.setColor(Color.RED);
        pintor.fillRect(x*tam, y*tam, tam-1, tam-1);

    }
    */
}
