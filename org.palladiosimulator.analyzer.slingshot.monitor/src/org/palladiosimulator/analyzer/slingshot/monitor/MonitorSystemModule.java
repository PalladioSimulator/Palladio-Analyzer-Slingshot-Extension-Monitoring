package org.palladiosimulator.analyzer.slingshot.monitor;

import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotExtension;
import org.palladiosimulator.analyzer.slingshot.monitor.ui.MonitorRepositoryLaunchConfig;
import org.palladiosimulator.analyzer.slingshot.monitor.ui.MonitorRepositoryProvider;
import org.palladiosimulator.monitorrepository.MonitorRepository;

public class MonitorSystemModule extends AbstractSlingshotExtension {

	@Override
	protected void configure() {
		install(MonitorRepositoryLaunchConfig.class);
		provideModel(MonitorRepository.class, MonitorRepositoryProvider.class);
	}
}
