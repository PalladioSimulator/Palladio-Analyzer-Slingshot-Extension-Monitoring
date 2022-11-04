package org.palladiosimulator.analyzer.slingshot.monitor.recorder.decorator;

import javax.inject.Inject;

import org.palladiosimulator.analyzer.slingshot.monitor.observer.EventBasedMeasurementObserver;
import org.palladiosimulator.analyzer.slingshot.monitor.utils.SlingshotCalculatorWrapper;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.probeframework.calculator.Calculator;
import org.palladiosimulator.probeframework.calculator.CalculatorProbeSet;
import org.palladiosimulator.probeframework.calculator.IGenericCalculatorFactory;

public final class MonitoringAttachingCalculatorFactoryDecorator implements IGenericCalculatorFactory {

	private final IGenericCalculatorFactory delegator;
	private final EventBasedMeasurementObserver observer;
	
	@Inject
	public MonitoringAttachingCalculatorFactoryDecorator(
			final IGenericCalculatorFactory delegator,
			final EventBasedMeasurementObserver observer) {
		this.delegator = delegator;
		this.observer = observer;
	}
	
	@Override
	public Calculator buildCalculator(MetricDescription arg0, MeasuringPoint arg1, CalculatorProbeSet arg2) {
		return this.setupCalculator(this.delegator.buildCalculator(arg0, arg1, arg2));
	}
	
	private Calculator setupCalculator(final Calculator calculator) {
		calculator.addObserver(SlingshotCalculatorWrapper.wrap(calculator, observer));
		return calculator;
	}
}
