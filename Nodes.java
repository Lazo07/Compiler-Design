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
public class Nodes {
    private TreeEl neko;
    private String token;
    private String type;
    public Nodes(String tok){
        token = tok;
        type="token";
    }
    public Nodes(TreeEl var){
        neko = var;
        type="variable";
    }
    
    public TreeEl getTree(){
        return neko;
    }
    
    public String getToken(){
        return token;
    }
    
    public String getType(){
        return type;
    }

}
