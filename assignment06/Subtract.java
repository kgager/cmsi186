
public class Subtract {

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public static void main(String[] args)
   {
      System.out.println( "\n   Hello world, from the Subtract program!!\n" ) ;
      if(args.length!=2)
      {
         System.out.println( "   Sorry you must enter two arguments\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }
      BrobInt a = new BrobInt(args[0]);
      BrobInt b = new BrobInt(args[1]);
      BrobInt c = a.subtract(b);
      System.out.println(c.toString());
   }
 }
