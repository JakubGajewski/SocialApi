###About the project

I've simplified a lot of things in this application - only because time
constraints. I believe that the code that I've written should be enough
for the recruitment process, but if I'm wrong, please let me know,
I can make further improvements. As for now application is working fine
and according to the requirements, but to make the application perfect
(and production ready) it would be necessary to:

###Simplifications
- write more unit test cases, for now there are only samples, not the full test suite
- integrate Spring Security, use JWT for user auth
(right now it's only "username" header)
- distinguish http responses status according to W3 rfc 2616
(https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html)
(right now we get Http status 200 or 201 if everything is OK, and 404 if now)
- more custom exceptions for corner cases, to give API's user valid info about what was wrong, to not show to the user
internal application error stack trace
- integrade style formatter like checkstyle
...but I hope it's "good enough" for the recruitment process, and I know it's not production ready.

###How to run the application?
To run the app please download the repo, and make sure the are current versions of Gradle and Java installed and
configured. Go to the project directory and run ```gradle build```. Make sure there is no application running on port
8080 and no running H2 database. After that, just run the ./build/libs/socialapi-0.0.1-SNAPSHOT.jar with ```java --jar```
command.

###API documentations
As the only constant in every project is change, instead of writing api docs manually, I've used the Swagger tool to do
the job for me. It's not only self-updating docs, but also curl generator. To see the docs, just run the app locally
and go to localhost:8080/swagger-ui.html;


