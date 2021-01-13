
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import org.apache.commons.io.FileUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author akash
 */
public class ConvertToPrime {
    
    public void printPrime() throws IOException{
    File primeFile = new File("prime.txt");
    String akashInASCII = "065107097115104032071097110101115097110033";
    String primeString = FileUtils.readFileToString(primeFile);
    primeString = primeString.replace("\n","");
    // Akash replace \n with nothing here
    String outString;
    
    
    primeString = fixString(primeString, akashInASCII);
    //File possiblePrimesFile = new File("possible_primes.txt");
    FileWriter fw = new FileWriter("possible_primes.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw);
    out.println(primeString);
    out.println("Begin Iterations");
    
    for(int x = 0; x < 10000; x++){
        outString = mutateString(primeString,20,"065107097115104032071097110101115097110033");
    
        BigInteger pNumber = new BigInteger(outString);
    
        if (pNumber.isProbablePrime(30)) {
            out.println("Number " + x + " is Possibly Prime");
            out.println(outString);
        }
        else {
            out.println("Number " + x + " is not Prime");
        }
        out.flush();
    }
    out.println("End Iterations");
    fw.close();


   // System.out.println(primeString);
    
    
        //System.out.println(pNumber.isProbablePrime(1));
    
}
    
    public String fixString(String inputString, String sufString){
        inputString = inputString.substring(0, inputString.length() - sufString.length());
        inputString = inputString + sufString;
        return inputString;
    }
    
    public String mutateString(String inputString, int numberChange, String sufString){
        char[] inputStringChars = inputString.toCharArray(); 
        for(int x = 0; x < numberChange; x++){
           int randomInt = (int) (Math.random() * (inputString.length() - sufString.length()));
           int randomVal = (int) (Math.random() * 10);
           char randomValAskii = (char) (randomVal + 48);
           inputStringChars[randomInt] = randomValAskii;  
        }

        inputString = String.valueOf(inputStringChars);
        return inputString;
    }
}