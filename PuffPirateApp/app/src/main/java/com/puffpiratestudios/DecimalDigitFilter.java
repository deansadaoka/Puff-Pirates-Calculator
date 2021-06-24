package com.puffpiratestudios;

import android.text.InputFilter;
import android.text.Spanned;

public class DecimalDigitFilter implements InputFilter{

    private final int digitsAfterZero;

    public DecimalDigitFilter(int digitsAfterZero) {
        this.digitsAfterZero = digitsAfterZero;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int dotPos = -1;
        int len = dest.length();
        for (int i = 0; i < len; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                dotPos = i;
                break;
            }
        }
        if (dotPos >= 0) {
            if (source.equals(".") || source.equals(",")) {
                return "";
            }
            if (dend <= dotPos) {
                return null;
            }
            if (len - dotPos > digitsAfterZero) {
                return "";
            }
        }
        return null;
    }
}
