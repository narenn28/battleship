//Written by Naren Nandyal, nandy017, ID: 5627987

public class Cell {
    public int row;
    public int col;
    public char status;

    //constructor
    public Cell(int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status; //represents whether the cell has a boat and has been hit
    }

    //getter method for status
    public char get_status(){
        return this.status;
    }

    //setter method for status
    public void set_status(char c){
        this.status = c;
    }

    public int get_row(){
        return row;
    }

    public void set_row(int r){
        row = r;
    }

    public int get_col(){
        return col;
    }

    public void set_col(int c){
        col = c;
    }
}
