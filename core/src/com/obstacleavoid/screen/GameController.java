package com.obstacleavoid.screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.config.DifficultyLevel;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;
import com.obstacleavoid.util.debug.DebugCameraController;

public class GameController {

    // == Constants ==
    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    // == Attributes / fields ==
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacleTimer;
    private float scoreTimer;
    private int score;
    private int displayScore; // Score that is displayed on HUD
    private int lives = GameConfig.LIVES_START;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    // == Constructor ==
    public GameController() {
        init();
    }

    // == Init ==
    private void init() {
        // create player
        player = new Player();


        // starting position
        float startPlayerX = GameConfig.WORLD_WIDTH / 2;
        float startPlayerY = 1;

        // position player
        player.setPosition(startPlayerX, startPlayerY);
    }

    // == Public Methods ==
    public void update(float delta) {
        if(isGameOver()) {
            log.debug("GameOver!");
            return;
        }
        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if(isPlayerCollidingWithObstacle()) {
            log.debug("Collision detected");
            lives--;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    // == Private Methods ==
    private boolean isGameOver() {
        return lives <= 0;
    }

    private void updatePlayer() {
        player.update();
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(
                player.getX(),
                player.getWidth() / 2,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2);

        player.setPosition(playerX, player.getY());
    }

    private void updateObstacles(float delta) {
        for(Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        createNewObstacle(delta);
        removePassedObstacles();

    }

    private void removePassedObstacles() {
        if(obstacles.size > 0) {
            Obstacle first = obstacles.first();

            float minObstacleY = -Obstacle.SIZE;

            if(first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
            }
        }
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if(obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);
            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }

    }

    private void updateScore(float delta) {
        scoreTimer += delta;
        if(scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        // Instead of having score jump up X number at a time, we can have the score count up
        // each frame. If the display score is less than score, then we will make the display score
        // == the minimum of either the score, or the display score + the framecount.
        // (assuming 40 * delta.)
        // If score is 4 and displayscore is 1 then on the first frame we will set the display score to
        // 1.66. and 1 will be displayed on the game UI.
        // Frame 2 - score is 4 and displayScore is 1.69 = 1 score displayed
        // Frame 3 - score is 4 and displayScore is 1.74 = 1 score displayed
        // Frame 4 - score is 4 and displayScore is 1.80 = 1 score displayed
        // Frame 5 - score is 4 and displayScore is 1.88 = 1 score displayed
        // Frame 6 - score is 4 and displayScore is 1.98 = 1 score displayed
        // Frame 7 - score is 4 and displayScore is 2.09 = 2 score displayed
        // This gives the effect of the score incrementing by one instead of jumping up in batches since
        // the display score is constantly catching up with the actual score.
        if(displayScore < score) {
            displayScore = Math.min(score, displayScore + (int) (120 * delta));
        }
    }

    private boolean isPlayerCollidingWithObstacle() {
        for(Obstacle obstacle : obstacles) {
            if(obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
                return true;
            }
        }
        return false;
    }


}
