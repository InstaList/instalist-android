/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.view.touchlistener;

import android.view.View;

/**
 * Created by TS on 11.05.2015.
 */
public interface IOnItemTouchEvents {


    /**
     * Event fired when swiped right over an element.
     * @param view the affected view.
     */
    public void onSwipeRight(View view);

    /**
     * Event fired when swiped left over an element.
     * @param view the affected view.
     */
    public void onSwipeLeft(View view);

    /**
     * Event fired when tapped on an element.
     * @param view the affected view.
     */
    public void onSingleTap(View view);

    /**
     * Event fired when long tap on an element.
     * @param view the affected view.
     */
    public void onLongTap(View view);

}
