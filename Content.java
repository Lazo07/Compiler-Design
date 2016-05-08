/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproj;

/**
 *
 * @author ACER
 */
public class Content {
    private String type;
    private String value;
    
    public Content(String x, String y){
        type=x;
        value=y;
    }
    
    public String getType(){
        return type;
    }
    public String getValue(){
        return value;
    }
}
