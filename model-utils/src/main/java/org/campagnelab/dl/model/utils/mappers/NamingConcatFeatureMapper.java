package org.campagnelab.dl.model.utils.mappers;

import java.util.Arrays;

/**
 * Created by rct66 on 8/4/16.
 *
 * This class is an alternative to ConcatFeatureMapper which enables featurename extraction.
 * This was created so that deprecated mappers would not need to have feature naming implemented.
 */
public class NamingConcatFeatureMapper extends ConcatFeatureMapper implements FeatureNameMapper {

    FeatureNameMapper mappers[];


    public NamingConcatFeatureMapper(FeatureNameMapper... featureMappers) {
        this.mappers = featureMappers;
        int offset = 0;
        int i = 1;
        offsets = new int[featureMappers.length + 1];
        offsets[0] = 0;
        for (FeatureNameMapper calculator : mappers) {
            numFeatures += calculator.numberOfFeatures();;
            offsets[i] = numFeatures;

            i++;
        }
    }

    public String getFeatureName(int i) {
        int indexOfDelegate = Arrays.binarySearch(offsets, i);
        if (indexOfDelegate < 0) {
            indexOfDelegate = -(indexOfDelegate + 1) - 1;
        }
        return this.mappers[indexOfDelegate].getFeatureName(i - offsets[indexOfDelegate]);
    }
}