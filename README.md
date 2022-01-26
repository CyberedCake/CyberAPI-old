# CyberAPI
CyberAPI is used for some of Cyber's plugins, including CyberNet ones!

# How to use

## Installation
You can install this by going **_[here](https://jitpack.io/#CyberedCake/CyberAPI/)_** and clicking on gradle or 
maven and adding to your pom.xml or build.gradle!

- Replace "Tag" in the 'version' section of Maven or Gradle with the version below (exclude "JitPack"):

    [![](https://jitpack.io/v/CyberedCake/CyberAPI.svg)](https://jitpack.io/#CyberedCake/CyberAPI)

## Usage
To use CyberAPI, write this in your main onEnable stuff
```java
@Override
public void onEnable() {
    CyberAPI.initSpigot(this); // if your plugin is spigot or a fork of spigot (paper)
    CyberAPI.initBungee(this); // if your plugin is bungee or a fork of bungee (waterfall)
}
```
