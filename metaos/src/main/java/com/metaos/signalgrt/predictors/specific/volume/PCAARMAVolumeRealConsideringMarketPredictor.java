/*
 * Copyright 2011 - 2012
 * All rights reserved. License and terms according to LICENSE.txt file.
 * The LICENSE.txt file and this header must be included or referenced 
 * in each piece of code derived from this project.
 */
package com.metaos.signalgrt.predictors.specific.volume;

import java.util.*;
import java.util.logging.*;
import com.metaos.signalgrt.predictors.*;
import com.metaos.signalgrt.predictors.specific.*;
import com.metaos.signalgrt.predictors.simple.*;
import com.metaos.util.*;
import com.metaos.datamgt.*;

/**
 * Calculates DYNAMICALLY daily volume for the given stock in the given market.
 *
 * From previous day, it calculates market PCA, and applies to desired stock
 * to predict profile. Then ARMA(1,1) is applied to calculate deviations
 * from prediction dynamically.
 * <br/>
 * Prepared to be used for markets with opening and closing times, but not
 * with pauses during the session.
 * <br/>
 * Outliers are optionally removed.
 * <br/>
 * When predicting, unknown data is represented as NaN.
 */
public final class PCAARMAVolumeRealConsideringMarketPredictor 
        extends DynamicARMAVolumeProfilePredictor {
    private static final Logger log = Logger.getLogger(
            PCAARMAVolumeRealConsideringMarketPredictor.class.getPackage()
                    .getName());
    //
    // Public methods ---------------------------------------
    //

    /**
     * Creates a dynamic PCA-ARMA predictor which uses data from each day to
     * forecast data for next day.
     *
     * @param symbols list of market symbols to consider when calculating 
     * PCA-ARMA
     * @param consideredSymbol symbol to calculate real volume based on 
     * PCA-ARMA
     * @param minimumVariance minimum explained variance by model.
     */
    public PCAARMAVolumeRealConsideringMarketPredictor(
            final CalUtils.InstantGenerator instantGenerator,
            final double minimumVariance, final String[] symbols, 
            final String consideredSymbol) {
        super(new PCAVolumeRealConsideringMarketPredictor(
                instantGenerator, minimumVariance, symbols, consideredSymbol),
            instantGenerator, consideredSymbol, -1);
    }


    /**
     * Creates a dynamic PCA-ARMA predictor which uses data from each day to
     * forecast data for next day ignoring elements at the begining and at
     * the end of the trading day.
     *
     * @param minimumVariance minimum explained variance by model.
     * @param ignoreElementsHead number of elements to ignore from the first
     * element with value (maybe opening auction).
     * @param ignoreElementsHead number of elements to ignore from the last 
     * element with value (maybe closing auction).
     * @param cleanOutliers true to clean otuliers when learning.
     * @param consideredSymbol symbol to calculate real volume based on PCAARMA
     */
    public PCAARMAVolumeRealConsideringMarketPredictor(
            final CalUtils.InstantGenerator instantGenerator,
            final double minimumVariance, final String[] symbols, 
            final String consideredSymbol, final int ignoreElementsHead, 
            final int ignoreElementsTail, final boolean cleanOutliers) {
        super(new PCAVolumeRealConsideringMarketPredictor(
                instantGenerator, minimumVariance, symbols, consideredSymbol,
                ignoreElementsHead, ignoreElementsTail, cleanOutliers),
            instantGenerator, consideredSymbol, -1);
    }

    /**
     * Returns the human name of the predictor.
     */
    public String toString() {
        return "Not Normalized PCAARMA Volume Real Considering "
                + "all market values Predictor";
    }
}
