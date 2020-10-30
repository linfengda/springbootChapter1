package com.linfengda.sb.chapter1.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @description: RabbitMq生产者配置
 * @author: linfengda
 * @date: 2020-10-13 00:40
 */
@SpringBootConfiguration
public class RabbitMqProducerConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean(name = "connectionFactory")
    @Primary
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.host, this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost("myVirtualHost");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean(name = "mqMes2WmsExchangeGoodDelivery")
    FanoutExchange mqMes2WmsExchangeGoodDelivery() {
        return new FanoutExchange("mq.mes2wms.exchange.good.delivery");
    }

    @Bean(name = "mqMes2WmsQueueGoodDelivery")
    public Queue mqMes2WmsQueueGoodDelivery() {
        return new Queue("mq.mes2wms.queue.good.delivery", true);
    }

    @Bean(name = "mqGoodDeliveryBinding")
    public Binding mqGoodDeliveryBinding(@Qualifier("mqMes2WmsQueueGoodDelivery") Queue queue, @Qualifier("mqMes2WmsExchangeGoodDelivery") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean(name = "wmsRabbitTemplate")
    public RabbitTemplate wmsRabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate wmsRabbitTemplate = new RabbitTemplate(connectionFactory);
        wmsRabbitTemplate.setExchange("mq.mes2wms.exchange.good.delivery");
        wmsRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        wmsRabbitTemplate.setConfirmCallback(new MyMqConfirmCallback());
        return wmsRabbitTemplate;
    }
}
