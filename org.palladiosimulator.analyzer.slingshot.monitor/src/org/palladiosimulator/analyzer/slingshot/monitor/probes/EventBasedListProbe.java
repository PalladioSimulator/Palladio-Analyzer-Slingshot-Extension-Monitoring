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
 * A probe that is {@link DESEvent}-based. Measurements/Probes are taken when
 * the associated {@link DESEvent} was published.
 *
 * @author Julijan Katic
 *
 * @param <V> The value type of the measurement.
 * @param <Q> The quantity type of the measurement.
 */
public abstract class EventBasedListProbe<V, Q extends Quantity> extends EventBasedProbe<V, Q> {

	/**
	 * Constructs an event-based probe with
	 * {@link EventDistinguisher#DEFAULT_DISTINGUISHER}.
	 *
	 * @param eventType        The event type to listen to.
	 * @param metricDesciption A metric description needed by the super-class.
	 */
	protected EventBasedListProbe(final MetricDescription metricDesciption) {
		this(metricDesciption, EventDistinguisher.DEFAULT_DISTINGUISHER);

	}

	private final BaseMetricDescription getTimeMetricDescription() {
		return (BaseMetricDescription) ((MetricSetDescription) this.getMetricDesciption()).getSubsumedMetrics().get(0);
	}

	private final BaseMetricDescription getValueMetricDescription() {
		return (BaseMetricDescription) ((MetricSetDescription) this.getMetricDesciption()).getSubsumedMetrics().get(1);
	}

	/**
	 * Constructs an event-based probe.
	 *
	 * @param eventType        The event type to listen to.
	 * @param metricDesciption A metric description needed by the super-class.
	 * @param distinguisher    The distinguisher that is used for creating
	 *                         {@link RequestContext}s.
	 */
	public EventBasedListProbe(final MetricDescription metricDescription,
			final EventDistinguisher distinguisher) {
		super(metricDescription, distinguisher);
	}

	/**
	 * Takes a measurement of the event type. The passed event must be of the given
	 * type by {@link #getEventType()}.
	 * <p>
	 * This calls {@link #getMeasure(DESEvent)} and wraps it into a
	 * {@link ProbeMeasurement} and notifies the listeners.
	 *
	 * @param event The event that happened and is of type {@link #getEventType()}.
	 * @throws IllegalArgumentException if the passed event is not of the type
	 *                                  returned by {@link #getEventType()}.
	 * @see #getMeasurement(DESEvent)
	 */
	@Override
	public void takeMeasurement(final DESEvent event) {
		final ProbeMeasurement probeMeasurement = this.getProbeMeasurement(event);
		this.notifyMeasurementSourceListener(probeMeasurement);
	}

	public Measure<Double, Duration> getTime(final DESEvent event) {
		return Measure.valueOf(event.time(), SI.SECOND);
	}

	/**
	 * An implementation that returns a {@link Measure} from a {@link DESEvent}.
	 * That measure is needed by {@link #takeMeasurement(DESEvent)}.
	 *
	 * @param event The event that happened.
	 * @return A {@link Measure} from the event.
	 */
	@Override
	public abstract Measure<V, Q> getMeasurement(final DESEvent event);

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
