
package paquete;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Tablero extends JPanel{
    //Color colorFondo = Color.ORANGE;
    int tamMax, tam, NumCasillas; // tamMaximo, tam Casillas, num Casillas
    
    // Parametros: cantidad de casillas NxN
    public Tablero(int cant){
        this.tamMax = 500; // tamanio de casillas
        this.NumCasillas = cant;
        this.tam = tamMax/cant;
    }
  
    // Genera el tablero
    @Override
    public void paint(Graphics pintor){
        super.paint(pintor);
        generarAgua(pintor);
        generarTierra(pintor);
        generPuentes(pintor);
    }
    
    public void generarTierra(Graphics pintor){
        //super.paint(pintor);
        pintor.setColor(Color.ORANGE);
        for(int i = 1; i < NumCasillas-1; i++){
            for(int j = 1; j < NumCasillas-1; j++){
                pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
            }
        }
    }
    
    public void generarAgua(Graphics pintor){
        //super.paint(pintor);
        pintor.setColor(Color.BLUE);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
            }
        }
    }
    
    public void generPuentes(Graphics pintor){
        //super.paint(pintor);
        pintor.setColor(Color.MAGENTA);
        for(int i = 0; i < NumCasillas; i++){
            for(int j = 0; j < NumCasillas; j++){
                boolean esquina1, esquina2;
                esquina1 = i == 0 && j == NumCasillas-1;
                esquina2 = i == NumCasillas-1 && j == 0;
                if(esquina1 || esquina2){
                    pintor.fillRect(i*tam, j*tam, tam-1, tam-1);
                }
            }
        }
    }
}
