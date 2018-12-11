# AWS SES Kotlin Example

Example code (not production ready) showing two ways of sending
personalised emails using AWS's Simple Email Service (SES):

* `StandardEmailSender`, which does its own templating using Handlebars
* `TemplatedEmailSender`, which creates/uses a hosted Template via the AWS API

## Prerequisites

You need to have a trusted email address already set up in SES,
allowing you to send email *From* that address.

The AWS region is hard-coded to "us-east-1" since SES support isn't global;
if necessary, change this to the region where you've configured SES.

AWS credentials/profiles are picked-up from the standard places,
i.e. `~/.aws/credentials`, environment variables, etc.

## Usage

Run the `main` method with two command-line arguments:
a *From* address and *To* address.
