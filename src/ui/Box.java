package ui;

import javax.swing.*;

public class Box extends JPanel {

    public Box(int x, int y){
        setBounds(x * Config.SIZE, y * Config.SIZE,
                Config.SIZE, Config.SIZE);
        setBackground(Config.BACK);
    }

    public  void setColor(int j){
        if (j ==0)
            setBackground(Config.BACK);
        else
            setBackground(Config.FORE);

    }


}
