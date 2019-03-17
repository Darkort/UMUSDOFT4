package Classes.Controller;


import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Umusdotg4;

/**
 *  Listens the touch of a given button actor
 *  @author AlexCantos
 *  * @version 1.0
 */
public class ButtonListener implements InputProcessor {

    private Button but;

    /**
     * Constructor
     * @param b button actor
     */
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

    /**
     * Checks if the touch is inside actor boundings to activate Control auxiliar variables and ups alpha of the button sprite
     * @param screenX X position of the touch
     * @param screenY Y position of the touch
     * @param pointer to be honest, im not sure what this parameter is
     * @param button  Idem
     * @return
     */

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)) {

            if(but.isLeft()){
                Controls.leftPressed=true;
            }else{
                Controls.rightPressed=true;
            }
            but.getSprite().setAlpha(0.20f);
        }
        return false;
    }

    /**
     * resets variables when the touch is finished
     * @param screenX X position of the touch up
     * @param screenY Y position of the touch up
     * @param pointer to be honest, im not sure what this parameter is
     * @param button Idem
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(but.isLeft() && but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)){
            Controls.leftPressed=false;
            but.getSprite().setAlpha(0.08f);
        }else if(!but.isLeft() && but.getSprite().getBoundingRectangle().contains(screenX*Umusdotg4.toMeter,screenY*Umusdotg4.toMeter)){
            Controls.rightPressed=false;
            but.getSprite().setAlpha(0.08f);
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
