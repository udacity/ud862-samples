/*
 * Copyright 2015 Udacity, Inc
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
package com.udacity.pickpalette;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Map;

public class SwatchAdapter extends ArrayAdapter {

    public SwatchAdapter(Context context, Object [] swatches) {
        super(context, 0, swatches);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swatch_view, parent, false);
        }

        View colorView = convertView.findViewById(R.id.color_swatch);
        TextView colorTitle = (TextView)convertView.findViewById(R.id.color_title);
        colorView.setBackgroundColor(entry.getValue());
        colorTitle.setText(entry.getKey() + " (#"+ Integer.toHexString(entry.getValue()).toUpperCase()+")");

        return convertView;
    }
}
