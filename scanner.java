package compilerproj;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class scanner {
    public static int i = 0, line = 0;
    public static char[] c; 
    private static String b, tokenName = "", printID="", printSTRING="", printNUMBER="", printBOOL="";
    public static String printERR="";
    private static FileReader scfile;
    private static BufferedReader read;
    private static tokentable table = new tokentable();
    private static parser tester = new parser();

    public static boolean isRWords() {
        String word = "";
        while(i<c.length-1 && isLetter(c[i])){
            word = word +c[i];
            i++;
        }
        if (table.isInTable(word)){
            tokenName=table.getTokenName(word);
                        i--;
                        return true;
        }
        else {
            if(word!= ""){
            System.out.print("??"+word+"??");
        
            }
            
            return false;
        }
        
       
    }

    public static boolean isIdentifier() {
        String id = "";
        if (c[i] == '#') {
            i++;
            while (isLetter(c[i]) || isNumber(c[i])) {
                id += "" + c[i];
                i++;
                if(i>=c.length) break;
            }
            i--;
            printID="#" + id;
            //check if id is reserved word
            tokenName = printID;
                table.addToTable(id, tokenName);
                //tempID+=id + " ";
            return true;
        } else {
            
            return false;
        }
    }

    public static boolean isComment() {
        if (c[i] == '/' && c[i+1] =='/') {
            try {
                b = read.readLine();
                c = b.toCharArray();
                i = 0;
                tokenName = "COMMENT1";
                return true;
            } catch (IOException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isComments() {
        if (c[i] == '$') {
            try {
                while (true) {
                    
                    if (b.endsWith("$")) {
                        i += b.length();
                        tokenName = "COMMENT2";
                        b = read.readLine();
                        c = b.toCharArray();
                        i = 0;
                        break;
                    }
                    i += b.length();
                    line++;
                    // out COMMENT2
                    b = read.readLine();
                    c = b.toCharArray();
                    i = 0;
                }
                return true;
            } catch (IOException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isStringConstant() {
        String stringconst ="";
        if (c[i] == '"') {
            tokenName = "STRINGCONST";
            i++;
            while (c[i] != '"') {
                stringconst = stringconst + c[i];
                i++; 
            }
            
            printSTRING = "'" + stringconst + "'";
            tokenName = printSTRING;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumDecConstant() {
        if (isNumber(c[i])) {
            
            String numconstant="", decconstant="";
            
            while (i < c.length-1 && isNumber(c[i])) {
               tokenName = "NUMCONST";
              numconstant = numconstant + c[i]; 
              i++;
        
            }
            
               tokenName = numconstant;
            
            
            
            
           
            if (isNumber(c[i]) && (i==c.length-1)){
                return true;
            }
            if (!isNumber(c[i]) && (i!=c.length-1)){
                i--;
                return true;
            }
            if (c[i] == '.' && (i == c.length -1))
            {
                i--;
                
                return true;
            }
            else if (c[i] == '.' && (i != c.length -1)){
                if (isNumber(c[i+1])){
                    tokenName ="DECCONST";
                    decconstant = numconstant + c[i];
                
                    i++;
                    while (i < c.length-1 && isNumber(c[i])) {
                        decconstant = decconstant + c[i]; 
                        i++;

                    }
                }
                if (i == c.length -1 && isNumber(c[i])){
                   
                    return true;
                }
                else if (!isNumber(c[i])){
                    i--;
                    return true;
                }
            }
        
        
        }return false;
    }

    public static boolean isLetter(char a) {
        String b = "" + a, alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZS";
        return alphabet.contains(b);
    }

    public static boolean isNumber(char a) {
        String b = "" + a, number = "1234567890";
        return (number.contains(b));
    }

    public static boolean isSymbol() {
       
        if (c[i] == '+' || c[i] == '-') {
            if(((c[i]=='+')&&(c[i-1] == '+' || c[i+1] == '+')) ||
                    ((c[i]=='-')&&(c[i-1] == '-' || c[i+1] == '-'))){
                tokenName = "AROP3";
                i++;
                return true;
            }
        else if(c[i] == '-' && (((!isNumber(c[i-1]))||c[i-1] == '=') &&(isNumber(c[i+1])) ) ){
                tokenName = "NEG";
                return true;
        } 
            else tokenName = "AROP1";
            return true;
        } else if (c[i] == '*' || c[i] == '/') {
            tokenName = "AROP2";
            return true;
        } else if (c[i] == '&' || c[i] == '|') {
            tokenName = isLOGOP();
            return true;
        } else if (c[i] == '!') {
            i++;
            if (c[i] == '=') {
                tokenName = "EQOP";
                return true;
            } else {
                i--;
                tokenName = "NOTOP";
                return true;
            }
        } else if (c[i] == '<' || c[i] == '>' || c[i] == '!') {
            tokenName = "RELOP";
            if (c[i+1] == '='){
                i++;
            }
            return true;
            
        } else if (c[i] == '=') {
            i++;
            if (c[i] == '=') {
                tokenName = "EQOP";
                return true;
            } else {
                i--;
                tokenName = "ASSIGNOP";
                return true;
            }
        } else if (c[i] == '(') {
            tokenName = "LPAREN";
            return true;
        } else if (c[i] == ')') {
            tokenName = "RPAREN";
            return true;
        } else if (c[i] == '{') {
            tokenName = "LCURL";
            return true;
        } else if (c[i] == '}') {
            tokenName = "RCURL";
            return true;
        } else if (c[i] == '[') {
            tokenName = "LBRACE";
            
            return true;
        } else if (c[i] == ']') {
            tokenName = "RBRACE";
            return true;
        } else if (c[i] == ':') {
            tokenName = "CONCAT";
            return true;
        } else if (c[i] == '.') {
            tokenName = "LINEDEL";
            return true;
        } else if (c[i] == ',') {
            tokenName = "COMMA";
            return true;
        } else if (c[i] == '^') {
            tokenName = "POW";
            return true;
        } else {
            return false;
        }
        
    }

    public static String isLOGOP() {
        if (c[i] == '&') {
            i++;
            if (c[i] == '&') {
                return "LOGOP";
            } else {
                i--;
                printERR = printERR+"[LINE: " +line+" ERROR] Invalid LOGOP '"+c[i]+c[i+1]+"' "+"\n";
                return "?"+c[i]+c[i+1]+"?";
            }
        } else if (c[i] == '|') {
            i++;
            if (c[i] == '|') {
                return "LOGOP";
            } else {
                i--;
                printERR = printERR+"[LINE: " +line+" ERROR] Invalid LOGOP '"+c[i]+c[i+1]+"' "+"\n";
                return "?"+c[i]+c[i+1]+"?";
            }
        }
        return "ERROR LOGOP";
    }

    public static String getToken() {
       
               
        if (isRWords()) {
            
            return tokenName;
        } else if (isIdentifier()) {
            return tokenName;
        } else if (isComment()) {
            return tokenName;
        } else if (isComments()) {
            return tokenName;
        } else if (isStringConstant()) {
            return tokenName;
        } else if (isNumDecConstant()) {
            return tokenName;
        } else if (isSymbol()) {
            return tokenName;
        }else if(c[i] == ' '){
        }
        
        return "";
     
        
    }
}
