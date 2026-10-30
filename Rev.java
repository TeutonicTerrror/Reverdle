import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Rev extends JFrame {
    private static final boolean SHOW_ANSWER = false;
    private char[] s, c, r;
    private JTextField g;
    private JPanel grid, kPanel;
    private int row = 0;
    private Tile[][] tiles;
    private final String[] KR = {"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"};

    public Rev(){
        String w = DB.get();
        while (w.length() != 5) w = DB.get();
        s = w.toUpperCase().toCharArray();
        c = ArrCop.cp(s);
        r = ArrRev.rv(c);

        setTitle("Reverdle");
        setSize(600,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2,1));
        JLabel t1 = new JLabel("Reverdle",SwingConstants.CENTER);
        t1.setFont(new Font("Arial",Font.BOLD,28));
        JLabel t2 = new JLabel("Guess the word... in REVERSE!",SwingConstants.CENTER);
        t2.setFont(new Font("Arial",Font.PLAIN,14));
        top.add(t1);
        top.add(t2);
        add(top,BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(6,r.length,8,8));
        tiles = new Tile[6][r.length];
        for(int i=0;i<6;i++)
            for(int j=0;j<r.length;j++){
                tiles[i][j]=new Tile(' ');
                grid.add(tiles[i][j]);
            }
        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.add(grid);
        add(centerWrap,BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel inputP = new JPanel();
        g = new JTextField(8);
        g.setFont(new Font("Arial",Font.PLAIN,18));
        JButton sB = new JButton("Guess");
        sB.addActionListener(e->guess());
        g.addActionListener(e->guess());
        JButton back = new JButton("Back");
        back.addActionListener(e->{String t=g.getText();if(!t.isEmpty())g.setText(t.substring(0,t.length()-1));});
        inputP.add(g);
        inputP.add(sB);
        inputP.add(back);
        if(SHOW_ANSWER){
            JButton hint = new JButton("\uD83D\uDCA1");
            hint.setFont(new Font("Arial",Font.PLAIN,20));
            hint.addActionListener(e->JOptionPane.showMessageDialog(this,"Answer: "+new String(r)));
            inputP.add(hint);
        }
        bottom.add(inputP,BorderLayout.NORTH);

        kPanel = new JPanel();
        kPanel.setLayout(new BoxLayout(kPanel, BoxLayout.Y_AXIS));
        for (int rIndex = 0; rIndex < KR.length; rIndex++) {
            String rowKeys = KR[rIndex];
            JPanel kr = new JPanel();
            if (rIndex == 2) {
                kr.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 4));
                for (char kc : rowKeys.toCharArray()) {
                    JButton kb = new JButton(String.valueOf(kc));
                    kb.setFocusable(false);
                    kb.setFont(new Font("Arial", Font.BOLD, 18));
                    kb.setPreferredSize(new Dimension(55,55));
                    kb.setMaximumSize(new Dimension(55,55));
                    kb.setMargin(new Insets(2,2,2,2));
                    kb.addActionListener(e -> { if (g.getText().length() < 5) g.setText(g.getText() + kc); });
                    kr.add(kb);
                }
            } else {
                kr.setLayout(new GridLayout(1, rowKeys.length(), 4, 4));
                for (char kc : rowKeys.toCharArray()) {
                    JButton kb = new JButton(String.valueOf(kc));
                    kb.setFocusable(false);
                    kb.setFont(new Font("Arial", Font.BOLD, 18));
                    kb.setPreferredSize(new Dimension(55,55));
                    kb.setMaximumSize(new Dimension(55,55));
                    kb.setMargin(new Insets(2,2,2,2));
                    kb.addActionListener(e -> { if (g.getText().length() < 5) g.setText(g.getText() + kc); });
                    kr.add(kb);
                }
            }
            kr.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            kPanel.add(Box.createVerticalStrut(3));
            kPanel.add(kr);
        }

        bottom.add(kPanel,BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);

        setVisible(true);
    }

    private void guess(){
        String u = g.getText().toUpperCase();
        if(u.length()!=5) return;
        if(!u.matches("[A-Z]+")) return;
        if(row>=6) return;
        char[] x = u.toCharArray();
        for(int i=0;i<r.length;i++){
            tiles[row][i].setChar(x[i]);
            if(x[i]==r[i]) tiles[row][i].setColor(new Color(106,170,100));
            else if(contains(r,x[i])) tiles[row][i].setColor(new Color(201,180,88));
            else tiles[row][i].setColor(new Color(120,124,126));
        }
        g.setText("");
        row++;
        if(Arrays.equals(u.toCharArray(), r)) showWinScreen(u);
        else if(row>=6) showLoseScreen();
    }

    private void showWinScreen(String guess){
        String msg = "<html><center>Congratulations! You guessed correctly!<br>"+ "Reversed Answer: " + guess + "<br>"+ "Original Word: " + new String(s) + "</center></html>";
        JOptionPane.showMessageDialog(this, msg, "You Win!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLoseScreen(){
        String msg = "<html><center>Out of guesses!<br>"+ "Reversed Answer: " + new String(r) + "<br>"+ "Original Word: " + new String(s) + "</center></html>";
        JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean contains(char[] a,char c){for(char x:a) if(x==c) return true; return false;}

    public static void main(String[] args){SwingUtilities.invokeLater(Rev::new);}
}
