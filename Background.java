/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version April 29, 2023
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

This class is for the background of the game. This will serve as the background of the game.
*/import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Background {

    private double arenaX;
    private double arenaY;
    private double arenaWidth;
    private double arenaHeight;
    public BufferedImage background;

    /*
     * This constructor for initializing the attributes of the background.
     * and the arena's width and height
     */
	public Background(){
        arenaX = 110;
        arenaY = 90;
        arenaWidth = 800;
        arenaHeight = 600;

        getBackgroundImage();
	}

    /*
     * This method is for getting the x of the arena. It returns the x value
     */
    public double getArenaX(){
        return arenaX;
    }

    
    /*
     * This method is for getting the y of the arena. It returns the y value
     */
    public double getArenaY(){
        return arenaY;
    }

    /*
     * This method is for getting the width of the arena. It returns the width
     */
    public double getArenaWidth(){
        return arenaWidth;
    }

    /*
     * This method is for getting the height of the arena. It returns the height.
     */
    public double getArenaHeight(){
        return arenaHeight;
    }

    /*
     * This method is for getting the background image to be used as the background. It goes into catch if it encounters an error
     * https://javarevisited.blogspot.com/2011/12/read-write-image-in-java-example.html#axzz81mFzsmo3
     */
    public void getBackgroundImage(){

        try {
            background = ImageIO.read(getClass().getResourceAsStream("images/background1.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * This method is for drawing the image. It draws the image
     */
    public void draw(Graphics2D g2d){
        BufferedImage image = background;

        g2d.drawImage(image, 0 , 0, 1000, 800, null);
    }


}