package org.campagnelab.dl.somatic.learning.architecture;

import org.campagnelab.dl.framework.architecture.nets.NeuralNetAssembler;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * A network with three dense layers. This is the first neural net architecture we tried for detecting
 * somatic variations.
 * <p>
 * Created by fac2003 on 6/10/16.
 *
 * @author Fabien Campagne
 */
public class CalibrationAssembler extends AbstractNeuralNetAssembler implements NeuralNetAssembler {

    public MultiLayerConfiguration createNetwork() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate).regularization(regularization).l2(regularizationRate)
                .updater(Updater.ADAGRAD)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                        .weightInit(WEIGHT_INIT)
                        .activation("relu").learningRateDecayPolicy(learningRatePolicy)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .weightInit(WEIGHT_INIT)
                        .activation("relu").learningRateDecayPolicy(learningRatePolicy)
                        .build())
                .layer(2, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .weightInit(WEIGHT_INIT)
                        .activation("relu").learningRateDecayPolicy(learningRatePolicy)
                        .build())
                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .weightInit(WEIGHT_INIT)
                        .activation("sigmoid").learningRateDecayPolicy(learningRatePolicy)
                        .nIn(numHiddenNodes).nOut(numOutputs).build())
                .pretrain(false).backprop(true).build();
        return conf;

    }
}
