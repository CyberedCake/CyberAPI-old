/********************************************************************************************************************************

                            > CYBERNET ONLY CLASS <
                                USING THIS CLASS WILL MOSTLY PROVIDE NO USE TO YOU, THE DEVELOPER AS MOST
                                OF THESE REQUIRE CYBERNET'S ENVIRONMENT! IF YOU USE ANY OF THESE METHODS
                                YOU WILL BE DENIED ANY SUPPORT! YOU HAVE BEEN WARNED!
 *******************************************************************************************************************************/

package net.cybercake.cyberapi.cybernet;

import net.cybercake.cyberapi.CyberAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainServerInfo {

    private MainServerInfo() {}

    protected static MainServerInfo mainServerInfo() {
        return new MainServerInfo();
    }

    private String fileName = "server.txt";

    private static ArrayList<String> lines = new ArrayList<>();

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return new File(ServerManager.serverManager().getMainDirectory(), getFileName());
    }

    public List<String> getLines(boolean containComments) {
        if(MainServerInfo.lines.size() > 0) {
            return lines;
        }

        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(getFile()));

            String line;
            while((line = reader.readLine()) != null) {
                if(!containComments && (line.startsWith("#") || line.startsWith("//"))) continue;
                lines.add(line);
            }
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to find the server info file! [CRITICAL]");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        MainServerInfo.lines = lines;
        return lines;
    }

    public String getValue(String key) {
        int index = 0;
        StringBuilder addToPrevious = new StringBuilder();
        for(String line : getLines(false)) {
            boolean multiLinedBlock = line.startsWith("  ") || line.startsWith("\u0009") || line.strip().equalsIgnoreCase("");
            if(!addToPrevious.isEmpty()) {
                if(multiLinedBlock) {
                    return addToPrevious.substring(1);
                }
                addToPrevious.append("\n").append(line);
            }

            if(multiLinedBlock) continue;

            if(line.split(":").length != 2) {
                CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "Failed to read key (key=" + key + ", lineIgnoringComments=" + index + ") because couldn't determine the key-value context! [CRITICAL]"); return null;
            }
            if(line.split(":")[0].equalsIgnoreCase(key)) {
                String value = line.split(":")[1];
                if(!value.strip().startsWith("|-")) {
                    return line.split(":")[1];
                }
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
