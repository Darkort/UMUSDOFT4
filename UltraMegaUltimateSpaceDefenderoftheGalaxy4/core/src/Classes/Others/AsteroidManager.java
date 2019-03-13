package Classes.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

import Classes.Actors.Asteroid;
import Classes.Actors.Player;

public class AsteroidManager {
    public ArrayList<Asteroid> asteroids= new ArrayList<Asteroid>();
    public int generationRate=90;
    public int maxSpeed;
    public int minSpeed;
    public int angle;
    public Vector2 vector;
    public World w;
    public Stage s;
    public Player p;


    public AsteroidManager(World w, Stage s, Player p){
        this.w=w;
        this.s=s;
        this.p=p;
        maxSpeed=150;
        minSpeed=20;
        angle=315;

    }

    public void generateAsteroidField(){
        Random rd= new Random();
        float x= (float)Math.cos(Math.toRadians(angle));
        float y= (float)Math.sin(Math.toRadians(angle));
        int speed= rd.nextInt(maxSpeed);

        vector = new Vector2(x*speed,y*speed);


        int posValue=rd.nextInt(Gdx.graphics.getHeight()*2);
        int posX= (int)-(Gdx.graphics.getWidth()*0.5)+posValue;
        int posY=Gdx.graphics.getHeight()/2 + posValue;

        if(rd.nextInt(generationRate)==1){
            asteroids.add(new Asteroid(posX,posY, vector,w));
            s.addActor(asteroids.get(asteroids.size()-1));
        }

        deleteErrants();

    }

    public void deleteErrants(){
        for(Asteroid a: asteroids){
            if(a.getY()<0-Gdx.graphics.getWidth()/4){
                a.remove();
            }else if(a.getX()>Gdx.graphics.getWidth()+Gdx.graphics.getWidth()/4){
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
}
