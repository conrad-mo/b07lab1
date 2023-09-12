public class Polynomial {
  double[] coefficients;

  public Polynomial(){
    coefficients = new double[]{0};
  }
  public Polynomial(double[] coarray){
    coefficients = coarray;
  }
  public Polynomial add (Polynomial num){
    int length;
    if (num.coefficients.length > coefficients.length){
      length = num.coefficients.length;
    }
    else{
      length = coefficients.length;
    }
    double[] placeholder = new double[length];
    Polynomial finalpPolynomial = new Polynomial(placeholder);
    if (num.coefficients.length > coefficients.length){
      for (int i = 0; i< coefficients.length; i++){
        finalpPolynomial.coefficients[i] = num.coefficients[i] + coefficients[i];
      }
      for (int i = coefficients.length; i< num.coefficients.length; i++){
        finalpPolynomial.coefficients[i] = num.coefficients[i];
      }
    }
    else{
      for (int i = 0; i< num.coefficients.length; i++){
        finalpPolynomial.coefficients[i] = num.coefficients[i] + coefficients[i];
      }
      for (int i = num.coefficients.length; i< coefficients.length; i++){
        finalpPolynomial.coefficients[i] = coefficients[i];
      }
    }
    return finalpPolynomial;
  }
  public double evaluate(double x){
    double sum = 0;
    for (int i = 0; i < coefficients.length; i++){
      sum = sum + coefficients[i] * Math.pow(x, i);
    }
    return sum;
  }
  public boolean hasRoot(double x){
    double sum = evaluate(x);
    if (sum == 0){
      return true;
    }
    return false;
  }
}