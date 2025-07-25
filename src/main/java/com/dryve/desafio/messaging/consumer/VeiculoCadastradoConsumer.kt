package com.dryve.desafio.messaging.consumer

import com.dryve.desafio.event.VeiculoCadastradoEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class VeiculoCadastradoConsumer {

    private val logger = LoggerFactory.getLogger(VeiculoCadastradoConsumer::class.java)

    @RabbitListener(queues = ["veiculo.cadastrado"])
    fun ouvir(event: VeiculoCadastradoEvent) {
        logger.info("Evento recebido no RabbitMQ: {}", event)
    }
}
