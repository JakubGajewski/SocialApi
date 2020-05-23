I've simplified a lot of things in this application - only because time
constraints. I believe that the code that I've written should be enough
for the recruitment process, but if I'm wrong, please let me know,
I can make further improvements. As for now application is working fine
and according to the requirements, but to make the application perfect
(and production ready) it would be necessary to:

- write more test cases, for now I've made "happy path" end-to-end test,
because these were extremally helpfull while developing the app
- test for proper JSON objects in integration tests
- manual testing - always worth to do a lot of it, but I didn't have
that much time for all corner cases
- integrate Spring Security, use JWT for user auth
(right now it's only "username" header)
- distinguish http responses status according to W3 rfc 2616
(https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html)
(right now we get Http status 200 or 201 if everything is OK, and 404 if now)
- use Hibernate validator to valid incoming request
- use @ExceptionHandler for resolving exceptions and give user pretty
output - right now user gets info about internal system erros, that's not
a good practice
- enabling HTTPs
- more custom exceptions for corner cases, to give API's user valid info
about what was wrong - right now we have only FollowYourselfException,
you can try it when you send f.e. this request:
`curl -X PUT "http://localhost:8080/api/v1/follow/Jakub" -H  "accept: application/json" -H  "username: Jakub"`

...but I hope it's "good enough" :) And if you want me to work more on it,
just please let me know.

API documentations
As the only constant in every project is change, instead of writing
api docs manually, I've used the Swagger tool to do the job for me.
It's not only self-updating docs, but also curl generator. To see the
docs, just run the app locally and go to localhost:8080/swagger-ui.html
