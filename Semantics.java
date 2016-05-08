/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproj;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
class Semantics {
    TreeEl parsetree;
    String lex;
    HashMap <String, Content> symb = new HashMap<>();
    public Semantics(TreeEl x){
        parsetree=x;
    }
    public boolean isInTable(String lex){
        return symb.containsKey(lex);
    }
    
    
    public void run(){
        System.out.println("\n\n\n\n");
        enPROGRAM(parsetree.getNode().get(2).getTree());
        //System.out.println("WEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    }

    private void enPROGRAM(TreeEl tree) {
       // if ("DECLARE".equals(tree.getNode().get(0).getToken())){
            enDECSTMT(tree.getNode().get(3).getTree());
       // }
        //if("MAIN".equals(tree.getNode().get(0).getToken())){
            enMAINPROG(tree.getNode().get(4).getTree());
            
       // }
    }

    private void enDECSTMT(TreeEl tree) {
        enDECSTMTLIST(tree.getNode().get(2).getTree());
    }

    private void enDECSTMTLIST(TreeEl tree) {
      String datatype = enDATATYPE(tree.getNode().get(0).getTree());
      String variable = enVARIABLE(tree.getNode().get(1).getTree());
      String value;
      if (tree.getNode().get(2).getTree().getVar() == "<DEC_STMT_DIFF>"){
        value = enDECSTMTDIFF1(tree.getNode().get(2).getTree());
      }
      else {
        value = null;
      }
      symb.put(variable,new Content(datatype,value));
      
      if ("<COMVAR_COM>".equals(tree.getNode().get(0).getTree().getVar())){
          variable = enVARIABLE(tree.getNode().get(1).getTree());
          value = null;
      }
      
        symb.put(variable,new Content(datatype,value));
    }

    private String enDATATYPE(TreeEl tree) {
        return tree.getNode().get(0).getToken();
    }

    private String enVARIABLE(TreeEl tree) {
        return tree.getNode().get(0).getToken();
    }

    private String enDECSTMTDIFF1(TreeEl tree) {
        String constant = enCONSTANT(tree.getNode().get(1).getTree());
        return constant;
    }

    private String enCONSTANT(TreeEl tree) {
        return tree.getNode().get(0).getToken();
    }

    private void enMAINPROG(TreeEl tree) {
        
        enSTMTLIST(tree.getNode().get(2).getTree());
    }

    private void enSTMTLIST(TreeEl tree) {
        //if ("DECLARE".equals(tree.getNode().get(0).getToken())){
       /// System.out.println(tree.getNode().size());
       // System.out.println(tree.getNode().lastIndexOf(tree.getNode().get(1).getTree()));
        enSTMT(tree.getNode().get(0).getTree());
        if (tree.getNode().size() > 1){
         enSTMTLIST(tree.getNode().get(1).getTree());
        }
        //}
    }

    private void enSTMT(TreeEl tree) {
       //System.out.println(tree.getVar()); pangalan nung tree mismo
      // System.out.println(tree.getNode().get(0).getTree().getVar()); pangalan nung subtree
     
       switch (tree.getNode().get(0).getTree().getVar()){
           
           case "<IN>":enIN(tree.getNode().get(0).getTree());break;
           case "<OUT>": enOUT(tree.getNode().get(0).getTree());break;
          // case "<AR_BLOCK>":enARBLOCK(tree.getNode().get(0).getTree());break;
          // case "<LOOP>":enLOOP(tree.getNode().get(0).getTree());break;
         //  case "<SWITCH>":enSWITCH(tree.getNode().get(0).getTree()); break;
         //  case "<IF>": enIF(tree.getNode().get(0).getTree());break;
         //  case "<WORDLENGTH>": enWORDLENGTH(tree.getNode().get(0).getTree());break;
           default: System.out.println("FINESHED EXECUTION");
           
       
       }
    }

    private void enIN(TreeEl tree) {
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine();
        String variable = enVARIABLE(tree.getNode().get(1).getTree());
        String datatype = symb.get(variable).getType();
        
  
        
        if(datatype == "INTEGER"){
            if (value.contains("0")||value.contains("1")||value.contains("2")||value.contains("3")|| value.contains("4")||value.contains("5")|| value.contains("6") || value.contains("7")||value.contains("8")||value.contains("9")){
               
                symb.replace(variable, new Content(datatype, value));
                System.out.println("Input = " + value);
                
               
            }
            else {
                System.out.println("INCOMPATIBLE DATATYPE AND INPUT VALUE.");
                System.exit(0);
            
            }
        }
        else if (datatype == "STRING"){
            if (value.contains("0")||value.contains("1")||value.contains("2")||value.contains("3")|| value.contains("4")||value.contains("5")|| value.contains("6") || value.contains("7")||value.contains("8")||value.contains("9")){
                 
                System.out.println("INCOMPATIBLE DATATYPE AND INPUT VALUE.");
                System.exit(0);
           
            }
            else {
               //  System.out.println(datatype);
                symb.replace(variable, new Content(datatype, value));
            }
            
        }
            
        else {
            System.out.println("NO SUCH THING LIKE THAT");
            System.exit(0);
        }
    }

    private void enOUT(TreeEl tree) {
        System.out.println("OUTPUT:");
        String outstring = enOUTCHOICES(tree.getNode().get(1).getTree());
        if(tree.getNode().size() > 3){
             outstring += enOUTCHOICES(tree.getNode().get(1).getTree());
        }
        System.out.println(outstring);
    }

    private void enARBLOCK(TreeEl tree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void enLOOP(TreeEl tree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void enSWITCH(TreeEl tree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void enIF(TreeEl tree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void enWORDLENGTH(TreeEl tree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String enOUTCHOICES(TreeEl tree) {
        if (tree.getNode().get(0).getTree().getVar().equals("<VARIABLE>")){
            String variable = enVARIABLE(tree.getNode().get(0).getTree());
            String value = symb.get(variable).getValue();
            return value;
        }
        else {
            return tree.getNode().get(0).getToken();
        }
    }

    private void enOUT1(TreeEl tree) {  
            enOUTCHOICES(tree.getNode().get(1).getTree());
            
   
    }
}
