# Architecture

This app follows the Clean Architecture pattern with separated modules.

* base: Holds common code shared between all the modules, like Constants, Result wrappers and Async utilities.
* base-android: Common code for android modules. For time, this app has all the features on the app module. But
if the module decouples it's features to separated modules, it will be less painful to migrate.
* data: Holds the DAOs, Entities, Stores, Sources, Services, Repositories and the DB abstraction.
* domain: Have the interactors that will make request to the repositories and return `DataResults`.
* data-android: Creates the Room Database and the modules used for dependency injection.
* app: Main module, which have the views, viewmodels that use the interactors for requesting data and
the states of the application. Has three folders:
** app: Koin modules for app's dependencies (ViewModels mostly) and the Application class.
** ui: Separated by features, almost all have 3 things in common: an activity, a view state and a viewmodel.
** utils: Just a Constants file.
* tests: Centralized module to test the others ones.

As most of stuff, and image say more than words: 

[Diagram](arch_diagram.jpg)

UI observes LiveData exposed by the ViewModel. The UI makes request to the ViewModel.
The ViewModel launches a coroutine that awaits for the results to be returned by the Interactor.
One the results arrives, they get dispatched to the LiveData as a new ViewState

## Test coverage

The unit test are run against the ViewModels. After implementing the pagination feature, the test for the HomeViewModel
changed and became difficult to fix. The reason for this is because the Paging library does not have a module for testing purposes.
The interactors are mocked for this tests.

Currently, only 1 test is passing for the HomeViewModel. The rest test cases for the other ViewModels are passing. To run them, use the following command:
`./gradlew test`
You can check the _without-paging_ branch to check all the test passing.

The instrumented test are run against the interactors. Koin is used for injecting all the dependencies, with the exception of the Services and the RoomDb itselft.
The services and mocked and the DB is created in memory thanks to Room testing support.
To run them, use the following command:

`./gradlew connectedAndroidTest`