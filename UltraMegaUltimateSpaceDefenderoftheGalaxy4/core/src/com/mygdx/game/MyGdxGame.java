package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Classes.Actors.Asteroid;
import Classes.Controller.Button;
import Classes.Controller.Controls;
import Classes.Actors.Player;
import Classes.Others.AsteroidManager;

public class MyGdxGame extends ApplicationAdapter {
	Stage stage;
	Button left;
    Controls controls;
    World world;
    SpriteBatch spriteBatch;
    TextureRegion background;
    AsteroidManager am;


    // TODO FIXTURE RADIUS
    
        @Override
        public void create () {
            stage=new Stage(new ScreenViewport());
            world= new World(new Vector2(0, 0), true);
            background = new TextureRegion(new Texture  ("Others/background.png"));
            Player player= new Player(world);
            stage.addActor(player);
            controls= new Controls(player);
            am= new AsteroidManager(world,stage);

            Asteroid as= new Asteroid((int)player.getX()/2,(int)player.getY(),new Vector2(200,0),world);
            spriteBatch= new SpriteBatch();

            stage.addActor(controls.getLeft().getBut());
            stage.addActor(controls.getRight().getBut());
            stage.addActor(as);

            InputMultiplexer multiplex= new InputMultiplexer();
            multiplex.addProcessor(controls.getLeft());
            multiplex.addProcessor(controls.getRight());
            Gdx.input.setInputProcessor(multiplex);

	}

	@Override
	public void render () {
        spriteBatch.begin();
	    spriteBatch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    spriteBatch.end();
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
}
