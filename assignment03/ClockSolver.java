/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  ClockSolver.java
 *  Purpose       :  The main program for the ClockSolver class
 *  @see
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

public class ClockSolver {
  /**
   *  Class field definintions go here
   */
   private static final double MAX_TIME_SLICE_IN_SECONDS  = 1800.00;
   private final double DEFAULT_TIME_SLICE_SECONDS = 60.0;
   private static final double EPSILON_VALUE              = 0.1;      // small value for double-precision comparisons
   private static double angle = 0;
   private static double timeSlice = 0;
   private static boolean overload= false;
  /**
   *  Constructor
   *  This just calls the superclass constructor, which is "Object"
   */
   public ClockSolver() {
      super();
   }

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public void handleInitialArguments( String args[] ) {
     // args[0] specifies the angle for which you are looking
     //  your simulation will find all the angles in the 12-hour day at which those angles occur
     // args[1] if present will specify a time slice value; if not present, defaults to 60 seconds
     // you may want to consider using args[2] for an "angle window"

      System.out.println( "\n   Hello world, from the ClockSolver program!!\n" ) ;
      System.out.println("This program determines all times during a twelve-hour day at which the two hands of a clock form a given angle!");
      System.out.println("The program check the angles every 60.0 seconds unless the user inputs a different timeslice\n");
      if( 0 == args.length ) {
         System.out.println( "   Sorry you must enter at least one argument\n" +
                             "   Usage: java ClockSolver <angle> [timeSlice]\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }
      Clock clock = new Clock();
      angle = clock.validateAngleArg(args[0]);
      if( args.length > 1 )
      {
          timeSlice = clock.validateTimeSliceArg(args[1]);
      }
      else{
        timeSlice = DEFAULT_TIME_SLICE_SECONDS;
      }
   }

  // Method to convert total secounds into the time of day. Inputs a double and outputs a String.
   private static String secToTime(double sec)
   {
     int hours = (int)(sec/(60*60));
     int mins = (int)((sec-hours*60*60)/60);
     int seconds = (int)(sec-hours*60*60-mins*60);
     String result = (hours + ":"+ mins + ":"+ seconds);
     return result;
   }
  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  @param  args  String array of the arguments from the command line
   *                args[0] is the angle for which we are looking
   *                args[1] is the time slice; this is optional and defaults to 60 seconds
   */
   public static void main( String args[] ) {
      ClockSolver cse = new ClockSolver();
      Clock clock    = new Clock();
      double[] timeValues = new double[1000];
      cse.handleInitialArguments( args );
      int numOfSuccesses = 0;
      while( clock.getTotalSeconds() < 43200 )
      {
          clock.tick(timeSlice);
          if((Math.abs(clock.getHandAngle() - angle) <EPSILON_VALUE))
          {
            // System.out.println("success");
            timeValues[numOfSuccesses] = clock.getTotalSeconds();
            numOfSuccesses ++;
          }
          if(numOfSuccesses == 1000)
          {
          System.out.println("This angle occurs a lot, so the only the first 1000 times were recorded.");
          overload = true;
          break;
         }
          // System.out.println("*");
      }
      if(timeValues[0] == 0){
          System.out.println("The angle of "+angle+ " degrees never occurs with given time slice of "+timeSlice);
      }
      else{
        System.out.print("The angle of "+angle+ " degrees occurs at ");
        for(int i =0; i < numOfSuccesses; i++)
        {
          if(i == 0){
            System.out.print( secToTime(timeValues[i]));
          }
          else{
            System.out.print(" and "+secToTime(timeValues[i]));
          }
        }
        System.out.println(" with given time slice of "+timeSlice);
      }
      if(overload)
      {
        System.out.println("This angle occurs a lot, so the only the first 1000 times were recorded.");
      }
      System.exit( 0 );
   }
}
