public class CompareTo {


public static void main(String[] args)
{
   System.out.println( "\n   Hello world, from the compareTo program!!\n" ) ;
   if(args.length!=2)
   {
      System.out.println( "   Sorry you must enter two arguments\n" +
                          "   Please try again..........." );
      System.exit( 1 );
   }
   BrobInt d1 = new BrobInt(args[0]);
   BrobInt d2 = new BrobInt(args[1]);
   int d3 = d1.compareTo(d2);
   System.out.println(d3);
}
}
