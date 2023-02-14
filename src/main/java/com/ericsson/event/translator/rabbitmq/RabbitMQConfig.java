package com.ericsson.event.translator.rabbitmq;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String topicExchangeName;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    /**
     * @return RabbitMQConfig queue
     */
    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    /**
     * @return TopicExchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**
     * @param exchange
     * @return Binding
     */
    @Bean
    Binding binding(TopicExchange exchange) {
        return BindingBuilder.bind(exchange).to(exchange).with(routingkey);
    }

    /**
     * @param queue
     * @param exchange
     * @return Binding
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    /**
     * @return ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * @param listenerAdapter
     * @return SimpleMessageListenerContainer
     */
    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(queue().getName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /**
     * @param receiver
     * @return MessageListenerAdapter
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQMsgReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
