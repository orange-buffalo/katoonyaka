# katoonyaka
Portfolio site of my favourite hand-maker: [katoonyaka.co](http://katoonyaka.co/).

## Technology stack
Our project is [Spring Boot](http://projects.spring.io/spring-boot/) Java application, 
with [Angular](https://angularjs.org/) front-end. 
We build the stylesheets from [SASS](http://sass-lang.com/) sources using [Compass](http://compass-style.org/),
all the front-end code is assembled with [Grunt](http://gruntjs.com/).
[Gradle](http://gradle.org/) is orchestrating the build process, 
connecting java and web universes. 

## Build
1. Install JDK 8+.
2. Install [Gradle 2.1+](http://gradle.org/).
3. Install the latest [Compass](http://compass-style.org/install/).

### First-time build preparation
To speed-up the build process we extracted some preparation time-consuming
steps to be executed only once. Regular builds will use configured infrastructure.

#### Grunt setup
```
cd $PROJECT_ROOT
gradle configureGrunt
```

#### Font build setup
Font build is optional and not included into core project build flow. 
It is required to be setup only if the changes to iconic font are planned

1. Install [FontForge] (https://fontforge.github.io/en-US/) and add `bin` folder to system path.
 
     **For Windows**: Use [this distribution](http://fontforgebuilds.sourceforge.net/).

2. **For Windows**: Download [sfnt2woff.exe] (https://people.mozilla.org/~jkew/woff/) and put it to `bin` folder of FontForge.

3. Install [ttfautohint](http://www.freetype.org/ttfautohint/#download) and make it available on system path.

### Build the project
```
cd $PROJECT_ROOT
gradle clean build
```

### Build the font
In case iconic-font should be rebuilt, this gradle task is to be executed.
```
cd $PROJECT_ROOT
gradle buildFont
```
The result will be the changes in scss files, to be committed to git.