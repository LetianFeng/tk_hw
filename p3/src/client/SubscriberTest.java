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

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        subscriberPublishSubscribe = new Subscriber("kecen", null);
        subscriberPublishSubscribe.subscribe("kecen", false);
        subscriberPublishSubscribe.subscribe("kecen", false);
        publisherPublishSubscribe = new Publisher("kecen", null);


    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        publisherPublishSubscribe.closeConnection();


        subscriberPublishSubscribe.closeConnection();

    }

    @Test
    public void testGetGreeting() {
        try {
        	System.out.println("test start");
            publisherPublishSubscribe.publishMessage("{\"content\":\"first blog\", \"date\": \"2016-12-25 12:59:59\", \"sender\": \"kecen\", \"avatar\": \"1\"}", "U.kecen");

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }


}