package com.obstacleavoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    // Describes assets to be loaded by filename and type
    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    // We do not need these anymore because we are using atlas
//    public static final AssetDescriptor<Texture> BACKGROUND =
//            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_BACKGROUND, Texture.class);
//    public static final AssetDescriptor<Texture> PLAYER =
//            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_PLAYER, Texture.class);
//    public static final AssetDescriptor<Texture> OBSTACLE =
//            new AssetDescriptor<Texture>(AssetPaths.TEXTURE_OBSTACLE, Texture.class);

    // We just need the one reference to our atlas texture
    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    // Asset descriptor for UI
    public static final AssetDescriptor<TextureAtlas> UI =
            new AssetDescriptor<TextureAtlas>(AssetPaths.UI, TextureAtlas.class);

    private AssetDescriptors() {
    }
}
