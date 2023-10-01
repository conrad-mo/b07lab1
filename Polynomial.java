import javax.lang.model.type.NullType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial {
  public double[] coefficients;
  public int[] exponents;

  public Polynomial(){
    coefficients = new double[]{0};
    exponents = new int[]{1};
  }
  public Polynomial(double[] coarray, int[] exparray){
    coefficients = coarray;
    exponents = exparray;
  }
  public Polynomial(File file) throws FileNotFoundException {
    Scanner input = new Scanner(file);
    String line = input.nextLine();
    String[] linearray = line.split("");
    int length = 1;
    for (int i = 0; i < linearray.length; i++){
      if (linearray[i].equals("+") || linearray[i].equals("-")){
        if (i != 0){
          length++;
        }
      }
    }
    coefficients = new double[length];
    exponents = new int[length];
    int index = 0;
    for (int i = 0; i < linearray.length; i++) {
      String temp = "";
      if (linearray[i].equals("-") || linearray[i].equals("+")){
        if (linearray[i].equals("-")){
          temp = temp + linearray[i];
        }
        i++;
      }
      while (i < linearray.length && !(linearray[i].equals("-") || linearray[i].equals("+"))) {
        temp = temp + linearray[i];
        i++;
        if (i >= linearray.length){
        }
      }
      if (temp.contains("x")) {
        double co = Double.parseDouble(temp.substring(0, temp.indexOf("x")));
        int exp = 0;
        if (temp.indexOf("x") + 1 < temp.length()){
          exp = Integer.parseInt(temp.substring(temp.indexOf("x") + 1));
        }
        else{
          exp = 1;
        }
        coefficients[index] = co;
        exponents[index] = exp;
        index++;
      } else {
        double co = Double.parseDouble(temp);
        coefficients[index] = co;
        exponents[index] = 0;
        index++;
      }
      i--;
    }
  }
  public Polynomial add (Polynomial num){
    double[] placeholderone = new double[num.coefficients.length + coefficients.length];
    int[] placeholdertwo = new int[num.coefficients.length + coefficients.length];
    int length = 0;
    Polynomial finalpPolynomial = new Polynomial(placeholderone, placeholdertwo);
    int index = 0;
    boolean checker = false;
    for (int i = 0; i < num.exponents.length; i++){
      for (int j = 0; j < exponents.length; j++){
        if (exponents[j] == num.exponents[i]){
          finalpPolynomial.exponents[index] = num.exponents[i];
          finalpPolynomial.coefficients[index] = num.coefficients[i] + coefficients[j];
          if (num.coefficients[i] + coefficients[j] != 0){
            length++;
          }
          index++;
          checker = true;
          break;
        }
      }
      if (!checker){
        finalpPolynomial.exponents[index] = num.exponents[i];
        finalpPolynomial.coefficients[index] = num.coefficients[i];
        index++;
        length++;
      }
      checker = false;
    }
    checker = false;
    for (int i = 0; i < exponents.length; i++){
      for (int j = 0; j < finalpPolynomial.exponents.length; j++){
        if (exponents[i] == finalpPolynomial.exponents[j]){
          checker = true;
          break;
        }
      }
      if (!checker){
        finalpPolynomial.exponents[index] = exponents[i];
        finalpPolynomial.coefficients[index] = coefficients[i];
        index++;
        length++;
      }
      checker = false;
    }
    double[] finalco = new double[length];
    int[] finalexp = new int[length];
    int indexfinal = 0;
    for (int i = 0; i < finalpPolynomial.coefficients.length; i++){
      if (finalpPolynomial.coefficients[i] != 0){
        finalco[indexfinal] = finalpPolynomial.coefficients[i];
        finalexp[indexfinal] = finalpPolynomial.exponents[i];
        indexfinal++;
      }
    }
    return new Polynomial(finalco, finalexp);
  }
  public double evaluate(double x){
    double sum = 0;
    for (int i = 0; i < coefficients.length; i++){
      sum = sum + coefficients[i] * Math.pow(x, exponents[i]);
    }
    return sum;
  }
  public boolean hasRoot(double x){
    double sum = evaluate(x);
    return sum == 0;
  }
  public Polynomial multiply (Polynomial second){
    Polynomial[] allpolynomials = new Polynomial[second.coefficients.length * coefficients.length];
    int index = 0;
    for (int i = 0; i < second.coefficients.length; i++){
      for (int j = 0; j < coefficients.length; j++){
        double[] tempcoefficients = new double[]{second.coefficients[i] * coefficients[j]};
        int[] tempexponents = new int[]{second.exponents[i] + exponents[j]};
        Polynomial temppolynomial = new Polynomial(tempcoefficients, tempexponents);
        allpolynomials[index] = temppolynomial;
        index++;
      }
    }
    for (int i = 1; i < allpolynomials.length; i++){
      allpolynomials[0] = allpolynomials[0].add(allpolynomials[i]);
    }
    return allpolynomials[0];
  }
  public void saveToFile (String filename) throws FileNotFoundException {
    String masterstring = "";
    for (int i = 0; i < coefficients.length; i++){
      if (i != 0 && coefficients[i] > 0){
        masterstring = masterstring + "+";
      }
      masterstring = masterstring + coefficients[i];
      if (exponents[i] != 0){
        masterstring = masterstring + "x" + exponents[i];
      }
    }
    PrintStream ps = new PrintStream(filename);
    ps.println(masterstring);
    ps.close();
  }
}