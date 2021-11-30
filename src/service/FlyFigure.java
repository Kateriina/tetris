package service;

import model.Coordinates;
import model.Figure;
import model.Mapable;
import ui.Config;

public class FlyFigure {
    private Coordinates coord;
    private Figure figure;
    private boolean landed;
    private int ticks;
    Mapable map;



    public FlyFigure(Mapable map){
        this.map = map;
        figure = Figure.getRandom();
        //coord = new Coordinates(Config.WIDTH/2 - 2, figure.top.y - figure.bot.y - 1);
        coord = new Coordinates(Config.WIDTH/2 - 2,  - 1);
        landed = false;
        ticks=2;
    }

    public Figure getFigure(){
        return figure;
    }

    public Coordinates getCoord(){
        return coord;
    }

    public boolean isLanded(){
        return landed;
    }

    public boolean canPlaceFigure(){
        return canMoveFigure(figure,0,0);
    }

    //разрешение на движение вправо/влево
    public boolean canMoveFigure(Figure figure, int sx, int sy){
        if (coord.x + sx + figure.top.x < 0) return false;
        if (coord.x + sx + figure.bot.x >= Config.WIDTH) return false;
        if (coord.y + sy + figure.bot.y >= Config.HEIGHT) return false;

        for(Coordinates dot: figure.dots){
            if(map.getBoxColor(coord.x + dot.x + sx, coord.y + dot.y + sy)>0){
                return false;
            }
        }
        return true;
    }

    public void moveFigure(int sx, int sy){
        if (canMoveFigure(figure, sx, sy))
            coord = coord.plus(sx, sy);
        else
            if (sy == 1){
                if (ticks > 0){
                    ticks--;
                }
                else{
                    landed = true;
                }
            }
    }


    public void turnFigure(int direction){
        Figure rotated = direction == 1? figure.turnRight():figure.turnLeft();
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

