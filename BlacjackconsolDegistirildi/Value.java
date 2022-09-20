public enum Value{
    AS("Asl",11),
    IKI("Iki",2),
    UC("Uc",3),
    DORT("Dort",4),
    BES("Bes",5),
    ALTI("Altı",6),
    YEDI("Yedi",7),
    SEKIZ("Sekiz",8),
    DOKUZ("Dokuz",9),
    ON("On",10),
    KING("Kral",10),
    JACK("Jack",10),
    QUEEN("Kız",10);

String dName;
int dValue;

Value(String dName, int dValue){
    this.dName= dName;
    this.dValue = dValue;
}
public String toString(){
    return dName;
}
}
