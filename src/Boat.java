
public class Boat {
    private int length;
    private boolean orientation; //true will be horizontal, false will be vertical
    private Cell[] location; // an array of cells because a boat takes up multiple cells

    public Boat(int length, boolean orientation, Cell[] location){
        this.length = length;
        this.orientation = orientation;
        this.location = location;
    }
    //getters and setters for class attributes
    public int getLength(){
        return length;
    }
    public void setLength(int length){
        this.length = length;
    }
    public boolean getOrientation(){
        return orientation;
    }
    public void setOrientation(boolean orientation){
        this.orientation = orientation;
    }
    public Cell[] getLocation(){
        return location;
    }
    public void setLocation(Cell[] location){
        this.location = location;
    }
}
