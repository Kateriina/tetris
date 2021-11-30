package ui;

import javax.swing.*;

public class Box extends JPanel {

    private int color;
    private int thick;
    public int getColor(){
        return color;
    }
    public int getThick(){
        return thick;
    }

    public Box(int x, int y){
        color = 0;
        thick = 0;
        setBounds(x * Config.SIZE, y * Config.SIZE,
                Config.SIZE, Config.SIZE);
        setBackground(Config.COLORS[0]);
    }

    public  void setColor(int color, int thick){
        this.color = color;
        this.thick = thick;
        if (color >=0 && color < Config.COLORS.length)
            setBackground(Config.COLORS[color]);
    }
}
