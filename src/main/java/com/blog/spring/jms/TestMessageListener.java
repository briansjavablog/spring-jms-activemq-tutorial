
package com.blog.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 * Class handles incoming messages
 *
 * @see PointOfIssueMessageEvent
 */
public class TestMessageListener implements MessageListener
{

	private TestMessageSender messageSender_i;

	private static final Logger logger_c = Logger.getLogger(TestMessageListener.class);


	/**
	 * Method implements JMS onMessage and acts as the entry
	 * point for messages consumed by Springs DefaultMessageListenerContainer.
	 * When DefaultMessageListenerContainer picks a message from the queue it
	 * invokes this method with the message payload.
	 */
	public void onMessage(Message message)
	{
		logger_c.debug("Received message from queue [" + message +"]");

		/* The message must be of type TextMessage */
		if (message instanceof TextMessage)
		{
			try
			{
				String msgText = ((TextMessage) message).getText();
				logger_c.debug("About to process message: " + msgText);

				/* call message sender to put message onto second queue */
				messageSender_i.sendMessage(msgText);

			}
			catch (JMSException jmsEx_p)
			{
				String errMsg = "An error occurred extracting message";
				logger_c.error(errMsg, jmsEx_p);
			}
		}
		else
		{
			String errMsg = "Message is not of expected type TextMessage";
			logger_c.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}

	/**
	 * Sets the message sender.
	 *
	 * @param messageSender_p the new message sender
	 */
	public void setTestMessageSender(TestMessageSender messageSender_p)
	{
		this.messageSender_i = messageSender_p;
	}
}
