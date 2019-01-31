package com.obstacleavoid.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.screen.game.GameScreen;
import com.obstacleavoid.screen.menu.MenuScreen;
import com.obstacleavoid.util.GdxUtils;


public class LoadingScreen extends ScreenAdapter {

    // == Constants ==
    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    // == Attributes ==
    // In a screen we always need a camera, viewport, and shaperenderer
    private OrthographicCamera camera;
    private Viewport viewPort;
    private ShapeRenderer renderer;

    private boolean changeScreen;

//    private OrthographicCamera hudCamera;
//    private Viewport hudViewport;
//    private BitmapFont font;
//    private SpriteBatch batch;
//    private final GlyphLayout layout = new GlyphLayout();

    private float progress;

    // After all assets are loaded, show the loading screen for this amount of time.
    private float waitTime = 0.75f;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    // == Constructor ==
    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == Public methods ==

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();

        // Before drawing anything and before setting projectionMatrix we need to apply the viewport
        // This tells openGl that we want to use the viewport. This is especially important when we are using multiple
        // viewports
        viewPort.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if(changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
    }

    @Override
    public void show() {
        // Initialize variables
        camera = new OrthographicCamera();
        viewPort = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

//        hudCamera = new OrthographicCamera();
//        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
//        batch = new SpriteBatch();
//        font = assetManager.get(AssetDescriptors.FONT);


        assetManager.load(AssetDescriptors.FONT);

        // Removing these because we are using the atlas texture for gameplay
//        assetManager.load(AssetDescriptors.BACKGROUND);
//        assetManager.load(AssetDescriptors.PLAYER);
//        assetManager.load(AssetDescriptors.OBSTACLE);

        // Load the gameplay atlas texture into the asset manager
        assetManager.load(AssetDescriptors.GAME_PLAY);

        // Load the UI atlas textures
        assetManager.load(AssetDescriptors.UI);
        // This blocks the program until all assets are loaded
//        assetManager.finishLoading();
    }

    @Override
    public void hide() {
        // NOTE: screens dont dispose automatically. They are just hidden
        // Thats why we dispose on hide.
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private static void waitMilis (long milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // == Private methods ==
    private void update(float delta) {
        // Simulate waiting for assets to load
//         waitMilis(100);

        // Getprogress will return a number between 0 and 1 showing the progress of
        // loading the assets
        progress = assetManager.getProgress();

        // if update returns true, all assets are loaded.
        if(assetManager.update()) {
            // if there are still assets to be loaded, then decrement waitTime
            waitTime -= delta;

            // if waitTime is 0 then load the game screen
            if(waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        // Center the progress bar
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

//        String loading = "Loading ... ";
//        layout.setText(font, loading);
//        font.draw(batch, loading, 20, GameConfig.HUD_HEIGHT - layout.height);

        // The width of the progress bar is multiplied by the progress (which is between 0 and 1)
        renderer.rect(progressBarX, progressBarY, progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }
}
