package org.palladiosimulator.analyzer.slingshot.monitor;

import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotExtension;
import org.palladiosimulator.analyzer.slingshot.monitor.interpreter.MonitorRepositoryInterpreterBehavior;
import org.palladiosimulator.analyzer.slingshot.monitor.ui.MonitorRepositoryLaunchConfig;
import org.palladiosimulator.analyzer.slingshot.monitor.ui.MonitorRepositoryProvider;
import org.palladiosimulator.monitorrepository.MonitorRepository;

public class MonitorModule extends AbstractSlingshotExtension {

	@Override
	protected void configure() {
		// Behaviors
		install(MonitorRepositoryInterpreterBehavior.class);

		// Launch Config & Model File
		install(MonitorRepositoryLaunchConfig.class);
		provideModel(MonitorRepository.class, MonitorRepositoryProvider.class);
	}
}
