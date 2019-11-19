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

  /**
   *  Main method
   *  runs program and call helper methods
   */
  public static void main(String[] args) {
    String inputLine = "";
    System.out.println("\n Welcome to Riemann! \n");
    System.out.println("This program simulates a Riemann sum approximation");
    handleIntialArgs(args);
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
      calculateArea(args);
      System.out.println("currentPercentage= "+currentPercentage+", percentage= "+percentage);
      System.out.println("percentage-currentPercentage= "+pDiff);
    }
    System.out.println("The area of "+equation+" from "+lowerBound+ " to "+upperBound+" is "+curRieVal);
    }


  public static void handleIntialArgs( String[] args) {
    percentage = DEFAULT_PERCENTAGE;
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

  public static void calculatePolynomial(String[] args){
    rectangleWidth = findRectangleWidth();
    curRieVal =0;
    for (int i=0;i<numOfRectangles;i++ ) {
      xValue = getMidpoint(rectangleWidth);
      for (int j=0;j<arr.length ;j++ ) {
        if(j!=0)
        {
            curRieVal += rectangleWidth*arr[j]*Math.pow(xValue,j);
          //  System.out.println("tAns: "+ curRieVal+", rw="+rectangleWidth+", j= "+j+", arr[j]= "+arr[j]+", xValue= "+xValue+ ", RW*M.pow(arr[j],j)=" + rectangleWidth*Math.pow(arr[j],j));
        }
        else{
          curRieVal += rectangleWidth* arr[j];
        }
      }
        System.out.println("* "+ curRieVal);

        // curRieVal += curRieVal;
    }
    System.out.println("\n    -     curRieVal= "+curRieVal+"\n");
    }

  public static void calculateSin(String[] args){
    System.out.println("Validating sin... ");
    rectangleWidth = findRectangleWidth();
    for (int i=0;i<numOfRectangles;i++ ) {
      xValue = getMidpoint(rectangleWidth);
      curRieVal += rectangleWidth * Math.sin(xValue);
      }

    }



  public static void calculateCos(String[] args){
    System.out.println("Validating cos... write code here");

    }

  public static void calculateTan(String[] args){
    System.out.println("Validating tan... write code here");

    }

  public static void calculateLog(String[] args){
    System.out.println("Validating log... write code here");

    }

  public static void calculateExp(String[] args){
    System.out.println("Validating Exp... write code here");

    }

  public static void calculateSqrt(String[] args){
    System.out.println("Validating sqrt... write code here");

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
          // System.out.println("this simulation is approximating a polynomial");
          calculatePolynomial(args);
        }
        else if(args[0].equals("sin"))
        {
          System.out.println("this simulation is doing a sine function");
          calculateSin(args);
        }
        else if(args[0].equals("cos"))
        {
          System.out.println("this simulation is doing a cosine function");
          calculateCos(args);
        }
        else if(args[0].equals("tan"))
        {
          System.out.println("this simulation is doing a tangent function");
          calculateTan(args);
        }
        else if(args[0].equals("log"))
        {
          System.out.println("this simulation is doing a log function");
          calculateLog(args);
        }
        else if(args[0].equals("sqrt"))
        {
          System.out.println("this simulation is doing a sqrt function");
          calculateSqrt(args);
        }
        else if(args[0].equals("exp"))
        {
          System.out.println("this simulation is doing a exp function");
          calculateExp(args);
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
      // System.out.println("got here (= , answ = "+answ);
    }
    else{
      answ+= rw;
      // System.out.println("went here instead )=");
    }
    return answ;
  }


}
