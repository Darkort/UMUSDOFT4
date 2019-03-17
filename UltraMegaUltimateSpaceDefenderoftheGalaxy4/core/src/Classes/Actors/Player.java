package Classes.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Umusdotg4;

/**
 * Actor controlled by the player
 * @author AlexCantos
 * @version 1.0
 */
public class Player extends Actor {
    private Sprite sprite;
    private World world;
    private BodyDef bd;
    private Body body;
    private ParticleEffect pf;


    /**
     * Sets this actor, is sprite and his body in the given world
     * @param w
     */
    public Player(World w){
        this.world= w;
        this.setX(Gdx.graphics.getWidth()*Umusdotg4.toMeter/2);
        this.setY(Gdx.graphics.getHeight()*Umusdotg4.toMeter/2);

        sprite= new Sprite(new Texture(Gdx.files.internal("Ships/player.png")));
        sprite.setBounds(this.getWidth(),this.getHeight(),88*Umusdotg4.toMeter,83*Umusdotg4.toMeter);
        sprite.setOriginCenter();

        bd= new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(this.getWidth(),this.getHeight());

        body= world.createBody(bd);
        body.setTransform(getX(),getY(),0);
        body.setFixedRotation(true);
        FixtureDef fd = new FixtureDef();

        //Balance fixes to manage player behavior with the world and inertia
        fd.restitution=0.5f;
        fd.friction=0.1f;
        body.setLinearDamping(0.3f);

        // Rectangle body that contains the sprite
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]=new Vector2(-sprite.getWidth()/2,-sprite.getHeight()/2);
        vertices[1]=new Vector2(sprite.getWidth()/2,-sprite.getHeight()/2);
        vertices[2]=new Vector2(sprite.getWidth()/2,sprite.getHeight()/2);
        vertices[3]=new Vector2(-sprite.getWidth()/2,sprite.getHeight()/2);
        shape.set(vertices);
        fd.shape = shape;
        // collision mask to manage with which actors can collide
        fd.filter.categoryBits = 0x0002;
        body.createFixture(fd);
        shape.dispose();

        //Sets the Thruster particle effects
        pf= new ParticleEffect();
        pf.load(Gdx.files.internal("Others/thruster.p"),Gdx.files.internal("Others"));
        pf.scaleEffect(0.5f,0.5f,1);
        pf.setPosition((this.getX())/Umusdotg4.toMeter,this.getY()/Umusdotg4.toMeter);
    }

    //updates position of the actor and sprite to follow the body and print the sprite
    public void draw(Batch batch, float parentAlpha){

        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation((float)Math.toDegrees(body.getAngle()));

        sprite.setOriginCenter();

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);

        sprite.draw(batch);
    }

    /**
     * Get the body of the player
     * @return body
     */
    public Body getBody() {
        return body;
    }

    /**
     * gets the particle effect to the thrusters
     * @return particle effects
     */
    public ParticleEffect getPf() {
        return pf;
    }

    /**
     * Applies a rotation(degrees) on the player
     * @param degrees of the rotation
     */
    public void rotate(int degrees){
        body.setTransform(body.getPosition(),body.getAngle()-(float)( Math.toRadians(degrees)));
    }

    /**
     * Applies a linear impulse to the player and starts the thruster particle emmiter
     * @param power of the force
     * @param s spritebatch where the thrusters will be drawed
     */
    public void impulsate(int power, SpriteBatch s){
        //particle stuff
        this.pf.setPosition(this.getX()/Umusdotg4.toMeter,this.getY()/Umusdotg4.toMeter);
        this.pf.getEmitters().first().getAngle().setHigh(this.getRotation()-180);
        this.pf.getEmitters().first().getAngle().setLow(this.getRotation()-180);
        s.begin();
        this.pf.draw(s,Gdx.graphics.getDeltaTime());
        s.end();

        //translates body angle to X and Y component to multiply it by the force
        float x = (float) Math.cos(body.getAngle());
        float y = (float) Math.sin(body.getAngle());
        body.applyForceToCenter( new Vector2(x*power,y*power), true);
    }

}
