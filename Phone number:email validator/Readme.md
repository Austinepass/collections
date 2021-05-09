=> Emphasis on testing: Unit and Instrumentation tests.

* Implementation 1 (In group of 2):
A contact application with pages to show a list of user's saved contact, users can add a new contact
App has a contact details page where users can edit, call, share and delete contacts . 
The data store is firebase and two devs share one firebase database.
Expectation:
· Real Time syncing of contacts for partners
· Different UI designs and codebase for partners
· Call should ask for permission

* Implementation 2:
An application that reads a users phone contacts and displays on a list via the ContentResolver.
Expectation:
· Ask for permission when app is launched
· If permission is denied an error message is displayed in a DialogBox.
· A button to ask again if permission is denied is provided on the DialogBox as well.

Good coverage of unit and instrumentation tests.
