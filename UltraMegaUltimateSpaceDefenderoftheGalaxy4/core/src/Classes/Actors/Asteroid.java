package Classes.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Umusdotg4;

import java.util.ArrayList;
import java.util.Random;

/**
 * Actors that wil collide with the player
 * @author AlexCantos
 * @version 1.0
 */
public class Asteroid extends Actor {

    private final static ArrayList<Sprite> sprites= new ArrayList<Sprite>();
    private Random rd;
    private Sprite sprite;
    private World world;
    private BodyDef bd;
    private  Body body;
    private int diameter;

    /**
     * Sets asteroid position and initial movement vector
     * @param x X position
     * @param y Y position
     * @param direction vector2 with X and Y components of the movement
     * @param w world where body will act
     */
    public Asteroid(int x,int y, Vector2 direction,World w){
        this.setX(x);
        this.setY(y);


        rd= new Random();
        createSprite();

        this.world= w;

        bd= new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;

        bd.position.set(this.getX(),this.getY());
        body= world.createBody(bd);

        body.setFixedRotation(true);
        body.setLinearVelocity(direction);

        //Random angularSpeed
        body.setAngularVelocity((float)Math.toRadians(new Random().nextInt(120)));
        //Allows to rotate in both directions(Random class doesn't allow negative numbers)
        if(new Random().nextInt(2)==0){
            body.setAngularVelocity(-body.getAngularVelocity());
        }

        FixtureDef fd = new FixtureDef();
        CircleShape cs= new CircleShape();

        //sets circular body with a radius equals to sprite radius(minus a 5%, due to fitting to visuals)
        cs.setRadius((float) (diameter/2-diameter*0.05));
        fd.filter.categoryBits = 0x0004;
        fd.filter.maskBits= 0x0002 | 0x0004;
        fd.shape= cs;
        fd.restitution=0;
        body.createFixture(fd);


    }

    /**
     * get asteroid diameter
     * @return diameter
     */
    public int getDiameter() {
        return diameter;
    }
    /**
     * set asteroid diameter
     * @return diameter
     */

    /**
     * Creates sprite with random sprite between 5 and a random diameter between 100 and 300 pixels(later will be translate to world meters)
     */
    private void createSprite(){

        sprites.add(new Sprite(new Texture("Asteroids/as1.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as2.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as3.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as4.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as7.png")));
        //random sprite
        this.sprite= sprites.get(rd.nextInt(sprites.size()));
        //random diametre
        this.diameter=(int)(rd.nextInt((300-100)+100)*Umusdotg4.toMeter);
        //avoiding the called "bullet asteroids" dat, for some reason spawns with less than 5 pixel diameter, beeing an unfair sad ending to the player
        if (diameter<50*Umusdotg4.toMeter){
            this.diameter=30;
        }

        sprite.setBounds(this.getX(),this.getY(),diameter,diameter);
        sprites.clear();
    }

    /**
     * updates actor and sprite with the body position and print sprite
     * @param batch where sprite will be drawed
     * @param parentAlpha of the drawing
     */
    public void draw(Batch batch, float parentAlpha){
        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation((float)Math.toDegrees(body.getAngle()));

        sprite.setOriginCenter();

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        sprite.setPosition(body.getPosition().x-diameter/2,body.getPosition().y-diameter/2);
        sprite.draw(batch);
    }









}
