package com.mona.game.windowHandler;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by _Silence on 2016/1/3.
 */
public class DirectionGestureDetector   extends GestureDetector{

    // Interface for using with this class
    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();
    }

    public DirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    // Class used for calculating the direction
    private static class DirectionGestureListener extends GestureAdapter {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener) {
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 0) {
                    directionListener.onRight();
                } else {
                    directionListener.onLeft();
                }
            } else {
                if (velocityY > 0) {
                    directionListener.onDown();
                } else {
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }
}
