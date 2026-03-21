@Component
public class SnowflakeIdGenerator {

    private final long workerId = 1L;

    private final long sequenceBits = 12L;
    private final long workerIdBits = 10L;

    private final long maxSequence = ~(-1L << sequenceBits);

    private final long workerShift = sequenceBits;
    private final long timestampShift = sequenceBits + workerIdBits;

    private final long customEpoch = 1704067200000L; // Jan 1, 2024

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;

            if (sequence == 0) {
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - customEpoch) << timestampShift)
                | (workerId << workerShift)
                | sequence;
    }

    private long waitNextMillis(long timestamp) {
        while (timestamp == lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
