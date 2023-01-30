package ie.tudublin;

public class Cat {

    private int numLives;

    public Cat(){
        numLives = 9;
    }

    public void kill(){
        if (numLives > 0){
            System.out.println("Ouch");
        }
        if(numLives == 0){
            System.out.println("Dead");
        }
    }


}
