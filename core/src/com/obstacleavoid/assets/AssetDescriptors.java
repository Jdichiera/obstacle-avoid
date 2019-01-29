package com.obstacleavoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetDescriptors {

    // Describes assets to be loaded by filename and type
    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);
    public static final AssetDescriptor<Texture> BACKGROUND =
            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_BACKGROUND, Texture.class);
    public static final AssetDescriptor<Texture> PLAYER =
            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_PLAYER, Texture.class);
    public static final AssetDescriptor<Texture> OBSTACLE =
            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_OBSTACLE, Texture.class);

    private AssetDescriptors() {
    }
}
