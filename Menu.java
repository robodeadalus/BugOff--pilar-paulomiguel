/**
 * @author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version May 10, 2023
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

 * The Menu class represents a menu in a game.
 * It provides methods to draw the menu image, handle button clicks,
 * and retrieve information about the menu dimensions.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Menu {
    
    private int buttonHeight, buttonWidth, buttonX, buttonY, counter;
    private boolean isButtonClicked, isButtonHovered;

    public BufferedImage menuImage1, menuImageHovered, menuImage2;

        /**
     * Constructs a Menu object with default button dimensions and initial state. IT initializes the attributes here
     */
    public Menu() {
        buttonHeight = 120;
        buttonWidth = 200;
        buttonX = 400;
        buttonY = 250;
        isButtonClicked = false;
        counter = 0;

        getMenuImage();
    }

    /**
     * Returns the height of the menu button.
     * 
     * @return The height of the button.
     */
    public int getHeight(){
        return buttonHeight;
    }

        /**
     * Returns the width of the menu button.
     * 
     * @return The width of the button.
     */
    public int getWidth(){
        return buttonWidth;
    }

        /**
     * Returns the X-coordinate of the menu button.
     * 
     * @return The X-coordinate of the button.
     */
    public int getX(){
        return buttonX;
    }

        /**
     * Returns the Y-coordinate of the menu button.
     * 
     * @return The Y-coordinate of the button.
     */
    public int getY(){
        return buttonY;
    }

        /**
     * Loads the menu image from a file.
     * If the file is not found, an IOException is thrown.
     */
    public void getMenuImage(){

        try {
            menuImage1 = ImageIO.read(getClass().getResourceAsStream("images/menu1.png"));
            menuImage2 = ImageIO.read(getClass().getResourceAsStream("images/menu2.png"));
            menuImageHovered = ImageIO.read(getClass().getResourceAsStream("images/ButtonHover.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Draws the menu on the specified graphics context.
     * IT sets the menu image to the image varaible
     * @param g2d The graphics context to draw on.
     */
    public void draw(Graphics2D g2d){
        if(isButtonHovered == true){
            g2d.drawImage(menuImageHovered, 0 , 0, 1000, 800, null);
        }
        else if(isButtonHovered == false){
            counter++;
            if(counter < 30){
                g2d.drawImage(menuImage1, 0 , 0, 1000, 800, null);
            }
            else if(counter < 55){
                g2d.drawImage(menuImage2, 0 , 0, 1000, 800, null);
            }
            else if(counter == 55){
                counter = 0;
                g2d.drawImage(menuImage1, 0 , 0, 1000, 800, null);
            }
        }
    }

    /**
     * Checks if the menu button has been clicked.
     * 
     * @return true if the button is clicked, false otherwise.
     */
    public boolean isButtonClicked(){  
        return isButtonClicked;
    }

    /**
     * Sets the state of the menu button.
     * 
     * @param arg The new state of the button.
     */
    public void buttonClick(boolean arg){
        isButtonClicked = arg;
    }

    public void isMouseHovered(boolean arg){
        isButtonHovered = arg;
    }
}
