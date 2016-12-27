package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;

import static com.exovum.ld37warmup.components.FontComponent.TYPE.TEMP;

/**
 * Created by exovu_000 on 12/10/2016.
 */
public class FontComponent implements Component {
    // font to use when rendering
    public BitmapFont font = null;
    // text of the font
    public GlyphLayout glyph = null;
    // will also use a TransformComponent for setting position
    public float targetWidth = 10f;
    public Color color = Color.BLACK;
    public float displayTime = 3600f;


    public enum TYPE {
        PERM, TEMP;
    }
    public TYPE type = TYPE.TEMP;


    public void setText(String text) {
        //if(font == null || glyph == null)
        //    return;
        Gdx.app.log("Font Component", "Setting text for Font Component");
        //glyph.setText(font, text, Color.BLACK, targetWidth, Align.left, true);
        glyph.setText(font, text);
    }
}
