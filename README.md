# Assessment Android project for interview

### User story
- There’s a list of stock items (let’s say 10 items). Each has unique ID, name and price.
-  Mock API (in app) should return top 5 items with highest price at the moment.
-  Price of each item increased/decreased on each “API call” by random value within range (i.e. -5.0 .. +5.0)
-  Write an activity which shows a list of top 5 items. Each list item should show name and price.
-  Price label’s color is depending on price change, i.e. red for decreasing, green for increasing, and black for neutral.

#### Tech requirements
-  Android native development using Kotlin.

#### Additional Tech requirements (based on job posirtion description)
- MVVM.
- Android Architecture Components.
- RxJava is required.

# Briew resul overview

![ezgif com-gif-maker](https://user-images.githubusercontent.com/635261/176037235-e980afc9-2d12-42de-af54-b50690708425.gif)

# Implementation description

## Hight level overview
From the architectural point of view, the application build based on Layered Architecture. In our specific case, three layers were selected - presentation, domain, and data layer.
- Presentation layer represented by MVVM architecture pattern. And MVVM was build around Android Architecture Components.

- The domain layer was built around the Transaction Script approach. Where each Business Scenario is wrapped into its own Use Case, implemented as a Command pattern. Because the application is small, Transaction Script is preferable then Domain Model or Table Model.

- The data layer in this case is represented by data access classes. To provide at least minimal flexibility, a Repository pattern was selected. In more complex solutions, a Repository can perform the role of Facade around several Data Sources. It can by customisable by Strategy pattern to pick up different data syncing approaches for different combinations of data sources.

Each of described layers was wrapped into its own Gradle unit. With such a simple application this solution could look a bit over-engineering, however, it's a kind of standard nowadays.

>  To keep this project simple, Dependency Injection was made with a simple Dependency Resolver class with provides static methods inside. I made it deliberately, but we always can use Dagger for this.

The idea was to use RxJava as a contract between Layers. Then each Layer can add all feature-related RxJava operators sequence to the data flow. So each of the Layers is responsible only for some specific data flow processing.

> For the User story described before, it can look like inside Data Layer the Data Source is emitting all available data streams then Repository is making briefly processing it to sort by the stock price. Next on Domin Layer, there is a Use Case then can filter only a portion of data (like give only the first 5). And on the final stage, on the Presentation Layer, the View model is making final processing, related to some UI-specific logic.

### Presentation layer
In the app single Activity was used. Inside of which one Fragment with one Recicle View were used. As was said before this layer is built with Android Architecture Components. All presentation logic like fetching data, and mapping it from the domain format into UI-related format, is located in View Model. View Model is spreading data to the Fragment in LiveData approach. So Fragment is subscribing to data updates when they are available. And all data manipulations are encapsulated in View Model. Just because the lover domain layer uses RxJava as a supplying contract, additional RxJava -> LiveData management is also a View Model responsibility. Data Binding is using to map UI and presentation logic.

### Domain layer
Using the Business Scenario paradigm, two Use Cases were created. Just to demonstrate, adding more Domain Logic is just simply customizable by adding a new Use Case class.

### Data layer
Represented by a simple Persistent Repository, working with a single Persistent Data Source. And Data Source which is returning the flow of mocked domain objects. Each of the times when new data fetch is performed, domain objects are recreated with the new price which is +/-5 on the hardcoded ground level.

> This layer was kept maximum simple. Looks like it was no specific reason to make Mocked HTTP client (using Retrofit for example), according to the task. If that was a point, it can be added too. In this case, instead of MockStockDataSource around a simple Array List, mocked HTTP client can be used inside. The Room was also not used for the same reason. It's a part of Android Architecture Components, but looks like using it is out of scope for this test task. However implementation can be extended, so we can use some Mocked HTTP clients together with Room inside of some Repository implementation if we need.

# Work that still need to be done
- Simple Unit tests were added for Repo and Use Cases. Exept ViewModel, because I was ran out of time;
- Some of the unused Android resourses can be removed.
