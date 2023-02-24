package org.palladiosimulator.analyzer.slingshot.monitor.interpreter;

import javax.inject.Inject;

import org.palladiosimulator.analyzer.slingshot.core.events.PreSimulationConfigurationStarted;
import org.palladiosimulator.analyzer.slingshot.core.extension.SimulationBehaviorExtension;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.Subscribe;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.EventCardinality;
import org.palladiosimulator.analyzer.slingshot.eventdriver.annotations.eventcontract.OnEvent;
import org.palladiosimulator.analyzer.slingshot.eventdriver.returntypes.Result;
import org.palladiosimulator.analyzer.slingshot.monitor.data.events.MonitoringEvent;
import org.palladiosimulator.analyzer.slingshot.monitor.data.events.modelvisited.MonitorModelVisited;
import org.palladiosimulator.monitorrepository.MonitorRepository;

@OnEvent(when = PreSimulationConfigurationStarted.class, then = { MonitorModelVisited.class }, cardinality = EventCardinality.MANY)
public class MonitorRepositoryInterpreterBehavior implements SimulationBehaviorExtension {

	private final MonitorRepository monitorRepository;

	@Inject
	public MonitorRepositoryInterpreterBehavior(final MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	@Subscribe
	public Result<MonitoringEvent> onPreSimulationConfigurationStarted(final PreSimulationConfigurationStarted event) {
		final MonitorModelVisitor visitor = new MonitorModelVisitor();
		return Result.from(visitor.doSwitch(monitorRepository));
	}

}
