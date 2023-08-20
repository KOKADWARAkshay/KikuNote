
# Kiku My Note App

Note-Taking App Documentation Introduction In this documentation, I will present the development process and design choices made while creating the Note-Taking App. The app allows users to create, edit, and delete notes, providing a user-friendly interface for efficient note management.




##  Architecture And Design Architectural Pattern:

MVVM (Model-View-ViewModel)

I chose the MVVM architectural pattern for its separation of concerns and clean code organization. This pattern allowed me to decouple the UI from business logic, making the app more maintainable and testable.

Components:

Model: Represents the data and business logic of the app. In this case, it includes data entities, repository, and Room database interactions. View: Represents the UI elements of the app. Activities and fragments are used to display and interact with the UI. ViewModel: Acts as an intermediary between the Model and View. It holds UI-related data and provides methods for UI interactions. User Interface Home Screen:

Displays a list of existing notes. Utilizes RecyclerView to efficiently display the notes. Implements a floating action button (FAB) for adding new notes. Note Preview:

Each note in the list shows its title, a content preview, and last modified date. Note Editor:

Allows users to input and modify note title and content. Provides formatting options such as bold, italic, and bullet points. Includes a save button to update the note and return to the home screen. Note Management Deletion:

Users can delete a note by swiping it from the list or using a contextual action mode. Search:

Implements a search functionality for users to find specific notes by title or content. Sorting:

Provides options to sort notes by creation date or last modified date. Persistence with Room I implemented local storage using the Room database library to ensure data retention even if the app is closed or the device is restarted. Room simplifies data management and allows for efficient querying of notes.
## Neumorphism UI Design:

Used Neumorphism Design: Enhancing User Experience Through Visual Appeal
Neumorphism, often referred to as "soft UI," is a design trend that combines elements of skeuomorphism (design that mimics real-world objects) and flat design (minimalistic and two-dimensional). This design style is characterized by creating elements that appear to be slightly raised from or pressed into the surface, creating a sense of depth and tactility..
## Unit Testing

Unit Testing I wrote unit tests to ensure critical components' functionality, including note creation, deletion, and search. These tests ensure the app's reliability and verify that essential features are working as expected.


![WhatsApp Image 2023-08-21 at 12 24 34 AM](https://github.com/KOKADWARAkshay/Kikunote/assets/70112790/d529c89f-7930-46be-801c-ab74eebf5b46)


![WhatsApp Image 2023-08-21 at 12 24 31 AM](https://github.com/KOKADWARAkshay/Kikunote/assets/70112790/01c3378a-c45c-461b-8d47-bde1afcbc23d)



![WhatsApp Image 2023-08-21 at 12 2![WhatsApp Image 2023-08-21 at 12 24 30 AM](https://github.com/KOKADWARAkshay/Kikunote/assets/70112790/f2f2adea-c3ab-48d3-b0f9-ba025e02c74a)


![WhatsApp Image 2023-08-21 at 12 24 28 AM](https://github.com/KOKADWARAkshay/Kikunote/assets/70112790/27a202b9-4642-4c3b-80a1-e3e362182f35)


![WhatsApp Image 2023-08-21 at 12 24 34 AM](https://github.com/KOKADWARAkshay/Kikunote/assets/70112790/a6d73744-519b-462b-8a99-d4303dd17a62)
