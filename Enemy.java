/**
 * @author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
 * @version May 1, 2023
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

This java class is for the enemy. It extends entitiy and is for creating the fly enemy.

*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Enemy extends Entity {

    private boolean isCollidingPlayer, isVulnerable;
    private int counter, immunityFrames, moveCounter, phaseCounter;
    public BufferedImage fly1, fly2, fly1flash, fly2flash, temp;
    private int ySpeed, xSpeed, bounce; 
    private Background arena;

    /*
     * THis constructor takes in two arguments. It takes in the x and y positions of the fly
     */
    public Enemy(int x, int y) {
        isCollidingPlayer = false;
        health = 20;
        this.x = x;
        this.y = y;
        ySpeed = 10;
        xSpeed = 4;
        size = 150;
        moveCounter = 0;
        phaseCounter = 0;
        arena = new Background();
        isVulnerable = true;
        getEnemyImage();
    }

    /*
     * THis method is for getting the sprites of the fly. This includes its movement and invulnerability flashes.
     * Source: https://javarevisited.blogspot.com/2011/12/read-write-image-in-java-example.html#axzz81mFzsmo3
     */
    public void getEnemyImage() {

        try {
            fly1flash = ImageIO.read(getClass().getResourceAsStream("images/fly1flash.png"));
            fly2flash = ImageIO.read(getClass().getResourceAsStream("images/fly2flash.png"));
            fly1 = ImageIO.read(getClass().getResourceAsStream("images/fly1.png"));
            fly2 = ImageIO.read(getClass().getResourceAsStream("images/fly2.png"));
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * This draws the fly. It also creates invulnerability frames
     */
    public void draw(Graphics2D g2d) { // Add a white screen sprite to indicate a hit

        if(counter < 30){
            if(isVulnerable == false){
                immunityFrames++;
                if(immunityFrames >= 20)
                    temp = fly1;
                else
                    temp = fly1flash;
            }
            else{
                temp = fly1;
            }
            counter++;
        }
        else if(counter < 40){
            if(isVulnerable == false){
                immunityFrames++;
                if(immunityFrames >= 20)
                    temp = fly2flash;
                else
                    temp = fly2;
            }
            else{
                temp = fly2;
            }
            counter++;
        }
        else{
            counter = 0;
        }

        if(immunityFrames == 40){
            isVulnerable = true;
            immunityFrames = 0;
        }

            BufferedImage image = temp;

            g2d.drawImage(image, x, y, 200, 140, null);


    }

    /*
     * This sets the phase counter of the boss. It makes it so that they are synchronized
     */
    public void setPhaseCounter(int counter){
        phaseCounter = counter;
    }

    /*
     * This method is for one of the phases of the boss. It makes it do the Phase X dash
     */
    public void movePhaseXDash() {
        if(y <= 1000 && moveCounter < 4){
            x += 20;
            if (x >= 1300) {
                y += 250;
                x = -300;
                moveCounter++;
            }
        }
        else if(moveCounter == 4){
            x = 1400;
            y = 0;
            moveCounter++;
        }
        else if(y <= 1000 && moveCounter >= 5) {
            x -= 20;
            if(x <= -200){
                y += 250;
                x = 1400;
           }
        }
    }

    /*
     * This method is used to set the location of the fly. It uses it for the phase x dash
     */
    public void movePhaseX() {
        // put fly in upper left corner and end in lower right corer
        x += 20;
        if (x >= 1000) {
            y += 200;
            x = 0;
            if (x == 800 && y == 90) {
                x -= 5;
                if (x == 110 && y == 90) {
                    y -= 5;
                    if (x == 90 && y == 600) {
                        x += 5;
                    }
                }
            }
        }
    }

    /*
     * This movement phase is for the zigzag motion of the fly. It uses math logic
     */
    public void movePhaseZigZag(){
        y += ySpeed;
        x += xSpeed;
        if(y + getEnemyHeight() >= arena.getArenaY() + arena.getArenaHeight()){
            ySpeed *= -1;
            bounce++;
        }
        if(bounce >= 1 && y <= arena.getArenaY()){
            ySpeed *= -1;
        } 
    }

    /*
     * This method is used to determine what phase the enemy will do. It constantly runs in the gameplay loop
     */
    public void updateEnemyMovement(){

            phaseCounter++;
            System.out.println(phaseCounter);
            if (phaseCounter == 100) {
                setEnemyX(-100);
                setEnemyY(-100);
            } else if (phaseCounter > 100 && phaseCounter < 500) {
                movePhaseZigZag();
            }
    
            if (phaseCounter == 500) {
                setEnemyX(-100);
                setEnemyY(50);
            }
            if (phaseCounter > 500) {
                movePhaseXDash();
            }
            if (phaseCounter == 1000){
                phaseCounter = 100;
            }
    }

    /*
     * This returns the x position of the fly. It returns x
     */
    public int getEnemyX() {
        return x;
    }

    /*
     * This returns the phase counter of the fly. It is used in the gameplay loop
     */
    public int getPhaseCounter(){
        return phaseCounter;
    }

    /*
     * This returns the y position of the fly. It is an accessor method
     */
    public int getEnemyY() {
        return y;
    }

    /*
     * This sets the x position of the fly. It takes in an int argument
     */
    public void setEnemyX(int newX){
        x = newX;
    }

    /*
     * This sets the y position of the fly. It takes in an int argument
     */
    public void setEnemyY(int newY){
        y = newY;
    }

    /*
     * This returns the width of the fly. Its a getter method
     */
    public double getEnemyWidth() {
        return size;
    }
    /*
     * This returns the height of the enemy. ITs a getter method
     */
    public double getEnemyHeight() {
        return size;
    }

    /*
     * This is a getter method. It checks if the enemy is colliding with a player
     */
    public boolean isCollidingPlayer() {
        return isCollidingPlayer;
    }

    /*
     * This returns the health of the enemy. It is an accessor method
     */
    public int getEnemyHealth() {
        return health;
    }

    /*
     * This method is for returning a boolean if the enemy is vulnerable. It is an accessor method
     */
    public boolean isEnemyVulnerable(){
        return isVulnerable;
    }
    /*
     * This method is called if the enemy is to be damaged. It decrements the enemy hp.
     */
    public void enemyDamaged() {
        health--;
        isVulnerable = false;
    }
}