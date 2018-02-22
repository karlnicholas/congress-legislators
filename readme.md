This program parses the congressional `legislators` yaml file into java beans.
The original files are found at https://github.com/unitedstates/congress-legislators

The java beans can then be easily worked with to extract desired information

This main class, `GovTrackRecords`, reads the `legislators` files and then
streams them so they can be manipulated using Java-8 lambda expressions. 

The program assumes that the congress-legislators files will be in the
src/main/resources directory. You will need to get a copy of them from 
the above link. See getting started below.

I wrote it so as to extract congressional party information between 
the years 1960 and 2017 and create a CSV file with the number of democrat 
and republican legislators for each region of the US. It made a nice
graph. See reps.txt and reps.xlsx.

			****** Getting Started ********

1:  Clone this repository. You can import it into Eclipse, with 
	File->Import->Check out Maven Project from SCM.
	
3: 	The project needs at least JDK version 8.

4:	You will need to get the `legislators-historical.yaml` and 
	the `legislators-current.yaml` files and put them into 
	the src/main/resources directory.
	
5:      Instantiate `GovTrackRecords` and then call the `.stream()` method.  

6:	There are two example program files in the default package, 
	which is in the src/main/java directory.
	
	a. GovTrackFilterExample: This program reads the data
	and does an simple filter by lastname and prints out the count. 
	It shows a simple and not too complicated example of working with 
	the Java Beans. See the comments in the file for more information.
	
	b. ExportPartiesByRegion: This is the program that 
	created reps.txt which was used to create the reps.xlsx
	
7:	Packages are: 
	a. model: has the java beans for each of the different GovTrack data nodes.
