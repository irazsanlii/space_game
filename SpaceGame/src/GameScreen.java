
import java.awt.HeadlessException;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irasan19
 */

public class GameScreen extends JFrame {
  
    public GameScreen(String title) throws HeadlessException {
        super(title);
    }
      
    public static void main(String[] args) {
        
        GameScreen screen=new GameScreen("Space Game");
        
        screen.setResizable(false);
        screen.setFocusable(false);
       
        screen.setSize(800,600);
        
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Game game=new Game();
       
        game.requestFocus(); // Get the inputs to focus.
       
        game.addKeyListener(game);
        
        game.setFocusable(true);
        game.setFocusTraversalKeysEnabled(false);
        
        screen.add(game);
        screen.setVisible(true);
        
    }


}
