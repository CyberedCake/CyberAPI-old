## This is outdated! Please use [the latest version of CyberAPI](https://github.com/CyberedCake/CyberAPI)

# CyberAPI
CyberAPI is used for some of Cyber's plugins, including CyberNet ones! It has additional features that Spigot and 
Bungeecord do not have.

# How to use

## Installation

### It is recommended that use Paper if you're using a Spigot fork or use Waterfall if you're using a Bungeecord fork!

You can install this by going **_[here](https://jitpack.io/#CyberedCake/CyberAPI/)_** and clicking on gradle or 
maven and adding to your pom.xml or build.gradle!

- Replace "Tag" in the 'version' section of Maven or Gradle with the version below (exclude "JitPack"):

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
    <version>v2.1.10</version>
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
	compileOnly 'com.github.CyberedCake:CyberAPI:v2.1.10'
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
		// make sure the methods below are the first things that come in the onEnable method,
		// especially if you use something like Log#info in the onEnable
		
		CyberAPI.initSpigot(this); // if your plugin is spigot or a fork of spigot (paper)
		CyberAPI.initBungee(this); // if your plugin is bungee or a fork of bungee (waterfall)
      
                CyberAPI.silenceLogs(true); // optional, silences info and warning messages (updates, init message, etc)
      
	}

}
```
