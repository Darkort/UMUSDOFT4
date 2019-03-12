package Classes.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    private Sprite sprite;
    World world;
    BodyDef bd;
    Body body;


    public Player(World w){
        this.world= w;
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);
        this.setX(Gdx.graphics.getWidth()/2);
        this.setY(Gdx.graphics.getHeight()/2);
        sprite= new Sprite(new Texture(Gdx.files.internal("Ships/player.png")));
        sprite.setBounds(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,88,83);

        bd= new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(getX(),getY());
        body= world.createBody(bd);
        body.setTransform(getX(),getY(),0);


        FixtureDef fd = new FixtureDef();
        fd.restitution=0.1f;
       fd.friction=0.005f;
        //fd.density=0.00005f;



        PolygonShape shape = new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]=new Vector2(0,0);
        vertices[1]=new Vector2(sprite.getWidth(),0);
        vertices[2]=new Vector2(sprite.getWidth(),sprite.getHeight());
        vertices[3]=new Vector2(0,sprite.getHeight());

        shape.set(vertices);
        fd.shape = shape;
        body.createFixture(fd);


    }

    public void draw(Batch batch, float parentAlpha){
        sprite.setRotation(body.getAngle());
        sprite.setPosition(body.getPosition().x,body.getPosition().y);
        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation(body.getAngle());
        sprite.draw(batch);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
