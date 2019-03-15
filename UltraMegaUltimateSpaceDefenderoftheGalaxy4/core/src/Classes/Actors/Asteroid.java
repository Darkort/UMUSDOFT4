package Classes.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Umusdotg4;

import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends Actor {

    private final static ArrayList<Sprite> sprites= new ArrayList<Sprite>();
    Random rd;
    private Sprite sprite;
    World world;
    BodyDef bd;
    Body body;
    public int diameter;

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
        body.setAngularVelocity((float)Math.toRadians(new Random().nextInt(120)));

        if(new Random().nextInt(2)==0){
            body.setAngularVelocity(-body.getAngularVelocity());
        }

        FixtureDef fd = new FixtureDef();
        CircleShape cs= new CircleShape();
        cs.setRadius((float) (diameter/2-diameter*0.05));
        fd.filter.categoryBits = 0x0004;
        fd.filter.maskBits= 0x0002 | 0x0004;
        fd.shape= cs;
        fd.restitution=0;
        body.createFixture(fd);


    }

    private void createSprite(){

        sprites.add(new Sprite(new Texture("Asteroids/as1.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as2.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as3.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as4.png")));
        sprites.add(new Sprite(new Texture("Asteroids/as7.png")));



        this.sprite= sprites.get(rd.nextInt(sprites.size()));

        this.diameter=(int)(rd.nextInt((300-100)+100)*Umusdotg4.toMeter);

        if (diameter<50*Umusdotg4.toMeter){
            this.diameter=30;
        }

        sprite.setBounds(this.getX(),this.getY(),diameter,diameter);
        sprites.clear();
    }

    public void draw(Batch batch, float parentAlpha){
        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation((float)Math.toDegrees(body.getAngle()));

        sprite.setOriginCenter();

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        sprite.setPosition(body.getPosition().x-diameter/2,body.getPosition().y-diameter/2);
        sprite.draw(batch);
    }









}
