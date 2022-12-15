import javax.swing.*;                  //gui part
import java.awt.*;                     //action listeners
import java.awt.event.*;
import java.security.Key;
import java.util.Arrays;              //storing snake body part coordinates
import java.util.Random;              //spawnig food randomly

public class Panel extends JPanel implements ActionListener{
    //setting dimensions of panel
    static int width =1200;
    static int height = 600;
    //size of each unit;
    static int unit = 50;
    //for checking state of game at regular intervals(frames/sec)

    Timer timer;
    static int delay = 160;
    //food spawning
    Random random;

    //setting initial body size
    int body = 3;                                        //1 head,2 bodyparts
    //initial direction
    char dir = 'R';
    int fx, fy;                                                 //0-1200 & 0-600 are the fx,fy coordinates intial of food
    int score = 0;                                                 //initial score;
    boolean flag = false;                               //game state is stopped
    static int size = (width * height) / (unit * unit);  //calculating area of each box from grid total area/1 unit area
   final int xsnake[] = new int[size];
   final int ysnake[] = new int[size];
    //constructor for panel  class

    Panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);                                 //game is on focus
        random = new Random();
        this.addKeyListener(new Key());                         // add listener to panel can record & act upon it
        game_start();
    }


    public void game_start() {
        spawnfood();     //as game start food should be spawn
        flag = true;                           //game is started so
        //staring the time with delay of 160 ms
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnfood() {
        //setting random coordinates for food
        fx = random.nextInt((int) (width / unit)) * unit;
        fy = random.nextInt((int) (height / unit)) * unit;
    }

    public void checkhit() { //chk heads collision with own body parts or walls
        for (int i = body; i > 0; i--) {
            if ((xsnake[0] == xsnake[i])&& (ysnake[0] == ysnake[i])) {
                flag = false;
            }
        }
        //checking with collsion with walls
        if (xsnake[0] < 0){ flag = false;}
        if (xsnake[0] > width){ flag = false;}
       if (ysnake[0] < 0){ flag = false;}
       if (ysnake[0] > height){ flag = false;}


        if (flag == false) {
            timer.stop(); //if snake hits stop the game
        }
    }

    //interemediate funtion between panel & drawing function
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);                //want to draw only on particular part of panel not on whole screen
        draw(graphic);
    }


    public void draw(Graphics graphic){
        if(flag){
            //setting parameter for food block
            graphic.setColor(Color.RED);
            graphic.fillOval(fx,fy,unit,unit);
            //setting parameters for snake
            for(int i=0;i<body;i++){
                if(i ==0){                           //head color different than body
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
                else {
                    graphic.setColor(Color.YELLOW);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            //drawing the Score
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics f = getFontMetrics(graphic.getFont());
            //drawing takes strings to draw starting position in x & the starting position y
            graphic.drawString("SCORE " +score,(width-f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());
        }
        else {
            gameOver(graphic);
        }

    }
    public void gameOver(Graphics graphic){
        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f = getFontMetrics(graphic.getFont());
        //drawing takes strings to draw starting position in x & the starting position y
        graphic.drawString("SCORE : " +score,(width-f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());

        //graphic for game over
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        //drawing takes strings to draw starting position in x & the starting position y
        graphic.drawString("GAME OVER ",(width-f2.stringWidth("GAME OVER:"))/2,height/2);

        //graphic for replay
        graphic.setColor(Color.yellow);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f3 = getFontMetrics(graphic.getFont());
        //drawing takes strings to draw starting position in x & the starting position y
        graphic.drawString("PRESS R to REPLAY " ,(width-f3.stringWidth("PRESS R to REPLAY:"))/2,height/2-120);
    }

    public void move(){
        for(int i=body;i>0;i--){                    //decrementing body parts & updating body parts excepts head
            xsnake[i] = xsnake[i-1];
            ysnake[i]= ysnake[i-1];
        }
        switch (dir){
            case 'U' : ysnake[0] = ysnake[0]-unit;
                       break;
            case 'L' : xsnake[0] = xsnake[0]-unit;
                break;
            case 'D' : ysnake[0] = ysnake[0]+unit;
                       break;

            case 'R' : xsnake[0] = xsnake[0]+unit;
                        break;

        }
    }
    public void checkScore(){
        //checking head & body part coincide with eachother
        if((xsnake[0]==fx) && (ysnake[0]==fy)){
            body++;
            score++;
            spawnfood();
        }
    }
    public class Key extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (dir != 'D') dir = 'U';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') dir = 'R';
                    break;

                case KeyEvent.VK_DOWN:
                    if (dir != 'U') dir = 'D';
                    break;
                case KeyEvent.VK_R:
                    if (!flag) {
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        game_start();

                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (flag){
            move();
            checkScore();
            checkhit();
        }
        repaint();
    }
}



