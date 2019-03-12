package Classes.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {

    Sprite sprite;
    boolean isLeft;
    float x;



    public Button(boolean isLeft) {
        //if isLeft=1, its the left side, if isLeft=0, its the right side

        sprite = new Sprite(new Texture(Gdx.files.internal("Buttons/button.png")));
        sprite.setAlpha(0.3f);

        this.isLeft=isLeft;
        if (isLeft == true) {
            x= (float)0.03;

        } else if (isLeft == false) {
            x= (float)0.67;
        }
        sprite.setBounds((float) (Gdx.graphics.getWidth() * x), (float) (Gdx.graphics.getHeight() * 0.03), (float) (Gdx.graphics.getWidth() * 0.30), (float) (Gdx.graphics.getHeight() * 0.93));

        this.addListener(new ButtonListener(this));
    }
    public void draw(Batch batch, float parentAlpha){
        sprite.draw(batch);
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
