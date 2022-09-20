public class Card {
    private Suit suit;
    private Value value;

    public Card( Suit suit, Value value){
        this.value= value;
        this.suit= suit;

    }
    public int getValue(){
        return value.dValue;
    }
    public Suit getSuit(){
        return suit;
    }
    public Value getVal(){
        return value;
    }
    public String toString(){
        return ("["+suit+"  "+ value + "] ("+this.getValue()+")");
        

    }

    
}
