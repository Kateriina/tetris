package ui;

import model.Coordinates;
import model.Mapable;
import service.FlyFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Window extends JFrame implements Runnable, Mapable {

    private Box[][] boxes;
    private FlyFigure fly;
    private JLabel statusBar;

    private int count = 0;
    private JLabel status;
    private Timer timer;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private boolean isFinished = false;
    private String highscore = "0";


    JLabel getStatusBar() {
        return statusBar;
    }
    public Window() {
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        count = 0;
        initForm();
        initBoxes();
        initMenu();
        initScore();

        addKeyListener(new KeyAdapter());
        TimeAdapter timeAdapter = new TimeAdapter();
        timer = new Timer(150, timeAdapter);
        timer.start();
    }


    public void addFigure(){
        fly = new FlyFigure(this);
        if(fly.canPlaceFigure()){
            showFigure();
        }
        else{
            isStarted = false;
            isFinished = true;
            setStatusText(String.valueOf(count) + ". Игра окончена!");
            timer.stop();

            setHighScore();
        }
    }




    public void initForm(){

        Image icon = new javax.swing.ImageIcon("images/tetris.png").getImage();
        setIconImage(icon);

        setSize(Config.WIDTH * Config.SIZE + 15,
                Config.HEIGHT * Config.SIZE + 80); // размер формы

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        setTitle("Игра Тетрис");
        setLocationRelativeTo(null);
        setLayout(null);

        setVisible(true);
        setResizable(false);
        isStarted = true;
        isFinished = false;
    }

    public void initMenu(){

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(itemExit);

        JMenuItem itemNg = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Window window = new Window();
                SwingUtilities.invokeLater(window);
                window.addFigure();
            }
        });
        menuFile.add(itemNg);

        JMenuItem itemHs = new JMenuItem(new AbstractAction("High Scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
                getHighScore();
                JOptionPane.showMessageDialog(null,"Рекорд: " + highscore);
                pause();
            }
        });
        menuFile.add(itemHs);

        menuFile.add(itemNg);
        JMenuItem itemClear = new JMenuItem(new AbstractAction("Clear High Scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
                setClearScore();
                pause();
            }
        });
        menuFile.add(itemClear);

        JMenuItem itemAbout = new JMenuItem(new AbstractAction("About") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
                ImageIcon icon = new ImageIcon("images/tetris.png");
                JOptionPane.showMessageDialog(null, "Игра Тетрис\n\nВерсия: 1.0\n\nУправление:\n\nСтрелка влево - движение влево\nСтрелка вправо - движение вправо\nСтрелка вверх - поворот направо\nСтрелка вниз - ускорение падения фигуры\nПробел - пауза\n\n© by Kate","About", JOptionPane.INFORMATION_MESSAGE, icon);
                pause();
            }
        });

        menuFile.add(itemAbout);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

    }

    public void initScore(){

        setLayout(new BorderLayout());
        status = new JLabel("");
        setStatusText("0");
        //JLabel status = new JLabel();
        //status.setText("Score: "+ score);
        add(status, BorderLayout.SOUTH);
    }


    private void initBoxes(){
        for ( int x = 0; x < Config.WIDTH; x++){
            for ( int y = 0; y < Config.HEIGHT; y++){
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);
            }
        }
    }
    public boolean isStarted() {
        return isStarted;
    }

    public boolean isPaused() {
        return isPaused;
    }
    public boolean isFinished() { return isFinished; }

    public void pause() {

        if (isFinished){
            return;
        }
        isPaused = !isPaused;
        if (isPaused) {
            isStarted = false;
            timer.stop();
            setStatusText("Пауза");
        } else {
            isStarted = true;
            timer.start();
            setStatusText(String.valueOf(count));
        }
        repaint();
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
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                pause();
                return;
            }
            if(!isStarted){
                return;
            }
            else{
                hideFigure();
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT : moveFly(-1, 0); break;
                    case KeyEvent.VK_RIGHT : moveFly(+1 ,0); break;

                    case KeyEvent.VK_UP: turnFly(2); break;
                    case KeyEvent.VK_DOWN: moveFly(0, +6); break;
                }
                showFigure();
            }


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


    public void getHighScore() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("highscore.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            highscore = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHighScore(){
        getHighScore();
        if (Integer.parseInt(highscore) < count){
            try (BufferedWriter writter = new BufferedWriter(new FileWriter("highscore.txt"))) {
                writter.write(String.valueOf(count));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setClearScore(){
        try (BufferedWriter writter = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writter.write("0");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
