package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import Classes.Actors.BorderBar;
import Classes.Controller.Controls;
import Classes.Actors.Player;
import Classes.DataBase.DataBase;
import Classes.Others.AsteroidManager;


/** This is the main class of Ultra Mega Ultimate SPACE DEFENDER of the Galaxy 4, an addicting game that with make you lose your mind playing
 *
 * @author AlexCantos
 * @version 1.0
 */
public class Umusdotg4 extends ApplicationAdapter {
    private int score;
    private int timer;
    private BitmapFont font;
    private GlyphLayout gl;
    private Stage stage;
    private Player p;
    private Controls controls;
    private World world;
    private TextureRegion background;
    private AsteroidManager am;
    private SpriteBatch s;
    private DataBase db;
    private Music music;
    //this is the conversion factor between pixel and world meters
    public final static float toMeter=1/12f;

    /**Constructor that creates a database to access to higherScore
     * @param dataBase, from where to access to local database
     */
        public Umusdotg4(DataBase dataBase){
            this.db=dataBase;
        }

    /**Called upon creation, in instances all objectos needed to the game
     *
     */
    @Override
        public void create () {

         //Definitions
            //screen size is reduced that one pixel sizes 1/12 meters, to scalate the world size, allowing box2d to work properly
            StretchViewport screen= new StretchViewport(Gdx.graphics.getWidth()*toMeter,Gdx.graphics.getHeight()*toMeter);
            stage=new Stage(screen);
            s= new SpriteBatch();
            music= Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
            music.setLooping(true);
            music.setVolume(0.8f);
            music.play();

            world= new World(new Vector2(0, 0), true);

            background = new TextureRegion(new Texture  ("Others/background.png"));
            p= new Player(world);

            am= new AsteroidManager(world);
            font= new BitmapFont(Gdx.files.internal("Others/console.fnt"),Gdx.files.internal("Others/console.png"),false);
            font.getData().setScale(8);
            gl= new GlyphLayout();

            controls= new Controls(p);
            addActors();
            manageCollision();
            multiplexControls();
            setScreenCollision();


            score=0;
            timer=0;

	}

    /**Called default 60 times per second, it renders all visuals and checks collisions and physics
     */
	@Override
	public void render () {

        scoreUp();

        //SpriteBatch that prins background and score
        s.begin();
	    s.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    gl.setText(font,""+score);
        font.setColor(26/255f, 126/255f, 9/255f, 0.3f);
        font.draw(s, ""+score, Gdx.graphics.getWidth()/2- gl.width / 2, Gdx.graphics.getHeight()/2+font.getCapHeight()/2);

        /* Poor try to implement collision particles, pending to review
         am.particleColisionDrawer(s);
         */
        s.end();


        //AsteroidManager generates and manages asteroids
        am.generateAsteroidField();
        controls.act(s);

        //Check needed for the proper working of Thruster Particles
        if(!(Controls.rightPressed&&Controls.leftPressed)){
            p.getPf().getEmitters().first().reset();
        }

        //world calculates physics and stage draws all actors
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        stage.act();
        stage.draw();

	}

    /**
     * Stops music when UMUSDOTG4 not in foreground
     */
	@Override
    public void pause(){
	    music.pause();
    }
    /**
     * restarts music when UMUSDOTG4 back to foreground
     */
    @Override
    public void resume(){
        music.play();
    }

    /**onDispose, destroy all objects
     */
	@Override
	public void dispose (){
	    music.dispose();
        font.dispose();
        s.dispose();
		stage.dispose();
		world.dispose();
	}

    /**
     * Manages score gain, incrementing it 7points every 0.5seconds
     */
	public void scoreUp(){
            if(timer==30){
                score+=7;
                timer=0;
            }else{
                timer++;
            }
    }

    /**Creates 4 invisible bars that prevents player to exit from screen
     *
     */
    public void setScreenCollision(){
        stage.addActor(new BorderBar(0,0,(int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),1,world));
        stage.addActor(new BorderBar(0,0,1,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),world));
        stage.addActor(new BorderBar((int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),0,1,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),world));
        stage.addActor(new BorderBar(0,(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter),(int)(Gdx.graphics.getWidth()*Umusdotg4.toMeter),1,world));

    }

    /**Add all actors to Stage instance
     *
     */
    public void addActors(){
        stage.addActor(p);
        stage.addActor(am.getAsteroidActors());
        Group controlActors= new Group();
        controlActors.addActor(controls.getLeft().getBut());
        controlActors.addActor(controls.getRight().getBut());
        stage.addActor(controlActors);
    }

    /**
     * Creates and sets the input multiplexer to manage screen touch controls
     */
    public void multiplexControls(){
        InputMultiplexer multiplex= new InputMultiplexer();
        multiplex.addProcessor(controls.getLeft());
        multiplex.addProcessor(controls.getRight());
        Gdx.input.setInputProcessor(multiplex);
    }

    /**
     * Adds listener on world that finishes the game and sets new highScore if needed when player collides with asteroids
     */
    public void manageCollision(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                //Needed to check both posibilities because not always player would be the fixtureA of the collision
                if(((contact.getFixtureA()==p.getBody().getFixtureList().first()) && (contact.getFixtureB().getBody().getType()!=BodyDef.BodyType.StaticBody))
                || ((contact.getFixtureB()==p.getBody().getFixtureList().first()) && (contact.getFixtureA().getBody().getType()!=BodyDef.BodyType.StaticBody))) {

                    if(score>db.getHigherScore().getScore()){
                        db.savenewHighScore(score);
                    }
                //Sets Controls to false to avoid buttons to be activated with a new game before a death
                    Controls.rightPressed=false;
                    Controls.leftPressed=false;
                    Music mus=Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
                    mus.play();
                    Gdx.app.exit();
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }
}
