/*
    Copyright (C) 2023 Nordix Foundation.
    For a full list of individual contributors, please see the commit history.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    SPDX-License-Identifier: Apache-2.0
*/
package tech.est.eiffel.translator.cdevents.rabbitmq;

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
