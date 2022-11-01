Table of Contents
=================

* [Form Flow Starter App](#form-flow-starter-app)
    * [Form Flow Concepts](#form-flow-concepts)
    * [Defining Flows](#defining-flows)
    * [Defining Screens](#defining-screens)
        * [Using Thymeleaf](#using-thymeleaf)
            * [Thymeleaf Model Data](#thymeleaf-model-data)
            * [Icon reference](#icon-reference)
    * [Defining Inputs](#defining-inputs)
    * [About Submissions](#about-submissions)
    * [Subflows](#subflows)
        * [Dedicated Subflow Screens](#dedicated-subflow-screens)
            * [Entry Screen](#entry-screen)
            * [Iteration Start Screen](#iteration-start-screen)
            * [Review Screen](#review-screen)
            * [Delete Confirmation Screen](#delete-confirmation-screen)
        * [Defining Subflows](#defining-subflows)
        * [Example flow-config.yaml with a docs subflow](#example-flow-configyaml-with-a-docs-subflow)
        * [When do you need to define subflow on a screen?](#when-do-you-need-to-define-subflow-on-a-screen)
    * [Defining Conditions](#defining-conditions)
        * [Using conditions in templates](#using-conditions-in-templates)
    * [Defining Static Pages](#defining-static-pages)
    * [Development setup](#development-setup)
        * [Install the following system dependencies:](#install-the-following-system-dependencies)
            * [Java Development Kit](#java-development-kit)
            * [Set up jenv to manage your jdk versions](#set-up-jenv-to-manage-your-jdk-versions)
            * [Gradle](#gradle)
        * [Start the local databases:](#start-the-local-databases)
        * [Authenticating the Library](#authenticating-the-library)
        * [Setup IntelliJ for the project:](#setup-intellij-for-the-project)
        * [Using a local version of the Form-Flow Library (For Form-Flow Library Developers):](#using-a-local-version-of-the-form-flow-library-for-form-flow-library-developers)
            * [Terminal](#terminal)
            * [IntelliJ](#intellij)
        * [Setup Fake Filler (optional, Chrome &amp; Firefox):](#setup-fake-filler-optional-chrome--firefox)
    * [About IntelliJ Live Templates](#about-intellij-live-templates)
        * [Applying Live Templates to your IntelliJ IDE](#applying-live-templates-to-your-intellij-ide)
        * [Using Live Templates](#using-live-templates)
        * [Contribute new Live Templates](#contribute-new-live-templates)
    * [Connect flows config schema with IntelliJ IDE](#connect-flows-config-schema-with-intellij-ide)

# Form Flow Starter App

This is a standard Spring Boot application that uses the `form-flows` Java package as a library. It
can be customized to meet the needs of a web app, and is meant to be built upon. It's a plain,
boring (but modern) Spring app that uses common, frequently-used libraries throughout.

It contains example code for a simple, generic application for public benefits. An applicant
can fill out screens with their basic info, upload supporting documents, then submit it all.
Upon submission, they receive a simple SMS confirmation and a receipt email with a filled-in
application PDF. The entire experience is in both English and Spanish.

To power the form flow logic, this app depends on the `form-flows` Java library. That library is
included in `build.gradle` along with all other dependencies. The codebase for the `form-flows`
package is [open source](https://example.com) (TBD).

Out-of-the-box, integrations can be set up with common third-party services:

- Intercom
- Google Analytics
- Mixpanel
- Optimizely
- Google Ads
- Facebook Ads

The relevant service keys and other settings are configurable in `application.yaml`.

## Form Flow Concepts ##

* Flows
* Inputs
* Screens
* Conditions
* Validations

Flows are the top-level construct that define the navigation between a collection of screens.
A flow can have many inputs to accept user data (e.g. first name, zip
code, email, file upload). Each input can have zero to many validations.

A flow also has many screens. Each screen can be made up of zero or more inputs. A flow has an
ordering of screens, and can use defined conditions to control navigation. Conditions use
submitted inputs to make a logical decision about showing or not showing a screen / part of a
screen.

```mermaid
erDiagram      
    Flow ||--|{ Screen : "ordered collection of"
    Flow ||--o{ Input : "collection of"
    Screen ||--o{ Input : displays
    Input ||--o{ Validation : "validated by"
    Input }|--o{ Condition: "determines"
```

## Defining Flows ##

To start, create a `flow-config.yaml` in `src/main/resources`.

TODO: define path in `application.yaml`?

You can define multiple flows
by [separating them with `---`](https://docs.spring.io/spring-boot/docs/1.2.0.M1/reference/html/boot-features-external-config.html#boot-features-external-config-multi-profile-yaml)
.

At it's base a flow as defined in yaml has a name, a flow object, and a collection of screens, their
next screens, any conditions for navigation between those screens, and optionally one or more
subflows.

A basic flow configuration could look like this:

```yaml
name: exampleFlow
flow:
  firstScreen:
    nextScreens:
      - name: secondScreen
  secondScreen:
    nextScreens:
      - name: thirdScreen
      - name: otherScreen
        condition: userSelectedExample
  thirdScreen:
    nextScreens:
      - name: success
  otherScreen:
    nextScreens:
      - name: success
  success:
    nextScreens: null
  ___
name: someOtherFlow
flow:
  otherFlowScreen:
```

[You can have autocomplete and validation for flows-config by connecting your intelliJ to the flows-config-schema.json](#connect-flows-config-schema-with-intellij-ide)

## Defining Screens ##

All screens must have an entry in the flows-config in order to be rendered. Additionally, each
screen
should have its own template defined in a folder respective to the flow that screen is contained
within.
Example `/src/resources/templates/<flowName>/<templateName>`.

We have provided a number of IntelliJ Live templates to make the creation of screens faster and
easier.
[More on Live Templates here](#about-intellij-live-templates).

When setting up a new flow, create a folder in `src/main/resources/templates` to hold all HTML
files.
In the starter app, we name the respective template folders after their respective flows.

For example, add an HTML file such
as `about-you.html` [in the flow's templates folder](src/main/resources/templates). Here is an
example using our [live templates for a form screen](#about-intellij-live-templates):

```html

<th:block th:replace="'fragments/form' :: form(action=${formAction}, content=~{::formContent})">
  <th:block th:ref="formContent">
    <div class="form-card__content">
      <th:block th:replace="'icons' :: 'clipboard'"></th:block>
      <th:block th:replace="'content' :: cardHeader(header='Tell us about yourself')"/>
      <th:block
          th:replace="'inputs' :: textInput(name='firstName', label='What's your first name?')"/>
      <th:block
          th:replace="'inputs' :: textInput(name='lastName', label='What's your last name?')"/>
      <th:block
          th:replace="'inputs' :: textInput(name='emailAddress', label='What's your email address?')"/>
    </div>
    <div class="form-card__footer">
      <th:block th:replace="'fragments/continueButton' :: continue"/>
    </div>
  </th:block>
</th:block>
```

### Using Thymeleaf

We use Thymeleaf for frontend views. Thymeleaf is a Java based HTML framework for frontend
templating.
[You can learn more about Thymeleaf here.](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

We use Thymeleaf's concept
of  [fragments](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#fragments) to store
complex mark up into simple reusable imports.

Fragments simplify the process of creating more complex HTML pages. Some places we use fragments
include input types, forms, page headers and footers, error handlers,
etc. [You can view these fragments
here.](src/main/resources/templates/fragments)

Thymeleaf is also capable of making direct calls to Java class methods using what is known as the
Spring Expression Language T operator. This allows you to implement Java code in your Thymeleaf
templates.
We provide two classes for this purpose:

- ConditionDefinitions
    - Houses methods which should always return Booleans and can be used to conditionally show or
      hide
      sections of a Thymeleaf template
- ViewUtilities
    - Houses methods for general purpose manipulation of data to display on the frontend in
      Thymeleaf templates

An example of using the T operator can be found in the `incomeAmounts` template from the starter
app.

```html

<main id="content" role="main" class="form-card spacing-above-35"
      th:with="selectedSelf=${T(org.codeforamerica.formflowstarter.app.config.ConditionDefinitions).incomeSelectedSelf(submission, uuid)},
                     houseHoldMemberName=${T(org.codeforamerica.formflowstarter.app.data.Submission).getSubflowEntryByUuid('income', uuid, submission).householdMember}">
  ...
</main>
```

#### Thymeleaf Model Data ####

We provide some data to the model for ease of use access in Thymeleaf templates. Below are the data
types
we pass and when they are available.

| Name              | Type                    | Availability                                                                     | Description                                                                                                                                                         |
|-------------------|-------------------------|----------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `flow`            | String                  | Always available                                                                 | The name of the flow the screen is contained within.                                                                                                                |
| `screen`          | String                  | Always available                                                                 | the name of the screen.                                                                                                                                             |
| `inputData`       | HashMap<String, Object> | Always available                                                                 | `inputData` is a HashMap of user submitted input data. If editing a subflow, `inputData` will only contain the data for that specific iteration within the subflow. |
| `submission`      | Submission              | Always available                                                                 | `submission` is the entire Submission object that contains a single users submission data.                                                                          |
| `formAction`      | String                  | Always available                                                                 | Is the correct endpoint for the forms `POST` action if `flows-config` is set up correctly.                                                                          |
| `errorMessages`   | ArrayList<String>       | On screens that fail validation                                                  | A list of error messages for inputs that failed validation.                                                                                                         |
| `subflow`         | String                  | On `deleteConfirmationScreen` screens                                            | This is the name of the subflow that the `deleteConfirmationScreen` screen belongs to.                                                                              |
| `noEntryToDelete` | Boolean                 | On `deleteConfirmationScreen` screens if corresponding `uuid` is no longer there | Indicates that the subflow entry containing a `uuid` is no longer available.                                                                                        |
| `reviewScreen`    | String                  | On `deleteConfirmationScreen` screens if corresponding `uuid` is no longer there | Name of the review screen for the subflow that the `deleteConfirmationScreen` belongs to.                                                                           |
| `subflowIsEmpty`  | Boolean                 | On `deleteConfirmationScreen` screens if no entries in a subflow exist           | Indicates that the subflow being accessed no longer has entries.                                                                                                    |
| `entryScreen`     | String                  | On `deleteConfirmationScreen` screens if no entries in a subflow exist           | Name of the entry screen for the subflow that the `deleteConfirmationScreen` belongs to.                                                                            |

[For more information on the T Operator see section 6.5.8 here.](https://docs.spring.io/spring-framework/docs/3.0.x/reference/expressions.html)

#### Icon reference

If you need to see a reference of all icons from the form flow library, you can paste this fragment
import into your template to quickly see a preview and names of icons:

```
<th:block th:replace="fragments/icons :: icons-list"></th:block>
```

## Defining Inputs ##

Inputs are defined in two places - the template in which they are rendered, and in a separate class
for validation.
The inputs class is defined in `/src/main/java/app/inputs/<nameOfFlowAsNameOfInputsClass>`

[When defining inputs we have provided a suite of input based Live Templates, more on that here.](#about-intellij-live-templates)

Live templates are provided for the following input types:

- `Checkbox`
- `Date`
- `Fieldset`
- `Money`
- `Number`
- `Radio`
- `Select`
- `SelectOption`
- `Text`
- `TextArea`
- `Phone`
- `Ssn`
- `YesOrNo`
- `Submit`
- `FileUpload` (TBD)

An example inputs class can be seen below, with example validations.

Please note that for single value inputs the type when defining the input is String. However, for
input types
that can contain more than one value, the type is ArrayList<String>.

```java
class Apply {

  @NotBlank(message = "{personal-info.provide-first-name}")
  String firstName;

  @NotBlank(message = "{personal-info.provide-last-name}")
  String lastName;

  String emailAddress;

  String phoneNumber;

  @NotEmpty(message = "{personal-info.please-make-a-gender-selection}")
  ArrayList<String> gender;
}
```

Validations for inputs use the JSR-303 bean validation paradigm, more specifically, Hibernate
validations. For a list of validation
decorators,
see [Hibernate's documentation.](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints)

## About Submissions ##

Submission data is stored in the `Submission` object, persisted to PostgreSQL via the Hibernate ORM.

```java
class Submission {

  @Id
  @GeneratedValue
  private Long id;

  private String flow;

  @CreationTimestamp
  @Temporal(TIMESTAMP)
  private Timestamp createdAt;

  @UpdateTimestamp
  @Temporal(TIMESTAMP)
  private Timestamp updatedAt;

  @Temporal(TIMESTAMP)
  private Timestamp submittedAt;

  @Type(JsonType.class)
  private Map<String, String> inputData = new HashMap<>();

}
```

The `inputData` field is a JSON object that stores input data from the inputs as a given
flow progresses. It can be used for defining conditions.

An instance variable `currentSubmission` is available for use in the `ScreenController` and
`inputData` is placed on the Thymeleaf model.

## Subflows ##

Subflows are repeating sections of one or more screens within a regular flow. These can be things
like household builders
that ask a repeating set of questions about members of a household. Subflows represent an array of
screens and their respective inputs (represented as a HashMap) where each item in the array is one
iteration.

### Dedicated Subflow Screens ###

These are screens that every subflow must have.

Here is an example of a *subflow* yaml:

```yaml
subflow:
  docs:
    entryScreen: docsEntry
    iterationStartScreen: docsStart
    reviewScreen: docsReview
    deleteConfirmationScreen: docsDeleteConfirmation
```

#### Entry Screen ####

This screen represents the entry point to a subflow, it is usually the point at which a user makes a
decision to enter the subflow or not. Example: a screen that asks "Would you like to add household
members?"
could be the entry screen for a household based subflow.

The entry screen is not part of the repeating
set of pages internal to the subflow and as such does not need to be demarked
with `subflow: subflowName`
in the `flows-config.yaml`.

#### Iteration Start Screen ####

This screen is the first screen in a subflows set of repeating screens. When this screen is
submitted,
it creates a new iteration which is then saved to the subflow array within the Submission object.

Because this screen is part of the repeating screens within the subfow, it **should** be denoted
with
`subflow: subflowName` in the `flows-config.yaml`.

#### Review Screen ####

This is the last screen in a subflow. This screen lists each iteration completed within a subflow,
and provides options to edit or delete
a single iteration.

This screen does not need to be demarked with `subflow: subflowName`
in the `flows-config.yaml`. It is not technically part of the repeating screens within a subflow,
however,
you do visit this screen at the end of each iteration to show iterations completed so far and ask
the
user if they would like to add another?

#### Delete Confirmation Screen ####

This screen appears when a user selects `delete` on a iteration listed on the review screen. It asks
the user to confirm their deletion before submitting the actual deletion request to the server.

This page is not technically part of the subflow and as such, does not need to be demarked
with `subflow: subflowName`
in the `flows-config.yaml`.

### Defining Subflows ###

What do you need to do to create a subflow?

- In `flows-config.yaml`:
    - Define a `subflow` section
    - Create a name for your subflow in the `subflow` section
    - Define `entryScreen`, `iterationStartScreen`, `reviewScreen`, and `deleteConfirmationScreen`
      in
      the `subflow` section
    - Add all subflow screens into the `flow`, with `subflow: <subflow-name>` unless otherwise noted
      above
      (for dedicated subflow screens)
    - Note for screens that aren't ever defined in `NextScreens` (delete confirmation screen), they
      still need to be somewhere in the `flow`
- Define `fields` that appear in subflow screens just like you would in a `screen`, in your flow
  Java Class
  (e.g. Ubi.java in the starter app)
- Define `screen` templates in `resources/templates/<flow-name>`

### Example `flow-config.yaml` with a docs subflow ###

```yaml
name: docFlow
flow:
  first:
    nextScreens:
      - name: second
  second:
    nextScreens:
      - name: docsEntry
  docsEntry:
    nextScreens:
      - name: docsStart
  docsStart:
    subflow: docs
    nextScreens:
      - name: docsInfo
  docsInfo:
    subflow: docs
    nextScreens:
      - name: docsReview
  docsReview:
    nextScreens:
      - name: success
  success:
    nextScreens:
  # NOTE: this screen still needs to be defined in `flow` to be rendered even though
  # it isn't the nextScreen of any other Screen
  docsDeleteConfirmation:
    nextScreens:
subflow:
  docs:
    entryScreen: docsEntry
    iterationStartScreen: docsStart
    reviewScreen: docsReview
    deleteConfirmationScreen: docsDeleteConfirmation
```

### When do you need to define `subflow` on a screen? ###

![Diagram showing screens that are in iteration loops to have the subflow key](readme-assets/subflow-stickies.png)

## Defining Conditions ##

Conditions are defined in Java as methods, and can read from the `currentSubmission` object. When
defining new conditions as methods, the instance variable `inputData` is accessible.

```java
public class ApplyConditions extends FlowConditions {

  public boolean isGmailUser() {
    return inputData.get('emailAddress').contains("gmail.com");
  }

} 
```

### Using conditions in templates

You can pull in conditions into a Thymeleaf with the T operator, then use the variable to define
show logic:

```html

<div
    th:with="showCondition=${T(org.codeforamerica.formflowstarter.app.config.ConditionDefinitions).<show-method>()}">
  <h1 th:if="showCondition">Conditionally show this element</h1>
</div>
```

## Defining Static Pages ##

Unlike Screens, Static Pages are HTML content not part of a flow. Examples include the home page,
privacy policy, or FAQ. This starter app contains a home page (`index.html`) and FAQ (`faq.html`)
as examples in the `resources/templates` folder.

To add a new Static Page:

1. Add an annotated method (`@GetMapping`) to the `StaticPageController`
2. Create a page template in `src/resources/templates`.

The template HTML can look like:

```html
<!DOCTYPE html>
<html th:lang="${#locale.language}">
<head th:replace="fragments/head :: head(title='')"></head>
<body>
<div class="page-wrapper">
  <th:block th:replace="fragments/toolbar :: toolbar"/>
  <th:block th:replace="fragments/demoBanner :: demoBanner"/>
  <section class="slab">
    <div class="grid">
      <div class="grid__item">
        <h1 class="spacing-below-35"></h1>
      </div>
    </div>
  </section>
  <main id="content" role="main" class="slab slab--white">

  </main>
</div>
<th:block th:replace="fragments/footer :: footer"/>
</body>
</html>
```

The IntelliJ Live Template for the above example can be generated with `cfa:staticPage`.

## Development setup ##

### Install the following system dependencies: ###

_Note: these instructions are specific to macOS, but the same dependencies do need to be installed
on Windows as well._

#### Java Development Kit ####

If you do not already have Java 17 installed, we recommend doing this:

```
brew tap homebrew/cask-versions
brew install --cask temurin17
```

#### Set up jenv to manage your jdk versions ####

First run `brew install jenv`.

Add the following to your `~/.bashrc` or `~/.zshrc`:

```
export PATH="$HOME/.jenv/bin:$PATH"
eval "$(jenv init -)"
```

Reload your terminal, then finally run this from the repo's root directory:

```
jenv add /Library/Java/JavaVirtualMachines/openjdk.jdk/Contents/Home/
```

#### Gradle ####

`brew install gradle`

### Start the local databases: ###

- Install PostgreSQL 14 via an [official download](https://www.postgresql.org/download/)
    - Or on macOS, through homebrew: `brew install postgresql@14`

<!-- TODO: Is this the right way to create db/user? -->

- Create the database using the command line:
    - `$ createdb starter-app`
    - `$ createuser -s starter-app`

### Authenticating the Library ###

In order to authenticate the library for use with this starter app you must have a GitHub account
and
a GitHub Personal Access Token (PAT). You can register a personal acess token through your user
settings panel
in GitHub via `Profile --> Settings --> Developer Settings --> Personal Access Tokens --> Tokens`.
Create a token that includes `read:packages`. Once you have done this create a `.env` file in the
root
directory of the starter and add your Username and PAT like below:

```
USERNAME=Your_GitHub_Username
TOKEN=Your_GitHub_Personal_Access_Token
```

### Setup IntelliJ for the project: ###

- Enable annotation processing
  in `Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processor`
- Set the Gradle JVM version to 17
  in `Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle`
- Set the Project SDK to Java 17 in `File > Project Structure`
- Run the application using the `FormflowstarterApplication` configuration

### Using a local version of the Form-Flow Library (For Form-Flow Library Developers): ###

To use the [form-flow](https://github.com/codeforamerica/form-flow) library locally:

1. Clone the form-flow repo in the same directory as the starter app. This line
   in [build.gradle](build.gradle) depends on it:
    ```
    implementation fileTree(dir: "$rootDir/../form-flow/lib/build/libs", include: '*.jar')
    ```
1. Ensure you build the jar.
1. Start the `form-flow-starter`.

#### Terminal ####

From the project root invoke
```./gradlew clean test```

#### IntelliJ ####

You can run tests directly in IntelliJ by running tests from test folder (via right click
or `ctrl + shift + r`).

### Setup Fake Filler (optional, Chrome & Firefox): ###

We use an automatic form filler to make manual test easier.

-

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

## About IntelliJ Live Templates ##

As a team, we use [IntelliJ](https://www.jetbrains.com/idea/) and can use
the [Live Templates](https://www.jetbrains.com/help/idea/using-live-templates.html) feature to
quickly build
Thymeleaf templates.

Support for importing/exporting these Live Templates is
a [buggy process](https://youtrack.jetbrains.com/issue/IDEA-184753) that can sometimes wipe away all
of your previous
settings. So we're going to use a copy/paste approach.

### Applying Live Templates to your IntelliJ IDE ###

1. Open the [intellij-settings/CfA.xml](intellij-settings/CfA.xml) from the root of
   this repo
2. Copy the whole file
3. Open Preferences (`cmd + ,`), search or find the section "Live Templates"
4. If there isn't a template group already called CfA, create one by pressing the "+" in the top
   right area and selecting "Template group..."
5. Highlight the template group "CfA", right click and "Paste"
6. You should now see Live templates with the prefix "cfa:" populated in the template group

### Using Live Templates ###

Once you have Live Templates installed on your IntelliJ IDE, in (`.html`, `.java`) files you can use
our
Live Templates by typing `cfa:` and a list of templates to autofill will show itself.

### Contribute new Live Templates ###

1. Open Preferences (`cmd + ,`), search or find the section "Live Templates"
2. Find the Live Template you want to contribute
3. Right click and "Copy" (this will copy the Live Template in XML form)
4. Open [intellij-live-templates/LiveTemplates.xml](intellij-live-templates/LiveTemplates.xml) in this repo
5. Paste at the bottom of the file
6. Commit to GitHub
7. Now others can copy/paste your Live Templates

## Connect flows config schema with IntelliJ IDE ##

TODO: move schema from library and include in webjar?

We use [JSON schema](https://json-schema.org/understanding-json-schema/index.html) to autocomplete
and validate the `flows-config.yaml` file.

You must manually connect the schema to the local file in your instance of IntelliJ IDE.

1. Open IntelliJ preferences (`Cmd + ,` on mac)
2. Navigate to "JSON Schema Mappings"
3. Select the "+" in the top left to add a new mapping
4. Name can be anything (I use "flow config")
5. "Schema file or URL" needs to be set to the `src/main/resources/flows-config-schema.json`
6. "Schema version" set to "JSON Schema version 7"
7. Use the "+" under schema version to add:
    - a new file and connect to `src/main/resources/flows-config.yaml`
    - a folder and connect to `src/test/resources/flows-config`

To confirm that the connection is work, go into `flows-config.yaml` and see if autocomplete is
appearing for you.

![IntelliJ JSON Schema Mappings menu](readme-assets/intellij-json-schema-mappings.png)

## Setup Platform Flavored Google Styles for Java ##

In intelliJ go to `Preferences --> Editor --> Code Style --> Java` and next to Scheme hit the cogwheel 
and `Import Scheme --> IntelliJ Code Style XML` with
![intellij-settings/PlatformFlavoredGoogleStyle.xml](intellij-settings/PlatformFlavoredGoogleStyle.xml)