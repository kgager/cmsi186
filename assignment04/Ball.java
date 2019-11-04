/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Ball.java
 *  Purpose       :  Provides a class defining methods for the SoccerSim class
 *  @author       :  Kevin Gager
 *  Date written  :  2019-10-31
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *                   for Homework 4.
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-10-31  Kevin Gager  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class  Ball{
  private double xPos = 0.0;
  private double yPos = 0.0;
  private double xVelocity = 0.0;
  private double yVelocity = 0.0;
  private static double timeSlice = 0.0;
  private boolean isInBounds = true;
  private static double radius = 4.45;

    /**
    *  Constructor
    *  initializes the position and velocity of the ball
    *  @param double the initial x position of the ball,
    *         double the initial y position of the ball,
    *         double the inital x velocity of the ball,
    *         double the inital y velocity of the ball
    */
    public Ball(double intXpos, double intYpos, double intxVelocity, double intYVelocity){
      this.xPos = intXpos;
      this.yPos = intYpos;
      this.xVelocity = intxVelocity;
      this.yVelocity = intYVelocity;
    }

     /**
    *  Method to calculate the new position after moving a time time given by the time increment
    *  Also decreases the X and Y velocities because of friction
    *  @param double The time increment for the method
    */
    public void move(double newTimeSlice) {
      timeSlice = newTimeSlice;
      this.xPos += timeSlice*xVelocity;
      this.yPos += timeSlice*yVelocity;
      this.xVelocity = xVelocity *timeSlice* .99;
      this.yVelocity = yVelocity *timeSlice* .99;
    }

    /**
   *  Method to calculate and return the current speed of the ball
   *  @return double-precision value of the current speed of the ball
   */
    public double getCurrentSpeed(){
      double speed = Math.sqrt(xVelocity*xVelocity+yVelocity*yVelocity);
      return roundAvoid(speed,3);
    }

    /**
     *  Method to fetch the current X-velocity
      *  @return double-precision value of the current X-velocity
     */
    public double getCurrentXVelocity(){
      return roundAvoid(xVelocity,3);
    }

    /**
     *  Method to fetch the current Y-velocity
      *  @return double-precision value of the current Y-velocity
     */
    public double getCurrentYVelocity(){
      return roundAvoid(yVelocity,3);
    }

    /**
     *  Method to fetch the current X-Position
      *  @return double-precision value of the current X-Position
     */
    public double getCurrentX_Position(){
      return roundAvoid(xPos,3);
    }

    /**
     *  Method to fetch the current Y-Position
      *  @return double-precision value of the current Y-Position
     */
    public double getCurrentY_Position(){
      return roundAvoid(yPos,3);
    }

    /**
     *  Method to check if the ball is still moving
      *  @return boolean value
      *          true when the ball is still moving
      *          false when the ball is no longer moving
     */
    public boolean isStillMoving(){
      if(xVelocity == 0 && yVelocity == 0){
        return false;
      }
      return true;
    }

    /**
     *  Method to check if the ball is in bounds
      *  @return boolean
      *          true when the ball is in bounds
      *          false when the ball is no longer in bounds
     */
    public boolean isInBounds(){
      if(isInBounds){
        return true;
      }
      return false;
    }

    /**
     *  Method to determine if the ball is in bounds based off of inputed field boundaries
     */
    public void setOutOfBounds(double minXpos, double maxXpos, double minYpos, double maxYpos){
      if(xPos + radius > maxYpos ||
         xPos - radius < minXpos ||
         yPos + radius > maxYpos ||
         yPos - radius < minYpos){
           xVelocity =0;
           yVelocity =0;
           isInBounds = false;
      }

    }

/**
 *  Method to return a String representation of this ball
 *  @return String value of the current ball
 */
    public String toString() {
      String stringOutput = "A Ball at ";
      stringOutput +="("+ getCurrentX_Position();
      stringOutput +="," +getCurrentY_Position();
      stringOutput +=") with a speed of " +getCurrentSpeed();
      return stringOutput;
   }

  /**
   *  Method to round double values
   *  copy and pasted from https://www.baeldung.com/java-round-decimal-number
   */
   public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }

  /**
   *  Method to test to see if program works
   */
  public static void main(String[] args) {

  }
}
