javac -cp contest.jar *.java
jar cmf MainClass.txt submission.jar *.class
java -jar testrun.jar -submission=player2 -evaluation=BentCigarFunction -seed=1