package it.androidworld.devcorner.drawingtest;

import android.view.View;

/**
 * Created by tiwiz on 15/04/14.
 */
public class ViewHolder {

    public interface Callback{
        public void action(boolean result);
    }

    private View insideView;
    private Callback callback;
    private String filename;

    public View getInsideView() {
        return insideView;
    }

    public void setInsideView(View insideView) {
        this.insideView = insideView;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ViewHolder() {
        this(null,null, "");
    }

    public ViewHolder(View insideView, Callback callback, String filename) {
        this.insideView = insideView;
        this.callback = callback;
        this.filename = filename;
    }
}
