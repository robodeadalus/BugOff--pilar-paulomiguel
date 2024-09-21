/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version May 5, 2023
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

This class is for the server of the game. It handles the server side of the game

*/
import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    private boolean isEnemyHit, IDOneStart, IDTwoStart;

    private int p1x, p1y, p2x, p2y, b1x, b1y, b2x, b2y,Player1HP, Player2HP, BossHP, newBossHP1, newBossHP2,PhaseCounter,PhaseCounterP1, PhaseCounterP2, BulletP1HP, BulletP2HP;
    /*
     * This is for initializing the attributes that will be the positions, health, and mechanics of the game.
     * IT also includes the variables whether to start the game or not.
     */
    public GameServer() {
        System.out.println("==== GAME SERVER ====");
        numPlayers = 0;
        maxPlayers = 2;

        p1x = 150;
        p1y = 500;
        p2x = 760;
        p2y = 500;

        b1x = 0;
        b1y = 0;
        b2x = 0;
        b2y = 0;

        Player1HP = 3;
        Player2HP = 3;
        BossHP = 20;
        newBossHP1 = BossHP;
        newBossHP2 = BossHP;

        PhaseCounter = 0;

        IDOneStart = false;
        IDTwoStart = false;

        try {
            ss = new ServerSocket(55891);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor()");
            ex.printStackTrace();
        }
    }

    /*
     * This method is for accepting connections to the game. It waits until two players have connected and
     * only then will it stop accepting connections
     */
    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while (numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();

                }
            }

            System.out.println("No longer accepting connection");

        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }

    }

    /*
     * This class is for receiving data input from the client. It uses a thread
     */
    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        /*
         * This constructor takes in the player id and the data input. The data input that comes in will be used.
         */
        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        /*
         * This method constantly ticks because its a thread. It keeps track of positions and health.
         */
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        IDOneStart = dataIn.readBoolean();
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                        b1x = dataIn.readInt();
                        b1y = dataIn.readInt();
                        Player1HP = dataIn.readInt();
                        newBossHP1 = dataIn.readInt();
                        PhaseCounterP1 = dataIn.readInt();
                        BulletP1HP = dataIn.readInt();
                        if (newBossHP1 < BossHP) {
                            BossHP = newBossHP1;
                        }
                        if(PhaseCounterP1<PhaseCounter){
                            PhaseCounter = PhaseCounterP1;
                        }
                    } else {
                        IDTwoStart = dataIn.readBoolean();
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        b2x = dataIn.readInt();
                        b2y = dataIn.readInt();
                        Player2HP = dataIn.readInt();
                        newBossHP2 = dataIn.readInt();
                        PhaseCounterP2 = dataIn.readInt();
                        BulletP2HP = dataIn.readInt();
                        if (newBossHP2 < BossHP) {
                            BossHP = newBossHP2;
                        }
                        if(PhaseCounterP2<PhaseCounter){
                          PhaseCounter = PhaseCounterP2;
                        }
                    }
                }

            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }
    }

    /*
     * This class is for sending data to the client. It uses a thread
     */
    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        /*
         * This constructor takes in player id and dataoutput stream. 
         * It works in conjuction with gameCanvas
         */
        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        /*
         * This method constantly ticks because its a thread. IT sends the postions
         * of the entities the client as well as if the game will begin or not
         */
        public void run() {
            try {

                while (true) {
                    if (playerID == 1) {
                        dataOut.writeBoolean(IDOneStart && IDTwoStart);
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeInt(b2x);
                        dataOut.writeInt(b2y);
                        dataOut.writeInt(Player2HP);
                        dataOut.writeInt(BossHP);
                        dataOut.writeInt(PhaseCounter);
                        dataOut.writeInt(BulletP2HP);
                        dataOut.flush();
                    } else {
                        dataOut.writeBoolean(IDOneStart && IDTwoStart);
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeInt(b1x);
                        dataOut.writeInt(b1y);
                        dataOut.writeInt(Player1HP);
                        dataOut.writeInt(BossHP);
                        dataOut.writeInt(PhaseCounter);
                        dataOut.writeInt(BulletP1HP);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }

            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        /*
         * This message will be sent if the game were to start. It only happens when the game has two players
         */
        public void sendStartMsg() {
            try {
                dataOut.writeUTF("We now have 2 players. Go!");
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }
    }

    /*
     * This initializes an instance of the game server. After that it calls accept connections
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

}
