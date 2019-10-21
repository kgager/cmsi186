/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Clock.java
 *  Purpose       :  Provides a class defining methods for the ClockSolver class
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
 *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class Clock {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double INVALID_ARGUMENT_VALUE = -1.0;
   private static final double MAXIMUM_DEGREE_VALUE = 360.0;
   private static final double HOUR_HAND_DEGREES_PER_SECOND = 0.00834;
   private static final double MINUTE_HAND_DEGREES_PER_SECOND = 0.1;

   private static double timeSlice=0;
   private double totalSecounds = 0;
  /**
   *  Constructor goes here
   */
   public Clock() {

   }

  /**
   *  Methods go here
   *
   *  Method to calculate the next tick from the time increment
   *  @return double-precision value of the current clock tick
   */


   public double tick(double newTimeSlice) {
      timeSlice = newTimeSlice;
      totalSecounds += timeSlice;
      return totalSecounds;
   }
  /**
   *  Method to validate the angle argument
   *  @param   argValue  String from the main programs args[0] input
   *  @return  double-precision value of the argument
   *  @throws  NumberFormatException
   */
   public static double validateAngleArg( String argValue ) throws NumberFormatException {
     double angle = 0;
     try {
       angle = Double.parseDouble(argValue);
     }
     catch(NumberFormatException nfe) {
        throw new IllegalArgumentException("Make the angle a valid number");
     }

     if(angle <= 0 || angle >=360)
     {
          throw new IllegalArgumentException("Make angle between 0 and 360 degrees");
     }
     return angle;
   }

  /**
   *  Method to validate the optional time slice argument
   *  @param  argValue  String from the main programs args[1] input
   *  @return double-precision value of the argument or -1.0 if invalid
   *  note: if the main program determines there IS no optional argument supplied,
   *         I have elected to have it substitute the string "60.0" and call this
   *         method anyhow.  That makes the main program code more uniform, but
   *         this is a DESIGN DECISION, not a requirement!
   *  note: remember that the time slice, if it is small will cause the simulation
   *         to take a VERY LONG TIME to complete!
   // */
   public static double validateTimeSliceArg( String argValue ) {

      double timeSlice= 0;
      try {
        timeSlice = Double.parseDouble(argValue);
      }
      catch(NumberFormatException nfe) {
         throw new IllegalArgumentException("Make timeSlice a valid number");
      }

      if(timeSlice <= 0 || timeSlice >=1800.00)
      {
          // System.out.println("timeSlice: "+timeSlice);
           throw new IllegalArgumentException("Make timeSlice between 0 and 1800.00 secounds");
      }
      return timeSlice;
   }

  /**
   *  Method to calculate and return the current position of the hour hand
   *  @return double-precision value of the hour hand location
   */
   public double getHourHandAngle() {
      return  (HOUR_HAND_DEGREES_PER_SECOND * totalSecounds)%360;
   }

  /**
   *  Method to calculate and return the current position of the minute hand
   *  @return double-precision value of the minute hand location
   */
   public double getMinuteHandAngle() {
      return (MINUTE_HAND_DEGREES_PER_SECOND * totalSecounds)%360;
   }

  /**
   *  Method to calculate and return the angle between the hands
   *  @return double-precision value of the angle between the two hands
   */
   public double getHandAngle() {
      return Math.abs(getHourHandAngle()-getMinuteHandAngle());
   }

  /**
   *  Method to fetch the total number of seconds
   *   we can use this to tell when 12 hours have elapsed
   *  @return double-precision value the total seconds private variable
   */
   public double getTotalSeconds() {
      return this.totalSecounds;
   }

  /**
   *  Method to return a String representation of this clock
   *  @return String value of the current clock
   */
   public String toString() {
      String stringOutput = "Clock- ";
      stringOutput +="\nTotal Secounds past: "+ getTotalSeconds();
      stringOutput +="\nMinute Hand Angle:  " +getMinuteHandAngle();
      stringOutput +="\nHour Hand Angle:  " +getHourHandAngle();
      stringOutput +="\nHand Angle:  " +getHandAngle();
      return stringOutput;
   }

  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  be sure to make LOTS of tests!!
   *  remember you are trying to BREAK your code, not just prove it works!
   */
   public static void main( String args[] ) {

      System.out.println( "\nCLOCK CLASS TESTER PROGRAM\n" +
                          "--------------------------\n" );
      System.out.println( "  Creating a new clock: " );
      Clock clock = new Clock();
      System.out.println( "    New clock created: " + clock.toString() );

      System.out.println( "    Testing validateAngleArg()....");
      System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
      try { System.out.println( (0.0 == clock.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }

      System.out.println( "    Testing validateTimeSliceArg()....");
      System.out.print( "      sending '  50 seconds', expecting double value   50.0" );
      try { System.out.println( (50.0 == clock.validateTimeSliceArg( "50.0" )) ? " - got 50.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }

      clock.tick(60);
      System.out.println("     Testing toString() method ");
      System.out.println("     Result of ticking clock 60 secounds should be displayed");

      System.out.println(clock);
      System.out.println("     Testing getTotalSeconds() method ");
      System.out.println("     Result should have said 60 secounds ");
      System.out.println("     Testing getMinuteHandAngle() method ");
      System.out.println("     Result should have said 6");
      System.out.println("     Testing getHourHandAngle() method ");
      System.out.println("     Result should have said .5004");
      System.out.println("     Testing getHandAngle() method ");
      System.out.println("     Result should have said 5.4996 ");




      System.out.println("\n \n Now ticking the clock 100 times \n \n");
      for (int i=0; i<99; i++) {
              clock.tick(60);
      }
      System.out.println(clock);
      System.out.println("     Testing getTotalSeconds() method ");
      System.out.println("     Result should have said 60000 secounds ");
      System.out.println("     Testing getMinuteHandAngle() method ");
      System.out.println("     Result should have said 240.0");
      System.out.println("     Testing getHourHandAngle() method ");
      System.out.println("     Result should have said 50.04");
      System.out.println("     Testing getHandAngle() method ");
      System.out.println("     Result should have said 189.96 ");


   }
}
