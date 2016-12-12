import re

filename = "G:\\dev\\code\\Utilities\\src\\main\\resources\\CDTOC.html"
destination = "G:\\dev\\code\\Utilities\\src\\main\\resources\\parse_CDTOC.html"
txt = open(filename, encoding="utf8")
dest = open(destination, "w+")

for line in txt:
    if "http" in line:
        r = re.findall("(http.+?)\">", line)
        if r:
            for thing in r:
                print(thing)
                dest.write(thing+"\n")
