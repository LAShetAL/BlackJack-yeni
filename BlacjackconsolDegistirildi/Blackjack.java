import java.util.*;

class Blackjack{

    
    
public static void main(String[] args){
      
     System.out.println("Blackjack!!!");
     Deck playingDeck = new Deck();
     playingDeck.createFullDeck();
     playingDeck.shuffle();

     //create a deck for the player
     Deck playerDeck = new Deck();
     Deck playerSideDeck1 = new Deck();
     Deck playerSideDeck2 = new Deck();
     Deck playerSideDecks1Side1 = new Deck();
     Deck playerSideDecks1Side2= new Deck();
     Deck playerTempDeck= new Deck();
     Deck dealerDeck = new Deck();
     Deck playerSideDecks2Side1 = new Deck();
     Deck playerSideDecks2Side2 = new Deck();
     double playerMoney = 100.00;
    
     System.out.println("Betinizi girin");
     Scanner userIn = new Scanner(System.in);

     //game loop
     
     
     while(playerMoney > 0){
        //bet
        System.out.println("Toplam paraniz "+ playerMoney+ " toplam bahsinizi giriniz");
        double playerBet = userIn.nextDouble();
        if(playerBet> playerMoney){
            System.out.println("sahip olmadiginiz parayi koyamazsin");
            break;
        }

        boolean endRound = false;
        boolean splitMax= false;
        boolean birinciSplit=false;
        boolean ikinciSplit=false;
        boolean doubleDwn=false;
        boolean insAsked=false;
        playerDeck.draw(playingDeck);
        dealerDeck.draw(playingDeck);
        
        playerDeck.draw(playingDeck);
        dealerDeck.draw(playingDeck);
        //split ilk durumda yok
        boolean split=false;
        
        while(true){
            if(split==false&&(dealerDeck.getCard(0).getValue()!= 11||playerDeck.cardsValue()!=21)){
            System.out.println("Eliniz:");
            System.out.println(playerDeck.toString());
            System.out.println("Elinizin toplami:"+ playerDeck.cardsValue());
            System.out.println("Dealerin eli: "+ dealerDeck.getCard(0).toString()+ " ve [Gizli]");
            System.out.println("Hit:1 , Stay:2 , Split:3, Double-Down:4");
            } if(split==false&&insAsked==false&&(dealerDeck.getCard(0).getValue()==11||playerDeck.cardsValue()==21||dealerDeck.cardsValue()==21)){
                insAsked=true;
                
                if(playerDeck.cardsValue()==21&&dealerDeck.cardsValue()!=21&&endRound==false&&split==false){
                    System.out.println("Blackjack! Kazandin");
                    playerMoney += 3*playerBet/2;
                    endRound=true;
                    break;
                }
                
                // INSURANCE
                if(dealerDeck.getCard(0).getValue()!= 11&&dealerDeck.cardsValue()==21){
                    System.out.println("Dealer Has blacjack!");
                    
                    endRound= true;
                    break;
                }
                if(dealerDeck.getCard(0).getValue()== 11){
                    System.out.println("Insurance? Y/N");
                    String ins= userIn.next();
                    double insBet= playerBet/2;
                    if(ins.equals("y")){
                        System.out.println("Insurance beti verildi");
                        playerMoney-=insBet;
                    }else if(ins.equals("n")){
                        System.out.println("Insurance verilmedi");
                    
                    }
                    
                    if(dealerDeck.cardsValue()==21){
                        System.out.println("Dealer Has BlackJack");
                        if(ins.equals("y")){
                        System.out.println("Paran Sigortalandi");
                        playerMoney+=insBet;
                        playerMoney+=playerBet;
                        endRound=true;
                        break;
                        }
                        if(ins.equals("n")){
                            System.out.println("Paran sigortalanmadi");
                            playerMoney-=playerBet;
                            endRound=true;
                            break;
                        }
                    }else if(dealerDeck.cardsValue()!=21&&ins.equals("n")){
                        System.out.println("Dealer'da 10 yok");
                        
                         
                    }
                
               
            }
            System.out.println("Eliniz:");
            System.out.println(playerDeck.toString());
            System.out.println("Elinizin toplami:"+ playerDeck.cardsValue());
            System.out.println("Dealerin eli: "+ dealerDeck.getCard(0).toString()+ " ve [Gizli]");
            System.out.println("Hit:1 , Stay:2 , Split:3, Double-Down:4");
            }
            if(split==true){//split olduğunda döngünün sonunda kart değerlerini farklı desteler olduğu için ayrı göstermesi gerek
                System.out.println("1. eliniz: " +playerSideDeck1.cardsValue()+"\n"+"2 .eliniz: "+playerSideDeck2.cardsValue()+"\n");
                
            }
            
            
            
       
            // player ne yapacak
            
        
            int response = userIn.nextInt();
            //hit derse
            if(response==1){
                doubleDwn=true;
                playerDeck.draw(playingDeck);
                System.out.println("Sunu cektiniz:"+ playerDeck.getCard(playerDeck.deckSize()-1).toString());
                System.out.println("Elinizin toplamı: "+ playerDeck.cardsValue());
                if(playerDeck.cardsValue()==21){
                    break;
                }
                if(playerDeck.cardsValue() >21){
                    System.out.println("Busted! Eliniz"+ playerDeck.cardsValue());
                    playerMoney -= playerBet;      
                    endRound = true;
                    break;    
                }
                  
            }
            //stay derse game looptan cıkıyor
            if(response==2){
                break;
            }
            //tatlı split kısmı
            //int indis1= playerDeck.getCard(0).cardsValue();

           // if(response==3&&!playerDeck.getCard(0).cardsValue().equals(playerDeck.getCard(1).cardsValue())){
           //     System.out.println("bunu yapamn");
           // }
            if((response==3)&&playerDeck.getCard(0).getValue()==playerDeck.getCard(1).getValue()){
                
                playerBet = playerBet+playerBet; //iki deste içinde aynı miktar para 5 girildiyse ayrılan deste için de 5
                
                
                split=true;
                // deste sol ve sag olarak ikiye ayrılıp direkt kart çekiliyor.
                playerDeck.moveAllToDeck(playerTempDeck); //gecici desteye
                playerSideDeck1.addCard(playerTempDeck.getCard(0)); //gecici destenin ilk elemanı 1. elin 1. elemanı
                playerSideDeck1.draw(playingDeck); //1. ele kart çek
                playerSideDeck2.addCard(playerTempDeck.getCard(1));//gecici destenin 2. elemanı 2. elin 1. elemanı
                playerSideDeck2.draw(playingDeck); //2. ele kart cek
                playerTempDeck.moveAllToDeck(playingDeck);

                // sol desteye direkt sorulur kart çekecek misiniz
                while(true){ //split loopu 2. elin busted veya stay olmasıyla bitecektir
                    System.out.println("1. eliniz: "+playerSideDeck1.toString()+"\n");
                    System.out.println("Toplamı: "+playerSideDeck1.cardsValue());
                    System.out.println("Hit: 1 or Stay: 2 or Split:3");
                    int cevab= userIn.nextInt(); //cevap alınır
                    if(cevab==1){
                        playerSideDeck1.draw(playingDeck);
                        if(playerSideDeck1.cardsValue()>21){
                            System.out.println("Busted, 1. elin kaybetti");
                            
                            break;
                        }
                        
                        System.out.println("Sunu cektiniz: "+ playerSideDeck1.getCard(playerSideDeck1.deckSize()-1).toString()+"\n");
                        System.out.println("Toplamı: "+ playerSideDeck1.cardsValue());
                        if(playerSideDeck1.cardsValue()==21){
                            break;
                        }
                    }
                    if(cevab==2){
                        break;
                    }
                    if((cevab==3)&&playerSideDeck1.getCard(0).getValue()==playerSideDeck1.getCard(1).getValue()){
                        splitMax=true;
                        birinciSplit=true;
                        playerBet += playerBet/2;
                        playerSideDeck1.moveAllToDeck(playerTempDeck);
                        playerSideDecks1Side1.addCard(playerTempDeck.getCard(0));
                        playerSideDecks1Side1.draw(playingDeck);
                        playerSideDecks1Side2.addCard(playerTempDeck.getCard(1));
                        playerSideDecks1Side2.draw(playingDeck);
                       
                        while(true){
                            System.out.println("1. eliniz: "+playerSideDecks1Side1.toString()+"\n");
                            System.out.println("Toplamı: "+playerSideDecks1Side1.cardsValue());
                            System.out.println("Hit: 1 or Stay: 2 ");
                            int cevaps= userIn.nextInt();
                            if(cevaps==1){
                                playerSideDecks1Side1.draw(playingDeck);
                                if(playerSideDecks1Side1.cardsValue()>21){
                                    System.out.println("Busted, 1. elin kaybetti");
                                    
                                    break;
                            }
                            
                                System.out.println("Sunu cektiniz: "+ playerSideDecks1Side1.getCard(playerSideDecks1Side1.deckSize()-1).toString()+"\n");
                                System.out.println("Toplamı: "+ playerSideDecks1Side1.cardsValue());
                                if(playerSideDecks1Side1.cardsValue()==21){
                                    break;
                                }
                            }
                            if(cevaps==2){
                                break;
                            }
                            
                        }
                        while(true){
                            System.out.println("2. eliniz: "+playerSideDecks1Side2.toString()+"\n");
                            System.out.println("Toplamı: "+playerSideDecks1Side2.cardsValue());
                            System.out.println("Hit: 1 or Stay: 2 ");
                            int cevapsu= userIn.nextInt();
                            if(cevapsu==1){
                                playerSideDecks1Side2.draw(playingDeck);
                                if(playerSideDecks1Side2.cardsValue()>21){
                                    System.out.println("Busted, 2. elin kaybetti");
                                    
                                    break;
                            }
                            
                                System.out.println("Sunu cektiniz: "+ playerSideDecks1Side2.getCard(playerSideDecks1Side2.deckSize()-1).toString()+"\n");
                                System.out.println("Toplamı: "+ playerSideDecks1Side2.cardsValue());
                                if(playerSideDecks1Side2.cardsValue()==21){
                                    break;
                                }
                            }
                            if(cevapsu==2){
                                break;
                            }
                        }
                        while(true){
                            if(splitMax==false){
                                System.out.println("2. eliniz: "+playerSideDeck2.toString()+"\n");
                            }else {
                            System.out.println("3. eliniz: "+playerSideDeck2.toString()+"\n");
                            }
                            System.out.println("Toplamı: "+playerSideDeck2.cardsValue());
                            System.out.println("Hit: 1 or Stay: 2");
                        int cevap= userIn.nextInt();
                        if(cevap==1){
                            playerSideDeck2.draw(playingDeck);
                            if(playerSideDeck2.cardsValue()>21){
                                if(splitMax==false){
                                System.out.println("Busted, 2. elin kaybetti");
                                }
                                else{
                                    System.out.println("Busted 3. elin kaybetti");
                                }
                                playerMoney -= playerBet/2;
                                
                                break;
                            }
                            
                            System.out.println("Sunu cektiniz: "+ playerSideDeck2.getCard(playerSideDeck2.deckSize()-1).toString()+"\n");
                            System.out.println("Toplamı: "+ playerSideDeck2.cardsValue());
                            if(playerSideDeck2.cardsValue()==21){
                                break;
                            }
                        }
                        if(cevap==2){
                            
                            break;
                        }
                        
                    }
                    
                    }
                }
                
               //2. deste kısmı 
                while(true){
                    if(splitMax==false){
                        System.out.println("2. eliniz: "+playerSideDeck2.toString()+"\n");
                        System.out.println("Toplamı: "+playerSideDeck2.cardsValue());
                        System.out.println("Hit: 1 or Stay: 2 or Split:3");
                        int cev= userIn.nextInt(); //cevap alınır
                        if(cev==1){
                            playerSideDeck2.draw(playingDeck);
                            if(playerSideDeck2.cardsValue()>21){
                                System.out.println("Busted, 2. elin kaybetti");
                                
                                break;
                            }
                            
                            System.out.println("Sunu cektiniz: "+ playerSideDeck2.getCard(playerSideDeck2.deckSize()-1).toString()+"\n");
                            System.out.println("Toplamı: "+ playerSideDeck2.cardsValue());
                            if(playerSideDeck2.cardsValue()==21){
                                break;
                            }
                        }
                        if(cev==2){
                            break;
                        }
                        if((cev==3)&&(playerSideDeck2.getCard(0).getValue()==playerSideDeck2.getCard(1).getValue())){
                            splitMax=true;
                            ikinciSplit=true;
                            playerBet += playerBet/2;
                            playerSideDeck2.moveAllToDeck(playerTempDeck);
                            playerSideDecks2Side1.addCard(playerTempDeck.getCard(0));
                            playerSideDecks2Side1.draw(playingDeck);
                            playerSideDecks2Side2.addCard(playerTempDeck.getCard(1));
                            playerSideDecks2Side2.draw(playingDeck);
                           
                            while(true){
                                System.out.println("2. eliniz: "+playerSideDecks2Side1.toString()+"\n");
                                System.out.println("Toplamı: "+playerSideDecks2Side1.cardsValue());
                                System.out.println("Hit: 1 or Stay: 2 ");
                                int cevaps= userIn.nextInt();
                                if(cevaps==1){
                                    playerSideDecks2Side1.draw(playingDeck);
                                    if(playerSideDecks2Side1.cardsValue()>21){
                                        System.out.println("Busted, 2. elin kaybetti");
                                        break;
                                }
                                
                                    System.out.println("Sunu cektiniz: "+ playerSideDecks2Side1.getCard(playerSideDecks2Side1.deckSize()-1).toString()+"\n");
                                    System.out.println("Toplamı: "+ playerSideDecks2Side1.cardsValue());
                                    if(playerSideDecks2Side1.cardsValue()==21){
                                        break;
                                    }
                                }
                                if(cevaps==2){
                                    break;
                                }
                                
                            }
                            while(true){
                                System.out.println("3. eliniz: "+playerSideDecks2Side2.toString()+"\n");
                                System.out.println("Toplamı: "+playerSideDecks2Side2.cardsValue());
                                System.out.println("Hit: 1 or Stay: 2 ");
                                int cevapsu= userIn.nextInt();
                                if(cevapsu==1){
                                    playerSideDecks2Side2.draw(playingDeck);
                                    if(playerSideDecks2Side2.cardsValue()>21){
                                        System.out.println("Busted, 3. elin kaybetti");
                                        
                                        break;
                                }
                                
                                    System.out.println("Sunu cektiniz: "+ playerSideDecks2Side2.getCard(playerSideDecks2Side2.deckSize()-1).toString()+"\n");
                                    System.out.println("Toplamı: "+ playerSideDecks2Side2.cardsValue());
                                    if(playerSideDecks2Side1.cardsValue()==21){
                                        break;
                                    }
                                }
                                if(cevapsu==2){
                                    break;
                                }
                            }
                           break;
                        }
                    //2. elde split yapılırsa kısmı
                    }else if(splitMax!=true) {
                    System.out.println("3. eliniz: "+playerSideDeck2.toString()+"\n");
                    System.out.println("Hit: 1 or Stay: 2");
                int cevap= userIn.nextInt();
                if(cevap==1){
                    playerSideDeck2.draw(playingDeck);
                    if(playerSideDeck2.cardsValue()>21){
                        if(splitMax==false){
                        System.out.println("Busted, 2. elin kaybetti");
                        }
                        else{
                            System.out.println("Busted 3. elin kaybetti");
                        }
                        playerMoney -= playerBet/2;
                        
                        break;
                    }
                    System.out.println("Sunu cektiniz: "+ playerSideDeck2.getCard(playerSideDeck2.deckSize()-1).toString()+"\n");
                    System.out.println("Toplamı: "+ playerSideDeck2.cardsValue());
                    if(playerSideDeck2.cardsValue()==21){
                        break;
                    }
                    
                }
                if(cevap==2){
                    
                    break;
                }
                    }
                    
                    
                
            }
                break;
            }
            if(response == 4&&doubleDwn==false){
                doubleDwn=true;
                playerBet = 2*playerBet;
                playerDeck.draw(playingDeck);
                System.out.println("Sunu cektiniz: "+ playerDeck.getCard(playerDeck.deckSize()-1).toString());
                System.out.println("Elinizin degeri: "+ playerDeck.cardsValue());
                if(playerDeck.cardsValue()>21){
                    playerMoney -= playerBet;
                    System.out.println("Busted.");
                    endRound=true;
                    break;
                }
                
                break;
            }
        }
        //Dealer 2. kartı aciyor
        
        System.out.println("Dealerin eli"+dealerDeck.toString());
        
        while((dealerDeck.cardsValue()<17)&& endRound==false){
            dealerDeck.draw(playingDeck);
            System.out.println("Dealer:"+ dealerDeck.getCard(dealerDeck.deckSize()-1).toString()+"'i cekti");
        }
        
        if((dealerDeck.cardsValue() > playerDeck.cardsValue())&&split==false&&dealerDeck.cardsValue()<=21){
            System.out.println("Dealer KAZANDI");
            playerMoney -= playerBet;
            endRound = true;
        }
        System.out.println("Dealerin eli:"+dealerDeck.cardsValue());
        if((dealerDeck.cardsValue()>21)&& endRound==false){
            if(split==true&&splitMax==false){
                if(playerSideDeck1.cardsValue()<=21){
                    System.out.println("1. el kazandi \n");
                    playerMoney += playerBet/2;
                }
                if(playerSideDeck1.cardsValue()>21){
                    System.out.println("1.el kaybetti");
                    playerMoney -= playerBet/2;
                    
                }
                if(playerSideDeck2.cardsValue()<=21){
                    System.out.println("2. el kazandi ");
                    playerMoney += playerBet/2;
                    endRound=true;
                }
                if(playerSideDeck2.cardsValue()>21){
                    System.out.println("2. el kaybetti");
                    playerMoney -= playerBet/2;
                    endRound=true;
                }

            }
            else if(splitMax==true&&birinciSplit==true){
                if(playerSideDecks1Side1.cardsValue()<=21){
                    System.out.println("1. el kazandi \n");
                    playerMoney += playerBet/2;
                }
                if(playerSideDeck2.cardsValue()<=21){
                    System.out.println("3. el kazandi ");
                    playerMoney += playerBet/2;
                    endRound=true;
                }
                if(playerSideDeck2.cardsValue()>21){
                    System.out.println("3. el kaybetti");
                    playerMoney -= playerBet/2;
                    endRound=true;
                }
                if(playerSideDecks1Side1.cardsValue()>21){
                    System.out.println("1.el kaybetti");
                    playerMoney -= playerBet/2;
                    
                }
                if(playerSideDecks1Side2.cardsValue()>21){
                    System.out.println("2.el kaybetti");
                    playerMoney -= playerBet/2;
                    
                }
                if(playerSideDecks1Side1.cardsValue()<=21){
                    System.out.println("2. el kazandi \n");
                    playerMoney += playerBet/2;
                }
            }
            else if(splitMax==true&&ikinciSplit==true){
                if(playerSideDeck1.cardsValue()<=21){
                    System.out.println("1. el kazandi \n");
                    playerMoney += playerBet/2;
                }
                if(playerSideDecks2Side2.cardsValue()<=21){
                    System.out.println("3. el kazandi ");
                    playerMoney += playerBet/2;
                    endRound=true;
                }
                if(playerSideDecks2Side2.cardsValue()>21){
                    System.out.println("3. el kaybetti");
                    playerMoney -= playerBet/2;
                    endRound=true;
                }
                if(playerSideDeck1.cardsValue()>21){
                    System.out.println("1.el kaybetti");
                    playerMoney -= playerBet/2;
                    
                }
                if(playerSideDecks2Side1.cardsValue()>21){
                    System.out.println("2.el kaybetti");
                    playerMoney -= playerBet/2;
                    
                }
                if(playerSideDecks2Side1.cardsValue()<=21){
                    System.out.println("2. el kazandi \n");
                    playerMoney += playerBet/2;
                }
            }
            else if(split==false&&splitMax==false){
            System.out.println("dealer busted, kazandın");
            
            playerMoney +=playerBet;
            endRound= true;
            }
        }

         if((playerDeck.cardsValue()==dealerDeck.cardsValue())&& endRound==false&&split==false){
            System.out.println("push");
            endRound= true;
        }
         if((playerDeck.cardsValue()>dealerDeck.cardsValue())&& endRound==false&&split==false&&playerDeck.cardsValue()<=21){
            System.out.println("Kazandın");
            playerMoney+=playerBet;
            endRound= true;
        }
        
        
        if((playerSideDeck1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21&&split==true&&splitMax==false){
            System.out.println("Birinci elin push");
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21){
                System.out.println("ikinci elin push");
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                playerMoney -= playerBet/2;
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                playerMoney += playerBet/2;
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()>21)&&endRound==false){
                System.out.println("ikinci el busted");
                
                endRound=true;
            }
        }
        if((playerSideDeck1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21&&dealerDeck.cardsValue()<=21&&split==true&&splitMax==false){
            System.out.println("birinci elin kaybetti");
            playerMoney -= playerBet/2;
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci elin push");
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                playerMoney -= playerBet/2;
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                playerMoney += playerBet/2;
                endRound= true;
            }
            if((playerSideDeck2.cardsValue()>21)&&endRound==false){
                System.out.println("ikinci el busted");
                
                endRound=true;
            }
        }
        if((playerSideDeck1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21&&dealerDeck.cardsValue()<=21&&split==true&&splitMax==false){
            System.out.println("birinci el kazandi");
            playerMoney += playerBet/2;
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el push");
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                playerMoney -= playerBet/2;
                endRound= true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                playerMoney += playerBet/2;
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()>21)&&endRound==false){
                System.out.println("ikinci el busted");
                
                endRound=true;
            }

        }
        if((playerSideDeck1.cardsValue()>21)&&endRound==false&&split==true&&splitMax==false){
            System.out.println("birinci el busted");
            playerMoney -= playerBet/2;
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el push");
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck2.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                playerMoney -= playerBet/2;
                endRound= true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("ikinci el kazandi");
                playerMoney += playerBet/2;
                endRound=true;
            }
            if((playerSideDeck2.cardsValue()>21)&&endRound==false){
                System.out.println("ikinci el busted");
                
                endRound=true;
            }
        }
        
        if((playerSideDecks1Side1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks1Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&birinciSplit==true){
            System.out.println("1. el kazandi");
            playerMoney += playerBet/3;
            if((playerSideDecks1Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks1Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks1Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if(playerSideDecks1Side2.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks1Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDeck2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
        }
        if((playerSideDecks1Side1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks1Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&birinciSplit==true){
            System.out.println("1. el push");
            
            if((playerSideDecks1Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks1Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks1Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if((playerSideDecks1Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if(playerSideDecks1Side2.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
                endRound = true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDeck2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
        }

        if((playerSideDecks1Side1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&birinciSplit==true){
            System.out.println("1. el kaybetti");
            playerMoney -= playerBet/3;
            if((playerSideDecks1Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks1Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks1Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if(playerSideDecks1Side2.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks1Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
                endRound = true;
            }
            if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDeck2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }

        }
        /////////////// 2. kısm split check

        if((playerSideDeck1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&ikinciSplit==true){
            System.out.println("1. el kazandi");
            playerMoney += playerBet/3;
            if((playerSideDecks2Side1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if(playerSideDecks2Side1.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks2Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
            }
            if((playerSideDecks2Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDecks2Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDecks2Side2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
        }
        if((playerSideDeck1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(playerSideDeck1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&ikinciSplit==true){
            System.out.println("1. el push");
            
            if((playerSideDecks2Side1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if(playerSideDecks2Side1.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks2Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
                endRound = true;
            }
            if((playerSideDecks2Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDecks2Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDecks2Side2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }

        }

        if((playerSideDeck1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)&&splitMax==true&&ikinciSplit==true){
            System.out.println("1. el kaybetti");
            playerMoney -= playerBet/3;
            if((playerSideDecks2Side1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kazandi");
                playerMoney += playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el kaybetti");
                playerMoney -= playerBet/3;
            }
            if((playerSideDecks2Side1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("2. el push");
            }
            if(playerSideDecks2Side1.cardsValue()>21&&endRound==false){
                System.out.println("2.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks2Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
                endRound = true;
            }
            if((playerSideDecks2Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDecks2Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDecks2Side2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }

        }
        if((playerSideDeck1.cardsValue()>21)&&endRound==false&&splitMax==true&&birinciSplit==true){
            System.out.println("birinci el busted");
            playerMoney -= playerBet/2;
            if((playerSideDecks2Side1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDecks2Side1.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el push");
                endRound=true;
            }
            if((playerSideDecks2Side1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDecks2Side1.cardsValue()<=21&&dealerDeck.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                playerMoney -= playerBet/3;
                endRound= true;
            }
            if((playerSideDecks2Side1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side1.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("ikinci el kazandi");
                playerMoney += playerBet/3;
                endRound=true;
            }
            if((playerSideDecks2Side1.cardsValue()>21)&&endRound==false){
                System.out.println("ikinci el busted");
                playerMoney-=playerBet/3;
                endRound=true;
            }
            if((playerSideDecks2Side2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el push");
                endRound = true;
            }
            if((playerSideDecks2Side2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&(playerSideDecks2Side2.cardsValue()<=21)&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kazandi");
                playerMoney += playerBet/3;
                endRound = true;
            }
            
            if((playerSideDecks2Side2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&(dealerDeck.cardsValue()<=21)){
                System.out.println("3. el kaybetti");
                playerMoney -= playerBet/3;
                endRound = true;
            }
            if(playerSideDecks2Side2.cardsValue()>21&&endRound==false){
                System.out.println("3.el busted");
                playerMoney -= playerBet/3;
                endRound= true;
            }
        }
        
        //tüm kartlar el bitince direkt desteye tekrar geri gelir karıştırılır. oyunun aslındaki versiyondan önce tamamen kodun iyileştirilmesi gerek.

        playerDeck.moveAllToDeck(playingDeck);
        playerSideDeck1.moveAllToDeck(playingDeck);
        playerSideDeck2.moveAllToDeck(playingDeck);
        playerSideDecks1Side1.moveAllToDeck(playingDeck);
        playerSideDecks1Side2.moveAllToDeck(playingDeck);
        playerTempDeck.moveAllToDeck(playingDeck);
        dealerDeck.moveAllToDeck(playingDeck);
        playerSideDecks2Side2.moveAllToDeck(playingDeck);
        playerSideDecks2Side1.moveAllToDeck(playingDeck);
        System.out.println("Elin sonu");
     }
     System.out.println("bitik oc öldün:DD"); // para biterse kafaya kursun
    


}

}


