# svsu-snap

### Requirements
1. [JDK 7+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Android Studio](http://developer.android.com/sdk/index.html) 
3. [git](http://git-scm.com/) - [(basic git setup)](https://help.github.com/articles/set-up-git/)


### Android Studio Setup:

1. Clone the project using git  into your selected directory. 
2. In Android Studio go to File -> Import Project. Select the project you just cloned.
3. Let Android Studio do everything and wait until you have no more messages.

### Parse Setup:
1. [Create Parse Account](https://www.parse.com/#signup) (you can login/sign using a github account)
2. [Create a project on parse](https://www.parse.com/apps/new)
3. Copy down your application id and client key
4. In Android Studio open SnapItApplication.java (app/src/main/java) and place your application id and client key 
```    
    public static final String APPLICATION_ID = "YOUR APP ID HERE";
    public static final String CLIENT_KEY = "YOUR CLIENT KEY HERE";
```
