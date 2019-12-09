import java.math.BigInteger;
import java.io.BufferedReader;
import java.util.Arrays;

public class BrobInt
{
  private byte[] a;
  private byte[] d2;
  public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
  public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
  public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
  public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
  public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
  public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
  public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
  public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
  public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
  public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
  public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
   public static final BrobInt MAX_INT  = new BrobInt( Integer.valueOf( Integer.MAX_VALUE ).toString() );
   public static final BrobInt MIN_INT  = new BrobInt( Integer.valueOf( Integer.MIN_VALUE ).toString() );
   public static final BrobInt MAX_LONG = new BrobInt( Long.valueOf( Long.MAX_VALUE ).toString() );
   public static final BrobInt MIN_LONG = new BrobInt( Long.valueOf( Long.MIN_VALUE ).toString() );

    /// These are the internal fields
     public  String internalValue = "";        // internal String representation of this BrobInt
     public  byte   sign          = 0;         // "0" is positive, "1" is negative
     public boolean wentOver = false;
     public boolean ableToGetOut = false;
    /// You can use this or not, as you see fit.  The explanation was provided in class
     private String reversed      = "";        // the backwards version of the internal String representation
    private static final boolean DEBUG_ON = false;
     private static final boolean INFO_ON  = false;

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to validate that all the characters in the value are valid decimal digits
     *  @throws  IllegalArgumentException if something is hinky
     *  note that there is no return false, because of throwing the exception
     *  note also that this must check for the '+' and '-' sign digits
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
     public void validateDigits(String value) throws IllegalArgumentException {
       for(int i =0; i< value.length();i++)
       {
         if(i== 0)
         {
           if(value.charAt(i) != '+' &&value.charAt(i) != '-' && value.charAt(i) != '0' && value.charAt(i) != '1' && value.charAt(i) != '2' && value.charAt(i) != '3' && value.charAt(i) != '4' && value.charAt(i) != '5' && value.charAt(i) != '5' && value.charAt(i) != '6' && value.charAt(i) != '7' && value.charAt(i) != '8' && value.charAt(i) != '9')
           {
             throw new IllegalArgumentException("please make inputs valid numbers");
           }
         }
         else
         {
           if(value.charAt(i) != '0' && value.charAt(i) != '1' && value.charAt(i) != '2' && value.charAt(i) != '3' && value.charAt(i) != '4' && value.charAt(i) != '5' && value.charAt(i) != '5' && value.charAt(i) != '6' && value.charAt(i) != '7' && value.charAt(i) != '8' && value.charAt(i) != '9')
           {
             System.out.println("invalid char = "+value.charAt(i));
             throw new IllegalArgumentException("please make inputs valid numbers");
           }
         }
       }

     }

   /**
    *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
    *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
    *   for later use
    *  @param  value  String value to make into a BrobInt
    */
    public BrobInt( String value ){
      validateDigits(value);
      internalValue = value;
      StringBuffer sbf;
      if(value.charAt(0)=='-')
      {
        sign = 1;
        sbf = new StringBuffer(value.substring(1));
      }
      else if(value.charAt(0)=='+')
      {
        sign = 0;
        sbf = new StringBuffer(value.substring(1));
      }
      else
      {
        sign =0;
        sbf = new StringBuffer(value);
      }
      sbf.reverse();
      reversed = new String(sbf);
      if(internalValue.length() == 0)
      {
        throw new IllegalArgumentException("Please enter an argument");
      }
      a = new byte[reversed.length()];
      d2 = new byte[reversed.length()];
      for (int i=0;i<reversed.length(); i++ )
      {
        try
        {
            a[i] = (byte) Character.getNumericValue(reversed.charAt(i));
            d2[i] = (byte) Character.getNumericValue(reversed.charAt(i));
        }
        catch(NumberFormatException nfe)
        {
          throw new IllegalArgumentException("Make the input a valid number");
        }
      }
    }


    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to add the value of a BrobIntk passed as argument to this BrobInt
  *  @param  bint         BrobInt to add to this
  *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt add( BrobInt b )
   {
     byte[] c = new byte[Math.max(a.length,b.length())+2];
     byte cary = 0;
     if(this.sign() == '+' && b.sign() == '-')
     {
       BrobInt neg = new BrobInt("-1");
       return this.subtract(b.multiply(neg));
     }
     if(this.sign() == '-' && b.sign() == '+')
     {
       return b.add(this);
     }
     for (int i=0;i<a.length ;i++ )
     {
       if(i<b.length())
       {
         c[i]= a[i];
         c[i] += b.valueAt(i) + cary;
         if( (a[i] + b.valueAt(i) +cary) <10)
         {
           cary = 0;
         }
         else{
            c[i] -= 10;
            cary = 1;
         }
       }
       else
       {
         c[i] = a[i];
         c[i] += cary;
         cary = 0;
       }
       if(i+1 == a.length && b.length()>a.length)
       {
         for (int j =i+1; j<b.length();j++)
         {
           c[j] = b.valueAt(j);
           c[j] += cary;
           cary = 0;
         }
       }
       else if(i+1 == a.length&& cary ==1)
       {
          c[i+1] = 1;
          cary = 0;
       }
     }
     String answ = "";
     if(this.sign()=='-' &&b.sign()== '-')
     {
       answ += '-';
     }
     for (int i=0;i<c.length;i++ )
     {
       answ +=c[c.length-1-i];
     }
     BrobInt res = new BrobInt(answ);
     return removeLeadingZeros(res);
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt
   *  @param  bint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtract( BrobInt b )
   {
     byte[] c = new byte[Math.max(a.length,b.length()+3)];
     if(this.sign() == '+' && b.sign() == '-')
     {
       BrobInt neg = new BrobInt("-1");
       return this.add(b.multiply(neg));
     }
     if(this.sign() == '-' && b.sign() == '-')
     {
       BrobInt neg = new BrobInt("-1");
       return this.add(b.multiply(neg));
     }
     if(this.compareTo(b)== -1)
     {
       BrobInt neg = new BrobInt("-1");
       return neg.multiply(b.subtract(this));
     }
     for(int i=0; i<a.length; i++)
     {
       if(i<b.length())
       {
         c[i] = a[i];
         if(c[i] < b.valueAt(i))
         {
           c[i]+=10;
           int j=i+1;
           if(a[j]==0)
           {
             while(a[j] ==0 && !ableToGetOut)
             {
               a[j] =9;
               j++;
               if(j==a.length)
               {
                  break;
               }
               if(a[j]!=0)
               {
                  a[j] -= 1;
                  ableToGetOut = true;
               }
              } //end of while loop
            }//end of if(a[j] =0)
            else
            {
              a[j] -= 1;
            }
          }
          c[i] -= b.valueAt(i);
        }
       else
       {
         c[i] = a[i];
       }
     }
     String answ = "";
     if(wentOver)
     {
       answ += "-";
     }
     for (int i=0;i<c.length;i++ )
     {
       answ +=c[c.length-1-i];
     }

     BrobInt res = new BrobInt(answ);
     return removeLeadingZeros(res);
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  bint         BrobInt to multiply this by
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt b ){
   int carry=0;
   int[] c;
   int temp;
   if(a.length >= b.length())
   {
     c = new int[a.length*2];
   }
   else
   {
     c = new int[b.length()*2];
   }

   for (int i = 0; i<a.length;i++ )
   {
     for (int j=0;j<b.length() ;j++ )
     {
       temp = c[i+j];
       c[i+j] = a[i]*b.valueAt(j)+carry+c[i+j];
       while(c[i+j]>9)
       {
          c[i+j] = c[i+j]-10;
          c[i+j+1] +=1;
       }
     }
     carry =0;
    }
    String answ = "";
    if(this.sign()== '-' && b.sign()=='+' ||this.sign()== '+' && b.sign()=='-')
    {
      answ += '-';
    }
    for (int i=0;i<c.length;i++ )
     {
       answ += c[c.length-1-i];
     }
     BrobInt res = new BrobInt(answ);
     return removeLeadingZeros(res);
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
  *  @param  bint         BrobInt to divide this by
  *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt d1 )
   {
     if(d1.equals(ZERO))
     {
       throw new IllegalArgumentException("The divisor cannot have a value of 0");
     }
     if(d1.equals(this))
     {
       return ONE;
     }
     if(d1.length() > d2.length)
     {
       return ZERO;
     }
     int[] quotient = new int[d2.length];
     BrobInt divident = d1;
     BrobInt currentDivisor;
     String curDivisor = "";
     int count =0;
     for(int j= 0;j<divident.length();j++)
     {
       curDivisor += d2[quotient.length-1-j];
     }
     for(int i= 0; i< d2.length-divident.length()+1;i++)
     {
       currentDivisor = new BrobInt(curDivisor);
       int c =0;
       String cAsString = "";
       BrobInt multiplier= ZERO;
       while(currentDivisor.compareTo(multiplier.multiply(divident)) > -1)
       {
         c++;
         cAsString = "";
         cAsString += c;
         multiplier = new BrobInt(cAsString);
       }
       c--;
       cAsString = "";
       cAsString += c;
       multiplier = new BrobInt(cAsString);
       quotient[i]= c; count++;
       curDivisor ="";
       curDivisor += currentDivisor.subtract(multiplier.multiply(divident)).toString();
       if((d2.length-i-divident.length()-1)>-1)
       {
         curDivisor += d2[d2.length-i-divident.length()-1];
       }
     }
     String answ = "";
     for (int i=0;i<count;i++ )
     {
        answ += quotient[i];
     }
     BrobInt res = new BrobInt(answ);
     return removeLeadingZeros(res);
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  @param  bint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt b )
   {
     BrobInt c = this.divide(b);
     return this.subtract(c);
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to return a String representation of this BrobInt
  *  @return String  which is the String representation of this BrobInt
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString(){
     String answ ="";
     if(this.sign() == '-')
     {
       answ += '-';
     }
     for (int i= this.a.length-1;i>-1 ;i-- ) {
       answ += this.a[i];
     }
     return answ;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to compare a BrobInt passed as argument to this BrobInt
  *  @param  bint  BrobInt to compare to this
  *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
  *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
  *        It takes into account the length of the two numbers, and if that isn't enough it does a
  *        character by character comparison to determine
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt b ){
     b= removeLeadingZeros(b);
     if(removeLeadingZeros(this).length() < b.length())
     {
       return -1;
     }
     if(removeLeadingZeros(this).length() > b.length())
     {
        return 1;
     }
     for (int i=b.length()-1;i>-1 ;i-- ) {
       if(removeLeadingZeros(this).valueAt(i) < b.valueAt(i))
       {
         return -1;
       }
       if(removeLeadingZeros(this).valueAt(i) > b.valueAt(i))
       {
         return 1;
       }
     }
     return 0;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  bint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt b  ){
     if(this.compareTo(b) != 0)
     {
       return false;
     }
     return true;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to determine the length of the BrobInt
   *  @return int  the length of the BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int length(){
     return a.length;
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to determine the sign of the BrobInt
     *  @return char      determines if the BrobInt is positive or negative
     *               +    when the BrobInt is positive
     *               -    when the BrobInt is negative
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public char sign(){
     if(sign == 1)
     {
       return '-';
     }
     return '+';
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to get a number in a BrobInt from a certain location passed as argument
     *  @param  int     location of the desired number in the array
     *  @return byte     the desired number
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public byte valueAt(int location){
     return a[location];
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to remove leading zeros from a BrobInt passed as argument
     *  @param  bint     BrobInt to remove zeros from
     *  @return BrobInt that is the argument BrobInt with leading zeros removed
     *  Note that the sign is preserved if it exists
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt removeLeadingZeros( BrobInt bint ) {
      Character sign = null;
      String returnString = bint.toString();
      int index = 0;
      if( ('-' == returnString.charAt( index )) || ('+' == returnString.charAt( index )) ) {
         sign = returnString.charAt( index );
         index++;
      }
      if( returnString.charAt( index ) != '0' ) {
         return bint;
      }
      String ZeroChecker ="";
      for (int i =0;i<returnString.length() ;i++ )
      {
        if(i==0 && sign != null)
        {
          ZeroChecker += '-';
        }
        else
        {
          ZeroChecker += '0';
        }
      }
      if(ZeroChecker.equals(returnString))
      {
        return ZERO;
      }
      while( returnString.charAt( index ) == '0' ) {
         index++;
      }
      returnString = bint.toString().substring( index, bint.toString().length() );
      if( sign != null ) {
         returnString = sign.toString() + returnString;
      }
      return new BrobInt( returnString );

   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      *  The main method contains tests for each method.
      *  @param  args  String array which contains command line arguments
      *  NOTE:  we don't really care about these, since we test the BrobInt class with the BrobIntTester
      *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
    System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );

    BrobInt b1 = null;;
    System.out.println("---------------------------Constructor Tests---------------------------");
    try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "123" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 123\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "805" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 805\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "888888" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 888888\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "000" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 000\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "147258369789456123" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 147258369789456123\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "-70" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: -70\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "-898" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: -898\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "-97898" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: -97898\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "-0" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: -0\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "\n   Making a new BrobInt: " ); b1 = new BrobInt( "439578324695237" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 439578324695237\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }

System.out.println("---------------------------Addition Tests---------------------------");
    System.out.println( "\n    Adding 1 by 1: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("1").add( new BrobInt("1") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 2 by 3: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("2").add( new BrobInt("3") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 4 by 7: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("4").add( new BrobInt("7") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 18 by 29: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("18").add( new BrobInt("29") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 123 by 456: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("123").add( new BrobInt("456") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 1 by -1: " );
    try { System.out.println( "      expecting: 0\n        and got: " + new BrobInt("1").add( new BrobInt("-1") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 792344 by 32499234: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("792344").add( new BrobInt("32499234") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 98 by 0: " );
    try { System.out.println( "      expecting: 98\n        and got: " + new BrobInt("98").add( new BrobInt("0") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 98 by -0: " );
    try { System.out.println( "      expecting: 98\n        and got: " + new BrobInt("98").add( new BrobInt("-0") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    System.out.println( "\n    Adding 23482398 by -1231293: " );
    try { System.out.println( "      expecting: 24713691\n        and got: " + new BrobInt("23482398").add( new BrobInt("-1231293") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

System.out.println("---------------------------Subtraction Tests---------------------------");
System.out.println( "\n    Subtracting 1 by 1: " );
try { System.out.println( "      expecting: 0\n        and got: " + new BrobInt("1").subtract( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 3 by 2: " );
try { System.out.println( "      expecting: 1\n        and got: " + new BrobInt("3").subtract( new BrobInt("2") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 2 by 3: " );
try { System.out.println( "      expecting: -1\n        and got: " + new BrobInt("2").subtract( new BrobInt("3") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 4 by -2: " );
try { System.out.println( "      expecting: 6\n        and got: " + new BrobInt("4").subtract( new BrobInt("-2") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting -7 by -8: " );
try { System.out.println( "      expecting: 1\n        and got: " + new BrobInt("-7").subtract( new BrobInt("-8") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 1 by -10: " );
try { System.out.println( "      expecting: 11\n        and got: " + new BrobInt("1").subtract( new BrobInt("-10") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 4697497 by 49254: " );
try { System.out.println( "      expecting: 4648243\n        and got: " + new BrobInt("4697497").subtract( new BrobInt("49254") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 924 by 0: " );
try { System.out.println( "      expecting: 924\n        and got: " + new BrobInt("924").subtract( new BrobInt("0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 0 by -0: " );
try { System.out.println( "      expecting: 0\n        and got: " + new BrobInt("0").subtract( new BrobInt("-0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Subtracting 647473421 by -32454213454: " );
try { System.out.println( "      expecting: 3.3e10\n        and got: " + new BrobInt("647473421").subtract( new BrobInt("-32454213454") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println("---------------------------Multiplication Tests---------------------------");

System.out.println( "\n    Multiplying 33 by 3: " );
try { System.out.println( "      expecting: 99\n        and got: " + new BrobInt("33").multiply( BrobInt.THREE ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 82832833 by 3: " );
try { System.out.println( "      expecting: 248498499\n        and got: " + new BrobInt("82832833").multiply( BrobInt.THREE ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 1 by 1: " );
try { System.out.println( "      expecting: 1\n        and got: " + new BrobInt("1").multiply( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 2 by 3: " );
try { System.out.println( "      expecting: 6\n        and got: " + new BrobInt("2").multiply( new BrobInt("3") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 5 by 7: " );
try { System.out.println( "      expecting: 35\n        and got: " + new BrobInt("5").multiply( new BrobInt("7") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 64 by 32: " );
try { System.out.println( "      expecting: 2048\n        and got: " + new BrobInt("64").multiply( new BrobInt("32") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 567 by 987: " );
try { System.out.println( "      expecting: 559629\n        and got: " + new BrobInt("567").multiply( new BrobInt("987") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 3 by -1: " );
try { System.out.println( "      expecting: -3\n        and got: " + new BrobInt("1").multiply( new BrobInt("-1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 425714 by 8164: " );
try { System.out.println( "      expecting: 3475529096\n        and got: " + new BrobInt("425714").multiply( new BrobInt("8164") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying -87 by -2: " );
try { System.out.println( "      expecting: 174\n        and got: " + new BrobInt("-87").multiply( new BrobInt("-2") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Multiplying 98 by 0: " );
try { System.out.println( "      expecting: 98\n        and got: " + new BrobInt("98").multiply( new BrobInt("0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

System.out.println("---------------------------Division Tests---------------------------");
System.out.println( "\n    Dividing 1 by 1: " );
try { System.out.println( "      expecting: 1\n        and got: " + new BrobInt("1").divide( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 4 by 1: " );
try { System.out.println( "      expecting: 4\n        and got: " + new BrobInt("4").divide( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 6 by 3: " );
try { System.out.println( "      expecting: 2\n        and got: " + new BrobInt("6").divide( new BrobInt("3") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 16 by 4: " );
try { System.out.println( "      expecting: 4\n        and got: " + new BrobInt("16").divide( new BrobInt("4") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 4 by 7: " );
try { System.out.println( "      expecting: 0\n        and got: " + new BrobInt("4").divide( new BrobInt("7") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 9 by 5: " );
try { System.out.println( "      expecting: 1\n        and got: " + new BrobInt("9").divide( new BrobInt("5") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 13 by 2: " );
try { System.out.println( "      expecting: 6\n        and got: " + new BrobInt("13").divide( new BrobInt("2") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 350 by 70: " );
try { System.out.println( "      expecting: 5\n        and got: " + new BrobInt("350").divide( new BrobInt("70") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 862315 by 9834: " );
try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("862315").divide( new BrobInt("9834") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Dividing 19 by 0: " );
System.out.println("      expecting: Exception thrown\n        and got:");
try { System.out.println( "      expecting: Exception thrown\n        and got: " + new BrobInt("19").divide( new BrobInt("0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

System.out.println("---------------------------Remainder Tests---------------------------");
System.out.println( "\n    Finding the remainder of 20 by 18: " );
try { System.out.println( "      expecting: 4\n        and got: " + new BrobInt("20").divide( new BrobInt("18") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Finding the remainder 80 by 17: " );
try { System.out.println( "      expecting: 12\n        and got: " + new BrobInt("80").divide( new BrobInt("17") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }


System.out.println("---------------------------Equals Tests---------------------------");
System.out.println( "\n    Checking to see if 1 = 1: " );
try { System.out.println( "      expecting: true\n        and got: " + new BrobInt("1").equals( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if -1 = -1: " );
try { System.out.println( "      expecting: true\n        and got: " + new BrobInt("-1").equals( new BrobInt("-1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if -1 = 1: " );
try { System.out.println( "      expecting: false\n        and got: " + new BrobInt("-1").equals( new BrobInt("1") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if 0 = 0: " );
try { System.out.println( "      expecting: true\n        and got: " + new BrobInt("0").equals( new BrobInt("0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if 1 = 0: " );
try { System.out.println( "      expecting: false\n        and got: " + new BrobInt("1").equals( new BrobInt("0") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if 349818324695237 = 349818324695237: " );
try { System.out.println( "      expecting: true\n        and got: " + new BrobInt("349818324695237").equals( new BrobInt("349818324695237") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println( "\n    Checking to see if 78621381297 = 12391280123: " );
try { System.out.println( "      expecting: false\n        and got: " + new BrobInt("78621381297").equals( new BrobInt("12391280123") ) ); }
catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
System.out.println("------------------------------------------------------------------");
 }


}
