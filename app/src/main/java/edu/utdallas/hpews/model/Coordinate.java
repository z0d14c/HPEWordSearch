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

    public Coordinate getNext(Direction direction) {
        return getNextCoordinate(this, direction);
    }



    public static Coordinate getNextCoordinate(Coordinate coordinate, Direction direction) {
        return getCoordinate(coordinate.getX(), coordinate.getY(), direction, 1);
    }

    public static Coordinate getCoordinate(int x, int y, Direction direction, int magnitude) {
        for (; magnitude > 0; magnitude--) {
            switch (direction) {
                case _0:
                    y--;
                    break;
                case _45:
                    x++;
                    y--;
                    break;
                case _90:
                    x++;
                    break;
                case _135:
                    x++;
                    y++;
                    break;
                case _180:
                    y++;
                    break;
                case _225:
                    x--;
                    y++;
                    break;
                case _270:
                    x--;
                    break;
                case _315:
                    x--;
                    y--;
                    break;
            }
        }

        return new Coordinate(x, y);
    }

    public static Coordinate getCoordinate(Coordinate startingCoordinate, Direction direction, int magnitude) {
        return getCoordinate(startingCoordinate.getX(), startingCoordinate.getY(), direction, magnitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}