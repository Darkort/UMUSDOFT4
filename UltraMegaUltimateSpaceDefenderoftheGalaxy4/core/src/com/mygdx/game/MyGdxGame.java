package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Classes.Controller.Button;
import Classes.Controller.Controls;
import Classes.Actors.Player;

public class MyGdxGame extends ApplicationAdapter {
	Stage stage;
	Button left;
    Controls controls;
    World world;


    // TODO fisicas y COLISIONES EN EL CONTROL concurrente
    
        @Override
        public void create () {
            stage=new Stage(new ScreenViewport());
            world= new World(new Vector2(0, 0), true);

            Player player= new Player(world);
            stage.addActor(player);
            controls= new Controls(player);


            stage.addActor(controls.getLeft());
            stage.addActor(controls.getRight());
            stage.addListener(controls.getRight().getListeners().first());
            stage.addListener(controls.getLeft().getListeners().first());
            Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render () {
	    Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
