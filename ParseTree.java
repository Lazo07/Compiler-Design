/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproj;

import java.util.List;

/**
 *
 * @author ACER
 */
public class ParseTree {
    List<ParseTree> leaf;
    ParseTree node;
    public ParseTree(ParseTree nodex){
        node = nodex;
    }
    
    
}
