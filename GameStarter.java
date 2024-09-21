
/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version April 18, 2023
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

This class is for starting the game. It connects the client to the server and sets up the GUI
*/

public class GameStarter {

    /*
     * This starts the game. It sets up the game frame the gui and the connections to the server
     */
    public static void main(String[] args){
        GameFrame gf = new GameFrame();
        gf.connectToServer();
        gf.setUpGUI();
    }

}