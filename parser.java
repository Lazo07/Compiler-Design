package compilerproj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class parser {
    public static int i = 0, line = 0;
    private static char[] c; 
    private static String b, tokenName = "", printID="", printSTRING="", printNUMBER="", printBOOL="", printERR="";
    private static FileReader scfile;
    private static BufferedReader read;
    
   
    public static void main(String[] args) {
        
            Stack<String> holder = new Stack<>();
            Stack<String> ituna = new Stack<>();
        try {
            scanner.line = 1;
            scfile = new FileReader("E:/PHASE4/CONCAT.txt");
            read = new BufferedReader(scfile);
            b = read.readLine();
            System.out.println("SCANNED LEX to TOK:============================");
            do {
                System.out.print(scanner.line+":  ");
                scanner.c = b.toCharArray();
                while (scanner.i < scanner.c.length) {
                    
                   tokenName = scanner.getToken();
                   System.out.print(tokenName + " ");
                   if(!tokenName.equals("")){//PANTANGGAL NG SPACE!!!
                   holder.push(tokenName);
                   }
                   scanner.i++;
                }
                
                
                
                System.out.println();
                scanner.line++;
                b = read.readLine();
                scanner.i = 0;
            } while (b != null);
            
                while(!holder.isEmpty()){
                    ituna.push(holder.peek());
                    holder.pop();
                }
        } catch (IOException err) {

        }
        
        //System.out.println("================================================");
        //System.out.println("SCANNING ERRORS: ");
        //System.out.println(scanner.printERR);
        //System.out.println("================================================");
      
        
        System.out.println("PARSING: ");
        Grammar g = new Grammar(ituna);
        
        g.run();
        TreeEl pt=g.getTree();
        
        Semantics sem = new Semantics(pt);
        sem.run();
        //System.out.println(g.parsetree);
       
    } 
}
