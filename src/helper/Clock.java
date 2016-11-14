package helper;

import org.lwjgl.Sys;

/**
 * Created by sebi on 002 02/11/2016.
 */
public final class Clock {
    public static long lastFrame, totalTime;
    public static float d = 0, multiplier = 1;
    private static boolean paused = false;

    public static long getTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static float getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta * 0.001f;
    }

    public static float delta() {
        if (d > 10)
            return 0;
        return paused ? 0 : d * multiplier;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        Clock.paused = paused;
    }

    public static void update() {
        d = getDelta();
        totalTime += d;
    }

    public static float getMultiplier() {
        return multiplier;
    }

    public static void setMultiplier(float multiplier) {
        Clock.multiplier = multiplier;
    }

    public static long getTotalTime() {
        return totalTime;
    }
}
