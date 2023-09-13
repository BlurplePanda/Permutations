// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2023T2, Assignment 4
 * Name: Amy Booth
 * Username: boothamy
 * ID: 300653766
 */

import ecs100.*;

import java.sql.Array;
import java.util.*;
import java.awt.Color;

/** 
 *  Compute all permutations of a list of Strings
 *
 *  You only have to write one method - the extendPermutations(...) method
 *  which does the recursive search.  
 */
public class Permutations {

    /**
     * Constructs a list of all permutations of the given items
     * by calling a recursive method, passing in a set of the items to permute
     * and an empty list to build up.
     * Prints the total number of permutations in the message window (with
     *  UI.printMessage(...);
     */
    public List<List<String>> findPermutations(Set<String> items){

        Set<String> copyOfItems = new HashSet<String>(items);   // a copy of the set of items that can be modified
        List<List<String>> ans = new ArrayList<List<String>>(); // where we will collect the answer
        counter=0;
        //suggested approach:
        extendPermutation(copyOfItems, new Stack<String>(), ans);   

        return ans;
    }

    /**
     * Builds all possible permutations recursively by adding the
     * remaining items on to the end of the permutation built up so far
     * and adding each completed permutation to the list of all permutations
     * @param remainingItems all remaining user-entered items
     * @param permutationSoFar the current permutation in its current state
     * @param allPermutations all permutations found (up to 10000 to save memory)
     */
    public void extendPermutation(Set<String> remainingItems, Stack<String> permutationSoFar, List<List<String>> allPermutations){
        if (remainingItems.isEmpty()) {
            counter++;
            if (allPermutations.size() < 10000) {
                allPermutations.add(new ArrayList<>(permutationSoFar));
            }
            return;
        }
        for (String item : remainingItems) {
            Set<String> remainingItemsCopy = new HashSet<>(remainingItems);
            remainingItemsCopy.remove(item);
            permutationSoFar.push(item);
            extendPermutation(remainingItemsCopy, permutationSoFar, allPermutations);
            permutationSoFar.pop();
        }
    }

    //===================================================
    // User Interface code

    /**
     * Setup GUI
     * Buttons to run permutations on either letters or words
     */
    public void setupGUI(){
        UI.addButton("A B C D E", ()->{printAll(findPermutations(Set.of("A","B","C","D","E")));});
        UI.addTextField("Letters", (String v)->{printAll(findPermutations(makeSetOfLetters(v)));});
        UI.addTextField("Words", (String v)->{printAll(findPermutations(makeSetOfWords(v)));});
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

    public void printAll(List<List<String>> permutations){
        UI.clearText();
        for (int i=0; i<permutations.size(); i++){
            for (String str : permutations.get(i)){UI.print(str+" ");}
            UI.println();
        }
        UI.println("----------------------");
        UI.printf("%d items:\n", permutations.get(0).size());
        UI.printf("%,d permutations\n", counter);
        UI.println("----------------------");
    }

    /**
     * Makes a set of strings, one string for each character in the argument
     */
    public Set<String> makeSetOfLetters(String str){
        Set<String> ans = new HashSet<String>();
        for (int i=0; i<str.length(); i++){
            if (str.charAt(i)!=' '){
                ans.add(""+str.charAt(i));
            }
        }
        return Collections.unmodifiableSet(ans);
    }

    /**
     * Makes a set of strings, one string for each word in the argument
     */
    public Set<String> makeSetOfWords(String str){
        Set<String> ans = new HashSet<String>();
        for (String v : str.split(" ")){ans.add(v);}
        return Collections.unmodifiableSet(ans);
    }

    // Counter for the number of complete permutations found
    private long counter = 0;  

    /** Report the value of counter in the message area */
    public void reportCounter(){
        if ((counter<<54)==0) {UI.printMessage((counter>10000000)?((counter>>>20)+"M"):((counter>>>10)+"K"));}
    }

    
    // Main
    public static void main(String[] arguments) {
        Permutations p = new Permutations();
        p.setupGUI();
    }
}
