# Form Flow Starter App

To use locally, create the db:
```bash
createdb starter-app
createuser -s starter-app
```

To test the [form-flow](https://github.com/codeforamerica/form-flow) library locally:
1. Clone the form-flow repo in the same directory as the starter app. This line in [build.gradle](build.gradle) depends on it:
    ```
    implementation fileTree(dir: "$rootDir/../form-flow/lib/build/libs", include: '*.jar')
    ```
1. Ensure you build the jar.
1. Start the `form-flow-starter`.
