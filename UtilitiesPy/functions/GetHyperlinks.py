import re

filename = "C:\\Users\\radgrias\\IdeaProjects\\ggwebnovelscraper\\Utilities\\src\\main\\resources\\ISSTHTOC.html"
destination = "C:\\Users\\radgrias\\IdeaProjects\\ggwebnovelscraper\\Utilities\\src\\main\\resources\\parse_ISSTHTOC.html"
txt = open(filename, encoding="utf8")
dest = open(destination, "w+")

for line in txt:
    if "http" in line:
        r = re.findall("(http.+?)\">", line)
        if r:
            for thing in r:
                print(thing)
                dest.write(thing+"\n")
