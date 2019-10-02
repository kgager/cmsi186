import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class  HighRoll{
  private static BufferedReader input = new BufferedReader( new InputStreamReader(System.in));
  private static int highScore =0;


  public static void main(String[] args){
      int count = 0;
      int sides = 0;
      String inputLine = "";
      if(2< args.length || 2 > args.length)
      {
        throw new IllegalArgumentException("Please enter 2 inputs for number of die and number of sides");
      }
      try{
        count = Integer.parseInt(args[0]);
        sides = Integer.parseInt(args[1]);
      } catch(NumberFormatException nfe){
        System.out.println("You must eneter 2 numbers as arguments for number of die and number of sides");
      }
      DiceSet ds = new DiceSet(count,sides);
      System.out.println("\n Welcome to HighRoll! \n");
      System.out.println("This is a dice game, the objective is to play a game in which you roll dice");
      while(true)
      {
        System.out.println("\nPlease select one of the following: \n"+
                            "A - ROLL ALL THE DICE \n"+
                            "B - ROLL A SINGLE DIE \n"+
                            "C - CALCULATE THE SCORE FOR THIS SET \n"+
                            "D - SAVE THIS SCORE AS HIGH SCORE \n"+
                            "E - DISPLAY THE HIGH SCORE \n"+
                            "Q - QUIT THE PROGRAM \n");
        try{
          inputLine = input.readLine();
          if('Q' == inputLine.toUpperCase().charAt(0))
          {
            System.exit(1);
          }
          if('A' == inputLine.toUpperCase().charAt(0))
          {
            ds.roll();
            if(sides == 1)
            {
              System.out.println("\nYour die roll is "+ ds +"\n");
            }
            else{
                System.out.println("\nYour dice are "+ ds +"\n");
            }
          }
          if('B' == inputLine.toUpperCase().charAt(0))
          {
            System.out.println("\nWhich die? \n");
            try{
              inputLine = input.readLine();
              if('1' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(0);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
              if('2' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(1);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
              if('3' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(2);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
              if('4' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(3);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
              if('5' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(4);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
              if('6' == inputLine.toUpperCase().charAt(0))
              {
                ds.rollIndividual(5);
                System.out.println("\nYour new dice roll is "+ ds +"\n");
              }
            }
            catch (IOException ioe) {
              System.out.println("Caught IO exception, bad input from user.");
            }
          }
          if('C' == inputLine.toUpperCase().charAt(0))
          {
            System.out.println("\nYour score fot this test is "+ds.sum()+"\n");
          }
          if('D' == inputLine.toUpperCase().charAt(0))
          {
            if(ds.sum()> highScore)
            {
                highScore = ds.sum();
                System.out.println("\nYour new high score is "+highScore+"\n");
            }
            else{
                System.out.println("\nSorry but "+ds.sum()+" is not a high score \n");
            }

          }
          if('E' == inputLine.toUpperCase().charAt(0))
          {
            System.out.println("\n Your high score is "+ highScore+ "\n");
          }

        }
        catch (IOException ioe) {
          System.out.println("Caught IO exception, bad input from user.");
        }

      }
  }
}
