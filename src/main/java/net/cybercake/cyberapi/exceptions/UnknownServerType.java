package net.cybercake.cyberapi.exceptions;

import net.cybercake.cyberapi.CyberAPI;

@Deprecated
public class UnknownServerType extends RuntimeException{

    @Deprecated
    public UnknownServerType(String errorMsg) {
        super(CyberAPI.getAPI().getPrefix(true) + " " + errorMsg);
    }

}
