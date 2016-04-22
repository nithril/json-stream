package org.nlab.json.stream.consumer;

import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedFunction;

/**
 * Created by nlabrot on 19/03/15.
 */
@FunctionalInterface
public interface CheckedConsumeAndContinueConsumer<T> extends CheckedConsumer<T>, CheckedFunction<T, Boolean> {


	void accept(T t) throws Throwable;

	@Override
	default Boolean apply(T t) throws Throwable {
		accept(t);
		return true;
	}
}
