package com.znz.compass.znzlibray.common;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;

import java.text.SimpleDateFormat;

public interface ZnzConst {

    String EXTRA_IMAGE_LIST = "imagelist";

    String MODE_PORTRAIT_CAPTURE = "PORTRAIT";

    String MODE_LANDSCAPE_CAPTURE = "LANDSCAPE";

    String LOG_FILE_DIR = "log";

    String DATA_FILE_DIR = "data";

    String IMAGE_FILE_DIR = "image";

    String FILENAMEDIR = "ccblife";

    String LOG_FILENAME = "log.txt";

    int TIME_OUT = 10 * 1000;

    int LOAD_DATA_COUNT = 20;

    String AES_KEY = "UITN25LMUQC436IM";// ????????????SharedPreferences ??????????????????

    String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID, "sort_key",
            "sort_key_alt"};

    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sTime = new SimpleDateFormat("HH:mm");
    /**
     * SimpleDateFormat.
     */
    SimpleDateFormat sDate = new SimpleDateFormat("MM-dd");

    public static final int INDEX = 1;// ???????????? ?????????
    /**
     * intent code
     */
    String extra_data = "extra_data";

}
