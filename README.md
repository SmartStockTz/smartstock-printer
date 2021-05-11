# INSTALLATION

## MAC

To install this printer service in a mac, you need to create a launchd daemon and execute upon user login. To achieve that do the following:

- Install java JDK and JRE
- Build the latest version of the app in jar format 
- Place the jar in the /Users/Shared/smartstock directory
- Create a .plist file to direct launchd on how to run the service
    >  vim  ~/Library/LaunchAgents/com.smartstock.daemon.plist             
- Add the following instructions in the file above.
    > <?xml version="1.0" encoding="UTF-8"?>
      <!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
      <plist version="1.0">
        <dict>
      
          <key>Label</key>
          <string>com.smartstock.daemon.plist</string>
      
          <key>KeepAlive</key>
          <true/>
      
          <key>StandardErrorPath</key>
          <string>/Users/Shared/smartstock/stderr.log</string>
      
          <key>StandardOutPath</key>
          <string>/Users/Shared/smartstock/stdout.log</string>
      
          <key>EnvironmentVariables</key>
          <dict>
            <key>PATH</key>
            <string><![CDATA[/usr/local/bin:/usr/local/sbin:/usr/bin:/bin:/usr/sbin:/sbin]]></string>
          </dict>
      
          <key>ProgramArguments</key>
          <array>
            <string>/usr/bin/java</string>
            <string>-jar</string>
            <string>/Users/Shared/smartstock/ssmjavapos-0.1.2.jar</string>
          </array>
      
        </dict>
      </plist>
 
- Launch the service by running.
    > launchctl load  ~/Library/LaunchAgents/com.smartstock.daemon.plist
  
NOTE: 
 - You can stop the service by running
    >  launchctl unload  ~/Library/LaunchAgents/com.smartstock.daemon.plist
 - Open Utilities and open an application called Console. This application allows you to view system logs as they are being executed.

    
