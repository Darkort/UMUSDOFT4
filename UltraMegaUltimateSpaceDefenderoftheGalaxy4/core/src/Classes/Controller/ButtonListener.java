package Classes.Controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;






public class ButtonListener extends InputListener {

    public Button but;

    public ButtonListener(Button b){
        but=b;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

        if(but.getSprite().getBoundingRectangle().contains(x,y)) {

            if(but.isLeft()){
                Controls.leftPressed=true;
            }else{
                Controls.rightPressed=true;
            }
            but.getSprite().setAlpha(1);
        }
        return true;
    }

    @Override
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        if(but.isLeft()){
            Controls.leftPressed=false;
        }else{
            Controls.rightPressed=false;
        }
        but.getSprite().setAlpha(0.3f);
    }
}
