public class CountTheDays{

  private static long month1 = 0;
  private static long day1 = 0;
  private static long year1 = 0;
  private static long month2 = 0;
  private static long day2 = 0;
  private static long year2 = 0;

  public CountTheDays(String[] args){
    month1 = Long.parseLong(args[0]);
    day1 = Long.parseLong(args[1]);
    year1 = Long.parseLong(args[2]);
    month2 = Long.parseLong(args[3]);
    day2 = Long.parseLong(args[4]);
    year2 = Long.parseLong(args[5]);
  }

  public static void main (String[] args){
    if(args.length != 6){
      System.out.println("Hey I need 6 args!");
      System.exit(1);
    }

    CountTheDays ctd = new CountTheDays(args);
    long count = CalendarStuff.daysBetween(month1, day1, year1, month2, day2, year2);
    System.out.println(count);
  }
}
