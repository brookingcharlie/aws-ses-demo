package org.example

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.*
import com.github.jknack.handlebars.Handlebars

class StandardEmailSender(val client: AmazonSimpleEmailService, val template: Template) {
    fun send(content: Content) {
        val handlebars = Handlebars()
        val personalisedSubject = handlebars.compileInline(template.subject).apply(content.data)
        val personalisedText = handlebars.compileInline(template.html).apply(content.data)
        val personalisedHtml = handlebars.compileInline(template.text).apply(content.data)

        val request = SendEmailRequest()
            .withSource(content.from)
            .withDestination(Destination().withToAddresses(content.to))
            .withMessage(
                Message()
                    .withSubject(Content().withCharset("UTF-8").withData(personalisedSubject))
                    .withBody(
                        Body()
                            .withHtml(Content().withCharset("UTF-8").withData(personalisedText))
                            .withText(Content().withCharset("UTF-8").withData(personalisedHtml))
                    )
            )
        client.sendEmail(request)
    }
}