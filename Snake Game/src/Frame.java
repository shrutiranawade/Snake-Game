import  javax.swing.JFrame;


public class Frame extends JFrame{
//constructor
    Frame(){
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); //user not allowed to setsize.fix size we make
        this.pack();  //get preffered size from system setting set window accordingly
        this.setVisible(true); //set visible to everybody
        this.setLocationRelativeTo(null);// spawning at center window
    }
}
