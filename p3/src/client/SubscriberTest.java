package client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.jms.JMSException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubscriberTest {

    private static Publisher publisherPublishSubscribe,
            publisherMultipleConsumers, publisherNonDurableSubscriber;
    private static Subscriber subscriberPublishSubscribe,
            subscriber1MultipleConsumers, subscriber2MultipleConsumers,
            subscriber1NonDurableSubscriber, subscriber2NonDurableSubscriber;
	private static Client client = new Client();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    	//Client client = new Client();
    	client.login("kecen", 1);

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        //publisherPublishSubscribe.closeConnection();

    	//client.shutDown();
        //subscriberPublishSubscribe.closeConnection();

    }

    @Test
    public void testGetGreeting() {
        try {

            subscriberPublishSubscribe = client.getSub();
            client.getSub().subscribe("kecen", false);
            client.getSub().subscribe("kecen", false);
        	System.out.println("test start");
            client.getPub().publishMessage("{\"content\":\"first #test# blog\", \"date\": \"2016-12-25 12:59:59\", \"sender\": \"kecen\", \"avatar\": \"1\"}", "T.public");
            client.getPub().publishMessage("{\"content\":\"first #test1# blog\", \"date\": \"2016-12-25 12:59:59\", \"sender\": \"kecen\", \"avatar\": \"1\"}", "T.public");
            client.getPub().publishMessage("{\"content\":\"first #test2# blog\", \"date\": \"2016-12-25 12:59:59\", \"sender\": \"kecen\", \"avatar\": \"1\"}", "T.public");
            System.out.println("subscribed topics are " + client.getTopicList());
            //client.shutDown();
        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }


}