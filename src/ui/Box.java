package ui;

import javax.swing.*;

public class Box extends JPanel {

    public Box(int x, int y){
        setBounds(x * Config.SIZE, y * Config.SIZE,
                Config.SIZE, Config.SIZE);
        setBackground(Config.BACK);
    }

}
