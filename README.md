# Team-14 - Waitless

<img src="app/src/main/res/drawable/splash_logo.png" alt="logo" width="100">

Your personal gym equipment queue manager!

## The Team

| Name | Email | Development Journal |
| -------- | ------ | --- |
|Harshitha Durai Babu|hduraiba@uwaterloo.ca|[Link](../../wikis/Development-Journal:-Harshitha)|
|Michael Mavely|mmavely@uwaterloo.ca|[Link](../../wikis/Development-Journal:-Michael)|
|Ashna Nadar|ashna.nadar@uwaterloo.ca|[Link](../../wikis/Development-Journal:-Ashna)|
|Abhinit Patil|a33patil@uwaterloo.ca|[Link](../../wikis/Development-Journal:-Abhinit)|


## Links
- [User Documentation](../../wikis/User-Documentation)
- [Design Documentation](../../wikis/Design-Documents)
- [Release Notes](../../wikis/Release-Notes)
- [Meeting Minutes](../../wikis/Meeting-Minutes)
- [Project Proposal](../../wikis/Project-Proposal)

## Design Pattern

**MVVM Pattern**
MVVM offers two-way binding between view and view-model. It makes use of the  observer pattern to make changes in view model
- Model: stores data + related logic (interacts with DB)
- View: standard UI components + pages
- View-Model: functions, commands, methods to support state of Views

## Folder Structure

We have seperated the app and server into two repositories. Please review server setup, routes and authentication setup on this [repository](https://github.com/mgmavely/waitless-server)

Here's the key outline of the folder structures for both repositories:

**App Structure:**
- model
    - contains the UserModel which has all of the data models
- userInterface
    - contains all to fhe views: EquipmentView, HomeView...
- controller
    - contains the userController to provide an interface with backend
- theme
    - specifies app level theme configurations
- viewModel
    - contains the UserViewModel which provides interface between Views and Models
- queue
    - defines the interfaces with Queue to which communicates with external FASTAPI
- MainActivity.kt: entrypoint to app
- MainScreen.kt: main view which contains nvaigation and more.

**Server Structure:**
- data
    - reposotory
        - contains the repositories (CRUD operations) for each data model (UserRepository, WorkoutRepository etc.)
- models
    - entities
        - provides the schema structure for each table in DB
- plugins
    - contains the server configurations for KTOR
- routes
    - contains the endpoints which are exposed for app to interact with
- utils
    - utiltiy functions used for development
- Application.kt: entrypoint for the server application

## To run this locally:

1. Clone this repo
2. Run ./gradlew build
3. Click the play icon to kick start the emulator
4. Viola! It should be running and you can verify logs in logcat

In the event signup fails, pelase use our test account to continue testing the app:

- Email: mgmmavely@gmail.com
- Pass: asdf1234

**Hope you like our app!**