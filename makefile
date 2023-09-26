JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  src/App.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/*.class
	$(RM) bin/*.class
	
run:
	java src/App