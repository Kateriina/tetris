package ui;

import model.Coordinates;
import model.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements Runnable{

    private Box[][] boxes;
    private Coordinates coord;
    private Figure figure;

    public Window() {
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        initForm();
        initBoxes();
        addKeyListener(new KeyAdapter());
    }
    public void addFigure(){
        figure = Figure.getRandom();
        coord = new Coordinates(5, 5);
        showFigure();
    }

    public void initForm(){
        setSize(Config.WIDTH * Config.SIZE + 15,
                Config.HEIGHT * Config.SIZE + 30); // размер формы

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Игра Тетрис");
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
    }

    private void initBoxes(){
        for ( int x = 0; x < Config.WIDTH; x++){
            for ( int y = 0; y < Config.HEIGHT; y++){
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);
            }
        }
    }

    @Override
    public void run() {
        repaint();
    }
    private void showFigure(){
        showFigure(figure, coord, 1, 1);
    }

    private void hideFigure(){
        showFigure(figure, coord, 0, 0);

    }

    private void showFigure(Figure figure, Coordinates at, int color, int thick){
        for (Coordinates dot: figure.dots){
            setBoxColor(at.x + dot.x, at.y + dot. y, color, thick);
        }
    }

    void setBoxColor(int x, int y, int color, int thick){
       if ( x < 0 || x >= Config.WIDTH) return;
       if ( y < 0 || y >= Config.HEIGHT) return;

       boxes[x][y].setColor(color);
       boxes[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK, thick));
    }

    private boolean canMoveFigure(int sx, int sy){
        int left = coord.x + sx + figure.top.x;
        if (left < 0) return false;
        int right = coord.x + sx + figure.bot.x;
        if ( right>= Config.WIDTH) return false;
        return true;
    }

    private void moveFigure(int sx, int sy){
        if (canMoveFigure(sx, sy))
            coord = coord.plus(sx, sy);
    }

    class KeyAdapter implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            hideFigure();
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT : moveFigure(-1, 0); break;
                case KeyEvent.VK_RIGHT : moveFigure(+1 ,0); break;
            }
            showFigure();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
