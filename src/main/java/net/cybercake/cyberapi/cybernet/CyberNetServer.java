/********************************************************************************************************************************

 > CYBERNET ONLY CLASS <
 USING THIS CLASS WILL MOSTLY PROVIDE NO USE TO YOU, THE DEVELOPER AS MOST
 OF THESE REQUIRE CYBERNET'S ENVIRONMENT! IF YOU USE ANY OF THESE METHODS
 YOU WILL BE DENIED ANY SUPPORT! YOU HAVE BEEN WARNED!
 *******************************************************************************************************************************/

package net.cybercake.cyberapi.cybernet;

import net.cybercake.cyberapi.CyberAPI;

import java.io.*;
import java.util.HashMap;

public class CyberNetServer {

    private final String serverLiteral;
    private final File directory;

    private CyberNetServer(File directory, String serverLiteral) {
        this.directory = directory;
        this.serverLiteral = serverLiteral;
    }

    // String: serverLiteral, File: directory
    private static final HashMap<String, File> knownServers = new HashMap<>();

    protected static CyberNetServer fromDirectory(File directory) {
        if(directory.listFiles() == null) return null;

        try {
            File returned = null;
            for(File file : directory.listFiles()) {
                if(file.getName().contains("server.txt")) {
                    returned = file;
                    break;
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(returned));
            String line;
            String literal = "none";
            while((line = reader.readLine()) != null) {
                if(!line.split(":")[0].equalsIgnoreCase("server")) continue;

                literal = line.split(":")[1];
            }

            return new CyberNetServer(directory, literal);
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to read the server info file (directory=" + directory.getName() + ")! [CRITICAL]");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        return null;
    }

    public String getServerLiteral() {
        return serverLiteral;
    }

    public File getDirectory() {
        return directory;
    }

    public ServerInfoFile getInfoFile() {
        return ServerInfoFile.serverInfoFile(getDirectory(), getServerLiteral());
    }

}
