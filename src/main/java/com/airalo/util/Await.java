package com.airalo.util;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.concurrent.Callable;

public class Await {

	public static <T extends Exception> void waitFor(String message, int atMost, Duration interval, Class<T> clazz,
													 Callable<Boolean> conditionEvaluator) {
		await(message)
				.atMost(atMost, SECONDS)
				.pollInterval(interval)
				.pollInSameThread()
				.ignoreException(clazz)
				.until(conditionEvaluator);
	}
}
