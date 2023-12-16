# CFR_Selenium_test
Automatic testing on the site "https://www.cfrcalatori.ro/" using Selenium with Java language , to evaluate the functionality and user experience of the website. There are 5 test and 3 bugs found.

The file _src/test/resources/driver_path.properties_ contains the path for the ChromeDriver. Change it with the path where you have the Chrome driver installed.

## Test description
### BuyInternationalTicketOneWay
_Description_: Buy an international ticket for the destination city.

_Steps_:
1. Open the website and click on "Mers tren trafic internațional". It opens another tab for international tickets.
1. Switch to the new tab and select "Dus".
1. Then input the departure city and select the city from the dropdown, the same for the destination city, and puts the current date 
1. After it searches for tickets and see the offers.

### BuyTicketsInternationalTest
_Description_: Buy an international round-trip ticket to the destination city and back.

_Steps_:
1. Open the website and click on "Mers tren trafic internațional", it opens another tab for international tickets. Tickets are for back and both between cities.
1. Switch to the new tab.
1. Input the city for departure, and click the city from the dropdown, then input the destination city and click the city from the dropdown.
1. Click the buton "Cautare" to search for tickets.
1. First ticket will appear for destination city and selects the first ticket by clicking on "Calatorie intors".
1. Second ticket appears for route back.Selects the first available ticket by clicking "Vezi oferte".
1. Details for the tickets are displayed. For the number of passangers it selects 2 adults.
1. Then "Vezi oferte". The tickets and rates are displayed.

### BuyTicketsNextDayTest
_Description_: It searches for tickets for the next day compared to the current date and chooses the first ticket that appears.

_Steps_:
1. Opens page and inputs the names of the departure and arrival cities.
1. In the calendar we select the next day.
1. Then clicks "Caută". A new tab opens and the tickets appear.
1. Switch to the new tab and selects first ticket available.
1. The ticket is displayed with details about it.

### BuyTicketsTest
_Description_: Buys ticket for the current date to the destination city.

_Steps_:
1. Opens page and inputs the names of the departure and arrival cities.
1. Then clicks "Caută". A new tab opens and the tickets appear.
1. Switch to the new tab and selects first ticket available.
1. The ticket is displayed with details about it.


### FAQTest
_Description_: Accesses the FAQ section and checks if it displays the answer to a question.

_Steps_:
1. Opens the page and changes the language to English.
1. Selects FAQ.
1. Select one question and verifys if the answer appears.

## Bugs description

### AdvancedSearchBugTest
_Description_: After certain steps, it tests if the site remains in Romanian.

_Steps_:
1. The site is in Romanian automatically.
1. Clicks on calendar.
1. Clicks on the up and down arrow symbol.
1. Clicks on "Căutare avansată".
1. Switches to the new page.
1. Verifies if the second page opened is in Romanian.

The bug is that on the second page, the language changes to English, but it should be in Romanian.

### DomesticTimetableBug
_Description_: Verify if the warning that appears after it puts the incomplete names of the city, is English.

_Steps_:
1. Changes language to English.
1. Inputs incomplete names in the destination and arrival city.
1. Verify if the warning appears.
1. The warnings are shown in Romanian and the page it's in English.

The warning appears in Romanian, even if the site is set in English.

### HourBugSearch for tickets
_Description_: At international ticket one way to the city, checks if all hours appear at the hour selection, using the arrows on the page.

_Steps_:
1. Select international ticket.
1. Switch to the new opened tab.
1. Select search for One Way Ticket.
1. Input hour "00".
1. Click right arrow and creates a list with the number that appear.
1. Verifies if all the hours appear.

The hour 23 doesn't appear and it stops.
