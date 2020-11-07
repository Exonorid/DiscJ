package us.exonorid.discj;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TimeUtil {
	public static OffsetDateTime getTimeFromSnowflake(long snowflake) {
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli((snowflake >> 22) + 1420070400000L), ZoneId.of("UTC"));
	}
	
	public static OffsetDateTime getTimeFromISO(String time) {
		return OffsetDateTime.parse(time);
	}
}
