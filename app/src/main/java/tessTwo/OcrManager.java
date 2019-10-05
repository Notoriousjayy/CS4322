package tessTwo;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OcrManager {

    TessBaseAPI baseAPI = null;

    public void initAPI(){

        baseAPI = new TessBaseAPI();
        // after copy, path to trained data is getExternalFilesDir(null)+"/tessdata/"+"eng.traineddata";
        String dataPath = MainApplication.instance.getTessDataParentDirectory();
        baseAPI.init(dataPath, "eng");
    }
    public String startRecognizer(Bitmap bitmap){
        if(baseAPI == null)
            initAPI();
        baseAPI.setImage(bitmap);
        return baseAPI.getUTF8Text();
    }
}
