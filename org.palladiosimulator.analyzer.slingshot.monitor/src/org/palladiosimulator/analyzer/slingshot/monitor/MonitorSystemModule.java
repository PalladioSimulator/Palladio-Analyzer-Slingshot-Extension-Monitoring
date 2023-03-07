package org.palladiosimulator.analyzer.slingshot.monitor;

import org.palladiosimulator.analyzer.slingshot.core.extension.AbstractSlingshotExtension;
import org.palladiosimulator.analyzer.slingshot.monitor.ui.MonitorRepositoryLaunchConfig;

public class MonitorSystemModule extends AbstractSlingshotExtension {

	@Override
	protected void configure() {
		install(MonitorRepositoryLaunchConfig.class);
	}
}
