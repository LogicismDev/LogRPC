package me.Logicism.LogRPC.core.executors;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class ProgramRunnable implements Runnable {

    private int processID = 0;
    private String windowTitle;

    @Override
    public void run() {
        while (LogRPC.INSTANCE.getProgramMenuItem().getState()) {
            IntByReference intRef = new IntByReference();
            LogRPC.User32.INSTANCE.GetWindowThreadProcessId(LogRPC.User32.INSTANCE.GetForegroundWindow(), intRef);

            int procID = intRef.getValue();

            if (procID != processID && procID != 0) {
                processID = procID;

                byte[] buffer = new byte[1024 * 2];
                LogRPC.User32.INSTANCE.GetWindowTextA(LogRPC.User32.INSTANCE.GetForegroundWindow(), buffer, 1024);

                windowTitle = Native.toString(buffer, "windows-1256");
            } else if (procID == processID && procID != 0) {
                byte[] buffer = new byte[1024 * 2];
                LogRPC.User32.INSTANCE.GetWindowTextA(LogRPC.User32.INSTANCE.GetForegroundWindow(), buffer, 1024);

                String windowTitleA = Native.toString(buffer, "windows-1256");

                if (!windowTitleA.isEmpty() && !windowTitle.equals(windowTitleA)) {
                    windowTitle = windowTitleA;
                }
            }

            SystemInfo systemInfo = new SystemInfo();
            OperatingSystem os = systemInfo.getOperatingSystem();
            OSProcess process = os.getProcess(processID);

            if (process != null) {
                System.out.println(process.getName());

                try {
                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.PROGRAM, new JSONData(new JSONObject().put("title", windowTitle).put("details", process.getName()))));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
