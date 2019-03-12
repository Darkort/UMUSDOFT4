package Classes.Others;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

import Classes.Actors.Asteroid;

public class AsteroidManager {
    public ArrayList<Asteroid> asteroids= new ArrayList<Asteroid>();
    public int generationRate=600;
    public Vector2 direction;
    public World w;
    public Stage s;


    public AsteroidManager(World w,Stage s){
        this.w=w;
        this.s=s;
    }

    public void generateAsteroidField(){
        Random rd= new Random();
        float x= MathUtils.cos((float)(0.3*Math.PI));
        float y= MathUtils.sin((float)(0.3*Math.PI));
        direction= new Vector2(x*100,y*100);


        if(rd.nextInt(generationRate)==1){
            //int posX= ;
           // int posY= ;

            asteroids.add(new Asteroid(50,20,direction,w));
            s.addActor(asteroids.get(asteroids.size()-1));
        }


    }
}
