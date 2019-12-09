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


    // returns a BrobInt whose value is the sum of this plus the argument
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

   // returns a BrobInt whose value is the difference of this minus the argument
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

   // returns a BrobInt whose b is the product of this times the argument
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

   // returns a BrobInt whose value is the quotient of this divided by the argument
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
        System.out.println("put in i");
     }
     System.out.println("answ= "+answ);
     BrobInt res = new BrobInt(answ);
     return removeLeadingZeros(res);
   }

   // returns a BrobInt whose value is the remainder of this divided by the argument
   public BrobInt remainder( BrobInt b )
   {
     BrobInt c = this.divide(b);
     return this.subtract(c);
   }
   // returns the decimal string represention of this BrobInt
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
   // returns -1/0/1 as this BrobInt is numerically less than/equal to/greater than the value passed as the argument
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
   // returns true iff x is a BrobInt whose value is numerically equal to this BrobInt
   public boolean equals( BrobInt b  ){
     if(this.compareTo(b) != 0)
     {
       return false;
     }
     return true;
   }
   // a BrobInt "static factory" for constructing BrobInt out of longs
   public static BrobInt valueOf( long b ){
     return ZERO;
   }

   public int length(){
     return a.length;
   }

   public char sign(){
     if(sign == 1)
     {
       return '-';
     }
     return '+';
   }

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

   public static void main( String[] args ) {
    System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
    System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

    BrobInt b1 = null;;
    try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "147258369789456123" ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
    try { System.out.println( "   expecting: 147258369789456123\n     and got: " + b1.toString() ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }

    System.out.println( "\n    Adding 123 by 456: " );
    try { System.out.println( "      expecting: 579\n        and got: " + new BrobInt("123").multiply( new BrobInt("456") ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

    System.out.println( "\n    Multiplying 33 by 3: " );
    try { System.out.println( "      expecting: 99\n        and got: " + new BrobInt("33").multiply( BrobInt.THREE ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }




    System.out.println( "\n    Multiplying 82832833 by 3: " );
    try { System.out.println( "      expecting: 248498499\n        and got: " + new BrobInt("82832833").multiply( BrobInt.THREE ) ); }
    catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

    // System.out.println( "\n    Multiplying 3 by 82832833 and adding 1: " );
    // try { System.out.println( "      expecting: 248498500\n        and got: " + BrobInt.THREE.multiply( new BrobInt( "82832833" ) ).add( BrobInt.ONE ) ); }
    // catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }
    // System.exit( 0 );

 }


}
