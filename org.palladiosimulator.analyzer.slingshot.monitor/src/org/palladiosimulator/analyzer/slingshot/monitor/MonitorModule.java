package org.palladiosimulator.analyzer.slingshot.monitor;

import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotExtension;
import org.palladiosimulator.analyzer.slingshot.monitor.calculator.CalculatorFactoryProvider;
import org.palladiosimulator.analyzer.slingshot.monitor.interpreter.MonitorRepositoryInterpreterBehavior;
import org.palladiosimulator.analyzer.slingshot.monitor.probes.ProbeFrameworkContextProvider;
import org.palladiosimulator.probeframework.ProbeFrameworkContext;
import org.palladiosimulator.probeframework.calculator.IGenericCalculatorFactory;

public class MonitorModule extends AbstractSlingshotExtension {
	@Override
	protected void configure() {
		install(MonitorRepositoryInterpreterBehavior.class);
		bind(IGenericCalculatorFactory.class).toProvider(CalculatorFactoryProvider.class);
		bind(ProbeFrameworkContext.class).toProvider(ProbeFrameworkContextProvider.class);
	}

	@Override
	public String getName() {
		return MonitorModule.class.getSimpleName();
	}
}
