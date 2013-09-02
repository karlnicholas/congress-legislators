This program parses the congressional legislators yaml file into java beans.
The original files are found at https://github.com/unitedstates/congress-legislators

The java beans can then be easily worked with to extract desired information

A basic filtering API is provided that will strip out desired entries
such as term start date, state, name, etc.

The program assumes that the congress-legislators files will be in the
src/main/resources directory. You will need to get a copy of them from 
the above link. See getting started below.

I wrote it so as to extract congressional party information between 
the years 1960 and 2013 and create a CSV file with democrat 
and republic counts for each region of the US. It made a nice
graph. See reps.txt and reps.xlsx.

			****** Getting Started ********

1:  Clone this repository. It was created with Eclipse Juno, so if you 
	have egit and maven installed you can 
	use File->Import->Check out Maven Project from SCM.
	
2:	The project uses Yamlbeans (1.06) from http://code.google.com/p/yamlbeans/
	so you have to include that when you build the project.

3: 	The project needs at least JDK version 7.

4:	You will need to get the legislators-historical.yaml and 
	the legislators-current.yaml files and put them into 
	the src/main/resources directory.

5:	There are four program files in the default package, 
	which is in the src/main/java directory.
	
	a. GovTrackFilterExample: This program reads the data
	and does an simple filter by lastname and prints out the result
	
	b. Filter: this program reads the data and does a more
	complicated filter and prints out the results
	
	c. ExportPartiesByRegion: This is the program that 
	created reps.txt. It shows a good and not too complicated
	example of working with the Java Beans. See the comments
	in the file for more information.
	
	d. ShowGovTrackHierarchy. This program will parse the 
	congress-legislators files and identify all unique fieldnames.
	It was used once in order to create the java beans.

6:	Packages are: 
	a. model: has the java beans for each of the different
	GovTrack maps.
	b. filter: has a chain-of-responsibility filtering
	interface and a couple of filters. 
