package Classes.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Umusdotg4;
/**
 *  Actor that contains the sprite of the buttons
 *  @author AlexCantos
 *  * @version 1.0
 */
public class Button extends Actor {

    private Sprite sprite;
    private boolean isLeft;
    private float x;


    /**
     * Constructor that sets if it will be the left or right button
     * @param isLeft
     */
    public Button(boolean isLeft) {

        sprite = new Sprite(new Texture(Gdx.files.internal("Buttons/button.png")));
        sprite.setAlpha(0.08f);

        //if isLeft=1, its the left side, if isLeft=0, its the right side
        this.isLeft=isLeft;
        if (isLeft == true) {
            x= (float)0.03;

        } else if (isLeft == false) {
            x= (float)0.72;
            sprite.flip(true,false);
        }
        sprite.setBounds((Gdx.graphics.getWidth()*Umusdotg4.toMeter * x), (float) (Gdx.graphics.getHeight()*Umusdotg4.toMeter * 0.03), (float) (Gdx.graphics.getWidth()*Umusdotg4.toMeter * 0.25), (float) (Gdx.graphics.getHeight()*Umusdotg4.toMeter * 0.93));

    }

    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);

    }

    public boolean isLeft() {
        return isLeft;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
