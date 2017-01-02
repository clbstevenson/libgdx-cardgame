/*******************************************************************************
 * Copyright 2016 See AUTHORS files
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.exovum.tools.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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
