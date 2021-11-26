package service;

import model.Coordinates;
import model.Figure;
import ui.Config;

public class FlyFigure {
    private Coordinates coord;
    private Figure figure;

    public Figure getFigure(){
        return figure;
    }

    public Coordinates getCoord(){
        return coord;
    }


    public FlyFigure(){
        figure = Figure.getRandom();
        coord = new Coordinates(Config.WIDTH/2 - 2, -figure.top.y -1);
    }

    //разрешение на движение вправо/влево
    private boolean canMoveFigure(Figure figure, int sx, int sy){
        if (coord.x + sx + figure.top.x < 0) return false;
        if (coord.x + sx + figure.bot.x >= Config.WIDTH) return false;
        //if (coord.y + sy + figure.top.y < 0) return false;
        if (coord.y + sy + figure.bot.y >= Config.HEIGHT) return false;
        return true;
    }

    public void moveFigure(int sx, int sy){
        if (canMoveFigure(figure, sx, sy))
            coord = coord.plus(sx, sy);
    }


    public void turnFigure(){
        Figure rotated = figure.turnRight();
        if (canMoveFigure(rotated,0,0)){
            figure = rotated;
        }
        else if (canMoveFigure(rotated,1,0)) {
            figure = rotated;
            moveFigure(1,0);
        }
        else if (canMoveFigure(rotated,-1,0)) {
            figure = rotated;
            moveFigure(-1,0);
        }else if (canMoveFigure(rotated,0,-1)) {
            figure = rotated;
            moveFigure(0,-1);
        }
    }
}
