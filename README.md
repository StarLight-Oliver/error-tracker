# error-tracker
This is an error tracker, I don't know how complicated it will be yet


Jira board: [link](https://odillion-qa.atlassian.net/jira/software/projects/EL/boards/)


Prerequisites
```
NPM
Java 11
Eclipse 2020-12
```
How to install front end

```
cd into the front-end
run npm install
```

# How to configure

go into application.properties
configure the details there


#How to build the application
```
cd into the front-end
npm run build

copy the files from dist into back-end/errortracker/src/main/resources/static

cd into back-end/errortracker
mvn clean package
```
#Authors

Oliver


# Acknowledgments
CSS Framework: https://tailwindcss.com

JS Crc32: https://github.com/SheetJS/js-crc32
