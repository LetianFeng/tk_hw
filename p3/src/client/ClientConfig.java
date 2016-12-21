package client;

public class ClientConfig {
	
	public static String DEFAULT_BROKER_URL = "tcp://localhost:61616";
	public static String TOPIC_PREFIX = "T.";
	public static String USER_PREFEX = "U.";
	public static String DEFAULT_PUBLIC_CHANNEL = "PUBLIC";
	public static int DEFAULT_TIMEOUT = 1000;
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final int NO_ERROR = 0;
	public static final int INVALID_USER_NAME_ERROR = 1;
	public static final int ACTIVEMQ_NOT_START_UP_ERROR  = 2;
	public static final int UNKNOWN_ERROR = 3;
}
