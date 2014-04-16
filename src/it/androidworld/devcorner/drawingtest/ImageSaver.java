package it.androidworld.devcorner.drawingtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.View;

import java.io.FileOutputStream;

public class ImageSaver extends AsyncTask<ViewHolder,Void,Boolean>{

    private ViewHolder internalHolder;
    @Override
    protected Boolean doInBackground(ViewHolder... holders) {

        if(null == holders){
            internalHolder = null;
            return false;
        }

        internalHolder = holders[0];

        //prendiamo la Bitmap che ci serve
        if(null != internalHolder.getInsideView()) {
            final Bitmap targetBitmap = getBitmapFromView(internalHolder.getInsideView());
            return saveBitmapToFile(targetBitmap); //e la salviamo su file
        }


        return false;
    }

    protected void onPostExecute(Boolean result){

        if(result){
            //controlliamo che sia stato impostato un callback e che si possa eseguire
            if((null != internalHolder) && (null != internalHolder.getCallback()))
                internalHolder.getCallback().action(result);
        }
    }

    private Bitmap getBitmapFromView(View view){

        // calcoliamo la lunghezza della Bitmap
        int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.AT_MOST);
        // calcoliamo l'altezza della Bitmap
        int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.AT_MOST);
        // misuriamo la View
        view.measure(widthSpec, heightSpec);
        // ed impostiamone i parametri
        view.layout(view.getLeft(), view.getTop(), view.getMeasuredWidth() + view.getLeft(), view.getMeasuredHeight() + view.getTop());
        // attraverso cui possiamo creare la Bitmpa vera e propria
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // creiamo un Canvas per poter prendere ciò che la View ha renderizzato e salvarlo nel file
        Canvas c = new Canvas(bitmap);
        // Posizioniamo correttamente il cancas
        c.translate(-view.getScrollX(), -view.getScrollY());
        // e prendiamo il Canvas risultante
        view.draw(c);

        return bitmap;
    }

    private boolean saveBitmapToFile(Bitmap bitmap){

        //otteniamo il nome che ci interessa
        final String imagePath = internalHolder.getFilename();

        try {
            //e lo salviamo
            final FileOutputStream fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

            return true;
        } catch (Exception e) {
            //TODO gestire l'errore correttamente
            return false;
        }
    }


}
