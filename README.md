# Restauraunteur App

This is a basic sample app that I created to showcase how <a href=https://github.com/facebook/litho>Litho</a> can interface with <a href=https://gradle.org/>Gradle</a>, <a href=http://reactivex.io>RXJava</a>, and <a href=https://square.github.io/retrofit/>Retrofit</a>
to pull data from an <a href="https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=10">API</a> (Doordash, in this case), and display it in a RecyclerView.

To view this app, you may directly download the SDK here.

## Set Up

Cloning this repository should be all that's needed! Gradle is used as the build tool, and it should install all dependencies as needed.

To install Gradle, please run `homebrew install gradle` (on Mac) or visit <a href=https://gradle.org/>https://gradle.org/</a>.

## Architecture

This app is not meant to be complex, but it's meant to be architected in ways similar to large-scale apps. This repository has
four main facets, as detailed below.


### 1. Android Views
<ol type="a">
  <li><b>Activity</b></li>
  This is a single activity app (<a href=https://github.com/Sresht/Restauraunteur/blob/master/app/src/main/java/com/restauranteur/view/MainActivity.java>MainActivity.java</a>). The activity pulls data from the DoorDash API using a Parser helper
  class, and then passes these data to the Fragment.
  <li><b>Fragment</b></li>
  This app has three fragments (<a href=https://github.com/Sresht/Restauraunteur/blob/master/app/src/main/java/com/restauranteur/view/MainFragment.java>MainFragment.java</a>, <a href=https://github.com/Sresht/Restauraunteur/blob/master/app/src/main/java/com/restauranteur/view/LoadingFragment.java>LoadingFragment.java</a>, and <a href=https://github.com/Sresht/Restauraunteur/blob/master/app/src/main/java/com/restauranteur/view/RestaurantDetailsFragment.java>RestaurantDetailsFragment.java</a>).
  LoadingFragment is displayed simply while waiting for the asynchronous data fetch to load.
  MainFragment is displayed once data has been fetched (or an error has occurred), and will render a list of restaurants near the hard-coded location (i.e. Doordash's HQ).
  RestaurantDetailsFragment is displayed once a restaurant is clicked, and will render a list of popular menu items at that restaurant, along with their prices.
</ol>

### 2. Litho Layouts
  Layout on this app is done using Litho. In other words, there is only a single barebones template XML file with a <content> div, and it
  is inflated and render asynchronously using the <a href=https://github.com/facebook/litho>Litho framework</a>, which is an open-source declarative-based layout framework from Facebook.
  There are multiple Litho ComponentSpecs defined in this app to render and display view elements when data has been rendered.

### 3. Data
Data are handled by a combination of a centralized <a href=https://github.com/Sresht/Restauraunteur/blob/master/app/src/main/java/com/restauranteur/parser/DoorDashDataParser.java>Parser</a> (because we only make one API call - more on that later in the Future Work section), and <a href=https://github.com/Sresht/Restauraunteur/tree/master/app/src/main/java/com/restauranteur/model>various model classes</a> which mirror the DoorDash restaurants API.

Sadly, at the moment, all data are pulled down in one query. Things are set up to paginate the data, but I couldn't figure out how to get it working without hackiness on the Litho side. To see my hacky attempts, feel free to check out <a href=https://github.com/Sresht/Restauraunteur/tree/pagination>the Pagination branch</a> on this repository!

## Testing

At the moment, <a href=https://github.com/Sresht/Restauraunteur/tree/master/app/src/androidTest/java/com/restauranteur/model>unit testing</a> is done only on the Models to ensure that all Models are Parcelable and contain all expected fields.

I wrote these tests in Kotlin to mirror a common use case in industry (Business logic in Java, Unit Testing in Kotlin).

There are no screenshot tests to mirror rapid development in industry - screenshot tests can often slow down development of rapidly-changing, low visibility apps/screens because engineers need to modify code in two places instead of one. In the future work, I outline the use cases of Screenshot testing.


## Future Work

<ol>
<li> <b>Pagination</b>
The most obvious gap here is pagination. All data are pulled down in one query, which will result in heavy lags as the data set size increases.
I have attempted <a href=https://github.com/Sresht/Restauraunteur/tree/pagination>a solution</a> to adding pagination, but it doesn't interface well (at least as far as I have tried) with Litho unless we do some hackiness.
If you come up with an idea that's not hacky, please feel free to drop me a Pull Request!

<li> <b>Broken Unit Tests</b>
I added unit testing for each Model class, but the RestaurantTest.kt and MenuTest.kt tests aren't passing because of a Parcel-related Exception. When I have more time, I will investigate this.
  
<li> <b>Screenshot Tests</b>
It doesn't make sense to add <a href=https://facebook.github.io/screenshot-tests-for-android/>screenshot tests</a> until a visual design is finalized and expected not to change, but once that happens, it makes sense to do so for critical flwos to alert regressions.
</ol>
