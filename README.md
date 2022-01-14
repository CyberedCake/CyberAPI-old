# CyberAPI
CyberAPI is used for some of Cyber's plugins, including CyberNet ones!

# How to use

## Installation
Install the jar file and include it in your libraries for your project (seen below, IntelliJ is shown)

![img.png](img.png)

After this, press the "apply" button then close that menu.

Please note: everytime you reload your maven/gradle, it will disable CyberAPI. I will fix that eventually.

## Usage
To use CyberAPI, write this in your main onEnable stuff
```java
@Override
public void onEnable() {
    CyberAPI.initSpigot(this); // if your plugin is spigot or a fork of spigot (paper)
    CyberAPI.initBungee(this); // if your plugin is bungee or a fork of bungee (waterfall)
}
```