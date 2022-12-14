package org.palladiosimulator.analyzer.slingshot.monitor.recorder.decorator;

import javax.inject.Inject;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.analyzer.slingshot.monitor.observer.EventBasedMeasurementObserver;
import org.palladiosimulator.analyzer.slingshot.monitor.utils.SlingshotCalculatorWrapper;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;
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
	public Calculator buildCalculator(final MetricDescription arg0, final MeasuringPoint arg1, final CalculatorProbeSet arg2) {
		// create copy
		final MeasuringPointRepository repo = MeasuringpointFactory.eINSTANCE.createMeasuringPointRepository();
		final MeasuringPoint copy = EcoreUtil.copy(arg1);
		copy.setMeasuringPointRepository(repo);

		return this.setupCalculator(this.delegator.buildCalculator(arg0, copy, arg2));
	}

	private Calculator setupCalculator(final Calculator calculator) {
		calculator.addObserver(SlingshotCalculatorWrapper.wrap(calculator, observer));
		return calculator;
	}
}
