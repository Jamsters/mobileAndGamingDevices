package uk.ac.tees.w9039358.mobileAndGamingDevices;

public class Settings {

    // Should be able to change settings for game here in code or in settings activity.

    // EASY
    private static final int EASY_SPAWN_DELAY = 700;
    private static final int EASY_MAX_ADDED_SPAWN_DELAY = 1200;

    // NORMAL
    private static final int NORMAL_SPAWN_DELAY = 600;
    private static final int NORMAL_MAX_ADDED_SPAWN_DELAY = 1000;

    // HARD
    private static final int HARD_SPAWN_DELAY = 500;
    private static final int HARD_MAX_ADDED_SPAWN_DELAY = 800;

    private static int BACKGROUND_MOVE_SPEED = -5;

    private Settings()
    {

    }


    public static int GetEasySpawnDelay() {
        return EASY_SPAWN_DELAY;
    }

    public static int GetEasyMaxAddedSpawnDelay() {
        return EASY_MAX_ADDED_SPAWN_DELAY;
    }

    public static int GetNormalSpawnDelay() {
        return NORMAL_SPAWN_DELAY;
    }

    public static int GetNormalMaxAddedSpawnDelay() {
        return NORMAL_MAX_ADDED_SPAWN_DELAY;
    }

    public static int GetHardSpawnDelay() {
        return HARD_SPAWN_DELAY;
    }

    public static int GetHardMaxAddedSpawnDelay() {
        return HARD_MAX_ADDED_SPAWN_DELAY;
    }

    public static int GetBackgroundMoveSpeed() {
        return BACKGROUND_MOVE_SPEED;
    }

    public static void SetBackgroundMoveSpeed(int backgroundMoveSpeed) {
        BACKGROUND_MOVE_SPEED = backgroundMoveSpeed;
    }
}
