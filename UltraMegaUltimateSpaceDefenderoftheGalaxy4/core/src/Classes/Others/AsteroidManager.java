package Classes.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Umusdotg4;

import java.util.ArrayList;
import java.util.Random;

import Classes.Actors.Asteroid;
import Classes.Actors.Player;

public class AsteroidManager {
    public ArrayList<Asteroid> asteroids= new ArrayList<Asteroid>();
    Random rd= new Random();
    public Group asteroidActors= new Group();
    public int generationRate=40;
    public int maxSpeed;
    public int minSpeed;
    public int angle;
    public Vector2 vector;
    public World w;
    public Stage s;
    public Player p;
    public SpriteBatch sb;

     float x;
     float y;
     int speed;
     int posX;
     int posY;
     int posValue;


    public AsteroidManager(World w, Stage s, Player p,SpriteBatch sb){
        this.w=w;
        this.s=s;
        this.p=p;
        maxSpeed=80;
        minSpeed=20;
        angle=315;
        this.sb=sb;

    }

    public void generateAsteroidField(){

         x= (float)Math.cos(Math.toRadians(angle));
         y= (float)Math.sin(Math.toRadians(angle));
         speed= rd.nextInt((maxSpeed-minSpeed)+minSpeed);

        vector = new Vector2(x*speed,y*speed);


         posValue=rd.nextInt((int)((Gdx.graphics.getWidth()*Umusdotg4.toMeter)));
         posX= (int)-(Gdx.graphics.getWidth()*0.5*Umusdotg4.toMeter)+posValue;
         posY=(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter/4 + posValue);

        if(rd.nextInt(generationRate)==1){
            asteroids.add(new Asteroid(posX,posY, vector,w));
            asteroidActors.addActor(asteroids.get(asteroids.size()-1));
        }


        deleteErrants();

    }

    public void deleteErrants(){
        for(Asteroid a: asteroids){
            if(a.getY()<0-a.diameter){
                a.remove();
            }else if(a.getX()>Gdx.graphics.getWidth()*Umusdotg4.toMeter+a.diameter){
                a.remove();
            }
        }
    }
    public void manageCollision(){
        w.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if((contact.getFixtureA()==p.getBody().getFixtureList().first()) && (contact.getFixtureB().getBody().getType()!=BodyDef.BodyType.StaticBody)) {
                    p.setVisible(false);

                }else if((contact.getFixtureB()==p.getBody().getFixtureList().first()) && (contact.getFixtureA().getBody().getType()!=BodyDef.BodyType.StaticBody)) {
                    p.setVisible(false);

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

    public void particleColisionDrawer(SpriteBatch sb){

            ParticleEffect colision = new ParticleEffect();
            colision.load(Gdx.files.internal("Others/collision.p"), Gdx.files.internal("Others"));
            //colision.setPosition(contact.getWorldManifold().getPoints()[0].x,contact.getWorldManifold().getPoints()[0].y);
            colision.setPosition(0,0);
            colision.scaleEffect(3);
            colision.draw(sb,Gdx.graphics.getDeltaTime());



    }
}
