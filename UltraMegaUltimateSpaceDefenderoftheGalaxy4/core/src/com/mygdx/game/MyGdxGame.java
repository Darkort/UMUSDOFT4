package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
	Stage stage;
	Button left;
    Controls controls;
    World world;
    SpriteBatch spriteBatch;
    TextureRegion background;
    AsteroidManager am;


    private Camera camera;
    public Box2DDebugRenderer b2dr;


    // TODO UPPER BORDERBAR, MOVEMENTSPEED por culpa del viewport/cap de box2d,THRUSTERS,
    
        @Override
        public void create () {

         /*   b2dr= new Box2DDebugRenderer();
            camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            camera.position.x=0+Gdx.graphics.getWidth()/2;
            camera.position.y=0+Gdx.graphics.getHeight()/2;
*/
            stage=new Stage(new ScreenViewport());
            world= new World(new Vector2(0, 0), true);
            background = new TextureRegion(new Texture  ("Others/background.png"));
            Player player= new Player(world);
            stage.addActor(player);
            controls= new Controls(player);
            am= new AsteroidManager(world,stage,player);
            am.manageCollision();

            spriteBatch= new SpriteBatch();



            stage.addActor(controls.getLeft().getBut());
            stage.addActor(controls.getRight().getBut());

            InputMultiplexer multiplex= new InputMultiplexer();
            multiplex.addProcessor(controls.getLeft());
            multiplex.addProcessor(controls.getRight());
            Gdx.input.setInputProcessor(multiplex);

            score=0;
            timer=0;

            setScreenCollision();
	}

	@Override
	public void render () {
        //camera.update();
        scoreUp();
        spriteBatch.begin();
	    spriteBatch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    spriteBatch.end();
	    am.generateAsteroidField();
	   // stage.setDebugAll(true);
        controls.act();
        stage.act();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
       // b2dr.render(world, camera.combined);
        stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		world.dispose();
	}


	public void scoreUp(){
            if(timer==60){
                score+=6;
                timer=0;
            }else{
                timer++;
            }
    }

    public void setScreenCollision(){
        stage.addActor(new BorderBar(0,0,Gdx.graphics.getWidth(),1,world));
        stage.addActor(new BorderBar(0,0,1,Gdx.graphics.getHeight(),world));
        stage.addActor(new BorderBar(Gdx.graphics.getWidth(),0,1,Gdx.graphics.getHeight(),world));
       // stage.addActor(new BorderBar(0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),100,world));

    }
}
