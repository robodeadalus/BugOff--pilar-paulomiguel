/**
 * @author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
 * @version April 25, 2023
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

The GameFrame class represents the main frame of the game.
It handles user input, game logic, and GUI setup.

*/

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.event.*;

public class GameFrame implements KeyListener, MouseListener {

    private JFrame f;
    private GameCanvas gc;
    private JPanel cp;
    private int counter;
    private boolean playMenuMusic, playDefeatMusic, playBattleMusic,playVictoryMusic,stopMusic;

    /**
     * Constructs a GameFrame object.
     * Initializes the game frame, canvas, and other components.
     */
    public GameFrame() {
        playMenuMusic=false;
        playDefeatMusic=false;
        playBattleMusic=false;
        stopMusic = true;
        counter = 0;
        f = new JFrame();
        gc = new GameCanvas(1000, 800);
        cp = (JPanel) f.getContentPane();

        Timer t = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    tick();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Executes the game logic in each tick of the timer.
     * Updates the game state, checks for collisions, and repaints the canvas.
     */
    public void tick() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(playMenuMusic==false ){
            gc.PlayMusic("menu");
            playMenuMusic=true;
        }
        if (gc.getMenu().isButtonClicked() && gc.gameStart) {
            if(stopMusic==true){
                gc.StopMusic();
                stopMusic=false;
            }

            if(playBattleMusic==false){
                gc.PlayMusic("battle");
                playBattleMusic=true;
            }
            counter++;
            gc.bulletMove();
            gc.getPlayer().movePlayer();
            gc.isPlayerCollidingWall();
            gc.isEnemyHit();
            gc.isPlayerHit();
            gc.updateEnemyBar();

            if (counter == 100) {
                gc.getFlyBoss().setEnemyX(-100);
                gc.getFlyBoss().setEnemyY(-100);
            } else if (counter > 100 && counter < 500) {
                gc.getFlyBoss().movePhaseZigZag();
            }

            if (counter == 500) {
                gc.getFlyBoss().setEnemyX(-100);
                gc.getFlyBoss().setEnemyY(0);
            }
            if (counter >= 500) {
                gc.getFlyBoss().movePhaseXDash();
            }
            if(counter >= 650){
                counter = 100;
            }
            if(gc.didLose==true && playDefeatMusic==false){
                gc.StopMusic();
                gc.PlayMusic("defeat");
                playDefeatMusic = true;
            }

            if(gc.didWin==true && playVictoryMusic==false){
                gc.StopMusic();
                gc.PlayMusic("victory");
                playVictoryMusic=true;
            }
        }
        
        gc.repaintCanvas();
    }

    /**
     * Sets up the GUI by adding the game canvas to the content pane.
     * Configures the frame properties and registers the key and mouse listeners.
     */
    public void setUpGUI() {
        cp.add(gc);
        f.setTitle("Bug Off!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
        gc.createSprites();
        f.addKeyListener(this);
        f.addMouseListener(this);
    }

    /**
     * 
     * Handles the logic when the mouse is clicked.
     * Checks if the mouse click is within the button area, updates the button state,
     * and performs additional actions if the button is clicked.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX() >= gc.getMenu().getX() && 
        e.getX() <= gc.getMenu().getX()+ gc.getMenu().getWidth() && 
        e.getY() >= gc.getMenu().getY() &&
        e.getY() <= gc.getMenu().getY()+ gc.getMenu().getHeight()&&
        gc.getMenu().isButtonClicked() == false)
            gc.getMenu().buttonClick(true);

        if(gc.getMenu().isButtonClicked() == true && gc.getBullet().getHealth() > 0){
            gc.getBullet().setBulletX(gc.getPlayer().getX());
            gc.getBullet().setBulletY(gc.getPlayer().getY());
            gc.getBullet().setAngle(gc.getPlayer().getX(), gc.getPlayer().getY(), e.getX(), e.getY());
            System.out.println(gc.getBullet().getHealth());
      }
    }

    /**
     * 
     * Handles the logic when a key is pressed.
     * Sets the corresponding flag in the game canvas based on the pressed key.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            gc.getPlayer().setLeft(true);
        }

        if (key == KeyEvent.VK_D) {
            gc.getPlayer().setRight(true);
        }

        if (key == KeyEvent.VK_W) {
            gc.getPlayer().setUp(true);
        }

        if (key == KeyEvent.VK_S) {
            gc.getPlayer().setDown(true);
        }
    }

      /**
     * 
     * Handles the logic when a key is released.
     * Resets the corresponding flag in the game canvas based on the released key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            gc.getPlayer().setLeft(false);
        } else if (key == KeyEvent.VK_D) {
            gc.getPlayer().setRight(false);
        }

        if (key == KeyEvent.VK_W) {
            gc.getPlayer().setUp(false);
        } else if (key == KeyEvent.VK_S) {
            gc.getPlayer().setDown(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Connects to the server.
     * Calls the corresponding method in the game canvas to establish a connection with the server.
     */
    public void connectToServer() {
        gc.connectToServer();
    }

}