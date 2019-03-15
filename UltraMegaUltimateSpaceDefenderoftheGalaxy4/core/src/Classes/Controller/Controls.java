package Classes.Controller;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import Classes.Actors.Player;



public class Controls {
    Player player;
    ButtonListener left;
    ButtonListener right;
    public static boolean leftPressed;
    public static boolean rightPressed;


    public Controls(Player player){
        this.player=player;
        right= new ButtonListener(new Button(false));
        left= new ButtonListener(new Button(true));


    }

    public ButtonListener getRight() {
        return right;
    }

    public ButtonListener getLeft() {
        return left;
    }


    public void act(){


        if(rightPressed && leftPressed){
            player.impulsate(50);
        }else if(rightPressed){
            player.rotate(6);
        }else if(leftPressed){
            player.rotate(-6);
        }

    }
}
