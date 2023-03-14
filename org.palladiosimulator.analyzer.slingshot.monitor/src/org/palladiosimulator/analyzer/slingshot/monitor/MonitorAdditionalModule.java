package org.palladiosimulator.analyzer.slingshot.monitor;

import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotSimulationExtension;
import org.palladiosimulator.analyzer.slingshot.monitor.calculator.CalculatorFactoryProvider;
import org.palladiosimulator.analyzer.slingshot.monitor.probes.ProbeFrameworkContextProvider;
import org.palladiosimulator.probeframework.ProbeFrameworkContext;
import org.palladiosimulator.probeframework.calculator.IGenericCalculatorFactory;

public class MonitorAdditionalModule extends AbstractSlingshotSimulationExtension {

	@Override
	protected void configure() {
		bind(IGenericCalculatorFactory.class).toProvider(CalculatorFactoryProvider.class);
		bind(ProbeFrameworkContext.class).toProvider(ProbeFrameworkContextProvider.class);
	}
}
