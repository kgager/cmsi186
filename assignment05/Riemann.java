/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Riemann.java
 *  Purpose       :  The main program for the Riemann class
 *  @see
 *  @author       :  Kevin Gager
 *  Date written  :  2019-10-31
 *  Description   :  This class provides a bunch of methods for Homework 5
 *  Exceptions    :  IllegalArgumentException when the input arguments are not doubles
                     NumberFormatException when the input arguments not the right amount, or result in the
 *                   balls overlapping initially or being initially out of bounds.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-10-31  Kevin Gager  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Riemann{
  public static double curRieVal=0;
  public static double prevRieVal=0;
  public static double DEFAULT_PERCENTAGE = .01;
  public static double lowerBound;
  public static double upperBound;
  private static int numOfRectangles =0;
  private static double rectangleWidth;
  private static double percentage;
  private static double currentPercentage = 0;
  private static int awayFromEnd =1;
  private static double[] arr;
  private static String equation;
  private static double xValue;
  private static boolean firstTimeGettingXvalue=true;
  public static boolean displayList = false;

  /**
   *  Main method
   *  runs program and call helper methods
   */
  public static void main(String[] args) {
    String inputLine = "";
    System.out.println("\n Welcome to Riemann! \n");
    System.out.println("This program simulates a Riemann sum approximation");
    System.out.println("To see list of available functions run this program with List as the first argument\n");
    handleIntialArgs(args);
    if(!displayList)
    {
      double pDiff =0;
      double vDiff =0;
      while(currentPercentage>percentage || numOfRectangles < 3)
      {
        firstTimeGettingXvalue =true;
        numOfRectangles++;
        vDiff = curRieVal-prevRieVal;
        prevRieVal = curRieVal;
        currentPercentage= vDiff/curRieVal;
        curRieVal = 0;
        rectangleWidth = findRectangleWidth();
        calculateArea(args);
      }
      System.out.println("The area of "+equation+" from "+lowerBound+ " to "+upperBound+" is "+curRieVal);
      System.out.println("The equation was approximated using "+numOfRectangles+" rectangles");
    }

    }

    /**
     *  Method to handle all the input arguments from the command line
     *  Checks to see if the right amount or arguments are inputted
     *  Checks to see if all arguments between the first and end are doubles
     *  Checks to see if the first argument is a string
     *  Checks to see if initial arguments result in the balls overlapping initially or being initially out of bounds
     *  Makes each set of four arguments into a ball for the Simulation
     *  Checks to see if the last argument is a Percentage
     *        If so, the method changes the percentage to the inputted value
     *        Else, makes percentage the default value
     *  this sets up the variables for the simulation
     * @throws      IllegalArgumentException
     *              NumberFormatException
     */
  public static void handleIntialArgs( String[] args) {
    percentage = DEFAULT_PERCENTAGE;
    if(args.length == 0){
      throw new IllegalArgumentException("Please enter arguments");
    }
    try{
        if(args[0].equals("list"))
        {
          showList();
          displayList = true;
        }
        else{

    if(args.length <3)
    {
      throw new IllegalArgumentException("Please enter atleast three arguments");
    }
    setBounds(args);
    arr = new double [args.length-awayFromEnd];
    for(int i =1; i< args.length+1-awayFromEnd;i++)
    {
      try{
          arr[i-1] = Double.parseDouble(args[i]);
      }
      catch(NumberFormatException nfe)
      {
        throw new IllegalArgumentException("Make middle terms valid numbers");
      }
    }
    }
   }
   catch(NumberFormatException nfe){
    System.out.println("Please enter the first argument as a string");
    System.exit(5);
   }
  }

  public static void calculatePolynomial(){
      for (int i=0;i<numOfRectangles;i++ )
      {
         xValue = getMidpoint(rectangleWidth);
         for (int j=0;j<arr.length ;j++ )
         {
            if(j!=0)
            {
               curRieVal += rectangleWidth*arr[j]*Math.pow(xValue,j);
            }
            else
            {
                curRieVal += rectangleWidth* arr[j];
            }
         }
      }
    }

  public static void calculateSin(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.sin(xValue);
    }
  }

  public static void calculateCos(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.cos(xValue);
    }

    }

  public static void calculateTan(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.atan(xValue);
    }

    }

    public static void calculateASin(){
      for (int i=0;i<numOfRectangles;i++ )
      {
        xValue = getMidpoint(rectangleWidth);
        curRieVal += rectangleWidth * Math.asin(xValue);
      }
    }

    public static void calculateACos(){
      for (int i=0;i<numOfRectangles;i++ )
      {
        xValue = getMidpoint(rectangleWidth);
        curRieVal += rectangleWidth * Math.acos(xValue);
      }

      }

    public static void calculateATan(){
      for (int i=0;i<numOfRectangles;i++ )
      {
        xValue = getMidpoint(rectangleWidth);
        curRieVal += rectangleWidth * Math.tan(xValue);
      }

      }

  public static void calculateLog10(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.log10(xValue);
    }

    }

    public static void calculateLn(){
      for (int i=0;i<numOfRectangles;i++ )
      {
        xValue = getMidpoint(rectangleWidth);
        curRieVal += rectangleWidth * Math.log(xValue);
      }

      }

  public static void calculateExp(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.exp(xValue);
    }

    }

  public static void calculateSqrt(){
    for (int i=0;i<numOfRectangles;i++ )
    {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.sqrt(xValue);
    }

    }

  public static void setBounds(String[] args)
  {
    String lastArg = args[args.length-awayFromEnd];
    if(lastArg.charAt(lastArg.length()-1)=='%')
    {
      lastArg = lastArg.substring(0,lastArg.length()-1);
      try
      {
        percentage = Double.parseDouble(lastArg);
      }
      catch(NumberFormatException nfe)
      {
        throw new IllegalArgumentException("Make the percentage a valid number");
      }
      System.out.println("Percentage = "+percentage);
      awayFromEnd ++;
    }
    try
    {
      upperBound =  Double.parseDouble(args[args.length-awayFromEnd]);
    }
    catch(NumberFormatException nfe)
    {
      throw new IllegalArgumentException("Make the upper bound a valid number");
    }
    awayFromEnd ++;
    try
    {
      lowerBound =  Double.parseDouble(args[args.length-awayFromEnd]);
    }
    catch(NumberFormatException nfe)
    {
      throw new IllegalArgumentException("Make the lower bound a valid number");
    }
    awayFromEnd ++;
    if(lowerBound>=upperBound)
    {
      throw new IllegalArgumentException("Please make the upper bound greater than the lower bound");
    }
  }

  private static void calculateArea(String[] args){
    try{
        if(args[0].equals("poly"))
        {
          for (int i=0;i<arr.length ;i++ ) {
            if(i==0)
            {
              equation = "y = "+ arr[0];
            }
            else if(i==1){
              equation += " + "+ arr[i] +"x";
            }
            else{
              equation += " + "+ arr[i] +"x^"+i;
            }

          }
          calculatePolynomial();
        }
        else if(args[0].equals("sin"))
        {
          equation = "y = sin x";
          calculateSin();
        }
        else if(args[0].equals("cos"))
        {
          equation = "y = cos x";
          calculateCos();
        }
        else if(args[0].equals("tan"))
        {
          equation = "y = tan x";
          calculateTan();
        }
        else if(args[0].equals("log10"))
        {
          equation = "y = log10 x";
          calculateLog10();
        }
        else if(args[0].equals("ln"))
        {
          equation = "y = ln x";
          calculateLn();
        }
        else if(args[0].equals("sqrt"))
        {
          equation = "y = x^(1/2)";
          calculateSqrt();
        }
        else if(args[0].equals("exp"))
        {
          equation = "y = e^x";
          calculateExp();
        }
        else if(args[0].equals("asin"))
        {
          equation = "y = arcsin x";
          calculateASin();
        }
        else if(args[0].equals("acos"))
        {
          equation = "y = arccos x";
          calculateACos();
        }
        else if(args[0].equals("atan"))
        {
          equation = "y = arctan x";
          calculateATan();
        }
        else{
          throw new IllegalArgumentException("Sorry, but "+args[0]+" is not a valid function for this program");
        }
      }
      catch(NumberFormatException nfe){
        System.out.println("Please enter the first argument as a string");
        System.exit(5);
      }
  }

  private static double findRectangleWidth(){
    double range = upperBound -lowerBound;
    return (range/numOfRectangles);
  }

  private static double getMidpoint(double rw){
    double answ = xValue;
    if(firstTimeGettingXvalue)
    {
      answ = lowerBound + rw/2;
      firstTimeGettingXvalue =false;
    }
    else{
      answ+= rw;
    }
    return answ;
  }

  public static void showList()
  {
    System.out.println("Available Functions--- \n");
    System.out.println("Poly  : integrates a polynomial function where args[1] … args[k]");
    System.out.println("        specify the coefficients of the x0 … xk terms of the polynomial");
    System.out.println("Sin   : integrates the sine function");
    System.out.println("Cos   : integrates the cosine function");
    System.out.println("Tan   : integrates the tangent function");
    System.out.println("Asin  : integrates the arc sine function");
    System.out.println("Acos  : integrates the arc cosine function");
    System.out.println("Atan  : integrates the arc tangent function");
    System.out.println("Ln    : integrates the (natural) logarithm function");
    System.out.println("Log10 : integrates the base 10 logarithm function");
    System.out.println("Exp   : integrates the function of Euler's number e raised to the power of x");
    System.out.println("Sqrt  : integrates the function of a rounded positive square root for x");


  }

}
