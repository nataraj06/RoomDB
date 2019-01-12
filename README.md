# RoomDB
Sample demo app for storing quotes using RoomDB + LiveData + ViewModel

<img src="https://github.com/nataraj06/RoomDB/blob/master/media/device-2019-01-10-210259.png" width="200" style="max-width:100%;">

*The above sample is the demonstration of using RoomDB along with LiveData and ViewModel. This is the simple app for storing 
our favourties quotes and we can also edit/delete*

Lets start with a most obvious question about Room : Why should I use another library to manage my SQLite database when I am already well equipped with SQLite usage?

Answer of this question lies in below problem statements.

Major problem with SQLite usage is

* There is no compile-time verification of raw SQL queries. For example if you write a SQL query with a wrong column name that does not exist in real database then it will give exception during run time and you can not capture this issue during compile time.
* As your schema changes you need to update the affected SQL queries manually. This process can be time consuming and error prone.
* You need to use lots of boilerplate code to convert between SQL queries and Java data objects.
All above concerns are taken care by Room persistence library that was introduced last month in Google I/O 2017. It is super easy to learn and reduce your efforts drastically.

Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite. Let’s start with how to add Room in your project.

### NOTE: As far as  speed and flexibility is considered, nothing can beat the SQLite.

## Bench mark comparison for Android ORM Frameworks

|Library|write(s)|read(s)|update(s)|delete(s)|write(c)|read(c)|update(c)|delete(c)|write(b)|read(b)|update(b)|delete(b)|
|:-----:|:------:|:-----:|:-------:|:-------:|:------:|:-----:|:-------:|:-------:|:------:|:-----:|:-------:|:-------:|
|ORMLite|151|666|122|105|445|3836|857|811|1563|3426|724|728|
|SugarORM|245|842|252|152|1402|4129|1467|1003|2204|4397|1702|1197|
|Freezer|248|5430|240|4797|1337|78982|2221|22104|3255|134942|1887|29515|
|DBFlow|97|757|459|186|360|3534|3124|1044|1129|4653|5204|1268|
|Requery|87|1501|147|129|461|8057|861|802|1368|8002|886|763|
|Realm|151|29|1079|723|698|688|19666|9180|1522|210|21129|10006|
|GreenDAO|81|1238|117|97|357|5552|455|274|598|5905|504|315|
|ActiveAndroid|3123|930|2293|2423|14671|4165|15958|13023|17213|4653|19303|14642|
|Sprinkles|5766|1050|6364|605|25978|4334|65579|2428|27774|4526|37705|2519|
|Room|131|699|170|109|562|3201|717|403|1330|3532|790|507|
|SQLite|50|436|63|80|386|2155|192|284|1146|2313|213|318|

### components of RoomDb in android

Room takes care of these concerns for you while providing an abstraction layer over SQLite.
```Database```, ```Entity```, ```DAO```

There are three major components in Room:
```Entity``` represents data for a single table row, constructed using an annotated java data object. Each entity is persisted into its own table.

```DAO (Data Access Object)``` defines the method that access the database, using annotation to bind SQL to each method.

```Database``` is a holder class that uses annotation to define the list of entities and database version. This class content defines the list of DAOs.

