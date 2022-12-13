package flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener,MouseListener {
    public static FlappyBird flappyBird;
    public Renderer renderer;
    public Rectangle bird;
    public final int WIDTH=1000,HEIGHT=600;
    ArrayList<Rectangle> column;
    public Random rand;
    public boolean gameover,started=true;
    public int ticks,ymotion;
    public FlappyBird(){ //constructor
        JFrame jframe =new JFrame();
        Timer timer =new Timer(20,this);

        renderer =new Renderer();

        jframe.add(renderer);
        jframe.setSize(WIDTH,HEIGHT);
        jframe.setTitle("flappyBird");
        rand=new Random();
        jframe.setVisible(true);
        jframe.addMouseListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
        column=new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }
    public void paintColumn(Graphics g,Rectangle column){
        g.setColor(Color.green.darker());//background colour
        g.fillRect(column.x, column.y, column.width, column.height);//
    }
    public void jump(){
        if(gameover){


            column.clear();
            ymotion=0;
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameover=false;
        }
        if(!started){
            started=true;
        }
        else if(!gameover){
            if(ymotion>0){
                ymotion=0;
            }
            ymotion-=10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;
        int speed=10;
        if(started){
            for(int i=0;i<column.size();i++){
                Rectangle c=column.get(i);
                c.x-=speed;
            }
            renderer.repaint();
            if(ticks%2==0 && ymotion<50){
                ymotion+=2;
            }
            bird.y+=ymotion;
            for(int i=0;i<column.size();i++){
                Rectangle c=column.get(i);
                if(c.x+c.width<0){
                    column.remove(c);
                    if(c.y==0){
                        addColumn(false);
                    }
                }
            }
            for(Rectangle column:column){ //for each loop
                if(column.intersects(bird)){
                    gameover=true;
                    bird.x=column.x-bird.width;
                }
            }
            if(bird.y<0 || bird.y>HEIGHT-120){
                gameover=true;
            }
            if(gameover){
                bird.y=HEIGHT-150- bird.height;
            }
        }
        renderer.repaint();
    }

    public static void main(String[] args) {
        flappyBird=new FlappyBird();
    }
    public void mouseClicked(MouseEvent e){
        jump();
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void addColumn(boolean start){
        int space=200;
        int width=100;
        int height=50+rand.nextInt(300);
        if(start){
            column.add(new Rectangle(width+WIDTH+column.size()*300,HEIGHT-height-120,width,height));
            column.add(new Rectangle(width+WIDTH+ (column.size()-1)*300,0,width,HEIGHT-height-space));
        }
        else{
            column.add(new Rectangle(column.get(column.size()-1).x+600,HEIGHT-height-120,width,height));
            column.add(new Rectangle(column.get(column.size()-1).x,0,width,HEIGHT-height-space));
        }
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.red);
        g.fillRect(bird.x,bird.y,bird.width,bird.height);
        g.setColor(Color.orange);
        g.fillRect(0,HEIGHT-150,WIDTH,150);
        g.setColor(Color.green);
        g.fillRect(0,HEIGHT-150,WIDTH,20);
        for (Rectangle column:column){
            paintColumn(g,column);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,100));
        if(gameover){
            g.drawString("Game Over",75,HEIGHT-250);
        }
        if(!started){
            g.drawString("Click to Start",75,HEIGHT-250);
        }

    }
}
