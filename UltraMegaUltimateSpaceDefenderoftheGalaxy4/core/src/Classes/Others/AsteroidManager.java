package Classes.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Umusdotg4;

import java.util.ArrayList;
import java.util.Random;

import Classes.Actors.Asteroid;
import Classes.Actors.Player;

/**Managess al creation and destroy of the asteroids to make a continuous asteroid field
 *
 * @author AlexCantos
 *  * @version 1.0
 */
public class AsteroidManager {
    //main attributes of the class
    private ArrayList<Asteroid> asteroids= new ArrayList<Asteroid>();
    private Group asteroidActors= new Group();
    private World w;

    //balancing values
    private int generationRate=30;
    private int maxSpeed=60;
    private int minSpeed=20;
    private int angle=315;

    //Auxiliar variables
    Random rd= new Random();
    private Vector2 vector;
     float x;
     float y;
     int speed;
     int posX;
     int posY;
     int posValue;

    /**
     * Constructor that sets the world where Asteroid Manager will act
     * @param w, the world
     */
    public AsteroidManager(World w){
        this.w=w;
    }

    /**
     * Gets the group of asteroids
     * @return asteroids
     */
    public Group getAsteroidActors() {
        return asteroidActors;
    }


    /**
     * Generates constantly asteroids that travels throught the screen and be deleted
     * These asteroids will be created according to a random speed within maxSpeed and minSpeed and the Asteroid Manager angle
     */
    public void generateAsteroidField(){
         //X and Y components of the given angle(in radians)
         x= (float)Math.cos(Math.toRadians(angle));
         y= (float)Math.sin(Math.toRadians(angle));

         //random Speed within given limits
         speed= rd.nextInt((maxSpeed-minSpeed)+minSpeed);

         //Movement vector of the asteroid
         vector = new Vector2(x*speed,y*speed);

         //Sets a random position on an imaginary line outside the screen, where the asteroid will spawn
         posValue=rd.nextInt((int)((Gdx.graphics.getWidth()*Umusdotg4.toMeter)));
         posX= (int)-(Gdx.graphics.getWidth()*0.5*Umusdotg4.toMeter)+posValue;
         posY=(int)(Gdx.graphics.getHeight()*Umusdotg4.toMeter/4 + posValue);

         //Generates asteroid with 1/30 rate (arround 2 per second)
         if(rd.nextInt(generationRate)==1){
              asteroids.add(new Asteroid(posX,posY, vector,w));
              asteroidActors.addActor(asteroids.get(asteroids.size()-1));
         }

        deleteErrants();

    }

    /**
     * Deletes all asteroids that have crossed across the screen to free memory
     */
    public void deleteErrants(){
        for(Asteroid a: asteroids){
            if(a.getY()<0-a.getDiameter()){
                a.remove();
            }else if(a.getX()>Gdx.graphics.getWidth()*Umusdotg4.toMeter+a.getDiameter()){
                a.remove();
            }
        }
    }

    /**
     * Unfinnished attempt to create particles upon asteroid collisions, unused
     * @param sb  where the asteroid sprites will be stored
     */
    public void particleColisionDrawer(SpriteBatch sb){

            ParticleEffect colision = new ParticleEffect();
            colision.load(Gdx.files.internal("Others/collision.p"), Gdx.files.internal("Others"));
            //colision.setPosition(contact.getWorldManifold().getPoints()[0].x,contact.getWorldManifold().getPoints()[0].y);
            colision.setPosition(0,0);
            colision.scaleEffect(3);
            colision.draw(sb,Gdx.graphics.getDeltaTime());



    }
}
