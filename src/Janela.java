import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class Janela extends JFrame {
	private static final long serialVersionUID = 1L;
    
    public Janela(Dimension screen) {
        setLayout( new BorderLayout() );
        
        setSize(screen.width, screen.height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation( (screen.width - getWidth())/2, (screen.height - getHeight())/2 );       
    }
}
