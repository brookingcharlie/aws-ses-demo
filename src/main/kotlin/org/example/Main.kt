package org.example

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder

data class Template(val subject: String, val text: String, val html: String)
data class Content(val from: String, val to: String, val data: Data)
data class Data(val name: String)

fun main(args: Array<String>) {
    val client = AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(DefaultAWSCredentialsProviderChain())
        .withRegion("us-east-1")
        .build()

    val template = Template(
        "Welcome to Example Corp, {{name}}!",
        """
            Hello, {{name}}
            Welcome to Example Corp - see https://example.com/
        """.trimIndent(),
        """
            <h1>Hello, {{name}}</h1>
            <p>Welcome to <a href="https://example.com/">Example Corp</a>.
        """.trimIndent()
    )

    val content = Content(args[0], args[1], Data("Harry"))

    StandardEmailSender(client, template).send(content)
    TemplatedEmailSender(client,template).send(content)
}
