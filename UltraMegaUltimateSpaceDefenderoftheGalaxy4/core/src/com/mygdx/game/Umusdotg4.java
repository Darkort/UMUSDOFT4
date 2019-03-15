package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import Classes.Actors.BorderBar;
import Classes.Controller.Controls;
import Classes.Actors.Player;
import Classes.Others.AsteroidManager;

public class Umusdotg4 extends ApplicationAdapter {
    int score;
    int timer;
    BitmapFont font;
    GlyphLayout gl;
	Stage stage;
	Player p;
    Controls controls;
    World world;
    TextureRegion background;
    AsteroidManager am;
    SpriteBatch s;

    private Camera camera;
    public Box2DDebugRenderer b2dr;
    public final static float toMeter=1/12f;



        @Override
        public void create () {
         /*   b2dr= new Box2DDebugRenderer();
            camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            camera.position.x=0+Gdx.graphics.getWidth()/2;
            camera.position.y=0+Gdx.graphics.getHeight()/2;
         */

         //Definitions
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StretchViewport screen= new StretchViewport(Gdx.graphics.getWidth()*toMeter,Gdx.graphics.getHeight()*toMeter);
            stage=new Stage(screen);
            s= new SpriteBatch();

            world= new World(new Vector2(0, 0), true);

            background = new TextureRegion(new Texture  ("Others/background.png"));
            p= new Player(world);

            am= new AsteroidManager(world,stage,p,s);
            font= new BitmapFont(Gdx.files.internal("Others/console.fnt"),Gdx.files.internal("Others/console.png"),false);
            font.getData().setScale(8);
            gl= new GlyphLayout();

            controls= new Controls(p);
            addActors();
            am.manageCollision();
            multiplexControls();
            setScreenCollision();


            score=0;
            timer=0;

	}

	@Override
	public void render () {
        //camera.update();
      // stage.setDebugAll(true);
         //b2dr.render(world, camera.combined);
        scoreUp();

        s.begin();
	    s.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    gl.setText(font,""+score);
        font.setColor(26/255f, 126/255f, 9/255f, 0.3f);
        font.draw(s, ""+score, Gdx.graphics.getWidth()/2- gl.width / 2, Gdx.graphics.getHeight()/2+font.getCapHeight()/2);
       // am.particleColisionDrawer(s);
        s.end();

        am.generateAsteroidField();
        controls.act(s);

        if(!(Controls.rightPressed&&Controls.leftPressed)){
            p.pf.getEmitters().first().reset();
        }

        stage.act();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        stage.draw();

	}
	
	@Override
	public void dispose () {
		stage.dispose();
		world.dispose();
	}


	public void scoreUp(){
            if(timer==30){
                score+=7;
                timer=0;
            }else{
                timer++;
            }
    }

    public void setScreenCollision(){
        stage.addActor(new BorderBar(0,0,(int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),1,world));
        stage.addActor(new BorderBar(0,0,1,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),world));
        stage.addActor(new BorderBar((int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),0,1,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),world));
        stage.addActor(new BorderBar(0,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),(int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),1,world));

    }
    public void addActors(){
        stage.addActor(p);
        stage.addActor(am.asteroidActors);
        Group controlActors= new Group();
        controlActors.addActor(controls.getLeft().getBut());
        controlActors.addActor(controls.getRight().getBut());
        stage.addActor(controlActors);
    }
    public void multiplexControls(){
        InputMultiplexer multiplex= new InputMultiplexer();
        multiplex.addProcessor(controls.getLeft());
        multiplex.addProcessor(controls.getRight());
        Gdx.input.setInputProcessor(multiplex);
    }
}
