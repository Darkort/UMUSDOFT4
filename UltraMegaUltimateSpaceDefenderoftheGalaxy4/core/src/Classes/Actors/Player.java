package Classes.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Umusdotg4;

public class Player extends Actor {
    public Sprite sprite;
    World world;
    BodyDef bd;
    Body body;
    public ParticleEffect pf;

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

        fd.restitution=0.5f;
        fd.friction=0.1f;
        //fd.density=0.5f;
        body.setLinearDamping(0.3f);


        PolygonShape shape = new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]=new Vector2(-sprite.getWidth()/2,-sprite.getHeight()/2);
        vertices[1]=new Vector2(sprite.getWidth()/2,-sprite.getHeight()/2);
        vertices[2]=new Vector2(sprite.getWidth()/2,sprite.getHeight()/2);
        vertices[3]=new Vector2(-sprite.getWidth()/2,sprite.getHeight()/2);

        shape.set(vertices);
        fd.shape = shape;
        fd.filter.categoryBits = 0x0002;
        //fd.filter.maskBits = 0x0004 & 0x0008;

        body.createFixture(fd);

        pf= new ParticleEffect();
        pf.load(Gdx.files.internal("Others/thruster.p"),Gdx.files.internal("Others"));
        pf.scaleEffect(0.5f,0.5f,1);
        pf.setPosition((this.getX())/Umusdotg4.toMeter,this.getY()/Umusdotg4.toMeter);

    }

    public void draw(Batch batch, float parentAlpha){

        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation((float)Math.toDegrees(body.getAngle()));

        sprite.setOriginCenter();

        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);

        sprite.draw(batch);
    }

    public Body getBody() {
        return body;
    }

    public void rotate(int degrees){
        body.setTransform(body.getPosition(),body.getAngle()-(float)( Math.toRadians(degrees)));
    }

    public void impulsate(int power, SpriteBatch s){

        this.pf.setPosition(this.getX()/Umusdotg4.toMeter,this.getY()/Umusdotg4.toMeter);
        this.pf.getEmitters().first().getAngle().setHigh(this.getRotation()-180);
        this.pf.getEmitters().first().getAngle().setLow(this.getRotation()-180);
        s.begin();
        this.pf.draw(s,Gdx.graphics.getDeltaTime());
        s.end();

        float x = (float) Math.cos(body.getAngle());
        float y = (float) Math.sin(body.getAngle());
        body.applyForceToCenter( new Vector2(x*power,y*power), true);
    }

}
