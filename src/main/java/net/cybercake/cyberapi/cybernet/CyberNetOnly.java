/********************************************************************************************************************************

                            > CYBERNET ONLY CLASS <
                                USING THIS CLASS WILL MOSTLY PROVIDE NO USE TO YOU, THE DEVELOPER AS MOST
                                OF THESE REQUIRE CYBERNET'S ENVIRONMENT! IF YOU USE ANY OF THESE METHODS
                                YOU WILL BE DENIED ANY SUPPORT! YOU HAVE BEEN WARNED!
 *******************************************************************************************************************************/

package net.cybercake.cyberapi.cybernet;

import net.cybercake.cyberapi.CyberAPI;

public class CyberNetOnly {

    private CyberNetOnly() {}

    public static CyberNetOnly getInstance() {
        if(!CyberAPI.getAPI().isCyberNet()) {

            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.WARN, "{plugin} is trying to access a CyberNetOnly resource! Please nag them about removing this from their plugin as they are not registered as apart of CyberNet.");
            return null;
        }
        return new CyberNetOnly();
    }

    public MainServerInfo getMainServerInfo() {
        return MainServerInfo.mainServerInfo();
    }

    public ServerManager getServerManager() {
        return ServerManager.serverManager();
    }

}
