package com.pakisoft.wordfinder.common

import java.time.Duration
import java.util.concurrent.Callable

import static java.time.Duration.ofMillis
import static org.awaitility.Awaitility.await

class AwaitUtil {

    static void waitFor(Callable condition, int atMost = 100) {
        await()
                .pollInterval(ofMillis(1))
                .atMost(ofMillis(atMost))
                .until(condition)
    }
}
