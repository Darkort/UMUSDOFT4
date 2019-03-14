package Classes.Controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Umusdotg4;


public class ButtonListener implements InputProcessor {

    public Button but;

    public ButtonListener(Button b){
        but=b;
    }

    public Button getBut() {
        return but;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)) {

            if(but.isLeft()){
                Controls.leftPressed=true;
            }else{
                Controls.rightPressed=true;
            }
            but.getSprite().setAlpha(0.30f);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(but.isLeft() && but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)){
            Controls.leftPressed=false;
            but.getSprite().setAlpha(0.1f);
        }else if(!but.isLeft() && but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)){
            Controls.rightPressed=false;
            but.getSprite().setAlpha(0.1f);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
