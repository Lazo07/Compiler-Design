package compilerproj;
import java.util.HashMap;
public class tokentable {
    private static HashMap<String, String> hashTable = new HashMap<>();
    
    public tokentable(){
        
        hashTable.put("yes", "BOOLCONST");
        hashTable.put("no", "BOOLCONST");
        hashTable.put("number", "INTEGER");
        hashTable.put("boolean", "BOOLEAN");
        hashTable.put("word", "STRING");
        hashTable.put("decimal", "FLOAT");
        hashTable.put("do", "DO");
        hashTable.put("while", "WHILE");
        hashTable.put("for", "FOR");
        hashTable.put("codename", "CODENAME");
        hashTable.put("declare", "DECLARE");
        hashTable.put("main", "MAIN");
        hashTable.put("wordlen", "WORDLEN");
        hashTable.put("if", "IFSTMT");
        hashTable.put("elseif", "ELSEIFSTMT");
        hashTable.put("else", "ELSESTMT");
        hashTable.put("switch", "SWITCH");
        hashTable.put("case", "CASE");
        hashTable.put("input", "INSTMT");
        hashTable.put("output", "OUTSTMT");
        hashTable.put("start", "START");
        hashTable.put("stop", "STOP");
        hashTable.put("break", "BREAK");
        hashTable.put("continue", "CONTINUE");
        hashTable.put("array", "ARRAY");
        hashTable.put("default", "DEFAULT");
     
        
    }
    
    public static boolean isInTable(String lex){
        return hashTable.containsKey(lex);
    }
    
    public static String getTokenName(String lex){
        if (isInTable(lex)){
            return hashTable.get(lex);
        } else{
            return "NO";
            //THERE IS NO TOKEN FOR THE LEXEME
        }
    }    
    public static void addToTable(String lex, String tok){
        hashTable.put(lex, tok);
    }
    
}
