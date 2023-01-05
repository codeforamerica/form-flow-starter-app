# Form Flow Starter Application

Table of Contents
=================
<!--
    **  This is not automatically generated. **
    Update this section when you update sections now.
    Please don't go more than three layers deep, so we can keep the TOC
    a reasonable size.
-->

* [Form Flow Concepts](#form-flow-concepts)
* [Defining Screens](#defining-screens)
    * [Using Thymeleaf](#using-thymeleaf)
        * [Icon reference](#icon-reference)
* [Defining Conditions](#defining-conditions)
    * [Using conditions in templates](#using-conditions-in-templates)
* [Defining Static Pages](#defining-static-pages)
* [Development setup](#development-setup)
    * [System Dependencies](#system-dependencies)
        * [Java Development Kit](#java-development-kit)
        * [Set up jenv to manage your jdk versions](#set-up-jenv-to-manage-your-jdk-versions)
        * [Gradle](#gradle)
    * [Start the local databases](#start-the-local-databases)
    * [Setup EnvFile in IntelliJ](#setup-envfile-in-intellij)
    * [Setup Application](#setup-application)
    * [Using a local version of the Form-Flow Library (For Form-Flow Library Developers):](#using-a-local-version-of-the-form-flow-library-for-form-flow-library-developers)
        * [Terminal](#terminal)
        * [IntelliJ](#intellij)
    * [Setup Fake Filler (optional, Chrome &amp; Firefox):](#setup-fake-filler-optional-chrome--firefox)
    * [Spring Profile: `dev`](#spring-profile-dev)
* [IntelliJ Setup](#intellij-setup)
    * [Connect Flows Config Schema](#connect-flows-config-schema)
    * [Applying Live Templates to your IntelliJ IDE](#applying-live-templates-to-your-intellij-ide)
    * [Using Live Templates](#using-live-templates)
    * [Contribute new Live Templates](#contribute-new-live-templates)

This is a standard Spring Boot application that uses the `form-flows` Java package as a library. It
can be customized to meet the needs of a web app, and is meant to be built upon. It's a plain,
boring (but modern) Spring app that uses common, frequently-used libraries throughout.

It contains example code for a simple, generic application for public benefits. An applicant
can fill out screens with their basic info, upload supporting documents, then submit it all.
Upon submission, they receive a simple SMS confirmation and a receipt email with a filled-in
application PDF. The entire experience is in both English and Spanish.

To power the form flow logic, this app depends on the `form-flows` Java library. That library is
included in `build.gradle` along with all other dependencies. The codebase for the `form-flows`
package is [open source](https://github.com/codeforamerica/form-flow).

A detailed explanation of form flow concepts can be found on in
the [form flow library's readme](https://github.com/codeforamerica/form-flow).

# Universal Basic Income (UBI) Form Flow

This chart below shows the flow created by the `flows-config.yaml` file in this repository.

```mermaid
flowchart 
    A[UBI Flow] --> B(howThisWorks)
    B --> C(languagePreferences)
    C --> D(fa:fa-person gettingToKnowYou)
    D --> E(fa:fa-pen personalInfo)
    E --> F(eligibility)
    F --> G(fa:fa-house housemates)
    G --> |Add Household Member| I(housemateInfo)
    G --> |No Members| K(fa:fa-dollar income)
    I --> J(householdList)
    J -- Add Another Member --> I
    J --> K
    K --> L(householdMemberIncome)
    L --subflow--> N(incomeTypes)
    N --> O(incomeAmounts)
    O --> P(annualHouseholdIncome)
    P -- Add Member Income --> L
    P --> Q(incomeComplete)
    Q --> R(fa:fa-star success)
```

## Static Pages

Unlike Screens, Static Pages are HTML content and are not part of a flow. Detailed information
about static pages can be found in
the [form-flow library documentation.](https://github.com/codeforamerica/form-flow#static-pages)

This application has three static pages served up by
the [StaticPageController](src/main/java/org/formflowstartertemplate/app/StaticPageController.java)
class:

* [index.html](src/main/resources/templates/index.hmtl)
* [faq.html](src/main/resources/templates/faq.html)
* [privacy.html](src/main/resources/templates/privacy.html)

# Development setup

## System dependencies

_Note: these instructions are specific to macOS, but the same dependencies do need to be installed
on Windows as well._

### Java Development Kit

If you do not already have Java 17 installed, we recommend doing this:

```
brew tap homebrew/cask-versions
brew install --cask temurin17
```

### Set up jenv to manage your jdk versions

First run `brew install jenv`.

Add the following to your `~/.bashrc` or `~/.zshrc`:

```
export PATH="$HOME/.jenv/bin:$PATH"
eval "$(jenv init -)"
```

For m1 macs, if the above snippet doesn't work, try:

```
export PATH="$HOME/.jenv/bin:$PATH"
export JENV_ROOT="/opt/homebrew/Cellar/jenv/"
eval "$(/opt/homebrew/bin/brew shellenv)"
eval "$(jenv init -)"
```

Reload your terminal, then finally run this from the repo's root directory:

```
jenv add /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
```

### Gradle

`brew install gradle`

### Start the local databases ###

- Install PostgreSQL 14 via an [official download](https://www.postgresql.org/download/)
    - Or on macOS, through homebrew: `brew install postgresql@14`

<!-- TODO: Is this the right way to create db/user? -->

- Create the database using the command line:
    - `$ createdb starter-app`
    - `$ createuser -s starter-app`

### Setup EnvFile in IntelliJ ###

We use a `.env` file to store secret, we use
the [EnvFile plugin](https://plugins.jetbrains.com/plugin/7861-envfile) to connect IntelliJ with
the `.env`.

- You will need to go [through their setup](https://plugins.jetbrains.com/plugin/7861-envfile).
- Follow EnvFile usage process [here](https://github.com/Ashald/EnvFile#usage) to setup Run
  Configurations with EnvFile.

### Setup Application  ###

- Use instructions from
  the [form-flow library here.](https://github.com/codeforamerica/form-flow#intellij-setup)
- Run the application using the `StarterApplication` configuration (found
  in `org.formflowstartertemplate.app`)

### Using a local version of the Form-Flow Library (For Form-Flow Library Developers): ###

To use the [form-flow](https://github.com/codeforamerica/form-flow) library locally:

1. Clone the form-flow repo in the same directory as the starter app. This line
   in [build.gradle](build.gradle) depends on it:
    ```
    implementation fileTree(dir: "$rootDir/../form-flow/lib/build/libs", include: '*.jar')
    ```
1. Ensure you build the jar.
1. Start the `form-flow-starter-app`.

### Setup Fake Filler (optional, Chrome & Firefox): ###

We use an automatic form filler to make manual test easier.

Install [Fake Filler for Chrome](https://chrome.google.com/webstore/detail/fake-filler/bnjjngeaknajbdcgpfkgnonkmififhfo)
or [Fake Filler for FireFox](https://addons.mozilla.org/en-US/firefox/addon/fake-filler/?utm_source=addons.mozilla.org&utm_medium=referral&utm_content=search)

- Go to [fakeFillerConfig.txt](fakeFillerConfig.txt), click on "Raw", then save the file to your
  computer.
- Open the Fake Filler Options then click
  on [Backup and Restore (chrome)](chrome-extension://bnjjngeaknajbdcgpfkgnonkmififhfo/options.html#/backup)
- Click on "Import Settings" and upload the config file that you saved above.
- Click
  on [Keyboard Shortcuts (chrome)](chrome-extension://bnjjngeaknajbdcgpfkgnonkmififhfo/options.html#/keyboard-shortcuts)
  to choose the shortcut you want to use to fill out the page.
