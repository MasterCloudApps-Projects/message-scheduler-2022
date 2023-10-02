package es.urjc.tfm.scheduly;

import java.util.concurrent.atomic.AtomicLong;

public class MongoIdGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public static long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
