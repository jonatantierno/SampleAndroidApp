
package com.finapps;

import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.runners.model.InitializationError;

import java.io.File;

public class RoboelectricSampleTestRunner extends RobolectricTestRunner {

    /**
     * Call this constructor to specify the location of resources and AndroidManifest.xml.
     * @param testClass
     * @throws InitializationError
     */
    public RoboelectricSampleTestRunner(@SuppressWarnings("rawtypes")
    final Class testClass) throws InitializationError {
        super(testClass, new RobolectricConfig(new File("../SampleAndroidApp/AndroidManifest.xml"), new File(
                "../SampleAndroidApp/res")));
    }
}
