package org.example

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class TemplatedEmailSender(val client: AmazonSimpleEmailService, val template: Template) {
    private val templateName = "WelcomeTemplate"

    fun send(content: Content) {
        client.createTemplate(
            CreateTemplateRequest().withTemplate(
                Template()
                    .withTemplateName(templateName)
                    .withSubjectPart(template.subject)
                    .withTextPart(template.text)
                    .withHtmlPart(template.html)
            )
        )
        client.sendTemplatedEmail(
            SendTemplatedEmailRequest()
                .withSource(content.from)
                .withDestination(Destination().withToAddresses(listOf(content.to)))
                .withTemplate(templateName)
                .withTemplateData(jacksonObjectMapper().writeValueAsString(content.data))
        )
        client.deleteTemplate(DeleteTemplateRequest().withTemplateName(templateName))
    }
}