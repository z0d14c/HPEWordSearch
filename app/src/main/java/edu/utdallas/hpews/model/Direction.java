package edu.utdallas.hpews.model;

/**
 * Created by sasha on 3/26/16.
 */
public enum Direction {

    _0,
    _45,
    _90,
    _135,
    _180,
    _225,
    _270,
    _315;

    public Direction getOpposite() {
        switch (this) {
            case _0:
                return _180;
            case _45:
                return _225;
            case _90:
                return _270;
            case _135:
                return _315;
            case _180:
                return _0;
            case _225:
                return _45;
            case _270:
                return _90;
            case _315:
                return _135;
            default:
                throw new IllegalStateException();
        }
    }
}