package com.dryve.desafio.event

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class VeiculoEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    fun publicar(event: VeiculoCadastradoEvent) {
        rabbitTemplate.convertAndSend("veiculo.cadastrado", event)
    }
}
