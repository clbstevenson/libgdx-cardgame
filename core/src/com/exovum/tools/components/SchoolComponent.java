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

/**
 * Created by exovu_000 on 12/9/2016.
 */
public class SchoolComponent implements Component{
    // school is 2x3 generally
    public static final float WIDTH = 12f;//2f;
    public static final float HEIGHT = 10f;//3f;
    // is there a reason to give the School a State?
    // maybe so it can only use or do certain tasks in certain states
    public static final String STATE_NORMAL = "DEFAULT";
}
