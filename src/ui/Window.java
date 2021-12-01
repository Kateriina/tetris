package ui;

import model.Coordinates;
import model.Figure;
import model.Mapable;
import service.FlyFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements Runnable, Mapable {

    private Box[][] boxes;
    private FlyFigure fly;
    private JLabel statusBar;

    private int count = 0;
    private JLabel status;
    private Timer timer;

    JLabel getStatusBar() {
        return statusBar;
    }
    public Window() {
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        count = 0;
        initForm();
        initBoxes();
        setLayout(new BorderLayout());

        status = new JLabel("");
        setStatusText("0");
        //JLabel status = new JLabel();
        //status.setText("Score: "+ score);
        add(status, BorderLayout.SOUTH);
        addKeyListener(new KeyAdapter());
        TimeAdapter timeAdapter = new TimeAdapter();
        timer = new Timer(100, timeAdapter);
        timer.start();
    }
    public void addFigure(){
        fly = new FlyFigure(this);
        if(fly.canPlaceFigure()){
            showFigure();
        }
        else{
            setStatusText(String.valueOf(count) + ". Игра окончена!");
            timer.stop();
            //setVisible(false);
            //dispose();
        }
    }

    public void initForm(){

        Image icon = new javax.swing.ImageIcon("images/tetris.png").getImage();
        setIconImage(icon);

        setSize(Config.WIDTH * Config.SIZE + 15,
                Config.HEIGHT * Config.SIZE + 65); // размер формы

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

    public void setStatusText(String sc) {
        status.setText("Счет: " + sc);
    }
    @Override
    public void run() {
        repaint();
    }
    private void showFigure(){
        showFigure( 1, 1);
    }

    private void hideFigure(){
        showFigure(0, 0);
    }

    private void showFigure(int color, int thick){
        for (Coordinates dot: fly.getFigure().dots){
            setBoxColor(fly.getCoord().x + dot.x, fly.getCoord().y + dot. y, color, thick);
        }
    }

    void setBoxColor(int x, int y, int color, int thick){
       if ( x < 0 || x >= Config.WIDTH) return;
       if ( y < 0 || y >= Config.HEIGHT) return;

       boxes[x][y].setColor(color, thick);
       boxes[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK, thick));
    }

    public int getBoxColor(int x, int y){
        if ( x < 0 || x >= Config.WIDTH) return -1;
        if ( y < 0 || y >= Config.HEIGHT) return -1;
        return boxes[x][y].getColor();
    }

    public int getBoxThick(int x, int y){
        if ( x < 0 || x >= Config.WIDTH) return -1;
        if ( y < 0 || y >= Config.HEIGHT) return -1;
        return boxes[x][y].getThick();
    }
    private void moveFly(int sx, int sy){
        hideFigure();
        fly.moveFigure(sx, sy);
        showFigure();
    }

    private void turnFly(int direction){
        hideFigure();
        fly.turnFigure(direction);
        showFigure();
    }

    class KeyAdapter implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            hideFigure();
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT : moveFly(-1, 0); break;
                case KeyEvent.VK_RIGHT : moveFly(+1 ,0); break;

                case KeyEvent.VK_UP: turnFly(2); break;
                //case KeyEvent.VK_DOWN: turnFly(1); break;
            }
            showFigure();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private  void removeLines(){
        int countLines = 0;
        for (int y = Config.HEIGHT - 1; y >= 0; y--){
            while(isFullLine(y)){
                ++countLines;
                dropLine(y);
            }
        }

        if(countLines > 0){
            count+=countLines;
            setStatusText(String.valueOf(count));
        }
    }

    private void dropLine(int y){
        for(int movey=y-1; movey >= 0; movey--){
            for(int x = 0; x < Config.HEIGHT; x++){
                setBoxColor(x, movey + 1, getBoxColor(x, movey), getBoxThick(x, movey));
            }
        }
        for(int x = 0; x < Config.WIDTH; x++){
            setBoxColor(x, 0, 0, 0);
        }
    }

    private boolean isFullLine(int y){
        for(int x = 0; x < Config.WIDTH; x++){
            if(getBoxColor(x, y) != 2)
                return false;
        }
        return true;
    }
    class TimeAdapter implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            moveFly(0,1);
            if (fly.isLanded()){
                showFigure(2,1);
                removeLines();
                addFigure();
            }
        }
    }
}
