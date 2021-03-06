package Classes.Actors;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * Rectangle bars with collision that prevents player to scape from screen
 * @author AlexCantos
 * @version 1.0
 */
public class BorderBar extends Actor {
    private World w;
    private BodyDef bd;
    private Body body;


    /**
     * Sets position and size of the body
     * @param x X position
     * @param y Y position
     * @param width of the body
     * @param height of the body
     * @param w world where body will act
     */
    public BorderBar(int x, int y,int width, int height, World w){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.w=w;



        bd= new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;


        bd.position.set(this.getX(),this.getY());
        body= w.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.restitution= 0.8f;
        //Collision mask to collide with player but no with asteroids
        fd.filter.categoryBits = 0x0008;
        fd.filter.maskBits= 0x0002 | ~0x0008;

        //Rectangular body
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]=new Vector2(-this.getWidth(),-this.getHeight());
        vertices[1]=new Vector2(this.getWidth(),-this.getHeight());
        vertices[2]=new Vector2(this.getWidth(),this.getHeight());
        vertices[3]=new Vector2(-this.getWidth(),this.getHeight());
        shape.set(vertices);


        fd.shape = shape;

        body.createFixture(fd);

    }

}
