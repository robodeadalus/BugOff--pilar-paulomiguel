/**
 * @author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
 * @version May 10, 2023
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

This class is for creating the end screen. It is used and depends on if the players won or not

*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


public class EndScreen{

    private int counter;
    public boolean isGameWon;
    BufferedImage defeatScreen1, defeatScreen2, victoryScreen, temp;

    /*
     * This constructor initializes the attributes of the class. Depending on the end result of the attributes
     * it will show either a defeat or victory screen.
     */
    public EndScreen(){
        isGameWon = false;
        getEndScreen();
        counter = 0;
    }

    /*
     * This method is for getting the end screen. It assigns variables to certain images
     * https://javarevisited.blogspot.com/2011/12/read-write-image-in-java-example.html#axzz81mFzsmo3
     */
    public void getEndScreen(){

        try {
            defeatScreen1 = ImageIO.read(getClass().getResourceAsStream("images/gameover1.png"));
            defeatScreen2 = ImageIO.read(getClass().getResourceAsStream("images/gameover2.png"));
            victoryScreen = ImageIO.read(getClass().getResourceAsStream("images/victory.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * This draws the image of the end screen. It uses a counter to determine what screen will be showed
     */
    public void draw(Graphics2D g2d){
        if(isGameWon == false){
            counter++;
            if(counter < 30){
                g2d.drawImage(defeatScreen1, 0, 0, 1000, 800, null);
            }
            else if(counter < 50){
                g2d.drawImage(defeatScreen2, 0, 0, 1000, 800, null);
            }
            else{
                g2d.drawImage(defeatScreen1, 0, 0, 1000, 800, null);
                counter = 0;
            }
        }
        else if(isGameWon == true){
            g2d.drawImage(victoryScreen, 0, 0, 1000, 800, null);
        }
       
    }

    /*
     * This method is for determining if the user won or not. It accepts a boolean argument
     */
    public void isGameWon(boolean arg){
        isGameWon = arg;
    }
}
