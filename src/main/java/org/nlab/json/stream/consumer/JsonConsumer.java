package org.nlab.json.stream.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jooq.lambda.fi.util.function.CheckedFunction;
import org.nlab.exception.UncheckedExecutionException;
import org.nlab.json.stream.JsonStream;
import org.nlab.json.stream.context.StreamContext;
import org.nlab.json.stream.matcher.EventMatcher;
import org.nlab.json.stream.matcher.EventMatchers;


public class JsonConsumer {

	private final JsonStream stream;

	private final List<CheckedFunction<StreamContext, Boolean>> consumers = new ArrayList<>();

	public JsonConsumer(JsonStream stream) {
		this.stream = Objects.requireNonNull(stream);
	}


	public JsonConsumer matchJsonPath(String query, CheckedConsumeAndContinueConsumer<StreamContext> consumer) {
		addConsumer(EventMatchers.jsonPath(query, consumer));
		return this;
	}

	public JsonConsumer matchJsonPath(String query, CheckedFunction<StreamContext, Boolean> consumer) {
		addConsumer(EventMatchers.jsonPath(query, consumer));
		return this;
	}


	public JsonConsumer matchObjects(String[] elements, CheckedFunction<StreamContext, Boolean> consumer) {
		addConsumer(EventMatchers.objects(elements, consumer));
		return this;
	}

	public JsonConsumer matchObjects(String[] elements, CheckedConsumeAndContinueConsumer<StreamContext> consumer) {
		addConsumer(EventMatchers.objects(elements, consumer));
		return this;
	}


	public JsonConsumer matchObject(String element, CheckedFunction<StreamContext, Boolean> consumer) {
		addConsumer(EventMatchers.object(element, consumer));
		return this;
	}

	public JsonConsumer matchObject(String element, CheckedConsumeAndContinueConsumer<StreamContext> consumer) {
		addConsumer(EventMatchers.object(element, consumer));
		return this;
	}

	public JsonConsumer match(Predicate<StreamContext> predicate, CheckedFunction<StreamContext, Boolean> consumer) {
		addConsumer(new EventMatcher(predicate, consumer));
		return this;
	}

	public JsonConsumer match(Predicate<StreamContext> predicate, CheckedConsumeAndContinueConsumer<StreamContext> consumer) {
		addConsumer(new EventMatcher(predicate, consumer));
		return this;
	}
	
	
	public JsonConsumer addConsumer(CheckedFunction<StreamContext, Boolean> consumer) {
		consumers.add(consumer);
		return this;
	}


	/**
	 * Consume the Stream
	 *
	 */
	public void consume() {

		try (Stream<StreamContext> stream = this.stream) {
			stream.forEach(c -> {
				for (CheckedFunction<StreamContext, Boolean> consumer : consumers) {
                    try {
                        if (!consumer.apply(c)) {
                            break;
                        }
                    } catch (Throwable throwable) {
                        throw new UncheckedExecutionException(throwable);
                    }
                }
			});
		}
	}
}
