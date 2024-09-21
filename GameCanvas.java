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

This class is for creating the GameCanvas which is the client side of the game. It handles the graphics of the game
and contains the methods for the mechanics of the game

*/

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;


public class GameCanvas extends JComponent {

    private Player player1, player2;
    private Bullet bullet1, bullet2;
    private Menu menu;
    private Background background;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfsRunnable;
    private WritetoServer wtsRunnable;
    public static int counter, gameCounter;
    public int waveDelay, player2HP, flyBossHP;
    private Enemy flyBoss;
    private PlayerBar playerbar;
    private EnemyBar enemybar;
    private EndScreen endscreen;
    private String ip;
    public boolean gameStart, menuClicked, buttonHovered, didWin, didLose;
    private AudioInputStream audioStream;
    private Clip clip;

    

    /*
     * This constructor takes in width and height that determines the dimensions of the program.
     * Additionally it sets the locations of the entities in the game.
     */
    public GameCanvas(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        player1 = new Player(150, 500, 75, true);
        player2 = new Player(760, 500, 75, false);
        flyBoss = new Enemy(400, 300);
        bullet1 = new Bullet(0, 0, 15);
        bullet2 = new Bullet(0, 0, 15);
        menu = new Menu();
        background = new Background();
        playerbar = new PlayerBar();
        enemybar = new EnemyBar();
        endscreen = new EndScreen();

        gameStart=false;
        menuClicked=false;
        buttonHovered = false;
        didWin=false;
        didLose=false;
    }
    /*
     * This method is for creating sprites. It places the locations of the sprites for each player
     */
    public void createSprites() {
        if (playerID == 1) {
            player1 = new Player(150, 500, 75, true);
            player2 = new Player(760, 500, 75, false);
            bullet1 = new Bullet(0, 0, 20);
            bullet2 = new Bullet(0, 0, 20);
            flyBoss = new Enemy(400, 500);

        } else if (playerID == 2) {
            player2 = new Player(150, 500, 75, true);
            player1 = new Player(760, 500, 75, false);
            bullet1 = new Bullet(0, 0, 20);
            bullet2 = new Bullet(0, 0, 20);
            flyBoss = new Enemy(400, 500);

        }
    }

        /*
         * This method is for playing the appropriate music depending on the phase of the game
         * It takes in a string to determine what phase one is in
         * 
         * Source: https://www.youtube.com/watch?v=SyZQVJiARTQ&t=553s&pp=ygUYaG93IHRvIGFkZCBtdXNpYyB0byBqYXZh
         */
        public void PlayMusic(String filename)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        try{
            if(filename=="menu"){
            File MenuMusic = new File("audio/menu.wav");
            audioStream = AudioSystem.getAudioInputStream(MenuMusic);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if(filename=="defeat"){
                File DefeatMusic = new File("audio/DefeatScreen.wav");
                audioStream = AudioSystem.getAudioInputStream(DefeatMusic);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if(filename=="battle"){
                File BattleMusic = new File("audio/BattleMusic.wav");
                audioStream = AudioSystem.getAudioInputStream(BattleMusic);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if( filename=="victory"){
                File VictoryMusic = new File("audio/VictoryMusic.wav");
                audioStream = AudioSystem.getAudioInputStream(VictoryMusic);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
        }catch (Exception ex){
            System.out.println("ExceptionError in PlayMusic()");
        }
    }
    /*
     * This method is for stopping the music. Its used for transition into other phases
     */
    public void StopMusic(){
        clip.stop();;
    }
    

    /*
     * This class is for recieving data from the server. It constantly runs
     * and receives data from the server.
     */
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        /*
         * This constructor takes in the argument in, which is Data.
         * It works in conjuction with the gameServer
         */
        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable Created");
        }

        /*
         * This method constantly ticks because it's a thread. Its for keeping track of the
         * positions of the entities in game as well as the HP. If it encounters an error
         * it goes to the catch.
         */
        public void run() {
            try {

                while (true) {
                    gameStart = dataIn.readBoolean();
                    int player2X = dataIn.readInt();
                    int player2Y = dataIn.readInt();
                    int bullet2X = dataIn.readInt();
                    int bullet2Y = dataIn.readInt();
                    player2HP = dataIn.readInt();
                    player2.health = player2HP;
                    flyBossHP = dataIn.readInt();
                    int BossPhaseCounter = dataIn.readInt();
                    int bulletP2HP = dataIn.readInt();
                    bullet2.health = bulletP2HP;
                    if (flyBossHP < flyBoss.health){
                        flyBoss.health = flyBossHP;
                        System.out.println(flyBoss.health);
                    }
                    if(BossPhaseCounter<flyBoss.getPhaseCounter()){
                        flyBoss.setPhaseCounter(BossPhaseCounter);
                    }

                    if (player2 != null && player2.health > 0) {
                        player2.setX(player2X);
                        player2.setY(player2Y);
                        bullet2.setBulletX(bullet2X);
                        bullet2.setBulletY(bullet2Y);
                    }

                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

        /*
         * This method exists so that there needs to be two players connected for the 
         * program to run. It takes in a message from the server using dataIn.
         */
        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    /*
     * This class is for writing data to the server. It constantly ticks and sends data, because its a thread
     */
    private class WritetoServer implements Runnable {
        private DataOutputStream dataOut;

        /*
         * This constructor takes in a DataOutputStream argument. It works in conjuction with the server
         */
        public WritetoServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable Created");
        }

        /*
         * This method constantly ticks because its a thread. Its for keeping track of the positions
         * of the entities in game as well as the HP. If it encounters an error it goes to the catch.
         */
        public void run() {
            try {
                while (true) {
                    if (menuClicked == false) {
                        menuClicked = menu.isButtonClicked();
                    }
                    if (player1 != null) {
                        dataOut.writeBoolean(menuClicked);
                        dataOut.writeInt(player1.getX());
                        dataOut.writeInt(player1.getY());
                        dataOut.writeInt(bullet1.getBulletX());
                        dataOut.writeInt(bullet1.getBulletY());
                        dataOut.writeInt(player1.health);
                        dataOut.writeInt(flyBoss.health);
                        dataOut.writeInt(flyBoss.getPhaseCounter());
                        dataOut.writeInt(bullet1.getHealth());
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }

    /*
     * This is for painting the graphics into the canvs. It draws the entities constantly
     */
    protected void paintComponent(Graphics g)   {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        if (menu.isButtonClicked() == false) {
            menu.draw(g2d);
        } else {
            background.draw(g2d);
            if(player1.health>0){
                player1.draw(g2d);
                if(bullet1.getHealth() > 0){
                    bullet1.draw(g2d);
                }
            }
            if(player2.health>0){
                player2.draw(g2d);
                if(bullet2.getHealth() > 0){
                    bullet2.draw(g2d);
                }
            }
            if(flyBoss.health>0){
                flyBoss.draw(g2d);
            }
                enemybar.draw(g2d);
                playerbar.draw(g2d);
            if(player1.health == 0 && player2.health == 0){
                endscreen.isGameWon(false);
                endscreen.draw(g2d);
                didLose=true;
            }
            if(flyBoss.health == 0){
                endscreen.isGameWon(true);
                endscreen.draw(g2d);
                didWin=true;
            }
            counter++;
        }
    }

    /*
     * This boolean is for getting if the players won or not. It is an accessor method
     */
    public boolean getDidWin(){
        return didWin;
    }
    /*
     * This method is for getting if the players lost or not. It is an accessor method
     */
    public boolean getDidLose(){
        return didLose;
    }

    /*
     * This method is for connecting to the server. It assigns a player id 
     * to a client and connects to the server
     */
    public void connectToServer() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter IP Address: ");
            ip = scanner.nextLine();
            scanner.close();
            
            socket = new Socket(ip, 55891);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player#" + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player#2 to connect...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WritetoServer(out);
            rfsRunnable.waitForStartMsg();
        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    /*
     * This method is for repainting the canvas. It is for refreshing the canvas constantly
     * making it update
     */
    public void repaintCanvas() {
        this.repaint();
    }

    /*
     * This method is for getting the player. It returns the player object.
     */
    public Player getPlayer() {
        return player1;
    }

    /*
     * This method is for getting the fly boss. It returns the flyboss.
     */
    public Enemy getFlyBoss() {
        return flyBoss;
    }

    /*
     * This method is for getting the menu. It returns the menu
     */
    public Menu getMenu() {
        return menu;
    }

    /*
     * This method is for getting the bullet. It returns the bullet.
     */
    public Bullet getBullet() {
        return bullet1;
    }

    /*
     * This method is for getting the player id. It returs the player id.
     */
    public int getPlayerID() {
        return playerID;
    }

    /*
     * This method is for getting the endscreen. It returns the endscreen
     */
    public EndScreen getEndScreen(){
        return endscreen;
    }

    /*
     * This method is for checking if the player is colliding with a wall.
     * It uses math logic such as determining the x and y to see if it is outside of bounds
     */
    public void isPlayerCollidingWall() {
        if (player1.getX() <= background.getArenaX()) {
            player1.collideLeft(true);
        } else {
            player1.collideLeft(false);
        }

        if (player1.getX() + player1.getSize() >= background.getArenaX() + background.getArenaWidth()) {
            player1.collideRight(true);
        } else {
            player1.collideRight(false);
        }

        if (player1.getY() <= background.getArenaY()) {
            player1.collideUp(true);
        } else {
            player1.collideUp(false);
        }

        if (player1.getY() + player1.getSize() >= background.getArenaY() + background.getArenaHeight()) {
            player1.collideDown(true);
        } else {
            player1.collideDown(false);
        }
    }

    /*
     * This method is for determining the path that the bullet will take
     * It makes use of another method in the bullet class
     */
    public void bulletMove() {
        bullet1.bulletShotPath();
    }

    /*
     * This method is for declaring if the player is hit or not. It decreases the player bar and the players health
     */
    public void isPlayerHit() {
        if (player1.isPlayerVulnerable() == true && flyBoss.getEnemyHealth() > 0) {
            if (player1.getSize() + player1.getY() >= flyBoss.getEnemyY() &&
                    player1.getY() <= flyBoss.getEnemyY() + flyBoss.getEnemyHeight() &&
                    player1.getX() <= flyBoss.getEnemyX() + flyBoss.getEnemyWidth() &&
                    player1.getX() + player1.getSize() >= flyBoss.getEnemyX()) {
                player1.playerHurt();
                playerbar.heartDecrease();
            }
        }
    }

    /*
     * This method is for updating the health of the enemy boss. Calling it will change the hp of the boss
     */
    public void updateEnemyBar() {
        enemybar.newHealth(flyBoss.health);
    }

    /*
     * This method is for declaring that the enemy has been hit. Calling it will damage the enemy
     */
    public void isEnemyHit() {
        if (flyBoss.isEnemyVulnerable() == true && bullet1.getHealth() == 1) {
            if (flyBoss.getEnemyHealth() > 0) {
                if (bullet1.getBulletSize() + bullet1.getBulletY() >= flyBoss.getEnemyY() &&
                        bullet1.getBulletY() <= flyBoss.getEnemyY() + flyBoss.getEnemyHeight() &&
                        bullet1.getBulletX() <= flyBoss.getEnemyX() + flyBoss.getEnemyWidth() &&
                        bullet1.getBulletX() + bullet1.getBulletSize() >= flyBoss.getEnemyX()) {
                    flyBoss.enemyDamaged();
                    bullet1.bulletHits();
                }
            }
        }
    }

    public boolean isEnemyHitChecker(){
        if (bullet1.getBulletSize() + bullet1.getBulletY() >= flyBoss.getEnemyY() &&
        bullet1.getBulletY() <= flyBoss.getEnemyY() + flyBoss.getEnemyHeight() &&
        bullet1.getBulletX() <= flyBoss.getEnemyX() + flyBoss.getEnemyWidth() &&
        bullet1.getBulletX() + bullet1.getBulletSize() >= flyBoss.getEnemyX()) {
            return true;
        }
        return false;
    }

    /*
     * This method is for determining if the mouse is hovering over the start of menu. It accepts a boolean
     */
    public void isMouseHovering(Boolean arg){
        menu.isMouseHovered(arg);
    }
}