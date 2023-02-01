package ie.tudublin;

public class Cat {
    private int numLives;

    public Cat(){
        numLives = 9;
    }

    public void kill(){
        if(numLives > 0){
            numLives = numLives - 1;

            System.out.println("Ouch");

        }else{
            System.out.println("Dead");
        }
    }
}
