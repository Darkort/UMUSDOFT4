package Classes.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Umusdotg4;

import Classes.Actors.Player;


/**
 * Class that manages screen button and listeners to make player to response
 * @author AlexCantos
 * @version 1.0
 */
public class Controls {
    private Player player;
    private ButtonListener left;
    private ButtonListener right;

    //auxiliar
    public static boolean leftPressed;
    public static boolean rightPressed;


    /**
     * Constructor, receives the player that will act upon touch
     * @param player that moves
     */
    public Controls(Player player){
        this.player=player;
        right= new ButtonListener(new Button(false));
        left= new ButtonListener(new Button(true));


    }

    /**
     * get Listener of right button
     * @return the Buttonlistener
     */
    public ButtonListener getRight() {
        return right;
    }
    /**
     * get Listener of left button
     * @return the ButtonListener
     */
    public ButtonListener getLeft() {
        return left;
    }

    /**
     * rotates and apply forces to the player upon button touch
     * @param s spritebatch where the Thruster particles will be printed
     */
    public void act(SpriteBatch s){

        if(rightPressed && leftPressed){
            player.impulsate(50,s);
        }else if(rightPressed){
            player.rotate(7);
        }else if(leftPressed){
            player.rotate(-7);
        }
    }
}
