/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  SoccerSim.java
 *  Purpose       :  The main program for the SoccerSim class
 *  @see
 *  @author       :  Kevin Gager
 *  Date written  :  2019-10-31
 *  Description   :  This class provides a bunch of methods for Homework 4
 *  Exceptions    :  IllegalArgumentException when the input arguments are not doubles, not the right amount, or result in the
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

public class SoccerSim{
  public static int numOfSoccerballs;
  public static Ball[] soccerBalls;
  public static double timeSlice;
  public static double DEFAULT_TIMELICE = 1;
  public static double timeElapsed = 0;
  public static boolean ballsCollided = false;
  private static BufferedReader input = new BufferedReader( new InputStreamReader(System.in));
  private static boolean hasPoll;

  private static int poleXLocation;
  private static int poleYLocation;

  /**
   *  Main method
   *  runs program and call helper methods
   */
  public static void main(String[] args) {
    validateArgsAndSetupSim(args);
    String inputLine = "";
    System.out.println("\n Welcome to SoccerSim! \n");
    System.out.println("This program simulates an arbitrary number of soccer balls being kicked on a perfectly flat playground with a height of 350 and a wideth of 150, at speeds and directions dictated by the user!");
    System.out.println("\nDo you want a flag pole in the field? \n"+
                            "Y - yes \n"+
                            "N - no\n");
    try{
        inputLine = input.readLine();
        if('Y' == inputLine.toUpperCase().charAt(0))
        {
          hasPoll = true;
          poleXLocation = (int)(150*Math.random()-75);
          poleYLocation = (int)(350*Math.random()-175);
          System.out.println("Pole created at ("+poleXLocation+","+poleYLocation +") proceding with simulation...");
        }
        else{
          hasPoll = false;
          System.out.println("No pole created, proceding with simulation...");
        }
      }

        catch (IOException ioe) {
          System.out.println("Caught IO exception, bad input from user.");
    }

    report();
    int i =0;
    while( collisonCheck()){
      if(!atLeastOneBallStillMoving()){
        System.out.println("\nAt t = "+timeElapsed+", all balls stopped moving. No collisions detected");
        break;
      }
      simUpdate();
      report();
      i++;
      if(i == 200){
        break;
      }
      if(isNotInBounds())
      {
        break;
      }
      if(hasPoll){
        if(collidedWithPoll()){
          break;
        }
      }
    }
    if(ballsCollided)
    {
      System.out.println("\nBalls collided at t = "+timeElapsed+" secounds");
    }


  }
  /**
   *  Method to handle all the input arguments from the command line
   *  Checks to see if the right amount or arguments are inputted
   *  Checks to see if all arguments are doubles
   *  Checks to see if initial arguments result in the balls overlapping initially or being initially out of bounds
   *  Makes each set of four arguments into a ball for the Simulation
   *  Mkaes last argument if applicable to be the timeslice
   *  this sets up the variables for the simulation
   * @throws      IllegalArgumentException
   */
  public static void validateArgsAndSetupSim( String args[] ) {
    if(args.length % 4 == 2 ||args.length % 4 == 3 || args.length == 0 || args.length ==1)
    {
      throw new IllegalArgumentException("Please enter the right amount of arguments");
    }
    if(args.length % 4 == 0)
    {
      numOfSoccerballs = args.length/4;
      timeSlice = DEFAULT_TIMELICE;
    }
    if(args.length % 4 == 1)
    {
      numOfSoccerballs = (args.length-1)/4;
      try{
        timeSlice = Double.parseDouble(args[args.length -1]);
      }
      catch(NumberFormatException nfe){
        System.out.println("Please enter the arguments as numbers");
        System.exit(5);
      }
    }
    soccerBalls = new Ball[numOfSoccerballs];
    int i = 0;
    int j = 0;
    while(j< numOfSoccerballs){
      try{
        soccerBalls[j] = new Ball (Double.parseDouble(args[i]),
                                   Double.parseDouble(args[i + 1]),
                                   Double.parseDouble(args[i + 2]),
                                   Double.parseDouble(args[i + 3]));
      }
      catch(NumberFormatException nfe){
        System.out.println("Please enter the arguments as numbers");
        System.exit(5);
      }
      j++;
      i+=4;
    }
    for (int i2 =0; i2<soccerBalls.length;i2++ ) {
      soccerBalls[i2].setOutOfBounds(-75,75,-175,175);
      if(!soccerBalls[i2].isInBounds())
      {
        throw new IllegalArgumentException("Please set the initial position of the balls to be in bounds");
      }

    }
    if(collisonCheck() == false){
      throw new IllegalArgumentException("Please set the initial positions of the balls to be non overlapping");
    }
  }

  /**
   *  Method to give the user feedback about the position and velocities of each ball based off at the current time
   */
  public static void report(){
    System.out.println("\nAt time t = "+timeElapsed + " secounds:");
    for (int i=0;i< soccerBalls.length ;i++ ) {
      System.out.println(soccerBalls[i]);

    }
  }

  /**
   *  Method to update the position and velocities of each ball due to passing time
   */
  public static void simUpdate(){
    for (int i =0;i<soccerBalls.length ; i++ ) {
      soccerBalls[i].move(timeSlice);
      soccerBalls[i].setOutOfBounds(-75,75,-175,175);
    }
    timeElapsed += timeSlice;
    }

  /**
   *  Method to check if at least one ball is still moving
    *  @return boolean
    *          true when a ball is still moving
    *          false when no balls are moving
   */
  public static boolean atLeastOneBallStillMoving(){
    for(int i =0;i<soccerBalls.length;i++)
    {
      if(soccerBalls[i].isStillMoving())
      {
        return true;
      }
    }
    return false;
  }

  /**
   *  Method to check if their is a collision
    *  @return boolean
    *          true when there is a collision between balls
    *          false when ther is no collison
   */
  public static boolean collisonCheck(){
    for (int i=0; i<soccerBalls.length;i++ ) {
      for (int j =0;j<soccerBalls.length ;j++ ) {
        if(i != j)
        {
          double xDif = Math.abs(soccerBalls[i].getCurrentX_Position()-soccerBalls[j].getCurrentX_Position());
          double yDif = Math.abs(soccerBalls[i].getCurrentY_Position()-soccerBalls[j].getCurrentY_Position());
          double distanceBetweenBalls = Math.hypot(xDif,yDif);
          if(distanceBetweenBalls < 9)
          {
            ballsCollided = true;
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   *  Method to check to see if any balls are out of bounds
    *  @return boolean
    *          true if any ball is out of bounds
    *          false if no balls are out of bounds
   */
  public static boolean isNotInBounds(){
    for (int i =0; i<soccerBalls.length;i++ ) {
      soccerBalls[i].setOutOfBounds(-75,75,-175,175);
      if(!soccerBalls[i].isInBounds())
      {
        System.out.println("\nA ball is no longer in bounds. No collisions detected. Simulation Ending");
        return true;
      }

    }
    return false;
  }

  /**
   *  Method to check if any ball collided with the poll
    *  @return boolean
    *          true if a ball collided with the poll
    *          false wif no balls have collided with the poll
   */
  public static boolean collidedWithPoll(){
    for (int i=0; i<soccerBalls.length;i++ ) {
      double xDif = Math.abs(soccerBalls[i].getCurrentX_Position()-poleXLocation);
      double yDif = Math.abs(soccerBalls[i].getCurrentY_Position()-poleYLocation);
      double distanceBetweenBallAndPoll = Math.hypot(xDif,yDif);
      if(distanceBetweenBallAndPoll < 4.5)
      {
        System.out.println("At t = "+timeElapsed+" secounds, a ball collided with the poll. Simulation Ending");
        return false;
      }
    }
    return false;
  }
}
