package Classes.Controller;

import Classes.Actors.Player;



public class Controls {
    Player player;
    Button left;
    Button right;
    public static boolean leftPressed;
    public static boolean rightPressed;

    public Controls(Player player){
        this.player=player;
        right= new Button(false);
        left= new Button(true);
    }

    public Button getRight() {
        return right;
    }

    public Button getLeft() {
        return left;
    }

    public void act(){
        if(rightPressed && leftPressed){
            player.moveBy(5,0);
        }else if(rightPressed){
            player.rotateBy(5);
        }else if(leftPressed){
            player.rotateBy(-5);
        }

    }
}
