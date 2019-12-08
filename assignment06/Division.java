public class Division {

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public static void main(String[] args)
   {
      System.out.println( "\n   Hello world, from the Division program!!\n" ) ;
      if(args.length!=2)
      {
         System.out.println( "   Sorry you must enter two arguments\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }
      BrobInt d1 = new BrobInt(args[0]);
      BrobInt d2 = new BrobInt(args[1]);
      BrobInt d3 = d1.divide(d2);
      System.out.println(d3.toString());
   }


}
