package client;

public class TopicMgtItem {

	private String topic;
	private boolean subscribed;
	
	public TopicMgtItem(String topic, boolean subscribed) {
		this.topic = topic;
		this.subscribed = subscribed;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public boolean isSubscribed() {
		return this.subscribed;
	}
	
	@Override
	public boolean equals(Object item) {
		if (item instanceof TopicMgtItem) {
			return this.topic.equalsIgnoreCase(((TopicMgtItem) item).getTopic());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return topic.hashCode();
	}
	
	@Override
	public String toString() {
		return topic + ": " + (subscribed ? "true" : "false");
	}
}
