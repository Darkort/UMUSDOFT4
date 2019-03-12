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

        float x = MathUtils.cos(player.getBody().getAngle()*(float)(Math.PI/180));
        float y = MathUtils.sin(player.getBody().getAngle()*(float)(Math.PI/180));

        if(rightPressed && leftPressed){
            player.getBody().applyForceToCenter( new Vector2(x*50,y*50), true);
        }else if(rightPressed){
            player.getBody().setTransform(player.getBody().getPosition(),player.getBody().getAngle()-4);
        }else if(leftPressed){
            player.getBody().setTransform(player.getBody().getPosition(),player.getBody().getAngle()+4);
        }

    }
}
