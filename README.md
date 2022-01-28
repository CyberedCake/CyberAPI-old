# CyberAPI
CyberAPI is used for some of Cyber's plugins, including CyberNet ones!

# How to use

## Installation
You can install this by going **_[here](https://jitpack.io/#CyberedCake/CyberAPI/)_** and clicking on gradle or 
maven and adding to your pom.xml or build.gradle!

- Replace "Tag" in the 'version' section of Maven or Gradle with the version below (exclude "JitPack" and put a "v" in front of it):

    [![](https://jitpack.io/v/CyberedCake/CyberAPI.svg)](https://jitpack.io/#CyberedCake/CyberAPI)

Basically, you can add CyberAPI by using...

<details>
  <summary>... Maven (Click to expand/shrink)</summary>
  
  ## Adding CyberAPI (Maven)
    
  Add this to your maven repositories...  
  ```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
 </repository>
  ```
    
  Add this to your maven dependencies...
  ```xml
<dependency>
    <groupId>com.github.CyberedCake</groupId>
    <artifactId>CyberAPI</artifactId>
    <version>Put latest version with a 'v' in front HERE</version>
</dependency>
   ```
</details>

<details>
  <summary>... Gradle (Click to expand/shrink)</summary>
  
  ## Adding CyberAPI (Gradle)
    
  Add this to your build.gradle...  
  ```gradle
repositories {
	maven { url 'https://jitpack.io' }
}
  ```
    
  ```gradle
dependencies {
	implementation 'com.github.CyberedCake:CyberAPI:Put latest version with a 'v' in front HERE'
}
   ```
</details>

## Usage
To use CyberAPI, write this in your main onEnable stuff
```java
public class Main extends Spigot { // if your plugin is spigot or a fork of spigot (paper)
public class Main extends Bungee { // if your plugin is bungee or a fork of bungee (waterfall)

	@Override
	public void onEnable() {
		// make sure CyberAPI.init... is always the first thing in your onEnable class if it accesses anything (like Log#info or something)
	
		CyberAPI.initSpigot(this); // if your plugin is spigot or a fork of spigot (paper)
		CyberAPI.initBungee(this); // if your plugin is bungee or a fork of bungee (waterfall)
	}

}
```
