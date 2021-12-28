
              ##        #######   ######   ########  ########   ######
              ##       ##     ## ##    ##  ##     ## ##     ## ##    ##
              ##       ##     ## ##        ##     ## ##     ## ##
              ##       ##     ## ##   #### ########  ########  ##
              ##       ##     ## ##    ##  ##   ##   ##        ##
              ##       ##     ## ##    ##  ##    ##  ##        ##    ##
              ########  #######   ######   ##     ## ##         ######

                             Version 1.0 by Logicism
              A Rich Presence program to show your activity on Discord

-------------------------------------------------------------------------------------

Java 8 is required to run this program! You can download it from:
    https://www.java.com/

Presence Types:
    Manual:
      - Default (config.yml) w/ Presets
      - Customizable Presence
      - Game Consoles:
          - Nintendo 3DS
          - Wii U
          - Nintendo Switch
      Credits go to ninstar on GitHub for Nintendo Console Discord Presences
      
    Program (Windows Only):
      Uses Windows User32 Library to grab the current window title and process name that the user is using currently.
      - Adobe (Photoshop, Illustrator, Indesign, After Effects, Premiere Pro, Dreamweaver, Acrobat Reader)
      - Beat Saber
      - Emulators (DeSmuME, DOSBox, FCEUX, Fusion, MAME, melonDS, Mesen, MPC-HC, NO$GBA, SNES9X, VisualBoyAdvance-M)
      - Microsoft Office (Word, PowerPoint, Excel)
      - LibreOffice (Writer, Calc, Impress, Draw, Base, Math)
      - Media Player Classic
      - Sony Vegas Pro 14.0
      - VLC Media Player
      - Zoom Meetings
    Credits go to: 
      MechaDragonX on GitHub for DOSBox, FCEUX, Fusion, SNES9X, VisualBoyAdvance-M, MAME Discord Presence Application IDs
      angeloanan on GitHub for MPC-HC Discord Presence Application IDs
      Pigpog on GitHub for VLC Discord Presence Application IDs
      FizzyApple12 on GitHub for Beat Saber Discord Presence Application IDs

    Website (Chrome/Firefox Extension):
      Uses the Chrome/Firefox Extension to grab the Website Title, URL and HTML to parse into Discord Presences.
      - Dailymotion
      - Instagram
      - Netflix
      - Twitch
      - Twitter
      - Wikipedia
      - YouTube
    Credits go to PreMiD on GitHub for Website Discord Presence Application IDs
    

    PlayStation (PS4/PS5) Presence:
      Uses the official Playstation API to grab your presence and what game you are playing or if you're at the home screen.
    Credits go to sirzechs753 on GitHub for PlayStation Discord Presence Application IDs

    Beat Saber Presence:
      Requires the HTTP Status Mod installed from ModAsssistant in order for it to work.
    Credits go to FizzyApple12 on GitHub for Beat Saber Application IDs

    Wiimmfi Mario Kart Wii Presence:
      Uses your browser or a headless proxy server to grab data from Wiimmfi.de

    DeSmuME (Pokémon Gen 4):
      Grabs data from a lua script to track map information on the emulator.
    Supports only Diamond, Pearl, and Platinum ROMs. Credits go to kiwi515 on GitHub for the script

If you would like to request a program, website or presence to be added, DM Logicism at his Discord (Logicism#9308)

-------------------------------------------------------------------------------------

	                How to install the LogRPC Extension

1. Enable Developer Mode on your Browser (if you are using Firefox, skip to the next step):  Click the menu icon and select Extensions or type in the url above chrome://extensions. Toggle the Developer Mode Option.

2. Load the Unpacked Extension:
  - Chrome (or Chromium Based Browsers): Click on Load Unpacked on the Extensions Page and select the LogRPC Extension folder.
  - Firefox (or Firefox Based Browsers): Click on the menu icon and select Settings. Click on Extensions & Themes below and then click on   Extensions on the left side panel. Click on the gear on the top right and click Debug Add-Ons. You can also type in the URL about:debugging.   Then click on Load Temporary Add-On and select the manifest.json file in the LogRPC Extension folder.

3. Test the Presence: Right Click on the LogRPC Program on the taskbar and click on Chrome. Your presence should be shown as you visit a page. You may have to reload the page if your browser is already on one.

-------------------------------------------------------------------------------------

		     How to get your PlayStation NPSSO Cookie

1. If you are not logged in to playstation.com, login with the account you would like to grab the presence data from.

2. Go to https://ca.account.sony.com/api/v1/ssocookie on your browser and copy the npsso into the config.yml

3. Test the Presence: Right Click on the LogRPC Program on the taskbar and click on PlayStation. Your presence should be shown.

* PlayStation NPSSO Cookies expire after 2 months, so if your Cookie is expired or is near expiration, you can repeat the steps above.

-------------------------------------------------------------------------------------

		     How to use the headless Wiimmfi Presence

* This is only for users who have set wiimmfiPresenceType to headless. This method does is grab data from a local FlareSolverr proxy server without having to open a browser window. This is only for advanced users, if you rather use your browser window to grab data, follow the instructions above on how to install the LogRPC Extension and then click on the Wiimmfi Presence instead of Chrome.

1. Download and Install FlareSolverr on GitHub and then start the proxy server. https://github.com/FlareSolverr/FlareSolverr

2. Configure the FlareSolverr URL if you are not running it on the same machine as LogRPC.

3. Right Click on the LogRPC Program on the taskbar and click on Wiimmfi. Your presence should be shown as you play on a Worldwide, Continential/Regional, or Private Room.

-------------------------------------------------------------------------------------

		      How to use the DeSmuME Presence Script

1. Open DeSmuME with a Platnium, Pearl or Diamond ROM.

2. Click on Tools and then Lua Scripting then click on New Lua Script Window. Click on Browse and open the export.lua file in the DeSmuME Presence Script file.

3. Right Click on the LogRPC Program on the taskbar and click on DeSmuME. In the DeSmuME Presence Script Folder, a file called out.dat will be generated by the emulator. Open that file in the dialog shown and your presence will be generated.

-------------------------------------------------------------------------------------
