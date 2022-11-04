package org.palladiosimulator.analyzer.slingshot.monitor.interpreter;

import javax.inject.Inject;

import org.palladiosimulator.analyzer.slingshot.core.events.PreSimulationConfigurationStarted;
import org.palladiosimulator.analyzer.slingshot.core.extension.SimulationBehaviorExtension;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.returntypes.Result;
import org.palladiosimulator.monitorrepository.MonitorRepository;

public class MonitorRepositoryInterpreterBehavior implements SimulationBehaviorExtension {

	private final MonitorRepository monitorRepository;
	
	@Inject
	public MonitorRepositoryInterpreterBehavior(final MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}
	
	@Subscribe
	public Result onPreSimulationConfigurationStarted(final PreSimulationConfigurationStarted event) {
		final MonitorModelVisitor visitor = new MonitorModelVisitor();
		return Result.from(visitor.doSwitch(monitorRepository));
	}
	
}
