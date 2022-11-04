package org.palladiosimulator.analyzer.slingshot.monitor.ui;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.analyzer.slingshot.core.extension.ModelProvider;
import org.palladiosimulator.analyzer.slingshot.core.extension.PCMResourceSetPartitionProvider;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.monitorrepository.MonitorRepository;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;

@Singleton
public class MonitorRepositoryProvider implements ModelProvider<MonitorRepository> {
	
	private final PCMResourceSetPartitionProvider resourceSet;
	
	@Inject
	public MonitorRepositoryProvider(final PCMResourceSetPartitionProvider resourceSet) {
		this.resourceSet = resourceSet;
	}

	@Override
	public MonitorRepository get() {
		final List<EObject> monitors = resourceSet.get().getElement(MonitorRepositoryPackage.eINSTANCE.getMonitorRepository());
		if (monitors.size() == 0) {
			throw new IllegalStateException("Monitor not present: List size is 0.");
		}
		return (MonitorRepository) monitors.get(0);
	}

}
