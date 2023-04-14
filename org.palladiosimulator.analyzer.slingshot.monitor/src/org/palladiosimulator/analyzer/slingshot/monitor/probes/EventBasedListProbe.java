package org.palladiosimulator.analyzer.slingshot.monitor.probes;

import java.util.ArrayList;
import java.util.List;

import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Quantity;
import javax.measure.unit.SI;

import org.palladiosimulator.analyzer.slingshot.common.events.DESEvent;
import org.palladiosimulator.measurementframework.BasicMeasurement;
import org.palladiosimulator.measurementframework.MeasuringValue;
import org.palladiosimulator.measurementframework.measureprovider.MeasurementListMeasureProvider;
import org.palladiosimulator.metricspec.BaseMetricDescription;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.metricspec.MetricSetDescription;
import org.palladiosimulator.probeframework.measurement.ProbeMeasurement;

/**
 * A probe that is {@link DESEvent}-based and probes for a
 * {@link MeasurementListMeasureProvider}.
 *
 * Measurements/Probes are taken when the associated {@link DESEvent} was
 * published.
 *
 * Beware: This probe requires a {@link MetricSetDescription}.
 *
 * @author Sarah Stieß
 *
 * @param <V> The value type of the measurement.
 * @param <Q> The quantity type of the measurement.
 */
public abstract class EventBasedListProbe<V, Q extends Quantity> extends EventBasedProbe<V, Q> {

	/**
	 * Constructs an event-based probe with
	 * {@link EventDistinguisher#DEFAULT_DISTINGUISHER}.
	 *
	 * @param metricDescription A metric description needed by the super-class.
	 */
	protected EventBasedListProbe(final MetricDescription metricDescription) {
		super(metricDescription);

		if (!(metricDescription instanceof MetricSetDescription)) {
			throw new IllegalArgumentException(String.format(
					"Illegal Metric Decription for Unary Calculator. Got %s %s but require a MetricSetDescription.",
					metricDescription.getClass().getSimpleName(), metricDescription.getName()));
		}
	}

	/**
	 * Constructs an event-based probe.
	 *
	 * @param metricDescription A metric description needed by the super-class.
	 * @param distinguisher     The distinguisher that is used for creating
	 *                          {@link RequestContext}s.
	 */
	public EventBasedListProbe(final MetricDescription metricDescription,
			final EventDistinguisher distinguisher) {
		super(metricDescription, distinguisher);
	}

	private final BaseMetricDescription getTimeMetricDescription() {
		return (BaseMetricDescription) ((MetricSetDescription) this.getMetricDesciption()).getSubsumedMetrics().get(0);
	}

	private final BaseMetricDescription getValueMetricDescription() {
		return (BaseMetricDescription) ((MetricSetDescription) this.getMetricDesciption()).getSubsumedMetrics().get(1);
	}

	public Measure<Double, Duration> getTime(final DESEvent event) {
		return Measure.valueOf(event.time(), SI.SECOND);
	}

	@Override
	protected ProbeMeasurement getProbeMeasurement(final DESEvent event) {
		final List<MeasuringValue> list = new ArrayList<>(2);

		/* TIME */
		final MeasuringValue pointInTimeMeasurement = new BasicMeasurement<>(this.getTime(event),
				this.getTimeMetricDescription()).getMeasuringValueForMetric(this.getTimeMetricDescription());
		list.add(pointInTimeMeasurement);
		/* VALUE */
		final MeasuringValue valueFooMeasurement = new BasicMeasurement<>(this.getMeasurement(event),
				this.getValueMetricDescription()).getMeasuringValueForMetric(this.getValueMetricDescription());
		list.add(valueFooMeasurement);

		final MeasurementListMeasureProvider resultMeasurement = new MeasurementListMeasureProvider(list);
		return new ProbeMeasurement(resultMeasurement, this, this.getDistinguisher().apply(event));
	}
}
