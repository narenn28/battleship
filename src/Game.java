
import java.util.Locale;
import java.util.Scanner;
import java.lang.Math;

//this class is the one that is actually run and it is the playing of the actual battleship game
public class Game {
    public static void main(String[] args){
        boolean debug = false;
        System.out.println("Welcome to Battleship! Here are some rules: \n" +
                "(1) you have two special powers, a missile and a drone. Each can only be used once and can be called by typing in 'missile' or 'drone' \n" +
                "(2) If you fire at a coordinate not on the board, you will lose a turn. Same applies if you fire at a target you have already fired at.\n" +
                "(3) At the end of the game, you will receive your score, which will be how many turns you took and how many shots you took");
        System.out.println("Debug Mode?");
        Scanner myScanner = new Scanner(System.in);
        String input = myScanner.nextLine();
        if(input.equals("yes") || input.equals("Yes") || input.equals("Y") || input.equals("y")){ //checking whether user wants to use the debug mode of the game
            debug = true;
        }
        //this part of is for creating the game board
        System.out.println("Please choose a board size (minimum 3x3 to maximum 10x10). First type the width and then the length followed by a space.");
        myScanner = new Scanner(System.in);
        int width;
        int height;
        while(true){ //makes sure user types in valid board size
            width = myScanner.nextInt();
            height = myScanner.nextInt();
            if((width >= 3 && width <= 10) && (height >= 3 && height <= 10)){
                System.out.println("Board created: ");
                break;
            }
            else{
                System.out.println("Please enter valid dimensions");
            }
        }

        Board game = new Board(width, height); //creates board with user input
        game.placeBoats(); //randomly places the boats on board
        //this part is actually playing the game
        int row;
        int column;
        int missileShots = 0;
        int droneCount = 0;
        while(true){
            if(debug == true){ //checks if regular game board or debugging game board should be displayed
                System.out.println("Debug board:");
                game.print();
            }
            else{
                game.display();
            }
            game.setTurns(1); //updates the turn
            System.out.println("Turn " + game.getTurns());
            System.out.println("Row:");
            //row = myScanner.nextInt();
            String x = myScanner.next().toLowerCase(); //checks to see if they want to shoot a missile or drone
            if(x.equals("missile") && missileShots == 0){ //checks to see whether user wants to fire a missile
                missileShots = 1;
                System.out.println("You have chosen to use your 1 missile shot.");
                while(true){ //makes sure user types in proper coordinate
                    System.out.println("Row:");
                    row = myScanner.nextInt();
                    System.out.println("Column:");
                    column = myScanner.nextInt();
                    if((row >= 0 && row<width) && (column >= 0 && column < height)){
                        game.missile(row,column); //fires missile
                        break;
                    }
                    else{
                        System.out.println("Please choose a valid coordinate");
                    }
                }
            }
            else if(x.equals("drone") && droneCount == 0){ //checks whether user wants to use drone
                droneCount = 1;
                System.out.println("You have chosen to use your one drone scan.");
                System.out.println("Would you like to scan a row or a column?");
                String droneScan = null;
                while(true){ //checks whether valid arguments are given by user
                    droneScan = myScanner.nextLine().toLowerCase();
                    if(droneScan.equals("row") || droneScan.equals("column")){
                        break;
                    }
                    else{
                        System.out.println("Please choose row or column");
                    }
                }
                System.out.println(droneScan + " number:");
                int value= 0;
                while(true){ //checks to make sure valid row/column number is given by user
                    value = myScanner.nextInt();
                    if(droneScan.equals("row")){
                        if(value >= 0 && value < width){
                            game.drone(0, value); //launches drone
                            break;
                        }
                        else{
                            System.out.println("Please input valid coordinate");
                        }
                    }
                    else if(droneScan.equals("column")){
                        if(value >= 0 && value < height){
                            game.drone(1, value); //launches drone
                            break;
                        }
                        else{
                            System.out.println("Please enter valid coordinate");
                        }
                    }
                }
            }
            else{
                if(x.equals("missile") && missileShots == 1){ //checks if any missiles are left for user to fire
                    System.out.println("No missiles left");
                    //missileShots = 2;
//                    System.out.println("Row: ");
//                    x = myScanner.next();
                    game.setTurns(-1);
                    continue;
                }
                else if(x.equals("drone") && droneCount == 1){ //checks if any drones are left for user to use
                    System.out.println("No drones left");
                    //droneCount = 2;
                    game.setTurns(-1);
                    continue;
                }
                // this is where the regular firing for the game takes place
                try{
                    row = Integer.parseInt(x);
                }catch(Exception e){
                    System.out.println("Please enter a valid integer");
                    game.setTurns(-1);
                    continue;
                }
                System.out.println("Column:");
                column = myScanner.nextInt();
                //checks whether coordinate is valid
                if(!((row >= 0 && row<width) && (column >= 0 && column < height))){
                    System.out.println("Please enter a valid position, you have received a penalty and one turn is skipped");
                    game.setTurns(1);
                }
                else{
                    game.setShots();
                    System.out.println(game.fire(row, column)); // fires at user specified location
                }
            }
            if(game.getShips() == 0){
                break;
            }

        }
        //end of game messages
        System.out.println("All ships sunk, final board:");
        game.display();
        System.out.println("Total turns taken: " + game.getTurns()); //prints out how many turns the user used
        System.out.println("Total shots taken: " + game.getShots()); //prints out number of shots user took
    }
}
