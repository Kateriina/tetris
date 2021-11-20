package ui;

import model.Coordinates;
import model.Figure;

import javax.swing.*;

public class Window extends JFrame implements Runnable{

    private int width;
    private int height;
    private Box[][] boxes;
    public Window() {
    }

    public void initForm(){
        setSize(Config.WIDTH * Config.SIZE + 15,
                Config.HEIGHT * Config.SIZE + 30); // размер формы

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Игра Тетрис");
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    private void initBoxes(){
        for ( int x = 0; x < Config.WIDTH; x++){
            for ( int y = 0; y < Config.HEIGHT; y++){
                Box box = new Box(x, y);
                add(box);
            }
        }
    }

    @Override
    public void run() {
        initForm();
        initBoxes();
    }
    public void showFigure(Figure figure, Coordinates at){

    }
}
