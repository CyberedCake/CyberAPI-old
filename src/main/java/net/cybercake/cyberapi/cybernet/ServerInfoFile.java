/********************************************************************************************************************************

 > CYBERNET ONLY CLASS <
 USING THIS CLASS WILL MOSTLY PROVIDE NO USE TO YOU, THE DEVELOPER AS MOST
 OF THESE REQUIRE CYBERNET'S ENVIRONMENT! IF YOU USE ANY OF THESE METHODS
 YOU WILL BE DENIED ANY SUPPORT! YOU HAVE BEEN WARNED!
 *******************************************************************************************************************************/

package net.cybercake.cyberapi.cybernet;

import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerInfoFile {

    private File serverDirectory;
    private String serverLiteral;

    private ServerInfoFile(File directory, String literal) {
        serverDirectory = directory;
        serverLiteral = literal;
    }

    protected static ServerInfoFile serverInfoFile(File serverDirectory, String serverLiteral) {
        return new ServerInfoFile(serverDirectory, serverLiteral);
    }

    private static final ArrayList<String> lines = new ArrayList<>();

    public String getServerLiteral() {
        return serverLiteral;
    }

    public File getDirectory() {
        return serverDirectory;
    }

    public File getServerDescriptor() {
        return new File(getDirectory(), "server.txt");
    }

    public List<String> getLines(boolean containsComments) {
        if(ServerInfoFile.lines.size() > 0) {
            return lines;
        }

        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(getServerDescriptor()));

            String line;
            while((line = reader.readLine()) != null) {
                if(!containsComments && (line.startsWith("#") || line.startsWith("//"))) continue;
                lines.add(line);
            }
        } catch (IOException ioException) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to read server info file line (server=" + getServerLiteral() + ")! [CRITICAL]");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, ioException);
        }
        return lines;
    }

    public String getValue(String key) {
        int index = 0;
        StringBuilder addToPrevious = new StringBuilder();
        Log.info("[debug] starting for loop");
        for(String line : getLines(false)) {
            Log.info("[debug] current line -> " + line);
            boolean multiLinedBlock = line.startsWith("  ") || line.startsWith("\u0009") || line.strip().equalsIgnoreCase("");
            Log.info("[debug] multi lined block -> " + multiLinedBlock);
            if(!addToPrevious.isEmpty() && multiLinedBlock) {
                Log.info("[debug] addToPrevious is not empty!! -- appending line");
                addToPrevious.append("\n").append(line);
            }
            if(!addToPrevious.isEmpty() && !multiLinedBlock) {
                Log.info("[debug] returning because addToPrevious is not empty, not multiLinedBlock");
                return addToPrevious.substring(1);
            }

            Log.info("only continuing if multiLinedBlock is false: " + multiLinedBlock);
            if(multiLinedBlock) continue;

            if(line.split(":").length != 2) {
                CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to read key (key=" + key + ", lineIgnoringComments=" + index + ") because couldn't determine the key-value context! [CRITICAL]"); return null;
            }

            Log.info("[debug] continuing!");
            if(line.split(":")[0].equalsIgnoreCase(key)) {
                Log.info("[debug] success! line -> " + line);
                String value = line.split(":")[1];
                Log.info("[debug] success! line.value -> " + value);
                if (!value.strip().startsWith("|-")) {
                    Log.info("[debug] returning " + line.split(":")[1]);
                    return line.split(":")[1];
                }
                Log.info("multi lined! added ' ' to addToPrevious.");
                addToPrevious.append(" ");
            }
            index++;
        }
        return null;
    }

    public String getKey(String value) {
        int index = 0;
        for(String line : getLines(false)) {
            if(line.split(":").length != 2) {
                CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to read key (value=" + value + ", lineIgnoringComments=" + index + ") because couldn't determine the key-value! [CRITICAL]"); return null;
            }
            if(line.split(":")[1].equalsIgnoreCase(value)) return line.split(":")[0];
            index++;
        }
        return null;
    }

}
