
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irasan19
 */

class Firing {
	// At every time that the actionPerformed method runs, the fire will try to go forward.
	private int x;
	private int y;
	
	public Firing(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}	
}

public class Game extends JPanel implements KeyListener,ActionListener {

    Timer timer=new Timer(5, this);
    
    private int time=0; // The passing time.
    private int numberOfShots=0;
    
    private BufferedImage img;
    
    private ArrayList<Firing> fire=new ArrayList<>(); //  Hold on to the fire and provide to shoot more. 
    
    private int firedirY=5; // It will be added on the coordinate Y to provide movement of the fire at every time that the actionPerformed method runs.  
    private int ballX=0; // Move left and right.
    private int balldirX=5; // If I do not give a first value, it can not move. It will be added on the ballX to provide the returning from right to left after meet with the limitations.	
    private int spaceshipX=0; // Location of the spaceship.
    private int dirSpaceX=20; // According to the keyPressed method, advance 20 units to right or left.
      
    public Game() {       
    
        try {
            img=ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start(); // When the timer start, actionPerformed method will run at every 5 milliseconds.       
    }

    @Override
    public void paint(Graphics g) {
    
        super.paint(g); 
        
        time+=5;
        
        g.setColor(Color.RED);
        
        g.fillOval(ballX,0,20,20); 
        g.drawImage(img,spaceshipX,475,img.getWidth()/2,img.getHeight()/2,this); // Add the spaceship.
        
        /*fire.stream().filter((firing) -> (firing.getY()<=0)).forEach((firing) -> {
            fire.remove(firing);
        });
        Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException
	at Game.lambda$paint$0(Game.java:99)*/
        
        g.setColor(Color.ORANGE);
        fire.stream().forEach((firing) -> {
            g.fillRect(firing.getX(),firing.getY(),5,10);
        });
        
        if (control()) {
            timer.stop();
            String msg=
                    "You won!\n"+
                    "The spent fire are "+numberOfShots+"."+
                    "\nThe passing time is "+time/1000.0+".";
            JOptionPane.showMessageDialog(this, msg);
            System.exit(0);
            
        }
    }

    @Override
    public void repaint() { // Request for operations from the paint method. 
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        fire.stream().forEach((firing) -> {
            firing.setY(firing.getY()-firedirY);
        });
        
        ballX += balldirX;
        
        if (ballX>=750) { // Hold the ball in the frame.
                balldirX=-balldirX; // Provide the back returning of the ball.	
        }
        
        if (ballX<=0) { // When the ball come back to the left side, block to go outside from the frame's left.
                balldirX=-balldirX; 
        }
        repaint(); // Show the ball's movement.   
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int c=e.getKeyCode();
        
        if (c==KeyEvent.VK_LEFT) {
            
            if (spaceshipX<=0) { 
                spaceshipX=0;
            } else {
                spaceshipX-=dirSpaceX;                
            }    
        }
        
        if (c==KeyEvent.VK_RIGHT) {
            
            if (spaceshipX>=710) { 
                spaceshipX=710;
            } else {
                spaceshipX+=dirSpaceX;                
            }    
        }   
        
        if (c==KeyEvent.VK_CONTROL) {
            fire.add(new Firing(spaceshipX+41,470)); // Create a fire where the spaceship is at.
            numberOfShots++;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    private boolean control() {
       return fire.stream().anyMatch((firing) -> (new Rectangle(firing.getX(),firing.getY(),5,10).intersects(new Rectangle(ballX,0,20,20))));
    }    
}
