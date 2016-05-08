/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproj;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author ACER
 */
public class Grammar {
    Stack<String> tokens = new Stack<>();
    String parsetree="";
    boolean push=true;
    TreeEl supRoot = new TreeEl("<START>");
    public Grammar(Stack<String> s){
        tokens = s;
    }
    
    public TreeEl getTree(){
        return supRoot;
    }
    
    public void run(){
        
        
        start();
       
        supRoot.print();
        
    }
    
    private void tokcheck(String expected, TreeEl x){
        if(push){
            String key;
            if(tokens.peek().contains("#")){
                key = "#";
            }
                else if(tokens.peek().contains("'")){
                key = "'";
            }
                else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
                    key = tokens.peek().substring(0);
                }
                else{
                key=tokens.peek();
            }
            System.out.println("I saw a "+tokens.peek()+" at "+x.variable+" "+expected);
            
            if(expected.equals(key)){
                //CHECK TOKENS!!!! KUNG TAMA
               // System.out.println(tokens.peek()); 
                parsetree+=tokens.peek()+" ";
                System.out.println("I popped a "+tokens.peek());
                x.inTree(tokens.peek());
                tokens.pop();
            }
            else{
                System.out.println("Expecting a "+expected+". Found a "+tokens.peek());
                tokens.empty();
                push=false;
                System.exit(0);
            }
        }
    }
    
    private void start(){
   
        parsetree+="<START>{ ";
        tokcheck("START",supRoot);
        tokcheck("LINEDEL",supRoot);
        supRoot.inTree(PROGRAM(new TreeEl("<PROGRAM>")));
        tokcheck("STOP",supRoot);
        tokcheck("LINEDEL",supRoot);
        parsetree+=" } ";
        System.out.println("hello");
    }
    
    private TreeEl PROGRAM(TreeEl x){
        if(push){
        parsetree+="<PROGRAM>{ ";
        tokcheck("CODENAME",x);
        tokcheck("'",x);
        tokcheck("LINEDEL",x);
        x.inTree(DEC_STMT(new TreeEl("<DEC_STMT>")));
        x.inTree(MAIN_PROG(new TreeEl("<MAIN_PROG>")));
        parsetree+=" } ";
        }
        return x;
    }
    
    private TreeEl DEC_STMT(TreeEl x){
        if(push){
        parsetree+="<DEC_STMT>{ ";
        tokcheck("DECLARE",x);
        tokcheck("LCURL",x);
        if(tokens.peek().equals("INTEGER")||tokens.peek().equals("BOOLEAN")||tokens.peek().equals("STRING")||tokens.peek().equals("FLOAT")){
        x.inTree(DEC_STMT_LIST(new TreeEl("<DEC_STMT_LIST>")));
        }
      
        tokcheck("RCURL",x);
        parsetree+=" } ";
        }
        return x;
    }
    
    private TreeEl MAIN_PROG(TreeEl x){
        if (push){
            parsetree+="<MAIN_PROG>{ ";
            tokcheck("MAIN",x);
            tokcheck("LCURL",x);
            if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
            tokcheck("RCURL",x);
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl STMT_LIST(TreeEl x){
        if(push){
            parsetree+="<STMT_LIST>{ ";
            if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT(new TreeEl("<STMT>")));
            }
            if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl STMT(TreeEl x){
        if(push){
            parsetree+="<STMT>{ ";
            String key;
            if(tokens.peek().contains("#")){
                key = "#";
            }
                else if(tokens.peek().contains("'")){
                key = "'";
            }
                else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9") ){
                    key = tokens.peek().substring(0);
                }
                else{
                key=tokens.peek();
            }
            
            
            
            switch(key){
                case "INSTMT":
                    x.inTree(IN(new TreeEl("<IN>")));
       
                    break;
                case "OUTSTMT":
                    x.inTree(OUT(new TreeEl("<OUT>")));
                    break;       
                    
                case "ASSIGNOP":
                    x.inTree(AR_BLOCK(new TreeEl("<AR_BLOCK>")));
                    break;
                case "AROP3": 
                    x.inTree(AR_BLOCK(new TreeEl("<AR_BLOCK>")));
                    break;
                case "#":
                    x.inTree(AR_BLOCK(new TreeEl("<AR_BLOCK>")));
                    break;
                            
                case "IFSTMT":
                    x.inTree(IF(new TreeEl("<IF>")));
                    break;
                    
                case "DO":
                    x.inTree(LOOP(new TreeEl("<LOOP>")));
                
                    break;
                case "WHILE":
                    x.inTree(LOOP(new TreeEl("<LOOP>")));

                    break;
                case "FOR":
                    x.inTree(LOOP(new TreeEl("<LOOP>")));
                    break;
                case "SWITCH":
                    x.inTree(SWITCH(new TreeEl("<SWITCH>")));
                    break;
                case "WORDLEN":
                    x.inTree(WORDLENGTH(new TreeEl("<WORDLENGTH>")));
                    break;
            }
        }   parsetree+=" } ";
        return x;
    }
    
    private TreeEl IN(TreeEl x){
        if(push){
             parsetree+="<IN>{ ";
             tokcheck("INSTMT",x);
             x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
             tokcheck("LINEDEL",x);
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl OUT(TreeEl x){
        if(push){
            parsetree+="<OUT>{ ";
            tokcheck("OUTSTMT",x); 
            
            x.inTree(OUT_CHOICES(new TreeEl("<OUT_CHOICES>")));
            if (tokens.peek().equals("CONCAT")){
                x.inTree(OUT1(new TreeEl("<OUT1>")));
            }
            tokcheck("LINEDEL",x); 
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl OUT_CHOICES(TreeEl x){
        if(push){
          
            parsetree+="<OUT_CHOICES>{ ";
            
            if (tokens.peek().substring(0).contains("#")){
                x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
                if (tokens.peek() == "ASSIGNOP")
                x.inTree(OUT_CHOICES2(new TreeEl("<OUT_CHOICES2>")));
           }
            else if(tokens.peek().substring(0).contains("'")){
                tokcheck ("'",x);
            }
        
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl OUT_CHOICES2(TreeEl x){
        if(push){
          
            parsetree+="<OUT_CHOICES2>{ ";
            
            if (tokens.peek() == "ASSIGNOP"){
                tokcheck ("ASSIGNOP",x);
                x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));
           }
           
            parsetree+=" } ";
        }
        return x;
    }
    
    private TreeEl OUT1(TreeEl x){
        if(push){

            parsetree+="<OUT1>{ ";
            tokcheck("CONCAT",x); 
            x.inTree(OUT_CHOICES(new TreeEl("<OUT_CHOICE>")));
            parsetree+=" } ";
        }
        return x;
               
    }
    private TreeEl AR_EXPR(TreeEl x){
        if(push){
            parsetree+="<AR_EXPR>{ ";
            x.inTree(NUMBERS(new TreeEl("<NUMBERS>")));
        
           if (tokens.peek() == "AROP1" || tokens.peek() == "AROP2"){
               x.inTree(AR_EXPR2(new TreeEl("<AR_EXPR2>")));
           }
            parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl AR_EXPR2(TreeEl x){
        if(push){
            parsetree+="<AR_EXPR2>{ ";
           x.inTree(BIN_AROP(new TreeEl("<BIN_AROP>")));
           String key = "";
          //if (tokens.peek() == "LPAREN" || tokens.peek() == "NEG" || tokens.peek().substring(0).contains("#") || tokens.peek().substring(0).contains("0")|| tokens.peek().substring(0).contains("1") || tokens.peek().substring(0).contains("2") || tokens.peek().substring(0).contains("3") || tokens.peek().substring(0).contains("4") || tokens.peek().substring(0).contains("5") ||tokens.peek().substring(0).contains("6")|| tokens.peek().substring(0).contains("7") ||tokens.peek().substring(0).contains("8") || tokens.peek().substring(0).contains("9")){
           //   x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>")));
             
           //}
           if (tokens.peek().contains("#")){
               key = "#";
           }
           else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
               key = tokens.peek().substring(0);
           }
           else{
               key = tokens.peek();
           }
           
           switch(key){
               case "0": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "1": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "2": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "3": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "4": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "5": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "6": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "7": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "8": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "9": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "#": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>"))); break;
               case "LPAREN": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>")));break;
               case "NEG": x.inTree(AR_EXPR_PAR2(new TreeEl("<AR_EXPR_PAR2>")));break;
           }
               
            parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl NUMBERS(TreeEl x){
        if(push){
           parsetree+="<NUMBERS>{ ";
           
           String key = "";
           if (tokens.peek()=="NEG"){
               tokcheck("NEG",x);
               x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));
            
               if (tokens.peek()== "POW"){
                x.inTree(POWER(new TreeEl("<POWER>")));
               }
           }
           else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
                //x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));
              // if (tokens.peek() == "POW"){
             //   x.inTree(POWER(new TreeEl("<POWER>")));
                //}
                key = tokens.peek().substring(0);
           }
           else if (tokens.peek().substring(0).contains("#") ){
               key = "#";
           }
           
           switch(key){
               case "0": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "1": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "2": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "3": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "4": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "5": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "6": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "7": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "8": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "9": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
               case "#": x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>")));if (tokens.peek() == "POW"){x.inTree(POWER(new TreeEl("<POWER>")));}; break;
           }
            parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl NUMBERS2(TreeEl x){
        if(push){
           parsetree+="<NUMBERS2>{ ";   
           String key = "";
           if (tokens.peek().contains("#")){
               key = "#";
               //x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
           }
           else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
               System.out.println("NUMBERSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS2");
                key = tokens.peek().substring(0);
               //tokcheck(tokens.peek().substring(0),x);
                
           }
           else {
               key = tokens.peek();
           }
           
           switch(key){
               case "0": tokcheck(tokens.peek().substring(0),x); break;
               case "1": tokcheck(tokens.peek().substring(0),x); break;
               case "2": tokcheck(tokens.peek().substring(0),x); break;
               case "3": tokcheck(tokens.peek().substring(0),x); break;
               case "4": tokcheck(tokens.peek().substring(0),x); break;
               case "5": tokcheck(tokens.peek().substring(0),x); break;
               case "6": tokcheck(tokens.peek().substring(0),x); break;
               case "7": tokcheck(tokens.peek().substring(0),x); break;
               case "8": tokcheck(tokens.peek().substring(0),x); break;
               case "9": tokcheck(tokens.peek().substring(0),x); break;
               case "#": x.inTree(VARIABLE(new TreeEl("<VARIABLE>"))); break;
           }
            parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl POWER(TreeEl x){
        if(push){
           parsetree+="<POWER>{ ";
           tokcheck("POW",x);
           x.inTree(NUMBERS(new TreeEl("<NUMBERS>")));
           parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl AR_BLOCK(TreeEl x){
        if(push){
             parsetree+="<AR_BLOCK>{ ";
             x.inTree(ARSTMT(new TreeEl("<ARSTMT>")));
             tokcheck("LINEDEL",x);
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl IF(TreeEl x){
        if(push){
             parsetree+="<IF>{ ";
             tokcheck("IFSTMT",x);
             tokcheck("LPAREN",x);
             x.inTree(CONDITION(new TreeEl("<CONDITION>")));
             tokcheck("RPAREN",x);
             tokcheck("LCURL",x);
             if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
             tokcheck("RCURL",x);
             if (tokens.peek().equals("ELSEIFSTMT")){
                 x.inTree(ELSEIF(new TreeEl("<ELSEIF>")));
             }
             if (tokens.peek().equals("ELSESTMT")){
                 x.inTree(ELSE(new TreeEl("<ELSE>")));
             }
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl ELSEIF(TreeEl x){
        if(push){
            parsetree+="<ELSEIF>{ ";
            tokcheck("ELSEIFSTMT",x); 
            tokcheck("LPAREN",x); 
            x.inTree(CONDITION(new TreeEl("<CONDITION>")));
 
            tokcheck("RPAREN",x); 
            tokcheck("LCURL",x);
            if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
        
            }
            tokcheck("RCURL",x);
            if (tokens.peek().equals("ELSEIFSTMT")){
                 x.inTree(ELSEIF(new TreeEl("<ELSEIF>")));
            }
        }   parsetree+=" } ";
        
        return x;
    }
    private TreeEl ELSE(TreeEl x){
        
        if(push){
            parsetree+="<ELSE>{ ";
            tokcheck("ELSESTMT",x);
            tokcheck("LCURL",x);
            if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
            tokcheck("RCURL",x);
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl CONDITION(TreeEl x){
        if(push){
            parsetree+="<CONDITION>{ ";
             if (push){
                 if(tokens.peek() == "NOTOP"){
                    tokcheck("NOTOP",x); 
                   x.inTree(REL_STMT(new TreeEl("<REL_STMT>")));
                 }
                 
                    else if((tokens.peek() == "BOOLCONST")||(tokens.peek().substring(0).contains("#"))||(tokens.peek().substring(0).contains("0"))||(tokens.peek().substring(0).contains("1")) ||(tokens.peek().substring(0).contains("2")) ||(tokens.peek().substring(0).contains("3"))|| (tokens.peek().substring(0).contains("4"))||(tokens.peek().substring(0).contains("5")) || (tokens.peek().substring(0).contains("6"))||(tokens.peek().substring(0).contains("7")) || (tokens.peek().substring(0).contains("8")) ||(tokens.peek().substring(0).contains("9")) ||tokens.peek().substring(0).contains("'") ){
                    x.inTree(REL_STMT(new TreeEl("<REL_STMT>")));
     
                 }
                 parsetree+=" } ";  
        
            }

        }
        return x;
    }
    private TreeEl REL_STMT(TreeEl x){
        if(push){
            parsetree+="<REL_STMT>{ ";
            if(tokens.peek().substring(0).contains("#")||tokens.peek().substring(0).contains("0")||tokens.peek().substring(0).contains("1")|| tokens.peek().substring(0).contains("2")||tokens.peek().substring(0).contains("3")|| tokens.peek().substring(0).contains("4")||tokens.peek().substring(0).contains("5")|| tokens.peek().substring(0).contains("6")||tokens.peek().substring(0).contains("7")|| tokens.peek().substring(0).contains("8")||tokens.peek().substring(0).contains("9")||tokens.peek() == "'"){
                x.inTree(COV_NOBOOL(new TreeEl("<COV_NOBOOL>")));
                tokcheck("RELOP",x);
                x.inTree(COV_NOBOOL(new TreeEl("<COV_NOBOOL>")));
                if (tokens.peek() == "LOGOP"){
                    x.inTree(LOG_STMT(new TreeEl("<LOG_STMT>")));
                }
            }
            else if (tokens.peek() == "BOOLCONST"){
                tokcheck("BOOLCONST",x);
            }
            parsetree+=" } ";
             
        }
        return x;
    }
    private TreeEl COV_NOBOOL(TreeEl x){
        if(push){
            parsetree+="<COV_NOBOOL>{ ";
            String key;
            if(tokens.peek().contains("#")){
                key="#";
            }
            else if (tokens.peek().contains("'")){
                key ="'";
            }
            else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
                key = tokens.peek().substring(0);
            }
            
            else{
                key=tokens.peek();
            }
            //e2 single digit lang yung nagagawa niya 
             switch(key){
                 case "0":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 //case "DECCONST":
                  //   tokcheck("DECCONST",x);
                  //   break;
                 case "1":
                     tokcheck(tokens.peek().substring(0),x);
                     break;   
                 case "2":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 case "3":
                     tokcheck(tokens.peek().substring(0),x);
                     break;   
                 case "4":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 case "5":
                     tokcheck(tokens.peek().substring(0),x);
                     break;   
                 case "6":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 case "7":
                     tokcheck(tokens.peek().substring(0),x);
                     break;   
                 case "8":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 case "9":
                     tokcheck(tokens.peek().substring(0),x);
                     break;
                 case "'":
                     tokcheck("'",x);
                     break;
                 case "#":
                     x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
             }
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl LOG_STMT(TreeEl x){
        if(push){
            parsetree+="<LOG_STMT>{ ";
            tokcheck("LOGOP",x);
            x.inTree(REL_STMT(new TreeEl("<REL_STMT>")));
            if (tokens.peek() == "LOGOP"){
                x.inTree(LOG_STMT(new TreeEl("<LOG_STMT>")));
            }   
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl ARSTMT(TreeEl x){
        if(push){
            String key = "";
            parsetree+="<ARSTMT>{ ";
            if(tokens.peek().contains("#")){
                key = "#";
                //x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
                //x.inTree(VAR_START(new TreeEl("<VAR_START>")));
            }
            else if (tokens.peek() == "AROP3"){
                key = tokens.peek();
                //tokcheck("AROP3",x);
                //x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
            }
            switch(key){
                case "#": x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));x.inTree(VAR_START(new TreeEl("<VAR_START>")));break;
                case "AROP3": tokcheck("AROP3",x); x.inTree(VARIABLE(new TreeEl("<VARIABLE>"))); break;
            }
            
            parsetree+=" } ";
        }
        return x;
    }
       
    
    private TreeEl VAR_START(TreeEl x){
        if(push){
            parsetree+="<VAR_START>{ ";
            if(tokens.peek() == "ASSIGNOP"){
               // x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));
                tokcheck("ASSIGNOP",x);
                //tokcheck(tokens.peek().substring(0), x);
                x.inTree(AR_EXPR_PAR(new TreeEl("<AR_EXPR_PAR>")));
                System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEY");
            }else if((tokens.peek() == "AROP3")||tokens.peek() == "DECCONST"|| tokens.peek() == "'"|| tokens.peek() == "BOOLCONST"){
                tokcheck("AROP3",x);
            }
            parsetree+=" } ";
            
        }
        return x;
    }
    private TreeEl AR_EXPR_PAR(TreeEl x){
        if(push){
            System.out.println(tokens.peek());
            System.out.println("LOLOLOLO");
            String key ="";
            parsetree+="<AR_EXPR_PAR>{ ";
           //if (tokens.peek() == "NEG" || tokens.peek().contains("#") || tokens.peek().contains("0123456789")||tokens.peek() == "DECCONST"){
               
               //x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));
           //}
           
           if (tokens.peek().contains("#")){
               key ="#";
               
           }
           else if (tokens.peek().contains("'")){
               key = "'";
           }
           else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
               key = tokens.peek().substring(0);
           }
           else if (tokens.peek()== "NEG"){
               key=tokens.peek();
           }
           else if (tokens.peek()== "LPAREN"){
               key=tokens.peek();
           }
           else{
               key=tokens.peek();
           }
           
           switch("1"){
               case "0": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "1": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "2": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "3": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "4": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "5": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "6": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "7": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "8": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "9": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "#": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "NEG": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>"))); break;
               case "LPAREN": 
                   tokcheck ("LPAREN",x);
                   x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));
                   tokcheck ("RPAREN",x); 
                   break;
           }
          // if (tokens.peek() == "LPAREN"){
          //     tokcheck ("LPAREN",x);
          //     x.inTree(NUMBERS2(new TreeEl("<NUMBERS2>"))); //HOOY
          //     tokcheck ("RPAREN",x);
          // }
           parsetree+=" } ";
        }
        return x;
    }
    private TreeEl AR_EXPR_PAR2(TreeEl x){
        if(push){
            parsetree+="<AR_EXPR_PAR>{ ";
            String key ="";
           if (tokens.peek().contains("#")){
               key ="#";
           }
           else if (tokens.peek().contains("'")){
               key = "'";
           }
           else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
               key = tokens.peek().substring(0);
           }
           else if (tokens.peek()== "NEG"){
               key=tokens.peek();
           }
           else if (tokens.peek()== "LPAREN"){
               key=tokens.peek();
           }
           
           switch(key){
               case "0": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "1": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "2": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "3": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "4": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "5": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "6": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "7": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "8": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "9": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "#": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "NEG": x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));; break;
               case "LPAREN": 
                   tokcheck ("LPAREN",x);
                   x.inTree(AR_EXPR(new TreeEl("<AR_EXPR>")));
                   tokcheck ("RPAREN",x); 
                   break;
                   
           }
           parsetree+=" } ";
        }
        return x;
    }
    private TreeEl LOOP(TreeEl x){
        if(push){
             parsetree+="<LOOP>{ ";
             if (tokens.peek() == "DO"){
                 x.inTree(DOLOOP(new TreeEl("<DOLOOP>")));
             }
             else if (tokens.peek() == "WHILE"){
                 x.inTree(WHILELOOP(new TreeEl("<WHILELOOP>")));
             }
             else if (tokens.peek() == "FOR"){
                 x.inTree(FORLOOP(new TreeEl("<FORLOOP>")));
             }
                 
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl DOLOOP(TreeEl x){
        if(push){
             parsetree+="<DOLOOP>{ ";
             tokcheck ("DO",x);
             tokcheck ("LCURL",x);
             if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
             if(tokens.peek() == "BREAK" || tokens.peek() == "CONTINUE"){
                x.inTree(CTRL(new TreeEl("<CTRL>")));
            }
             tokcheck ("RCURL",x);
             tokcheck ("WHILE",x);
             tokcheck ("LPAREN",x);
             x.inTree(CONDITION(new TreeEl("<CONDITION>")));
             tokcheck ("RPAREN",x);
             parsetree+=" } ";
        }
        return x;
    }
    
    private TreeEl WHILELOOP(TreeEl x){
        if(push){
             parsetree+="<WHILELOOP>{ ";
             tokcheck("WHILE",x);
             tokcheck ("LPAREN",x);
             x.inTree(CONDITION(new TreeEl("<CONDITION>")));
             tokcheck ("RPAREN",x);
             tokcheck ("LCURL",x);
             if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));     
            }
             if(tokens.peek() == "BREAK" || tokens.peek() == "CONTINUE"){
                x.inTree(CTRL(new TreeEl("<CTRL>")));
            }
             tokcheck ("RCURL",x);
             parsetree+=" } "; 
        }
        return x;
    }
    private TreeEl FORLOOP(TreeEl x){
        if(push){
           parsetree+="<FORLOOP>{ ";
             tokcheck("FOR",x);
             tokcheck("LPAREN",x);
             x.inTree(DATATYPE(new TreeEl("<DATATYPE>")));
             x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
             tokcheck("ASSIGNOP",x);
             tokcheck(tokens.peek().substring(0),x); //NUMCONST
             tokcheck("LINEDEL",x);
             x.inTree(CONDITION(new TreeEl("<CONDITION>")));
             tokcheck("LINEDEL",x);
             x.inTree(FOR_AROP3(new TreeEl("<FOR_AROP3>")));
             tokcheck("RPAREN",x);
             tokcheck("LCURL",x);
             if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
            }
            if(tokens.peek() == "BREAK" || tokens.peek() == "CONTINUE"){
                x.inTree(CTRL(new TreeEl("<CTRL>")));
            }
            tokcheck("RCURL",x);
             
             parsetree+=" } ";   
        }
        return x;
    }
    
    private TreeEl FOR_AROP3(TreeEl x){
        if(push){
            parsetree+="<FOR_AROP3>{ ";
             if (tokens.peek() == "AROP3"){
                 tokcheck("AROP3",x);
                 x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
             }
             else if (tokens.peek().substring(0).contains("#")){
                  x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
                 tokcheck("AROP3",x);
             }
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl CTRL(TreeEl x){
        if(push){
              parsetree+="<CTRL>{ ";
             if (tokens.peek() == "BREAK"){
                 tokcheck("BREAK",x);
                 tokcheck("LINEDEL",x);
             }
             else if (tokens.peek() == "CONTINUE"){
                  x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
                 tokcheck("CONTINUE",x);
                 tokcheck("LINEDEL",x);
             }
             parsetree+=" } ";
        
        }
        return x;
    }
    private TreeEl SWITCH(TreeEl x){
        if(push){
             parsetree+="<SWITCH>{ ";
             tokcheck("SWITCH",x);
              tokcheck("LPAREN",x);
               x.inTree(CONS_OR_VAR(new TreeEl("<CONS_OR_VAR>")));
              tokcheck("RPAREN",x);
              tokcheck("LCURL",x);
              if (tokens.peek() == "CASE"){
                 x.inTree(CASE(new TreeEl("<CASE>")));
              }
              tokcheck("RCURL",x);
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl CONS_OR_VAR(TreeEl x){
        if(push){
            String key = "";
             parsetree+="<CONS_OR_VAR>{ ";
                if (tokens.peek().contains("#")){
                    
                    key ="#";
                     //x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
                }
                else if (tokens.peek().contains("'")){
                    key ="'";
                }
                
                else if (tokens.peek().contains("0")||tokens.peek().contains("1")||tokens.peek().contains("2")||tokens.peek().contains("3")||tokens.peek().contains("4")||tokens.peek().contains("5")||tokens.peek().contains("6")||tokens.peek().contains("7")||tokens.peek().contains("8")||tokens.peek().contains("9")){
                   
                    key = tokens.peek().substring(0);
                    //x.inTree(CONSTANT(new TreeEl("<CONSTANT>")));
                }
                
                switch(key){
               case "0": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "1": x.inTree(CONSTANT(new TreeEl("<CONSTANT>")));break;
               case "2": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "3": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "4": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "5": x.inTree(CONSTANT(new TreeEl("<CONSTANT>")));break;
               case "6": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "7": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "8": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "9": x.inTree(CONSTANT(new TreeEl("<CONSTANT>"))); break;
               case "#": x.inTree(VARIABLE(new TreeEl("<VARIABLE>"))); break;
               case "'": x.inTree(VARIABLE(new TreeEl("<VARIABLE>"))); break;
           }
                
                
                
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl CASE(TreeEl x){
        if(push){
             parsetree+="<CASE>{ ";
                tokcheck("CASE",x);
                 x.inTree(CONSTANT(new TreeEl("<CONSTANT>")));
                tokcheck("LCURL",x);
                if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                     x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
                }
                 tokcheck("BREAK",x);
                 tokcheck("LINEDEL",x);
                 tokcheck("RCURL",x);
                  x.inTree(CASE_OR_DEF(new TreeEl("<CASE_OR_DEF>")));
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl CASE_OR_DEF(TreeEl x){
        if(push){
             parsetree+="<CASE_OR_DEF>{ ";
             if (tokens.peek() == "CASE"){
                  x.inTree(CASE(new TreeEl("<CASE>")));
             }
             else if (tokens.peek() == "DEFAULT"){
                  x.inTree(DEFAULT(new TreeEl("<DEFAULT>")));
             }
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl DEFAULT(TreeEl x){
        if(push){
             parsetree+="<DEFAULT>{ ";
                tokcheck("DEFAULT",x);
                tokcheck("LCURL",x);
                if (tokens.peek().equals("INSTMT")||tokens.peek().equals("OUTSTMT")||tokens.peek().equals("ASSIGNOP")||tokens.peek().equals("AROP3")||tokens.peek().equals("IFSTMT")||tokens.peek().substring(0).contains("#")||tokens.peek().equals("DO")||tokens.peek().equals("WHILE")||tokens.peek().equals("FOR")||tokens.peek().equals("SWITCH")||tokens.peek().equals("WORDLEN")){
                     x.inTree(STMT_LIST(new TreeEl("<STMT_LIST>")));
                }
                tokcheck("RCURL",x);
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl WORDLENGTH(TreeEl x){
        if(push){
             parsetree+="<WORDLENGTH>{ ";
             tokcheck("WORDLEN",x);
              x.inTree(STRING_OR_VAR(new TreeEl("<STRING_OR_VAR>")));
             tokcheck("LINEDEL",x);
             parsetree+=" } ";
        }
        return x;
    }
    private TreeEl STRING_OR_VAR(TreeEl x){
        if(push){
             parsetree+="<STRING_OR_VAR>{ ";
             
             String key ="";
             if (tokens.peek().contains("'")){
                 key = "'";
                 //tokcheck ("'",x);
             }
             else if (tokens.peek().contains("#")){
                 key = "#";
                  //x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));  
             }
             else {
                 key = tokens.peek();
             }
             switch(key){
                 case "'": tokcheck("'", x); break;
                 case "#": x.inTree(VARIABLE(new TreeEl("<VARIABLE>"))); break;
             
             }
             
             parsetree+=" } ";
        }
        return x;
    }
    
    
    
    private TreeEl DEC_STMT_LIST(TreeEl x){
        if(push){
            parsetree+="<DEC_STMT_LIST>{ ";
             x.inTree(DATATYPE(new TreeEl("<DATATYPE>")));
             x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
              x.inTree(DEC_STMT_DIFF(new TreeEl("<DEC_STMT_DIFF>")));
           
            if(tokens.peek().equals("INTEGER")||tokens.peek().equals("BOOLEAN")||tokens.peek().equals("STRING")||tokens.peek().equals("FLOAT")){
                 x.inTree(DEC_STMT_LIST(new TreeEl("<DEC_STMT_LIST>")));
            }
            parsetree+=" } ";
                
        }
        return x;
    }
    private TreeEl DATATYPE(TreeEl x){
       if (push){
           
           
           
           parsetree+="<DATATYPE>{ ";
            switch(tokens.peek()){
                case "INTEGER":
                    tokcheck("INTEGER",x);
                    break;
                case "BOOLEAN":
                    tokcheck("BOOLEAN",x);
                    break;
                case "STRING":
                    tokcheck("STRING",x);
                    break;
                case "FLOAT":
                    tokcheck("FLOAT",x);
                    break;
                            
                    
            }
            parsetree+=" } ";
        
        } 
       return x;
                
    }
    private TreeEl VARIABLE(TreeEl x){
           parsetree+="<VARIABLE>{ ";
           tokcheck("#",x);
           if(tokens.peek().equals("LBRACE")){
                        x.inTree(VAR_ARR(new TreeEl("<VAR_ARR>")));
                    }
           parsetree+=" } ";
        return x;        
    }
    private TreeEl VAR_ARR(TreeEl x){
        if(push){
            parsetree+="<VAR_ARR>{ ";
            tokcheck("LBRACE",x);
            x.inTree(ARR_SIZE(new TreeEl("<AR_SIZE>")));
            tokcheck("RBRACE",x);
            parsetree+=" } ";
        }
        return x;
    }
    
    private TreeEl ARR_SIZE(TreeEl x){
        if (push){
            parsetree+="<ARR_SIZE>{ ";
            String key;
            if(tokens.peek().contains("#")){
                key="#";
            }
            else if (tokens.peek().contains("'")){
                key ="'";
            }
            else if (tokens.peek().contains("0")|| tokens.peek().contains("1")||tokens.peek().contains("2")|| tokens.peek().contains("3") || tokens.peek().contains("4") || tokens.peek().contains("5") || tokens.peek().contains("6")|| tokens.peek().contains("7")|| tokens.peek().contains("8")|| tokens.peek().contains("9")){
                key = tokens.peek().substring(0);
            }
            
            else{
                key=tokens.peek();
            }
            
            
            switch(key){
                case "0":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                case "1":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                case "2":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                case "3":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                case "4":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                case "5":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                    
                case "6":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                    
                case "7":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                    
                case "8":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }
                    
                 case "9":
                    tokcheck(tokens.peek().substring (0),x);
                    if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                        x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
                  
                    break;
                }

                case "#":
                    tokcheck("#",x);    
                    break;
            }
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl AR_EXPR_ARR(TreeEl x){
        if(push){
            parsetree+="<AR_EXPR_ARR>{ ";
            x.inTree(BIN_AROP(new TreeEl("<BIN_AROP>")));
            x.inTree(ID_OR_NUM(new TreeEl("<ID_OR_NUM>")));
            if(tokens.peek().equals("AROP1")||tokens.peek().equals("AROP2")){
                x.inTree(AR_EXPR_ARR(new TreeEl("<AR_EXPR_ARR>")));
            
            }
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl BIN_AROP(TreeEl x){
        if(push){
            parsetree+="<BIN_AROP>{ ";
            if (tokens.peek() == "AROP1"){
                tokcheck("AROP1",x);
            }
            else if (tokens.peek() == "AROP2"){
                tokcheck("AROP2",x);
            }
            parsetree+=" } ";
        }
        return x;
    }
    private TreeEl ID_OR_NUM(TreeEl x){
        if(push){
            parsetree+="<ID_OR_NUM>{ ";
            String key;
            if(tokens.peek().contains("#")){
                key="#";
            }
            else if (tokens.peek().contains("'")){
                key ="'";
            }
            else if (tokens.peek().contains("0")|| tokens.peek().contains("1")||tokens.peek().contains("2")|| tokens.peek().contains("3") || tokens.peek().contains("4") || tokens.peek().contains("5") || tokens.peek().contains("6")|| tokens.peek().contains("7")|| tokens.peek().contains("8")|| tokens.peek().contains("9")){
                key = tokens.peek().substring(0);
            }
            
            else{
                key=tokens.peek();
            }
            
            switch(key){
                    

                case "#":
                    tokcheck("#",x);    
                    break;
                case "0":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "1":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "2":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "3":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "4":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "5":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                
                case "6":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
               case "7":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "8":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "9":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                
                 
                
            }
            parsetree+=" } ";
        }
        return x;
    }
    
    
    private TreeEl DEC_STMT_DIFF(TreeEl x){
        if(push){
            parsetree+="<DEC_STMT_DIFF>{ ";
            switch(tokens.peek()){
                case "ASSIGNOP":
                    tokcheck("ASSIGNOP",x);
                    x.inTree(CONSTANT(new TreeEl("<CONSTANT>")));
                    System.out.println("HELLOASSIGNOP!!!!!!!!!!!!!!!!!!!!!!!!!");
                    tokcheck("LINEDEL",x);
                    if(tokens.peek().equals("INTEGER")||tokens.peek().equals("BOOLEAN")||tokens.peek().equals("STRING")||tokens.peek().equals("FLOAT")){
                        x.inTree(DEC_STMT_LIST(new TreeEl("<DEC_STMT_LIST>")));
                    }
                    
                    break;
                
                case "COMMA":
                    if(tokens.peek().equals("COMMA")){
                        x.inTree(COMVAR_COM(new TreeEl("<COMVAR_COM>")));
                    }
                    tokcheck("LINEDEL",x);
                    if(tokens.peek().equals("INTEGER")||tokens.peek().equals("BOOLEAN")||tokens.peek().equals("STRING")||tokens.peek().equals("FLOAT")){
                        x.inTree(DEC_STMT_LIST(new TreeEl("<DEC_STMT_LIST>")));
                    }
                    break;
                case "LINEDEL":
                    tokcheck("LINEDEL", x);

            }
            parsetree+=" } ";
            
        }
        return x;
                
    }
    private TreeEl COMVAR_COM(TreeEl x){
        if (push){
            parsetree+="<COMVAR_COM>{ ";
            tokcheck("COMMA",x);
            x.inTree(VARIABLE(new TreeEl("<VARIABLE>")));
            if(tokens.peek().equals("COMMA")){
                x.inTree(COMVAR_COM(new TreeEl("<COMVAR_COM>")));
            }parsetree+=" } ";
        }
        
         return x;       
    }
    private TreeEl CONSTANT(TreeEl x){
        if (push){
            parsetree+="<CONSTANT>{ ";
            String key;
            if(tokens.peek().contains("#")){
                key="#";
            }
            else if (tokens.peek().contains("'")){
                key ="'";
            }
            else if (tokens.peek().contains("0")|| tokens.peek().contains("1")||tokens.peek().contains("2")|| tokens.peek().contains("3") || tokens.peek().contains("4") || tokens.peek().contains("5") || tokens.peek().contains("6")|| tokens.peek().contains("7")|| tokens.peek().contains("8")|| tokens.peek().contains("9")){
                key = tokens.peek().substring(0);
            }
            
            else{
                key=tokens.peek();
            }
            
            
            
            switch(key){
                 case "0":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "1":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "2":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "3":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "4":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "5":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                
                case "6":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
               case "7":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "8":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "9":
                    tokcheck(tokens.peek().substring(0),x);   
                    break;
                case "DECCONST":
                    tokcheck("DECCONST",x);
                    break;
                case "'":
                    tokcheck("'",x);
                    break;
                case "BOOLCONST":
                    tokcheck("BOOLCONST",x);
                    break;
            }
            parsetree+=" } ";
        }
      return x;          
    }
}
