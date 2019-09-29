
# CMSI 186: Homework Assignment 02
## Problems About Dice
## Due: Monday 09.30.19, BEFORE class begins

### Summary


### Requirements:

Make  **public class Die**, which contains at the very least the following methods.

     1. public Die( int nSides )
       // Constructor for a single die with sides numbered 1, 2, …, up to nSides
    2. public int roll()
       // Returns a value as a result of randomly rolling this die
    3. public int getValue()
       // Returns the curent value of this die which resulted from the last roll
    4. public int setSides( int nSides )
       // change the configuration of this die and return the new number of sides
    5. public String toString()
       // Instance method that returns a string-y representation of THIS die,
       // e.g., [11]
    6. public static String toString()
       // Classwide version of the preceding instance method
    7. public static void main( String[] args );
       // The built-in test program for this class
       // TRY TO TEST AT LEAST 10 DIFFERENT CONFIGURATIONS

Make  **public class DiceSet**, which contains at the very least the following methods.

    1. public DiceSet( int k, int n )
       // Constructor for a set of k dice each with n-sides (k ≥ 1 and n ≥ four)
    2. public int sum()
       // Returns the present sum of this set of dice
    3. public void roll()
       // Randomly rolls all of the dice in this set; returns void since it just sets the values
       // Use either of the "toString()" methods to get the values in the set
    4. public void rollIndividual( int i )
       // Randomly rolls only the ith die in this set [indexed from zero]
    5. public int getIndividual( int i )
       // Gets the value of the ith die in this set
    6. public String toString()
       // Returns a string-y representation of this set of dice, e.g., [3][9][12][4]
    7. public static String toString( DiceSet ds )
       // Classwide version of the preceding instance method
    8. public boolean isIdentical( DiceSet ds )
       // Returns true iff this set is identical to the set ds passed as an argument
       // with the ordering of the dice in each set is not important
    9. public static void main( String[] args )
       // The built-in test program for this class

Make  **public class HighRoll.java**, which uses your dice set to play the game. You will need to have a main that constructs the dice set, has rolls, and displays scores from each roll.

The rules are as follows:

1. Implement a Textual User Interface (TUI) on the command line. This will display a list of options to the user, and will prompt for input. Based on that input your program will do what the user selected, then will display the results, a blank line or two, and then re-display the options.

- Option 1 in the list must be: ROLL ALL THE DICE

- Option 2 in the list must be: ROLL A SINGLE DIE

- Option 3 in the list must be: CALCULATE THE SCORE FOR THIS SET

- Option 4 in the list must be: SAVE THIS SCORE AS HIGH SCORE

- Option 5 in the list must be: DISPLAY THE HIGH SCORE

- Option 6 in the list must be: ENTER 'Q' TO QUIT THE PROGRAM

For option 2, you may present a second prompt to get the number of the die to roll, or you may handle it as two numbers on the entry. An example of the first method would be to enter a '2' at the prompt, then display a new prompt such as "which die?" and read the user input. An example of the second method would be to read "2 5" to re-roll only die number 5; this method means you must parse the input to get the die index.

To run the program, you can do one of two things: EITHER start the program using...
- java HighRoll <number of dice> <number of sides> …OR
- java HighRoll and prompt the user for the parameters.



### Notes:

1. The method sum() in the DiceSet class should return an integer, not a long.
2. There is a minimum number of sides that a die may have. When you think about it, a two-sided die is just a coin: heads or tails are the only values possible, corresponding to one or two, zero or one, on or off, light or dark, etc. Since this isn't a "Coin" class, don't use that.
3. Likewise, there is no such thing as a "three-sided" die. Such an item is not possible in this 3D world we inhabit. A case could be made for a hollow prism to be a three-sided die, but we would then have to define the inside faces of each side as "non-existent" or "not counted" or some such lame excuse to prevent confusion with a six-sided die.
4. A four-sided die would be a tetrahedron [also known as a pyramid]. You can use that, but since it will never end up on its point, it is a bit hard to define what it's value would be when rolled, without coming up with a convention to unequivocably pick a side to which you can refer as its "value". If you can do that, or you can make a case in which it doesn't matter, go for it.
5. A five-sided die is possible, if you take a tetrahedron and chop off the top to make a frustum. This would look like a pyramid with the top flattened out, or you could have a prism with the top and bottom [or ends] being solid triangular shapes. This is probably the smallest usable number of sides in real life.
6. All that being said, this IS a sort of "thought problem" so if you can figure out a way to use even a one-sided die [pretty boring rolling that one], feel free!
7. Note that you should only allow the user to quit the game by using the QUIT option from the menu. That means you will implement a "good endless loop" as discussed in class.


### Submission Guidelines:
Make a sub-directory in your repository as mentioned above, called assignment02 and commit your source code into it. Add a commit comment. Commit as many times as you want so commit early and commit often.
