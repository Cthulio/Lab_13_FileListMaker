import java.util.Scanner;

public class SafeInput {
    //#tested_methods
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);
        return retString;
    }
    public static int getInt(Scanner pipe, String prompt) {
        int retInt = 0;
        int temporaryInput = 0;
        String trash = "";
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space

            //reusing the basic structure of the code from previous models in this assignment, downscaling for fewer inputs.
            do {
                do {
                    if (pipe.hasNextInt()) {
                        temporaryInput = pipe.nextInt();
                        pipe.nextLine();
                        trash = "";//empty the trash.
                        //checking for negatives
                        if (temporaryInput <= 0) {
                            System.out.println("Please use a positive number!");
                        } else {
                            retInt = temporaryInput;
                        }
                    } else {
                        trash = pipe.nextLine();
                        if(!trash.isEmpty()) {//avoiding a weird but mostly harmless bug if the user puts a blank line.
                            System.out.println(trash + " is not an integer. Please insert an integer!");
                        }
                    }
                }
                while (!trash.isEmpty());
            }
            while (temporaryInput <= 0);
        } while (retInt == 0);

        return retInt;
    }
    public static double getDouble(Scanner pipe, String prompt)
    {
        double retDouble = 0;
        double temporaryInput = 0;
        String trash = "";
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space

            //reusing the basic structure of the code from previous models in this assignment, downscaling for fewer inputs.
            do {
                do {
                    if (pipe.hasNextDouble()) {
                        temporaryInput = pipe.nextDouble();
                        pipe.nextLine();
                        trash = "";//empty the trash.
                        //checking for negatives
                        if (temporaryInput <= 0) {
                            System.out.println("Please use a positive number!");
                        } else {
                            retDouble = temporaryInput;
                        }
                    } else {
                        trash = pipe.nextLine();
                        if(!trash.isEmpty()) {//avoiding a weird but mostly harmless bug if the user puts a blank line.
                            System.out.println(trash + " is not a double. Please insert a double!");
                        }
                    }
                }
                while (!trash.isEmpty());
            }
            while (temporaryInput <= 0);
        } while (retDouble == 0);
        return retDouble;
    }
    public static int getRangedInt(Scanner pipe, String prompt, int lo, int hi)
    {
        int retRangedInt = -1;
        int temporaryInput = lo-1;//avoid a bug with input: 'Enter + non-number value'
        String trash = "";
        System.out.print("\n" + prompt + ": "); // show prompt add space
        //reusing code from previous module, modified to fit the needs of this assignment.
        //reusing the basic structure of the code from previous models in this assignment, downscaling for fewer inputs.
        do {
            do {
                if (pipe.hasNextInt()) {
                    temporaryInput = pipe.nextInt();
                    pipe.nextLine();
                    trash = "";//empty the trash.
                    //checking for negatives
                    if (temporaryInput < lo || temporaryInput > hi) {
                        System.out.println("Please use a number between "+lo+"-"+hi+"!");
                    } else {
                        retRangedInt = temporaryInput;
                    }

                } else {
                    trash = pipe.nextLine();
                    System.out.println(trash + " is not an integer. Please insert an integer!");
                }
            }
            while (!trash.isEmpty());
        }
        while (temporaryInput < lo || temporaryInput > hi);

        return retRangedInt;
    }
    public static double getRangedDouble(Scanner pipe, String prompt, double lo, double hi)
    {
        double retRangedDouble = -1;
        double temporaryInput = lo-1;//avoid a bug with input: 'Enter + non-number value'
        String trash = "";
        System.out.print("\n" + prompt + ": "); // show prompt add space
        //reusing code from previous module, modified to fit the needs of this assignment.
        //reusing the basic structure of the code from previous models in this assignment, downscaling for fewer inputs.
        do {
            do {
                if (pipe.hasNextDouble()) {
                    temporaryInput = pipe.nextDouble();
                    pipe.nextLine();
                    trash = "";//empty the trash.
                    //checking for negatives
                    if (temporaryInput < lo || temporaryInput > hi) {
                        System.out.println("Please use a number between "+lo+"-"+hi+"!");
                    } else {
                        retRangedDouble = temporaryInput;
                    }

                } else {
                    trash = pipe.nextLine();
                    System.out.println(trash + " is not a double. Please insert a double!");
                }
            }
            while (!trash.isEmpty());
        }
        while (temporaryInput < lo || temporaryInput > hi);

        return retRangedDouble;
    }
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean retIsYN = true;
        String YorN;
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            YorN = pipe.nextLine();
            if (YorN.equalsIgnoreCase("Y")) {
                retIsYN = true;
            } else if (YorN.equalsIgnoreCase("N")) {
                retIsYN = false;
            }
            else
            {
                System.out.println(YorN + " is not valid input, Y/N only!");
                YorN = "";
            }
        }
        while(YorN.isEmpty());
        return retIsYN;
    }
    public static String getRegExString(Scanner pipe, String prompt, String regExPattern)
    {
        String retRegExString = "";
        String temporaryInput = "";
        do
        {

            System.out.print("\n" +prompt + ": "); // show prompt add space
            temporaryInput = pipe.nextLine();
            if(temporaryInput.matches(regExPattern))
            {
                retRegExString = temporaryInput;
            }
            else
            {
                //modified to make it read better in general errors.
                System.out.println(temporaryInput + " invalid option, please try again!");
            }

        }
        while(retRegExString.isEmpty());
        return retRegExString;
    }
    //#endtested_methods
}
