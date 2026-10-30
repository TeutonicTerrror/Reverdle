import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    private char c;
    private Color bg = Color.LIGHT_GRAY;

    public Tile(char c){this.c=c;setPreferredSize(new Dimension(50,50));}
    public void setColor(Color c){bg=c;repaint();}
    public void setChar(char c){this.c=c;repaint();}
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),15,15);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial",Font.BOLD,24));
        FontMetrics fm=g2.getFontMetrics();
        int x=(getWidth()-fm.charWidth(c))/2;
        int y=(getHeight()-fm.getHeight())/2+fm.getAscent();
        g2.drawString(""+c,x,y);
    }
}
