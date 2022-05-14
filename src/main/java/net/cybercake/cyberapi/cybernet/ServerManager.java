/********************************************************************************************************************************

 > CYBERNET ONLY CLASS <
 USING THIS CLASS WILL MOSTLY PROVIDE NO USE TO YOU, THE DEVELOPER AS MOST
 OF THESE REQUIRE CYBERNET'S ENVIRONMENT! IF YOU USE ANY OF THESE METHODS
 YOU WILL BE DENIED ANY SUPPORT! YOU HAVE BEEN WARNED!
 *******************************************************************************************************************************/

package net.cybercake.cyberapi.cybernet;

import net.cybercake.cyberapi.CyberAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    private ServerManager() {}

    protected static ServerManager serverManager() {
        return new ServerManager();
    }

    private static File mainDirectory = null;
    private static List<File> serverDirectories = new ArrayList<>();

    public File getMainDirectory() {
        if (mainDirectory == null) {
            mainDirectory = CyberAPI.getAPI().getDataFolder().getAbsoluteFile().getParentFile().getParentFile().getParentFile();
        }
        return mainDirectory;
    }

    public List<File> getAllServerFiles() {
        if(serverDirectories.size() < 1) {
            getAllServers();
        }

        return serverDirectories;
    }

    public List<CyberNetServer> getAllServers() {
        List<CyberNetServer> servers = new ArrayList<>();
        serverDirectories.clear();
        try {
            for(File file : getMainDirectory().listFiles()) {
                if(!file.isDirectory()) continue;

                for(File inDir : file.listFiles()) {
                    if(inDir.isDirectory() || (!inDir.getName().equalsIgnoreCase("server.txt"))) continue;

                    servers.add(CyberNetServer.fromDirectory(file));
                    serverDirectories.add(file);
                    break;
                }
            }
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to load all servers! [CRITICAL]");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        return servers;
    }

    public CyberNetServer getServerFromName(String name) {
        CyberNetServer returned = null;
        try {
            for(File actualServer : getAllServerFiles()) {
                for(File serverFile : actualServer.listFiles()) {
                    if(returned != null) break;

                    if(!serverFile.getName().equalsIgnoreCase("server.txt")) continue;

                    BufferedReader reader = new BufferedReader(new FileReader(serverFile));
                    String line;
                    while((line = reader.readLine()) != null) {
                        if(returned != null) break;

                        if(line.split(":").length != 2) continue;

                        if(line.split(":")[0].equalsIgnoreCase("server")) {
                            if(line.split(":")[1].equalsIgnoreCase(name)) {
                                returned = CyberNetServer.fromDirectory(actualServer);
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to load all servers ... files! [CRITICAL]");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        return returned;
    }

    public CyberNetServer getServerFromDir(File directory) {
        return CyberNetServer.fromDirectory(directory);
    }

}
