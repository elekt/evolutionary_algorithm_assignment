python clean.py
javac -cp contest.jar *.java
jar cmfMainClass.txt submission.jar *.class
java -jar testrun.jar -submission=player2 -evaluation=BentCigarFunction -seed=1
pause