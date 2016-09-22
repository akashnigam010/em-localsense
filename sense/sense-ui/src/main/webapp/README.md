Sense
> A full-featured User Interface written in Angular for Sense Web App. 

###Quick Start
---

Install [Node.js](https://nodejs.org/download/).

Fork this Repository to your Bitbucket Account then :
   
```
    $ git clone https://cantwaitguest@bitbucket.org/cantwaitguest/cantwait_ui.git
    $ cd cantwait_ui
    $ git checkout '<branchname>'
    $ npm install --save-dev grunt
    $ grunt startServer


```

Boom!! , This will open your default browser window running current development version. It will **reload the app** if you make changes to source files.


###Developer Guide
---

Refer to this section for more detailed instructions on different tasks

####Installing dependencies
---

This application have npm and bower dependencies.

To Install **npm dependencies** , run
 
 ```
 $ npm install	
 ``` 

To Install **bower dependencies** , run
 
 ```
 $ bower install	
 ``` 

####Run Application
---

Simply Type

 ```
 $ grunt startServer
  ``` 

####Run Unit Test Cases
---

Karma + Jasmine Unit Test cases are placed and implemented in folder `unit/tests`

To run Unit Test Cases , Simply Type

 ```
 $ grunt test
  ```   

####CodeReview
---

If you are contributing to the project , make sure you **run codereview check** against the code added. 
If you make changes when Application is up and running , a code review is performed automatically in background and display on Command windows or shell.

To run Unit Test Cases , Simply Type

 ```
 $ grunt codereview
  ``` 

####Build Package
---

If you like to build a deployable Artifact and run it in your own http server. Simply Type :

 ```
 $ grunt build
 $ grunt buildpackage
  ``` 