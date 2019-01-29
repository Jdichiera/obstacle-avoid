package com.obstacleavoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.AssetPaths;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Background;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {
    // == Attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;
    private final GameController controller;
    private final AssetManager assetManager;
    private Texture playerTexture;
    private Texture obstacleTexture;
    private Texture backgroundTexture;

    // == Constructor ==
    public GameRenderer(AssetManager assetManager, GameController controller) {
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = assetManager.get(AssetDescriptors.FONT);

        // Create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        playerTexture = assetManager.get(AssetDescriptors.PLAYER);
        obstacleTexture = assetManager.get(AssetDescriptors.OBSTACLE);
        backgroundTexture = assetManager.get(AssetDescriptors.BACKGROUND);
    }

    // == Public methods ==
    public void render(float delta) {
        // Not wrapping inside alive because we want to be able to control camera at game over
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // Clear screen
        GdxUtils.clearScreen();

        // Render gameplay layer
        renderGameplay();

        // Render UI / HUD
        renderUI();

        // Render debug graphics
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();

        // These are all disposed by assetManager
//        font.dispose();
//        playerTexture.dispose();
//        obstacleTexture.dispose();
//        backgroundTexture.dispose();

        assetManager.dispose();
    }

    // == Private Methods ==
    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Draw background
        Background background = controller.getBackground();
        batch.draw(backgroundTexture, background.getX(), background.getY(), background.getWidth(), background.getHeight());
        // Draw player
        Player player = controller.getPlayer();
        batch.draw(playerTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Draw obstacles
        for (Obstacle obstacle : controller.getObstacles()) {
            batch.draw(obstacleTexture, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }
        batch.end();
    }

    private void renderUI() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);

        font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);
        font.draw(batch, scoreText, GameConfig.HUD_WIDTH - layout.width - 20, GameConfig.HUD_HEIGHT - layout.height);
        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        Player player = controller.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = controller.getObstacles();
        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }
    }


}
