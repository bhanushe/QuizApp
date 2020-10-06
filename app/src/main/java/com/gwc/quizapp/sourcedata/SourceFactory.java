package com.gwc.quizapp.sourcedata;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SourceFactory {
    private static final String TAG = "SOURCEFACTORY";

    public static Source createSource(SourceType sampleSourceType) {
        Source returnSource = null;
        Class sourceClazz = sampleSourceType.sourceClass;
        Constructor sourceConstructor = null;
            try {
                sourceConstructor = sourceClazz.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "createSource: Could not find Declared Constructor", e);
            }
        try {
            returnSource = (Source) sourceConstructor.newInstance();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "createSource: Illegal Access", e);
        } catch (InstantiationException e) {
            Log.e(TAG, "createSource: Cannot Instantiate", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "createSource: Cannot Invoke Target", e);
        }

        return returnSource;
    }
}
