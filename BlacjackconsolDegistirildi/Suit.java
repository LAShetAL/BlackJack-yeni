
public enum Suit {
    SINEK("Sinek"),
    KUPA("Kupa"),
    MACA("Maca"),
    KARO("Karo");
    String suitIsim;

    Suit(String suitIsim){
        this.suitIsim = suitIsim;
    }

    public String toString(){
        return suitIsim;
    }
}
