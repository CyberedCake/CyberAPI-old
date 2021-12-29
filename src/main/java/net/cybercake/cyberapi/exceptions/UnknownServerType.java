package net.cybercake.cyberapi.exceptions;

import net.cybercake.cyberapi.CyberAPI;

public class UnknownServerType extends RuntimeException{

    public UnknownServerType(String errorMsg) {
        super(CyberAPI.getAPI().getPrefix(true) + " " + errorMsg);
    }

}
