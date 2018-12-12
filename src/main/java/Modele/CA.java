/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Matthias
 */
public class CA {
    private String Description;
    private float Cout;
    
    public CA (String desc, float c){
        this.Description=desc;
        this.Cout=c;
    }
    
    public String getDescription(){
        return this.Description;
    }
    
    public float getCout(){
        return this.Cout;
    }
}
