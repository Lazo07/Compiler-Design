/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproj;

import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class TreeEl {
    public String variable="";
    private ArrayList<Nodes> neko=new ArrayList<>();
    private int con=0;
    
    public TreeEl(String x){
        variable=x;
    }
    
    public ArrayList<Nodes> getNode(){
        return neko;
    }
    
    public void inTree(String tok){
        neko.add(new Nodes(tok));
    }
    
    public void inTree(TreeEl n){
        neko.add(new Nodes(n));
    }
    public String getVar(){
        return variable;
    }

    void print() {
        System.out.print(variable+"{ ");
        for(int ctr=0;ctr<neko.size();ctr++){
            if(neko.get(ctr).getType().equals("variable")){ 
                neko.get(ctr).getTree().print();
            }else{
                System.out.print(neko.get(ctr).getToken()+" ");
            }
        }
        System.out.print(" }");
    }
}
