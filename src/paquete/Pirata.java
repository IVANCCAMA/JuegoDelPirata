/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete;

public class Pirata {
    String nombrePirata;
    int x,y;
    int NumMovimientos;
    boolean encontroTesoro;
    
    public Pirata(){
        this.nombrePirata = "Pata de Palo";
        this.x = 0;
        this.y = 0;
        NumMovimientos = 0;
    }
    
    
    public void busarTeroso(){
        
    }
    public void actualizarMov(){
        NumMovimientos++;
    }
    public boolean encontroTesoro(){
        return encontroTesoro;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
