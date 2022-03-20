package dev.thatalex.game;

public enum GameState {
    IN_LOBBY(true), 
    IN_GAME(false), 
    LIGHTS_OFF(false),
    PANIC_MODE(false), 
    RESETTING(false);

    private boolean canJoin;
    private static GameState currentState;

    GameState(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public boolean canJoin() {
        return canJoin;
    }

    public static void setState(GameState state) {
        GameState.currentState = state;
    }

    public static boolean isState(GameState state) {
        return GameState.currentState == state;
    }

    public static GameState getState() {
        return currentState;
    }
}
