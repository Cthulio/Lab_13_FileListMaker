import javax.swing.*;// this was auto imported when I wrote the JFileChooser code.
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.CREATE;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileListMaker {
    public static ArrayList<String> myArrList = new ArrayList<>();
    public static ArrayList<String> MainMenu = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);
    public static boolean quitMenu = false;
    public static boolean needToSave = false;

    public static void main(String[] args) {
        String selection = "";
        initMenu();

        do
        {
            //before the user gets to do anything, print the menu.
            printMenu();
            do
            {
                selection = SafeInput.getRegExString(in, "What do you want to do?","[AaDdIiVvQqMmOoSsCc]");
                //the switch allows us to add/change items later if we want. See also: note in initMenu()
                switch(selection) {
                    case "A":
                    case "a":
                        addItem();
                        break;
                    case "D":
                    case "d":
                        deleteItem();
                        break;
                    case "I":
                    case "i":
                        insertItem();
                        break;
                    case "V"://changed
                    case "v":
                        viewList();
                        break;
                    case "Q":
                    case "q":
                        quitList();
                        break;
                    case "M"://from here down added
                    case "m":
                        moveItem();
                        break;
                    case "O":
                    case "o":
                        openList();
                        break;
                    case "S":
                    case "s":
                        saveList();
                        break;
                    case "C":
                    case "c":
                        clearList();
                        break;
                }
            }while(selection == "");//prompt user until there is a valid input.
        }
        while(!quitMenu);//if the user wants to leave stop looping.

    }

    public static void addItem()
    {
        //Add an item always puts it at the end of the list.
        myArrList.add(SafeInput.getNonZeroLenString(in, "What do you want to enter?"));
        needToSave = true;//added this to any modification to the list.
    }

    public static void deleteItem()
    {
        viewList();
        //Delete an item user has to specify which one using the item number from the display.
        myArrList.remove(SafeInput.getRangedInt(in,"Enter the line you would like to delete",1,myArrList.size())-1);
        needToSave = true;
        myArrList.trimToSize();//keep from adding a bunch of blank lines to the list/file.
    }

    public static void insertItem()
    {
        //this only runs if the user wants to insert a line into an empty list.
        if(myArrList.size() == 0)
        {
            System.out.println("There are no items in the list, adding one instead.");
            addItem();
        }
        //...or a "list" of one item.
        else if(myArrList.size() == 1)
        {
            // also adds a custom message per number of item(s) available.
            System.out.println("There is only one item, adding to it instead.");
            addItem();
        }
        //otherwise, they get to choose where to insert the item.
        else {
            viewList();//I wanted to display the list for the user before they made a potentially blind decision.
            //Insert an item user has to indicate where using a location number.
            myArrList.add(SafeInput.getRangedInt(in, "Enter the line number you would like to insert a line AFTER",
                    1, myArrList.size()), SafeInput.getNonZeroLenString(in, "What do you want to enter?"));
        }
        needToSave = true;
    }

    public static void viewList()
    {
        //Print the list just displays the list.
        //I chose to add numbers to the list for the user to easily select an item.
        for(String i : myArrList) {
            System.out.println(myArrList.indexOf(i)+1+": "+i);
        }
    }

    // Sets the bool to quit the program after making sure the user meant to.
    public static void quitList()
    {
        //Quit asks the user if they are sure and then terminates the program.
        if (SafeInput.getYNConfirm(in,"Are you sure you want to quit?")) {

            if (needToSave)
            {
                boolean doSave = SafeInput.getYNConfirm(in,"Would you like to save your work?");
                if(doSave)
                {
                    System.out.println("Saving...");
                    saveList();
                }
            }
            quitMenu = true;
            System.out.println("Goodbye!");
        }
    }

    //Called once, initializes menu.
    //I didn't want this list initialization up in the variables as it made a mess,
    // though this isn't really necessary it looks cleaner to me,
    // and it gives the possibility to customize the menu more easily.
    public static void initMenu()
    {
        MainMenu.add("A – Add an item to the list");
        MainMenu.add("D – Delete an item from the list");
        MainMenu.add("I – Insert an item into the list");
        MainMenu.add("V - View the list");//changed
        MainMenu.add("Q – Quit the program");
        //added
        MainMenu.add("M - Move an item");
        MainMenu.add("O - Open a list file from disk");
        MainMenu.add("S - Save the current list file to disk");
        MainMenu.add("C - Clear all elements from the current list");
    }

    // Display the main menu, this will happen until the user quits.
    public static void printMenu()
    {
        System.out.println();
        //Print the list just displays the list.
        for(String i : MainMenu) {
            System.out.println(i);
        }
        System.out.println();
    }

    public static void moveItem()
    {
        viewList();
        //save the position and content of a deleted item, then compare to the place the user wants to "create a new item".
        int oldPosition = SafeInput.getRangedInt(in,"Enter the line you would like to move",1,myArrList.size())-1;

        int newPosition = SafeInput.getRangedInt(in,"Enter the line you would like to move it to",1,myArrList.size())-1;


        // account for an index shift by adding 1 if the original position was smaller than the newest
        if (oldPosition < newPosition)
        {
            myArrList.add(newPosition+1,myArrList.get(oldPosition));
            myArrList.remove(oldPosition);
        }
        else if (oldPosition > newPosition)
        {
            myArrList.add(newPosition,myArrList.get(oldPosition));
            myArrList.remove(oldPosition+1);
        }
        else//if it's not moving we don't need to move it.
            System.out.println("It's already there! Job's done!");

        needToSave = true;
    }
    public static void openList()
    {// this works very similar to the FileInspector from Lab 12, so i'm reusing that code where it's applicable.
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec="";
        if(!myArrList.isEmpty())
        {
            //if the user already has data in their list, ask to save it before overwriting.
            if (SafeInput.getYNConfirm(in,"Would you like to save the previous list?")) {
                saveList();
            }
            else {
                System.out.println("Clearing the old list...");
                clearList();
                try {
                    File workingDirectory = new File(System.getProperty("user.dir"));
                    chooser.setCurrentDirectory(workingDirectory);
                    // the example also shows that we can do this another way...
                    // Path target = new File(System.getProperty("user.dir")).toPath();
                    // chooser.setCurrentDirectory(target.toFile());
                    // our way just makes more sense to me, with separation of values.

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        //handle opening file here
                        selectedFile = chooser.getSelectedFile();
                        Path file = selectedFile.toPath();

                        InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        int line = 0;
                        while (reader.ready()) {
                            //Line handling:
                            rec = reader.readLine();//read line
                            myArrList.add(rec); //add the read line to the arraylist
                            line++;//iterate line count (contained within the arraylist)
                        }
                        reader.close();//let the reader rest
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Something happened when opening the file, please run the program again.");
                    e.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
    public static void saveList()
    {
        if (myArrList.size() == 0)//empty files don't save.
        {
            System.out.println("You should put something in your file before you save!");
        }
        else {//this is very similar to the DataSaver from Lab 12, reusing that code where applicable.
            String fileName = SafeInput.getNonZeroLenString(in, "Please name your file.");
            System.out.println("Saving your file...");
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName + ".txt");
            needToSave = false;
            try {
                OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                for (String recs : myArrList)
                {
                    writer.write(recs, 0, recs.length());
                    writer.newLine();
                }
                writer.close();
                System.out.println("Saved!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static void clearList()
    {//remove all the data from the arraylist by iterating through it
        if (SafeInput.getYNConfirm(in,"Are you sure you want to remove ALL ITEMS from your list?")) {
            String confirmClear = SafeInput.getNonZeroLenString(in, "Type DELETE to confirm.");
            if (confirmClear.equals("DELETE")) {

                //because of how lists shift indexes according to their size we need to start from the end of the list when clearing it out.
                for (int i = myArrList.size(); i > 0; i--) {
                    myArrList.remove(i - 1);
                }

                needToSave = true;//when we delete (see also: clear) a list, we should ask the user at some point to save.
                //note: they will not be able to save immediately, so it will notify them to put something in the list first.
            } else
                System.out.println("Canceling clear.");
        }else
            System.out.println("Canceling clear.");
    }
}
