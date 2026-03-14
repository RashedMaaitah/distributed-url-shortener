package dev.rashed.urlshortener.infrastructure.id;

///
/// A Snowflake ID generator for generating unique 64-bit IDs across distributed systems.
///
/// ## ID Format
/// ```text
/// 1 bit unused | 41 bits timestamp | 10 bits machine id | 12 bits sequence
/// ```
///
/// - **1 bit unused**: Always 0, used to keep IDs positive.
/// - **41 bits timestamp**: Milliseconds since a custom epoch (`2023-01-01 00:00:00 UTC`).
/// - **10 bits machine id**: 5 bits for `workerId` + 5 bits for `datacenterId`.
/// - **12 bits sequence**: Incremented for each ID generated in the same millisecond.
///
/// ## Maximum IDs Per Millisecond
/// - Sequence bits = 12 → allows `2^12 = 4096` IDs per millisecond per machine.
/// - If more than 4096 IDs are requested in the same millisecond, the generator waits for the **next millisecond** to continue.
///
/// ## Thread Safety
/// - The `nextId()` method is `synchronized` to prevent multiple threads from generating the **same sequence number** at the same millisecond on the same machine.
/// - This ensures uniqueness of IDs even under high concurrency.
///
/// ## Example Usage
/// ```java
/// SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);
/// long id = generator.nextId();
/// ```
public class SnowflakeIdGenerator {
    private final long epoch = 1672531200000L; // 2023-01-01 00:00:00 UTC
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // [ timestamp ][ datacenterId ][ workerId ][ sequence ]
        return ((timestamp - epoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
