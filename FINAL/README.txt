This app's purpose is fivefold:
News - display news events from the glenrock server
Calendar - display calendar events from the glenrock server
Trash - display trash events from the glenrock server
Contact - allow the user to send emails to a provided list of contacts, and displays phone numbers for these contacts as well.
GoLocal - displays a list of local business information taken from the glenrock server.

The folder GlenrockAppV1 is the main app folder, and contains the following source files:




AndroidManifest.xml:
This file states the permissions necessary for the app, how the app is displayed on the screen, and also describes how the app is launched.

GlenrockAppV1/src/com/glenrockappv1 contains the java files:

Article.java: This class is for storing information about individual news articles.

ArticleFragment.java: This class is for displaying the information from the Article class.

CalendarAdapter.java: This class is for describing the individual behavior of the items in the grid on the calendar page.

CalendarEventArticle.java: This is for storing information about individual events from the calendar.

CalendarEventArticleFragment.java: This is for displaying information from the CalendarEventArticle class.

CalendarFragment.java: This is for displaying the grid on the calendar page, and the list of upcoming events underneath it.

CalendarShortEventListAdapter: This is for describing the individual behavior of the items in the list of upcoming events in the Calendar section.

GetJsonTask.java: This is for getting information from the database, using php scripts.

GoLocalDirFragment.java: This class is for displaying all the business information in one category of the GoLocal Directory. 

GoLocalFragment.java: This class is for displaying buttons for each category in the GoLocal Directory.

MainActivity.java: This class communicates with all the fragments. It sets up the information for the GoLocal and Contact Fragments, and allows transitions from pages with buttons to the pages the buttons lead to.

NavListAdapter.java: This class displays the menu to navigate between the sections.

NewsFragment.java: This class is for displaying the shortened version of the News articles in a list.

NewsListAdapter.java: This class describes the individual behavior of each item in the list of news articles.

PostJsonTask.java: This class is used by the Calendar and Trash sections to get information from the database - it allows for arguments when calling the php scripts, which the GetJsonTask class does not.

TrashAdapter.java: This class is for describing the individual behavior of the items in the grid on the trash page.

TrashEventArticle.java: This is for storing information about individual events relating to Trash/Recycling.

TrashEventArticleFragment.java: This is for displaying information from the TrashEventArticle class.

TrashFragment.java: This is for displaying the grid on the trash page, and the list of upcoming events underneath it.

TrashShortEventListAdapter: This is for describing the individual behavior of the items in the list of upcoming events in the trash section.

GlenRockAppV1/res/layout contains the following layout files:

activity_main.xml
calendar.xml
calendar_article_fragment.xml
calendar_item.xml
calendar_short_event_item.xml
contact_button_fragment.xml
contact_button_fragment_list_item.xml
contact_form_fragment.xml
drawer_list_item.xml
email_newsletter_dialog.xml
fragment_article.xml
fragment_news.xml
go_local_button_fragment.xml
go_local_button_fragment_list_item.xml
go_local_dir_fragment.xml
go_local_dir_fragment_list_item.xml
news_list_item.xml

GlenRockAppV1/res/values contains the following files:

color.xml - contains colors used in the app
strings.xml - contains string and integer constants and arrays
styles.xml - unused
dimens.xml - sets screen margins

The /www/ folder has the following files:

banking.php: Gets information about businesses tagged in the banking category.

calendar.php: Gets information about a calendar event given a specific date.

config.php: Sets up the ip of the server.

construction.php: Gets information about businesses tagged in the construction category.

education.php: Gets information about businesses tagged in the education category.

entertainment.php: Gets information about businesses tagged in the entertainment category.

food.php: Gets information about businesses tagged in the food category.

housing.php: Gets information about businesses tagged in the housing category.

medical.php: Gets information about businesses tagged in the medical category.

news.php: Gets information about the latest news articles.

proServices.php: Gets information about businesses tagged in the Professional Services category.

retail.php: Gets information about businesses tagged in the Retail category.

salons.php: Gets information about businesses tagged in the Salons category.

trash.php: Gets information about a trash event given a specific date.