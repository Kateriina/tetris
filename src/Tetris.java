import model.Coordinates;
import model.Figure;
import ui.Info;
import ui.Window;

import javax.swing.*;

public class Tetris {
    public static void main(String[] args){

        Window window = new Window();
        SwingUtilities.invokeLater(window);
        window.addFigure();
    }
}

