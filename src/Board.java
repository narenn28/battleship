
import java.util.Random;

public class Board {
    private Cell[][] board;
    private Boat[] boats;
    private int shots, turns, ships;
    private boolean isMissile = false; //this is used later when firing a missile

    public Board(int width, int height){
        board = new Cell[width][height]; //creates the game board
        // initializes all the cells of the board as a grid and assigns their status to '-'
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[i][j] = new Cell(i, j, '-');
            }
        }
    }
    public int getShips(){return ships;} // I use this to get the total number of ships left in the game class
    public int getTurns(){return turns;} // I use this to get the total number of turns used for the game class
    public void setTurns(int val){turns += val;} // allows turns to be updated in game class
    public int getShots(){return shots;} //allows number of shots to be accessed be game class
    public void setShots(){shots += 1;} //allows shots to be updated in game class

    //helper method made for placeBoats method that actually finds a place to place boats
    public void findBoatLocation(Boat[] boats){
        for(int k = 0; k < boats.length; k++) {
            int row;
            int col;
            boolean direction = Math.random() < .5; // picks a random direction (true or false) – learned from online source
            Cell[] boatLocation = new Cell[boats[k].getLength()];
            outerloop: // for breaking out of the loop from another loop
            while (true) {
                row = (int) Math.floor(Math.random() * board.length);
                col = (int) Math.floor(Math.random() * board[0].length);
                for(int x = 0; x < boats[k].getLength(); x++){
                    if(direction){
                        if((col + x) < board[0].length && board[row][col + x].get_status() == '-'){
                            boatLocation[x] = board[row][col+x];
                            if(x == boats[k].getLength() - 1){
                                break outerloop; // breaking out of outerloop
                            }
                        }
                        else{break;}
                    }
                    else{
                        if((row + x) < board.length && board[row+x][col].get_status() == '-'){
                            boatLocation[x] = board[row + x][col];
                            if(x == boats[k].getLength() - 1){
                                break outerloop; //breaking out of outerloop
                            }
                        }
                        else{break;}
                    }
                }
            }
            boats[k].setOrientation(direction); //update boat orientation
            boats[k].setLocation((boatLocation)); //update boat location
            Cell[] location = boats[k].getLocation();
            //change status of cells with boat placed
            for(int j = 0; j < location.length; j++){
                location[j].set_status('B');
            }
        }
    }
    public void placeBoats(){ //creates and updates the boats locations'
        if(board.length == 3 || board[0].length == 3){
            ships = 1;
            boats = new Boat[1]; //initializes how many boats should be in boat array
            boats[0] = new Boat(2, true, null);
            findBoatLocation(boats); //call to helper method that finds a valid placement of boats
        }
        else if(board.length > 3 && board.length <= 4 || board[0].length > 3 && board[0].length <=4){
            ships = 2;
            boats = new Boat[2];
            boats[0] = new Boat(2, true, null);
            boats[1] = new Boat(3, true, null);
            findBoatLocation(boats);
        }
        else if(board.length > 4 && board.length <= 6 || board[0].length > 4 && board[0].length <=6){
            ships = 3;
            boats = new Boat[3];
            boats[0] = new Boat(2, true, null);
            boats[1] = new Boat(3, true, null);
            boats[2] = new Boat(3, true, null);
            findBoatLocation(boats);
        }
        else if(board.length > 6 && board.length <= 8 || board[0].length > 6 && board[0].length <=8){
            ships = 4;
            boats = new Boat[4];
            boats[0] = new Boat(2, true, null);
            boats[1] = new Boat(3, true, null);
            boats[2] = new Boat(3, true, null);
            boats[3] = new Boat(4, true, null);
            findBoatLocation(boats);
        }
        else if(board.length > 8 && board.length <= 10 || board[0].length > 8 && board[0].length <=10){ //can this just be "else"?
            ships = 5;
            boats = new Boat[5];
            boats[0] = new Boat(2, true, null);
            boats[1] = new Boat(3, true, null);
            boats[2] = new Boat(3, true, null);
            boats[3] = new Boat(4, true, null);
            boats[4] = new Boat(5, true, null);
            findBoatLocation(boats);
        }
    }
    public boolean sunk(Boat boat){ //helper method that checks whether a boat is sunk of not
        Cell[] boatLocation = boat.getLocation();
        for(int i = 0; i < boatLocation.length; i++){
            if(boatLocation[i].get_status() != 'H'){
                return false; //means that a boat is not sunk
            }
        }
        return true;
    }
    //this method "fires" at the boats and also returns a string of whether a boat was hit, sunk, or not
    public String fire(int x, int y){ //not sure if i should have this return this string
        //shots+=1;
        String status = new String("");
        if(board[x][y].get_status() == 'B'){
            board[x][y].set_status('H');
            boolean sank = false;
            for(int i = 0; i < boats.length; i++){ //this for loop checks what boat a hit cell on the board is a part of
                Cell[] location = boats[i].getLocation();
                for(int j = 0; j < location.length; j++){ //this then checks to see if the boat is fully sunk by calling helper method sunk.
                    if(location[j] == board[x][y]){
                        sank = sunk(boats[i]);
                    }
                }
            }
            if(!sank) {
                status += "Hit!";
            }
            else{
                ships -= 1;
                status+= "Boat Sunk";
            }
        }
        else if(board[x][y].get_status() =='-'){
            board[x][y].set_status('M');
            status+="Miss";
        }
        else{
            if(!isMissile){
                turns += 1;
            }
            isMissile = false;
            status += "Penalty, miss a turn";
        }
        return status;
    }
    public void display(){ //displays the game board for the player without exposing where the actual boats are
        String table = new String("  ");

        // print out the column numbers for the board
        for(int k = 0; k < board[0].length; k++){
            table += " " + k + " ";
        }
        for(int j = 0; j < board.length; j++) {
            table += "\n" + j + " "; //prints out the row numbers for the board
            for (int i = 0; i < board[0].length; i++) {
                if (board[j][i].get_status() == 'M') {
                    table += " M ";
                }
                else if(board[j][i].get_status() == 'H') {
                    table += " H ";
                }
                else {
                    table += " - ";
                }
            }
        }
        table += "\n";
        System.out.println(table);
    }
    public void print(){ // displays the game board with the location of the boats - for debugging
        String table = new String("  ");
        //print out the column number for the board
        for(int k = 0; k < board[0].length; k++){
            table += " " + k + " ";
        }
        for(int j = 0; j < board.length; j++) {
            table+= "\n" + j + " ";
            for (int i = 0; i < board[0].length; i++) {
                if (board[j][i].get_status() == '-') {
                    table += " - ";
                } else if (board[j][i].get_status() == 'M') {
                    table += " M ";
                }
                else if(board[j][i].get_status() == 'H'){
                    table += " H ";
                }
                else{
                    table += " B ";
                }
            }
        }
        table+= "\n";
        System.out.println(table);
    }
    public void missile(int x, int y){ //represents the firing of a missile, which really just calls the fire method multiple times
        isMissile = true;
        fire(x,y);
        try{
            fire(x-1,y-1);
        } catch (Exception ignored){}
        try{
            fire(x-1, y);
        } catch (Exception ignored){}
        try{
            fire(x-1, y+1);
        } catch (Exception ignored){}
        try{
            fire(x, y-1);
        } catch (Exception ignored){}
        try{
            fire(x, y+1);
        } catch (Exception ignored){}
        try{
            fire(x+1, y-1);
        } catch (Exception ignored){}
        try{
            fire(x+1, y);
        } catch (Exception ignored){}
        try{
            fire(x+1,y+1);
        } catch (Exception ignored){}

    }
    //drone scanning method
    public void drone(int direction, int index){
        int count = 0;
        if(direction == 0){ // if scanning rows
            for(int i = 0; i < board[0].length; i++){
                if(board[index][i].get_status() == 'B' || board[index][i].get_status() == 'H'){ //checks how many boats a row has
                    count += 1;
                }
            }
        }
        else{ //for scanning column
            for(int i = 0; i < board.length; i++){
                if(board[i][index].get_status() == 'B' || board[i][index].get_status() == 'H'){ //checks how many boats a column has
                    count += 1;
                }
            }
        }
        System.out.println("Drone has scanned " + count + " targets in the specified area");
    }
}
