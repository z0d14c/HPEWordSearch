package edu.utdallas.hpews.model;

/**
 * Created by sasha on 3/26/16.
 */
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    public static Coordinate getNextCoordinate(Coordinate coordinate, Direction direction) {
        return getNextCoordinate(coordinate.getX(), coordinate.getY(), direction);
    }

    public static Coordinate getNextCoordinate(int x, int y, Direction direction) {
        switch (direction) {
            case _0:
                y++;
                break;
            case _45:
                x++;
                y++;
                break;
            case _90:
                x++;
                break;
            case _135:
                x++;
                y--;
                break;
            case _180:
                y--;
                break;
            case _225:
                x--;
                y--;
                break;
            case _270:
                x--;
                break;
            case _315:
                x--;
                y++;
                break;
        }

        return new Coordinate(x, y);
    }

}