package com.itheima.wheelpicker.util;


import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.agp.components.element.Element;
import ohos.agp.text.Font;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;

import java.io.*;
import java.util.Optional;

public final class AttrUtil {
    private static final String TAG = "AttrUtil";

    private static final int BUFFER_LENGTH = 8192;

    private static final int DEFAULT_ERROR = -1;

    private static final String RAW_FILE_PATH = "entry/resources/rawfile/";

    private AttrUtil() {
    }

    public static int getColorValue(AttrSet attrSet, String key, int defValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getColorValue().getValue();
        } else {
            return defValue;
        }
    }

    public static boolean getBooleanValue(AttrSet attrSet, String key, boolean isDefValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getBoolValue();
        } else {
            return isDefValue;
        }
    }

    public static Element getElementValue(AttrSet attrSet, String key, Element defValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getElement();
        } else {
            return defValue;
        }
    }

    public static String getStringValue(AttrSet attrSet, String key, String defValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getStringValue();
        } else {
            return defValue;
        }
    }

    public static float getDimension(AttrSet attrSet, String key, float defDimensionValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getDimensionValue();
        } else {
            return defDimensionValue;
        }
    }

    public static int getIntegerValue(AttrSet attrSet, String key, int defValue) {
        Optional<Attr> temp = attrSet.getAttr(key);
        if (temp.isPresent()) {
            return temp.get().getIntegerValue();
        } else {
            return defValue;
        }
    }

    public static Font createFont(Context context, String fontFamily) {
        String path = RAW_FILE_PATH + fontFamily;
        LogUtil.error(TAG, "Font Path : " + path);
        File file = new File(context.getDataDir(), fontFamily);
        try (OutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = context.getResourceManager().getRawFileEntry(path).openRawFile()) {
            byte[] buffers = new byte[BUFFER_LENGTH];
            int bytesRead = inputStream.read(buffers, 0, BUFFER_LENGTH);
            while (bytesRead != DEFAULT_ERROR) {
                outputStream.write(buffers, 0, bytesRead);
                bytesRead = inputStream.read(buffers, 0, BUFFER_LENGTH);
            }
        } catch (FileNotFoundException exception) {
            LogUtil.error(TAG, "loadFontFromFile : FileNotFoundException");
        } catch (IOException exception) {
            LogUtil.error(TAG, "loadFontFromFile : IOException");
        }
        return Optional.of(new Font.Builder(file).setWeight(Font.REGULAR).build()).get();
    }

    public static float getDimen(Context context, int id) {
        float result = 0;
        if (context == null) {
            LogUtil.error(TAG, "getDimen -> get null context");
            return result;
        }
        ResourceManager manager = context.getResourceManager();
        if (manager == null) {
            LogUtil.error(TAG, "getDimen -> get null ResourceManager");
            return result;
        }
        try {
            result = manager.getElement(id).getFloat();
        } catch (IOException e) {
            LogUtil.error(TAG, "getDimen -> IOException");
        } catch (NotExistException e) {
            LogUtil.error(TAG, "getDimen -> NotExistException");
        } catch (WrongTypeException e) {
            LogUtil.error(TAG, "getDimen -> WrongTypeException");
        }
        return result;
    }

    public static String getString(Context context, int i){
        try {
            return context.getResourceManager().getElement(i).getString();
        } catch (IOException | WrongTypeException | NotExistException e) {
            e.printStackTrace();
        }
        return null;
    }
}