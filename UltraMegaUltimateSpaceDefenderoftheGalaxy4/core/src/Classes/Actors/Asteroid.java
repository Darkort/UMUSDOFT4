package Classes.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends Actor {
    private static ArrayList<Sprite> sprites= new ArrayList<Sprite>();
    private Sprite sprite;
    World world;
    BodyDef bd;
    Body body;
    public int diameter;

    public Asteroid(int x,int y, Vector2 direction,World w){
        this.setX(x);
        this.setY(y);

        createSprite();

        this.world= w;
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        bd= new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.set(getX(),getY());
        body= world.createBody(bd);
        body.setTransform(getX(),getY(),0);

        body.setLinearVelocity(direction);
        body.setAngularVelocity(new Random().nextInt(150));

        FixtureDef fd = new FixtureDef();
        CircleShape cs= new CircleShape();
        cs.setRadius(diameter/2);
        fd.shape= cs;

        body.createFixture(fd);


    }

    private void createSprite(){

        sprites.add(new Sprite(new Texture("Asteroids/a1.png")));
        sprites.add(new Sprite(new Texture("Asteroids/a2.png")));
        sprites.add(new Sprite(new Texture("Asteroids/a3.png")));
        sprites.add(new Sprite(new Texture("Asteroids/a4.png")));

        Random rd= new Random();

        this.sprite= sprites.get(rd.nextInt(sprites.size()));

        this.diameter=(rd.nextInt(150-50)+50);
        sprite.setBounds(this.getX(),this.getY(),diameter,diameter);
        sprite.setOrigin(this.getWidth()/2,this.getHeight()/2);
        sprites.clear();
    }

    public void draw(Batch batch, float parentAlpha){
        sprite.setRotation(body.getAngle());
        sprite.setPosition(body.getPosition().x,body.getPosition().y);
        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation(body.getAngle());
        sprite.draw(batch);
    }









}
