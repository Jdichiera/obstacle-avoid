package com.obstacleavoid.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

// This is used to pack assets into an atlas
public class AssetPacker {
    // Draw an outline around images
    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        // Pack the gameplay assets
        TexturePacker.process(settings, RAW_ASSETS_PATH + "/gameplay", ASSETS_PATH + "/gameplay", "gameplay");

        // Pack the UI assets
        TexturePacker.process(settings, RAW_ASSETS_PATH + "/ui",  ASSETS_PATH + "/ui", "ui");
    }
}
