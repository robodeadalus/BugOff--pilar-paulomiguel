/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version April 25, 2023
**/
/*
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.
I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.

This class is for creating the bullet. It creates the bullet
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


public class Bullet extends Entity{

    private boolean canShoot;
    private double angle;
    private int counter;
    private BufferedImage bullet;
    
    /*
     * This constructor takes in the x, y, and speed of the bullet. It also initializes the attributes of the bullet
     */
    public Bullet(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        health = 1;
        size = 30;
        canShoot = true;
        counter = 0;

        getBulletImage();
    }

    /*
     * This is for getting the image of the bullet. It returns an IOException error if it encounters an error
     * https://javarevisited.blogspot.com/2011/12/read-write-image-in-java-example.html#axzz81mFzsmo3
     */
    public void getBulletImage(){

        try {
            bullet = ImageIO.read(getClass().getResourceAsStream("images/bullet.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * This method is for drawing the image of the bullet. It draws the image of the bullet
     */
    public void draw(Graphics2D g2d){
        BufferedImage image = bullet;

        if(health == 1){
            g2d.drawImage(image, x , y, 40, 70, null);
        }
    }
    
    /*
     * This method is for setting the angle at which the bullet will shoot. It sets the direction of the bullet
     */
    public void setAngle(double playerX, double playerY, double mouseX, double mouseY) {
        if(canShoot){
            double dx = mouseX - playerX;
            double dy = mouseY - playerY;
            angle = Math.atan2(dy, dx);
        }
    }

    /*
     * This method is for determining the path of the bullet. It uses math logic to determine the path
     */
    public void bulletShotPath(){
        x += (double)(speed * Math.cos(angle));
        y += (double)(speed * Math.sin(angle));

        if(health == 0){
            counter++;
            if(counter == 50){
                health = 1;
                counter = 0;
            }
        }
    }


    /*
     * This is for determining if the bullet hit the boss. It decrements the hp of the bullet.
     */
    public void bulletHits(){
        health = 0;
    }

    /*
     * This is for getting the health of the bullet. IT returns the health.
     */
    public int getHealth(){
        return health;
    }

    /*
     * This is for getting the size of the bullet. It returns the size
     */
    public int getBulletSize(){
        return size;
    } 
    
    /*
     * This gets the x position of the bullet. It returns the x
     */
    public int getBulletX(){
        return x;
    }

    /*
     * This gets the y position of the bullet. It returns the y.
     */
    public int getBulletY(){
        return y;
    }

    /*
     * This sets the x position of the bullet. It sets the bullets x position
     */
    public void setBulletX(int n){
        if(canShoot)
            x = n;
    }

    /*
     * This sets the y position of the bullet. It sets the bullets y position.
     */
    public void setBulletY(int n){
        if(canShoot)    
            y = n;
    }

    /*
     * This accepts a boolean argument saying that it can shoot. Based on that boolean it determines if it can shoot
     */
    public void setCanShoot(boolean arg){
        canShoot = arg;
    }

    /*
     * This returns a boolean dictating if the bullet can be shot. It returns canShoot.
     */
    public boolean canItShoot(){
        return canShoot;
    }
   
} 