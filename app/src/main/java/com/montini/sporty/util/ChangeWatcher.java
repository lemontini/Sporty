package com.montini.sporty.util;

import android.text.Editable;
import android.text.TextWatcher;

public class ChangeWatcher {

    private boolean contentChanged = false;

    // instantiates a new TextWatcher for every new EditText view (every time getInstance() is called)
    public TextWatcher getInstance() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                contentChanged = true; // changes in any of EditText fields produces isContentChanged = true
            }
        };

    }

    public boolean isContentChanged() { return contentChanged; }
    public void setContentChanged() { contentChanged = true; }
}
