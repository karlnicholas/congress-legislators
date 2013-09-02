This program parses the congressional legislators yaml file into java beans.
The original files are found at https://github.com/unitedstates/congress-legislators

The java beans can then be easily worked with to extract desired information

A basic filtering API is provided that will strip out desired entries
such as term start date, state, name, etc.

The program assumes that the congress-legislators files will be in the
src/main/resources directory. You will need to get a copy of them from 
the above link.

I wrote it so as to extract congressional party information between 
the years 1960 and 2013 and create a CSV file with democrat 
and republic counts for each region of the US. It made a nice
graph. See reps.txt and reps.xlsx.
