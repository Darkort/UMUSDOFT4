package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Classes.Actors.Asteroid;
import Classes.Actors.BorderBar;
import Classes.Controller.Button;
import Classes.Controller.Controls;
import Classes.Actors.Player;
import Classes.Others.AsteroidManager;

public class MyGdxGame extends ApplicationAdapter {
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


    //TODO MOVEMENTSPEED por culpa del viewport/cap de box2d,THRUSTERS,
    
        @Override
        public void create () {

         /*   b2dr= new Box2DDebugRenderer();
            camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            camera.position.x=0+Gdx.graphics.getWidth()/2;
            camera.position.y=0+Gdx.graphics.getHeight()/2;
         */

         //Definitions
            stage=new Stage(new ScreenViewport());
            world= new World(new Vector2(0, 0), true);
            background = new TextureRegion(new Texture  ("Others/background.png"));
            p= new Player(world);
            controls= new Controls(p);
            am= new AsteroidManager(world,stage,p);
            font= new BitmapFont(Gdx.files.internal("Others/console.fnt"),Gdx.files.internal("Others/console.png"),false);
            font.getData().setScale(8);
            gl= new GlyphLayout();
            addActors();
            am.manageCollision();
            multiplexControls();
            setScreenCollision();
            s= new SpriteBatch();

            score=0;
            timer=0;

	}

	@Override
	public void render () {
        //camera.update();
        // stage.setDebugAll(true);
        // b2dr.render(world, camera.combined);

        scoreUp();

        s.begin();
	    s.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    gl.setText(font,""+score);
        font.setColor(26/255f, 126/255f, 9/255f, 0.3f);
        font.draw(s, ""+score, Gdx.graphics.getWidth()/2- gl.width / 2, Gdx.graphics.getHeight()/2+font.getCapHeight()/2);
        s.end();

        am.generateAsteroidField();
        controls.act();
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
                score+=3;
                timer=0;
            }else{
                timer++;
            }
    }

    public void setScreenCollision(){
        stage.addActor(new BorderBar(0,0,Gdx.graphics.getWidth(),1,world));
        stage.addActor(new BorderBar(0,0,1,Gdx.graphics.getHeight(),world));
        stage.addActor(new BorderBar(Gdx.graphics.getWidth(),0,1,Gdx.graphics.getHeight(),world));
        stage.addActor(new BorderBar(0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),1,world));

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
